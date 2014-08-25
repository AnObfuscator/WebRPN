package webrpn.rpn;

import java.util.Map;
import java.util.Stack;

import webrpn.dependencies.Operators;
import webrpn.rpn.Operator;

import com.google.inject.Inject;

public class ExpressionParser 
{
	private final Map<String, Operator> operators;
	
	@Inject
	public ExpressionParser(@Operators Map<String, Operator> operators)
	{
		this.operators = operators;
	}
	
	public double parseExpression(String[] input)
	{
		Stack<Double> stack = new Stack<Double>();
		
		for(String token : input)
		{	
			Double value = tryParse(token);
			
			if (value != null)
			{
				stack.push(value);
			}
			else if (operators.containsKey(token))
			{
				Operator operator = operators.get(token);
				
				int numberOfOperands = operator.getNumberOfOperands();
				double[] operands = new double[numberOfOperands];
				
				for (int i=numberOfOperands-1; i>=0; i--)
				{
					operands[i] = stack.pop();
				}
				
				double result = operator.eval(operands);
				
				stack.push(result);
			}
			else
			{
				throw new IllegalArgumentException(String.format("Unknown token: %s", token));
			}
		}
		
		if (stack.size() != 1)
		{
			throw new IllegalStateException("Error: Operator/operand missmatch.");
		}
		
		return stack.pop();
	}

	private Double tryParse(String token) 
	{
		Double result = null;
		
		// This is kind of horrific. I'm sorry.
		try 
		{
			result = Double.parseDouble(token);
		}
		catch (NumberFormatException e)
		{
			// explicit do nothing
		}

		return result;
	}
}
