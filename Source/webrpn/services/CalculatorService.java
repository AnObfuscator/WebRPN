package webrpn.services;

import webrpn.dependencies.AllowedOperators;
import webrpn.rpn.ExpressionParser;
import webrpn.rpn.InputResult;
import webrpn.rpn.InputValidator;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import webrpn.types.CalculationInput;
import webrpn.types.CalculationResult;

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
	
	public CalculationResult calculate(CalculationInput input)
	{
        String expression = input.getExpression();
        CalculationResult result = new CalculationResult();
        result.setExpression(expression);
		
		String[] expressionArray = expression.split(" ");
		InputResult inputResult = inputValidator.validate(expressionArray);
    	if (inputResult.isValidInput())
    	{
    		try 
    		{
				String[] validatedInput = inputResult.getValidatedInput();
				double calculation = parser.parseExpression(validatedInput);
                String output = Double.toString(calculation);
                result.setResult(output);
                result.setIsError(false);
			} 
    		catch (Exception e) 
    		{
                String output = String.format("Parsing error: %s. Try again or enter 'help' for usage.", e.getMessage());
                result.setResult(output);
                result.setIsError(true);
			}
    	}
		else if (inputResult.isHelpRequest())
		{
            String output = String.format("Allowed operators: %s", allowedOperators);
            result.setResult(output);
            result.setIsError(false);
		}
		else
		{
            String output = String.format("Error reading input: %s. Try again or enter 'help' for usage.", inputResult.getError());
            result.setResult(output);
            result.setIsError(true);
		}

    	return result;
	}

}
