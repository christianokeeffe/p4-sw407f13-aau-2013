import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeGenrator extends SPLADBaseVisitor<Value>{
	
	List<Error> ErrList = new ArrayList<Error>();
	Map<String, Value> VariableMemory = new HashMap<String, Value>();
	Map<String, String> FunctionMemory = new HashMap<String, String>();
	
	@Override
	public Value visitAssign(SPLADParser.AssignContext ctx) {

		String id = ctx.callid().getText();
		Value val = visit(ctx.assignend());
		VariableMemory.put(id,  val);
		
		return new Value(val.toString());
	}
	
	@Override
	public Value visitAssignend(SPLADParser.AssignendContext ctx)
	{		
		Value val;
		
		if(ctx.expr() != null)
		{
			val = visit(ctx.expr());
		}
		else
		{
			val = null;
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
		//System.out.print("switch(");
		visit(ctx.expr());
		//System.out.print(")\n{\n");
		visit(ctx.cases());
		//System.out.print("}\n");
		return null;
	}
	
	@Override
	public Value visitRoot(SPLADParser.RootContext ctx) {
		if(ctx.dcl() != null){
			//visit(ctx.dcl());
			System.out.print(visit(ctx.dcl()).toString());
		}
		else if (ctx.function() != null){
			visit(ctx.function());
		}
		return null;
	}
	
	@Override
	public Value visitNontermwhile(SPLADParser.NontermwhileContext ctx) {
		//System.out.print("while (");
		visit(ctx.expr());
		//System.out.print(")\n");
		//System.out.print("{\n");
		visit(ctx.stmts());
		//System.out.print("}\n");
		return null;
	}
	
	@Override
	public Value visitStmts(SPLADParser.StmtsContext ctx) {
		Value stmt = new Value("");
		Value stmts = new Value("");
		
		if (ctx.stmt() != null){
			stmt = visit(ctx.stmt());
			stmts = visit(ctx.stmts());
		}
		return new Value(stmt.toString() + stmts.toString());
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
			//System.out.print("." + ctx.DIGIT().getText());
		}
		return null;
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
		
		char[] ExprArr = Expr.toCharArray();
		
		int i  = 0;
		int location = 0;
		int ArCounter = 0;
		/*
		String[] Expressions = new String[];
		for(i=0; i<= ExprArr.length-1; i++)
		{
			if(ExprArr[i] == '<' || ExprArr[i] == '>' || ExprArr[i] == '!')
			{
				if(ExprArr[i+1] == '=')
				{
					Expressions[ArCounter] = Expr.substring(location, i-1);
					ArCounter++; 
					location = i;
				}
				else
				{ 
					Expressions[ArCounter] = Expr.substring(location, i);
					ArCounter++; 
					location = i;
				}
			}
			
			if(ExprArr[i] == '=' && ExprArr[i-1] != '!' && ExprArr[i-1] != '>' && ExprArr[i-1] != '<')
			{
				Expressions[ArCounter] = Expr.substring(location, i);
				ArCounter++;
				location = i;
			}
		}
		
		
		String[] TestArray = Expressions;
		
		 */
		int adw = 3+3;
		return new Value( Expr );
	}
	
	@Override
	public Value visitFrom(SPLADParser.FromContext ctx) {
		//System.out.print("for(");
		visit(ctx.assign());
		//System.out.print("; ");
		//System.out.print("<=");
		visit(ctx.expr());
		//System.out.print("; ");
		//System.out.print("= ");
		//System.out.print(" + ");
		visit(ctx.plusminusorempty());
		//System.out.print(ctx.DIGIT().getText());
		//System.out.print(")\n{\n");
		visit(ctx.stmts());
		//System.out.print("}\n");
		return null;
	}
	
	@Override
	public Value visitCallexpr(SPLADParser.CallexprContext ctx) {
		if (ctx.subcallexpr() != null){
			visit(ctx.subcallexpr());
		}
		return null;
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
	
	
	@Override
	public Value visitType(SPLADParser.TypeContext ctx) {
		
		Value val = visit(ctx.primitivetype());
		
		if(ctx.arraytype().children != null)
		{
			visit(ctx.arraytype());
		}
		
		return val;
	}
	
	@Override
	public Value visitPrimitivetype(SPLADParser.PrimitivetypeContext ctx){
		
		Value val = new Value(ctx.getText());

		return val;
	}
	
	@Override
	public Value visitFunction(SPLADParser.FunctionContext ctx) {
		
		String Funcid = ctx.id().getText();
		
		FunctionMemory.put(Funcid, ctx.functionmid().functionend().params().getText());
		
		Value id = visit(ctx.id());
		Value mid = visit(ctx.functionmid());
		Value type = new Value("");
		
		if (ctx.functionmid().type() != null){
			type = visit(ctx.functionmid().type());
		}
		else 
		{
			type = new Value("nothing");
		}
		
		
		return new Value(id.toString() + type.toString() + mid.toString());
	}
	
	@Override
	public Value visitId(SPLADParser.IdContext ctx) {
		String id = ctx.getText();
		
		if(VariableMemory.containsKey(id))
		{
			return VariableMemory.get(id);
		}
		else if(FunctionMemory.containsKey(id))
		{
			return new Value(FunctionMemory.get(id));
		}
		else
		{
			Error err = new Error(5, id);
			ErrList.add(err);
			return new Value("");
		}
	}
	
	@Override
	public Value visitFunctioncall(SPLADParser.FunctioncallContext ctx) {
		visit(ctx.id());
		System.out.print("(");
		visit(ctx.callexpr());
		System.out.print(")");
		return null;
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
	public Value visitArraytype(SPLADParser.ArraytypeContext ctx) {
		System.out.print(ctx.getText() + " ");
		return null;
	}
	
	@Override
	public Value visitSubcallexprend(SPLADParser.SubcallexprendContext ctx) {
		if (ctx.subcallexpr() != null){
			System.out.print(", ");
			visit(ctx.subcallexpr());
		}
		return null;
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
		Value Stmts = visit(ctx.stmts());
		Value Endif = visit(ctx.endif());
		return new Value(Expr.toString() + Stmts.toString() + Endif.toString());
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
		
		String ReturnString = "";
		
		if(ctx.compend().children != null)
		{
			ReturnString = visit(ctx.compend()).toString();
		}
		
		
		
		return new Value(val.toString() + ReturnString);
	}
	
	@Override
	public Value visitCases(SPLADParser.CasesContext ctx) {
		System.out.print("case ");
		visit(ctx.expr());
		System.out.print(":\n");
		visit(ctx.stmts());
		visit(ctx.endcase());
		return null;
	}
	
	@Override
	public Value visitNontermelse(SPLADParser.NontermelseContext ctx) {
		Value Nontermif = new Value("");
		Value Stmts = new Value("");
		
		if (ctx.nontermif() != null){
			Nontermif = visit(ctx.nontermif());
		}
		else {
			Stmts = visit(ctx.stmts());
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
		return new Value(visit(ctx.factor()).toString() + visit(ctx.muldivend()).toString()); 
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
		return null;
	}
	
	@Override
	public Value visitStmt(SPLADParser.StmtContext ctx) {

		if (ctx.nontermif() != null){
			return visit(ctx.nontermif());
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
		visit(ctx.expr());
		visit(ctx.subcallexprend());
		return null;
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
		}
		else {
			String[] splitarray = ctx.getText().trim().split(" ");
			
			if(splitarray[0].isEmpty())
			{
				splitarray[0] = "SYNTAXERROR";
			}
			
			if (splitarray[0] == "LOW"){
				val = new Value("LOW");
			}
			else if (splitarray[0] == "HIGH"){
				val = new Value("HIGH");
			}
			else if (splitarray[0] == "true"){
				val = new Value("true");
			}
			else if (splitarray[0] == "false"){
				val = new Value("false");
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
		
		if (ctx.type() != null)
		{
			Expr = visit(ctx.expr());
		}
		
		if(ctx.type().toString().equals("int"))
		{
			if(!Expr.isInt())
			{
				Error err = new Error(7, ctx.type().toString());
				ErrList.add(err);
			}
		}
		else if(ctx.type().toString().equals("double"))
		{
			if(!Expr.isDouble())
			{
				Error err = new Error(7, ctx.type().toString());
				ErrList.add(err);
			}
		}
		
		return new Value(Expr.toString() + FunctionEnd.toString());
	}
	
	@Override
	public Value visitDcl(SPLADParser.DclContext ctx) {
		Value type = visit(ctx.type());   //double
		
		Value expr = visit(ctx.assign()); //2+3.1
		
		String Expression = expr.toString();
		
		
		String[] NewArr = Expression.split("[\\+\\-\\*\\/]");
		
		if(type.toString().equals("double") || type.toString().equals("int"))
		{
			for(String str : NewArr)
			{
				if(str.matches("\"[A-Za-z]+\""))
				{
					Error err = new Error(6, str);
					ErrList.add(err);
				}
				if(type.toString().equals("int"))
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
			if(expr != null)
			{
				String ExprString = expr.toString();
				if(ExprString.charAt(0) != '"' || ExprString.charAt(ExprString.length()-1) != '"')
				{
					Error err = new Error(3, expr.toString());
					ErrList.add(err);
				}
			}
			else
			{
				Error err = new Error(-1, "");
				ErrList.add(err);
			}
		}
		return new Value(type.toString() + "<--" + expr.toString());
	}
	
	@Override
	public Value visitArrayiden(SPLADParser.ArrayidenContext ctx) {
		if(ctx != null){
			System.out.print(ctx.getText() + " ");
		}
		return null;
	}
	
	@Override
	public Value visitSubparams(SPLADParser.SubparamsContext ctx) {
		Value Type = visit(ctx.type());
		Value Id = visit(ctx.id());
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
		visit(ctx.type());
		System.out.print("(");
		visit(ctx.expr());
		System.out.print(") ");
		return null;
	}
	
	@Override
	public Value visitEndcase(SPLADParser.EndcaseContext ctx) {
		if (ctx.cases() != null){
			visit(ctx.cases());
		}
		else if (ctx.breakend() != null){
			System.out.print("break;\n");
			visit(ctx.breakend());
		}
		else {
			System.out.print("default:\n");
			visit(ctx.stmts());
			System.out.print("break;\n");
		}
		return null;
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
		Value ArrayIdx = visit(ctx.arrayiden());
		
		if(ArrayIdx != null)
		{
			return new Value(CallId + ArrayIdx.toString());
		}
		else
		{
			return new Value(CallId);
		}
	}
	
	@Override
	public Value visitProgram(SPLADParser.ProgramContext ctx) {
		PrintContentofFile("C:\\test\\header.txt");
		visit(ctx.roots());
		
		
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
		if (ctx.cases() != null){
			visit(ctx.cases());
		}
		else if (ctx.stmts() != null){
			//System.out.print("default:\n");
			visit(ctx.stmts());
			//System.out.print("break;\n");
		}
		return null;
	}
	
	@Override
	public Value visitCompend(SPLADParser.CompendContext ctx) {
		String Operator = "";
		String Comp = "";
		
		if(ctx.getText() != ""){
			Operator = visit(ctx.comparisonoperator()).toString();
			Comp = visit(ctx.comp()).toString();
		}
		return new Value(Operator + Comp);
	}
	
	private void PrintContentofFile(String path){
		BufferedReader in;
		try{
			in = new BufferedReader(new FileReader(path));
			while (in.ready()){
				String fileintxt = in.readLine();
				//System.out.print(fileintxt+"\n");
			}
			in.close();
		}
		catch (FileNotFoundException error){
			/*file not found do something*/
		}
		catch (IOException IOerror){
			/*some error do something*/
		}
	}
}
