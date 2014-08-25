package webrpn.rpn.operators;

import webrpn.rpn.Operator;
import webrpn.rpn.OperatorValidator;

public class Power implements Operator 
{
	private final String name;
	private final String[] tokens = new String[] { "^", "pow" };
	private final OperatorValidator validator;
	
	public Power(String name, OperatorValidator validator)
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
		return Math.pow(operands[0], operands[1]);
	}
}
