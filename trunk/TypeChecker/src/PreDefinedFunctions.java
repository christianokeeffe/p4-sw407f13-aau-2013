import java.util.ArrayList;


public class PreDefinedFunctions {
	public ArrayList<Function> GetPreDefinedFunctions()
	{
	//latex start LCDPrint
		ArrayList<Function> Functions = new ArrayList<Function>();
		
		
		Function LCDPrint = new Function();
		
		ParamsType LCDPrintParamsString = new ParamsType();
		ParamsType LCDPrintParamsLine= new ParamsType();
		
		LCDPrintParamsString.id = "x";
		LCDPrintParamsString.type = "\"string\"";
		LCDPrintParamsLine.id = "y";
		LCDPrintParamsLine.type = "3";
		
		ArrayList<ParamsType> LCDPrintParams = new ArrayList<ParamsType>();
		LCDPrintParams.add(LCDPrintParamsString);
		LCDPrintParams.add(LCDPrintParamsLine);
		LCDPrint.Params = LCDPrintParams;
		LCDPrint.Name = "LCDPrint";
		LCDPrint.ReturnType = "nothing";
		Functions.add(LCDPrint);
		
	//latex end
		
		Function LCDClear = new Function();
		LCDClear.Name = "LCDClear";
		LCDClear.ReturnType = "nothing";
		Functions.add(LCDClear);
		
		Function RFIDWrite = new Function();
		ParamsType RFIDWriteParamsIntx = new ParamsType();
		ParamsType RFIDWriteParamsinty = new ParamsType();
		RFIDWriteParamsIntx.id = "x";
		RFIDWriteParamsIntx.type = "3";
		RFIDWriteParamsinty.id = "y";
		RFIDWriteParamsinty.type = "3";
		RFIDWrite.Name = "RFIDWrite";
		RFIDWrite.ReturnType = "bool";
		ArrayList<ParamsType> RFIDWriteParams = new ArrayList<ParamsType>();
		RFIDWriteParams.add(RFIDWriteParamsIntx);
		RFIDWriteParams.add(RFIDWriteParamsinty);
		RFIDWrite.Params = RFIDWriteParams;
		Functions.add(RFIDWrite);
		
		//latex start PourDrink
		Function PourDrink = new Function();
		ParamsType PourDrinkParam = new ParamsType();
		
		PourDrinkParam.id = "x";
		PourDrinkParam.type = "drink";
		PourDrink.Name = "pourDrink";
		PourDrink.ReturnType = "nothing";
		ArrayList<ParamsType> PourDrinkParams = new ArrayList<ParamsType>();
		PourDrinkParams.add(PourDrinkParam);
		PourDrink.Params = PourDrinkParams;
		Functions.add(PourDrink);
		//latex end
				
		return Functions;
	}
	
	public ArrayList<Variable> PreDefinedVariables()
	{
		ArrayList<Variable> Variables = new ArrayList<Variable>();
		//latex start OUTPUT
		Variable Output = new Variable();
		Output.Id = "OUTPUT";
		Output.Type = "int";
		Output.Value = "0";
		Variables.add(Output);
		//latex end
		Variable Input = new Variable();
		Input.Id = "INPUT";
		Input.Type = "int";
		Input.Value = "1";
		Variables.add(Input);
		
		Variable A0 = new Variable();
		A0.Id = "A0";
		A0.Type = "container";
		A0.Value = "A0";
		Variables.add(A0);
		
		Variable A1 = new Variable();
		A1.Id = "A1";
		A1.Type = "container";
		A1.Value = "A1";
		Variables.add(A1);
		
		Variable A2 = new Variable();
		A2.Id = "A2";
		A2.Type = "container";
		A2.Value = "A2";
		Variables.add(A2);
		
		Variable A3 = new Variable();
		A3.Id = "A3";
		A3.Type = "container";
		A3.Value = "A3";
		Variables.add(A3);
		
		Variable A4 = new Variable();
		A4.Id = "A4";
		A4.Type = "container";
		A4.Value = "A4";
		Variables.add(A4);
		
		Variable A5 = new Variable();
		A5.Id = "A5";
		A5.Type = "container";
		A5.Value = "A5";
		Variables.add(A5);
		
		return Variables;
	}
}
