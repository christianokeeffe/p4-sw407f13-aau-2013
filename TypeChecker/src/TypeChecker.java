import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeChecker extends SPLADBaseVisitor<Value>{
	
	List<Error> ErrList = new ArrayList<Error>();
	List<String> ErrorFunctions = new ArrayList<String>();
	
	Map<String, Variable> VariableMemory = new HashMap<String, Variable>();
	Map<String, Function> FunctionMemory = new HashMap<String, Function>();
	
	
	@Override
	public Value visitAssign(SPLADParser.AssignContext ctx) {
		Variable Var = new Variable();
		Value Expr = new Value("");

		String id = ctx.callid().getText();
				
		if(id.contains("["))
		{
			id = id.substring(0, id.indexOf('['));
		}
		
		if(ctx.assignend() != null)
		{
			Expr = visit(ctx.assignend());
			Var.Value = Expr.toString();
			
			if(VariableMemory.containsKey(id))
			{
				Var = VariableMemory.get(id);
				
				if(Var.constant == true && Var.isArray == false)
				{
					Error err = new Error(18, id);
					ErrList.add(err);
				}
				else if(Var.Type.equals("int") || Var.Type.equals("double"))
				{
					if(!Expr.isNumericExpression())
					{
						Error err = new Error(2, Expr.toString());
						ErrList.add(err);
					}
				}
			}
			else
			{
				Variable nVar = new Variable();
				nVar.Id = id;
				nVar.Value = Expr.toString();
			}
		}
		else
		{
			Var.Value = VariableMemory.get(id).Value; 
		}
		
		
		
		Value Callid = visit(ctx.callid());
	
		
		Var.Id = id;

		return new Value(id + "<--" + Expr);
	}
	
	@Override
	public Value visitAssignend(SPLADParser.AssignendContext ctx)
	{		
		Value val;
		
		if(ctx.expr() != null)
		{
			val = visit(ctx.expr());
			
			String[] NewArr = val.toString().split("[\\+\\-\\*\\/]");
			
			for(String str : NewArr)
			{
				if(str.length() > 1 && str.charAt(0) != '"' && str.charAt(str.length()-1) != '"')
				{
					if(str.charAt(0) != '\'' && str.length() != 3)
					{
						if(!new Value(str).isNumeric() && !str.equals("true") && !str.equals("false"))
						{
							if(!VariableMemory.containsKey(str))
							{
								int trigger = 0;
								for(Function f : FunctionMemory.values())
								{
									if(f.Params != null)
									{
										for(ParamsType p : f.Params)
										{
											if(p.id.contains("["))
											{
												p.id = p.id.substring(0, p.id.indexOf('['));
											}
												
											if(p.id.equals(str))
											{
												trigger = 1;
											}
										}
									}
								}
								if(trigger == 0)
								{
									Error err = new Error(5, str);
									ErrList.add(err);
								}
							}
						}
					}
				}
				
			}
		}
		else
		{
			val = new Value();
		}
		
		return val;
	}
	
	@Override
	public Value visitTermsymbol(SPLADParser.TermsymbolContext ctx){
		Value val = new Value("");
		
		if (ctx.getText().trim() == "AND"){
			val = new Value("AND");
		}
		else {
			val = new Value(ctx.getText());
		}
		return val;
	}
	
	@Override
	public Value visitExprsymbol(SPLADParser.ExprsymbolContext ctx){
		Value ReturnVal;
		
		if (ctx.getText().trim() == "OR"){
			ReturnVal = new Value("||");
		}
		else {
			ReturnVal = new Value(ctx.getText());
		}
		return ReturnVal;
	}
	
	@Override
	public Value visitNumeric(SPLADParser.NumericContext ctx) {
		String SignSymbol = visit(ctx.plusminusorempty()).toString();
		String Value = ctx.DIGIT().getText();
		String Decimal = "";
		
		if(ctx.numericend().children != null)
		{
			Decimal = ctx.numericend().getText();
		}
		
		Value ReturnValue = new Value( SignSymbol + Value + Decimal );
		
		return ReturnValue;
	}
	
	@Override
	public Value visitNontermswitch(SPLADParser.NontermswitchContext ctx) {
		Value expr = visit(ctx.expr());
		Value cases =visit(ctx.cases());
		return new Value(expr.toString() + cases.toString());
	}
	
	@Override
	public Value visitRoot(SPLADParser.RootContext ctx) {
		if(ctx.dcl() != null){
			//visit(ctx.dcl());
			visit(ctx.dcl());
		}
		else if (ctx.function() != null){
			visit(ctx.function());
		}
		else if(ctx.drinkdcl() != null)
		{
			visit(ctx.drinkdcl());
		}
		return null;
	}
	
	@Override
	public Value visitNontermwhile(SPLADParser.NontermwhileContext ctx) {
		Value expr = visit(ctx.expr());
		
		if(!expr.isBool() && !expr.toString().contains(">") && !expr.toString().contains("<") && !expr.toString().contains("="))
		{
			Error err = new Error(11, expr.toString());
			ErrList.add(err);
		}
		
		Value block = visit(ctx.block());
		return new Value(expr.toString() + block.toString());
	}
	
	@Override
	public Value visitStmts(SPLADParser.StmtsContext ctx) {
		Value stmt = new Value("");
		Value stmts = new Value("");
		
		if (ctx.stmt() != null){
			stmt = visit(ctx.stmt());
			stmts = visit(ctx.stmts());
		}
		
		return new Value(stmt.toString()+ " " + stmts.toString());
	}
	
	@Override
	public Value visitTermend(SPLADParser.TermendContext ctx) {
		String TermSymbol = "";
		String Term = "";
		
		if (ctx.getText() != ""){
			TermSymbol = visit(ctx.termsymbol()).toString();
			Term = visit(ctx.term()).toString();
		}
		
		if(TermSymbol.equals("") && Term.equals(""))
		{
			return null;
			
		}
		else
		{
			return new Value(TermSymbol + Term);
		}
	}
	
	@Override
	public Value visitNumericend(SPLADParser.NumericendContext ctx) {
		
		if (ctx.getText() != ""){
			return new Value("." + ctx.DIGIT().getText());
		}
		else
		{
			return new Value("");
		}
		
	}
	
	@Override
	public Value visitExpr(SPLADParser.ExprContext ctx) {
		
		Value val = visit(ctx.term());

		Value ExprEndVal = new Value("");
		
		if(ctx.exprend().children != null)
		{
			ExprEndVal = visit(ctx.exprend());
		}
		
		String Expr  = val.toString() + ExprEndVal.toString();
		
		return new Value( Expr );
	}
	
	@Override
	public Value visitFrom(SPLADParser.FromContext ctx) {
		Value assign = 	visit(ctx.assign());
		Value expr = visit(ctx.expr());
		Value plusMinusorEmpty =  visit(ctx.plusminusorempty());
		Value block =  visit(ctx.block() ); 
		
		if(assign.toString().contains("<--"))
		{
			assign = new Value(assign.toString().substring(assign.toString().indexOf("<--")+3, assign.toString().length()));
		}
		
		if(!assign.isInt())
		{
			Error err = new Error(12, assign.toString());
			ErrList.add(err);
		}
		else if(!expr.isNumericExpression())
		{
			Error err = new Error(12, expr.toString());
			ErrList.add(err);
		}
		
		return new Value(assign.toString() + expr.toString() + plusMinusorEmpty.toString() + block);
	}
	
	@Override public Value visitArraycall(SPLADParser.ArraycallContext ctx) 
	{ 
		return visitChildren(ctx); 
	}

	
	@Override
	public Value visitCallexpr(SPLADParser.CallexprContext ctx) {
		
		if (ctx.subcallexpr() != null){
			return visit(ctx.subcallexpr());
		}
		
		return new Value("");
	}
	
	@Override 
	public Value visitPlusminusorempty(SPLADParser.PlusminusoremptyContext ctx) 
	{ 
		if(ctx.plusminus() == null)
		{
			return new Value("");
		}
		else
		{
			return visit(ctx.plusminus());
		}
	}
	
	@Override public Value visitConstant(SPLADParser.ConstantContext ctx) 
	{ 
		return new Value(ctx.getText()); 
	}
	
	
	@Override
	public Value visitType(SPLADParser.TypeContext ctx) {
		
		Value val = new Value();
		Value vcon = new Value();
		if(ctx.primitivetype() != null)
		{
			val = visit(ctx.primitivetype());
		}
		
		if(ctx.specialtype() != null)
		{
			val = visit(ctx.specialtype());
			val = new Value("const," + val.toString());
		}
		
		if(ctx.constant() != null)
		{
			if(!visit(ctx.constant()).toString().equals(""))
			{
				vcon = new Value(visit(ctx.constant()).toString() + ",");
			}
		}
		return new Value(vcon.toString() + val.toString());
	}
	
	@Override public Value visitSpecialtype(SPLADParser.SpecialtypeContext ctx) 
	{ 
		return new Value(ctx.getText()); 
	}
	
	@Override
	public Value visitPrimitivetype(SPLADParser.PrimitivetypeContext ctx){
		
		Value val = new Value(ctx.getText());

		return val;
	}
	
	@Override
	public Value visitFunction(SPLADParser.FunctionContext ctx) {
		
		String Funcid = ctx.id().getText();
		
		Value type = new Value("");
		
		if (ctx.functionmid().type() != null){
			type = visit(ctx.functionmid().type());
		}
		else 
		{
			type = new Value("nothing");
		}
				
		if(!ctx.functionmid().functionend().params().getText().equals(""))
		{
			String Params[] = ctx.functionmid().functionend().params().getText().split(",");
			ArrayList<ParamsType> ParamsList = new ArrayList<ParamsType>();

			for(String s : Params)
			{
				if(s.substring(0, 3).equals("int"))
				{
					ParamsType CurParam = new ParamsType();
					CurParam.id = s.substring(3, s.length());
					CurParam.type = "1";
					ParamsList.add(CurParam);
				}
				else if (s.substring(0, 6).equals("double"))
				{
					ParamsType CurParam = new ParamsType();
					CurParam.id = s.substring(6, s.length());
					CurParam.type = "3.1";
					ParamsList.add(CurParam);
				}
				else if (s.substring(0, 4).equals("char"))
				{
					ParamsType CurParam = new ParamsType();
					CurParam.id = s.substring(4, s.length());
					CurParam.type = "'s'";
					ParamsList.add(CurParam);
				}
				else if (s.substring(0, 6).equals("string"))
				{
					ParamsType CurParam = new ParamsType();
					CurParam.id = s.substring(6, s.length());
					CurParam.type = "\"string\"";
					ParamsList.add(CurParam);
				}
				else if (s.substring(0, 4).equals("bool"))
				{
					ParamsType CurParam = new ParamsType();
					CurParam.id = s.substring(4, s.length());
					CurParam.type = "true";
					ParamsList.add(CurParam);
				}
				else if (s.substring(0, 9).equals("container"))
				{
					ParamsType CurParam = new ParamsType();
					CurParam.id = s.substring(9, s.length());
					CurParam.type = "container";
					ParamsList.add(CurParam);
				}
				else if (s.substring(0, 5).equals("drink"))
				{
					ParamsType CurParam = new ParamsType();
					CurParam.id = s.substring(5, s.length());
					CurParam.type = "drink";
					ParamsList.add(CurParam);
				}
			}
			Function func = new Function();
			
			func.Name = Funcid;
			func.Params = ParamsList;
			func.ReturnType = type.toString();
			
			FunctionMemory.put(Funcid, func);
		}
		else
		{
			Function func = new Function();
			func.Name = Funcid;
			func.Params = null;
			func.ReturnType = type.toString();
			FunctionMemory.put(Funcid, func);
		}
		
		Value id = visit(ctx.id());
		Value mid = visit(ctx.functionmid());

		
		//FunctionMemory.put(Funcid, ctx.functionmid().functionend().params().getText());
	
		
		
		return new Value(id.toString() + type.toString() + mid.toString());
	}
	
	@Override
	public Value visitId(SPLADParser.IdContext ctx) {
		String id = ctx.getText();
		
		if(VariableMemory.containsKey(id))
		{
			Value val = new Value(VariableMemory.get(id).Value);
			return new Value(VariableMemory.get(id).Value);
		}
		
		
		if(FunctionMemory.containsKey(id))
		{
			Value val = new Value(FunctionMemory.get(id));
			return new Value(FunctionMemory.get(id));
		}
		else
		{
			for(Function f : FunctionMemory.values())
			{
				if(f.Params != null)
				{
					for(ParamsType p : f.Params)
					{
						if(p.id.contains("["))
						{
							p.id = p.id.substring(0, p.id.indexOf('['));
						}
							
						if(p.id.equals(id))
						{
							Value val = new Value(p.type);
							return new Value(p.type);
						}
					}
				}
			}
			
		}
		
		return new Value(id);
	}
	
	@Override
	public Value visitFunctioncall(SPLADParser.FunctioncallContext ctx) {
		Value id = visit(ctx.id());
		Value CallExpr = visit(ctx.callexpr());
		Function cFunc = new Function();
		int RequiredParams = -1;
		
		if(!FunctionMemory.containsKey(id.toString()))
		{
			if(!ErrorFunctions.contains(id.toString()))
			{
				ErrorFunctions.add(id.toString());
				Error err = new Error(17, id.toString());
				ErrList.add(err);
			}
		}
		else
		{
			cFunc = (Function)id.value;
			if(cFunc.Params != null)
			{
				RequiredParams = cFunc.Params.size();
			}
			else
			{
				RequiredParams = 0;
			}
		}
		
		
		int i = 0;
		int location = 0;
		int StringFlag = 0;
		
		char[] callCharArray = CallExpr.toString().toCharArray();
		
		List<Value> Params = new ArrayList<Value>();
		
		for(i = 0; i<= callCharArray.length-1; i++)
		{
			if(callCharArray[i] == '"')
			{
				if(StringFlag == 0)
				{
					StringFlag = 1;
				}
				else
				{
					StringFlag = 0;
					//Params.add(new Value(CallExpr.toString().substring(location, i+1)));
					//location = i+2;
					//i=i+1;
				}
			}
			else if(callCharArray[i] == ',' && StringFlag != 1)
			{
				Params.add(new Value(CallExpr.toString().substring(location, i)));
				location = i+1;
			}
			
			if(i == callCharArray.length-1)
			{
				Params.add(new Value(CallExpr.toString().substring(location, i+1)));
			}
		}
		
		
		
		if(RequiredParams != -1)
		{
			int wrongNumberofParametersFlag = 0;
		
			if(Params.size() > RequiredParams)
			{
				wrongNumberofParametersFlag = 1;
				Error err = new Error(13, id.toString());
				ErrList.add(err);
			}
			else if (Params.size() < RequiredParams)
			{
				wrongNumberofParametersFlag = 1;
				Error err = new Error(14, id.toString());
				ErrList.add(err);
			}
			
			if(wrongNumberofParametersFlag == 0)
			{
				for(i=0; i< RequiredParams; i++)
				{
					if(!new Value(cFunc.Params.get(i).type).isSameType(new Value(Params.get(i))))
					{
						Error err = new Error(15, cFunc.Name + ". Got " + Params.get(i).GetType() + " expecting " + cFunc.Params.get(i).type);
						ErrList.add(err);
					}
				}
			}
		}
		ErrorFunctions.add(id.toString());
	return new Value(-1);
	}
	
	@Override
	public Value visitEndif(SPLADParser.EndifContext ctx) {
		Value nontermelse = new Value("");
		if (ctx.nontermelse() != null){
			
			nontermelse = visit(ctx.nontermelse());
		}
		return nontermelse;
	}
	
	
	@Override
	public Value visitSubcallexprend(SPLADParser.SubcallexprendContext ctx) {
		Value ExprEnd = new Value();
		
		if (ctx.subcallexpr() != null)
		{
			ExprEnd = visit(ctx.subcallexpr());
		}
		
		return new Value("," + ExprEnd.toString());
	}
	
	@Override
	public Value visitSubparamsend(SPLADParser.SubparamsendContext ctx) {
		
		if (ctx.subparams() != null){
			return new Value("," + visit(ctx.subparams()).toString());
		}
		else
		{
			return new Value("");
		}
	}
	
	@Override
	public Value visitNontermif(SPLADParser.NontermifContext ctx) {
		Value Expr = visit(ctx.expr());
		Value Stmts = visit(ctx.block());
		Value Endif = visit(ctx.endif());
		return new Value(Expr.toString() + " " + Stmts.toString() + Endif.toString());
	}
	
	@Override
	public Value visitComparisonoperator(SPLADParser.ComparisonoperatorContext ctx) {
		return new Value(ctx.getText());
	}
	
	@Override
	public Value visitExprend(SPLADParser.ExprendContext ctx) {
		String ReturnString = "";
		
		ReturnString = visit(ctx.exprsymbol()).toString();
		ReturnString += visit(ctx.expr()).toString();
		
		
		return new Value(ReturnString);
	}
	
	@Override
	public Value visitComp(SPLADParser.CompContext ctx) {

		Value val = visit(ctx.addsub());
		Value CompEnd = new Value("");
		String FunctionID = "";
		String CompOperator = "";
		
		if(ctx.compend().children != null)
		{
		    CompEnd = visit(ctx.compend());
		    CompOperator = visit(ctx.compend().comparisonoperator()).toString();
		    
			String[] valArr = val.toString().split("[\\+\\-\\*\\/]");
			String[] endArr = CompEnd.toString().split("[\\+\\-\\*\\/]");
			
			if(CompOperator.equals("=") || CompOperator.equals("!="))
			{
				String ValType = "";
				String CompType = "";
				for(String s : valArr)
				{
					Value nVal = new Value(s);
					if(nVal.isNumeric())
					{
						if(ValType.equals(""))
						{
							ValType = "numeric";
						}
						else if(!ValType.equals("numeric"))
						{
							Error err = new Error(10, val.toString());
							ErrList.add(err);
							break;
						}
					}
					else if(nVal.isBool())
					{
						if(ValType.equals(""))
						{
							ValType = "bool";
						}
						else if(!ValType.equals("bool"))
						{
							Error err = new Error(10, val.toString());
							ErrList.add(err);
							break;
						}
					}
					else if(nVal.isChar())
					{
						if(ValType.equals(""))
						{
							ValType = "char";
						}
						else if(!ValType.equals("char"))
						{
							Error err = new Error(10, val.toString());
							ErrList.add(err);
							break;
						}
					}
					else if(nVal.isString())
					{
						if(ValType.equals(""))
						{
							ValType = "string";
						}
						else if(!ValType.equals("string"))
						{
							Error err = new Error(10, val.toString());
							ErrList.add(err);
							break;
						}
					}
				}
				
				for(String s : endArr)
				{
					Value nComp = new Value(s);
					if(nComp.isNumeric())
					{
						if(CompType.equals(""))
						{
							CompType = "numeric";
						}
						else if(!CompType.equals("numeric"))
						{
							Error err = new Error(10, CompEnd.toString());
							ErrList.add(err);
							break;
						}
					}
					else if(nComp.isBool())
					{
						if(CompType.equals(""))
						{
							CompType = "bool";
						}
						else if(!CompType.equals("bool"))
						{
							Error err = new Error(10, CompEnd.toString());
							ErrList.add(err);
							break;
						}
					}
					else if(nComp.isChar())
					{
						if(CompType.equals(""))
						{
							CompType = "char";
						}
						else if(!CompType.equals("char"))
						{
							Error err = new Error(10, CompEnd.toString());
							ErrList.add(err);
							break;
						}
					}
					else if(nComp.isString())
					{
						if(CompType.equals(""))
						{
							CompType = "string";
						}
						else if(!CompType.equals("string"))
						{
							Error err = new Error(10, CompEnd.toString());
							ErrList.add(err);
							break;
						}
					}
				}
				
				if(!CompType.equals(ValType))
				{
					//FunctionID
					if(!ErrorFunctions.contains(FunctionID) && !FunctionID.equals(""))
					{
						Error err = new Error(9, val.toString() + " " + CompOperator + " " + CompEnd.toString());
						ErrList.add(err);
					}
				}
			}
			else
			{				
				for(String s : valArr)
				{
					Value nVal = new Value(s);
					if(!nVal.isNumeric() && !nVal.toString().equals(""))
					{
						Error err = new Error(8,s);
						ErrList.add(err);
					}
				}
				
				for(String s : endArr)
				{
					Value nVal = new Value(s);
					if(!nVal.isNumeric() && !nVal.toString().equals(""))
					{
						Error err = new Error(8,s);
						ErrList.add(err);
					}
				}
			}
		}
		
		return new Value(val.toString() + CompOperator + CompEnd.toString());
	}
	
	@Override
	public Value visitCases(SPLADParser.CasesContext ctx) {
		Value expr = visit(ctx.expr());
		Value stmts = visit(ctx.stmts());
		Value endcase = visit(ctx.endcase());
		return new Value(expr.toString() + stmts.toString() + endcase.toString());
	}
	
	@Override
	public Value visitNontermelse(SPLADParser.NontermelseContext ctx) {
		Value Nontermif = new Value("");
		Value Stmts = new Value("");
		
		if (ctx.nontermif() != null){
			Nontermif = visit(ctx.nontermif());
		}
		else {
			Stmts = visit(ctx.block());
		}
		return new Value( Nontermif.toString() + Stmts.toString() );
	}
	
	@Override 
	public Value visitAddsub(SPLADParser.AddsubContext ctx) 
	{ 
		Value Muldiv = visit(ctx.muldiv());
		Value AddSubEnd = visit(ctx.addsubend());
		
		return new Value(Muldiv.toString() + AddSubEnd.toString()); 
	}
	
	@Override 
	public Value visitAddsubend(SPLADParser.AddsubendContext ctx) 
	{ 
		if(ctx.plusminus() != null)
		{
			Value Sign = visit(ctx.plusminus());
			Value Num = visit(ctx.addsub());
			
			if(Sign == null)
			{
				Sign = new Value("");
			}
			if(Num == null)
			{
				Num = new Value("");
			}
			
			return new Value(Sign.toString() + Num.toString());
		}
		else
		{
			return new Value("");
		}
	}
	
	@Override 
	public Value visitMuldiv(SPLADParser.MuldivContext ctx) 
	{ 
		Value Factor = visit(ctx.factor());
		Value MulDivEnd = visit(ctx.muldivend());
		
		return new Value(Factor.toString() + MulDivEnd.toString()); 
	}
	
	@Override 
	public Value visitMuldivend(SPLADParser.MuldivendContext ctx) 
	{ 
		if(ctx.children != null)
		{
			 Value TimesDivide = visit(ctx.timesdivide());
			 Value MulDiv = visit(ctx.muldiv());
			 
			 return new Value(TimesDivide.toString() + MulDiv.toString());
		}
		else
		{
			return new Value("");
		}
	}
	
	@Override 
	public Value visitTimesdivide(SPLADParser.TimesdivideContext ctx)
	{ 
		return new Value(ctx.getText()); 
	}
	
	@Override
	public Value visitRoots(SPLADParser.RootsContext ctx) {
		if (ctx.root() != null){
			visit(ctx.root());
			visit(ctx.roots());
		}
		return new Value();
	}
	
	@Override
	public Value visitStmt(SPLADParser.StmtContext ctx) {

		if (ctx.nontermif() != null){
			return visit(ctx.nontermif());
		}
		else if(ctx.assign() != null)
		{			
			Value Assign = visit(ctx.assign());
			String VariableID = Assign.toString().substring(0, Assign.toString().indexOf("<--"));	
			
			if(!VariableMemory.containsKey(VariableID))
			{
				int trigger  = 0;
				for(Function f : FunctionMemory.values())
				{
					if(f.Params != null)
					{
						for(ParamsType p : f.Params)
						{
							if(p.id.contains("["))
							{
								p.id = p.id.substring(0, p.id.indexOf('['));
							}
								
							if(p.id.equals(VariableID))
							{
								trigger = 1;
							}
						}
					}
				}
				
				if(trigger == 0)
				{
					Error err = new Error(5, VariableID);
					ErrList.add(err);
				}
			}
			
			return Assign;
		}
		else if (ctx.nontermwhile() != null){
			return visit(ctx.nontermwhile());
		}
		else if (ctx.from() != null){
			return visit(ctx.from());
		}
		else if (ctx.dcl() != null){
			return visit(ctx.dcl());
		}
		else if (ctx.functioncall() != null){
			return visit(ctx.functioncall());
		}
		else if (ctx.nontermswitch() != null){
			return visit(ctx.nontermswitch());
		}
		else
		{
			return new Value("");
		}
	}
	
	@Override
	public Value visitSubcallexpr(SPLADParser.SubcallexprContext ctx) {
		Value Expr = visit(ctx.expr());
		Value SubCall = new Value("");
		
		if(ctx.subcallexprend().children != null)
		{
			SubCall = visit(ctx.subcallexprend());
		}
		
		return new Value(Expr.toString() + SubCall.toString());
	}
	
	@Override
	public Value visitParams(SPLADParser.ParamsContext ctx) {
		Value Subparams = new Value("");
		
		if (ctx.subparams() != null)
		{
			Subparams = visit(ctx.subparams());
		}
		
		return Subparams;
	}
	
	@Override
	public Value visitFactor(SPLADParser.FactorContext ctx) {
		
		Value val = new Value("");
		
		if(ctx.cast() != null){
			val = visit(ctx.cast());
		}
		else if(ctx.string() != null){
			val = visit(ctx.string());
		}
		else if(ctx.functioncall() != null){
			val = visit(ctx.functioncall());
		}
		else if(ctx.numeric() != null){
			val = visit(ctx.numeric());
		}
		else if(ctx.callid() != null){
			val = visit(ctx.callid());
			
			if(val.toString().equals(ctx.callid().id().getText()))
			{
				Error err = new Error(5, val.toString());
				ErrList.add(err);
			}
		}
		else {
			String[] splitarray = ctx.getText().trim().split(" ");
			
			if(splitarray[0].isEmpty())
			{
				splitarray[0] = "SYNTAXERROR";
			}
			
			if (splitarray[0].equals("LOW")){
				val = new Value("LOW");
			}
			else if (splitarray[0].equals("HIGH")){
				val = new Value("HIGH");
			}
			else if (splitarray[0].equals("true")){
				val = new Value("true");
			}
			else if (splitarray[0].equals("false")){
				val = new Value("false");
			}
			else if (splitarray[0].matches("A[0-5]")){
				val = new Value(splitarray[0]);
			}
			else if (splitarray[0].toCharArray()[0] == '!'){
				String ExpressionString = visit(ctx.expr()).toString();
				val = new Value("!(" + ExpressionString + ")");
			}
			else if (splitarray[0].toCharArray()[0] == '('){
				String ExpressionString = visit(ctx.expr()).toString();
				val = new Value("(" + ExpressionString + ")");
			}
			else{
				val = new Value("Syntax Error");
			}
		}
		
		
		return val;
	}
	
	@Override
	public Value visitFunctionmid(SPLADParser.FunctionmidContext ctx) {
		
		Value FunctionEnd = visit(ctx.functionend());
		Value Expr = new Value("nothing");
		Value Type = new Value("nothing");
		
		if(ctx.type() != null)
		{
			Type = visit(ctx.type());
		}
		
		if(!Type.toString().equals("nothing"))
		{
			Expr = visit(ctx.expr());
		}
		
		
		if(Type.toString().equals("int"))
		{
			if(!Expr.toString().equals("int") && !Expr.isInt())
			{
				Error err = new Error(7, Type.toString());
				ErrList.add(err);
			}
		}
		else if(Type.toString().equals("double"))
		{
			if(!Expr.toString().equals("double") && !Expr.isDouble())
			{
				Error err = new Error(7, Type.toString());
				ErrList.add(err);
			}
		}
		
		return new Value(Expr.toString() + FunctionEnd.toString());
	}
	
	@Override
	public Value visitDcl(SPLADParser.DclContext ctx) {
		Value type = visit(ctx.type());   //double
		Value assign = visit(ctx.assign()); //2+3.1
		boolean isArray = false;
		
		if(ctx.assign().callid() != null)
		{
			if(ctx.assign().callid().arrayidend() != null)
			{
				if(ctx.assign().callid().arrayidend().arraycall() != null)
				{
					isArray = true;
				}
			}
		}
		
		
		String VariableId = assign.toString().substring(0, assign.toString().indexOf("<--"));
		String Expression = assign.toString().substring(assign.toString().indexOf("<--")+3, assign.toString().length());
	
		
		if(Expression.equals(""))
		{
			if(type.toString().equals("string"))
			{
				Expression = "\"string\"";
			}
			else if(type.toString().equals("int"))
			{
				Expression = "1";
			}
			else if(type.toString().equals("double"))
			{
				Expression = "1.0";
			}
			else if(type.toString().equals("char"))
			{
				Expression = "'c'";
			}
			else if(type.toString().equals("bool"))
			{
				Expression = "true";
			}
			else if(type.toString().contains("container"))
			{
				Expression = "container";
			}
		}
		
		
		if(Expression.equals("double") || Expression.equals("int") || Expression.equals("char") || Expression.equals("string")
		|| Expression.equals("bool"))
		{
			if(Expression.equals("double") || Expression.equals("int") && !(type.toString().equals("double") || type.toString().equals("int")))
			{
				Error err = new Error(2, "");
				ErrList.add(err);
			}
			else if(Expression.equals("char") && !(type.toString().equals("char") || type.toString().equals("string")))
			{
				Error err = new Error(2, "");
				ErrList.add(err);
			}
		}
		
		String[] NewArr = Expression.split("[\\+\\-\\*\\/]");
		
		if(type.toString().equals("double") || type.toString().equals("int") || type.toString().equals("container"))
		{
			for(String str : NewArr)
			{
				Value nVal = new Value(str);
				
				if(!nVal.isNumeric() && !str.equals(""))
				{
					Variable Var = VariableMemory.get(str);
					if(Var == null)
					{
						if(str.charAt(0) != '"' && str.charAt(str.length()-1) != '"')
						{
							Error err = new Error(5, str);
							ErrList.add(err);
						}
					}
				}
				
				if(str.matches("\"[A-Za-z]+\""))
				{
					Error err = new Error(6, str);
					ErrList.add(err);
				}
				
				if(type.toString().equals("int") || type.toString().equals("container"))
				{
					if(str.contains("."))
					{
						Error err = new Error(1, str);
						ErrList.add(err);
					}
				}
			}
		}
		
		if(type.toString().equals("string"))
		{
			if(Expression != null && !Expression.equals(""))
			{
				String ExprString = Expression;
				if(ExprString.charAt(0) != '"' || ExprString.charAt(ExprString.length()-1) != '"')
				{
					Error err = new Error(3, assign.toString());
					ErrList.add(err);
				}
			}
		}
		
		Variable Var = new Variable();
		
		String VarType = type.toString();
		if(type.toString().contains("const"))
		{
			Var.constant = true;
			Var.Type = VarType.substring(6);
		}
		else
		{
			Var.Type = type.toString();
		}
		
		Var.Id = VariableId;
		Var.Value = Expression;
		Var.isArray = isArray;
		
		VariableMemory.put(VariableId, Var);
		
		return new Value(type.toString() + "<--" + Expression);
	}
	
	@Override
	public Value visitArrayidend(SPLADParser.ArrayidendContext ctx) 
	{
		Value Size = null;
		
		if(ctx.arraycall() != null)
		{
			Value arraycall = visit(ctx.arraycall());
		}
		if(ctx.expr() != null)
		{
			Size = visit(ctx.expr());
		}
		return Size;
	}
	
	@Override
	public Value visitSubparams(SPLADParser.SubparamsContext ctx) {
		Value Type = visit(ctx.type());
		visit(ctx.callid());
		String Id = ctx.callid().getText();
		Value SupParEnd = visit(ctx.subparamsend());
		
		return new Value(Type.toString() + Id.toString() + SupParEnd.toString());
	}
	
	@Override
	public Value visitPlusminus(SPLADParser.PlusminusContext ctx) {
		Value val = new Value(ctx.getText());
		return val;
	}
	
	@Override
	public Value visitCast(SPLADParser.CastContext ctx) {
		/*
		 * Alt kan castes til en string
		 * numerics kan castes til numerics eller string
		 * 
		 */
		
		Value Type =  visit(ctx.type());
		Value Expr =  visit(ctx.expr());
		
		//Trying to cast to numeric, expr must also be type numeric
		if(Type.toString().equals("double") || Type.toString().equals("int"))
		{
			if(!Expr.isNumeric())
			{
				if(!Expr.toString().equals("int") && !Expr.toString().equals("double"))
				{
					Error err = new Error(16, Expr.toString() + " is not numeric");
					ErrList.add(err);
				}
			}
		}
		else if(Type.toString().equals("bool"))
		{
			if(!Expr.isNumeric())
			{
				Error err = new Error(16, Expr.toString() + " is not numeric");
				ErrList.add(err);
			}
		}
		
		if(Type.toString().equals("string"))
		{
			return new Value("\"string\"");
		}
		else if(Type.toString().equals("int"))
		{
			return new Value(3);
		}
		else if(Type.toString().equals("double"))
		{
			return new Value(3.3);
		}
		else if(Type.toString().equals("bool"))
		{
			return new Value(true);
		}
		else if(Type.toString().equals("char"))
		{
			return new Value('s');
		}
		
	return new Value(Type.toString());
	}
	
	@Override
	public Value visitEndcase(SPLADParser.EndcaseContext ctx) {
		Value cases = new Value();
		Value breakEnd = new Value();
		Value stmts = new Value();
		
		if (ctx.cases() != null)
		{
			cases =	visit(ctx.cases());
		}
		else if (ctx.breakend() != null)
		{
			breakEnd = visit(ctx.breakend());
		}
		else 
		{
		 	stmts = visit(ctx.stmts());
		}
		return new Value(cases.toString() + breakEnd.toString() + stmts.toString());
	}
	
	@Override
	public Value visitTerm(SPLADParser.TermContext ctx) {
		Value val = visit(ctx.comp());
		Value Returnval = new Value("");;
		
			Value Termend = visit(ctx.termend());
			if(Termend != null)
			{
				char Operator = Termend.toString().charAt(0);
				if(Termend.toString().length() > 1)
				{
					Returnval = new Value(val.toString() + Termend.toString());
				}
				else
				{
					Error err = new Error(4, Operator + "");
					ErrList.add(err);
				}
			}
			else
			{
				Returnval = val;
			}
		
		return Returnval;
	}
	
	@Override
	public Value visitCallid(SPLADParser.CallidContext ctx) {
		String CallId = visit(ctx.id()).toString();
		Value ArrayIdx = null;

		ArrayIdx = visit(ctx.arrayidend());
		
		return new Value(CallId);
	}
	
	@Override 
	public Value visitBlock(SPLADParser.BlockContext ctx) 
	{ 
		return visit(ctx.stmts()); 
	}
	
	@Override
	public Value visitProgram(SPLADParser.ProgramContext ctx) {

		PreDefinedFunctions PreFunctions = new PreDefinedFunctions();
		
		for(Function f : PreFunctions.GetPreDefinedFunctions())
		{
			FunctionMemory.put(f.Name, f);
		}
		
		for(Variable v : PreFunctions.PreDefinedVariables())
		{
			VariableMemory.put(v.Id, v);
		}
		
		visit(ctx.roots());
		
		if(!FunctionMemory.containsKey("setup"))
		{
			Error err = new Error(21, "setup");
			ErrList.add(err);
		}
		if(!FunctionMemory.containsKey("loop"))
		{
			Error err = new Error(21, "loop");
			ErrList.add(err);
		}
		
		if(!ErrList.isEmpty())
		{
	        for (Error e : this.ErrList) {
	            System.out.print(e.GetErrorMessage());
	        }
		}
		return null;
	}
	
	@Override
	public Value visitString(SPLADParser.StringContext ctx) {
		
		return new Value( ctx.getText() );
	}
	
	@Override
	public Value visitFunctionend(SPLADParser.FunctionendContext ctx) {
		Value Param = 	visit(ctx.params());
		Value Stmt = visit(ctx.stmts());
		
		return new Value(Param.toString() + Stmt.toString());
	}
	
	@Override
	public Value visitBreakend(SPLADParser.BreakendContext ctx) {
		Value cases = new Value();
		Value stmts = new Value();
		
		if (ctx.cases() != null)
		{
			cases = visit(ctx.cases());
		}
		else if (ctx.stmts() != null)
		{
			stmts = visit(ctx.stmts());
		}
		return new Value(cases.toString() + stmts.toString());
	}
	
	@Override
	public Value visitCompend(SPLADParser.CompendContext ctx) {
		String Comp = "";
		
		if(ctx.getText() != ""){
			Value Operator = visit(ctx.comparisonoperator());
			Comp = visit(ctx.comp()).toString();
		}
		return new Value(Comp);
	}
	
	@Override public Value visitDrinkdcl(SPLADParser.DrinkdclContext ctx) 
	{ 
	    Value ID  = visit(ctx.id(0));
		
		if(ctx.id(1) != null)
		{
			visit(ctx.id(1));
			visit(ctx.changedrinkstmts());
		}
		else
		{
			visit(ctx.drinkstmts());
		}
		
		Variable var = new Variable();
		var.Id = ID.toString();
		var.Type = "drink";
		var.Value = "drink";
		
		VariableMemory.put(ID.toString(), var);
		
		return null; 
	}
	
	@Override public Value visitChangedrinkstmts(SPLADParser.ChangedrinkstmtsContext ctx)
	{ 
		visit(ctx.changedrinkstmt());
		visit(ctx.changedrinkstmtsend());
		return null; 
	}
	
	@Override public Value visitDrinkstmts(SPLADParser.DrinkstmtsContext ctx) 
	{ 
		visit(ctx.drinkstmt());
		visit(ctx.drinkstmtsend());
		
		return null; 
	}
	
	@Override public Value visitDrinkstmt(SPLADParser.DrinkstmtContext ctx) 
	{ 
		visit(ctx.numeric());
		Value id = visit(ctx.id());
		
		if(!VariableMemory.containsKey(ctx.id().getText()))
		{
			Error err = new Error(19, id.toString());
			ErrList.add(err);
		}
		else
		{
			Variable Var = VariableMemory.get(ctx.id().getText());
			if(!Var.Type.equals("container"))
			{
				Error err = new Error(20, Var.Id);
				ErrList.add(err);
			}
		}
		return null; 
	}

	@Override public Value visitDrinkstmtsend(SPLADParser.DrinkstmtsendContext ctx) 
	{ 
		if(ctx.drinkstmts() != null)
		{
			visit(ctx.drinkstmts());
		}
		return null; 
	}
	
	@Override public Value visitChangedrinkstmt(SPLADParser.ChangedrinkstmtContext ctx) 
	{ 
		if(ctx.drinkstmt() != null)
		{
			visit(ctx.drinkstmt());
		}
		else if(ctx.id() != null)
		{
			//We must remove ingredient, we must check if it is a container
			Value Vid = visit(ctx.id());
			
			if(!VariableMemory.containsKey(ctx.id().getText()))
			{
				Error err = new Error(19, ctx.id().getText());
				ErrList.add(err);
			}
			else
			{
				Variable Var = VariableMemory.get(ctx.id().getText());
				if(!Var.Type.equals("container"))
				{
					Error err = new Error(20, Var.Id);
					ErrList.add(err);
				}
			}
		}
		return null; 
	}
	
	@Override public Value visitChangedrinkstmtsend(SPLADParser.ChangedrinkstmtsendContext ctx) 
	{ 
		if(ctx.changedrinkstmts() != null)
		{
			visit(ctx.changedrinkstmts());
		}
		return null;
	}



}
