package webrpn.rpn.operators;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import webrpn.rpn.Operator;
import webrpn.rpn.OperatorValidator;
import webrpn.rpn.operators.Power;

public class PowerTest 
{
	@Test
	public void Test_eval_ValidatesAndRaisesToPower()
	{
		OperatorValidator mockValidator = Mockito.mock(OperatorValidator.class);
		
		double[] operands = new double[] { 5, 7 };
		
		Operator power = new Power("pow", mockValidator);
		
		double result = power.eval(operands);
		
		Assert.assertEquals(Math.pow(5, 7), result, 0);
		Mockito.verify(mockValidator).validateOperands(operands, power);
	}
}
