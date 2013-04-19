
public class Error {
	int ErrorNumber;
	String ErrorBody;

	public Error(int ErrNmbr, String ErrBdy)
	{
	  this.ErrorNumber = ErrNmbr;
	  this.ErrorBody = ErrBdy;
	}

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
	         	case 2:  ErrorMessage = "Value \"" + this.ErrorBody +"\" is not of type double\n";
	            break;
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
	         	default: ErrorMessage = "Error code: -1";
	                  break;
			 }
		}
		
		return ErrorMessage;
	}
}
