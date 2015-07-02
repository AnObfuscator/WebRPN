package webrpn.services;

import java.io.PrintStream;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import webrpn.rpn.ExpressionParser;
import webrpn.rpn.InputResult;
import webrpn.rpn.InputValidator;

public class CalculatorServiceTest
{
	ExpressionParser mockParser;
	InputValidator mockInputValidator;
	PrintStream mockPrintStream;
	
	@Before
	public void setup()
	{
		mockParser = Mockito.mock(ExpressionParser.class);
		mockInputValidator = Mockito.mock(InputValidator.class);
		mockPrintStream = Mockito.mock(PrintStream.class);
	}
	
	@Test
	public void Test_process_ValidResultWritesOutput()
	{
		InputResult inputResult = new InputResult();
		inputResult.setValidatedInput(new String[0]);
		
		Mockito.doReturn(inputResult).when(mockInputValidator).validate(Mockito.any(String[].class));
		Mockito.doReturn(42.0).when(mockParser).parseExpression(Mockito.any(String[].class));

		CalculatorService calculator = new CalculatorService(mockParser, mockInputValidator, "");

		String result = calculator.calculate("test");
		
		Mockito.verify(mockInputValidator, Mockito.times(1)).validate(Mockito.any(String[].class));
		Mockito.verify(mockParser, Mockito.times(1)).parseExpression(Mockito.any(String[].class));

        Assert.assertEquals("42.0", result);
	}
	
	@Test
	public void Test_process_ValidResultHandlesParseException()
	{
		InputResult inputResult = new InputResult();
		inputResult.setValidatedInput(new String[0]);
		
		Mockito.doReturn(inputResult).when(mockInputValidator).validate(Mockito.any(String[].class));
		Mockito.doThrow(new RuntimeException("test error msg")).when(mockParser).parseExpression(Mockito.any(String[].class));
		
        CalculatorService calculator = new CalculatorService(mockParser, mockInputValidator, "");

        String result = calculator.calculate("test");
		
		Mockito.verify(mockInputValidator, Mockito.times(1)).validate(Mockito.any(String[].class));
		Mockito.verify(mockParser, Mockito.times(1)).parseExpression(Mockito.any(String[].class));

        Assert.assertEquals("Parsing error: test error msg. Try again or enter 'help' for usage.", result);
	}
	
	@Test
	public void Test_process_HelpResultWritesHelpMessage()
	{
		InputResult inputResult = new InputResult();
		inputResult.setIsHelp();
		
		Mockito.doReturn(inputResult).when(mockInputValidator).validate(Mockito.any(String[].class));

        CalculatorService calculator = new CalculatorService(mockParser, mockInputValidator, "sample ops");

        String result = calculator.calculate("test usage");
		
		Mockito.verify(mockInputValidator, Mockito.times(1)).validate(Mockito.any(String[].class));
		Mockito.verify(mockParser, Mockito.never()).parseExpression(Mockito.any(String[].class));

		Assert.assertEquals("Allowed operators: sample ops", result);
	}

	@Test
	public void Test_process_InvalidResultWritesErrorMessage()
	{
		InputResult inputResult = new InputResult();
		inputResult.setError("test error msg");
		
		Mockito.doReturn(inputResult).when(mockInputValidator).validate(Mockito.any(String[].class));

        CalculatorService calculator = new CalculatorService(mockParser, mockInputValidator, "");

        String result = calculator.calculate("test");
		
		Mockito.verify(mockInputValidator, Mockito.times(1)).validate(Mockito.any(String[].class));
		Mockito.verify(mockParser, Mockito.never()).parseExpression(Mockito.any(String[].class));
        
        Assert.assertEquals("Error reading input: test error msg. Try again or enter 'help' for usage.", result);
	}
}
