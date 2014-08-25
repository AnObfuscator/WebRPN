package webrpn.rpn.operators;

import webrpn.rpn.Operator;
import webrpn.rpn.OperatorValidator;

public class Divide implements Operator 
{
	private final String name;
	private final String[] tokens = new String[] { "/", "div" };
	private final OperatorValidator validator;
	
	public Divide(String name, OperatorValidator validator)
	{
		this.name = name;
		this.validator = validator;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public String[] getTokens() 
	{
		return tokens;
	}

	public int getNumberOfOperands() 
	{
		return 2;
	}

	public double eval(double[] operands) 
	{
		validator.validateOperands(operands, this);
		return operands[0] / operands[1];
	}
}
