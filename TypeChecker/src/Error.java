
public class Error {
	int ErrorNumber;
	String ErrorBody;

	public Error(int ErrNmbr, String ErrBdy)
	{
	  this.ErrorNumber = ErrNmbr;
	  this.ErrorBody = ErrBdy;
	}
//latex start Error
	public String GetErrorMessage()
	{
		String ErrorMessage;
		
		if(this.ErrorBody.equals("Syntax Error"))
		{
			ErrorMessage = "Syntax Error";
		}
		else
		{
			 switch (this.ErrorNumber) {
	         	case 1:  ErrorMessage = "Value \"" + this.ErrorBody +"\" is not of type int\n";
	         	break;
	         	case 2:  ErrorMessage = "Value \"" + this.ErrorBody +"\" is not of type numeric\n";
	            break;
				//latex end
	         	case 3:  ErrorMessage = "Value \"" + this.ErrorBody +"\" is not of type string\n";
	            break;
	         	case 4:  ErrorMessage = "Dangling operator \"" + this.ErrorBody +"\"\n";
	            break;
	         	case 5:  ErrorMessage = "Variable \"" + this.ErrorBody + "\" is undeclared\n";
	            break;
	         	case 6:  ErrorMessage = "Cannot assign string " + this.ErrorBody + " as numeric\n";
	            break;
	         	case 7:  ErrorMessage = "Return value is not of type " + this.ErrorBody + "\n";
	            break;
	         	case 8:  ErrorMessage = "Argument \"" + this.ErrorBody + "\" is not valid in > or < comparison\n";
	            break;
	         	case 9:  ErrorMessage = "Comparison type mismatch in \"" + this.ErrorBody + "\"\n";
	            break;
	         	case 10:  ErrorMessage = "Type mismatch in \"" + this.ErrorBody + "\"\n";
	            break;
	         	case 11:  ErrorMessage = "Loop condition \"" + this.ErrorBody + "\" not valid\n";
	            break;
	         	case 12:  ErrorMessage = "In from-loop, \"" + this.ErrorBody + "\" must be int\n";
	            break;
	         	case 13:  ErrorMessage = "Too many parameters in function call: \"" + this.ErrorBody + "\"\n";
	            break;
	         	case 14:  ErrorMessage = "Too few parameters in function call: \"" + this.ErrorBody + "\"\n";
	            break;
	         	case 15:  ErrorMessage = "Type mismatch in function " + this.ErrorBody + "\n";
	            break;
	         	case 16:  ErrorMessage = "Invalid cast: " + this.ErrorBody + "\n";
	            break;
	         	case 17:  ErrorMessage = "Warning: Unknown function \"" + this.ErrorBody + "\". Cannot be type-checked.\n";
	            break;
	         	case 18:  ErrorMessage = "Cannot re-assign constant \"" + this.ErrorBody + "\".\n";
	            break;
	         	case 19:  ErrorMessage = "Unknown ingredient \"" + this.ErrorBody + "\".\n";
	            break;
	         	case 20:  ErrorMessage = "Trying use non-container \"" + this.ErrorBody + "\" as container\n";
	            break;
	         	case 21:  ErrorMessage = "Missing required function \"" + this.ErrorBody + "\"\n";
	            break;
	         	default: ErrorMessage = "Error code: -1";
	                  break;
			 }
		}
		
		return ErrorMessage;
	}
}
