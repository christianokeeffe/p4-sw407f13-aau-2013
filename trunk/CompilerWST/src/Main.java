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
	public final static SPLADParser generatedParseTree(){
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
		CodeGenrator generator = new CodeGenrator();
		TypeChecker TypeCheck = new TypeChecker();
		
		System.out.println("Enter the path to the file you want compiled:");
		try {
			file = inputreader.readLine();
		} catch (IOException e2) {
			System.out.println("The system could not read from the console");
			e2.printStackTrace();
		}
		
		System.out.println("Enter the desired name of the output file");
		
		String fileName ="";
		try {
			fileName = inputreader.readLine() + ".ino";
		} catch (IOException e1) {
			System.out.println("The system could not read from the console");
			e1.printStackTrace();
		}
		
		TypeCheck.visit(generatedParseTree().program());
		
		PrintWriter toFileWriter;
		try {
			toFileWriter = new PrintWriter(fileName, "UTF-16");
			toFileWriter.print(generator.visit(generatedParseTree().program()));
			toFileWriter.close();
		} catch (FileNotFoundException e) {
			System.out.println("The file could not be generated");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			System.out.println("The system does not support UTF-16 format");
			e.printStackTrace();
		}
		
		if(!TypeCheck.ErrList.isEmpty())
		{
	        for (Error e : TypeCheck.ErrList) {
	            System.out.print(e.GetErrorMessage());
	        }
		}
		
		for(int i = 0; i < generator.listOfErrors.size();i++)
		{
			System.out.println(generator.listOfErrors.get(i));
		}
		
	}
}
