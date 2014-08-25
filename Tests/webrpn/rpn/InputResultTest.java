package webrpn.rpn;

import org.junit.Assert;
import org.junit.Test;

import webrpn.rpn.InputResult;

public class InputResultTest 
{
	@Test
	public void Test_ValidatedInput_SetsValidTrue()
	{
		InputResult inputResult = new InputResult();
		
		String[] input = new String[0];
		inputResult.setValidatedInput(input);
		
		Assert.assertArrayEquals(input, inputResult.getValidatedInput());
		Assert.assertTrue(inputResult.isValidInput());
	}
	
	@Test
	public void Test_Error_SetsValidFalse()
	{
		InputResult inputResult = new InputResult();
		
		String error = "test";
		inputResult.setError(error);
		
		Assert.assertEquals(error, inputResult.getError());
		Assert.assertFalse(inputResult.isValidInput());
	}
	
	@Test
	public void Test_HelpResult_SetsValidFalse()
	{
		InputResult inputResult = new InputResult();
		
		inputResult.setIsHelp();
		
		Assert.assertTrue(inputResult.isHelpRequest());
		Assert.assertFalse(inputResult.isValidInput());
	}
}
