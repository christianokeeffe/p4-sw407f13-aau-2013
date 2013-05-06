import java.io.IOException;

import org.antlr.v4.runtime.*;

public class Main{
	
	static String file = "C:/Users/Aleksander/Documents/P4/Personlige mapper/Diktatoren/mainprogram3.splad";
	
	public final static SPLADParser generatParseTree(){
		CharStream program = null;
		try {
			program = new ANTLRFileStream(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		SPLADLexer lexer = new SPLADLexer(program);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		SPLADParser parser = new SPLADParser(tokens);
		parser.setBuildParseTree(true);
		return parser;
	}
	
	public static void main(String[] args) {

		CodeGenrator tree = new CodeGenrator();
		ScopeRuleChecker scopechecker = new ScopeRuleChecker();
		scopechecker.visit(generatParseTree().program());
		if (scopechecker.listOfErrors.size() != 0){

			System.out.println(tree.visit(generatParseTree().program()));
		}
		else {
			for(int i = 0; i < scopechecker.listOfErrors.size();i++)
			{
				System.out.println(scopechecker.listOfErrors.get(i));
			}
		}
		
	}
}
