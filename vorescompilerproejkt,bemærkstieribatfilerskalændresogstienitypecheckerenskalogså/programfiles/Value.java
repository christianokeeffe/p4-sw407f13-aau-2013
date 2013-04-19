
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

    public boolean isDouble() 
    {
		if(this.toString().matches("\"[A-Za-z]+\""))
		{
			return false;
		}
		if(this.toString().split(".").length > 2)
		{
			return false;
		}
		return true;
    }
    
    public boolean isInt()
    {
		if(this.toString().matches("\"[A-Za-z]+\""))
		{
			return false;
		}
		if(this.toString().contains("."))
		{
			return false;
		}
    	return true;
    }
    
    public boolean isChar()
    {
		if(this.toString().length() > 1)
		{
			return false;
		}
		return true;
    }
    
    public boolean isString()
    {
		if(this.toString().charAt(0) != '"'|| this.toString().charAt(this.toString().length()-1) != '"')
		{
			return false;
		}
		return true;
    }

    @Override
    public boolean equals(Object o) {

        if(value == o) {
            return true;
        }

        if(value == null || o == null || o.getClass() != value.getClass()) {
            return false;
        }

        Value that = (Value)o;

        return this.value.equals(that.value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
