import java.io.IOException;

import org.antlr.v4.runtime.*;

public class Main{
	
	public static void main(String[] args) {
		String file = "C:/Users/Aleksander/Documents/P4/PrettyPrinter/antlr/HelloWorld.splad";
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
		Prettyprinter tree = new Prettyprinter();
		tree.visit(parser.program());
		//SPLADParser.ProgramContext hej = parser.program();
		//System.out.print(hej.toStringTree(parser));
	}
}
