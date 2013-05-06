import java.io.IOException;

import org.antlr.v4.runtime.*;

public class Main{
	
	public static void main(String[] args) {
		String file = "C:/Users/Aleksander/Documents/P4/Personlige mapper/Diktatoren/mainprogram3.splad";
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
		CodeGenrator tree = new CodeGenrator();
		ScopeRuleChecker scopechecker = new ScopeRuleChecker();
		scopechecker.visit(parser.program());
		if (scopechecker.listOfErrors.size() != 0){
			file = "C:/Users/Aleksander/Documents/P4/Personlige mapper/Diktatoren/mainprogram3.splad";
			program = null;
			try {
				program = new ANTLRFileStream(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			lexer = new SPLADLexer(program);
			tokens = new CommonTokenStream(lexer);
			parser = new SPLADParser(tokens);
			parser.setBuildParseTree(true);
			System.out.println(tree.visit(parser.program()));
		}
		else {
			for(int i = 0; i < scopechecker.listOfErrors.size();i++)
			{
				System.out.println(scopechecker.listOfErrors.get(i));
			}
		}
		
	}
}
