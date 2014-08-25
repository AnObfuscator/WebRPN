package webrpn.rpn.operators;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import webrpn.rpn.Operator;
import webrpn.rpn.OperatorValidator;
import webrpn.rpn.operators.Subtract;

public class SubtractTest 
{
	@Test
	public void Test_eval_ValidatesAndSubtracts()
	{
		OperatorValidator mockValidator = Mockito.mock(OperatorValidator.class);
		
		double[] operands = new double[] { 3, 5 };
		
		Operator subtract = new Subtract("sub", mockValidator);
		
		double result = subtract.eval(operands);
		
		Assert.assertEquals(-2, result, 0);
		Mockito.verify(mockValidator).validateOperands(operands, subtract);
	}
}
