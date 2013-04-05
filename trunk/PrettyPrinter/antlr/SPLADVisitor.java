// Generated from SPLAD.g4 by ANTLR 4.0
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.Token;

public interface SPLADVisitor<T> extends ParseTreeVisitor<T> {
	T visitAssign(SPLADParser.AssignContext ctx);

	T visitNumeric(SPLADParser.NumericContext ctx);

	T visitNontermswitch(SPLADParser.NontermswitchContext ctx);

	T visitRoot(SPLADParser.RootContext ctx);

	T visitNontermwhile(SPLADParser.NontermwhileContext ctx);

	T visitStmts(SPLADParser.StmtsContext ctx);

	T visitTermend(SPLADParser.TermendContext ctx);

	T visitNumericend(SPLADParser.NumericendContext ctx);

	T visitExpr(SPLADParser.ExprContext ctx);

	T visitFrom(SPLADParser.FromContext ctx);

	T visitCallexpr(SPLADParser.CallexprContext ctx);

	T visitType(SPLADParser.TypeContext ctx);

	T visitFunction(SPLADParser.FunctionContext ctx);

	T visitId(SPLADParser.IdContext ctx);

	T visitFunctioncall(SPLADParser.FunctioncallContext ctx);

	T visitEndif(SPLADParser.EndifContext ctx);

	T visitArraytype(SPLADParser.ArraytypeContext ctx);

	T visitSubcallexprend(SPLADParser.SubcallexprendContext ctx);

	T visitSubparamsend(SPLADParser.SubparamsendContext ctx);

	T visitNontermif(SPLADParser.NontermifContext ctx);

	T visitDclend(SPLADParser.DclendContext ctx);

	T visitComparisonoperator(SPLADParser.ComparisonoperatorContext ctx);

	T visitExprend(SPLADParser.ExprendContext ctx);

	T visitComp(SPLADParser.CompContext ctx);

	T visitCases(SPLADParser.CasesContext ctx);

	T visitNontermelse(SPLADParser.NontermelseContext ctx);

	T visitRoots(SPLADParser.RootsContext ctx);

	T visitStmt(SPLADParser.StmtContext ctx);

	T visitSubcallexpr(SPLADParser.SubcallexprContext ctx);

	T visitParams(SPLADParser.ParamsContext ctx);

	T visitFactor(SPLADParser.FactorContext ctx);

	T visitFunctionmidt(SPLADParser.FunctionmidtContext ctx);

	T visitDcl(SPLADParser.DclContext ctx);

	T visitArrayiden(SPLADParser.ArrayidenContext ctx);

	T visitSubparams(SPLADParser.SubparamsContext ctx);

	T visitPlusminus(SPLADParser.PlusminusContext ctx);

	T visitCast(SPLADParser.CastContext ctx);

	T visitEndcase(SPLADParser.EndcaseContext ctx);

	T visitTerm(SPLADParser.TermContext ctx);

	T visitCallid(SPLADParser.CallidContext ctx);

	T visitProgram(SPLADParser.ProgramContext ctx);

	T visitString(SPLADParser.StringContext ctx);

	T visitFunctionend(SPLADParser.FunctionendContext ctx);

	T visitBreakend(SPLADParser.BreakendContext ctx);

	T visitCompend(SPLADParser.CompendContext ctx);
}