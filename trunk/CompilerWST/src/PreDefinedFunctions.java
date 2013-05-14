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
		
		Function delay = new Function();
		ParamsType delayParam = new ParamsType();
		
		delayParam.id = "x";
		delayParam.type = "int";
		delay.Name = "delay";
		delay.ReturnType = "nothing";
		ArrayList<ParamsType> delayParams = new ArrayList<ParamsType>();
		delayParams.add(delayParam);
		delay.Params = delayParams;
		Functions.add(delay);
		
		Function digitalread = new Function();
		ParamsType digitalreadParam = new ParamsType();
		
		digitalreadParam.id = "x";
		digitalreadParam.type = "container";
		digitalread.Name = "digitalRead";
		digitalread.ReturnType = "int";
		ArrayList<ParamsType> digitalreadParams = new ArrayList<ParamsType>();
		digitalreadParams.add(digitalreadParam);
		digitalread.Params = digitalreadParams;
		Functions.add(digitalread);
		
		Function digitalwrite = new Function();
		ParamsType digitalwriteParam = new ParamsType();
		
		digitalwriteParam.id = "x";
		digitalwriteParam.type = "container";
		digitalwrite.Name = "digitalWrite";
		digitalwrite.ReturnType = "nothing";
		ArrayList<ParamsType> digitalwriteParams = new ArrayList<ParamsType>();
		digitalwriteParams.add(digitalwriteParam);
		ParamsType digitalwriteParam2 = new ParamsType();
		digitalwriteParam2.id = "y";
		digitalwriteParam2.type = "int";
		digitalwriteParams.add(digitalwriteParam2);
		digitalwrite.Params = digitalwriteParams;
		Functions.add(digitalwrite);
		
		Function pinmode = new Function();
		ParamsType pinmodeParam = new ParamsType();
		
		pinmodeParam.id = "x";
		pinmodeParam.type = "container";
		pinmode.Name = "pinMode";
		pinmode.ReturnType = "nothing";
		ArrayList<ParamsType> pinmodeParams = new ArrayList<ParamsType>();
		pinmodeParams.add(pinmodeParam);
		ParamsType pinmodeParam2 = new ParamsType();
		pinmodeParam2.id = "y";
		pinmodeParam2.type = "int";
		pinmodeParams.add(pinmodeParam2);
		pinmode.Params = pinmodeParams;
		Functions.add(pinmode);
				
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
		
		Variable HIGH = new Variable();
		HIGH.Id = "HIGH";
		HIGH.Type = "int";
		HIGH.Value = "1";
		Variables.add(HIGH);
		
		Variable LOW = new Variable();
		LOW.Id = "LOW";
		LOW.Type = "int";
		LOW.Value = "0";
		Variables.add(LOW);
		
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
