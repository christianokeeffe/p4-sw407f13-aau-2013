import static org.junit.Assert.*;

import org.junit.Test;


public class ValueTest {

	@Test
	public void testValueObject() {
		assertTrue("10 must equals value 10",  (int)new Value(10).value == 10);
		assertTrue("string must equal string",  new Value("string").value.equals("string"));
		assertFalse("string must not equal int",  new Value("string").value.equals(10));
	}

	@Test
	public void testValue() {
		assertTrue("Empty Value is Value with \"\"",  new Value().toString().equals(""));
		assertFalse("Empty Value is not null",  new Value() == null);
		assertTrue("Empty Value is TYPE null", new Value().GetType() == null);
	}

	@Test
	public void testIsDouble() {
	    Value test = new Value(10.0);
	    assertTrue("10.0 must be a double", test.isDouble());
	    assertFalse("10 is not a double", new Value(10).isDouble());
	    assertFalse("string is not a double", new Value("double").isDouble());
	}

	@Test
	public void testIsInt() {
	    Value test = new Value(10);
	    assertTrue("10 must be an int", test.isInt());
	    assertFalse("10.0 is not int", new Value(10.0).isInt());
	    assertFalse("string is not", new Value("int").isInt());
	}

	@Test
	public void testIsDrink() {
	    Value test = new Value("drink");
	    assertTrue("drink must be a drink", test.isDrink());
	    assertFalse("random string is not drink", new Value("random").isDrink());
	    assertFalse("random string is not drink", new Value(55).isDrink());
	}

	@Test
	public void testIsNumeric() {
	    Value test = new Value(10);
	    assertTrue("10 must be a numeric", test.isNumeric());
	    assertTrue("10.0 is also numeric", new Value(10.0).isNumeric());
	    assertFalse("string is not numeric", new Value("string").isNumeric());
	    assertTrue("int is numeric", new Value("int").isNumeric());
	    assertTrue("double is numeric", new Value("double").isNumeric());
	}

	@Test
	public void testIsNumericExpression() {
	    Value test = new Value("10+4-1+4*31-3/12+1.1-4-1");
	    assertTrue("test must be numeric expression", test.isNumericExpression());
	    assertFalse("Following expression must not be numeric expression", new Value("10+3--13+12+4").isNumericExpression());
	    assertFalse("String must not be numeric expression", new Value("string").isNumericExpression());
	    assertTrue("Expression must be numeric expression", new Value("(3+4)-1").isNumericExpression());
	    assertTrue("Expression must be numeric expression", new Value("31+(41-4)*(3+4)-1-(123-4)").isNumericExpression());
	}

	@Test
	public void testIsChar() {
	    Value test = new Value("'c'");
	    assertTrue("c must be a char", test.isChar());
	    assertFalse("string is not a char", new Value("string").isChar());
	    assertFalse("10 is not a char", new Value(10).isChar());
	    assertFalse("a without single qoutes is not char", new Value("a").isChar());
	    assertFalse("string is not char", new Value("abc").isChar());
	    assertFalse("string is not char", new Value("'bc").isChar());
	}

	@Test
	public void testIsString() {
	    Value test = new Value("\"this is my teststring\"");
	    assertTrue("test must be a string", test.isString());
	    assertFalse("10 is not a stirng", new Value(10).isString());
	    assertTrue("\"10\" is a stirng", new Value("\"10\"").isString());
	    assertFalse("\"invalid string is not a valid string", new Value("\"invalid string").isString());
	}

	@Test
	public void testIsBool() {
	    Value test = new Value(true);
	    assertTrue("true must be bool", test.isBool());
	    assertTrue("true must be bool", new Value("true").isBool());
	    assertFalse("10 is not a bool", new Value(10).isBool());
	}

	@Test
	public void testIsContainer() {
		//A container can be A0-A5, and 1-13
	    Value test = new Value("A1");
	    assertTrue("A0 must be a container", test.isContainer());
	    assertTrue("10 must be a container", new Value(10).isContainer());
	    assertFalse("B2 must not be a container", new Value("B2").isContainer());
	    assertFalse("string must not be container", new Value("lol").isContainer());
	    assertTrue("container is container", new Value("container").isContainer());
	}

	@Test
	public void testIsSameType() {
	    Value test = new Value(10);
	    assertTrue("10 must be same type as 10", test.isSameType(new Value(10)));
	    assertTrue("string and string must be same type ", new Value("\"string\"").isSameType(new Value("\"another string\"")));
	    assertFalse("bool and int is not the same type", new Value(true).isSameType(new Value(1)));
	    assertTrue("true is same type as false", new Value(true).isSameType(new Value(false)));
	    assertTrue("char is same type as another char", new Value("'a'").isSameType(new Value("'b'")));
	    assertTrue("10 is numeric, therefore same type as 1.0", new Value(10).isSameType(new Value(1.0)));
	    assertTrue("30 is numeric, therefore same type as 10+3-1-4+3", new Value(30).isSameType(new Value("10+3-1-4+3")));
	    assertTrue("10+10 is numeric, therefore same type as 10-10", new Value(10+10).isSameType(new Value(10-10)));
	    assertTrue("drink is same type as drink", new Value("drink").isSameType(new Value("drink")));
	    assertFalse("char is not the same type as string", new Value("'a'").isSameType(new Value("\"string\"")));
	    assertFalse("10 is not the same type as string", new Value(10).isSameType(new Value("\"string\"")));
	    assertFalse("string is not the same type as int", new Value("\"string\"").isSameType(new Value(10)));
	    assertFalse("drink is not the same type as string", new Value("drink").isSameType(new Value("\"string\"")));
	}
	

	@Test
	public void testGetType() {
	    Value test = new Value(true);
	    //Test bool
	    assertTrue("true must be bool", test.GetType().equals("bool"));
	    assertTrue("bool is type bool", new Value("bool").GetType().equals("bool"));
	    
	    //Test int
	    assertTrue("20 is int", new Value(20).GetType().equals("int"));
	    assertFalse("1.0 is not int", new Value(1.0).GetType().equals("int"));
	    assertTrue("int is type int", new Value("int").GetType().equals("int"));
	    
	    //Test container
	    assertTrue("A1 is type container", new Value("A1").GetType().equals("container"));
	    assertTrue("container is type container", new Value("container").GetType().equals("container"));
	    
	    //Test char
	    assertFalse("string is not char", new Value("\"string\"").GetType().equals("char"));
	    assertTrue("char is type char", new Value("char").GetType().equals("char"));
	    assertTrue("c is type char", new Value("'c'").GetType().equals("char"));
	    
	    //Test string
	    assertTrue("string is type string", new Value("string").GetType().equals("string"));
	    
	    //Test double
	    assertTrue("double is type double", new Value("double").GetType().equals("double"));
	    
	    assertTrue("drink is type drink", new Value("drink").GetType().equals("drink"));
	    
	}

	@Test
	public void testToString() {
		assertTrue("string must be string when cast to string", new Value("string").toString().equals("string"));
		assertFalse("string is not 10 when cast to string", new Value("string").toString().equals(10));
	}

}
