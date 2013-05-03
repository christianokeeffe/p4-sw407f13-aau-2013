import java.io.IOException;

import org.antlr.v4.runtime.*;

public class Main{
	
	public static void main(String[] args) {
		String file = "C:/ting/mainprogram.splad";
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
		System.out.println(tree.visit(parser.program()));
		
		for(int i = 0; i < tree.fejltest.size();i++)
		{
			System.out.println(tree.fejltest.get(i));
		}
	}
}
