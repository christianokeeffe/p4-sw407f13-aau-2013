import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.antlr.v4.runtime.*;

public class Main{
	
	static String file;
	
	//Function for generating a parse tree
	//latex start generatParseTree
	public final static SPLADParser generatedParseTree(){
		CharStream program = null;
		try {
			program = new ANTLRFileStream(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//latex start MainSyntacticAnalysis
		SPLADLexer lexer = new SPLADLexer(program);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		SPLADParser parser = new SPLADParser(tokens);
		//latex end
		parser.setBuildParseTree(true);
		return parser;
	}
	//latex end
	
	public static void main(String[] args) {
		
		BufferedReader inputreader = new BufferedReader(new InputStreamReader(System.in));
		//Declaring the code generator
		CodeGenrator generator = new CodeGenrator();
		//Declaring the type checker
		TypeChecker TypeCheck = new TypeChecker();
		
		//Find the source code to compile
		System.out.println("Enter the path to the file you want compiled:");
		try {
			file = inputreader.readLine();
		} catch (IOException e2) {
			System.out.println("The system could not read from the console");
			e2.printStackTrace();
		}
		
		//Read the command prompt and stores the input, which we be used for the output file name
		System.out.println("Enter the desired name of the output file");
		
		String fileName ="";
		try {
			fileName = inputreader.readLine() + ".ino";
		} catch (IOException e1) {
			System.out.println("The system could not read from the console");
			e1.printStackTrace();
		}
		
		//Type checks the source code
		TypeCheck.visit(generatedParseTree().program());
		
		PrintWriter toFileWriter;
		try {
			//Generates code and checks the scopes
			String OutputCode = generator.visit(generatedParseTree().program());
			//If no scope errors then the file is created
			if (generator.listOfErrors.isEmpty() && generator.CodeGeneratorErrors.isEmpty()){
				toFileWriter = new PrintWriter(fileName, "UTF-8");
				toFileWriter.print(OutputCode);
				toFileWriter.close();
			}

			for(String scopeerr : generator.listOfErrors)
			{
				System.out.println(scopeerr);
			}
			
			for(String err : generator.CodeGeneratorErrors)
			{
				System.out.println(err);
			}
		} catch (FileNotFoundException e) {
			System.out.println("The file could not be generated");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			System.out.println("The system does not support UTF-8 format");
			e.printStackTrace();
		}
		
		//Prints the type errors and warnings
		if(!TypeCheck.ErrList.isEmpty())
		{
	        for (Error e : TypeCheck.ErrList) {
	            System.out.print(e.GetErrorMessage());
	        }
		}
	}
}
