import java.util.ArrayList;
import java.util.List;

public class ScopeRuleChecker extends SPLADBaseVisitor<String>{
	// scope start
	List<List<String>> Scopecontrol = new ArrayList<List<String>>();
	List<String> listOfErrors = new ArrayList<String>();
	int count = 0 ;
	// scopes slut
	
	@Override
	public String visitAssign(SPLADParser.AssignContext ctx) {
		if(ctx.assignend() != null){
			return visit(ctx.callid()) + visit(ctx.assignend());
		}
		else
		{
			return "";
		}
	}
	
	@Override
	public String visitTermsymbol(SPLADParser.TermsymbolContext ctx){
		return "";
	}
	
	@Override
	public String visitExprsymbol(SPLADParser.ExprsymbolContext ctx){
		return "";
	}
	
	@Override
	public String visitNumeric(SPLADParser.NumericContext ctx) {
		return visit(ctx.plusminusorempty()) + ctx.DIGIT().getText() + visit(ctx.numericend());
	}
	
	@Override
	public String visitNontermswitch(SPLADParser.NontermswitchContext ctx) {
		return visit(ctx.expr()) + visit(ctx.cases());
	}
	
	@Override
	public String visitRoot(SPLADParser.RootContext ctx) {
		if(ctx.dcl() != null){
			return visit(ctx.dcl());
		}
		else if (ctx.function() != null){
			return visit(ctx.function());
		}
		else if (ctx.assign() != null)
		{
			return visit(ctx.assign());
		}
		return "";
	}
	
	@Override
	public String visitNontermwhile(SPLADParser.NontermwhileContext ctx) {
		return visit(ctx.expr()) + visit(ctx.block());
	}
	
	@Override
	public String visitStmts(SPLADParser.StmtsContext ctx) {
		if (ctx.stmt() != null){
			return visit(ctx.stmt())+ visit(ctx.stmts());
		}
		return "";
	}
	
	@Override
	public String visitTermend(SPLADParser.TermendContext ctx) {
		if (ctx.term() != null){
			return visit(ctx.termsymbol()) + visit(ctx.term());
		}
		return "";
	}
	
	@Override
	public String visitNumericend(SPLADParser.NumericendContext ctx) {
		if (!ctx.getText().equals("")){
			return ctx.DIGIT().getText();
		}
		return "";
	}
	
	@Override
	public String visitExpr(SPLADParser.ExprContext ctx) {
		return visit(ctx.term()) + visit(ctx.exprend());
	}
	
	@Override
	public String visitFrom(SPLADParser.FromContext ctx) {
		// scope start
		String Temp = "for(" +	visit(ctx.assign()) + "; " + visit(ctx.assign().callid()) + "<=" +
				visit(ctx.expr()) + "; " + visit(ctx.assign().callid()) + "= " + visit(ctx.assign().callid()) +
				" + " + visit(ctx.plusminusorempty()) + ctx.DIGIT().getText() + ")\n";		
		Temp = Temp + visit(ctx.block());
		// scope slut
		return Temp;
	}
	
	@Override
	public String visitCallexpr(SPLADParser.CallexprContext ctx) {
		if (ctx.subcallexpr() != null){
			return visit(ctx.subcallexpr());
		}
		return "";
	}
	
	@Override
	public String visitType(SPLADParser.TypeContext ctx) {
		return visit(ctx.constant()) + visit(ctx.primitivetype()) + " ";
	}
	
	@Override
	public String visitConstant(SPLADParser.ConstantContext ctx) {
		return "";
	}
	
	@Override
	public String visitPrimitivetype(SPLADParser.PrimitivetypeContext ctx){
		return "";
	}
	
	@Override
	public String visitFunction(SPLADParser.FunctionContext ctx) {
		// scope start
		String Temp;
		Temp = "(";
		List<String> Templist = new ArrayList<String>();
		Scopecontrol.add(Templist);
		if(ctx.id().getText().equals("setup")){
			Scopecontrol.remove(Scopecontrol.size()-1);
			return "";
		}
		else if (ctx.id().getText().equals("loop")){
			Scopecontrol.remove(Scopecontrol.size()-1);
			return "";
		}
		else {
			if (ctx.functionmid().type() != null){
				Temp = visit(ctx.functionmid().type()) + visit(ctx.id()) + visit(ctx.functionmid());;
				Scopecontrol.remove(Scopecontrol.size()-1);
			 return Temp;
			}
			else {
				Temp = "void " + visit(ctx.id()) + visit(ctx.functionmid());
				Scopecontrol.remove(Scopecontrol.size()-1);
				return Temp;
			}
		}
	}
	
	@Override
	public String visitId(SPLADParser.IdContext ctx) {
		return ctx.getText();
	}
	
	@Override
	public String visitFunctioncall(SPLADParser.FunctioncallContext ctx) {
		return visit(ctx.id()) + visit(ctx.callexpr());
	}
	
