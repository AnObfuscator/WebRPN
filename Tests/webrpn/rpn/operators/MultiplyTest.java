package webrpn.rpn.operators;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import webrpn.rpn.Operator;
import webrpn.rpn.OperatorValidator;
import webrpn.rpn.operators.Multiply;

public class MultiplyTest 
{
	@Test
	public void Test_eval_ValidatesAndMultiplies()
	{
		OperatorValidator mockValidator = Mockito.mock(OperatorValidator.class);
		
		double[] operands = new double[] { 9, 2 };
		
		Operator multiply = new Multiply("mul", mockValidator);
		
		double result = multiply.eval(operands);
		
		Assert.assertEquals(18, result, 0);
		Mockito.verify(mockValidator).validateOperands(operands, multiply);
	}
}
