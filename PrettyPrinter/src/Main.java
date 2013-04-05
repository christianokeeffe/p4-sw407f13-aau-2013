import java.io.IOException;

import org.antlr.v4.runtime.*;

public class Main{
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String file = "C:/Users/Christian/Documents/Arbejde/Eclipse/PrettyPrinter/antlr/HelloWorld.splad";
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
		test tree = new test();
		//tree.visit(parser.program());
		SPLADParser.ProgramContext hej = parser.program();
		System.out.print(hej.toStringTree(parser));
	}
}
