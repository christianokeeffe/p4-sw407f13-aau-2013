import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.antlr.v4.runtime.*;

public class Main{
	
	static String file;
	
	//latex start generatParseTree
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
	//latex end
	
	public static void main(String[] args) {

		BufferedReader inputreader = new BufferedReader(new InputStreamReader(System.in));
		CodeGenrator tree = new CodeGenrator();
		ScopeRuleChecker scopechecker = new ScopeRuleChecker();
		System.out.println("Enter the path to the file you want compiled:");
		try {
			file = inputreader.readLine();
		} catch (IOException e2) {
			System.out.println("The system could not read from the console");
			e2.printStackTrace();
		}
		scopechecker.visit(generatParseTree().program());
		if (scopechecker.listOfErrors.size() == 0){
			
			System.out.println("Enter the desired name of the output file");
			
			String fileName ="";
			try {
				fileName = inputreader.readLine() + ".ino";
			} catch (IOException e1) {
				System.out.println("The system could not read from the console");
				e1.printStackTrace();
			}
			
			PrintWriter toFileWriter;
			try {
				toFileWriter = new PrintWriter(fileName, "UTF-16");
				toFileWriter.print(tree.visit(generatParseTree().program()));
				toFileWriter.close();
			} catch (FileNotFoundException e) {
				System.out.println("The file could not be generated");
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				System.out.println("The system does not support UTF-16 format");
				e.printStackTrace();
			}
			
			
		}
		else {
			for(int i = 0; i < scopechecker.listOfErrors.size();i++)
			{
				System.out.println(scopechecker.listOfErrors.get(i));
			}
		}
		
	}
}
