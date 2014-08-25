package webrpn.rpn.validators;

import webrpn.rpn.Operator;
import webrpn.rpn.OperatorValidator;

public class DefaultOperatorValidator implements OperatorValidator 
{
	public boolean validateOperands(double[] operands, Operator operator)
	{
		if (operands == null)
		{
			throw new NullPointerException("operands cannot be null.");
		}

		if (operator == null)
		{
			throw new NullPointerException( "operator cannot be null.");
		}
		
		if (operands.length != operator.getNumberOfOperands())
		{
			throw new IllegalArgumentException(String.format("%s operator requires %h operands.", operator.getName(), operator.getNumberOfOperands()));
		}
		
		return true;
	}
}
