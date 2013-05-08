import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;


public class TypeCheckerTest {

	@Test
	public void testVisitNumericNumericContext() {
		
		String file = "C:/SPLADUnitTest/numeric.splad";
		CharStream program = null;
		try 
		{   
			program = new ANTLRFileStream(file);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		SPLADLexer lexer = new SPLADLexer(program);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		SPLADParser parser = new SPLADParser(tokens);
		parser.setBuildParseTree(true);
		TypeChecker tree = new TypeChecker();
		Value val = tree.visit(parser.program().roots().root().dcl().assign().assignend().expr().term().comp().addsub().muldiv().factor().numeric());
	}

}
