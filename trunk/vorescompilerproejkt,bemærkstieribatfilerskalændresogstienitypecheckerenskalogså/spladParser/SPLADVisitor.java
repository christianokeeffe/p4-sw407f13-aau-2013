// Generated from SPLAD.g4 by ANTLR 4.0
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.Token;

public interface SPLADVisitor<T> extends ParseTreeVisitor<T> {
	T visitAssign(SPLADParser.AssignContext ctx);

	T visitNumeric(SPLADParser.NumericContext ctx);

	T visitNontermswitch(SPLADParser.NontermswitchContext ctx);

	T visitTermsymbol(SPLADParser.TermsymbolContext ctx);

	T visitRoot(SPLADParser.RootContext ctx);

	T visitNontermwhile(SPLADParser.NontermwhileContext ctx);

	T visitTimesdivide(SPLADParser.TimesdivideContext ctx);

	T visitTermend(SPLADParser.TermendContext ctx);

	T visitType(SPLADParser.TypeContext ctx);

	T visitFunction(SPLADParser.FunctionContext ctx);

	T visitEndif(SPLADParser.EndifContext ctx);

	T visitSubcallexprend(SPLADParser.SubcallexprendContext ctx);

	T visitNontermif(SPLADParser.NontermifContext ctx);

	T visitComparisonoperator(SPLADParser.ComparisonoperatorContext ctx);

	T visitPrimitivetype(SPLADParser.PrimitivetypeContext ctx);

	T visitMuldivend(SPLADParser.MuldivendContext ctx);

	T visitRoots(SPLADParser.RootsContext ctx);

	T visitSubcallexpr(SPLADParser.SubcallexprContext ctx);

	T visitSubparams(SPLADParser.SubparamsContext ctx);

	T visitCast(SPLADParser.CastContext ctx);

	T visitCallid(SPLADParser.CallidContext ctx);

	T visitProgram(SPLADParser.ProgramContext ctx);

	T visitFunctionend(SPLADParser.FunctionendContext ctx);

	T visitCompend(SPLADParser.CompendContext ctx);

	T visitPlusminusorempty(SPLADParser.PlusminusoremptyContext ctx);

	T visitNumericend(SPLADParser.NumericendContext ctx);

	T visitStmts(SPLADParser.StmtsContext ctx);

	T visitExpr(SPLADParser.ExprContext ctx);

	T visitFrom(SPLADParser.FromContext ctx);

	T visitCallexpr(SPLADParser.CallexprContext ctx);

	T visitId(SPLADParser.IdContext ctx);

	T visitFunctioncall(SPLADParser.FunctioncallContext ctx);

	T visitArraytype(SPLADParser.ArraytypeContext ctx);

	T visitSubparamsend(SPLADParser.SubparamsendContext ctx);

	T visitExprsymbol(SPLADParser.ExprsymbolContext ctx);

	T visitFunctionmid(SPLADParser.FunctionmidContext ctx);

	T visitMuldiv(SPLADParser.MuldivContext ctx);

	T visitAssignend(SPLADParser.AssignendContext ctx);

	T visitComp(SPLADParser.CompContext ctx);

	T visitExprend(SPLADParser.ExprendContext ctx);

	T visitCases(SPLADParser.CasesContext ctx);

	T visitNontermelse(SPLADParser.NontermelseContext ctx);

	T visitStmt(SPLADParser.StmtContext ctx);

	T visitAddsubend(SPLADParser.AddsubendContext ctx);

	T visitFactor(SPLADParser.FactorContext ctx);

	T visitParams(SPLADParser.ParamsContext ctx);

	T visitDcl(SPLADParser.DclContext ctx);

	T visitAddsub(SPLADParser.AddsubContext ctx);

	T visitArrayiden(SPLADParser.ArrayidenContext ctx);

	T visitPlusminus(SPLADParser.PlusminusContext ctx);

	T visitEndcase(SPLADParser.EndcaseContext ctx);

	T visitTerm(SPLADParser.TermContext ctx);

	T visitString(SPLADParser.StringContext ctx);

	T visitBreakend(SPLADParser.BreakendContext ctx);
}