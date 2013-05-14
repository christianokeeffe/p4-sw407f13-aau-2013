//latex start Value
public class Value {
	public static Value VOID = new Value(new Object());

    final Object value;

    public Value(Object value) {
        this.value = value;
    }
    
    public Value()
    {
    	this.value = "";
    }
//latex end

//latex start isType
    public boolean isDouble() 
    {
		if(this.toString().matches("((-)?[0-9]+)\\.([0-9]){1,2}"))
		{
			return true;
		}
		return false;
    }
    
    public boolean isInt()
    {
		if(this.toString().matches("(-)?[0-9]+"))
		{
			return true;
		}
		return false;
    }
//latex end    
    public boolean isDrink()
    {
		if(this.toString().equals("drink"))
		{
			return true;
		}
		return false;
    }
    
    public boolean isNumeric()
    {
    	if(this.isInt() || this.isDouble() || this.toString().equals("int") || this.toString().equals("double"))
    	{
    		return true;
    	}
    	return false;
    }
    
    public boolean isNumericExpression()
    {    	
    	String StringToVerify = this.toString();
    	
    	if(StringToVerify.contains("("))
    	{
    		StringToVerify = StringToVerify.replace("(", "");
    	}
    	
    	if(StringToVerify.contains(")"))
    	{
    		StringToVerify = StringToVerify.replace(")", "");
    	}
    	
    	if(StringToVerify.matches("(-)?[0-9]+(\\.[0-9]{1,2})?((\\+|\\-|\\*|\\/)[0-9]+(\\.[0-9]{1,2})?)*"))
    	{
    		return true;
    	}
    	return false;
    }
    
    public boolean isChar()
    {
		if(this.toString().length() != 3 || 
		   this.toString().charAt(0) != '\'' || 
		   this.toString().charAt(this.toString().length()-1) != '\'')
		{
			return false;
		}
		return true;
    }
    
    public boolean isString()
    {
    	if(this.toString().length() < 2)
    	{
    		return false;
    	}
    	else if(this.toString().charAt(0) != '"'|| this.toString().charAt(this.toString().length()-1) != '"')
		{
			return false;
		}
		return true;
    }
    
    public boolean isBool()
    {
    	if(this.toString().equals("true") || this.toString().equals("false"))
    	{
    		return true;
    	}
    	return false;
    }
    
    public boolean isContainer()
    {
    	if(this.toString().matches("((1?[0-3])|([0-9]))|A[0-5]") || this.toString().equals("container"))
    	{
    		return true;
    	}
    	return false;
    }
    
    public boolean isSameType(Value val)
    {
    	if(this.isBool() && val.isBool())
    	{
    		return true;
    	}
    	if(this.isContainer() && val.isContainer())
    	{
    		return true;
    	}
    	else if(this.isChar() && val.isChar())
    	{
    		return true;
    	}
    	else if(this.isNumeric() && val.isNumeric() || this.isNumeric() && val.isNumericExpression())
    	{
    		return true;
    	}
    	else if(this.isString() && val.isString())
    	{
    		return true;
    	}
    	else if(this.isDrink() && val.isDrink())
    	{
    		return true;
    	}
    	
    	return false;
    }
    
    public String GetType()
    {
    	if(this.isBool() || this.toString().equals("bool"))
    	{
    		return "bool";
    	}
    	else if(this.toString().equals("container") || this.isContainer())
    	{
    		return "container";
    	}
    	else if(this.isInt() || this.toString().equals("int"))
    	{
    		return "int";
    	}
    	else if(this.isDouble() || this.toString().equals("double"))
    	{
    		return "double";
    	}
    	else if(this.isChar() || this.toString().equals("char"))
    	{
    		return "char";
    	}
    	else if(this.isString() || this.toString().equals("string"))
    	{
    		return "string";
    	}
    	else if(this.toString().equals("drink"))
    	{
    		return "drink";
    	}
    	else
    	{
    		return null;
    	}
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
