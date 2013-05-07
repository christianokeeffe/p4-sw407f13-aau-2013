import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.*;


public class test implements SPLADVisitor<Value>{

	public void VisitChildrenType (@NotNull ParseTree arg0){
		if(arg0 instanceof SPLADParser.ProgramContext){
			this.visitProgram((SPLADParser.ProgramContext)arg0);
		}else if (arg0 instanceof SPLADParser.AssignContext){
			this.visitAssign((SPLADParser.AssignContext)arg0);
		}
		
		switch(arg0.getClass().toString()){
		case "class SPLADParser$ProgramContext":
			this.visitProgram((SPLADParser.ProgramContext)arg0);
			break;
		case "class SPLADParser$AssignContext":
			this.visitAssign((SPLADParser.AssignContext)arg0);
			break;
		}
	}
	@Override
	public Value visit(@NotNull ParseTree arg0) {
		for(int i = 0; i < arg0.getChildCount(); i++){
			this.visit(arg0.getChild(i));
			this.VisitChildrenType(arg0.getChild(i));
		}
		return null;
	}

	@Override
	public Value visitChildren(@NotNull RuleNode arg0) {
		
		return null;
	}

	@Override
	public Value visitErrorNode(@NotNull ErrorNode arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitTerminal(@NotNull TerminalNode arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitAssign(SPLADParser.AssignContext ctx) {
		System.out.print("Her er assign");
		return null;
	}

	@Override
	public Value visitNumeric(SPLADParser.NumericContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitNontermswitch(SPLADParser.NontermswitchContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitRoot(SPLADParser.RootContext ctx) {
		System.out.print("hej");
		return null;
	}

	@Override
	public Value visitNontermwhile(SPLADParser.NontermwhileContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitStmts(SPLADParser.StmtsContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitTermend(SPLADParser.TermendContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitNumericend(SPLADParser.NumericendContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitExpr(SPLADParser.ExprContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitFrom(SPLADParser.FromContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitCallexpr(SPLADParser.CallexprContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitType(SPLADParser.TypeContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitFunction(SPLADParser.FunctionContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitId(SPLADParser.IdContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitFunctioncall(SPLADParser.FunctioncallContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitEndif(SPLADParser.EndifContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitArraytype(SPLADParser.ArraytypeContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitSubcallexprend(SPLADParser.SubcallexprendContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitSubparamsend(SPLADParser.SubparamsendContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitNontermif(SPLADParser.NontermifContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitDclend(SPLADParser.DclendContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitComparisonoperator(SPLADParser.ComparisonoperatorContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitExprend(SPLADParser.ExprendContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitComp(SPLADParser.CompContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitCases(SPLADParser.CasesContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitNontermelse(SPLADParser.NontermelseContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitRoots(SPLADParser.RootsContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitStmt(SPLADParser.StmtContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitSubcallexpr(SPLADParser.SubcallexprContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitParams(SPLADParser.ParamsContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitFactor(SPLADParser.FactorContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitFunctionmidt(SPLADParser.FunctionmidtContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitDcl(SPLADParser.DclContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitArrayiden(SPLADParser.ArrayidenContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitSubparams(SPLADParser.SubparamsContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitPlusminus(SPLADParser.PlusminusContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitCast(SPLADParser.CastContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitEndcase(SPLADParser.EndcaseContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitTerm(SPLADParser.TermContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitCallid(SPLADParser.CallidContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitProgram(SPLADParser.ProgramContext ctx) {
		System.out.print("test");
		return null;
	}

	@Override
	public Value visitString(SPLADParser.StringContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitFunctionend(SPLADParser.FunctionendContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitBreakend(SPLADParser.BreakendContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value visitCompend(SPLADParser.CompendContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}
}
