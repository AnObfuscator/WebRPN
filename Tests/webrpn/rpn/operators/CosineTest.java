package webrpn.rpn.operators;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import webrpn.rpn.Operator;
import webrpn.rpn.OperatorValidator;
import webrpn.rpn.operators.Cosine;

public class CosineTest 
{
	@Test
	public void Test_eval_ValidatesAndApplysCosine()
	{
		OperatorValidator mockValidator = Mockito.mock(OperatorValidator.class);
		
		double[] operands = new double[] { 3 };
		
		Operator cosine = new Cosine("cos", mockValidator);
		
		double result = cosine.eval(operands);
		
		Assert.assertEquals(Math.cos(3), result, 0);
		Mockito.verify(mockValidator).validateOperands(operands, cosine);
	}
}
