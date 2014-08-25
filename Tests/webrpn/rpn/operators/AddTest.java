package webrpn.rpn.operators;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import webrpn.rpn.Operator;
import webrpn.rpn.OperatorValidator;
import webrpn.rpn.operators.Add;

public class AddTest 
{
	@Test
	public void Test_eval_ValidatesAndAdds()
	{
		OperatorValidator mockValidator = Mockito.mock(OperatorValidator.class);
		
		double[] operands = new double[] { 3, 4 };
		
		Operator add = new Add("add", mockValidator);
		
		double result = add.eval(operands);
		
		Assert.assertEquals(7, result, 0);
		Mockito.verify(mockValidator).validateOperands(operands, add);
	}
}
