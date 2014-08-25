package webrpn.services;

import webrpn.dependencies.AllowedOperators;
import webrpn.rpn.ExpressionParser;
import webrpn.rpn.InputResult;
import webrpn.rpn.InputValidator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CalculatorService 
{
	private final ExpressionParser parser;
	private final InputValidator inputValidator;
	private final String allowedOperators;
	
	@Inject
	public CalculatorService(ExpressionParser parser, InputValidator validator, @AllowedOperators String allowedOperators)
	{
		this.parser = parser;
		this.inputValidator = validator;
		this.allowedOperators = allowedOperators;
	}
	
	public String calculate(String expression) 
	{
		String result = null;
		
		String[] expressionArray = expression.split(" ");
		InputResult inputResult = inputValidator.validate(expressionArray);
    	if (inputResult.isValidInput())
    	{
    		try 
    		{
				String[] input = inputResult.getValidatedInput();
				double calculation = parser.parseExpression(input);
				result = Double.toString(calculation);
			} 
    		catch (Exception e) 
    		{
				result = String.format("Parsing error: %s. Try again or enter 'help' for usage.", e.getMessage());
			}
    	}
		else if (inputResult.isHelpRequest())
		{	
			result = String.format("Allowed operators: %s", allowedOperators);
		}
		else
		{
    		result = String.format("Error reading input: %s. Try again or enter 'help' for usage.", inputResult.getError());
		}
    	
    	return result;
	}

}
