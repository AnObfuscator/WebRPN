package webrpn.rpn;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import webrpn.rpn.Operator;
import webrpn.rpn.OperatorValidator;
import webrpn.rpn.validators.DefaultOperatorValidator;

public class DefaultOperatorValidatorTest 
{
	Operator mockOperator;
	
	@Before
	public void setup()
	{
		mockOperator = Mockito.mock(Operator.class);
	}
	
	@Test(expected=NullPointerException.class)
	public void Test_validateOperands_NullOperandsThrowsException()
	{
		OperatorValidator validator = new DefaultOperatorValidator();
		
		validator.validateOperands(null, mockOperator);
	}
	
	@Test(expected=NullPointerException.class)
	public void Test_validateOperands_NullOperatorThrowsException()
	{
		double[] operands = new double[3];
		Mockito.doReturn(3).when(mockOperator).getNumberOfOperands();
		
		OperatorValidator validator = new DefaultOperatorValidator();
		
		validator.validateOperands(operands, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void Test_validateOperands_OperandsSizeMismatchThrowsException()
	{
		double[] operands = new double[3];
		Mockito.doReturn(2).when(mockOperator).getNumberOfOperands();
		
		OperatorValidator validator = new DefaultOperatorValidator();
		
		validator.validateOperands(operands, mockOperator);
	}
	
	@Test
	public void Test_validateOperands_OperandsSizeMatchPasses()
	{
		double[] operands = new double[3];
		Mockito.doReturn(3).when(mockOperator).getNumberOfOperands();
		
		OperatorValidator validator = new DefaultOperatorValidator();
		
		boolean result = validator.validateOperands(operands, mockOperator);
		
		Assert.assertTrue(result);
		Mockito.verify(mockOperator, Mockito.times(1)).getNumberOfOperands();
	}
}