	@Override
	public String visitEndif(SPLADParser.EndifContext ctx) {
		if (ctx.nontermelse() != null){
			return visit(ctx.nontermelse());
		}
		else{
			return "";
		}
	}
	
	@Override
	public String visitSubcallexprend(SPLADParser.SubcallexprendContext ctx) {
		if (ctx.subcallexpr() != null){
			return visit(ctx.subcallexpr());
		}
		else {
			return "";
		}
	}
	
	@Override
	public String visitSubparamsend(SPLADParser.SubparamsendContext ctx) {
		if (ctx.subparams() != null){
			return visit(ctx.subparams());
		}
		else{
			return "";
		}
	}
	
	@Override
	public String visitNontermif(SPLADParser.NontermifContext ctx) {
		return visit(ctx.expr()) +	visit(ctx.block()) + visit(ctx.endif());
	}
	
	@Override
	public String visitAssignend(SPLADParser.AssignendContext ctx) {
		if (ctx.expr() != null) {
		return visit(ctx.expr());
		}
		else {
			return "";
		}
	}
	
	@Override
	public String visitComparisonoperator(SPLADParser.ComparisonoperatorContext ctx) {
		return "";
	}
	
	@Override
	public String visitExprend(SPLADParser.ExprendContext ctx) {
		if (ctx.exprsymbol() != null){
			return visit(ctx.exprsymbol()) + visit(ctx.expr());
		}
		return "";
	}
	
	@Override
	public String visitComp(SPLADParser.CompContext ctx) {
		return visit(ctx.addsub()) + visit(ctx.compend());
	}
	
	@Override
	public String visitAddsub(SPLADParser.AddsubContext ctx){
		return visit(ctx.muldiv()) + visit(ctx.addsubend());
	}
	
	
	
	@Override
	public String visitAddsubend(SPLADParser.AddsubendContext ctx){
		if (ctx.plusminus() != null){
			return visit(ctx.plusminus()) + visit(ctx.addsub());
		}
		else {
			return "";
		}
	}
	
	@Override
	public String visitCases(SPLADParser.CasesContext ctx) {
		return visit(ctx.expr()) + visit(ctx.stmts()) + visit(ctx.endcase());
	}
	
	@Override
	public String visitNontermelse(SPLADParser.NontermelseContext ctx) {
		if (ctx.nontermif() != null){
			return visit(ctx.nontermif());
		}
		else {
			return visit(ctx.block());
		}
	}
	
	@Override
	public String visitRoots(SPLADParser.RootsContext ctx) {
		if (ctx.root() != null){
			return visit(ctx.root()) + visit(ctx.roots());
		}
		else {
			return "";
		}
	}
	
