package webrpn.rpn.operators;

import webrpn.rpn.Operator;
import webrpn.rpn.OperatorValidator;

public class Cosine implements Operator 
{
	private final String name;
	private final String[] tokens = new String[] { "cos" };
	private final OperatorValidator validator;
	
	public Cosine(String name, OperatorValidator validator)
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
		return 1;
	}

	public double eval(double[] operands) 
	{
		validator.validateOperands(operands, this);
		return Math.cos(operands[0]);
	}
}
