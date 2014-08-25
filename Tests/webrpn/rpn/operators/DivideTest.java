package webrpn.rpn.operators;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import webrpn.rpn.Operator;
import webrpn.rpn.OperatorValidator;
import webrpn.rpn.operators.Divide;

public class DivideTest 
{
	@Test
	public void Test_eval_ValidatesAndDivides()
	{
		OperatorValidator mockValidator = Mockito.mock(OperatorValidator.class);
		
		double[] operands = new double[] { 3, 2 };
		
		Operator divide = new Divide("div", mockValidator);
		
		double result = divide.eval(operands);
		
		Assert.assertEquals(3.0/2, result, 0);
		Mockito.verify(mockValidator).validateOperands(operands, divide);
	}
	
	@Test
	public void Test_eval_DivideByZeroReturnsInfinity()
	{
		OperatorValidator mockValidator = Mockito.mock(OperatorValidator.class);
		
		double[] operands = new double[] { 3, 0 };
		
		Operator divide = new Divide("div", mockValidator);
		
		double result = divide.eval(operands);
		
		Assert.assertTrue(Double.isInfinite(result));
		Mockito.verify(mockValidator).validateOperands(operands, divide);
	}
}