	@Override
	public String visitStmt(SPLADParser.StmtContext ctx) {
		if (ctx.assign() != null){
			return visit(ctx.assign());
		}
		else if (ctx.nontermif() != null){
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
		else {
			return "";
		}
	}
	
	@Override
	public String visitSubcallexpr(SPLADParser.SubcallexprContext ctx) {
		return visit(ctx.expr()) + visit(ctx.subcallexprend());
	}
	
	@Override
	public String visitParams(SPLADParser.ParamsContext ctx) {
		if (ctx.subparams() != null){
			return visit(ctx.subparams());
		}
		else {
			return "";
		}
	}
	
	@Override
	public String visitFactor(SPLADParser.FactorContext ctx) {
		if(ctx.cast() != null){
			return visit(ctx.cast());
		}
		else if(ctx.string() != null){
			return visit(ctx.string());
		}
		else if(ctx.functioncall() != null){
			return visit(ctx.functioncall());
		}
		else if(ctx.numeric() != null){
			return visit(ctx.numeric());
		}
		else if(ctx.callid() != null){
			return visit(ctx.callid());
		}
		else {
			String[] splitarray = ctx.getText().trim().split(" ");
			if (splitarray[0].toCharArray()[0] == '!'){
				return visit(ctx.expr());
			}
			else if (splitarray[0].toCharArray()[0] == '('){
				return visit(ctx.expr());
			}
			else {
				return "";
			}
			
		}
	}
	
	@Override
	public String visitFunctionmid(SPLADParser.FunctionmidContext ctx) {
		if (ctx.type() != null){
			return visit(ctx.functionend()) + visit(ctx.expr()) + ";\n}";
		}
		else {
			return visit(ctx.functionend()) + ";\n}\n";
		}
	}
	
	@Override
	public String visitDcl(SPLADParser.DclContext ctx) {
		// scope start
		String Temp = ctx.assign().callid().id().getText();
		Temp = Temp.replaceAll("\\s","");
		int ScopeRange = Scopecontrol.size()-1 ;
		Scopecontrol.get(ScopeRange).add(Temp);
		// scope slut
		
		return visit(ctx.type()) + visit(ctx.assign());
	}
	
	@Override
	public String visitArrayidend(SPLADParser.ArrayidendContext ctx) {
		if(ctx.arraycall() != null){
			return visit(ctx.arraycall()) + visit(ctx.expr());
		}
		else {
			return "";
		}
	}
	
	@Override
	public String visitArraycall(SPLADParser.ArraycallContext ctx){
		if (ctx.expr() != null){
			return visit(ctx.expr()) + visit(ctx.arraycall());
		}
		else if (ctx.arraycall() != null){
			return visit(ctx.arraycall());
		}
		else {
			return "";
		}
	}
	
	@Override
	public String visitSubparams(SPLADParser.SubparamsContext ctx) {
		//scope start
		String Temp = ctx.callid().id().getText();
		Temp = Temp.replaceAll("\\s","");
		Scopecontrol.get(Scopecontrol.size()-1).add(Temp);
		// scope slut
		return visit(ctx.type()) + visit(ctx.callid()) + visit(ctx.subparamsend());
	}
	
	@Override
	public String visitPlusminus(SPLADParser.PlusminusContext ctx) {
		return "";
	}
	
	@Override
	public String visitCast(SPLADParser.CastContext ctx) {
		return visit(ctx.type()) + visit(ctx.expr());
	}
	
	@Override
	public String visitEndcase(SPLADParser.EndcaseContext ctx) {
		if (ctx.cases() != null){
			return visit(ctx.cases());
		}
		else if (ctx.breakend() != null){
			return visit(ctx.breakend());
		}
		else {
			return visit(ctx.stmts());
		}
	}
	
	@Override
	public String visitTerm(SPLADParser.TermContext ctx) {
		return visit(ctx.comp()) + visit(ctx.termend());
	}
	
	@Override
	public String visitCallid(SPLADParser.CallidContext ctx) {
		// scope start
		String Temp = visit(ctx.id()); //visit(ctx.arrayidend())
		String Temp2 = "";
		Temp = Temp.replaceAll("\\s","");
		//latex start scopecallid
		for(int i = Scopecontrol.size()-1; i >= 0; i--)
		{
			for(int j = Scopecontrol.size()-1; j >= 0; j--)
			{
				Temp2 = Scopecontrol.get(i).get(j).toString();
				if(Temp.equals(Temp2)) //Scopecontrol.get(i).contains(Scopecontrol.get(i).get(j).toString())
				{
					return visit(ctx.id()) + visit(ctx.arrayidend());
					//latex end
				}
			}
		}
		listOfErrors.add("error: variable " + Temp + " can not be accessed ");
		
		return visit(ctx.id()) + visit(ctx.arrayidend());
		// komme med en fejl her !!!
		// scope slut
	}
	
	@Override
	public String visitProgram(SPLADParser.ProgramContext ctx) {
		// Scope start
		List<String> GlobalScope = new ArrayList<String>();
		Scopecontrol.add(GlobalScope);
		// Scope slut
		visit(ctx.roots());
		return "";
		
	}
	
	@Override
	public String visitString(SPLADParser.StringContext ctx) {
		return ctx.getText();
	}
	
	@Override
	public String visitFunctionend(SPLADParser.FunctionendContext ctx) {
		//scope start
		return visit(ctx.params()) + visit(ctx.stmts());
		// scope slut
	}
	
	@Override
	public String visitBreakend(SPLADParser.BreakendContext ctx) {
		if (ctx.cases() != null){
			return visit(ctx.cases());
		}
		else if (ctx.stmts() != null){
			return visit(ctx.stmts());
		}
		else {
			return "";
		}
	}
	
	@Override
	public String visitCompend(SPLADParser.CompendContext ctx) {
		if(ctx.comparisonoperator() != null){
			return visit(ctx.comparisonoperator()) + visit(ctx.comp());
		}
		else {
			return "";
		}
	}
	
	@Override
	public String visitTimesdivide(SPLADParser.TimesdivideContext ctx) {
		return "";
	}
	
	@Override
	public String visitMuldivend(SPLADParser.MuldivendContext ctx) {
		if (ctx.timesdivide() != null){
			return visit(ctx.timesdivide()) + visit(ctx.muldiv());
		}
		else {
			return "";
		}
	}
	
	@Override
	public String visitPlusminusorempty(SPLADParser.PlusminusoremptyContext ctx) {
		if(ctx.plusminus() != null){
			return visit(ctx.plusminus());
		}
		else {
			return "";
		}
	}
	
	@Override // Scope start 
	public String visitBlock(SPLADParser.BlockContext ctx) {
		
		List<String> Templist = new ArrayList<String>();
		Scopecontrol.add(Templist);
		
		String Temp = "{" + visit(ctx.stmts()) + "}";
		
		Scopecontrol.remove(Scopecontrol.size()-1);
		return Temp;
		//scope slut
	}
	
	@Override
	public String visitMuldiv(SPLADParser.MuldivContext ctx) {
		return visit(ctx.factor()) + visit(ctx.muldivend());
	}
}
