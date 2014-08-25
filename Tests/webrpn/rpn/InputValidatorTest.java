package webrpn.rpn;

import org.junit.Assert;
import org.junit.Test;

import webrpn.rpn.InputResult;
import webrpn.rpn.InputValidator;

public class InputValidatorTest 
{

	@Test(expected=NullPointerException.class)
	public void Test_constructor_NullHelpTokenThrowsException()
	{
		new InputValidator(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void Test_validate_NullInputThrowsException()
	{
		InputValidator validator = new InputValidator("test");
		validator.validate(null);
	}
	
	@Test
	public void Test_validate_EmptyInputReturnsError()
	{
		String[] testInput = new String[] {};
		
		InputValidator validator = new InputValidator("test");
		InputResult result = validator.validate(testInput);
		
		Assert.assertFalse(result.isValidInput());
		Assert.assertFalse(result.isHelpRequest());
		Assert.assertEquals("Empty input.", result.getError());
	}
	
	@Test
	public void Test_validate_WhitespaceInputReturnsError()
	{
		String[] testInput = new String[] { "", " ", "	" };
		
		InputValidator validator = new InputValidator("test");
		InputResult result = validator.validate(testInput);
		
		Assert.assertFalse(result.isValidInput());
		Assert.assertFalse(result.isHelpRequest());
		Assert.assertEquals("Empty input.", result.getError());
	}
	
	@Test
	public void Test_validate_HelpInputReturnsHelpResult()
	{
		String[] testInput = new String[] { "help" };
		
		InputValidator validator = new InputValidator("help");
		InputResult result = validator.validate(testInput);
		
		Assert.assertFalse(result.isValidInput());
		Assert.assertTrue(result.isHelpRequest());
	}
	
	@Test
	public void Test_validate_AnyHelpInputReturnsHelpResult()
	{
		String[] testInput = new String[] { "hElP", "help", "HeLp" };
		
		InputValidator validator = new InputValidator("HELP");
		InputResult result = validator.validate(testInput);
		
		Assert.assertFalse(result.isValidInput());
		Assert.assertTrue(result.isHelpRequest());
	}
	
	@Test
	public void Test_validate_HelpInputAnywhereReturnsHelpResult()
	{
		String[] testInput = new String[] {"3", "4", "help", "+" };
		
		InputValidator validator = new InputValidator("help");
		InputResult result = validator.validate(testInput);
		
		Assert.assertFalse(result.isValidInput());
		Assert.assertTrue(result.isHelpRequest());
	}
	
	@Test
	public void Test_validate_ValidInputReturnsValidResult()
	{
		String[] testInput = new String[] {"3", "4", "+" };
		
		InputValidator validator = new InputValidator("help");
		InputResult result = validator.validate(testInput);
		
		Assert.assertTrue(result.isValidInput());
		Assert.assertFalse(result.isHelpRequest());
		Assert.assertArrayEquals(testInput, result.getValidatedInput());
	}
}
