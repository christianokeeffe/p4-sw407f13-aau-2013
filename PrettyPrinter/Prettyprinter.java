public class Prettyprinter extends SPLADBaseVisitor<Value> {
	int tabs = 0;
	
	@Override
	public Value visitAssign(SPLADParser.AssignContext ctx) {
		System.out.print("<-- ");
		visit(ctx.expr());
		return null;
	}
	
	@Override
	public Value visitTermsymbol(SPLADParser.TermsymbolContext ctx){
		System.out.print(ctx.getText());
		return null;
	}
	
	@Override
	public Value visitExprsymbol(SPLADParser.ExprsymbolContext ctx){
		System.out.print(ctx.getText() + " ");
		return null;
	}

	@Override
	public Value visitNumeric(SPLADParser.NumericContext ctx) {
		visit(ctx.plusminus());
		System.out.print(ctx.getText() + " ");
		visit(ctx.numericend());
		return null;
	}

	@Override
	public Value visitNontermswitch(SPLADParser.NontermswitchContext ctx) {
		System.out.print("switch(");
		visit(ctx.expr());
		System.out.print(")\nbegin\n");
		visit(ctx.cases());
		System.out.print("end\n");
		return null;
	}

	@Override
	public Value visitRoot(SPLADParser.RootContext ctx) {
		if(ctx.dcl() != null){
			visit(ctx.dcl());
			System.out.print(";\n");
		}
		else if (ctx.function() != null){
			visit(ctx.function());
		}
		else if (ctx.callid() != null)
		{
			visit(ctx.callid());
			visit(ctx.assign());
			System.out.print(";\n");
		}
		return null;
	}

	@Override
	public Value visitNontermwhile(SPLADParser.NontermwhileContext ctx) {
		System.out.print("while (");
		visit(ctx.expr());
		System.out.print(")\n");
		System.out.print("begin\n");
		visit(ctx.stmts());
		System.out.print("end\n");
		return null;
	}

	@Override
	public Value visitStmts(SPLADParser.StmtsContext ctx) {
		if (ctx.stmt() != null){
			visit(ctx.stmt());
			visit(ctx.stmts());
		}
		return null;
	}

	@Override
	public Value visitTermend(SPLADParser.TermendContext ctx) {
		if (ctx.getText() != ""){
			visit(ctx.termsymbol());
			visit(ctx.term());
		}
		return null;
	}

	@Override
	public Value visitNumericend(SPLADParser.NumericendContext ctx) {
		/*if (ctx.getText() != null){
			System.out.print("." + ctx.getText());
		}*/
		return null;
	}

	@Override
	public Value visitExpr(SPLADParser.ExprContext ctx) {
		visit(ctx.term());
		visit(ctx.exprend());
		return null;
	}

	@Override
	public Value visitFrom(SPLADParser.FromContext ctx) {
		System.out.print("from ");
		visit(ctx.expr(0));
		System.out.print("to ");
		visit(ctx.expr(1));
		System.out.print("step ");
		visit(ctx.callid());
		visit(ctx.assign());
		System.out.print("\nbegin\n");
		visit(ctx.stmts());
		System.out.print("end\n");
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
	public Value visitType(SPLADParser.TypeContext ctx) {
		visit(ctx.primitivetype());
		visit(ctx.arraytype());
		return null;
	}
	
	@Override
	public Value visitPrimitivetype(SPLADParser.PrimitivetypeContext ctx){
		System.out.print(ctx.getText());
		return null;
	}
	
	@Override
	public Value visitFunction(SPLADParser.FunctionContext ctx) {
		System.out.print("function ");
		visit(ctx.id());
		System.out.print("return ");
		visit(ctx.functionmidt());
		return null;
	}

	@Override
	public Value visitId(SPLADParser.IdContext ctx) {
		System.out.print(ctx.getText() + " ");
		return null;
	}

	@Override
	public Value visitFunctioncall(SPLADParser.FunctioncallContext ctx) {
		System.out.print("call ");
		visit(ctx.id());
		System.out.print("(");
		visit(ctx.callexpr());
		System.out.print(")");
		return null;
	}

	@Override
	public Value visitEndif(SPLADParser.EndifContext ctx) {
		if (ctx.nontermelse() != null){
			System.out.print("else\n");
			visit(ctx.nontermelse());
		}
		return null;
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
			System.out.print(", ");
			visit(ctx.subparams());
		}
		return null;
	}

	@Override
	public Value visitNontermif(SPLADParser.NontermifContext ctx) {
		System.out.print("if (");
		visit(ctx.expr());
		System.out.print(")\nbegin\n");
		visit(ctx.stmts());
		System.out.print("end\n");
		visit(ctx.endif());
		return null;
	}

	@Override
	public Value visitDclend(SPLADParser.DclendContext ctx) {
		if(ctx.assign() != null){
			visit(ctx.assign());
		}
		return null;
	}

	@Override
	public Value visitComparisonoperator(SPLADParser.ComparisonoperatorContext ctx) {
		System.out.print(ctx.getText() + " ");
		return null;
	}

	@Override
	public Value visitExprend(SPLADParser.ExprendContext ctx) {
		if (ctx.getText() != ""){
		visit(ctx.exprsymbol());
		visit(ctx.expr());
		}
		return null;
	}

	@Override
	public Value visitComp(SPLADParser.CompContext ctx) {
		visit(ctx.factor());
		visit(ctx.compend());
		return null;
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
		if (ctx.nontermif() != null){
			visit(ctx.nontermif());
		}
		else {
			System.out.print("begin\n");
			visit(ctx.stmts());
			System.out.print("end\n");
		}
		return null;
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
		if (ctx.callid() != null){
			visit(ctx.callid());
			visit(ctx.assign());
			System.out.print(";\n");
		}
		else if (ctx.nontermif() != null){
			visit(ctx.nontermif());
		}
		else if (ctx.nontermwhile() != null){
			visit(ctx.nontermwhile());
		}
		else if (ctx.from() != null){
			visit(ctx.from());
		}
		else if (ctx.dcl() != null){
			visit(ctx.dcl());
			System.out.print(";\n");
		}
		else if (ctx.functioncall() != null){
			visit(ctx.functioncall());
			System.out.print(";\n");
		}
		else if (ctx.nontermswitch() != null){
			visit(ctx.nontermswitch());
		}
		else if (ctx.COMMENT() != null){
			System.out.print(ctx.COMMENT().getText() + " ");
		}
		return null;
	}

	@Override
	public Value visitSubcallexpr(SPLADParser.SubcallexprContext ctx) {
		visit(ctx.expr());
		visit(ctx.subcallexprend());
		return null;
	}

	@Override
	public Value visitParams(SPLADParser.ParamsContext ctx) {
		if (ctx.subparams() != null){
			visit(ctx.subparams());
		}
		return null;
	}

	@Override
	public Value visitFactor(SPLADParser.FactorContext ctx) {
		if(ctx.cast() != null){
			visit(ctx.cast());
		}
		else if(ctx.string() != null){
			visit(ctx.string());
		}
		else if(ctx.functioncall() != null){
			visit(ctx.functioncall());
		}
		else if(ctx.numeric() != null){
			visit(ctx.numeric());
		}
		else if(ctx.callid() != null){
			visit(ctx.callid());
		}
		else {
			String[] splitarray = ctx.getText().trim().split(" ");
			if (splitarray[0] == "LOW"){
				System.out.print("LOW ");
			}
			else if (splitarray[0] == "HIGH"){
				System.out.print("HIGH ");
			}
			else if (splitarray[0] == "true"){
				System.out.print("true ");
			}
			else if (splitarray[0] == "false"){
				System.out.print("false ");
			}
			else if (splitarray[0].toCharArray()[0] == '!'){
				System.out.print("!(");
				visit(ctx.expr());
				System.out.print(")");
			}
			else if (splitarray[0].toCharArray()[0] == '('){
				System.out.print("(");
				visit(ctx.expr());
				System.out.print(")");
			}
			
		}
		return null;
	}

	@Override
	public Value visitFunctionmidt(SPLADParser.FunctionmidtContext ctx) {
		if (ctx.type() != null){
			visit(ctx.type());
			visit(ctx.functionend());
			visit(ctx.expr());
			System.out.print(";\nend");
		}
		else {
			System.out.print("nothing ");
			visit(ctx.functionend());
			System.out.print("nothing;\nend\n");
		}
		return null;
	}

	@Override
	public Value visitDcl(SPLADParser.DclContext ctx) {
		visit(ctx.type());
		visit(ctx.id());
		visit(ctx.dclend());
		return null;
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
		visit(ctx.type());
		visit(ctx.id());
		visit(ctx.subparamsend());
		return null;
	}

	@Override
	public Value visitPlusminus(SPLADParser.PlusminusContext ctx) {
		System.out.print(ctx.getText() + " ");
		return null;
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
		visit(ctx.comp());
		visit(ctx.termend());
		return null;
	}

	@Override
	public Value visitCallid(SPLADParser.CallidContext ctx) {
		visit(ctx.id());
		visit(ctx.arrayiden());
		return null;
	}

	@Override
	public Value visitProgram(SPLADParser.ProgramContext ctx) {
		visit(ctx.roots());
		return null;
	}

	@Override
	public Value visitString(SPLADParser.StringContext ctx) {
		System.out.print(ctx.getText());
		return null;
	}

	@Override
	public Value visitFunctionend(SPLADParser.FunctionendContext ctx) {
		System.out.print("using (");
		visit(ctx.params());
		System.out.print(")\nbegin\n");
		visit(ctx.stmts());
		System.out.print("return ");
		return null;
	}

	@Override
	public Value visitBreakend(SPLADParser.BreakendContext ctx) {
		if (ctx.cases() != null){
			visit(ctx.cases());
		}
		else if (ctx.stmts() != null){
			System.out.print("default:\n");
			visit(ctx.stmts());
			System.out.print("break;\n");
		}
		return null;
	}

	@Override
	public Value visitCompend(SPLADParser.CompendContext ctx) {
		if(ctx.getText() != ""){
			visit(ctx.comparisonoperator());
			visit(ctx.comp());
		}
		return null;
	}
}
