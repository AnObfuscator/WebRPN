package webrpn.rpn;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import webrpn.rpn.ExpressionParser;
import webrpn.rpn.Operator;

public class ExpressionParserTest 
{
	Operator mockOp1;
	Operator mockOp2;
	Operator mockOp3;
	
	@Before
	public void setup()
	{
		mockOp1 = Mockito.mock(Operator.class);
		mockOp2 = Mockito.mock(Operator.class);
		mockOp3 = Mockito.mock(Operator.class);
	}
	
	@Test(expected=NullPointerException.class)
	public void Test_parseExpression_NullInputThrowsException()
	{
		Map<String, Operator> operators = new HashMap<>();
		operators.put("ta", mockOp1);
		
		ExpressionParser parser = new ExpressionParser(operators);
		
		parser.parseExpression(null);
	}
	
	@Test(expected=IllegalStateException.class)
	public void Test_parseExpression_EmptyInputThrowsException()
	{
		Map<String, Operator> operators = new HashMap<>();
		operators.put("ta", mockOp1);
		
		ExpressionParser parser = new ExpressionParser(operators);
		
		parser.parseExpression(new String[0]);
	}
	
	@Test(expected=NullPointerException.class)
	public void Test_parseExpression_InputOfNullsThrowsException()
	{
		Map<String, Operator> operators = new HashMap<>();
		operators.put("ta", mockOp1);
		
		ExpressionParser parser = new ExpressionParser(operators);
		
		parser.parseExpression(new String[] {null, null, null});
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void Test_parseExpression_UnknownTokenThrowsException()
	{
		Map<String, Operator> operators = new HashMap<>();
		operators.put("ta", mockOp1);
		
		String[] input = new String[] { "3", "4" , "wrong" };
		
		ExpressionParser parser = new ExpressionParser(operators);
		
		parser.parseExpression(input);
	}
	
	@Test
	public void Test_parseExpression_GetsNumOpsAndCallsEval()
	{
		Map<String, Operator> operators = new HashMap<>();
		operators.put("ta", mockOp1);
		
		Mockito.doReturn(2).when(mockOp1).getNumberOfOperands();
		
		ArgumentCaptor<double[]> operandsCaptor = ArgumentCaptor.forClass(double[].class);
		Mockito.doReturn(100.0).when(mockOp1).eval(operandsCaptor.capture());
		
		String[] input = new String[] { "3", "4" , "ta" };
		
		ExpressionParser parser = new ExpressionParser(operators);
		
		double result = parser.parseExpression(input);
		double[] operands = operandsCaptor.getValue();
		
		Assert.assertEquals(100, result, 0);
		Assert.assertEquals(3, operands[0], 0);
		Assert.assertEquals(4, operands[1], 0);
		
		Mockito.verify(mockOp1, Mockito.times(1)).getNumberOfOperands();
		Mockito.verify(mockOp1, Mockito.times(1)).eval(Mockito.any(double[].class));
	}
	
	@Test
	public void Test_parseExpression_GetsNumOpsAndCallsEvalForMultipleOps()
	{
		Map<String, Operator> operators = new HashMap<>();
		operators.put("ta", mockOp1);
		operators.put("tb", mockOp2);
		operators.put("tc", mockOp3);
		
		Mockito.doReturn(2).when(mockOp1).getNumberOfOperands();
		Mockito.doReturn(2).when(mockOp2).getNumberOfOperands();
		Mockito.doReturn(3).when(mockOp3).getNumberOfOperands();
		
		ArgumentCaptor<double[]> operands1Captor = ArgumentCaptor.forClass(double[].class);
		Mockito.doReturn(100.0).when(mockOp1).eval(operands1Captor.capture());
		
		ArgumentCaptor<double[]> operands2Captor = ArgumentCaptor.forClass(double[].class);
		Mockito.doReturn(200.0).when(mockOp2).eval(operands2Captor.capture());
		
		ArgumentCaptor<double[]> operands3Captor = ArgumentCaptor.forClass(double[].class);
		Mockito.doReturn(300.0).when(mockOp3).eval(operands3Captor.capture());
		
		String[] input = new String[] {"2", "3", "4" , "ta", "5", "6", "tb", "tc" };
		
		ExpressionParser parser = new ExpressionParser(operators);
		
		double result = parser.parseExpression(input);
		
		Assert.assertEquals(300, result, 0);
		
		double[] operands1 = operands1Captor.getValue();
		Assert.assertEquals(3, operands1[0], 0);
		Assert.assertEquals(4, operands1[1], 0);
		
		double[] operands2 = operands2Captor.getValue();
		Assert.assertEquals(5, operands2[0], 0);
		Assert.assertEquals(6, operands2[1], 0);
		
		double[] operands3 = operands3Captor.getValue();
		Assert.assertEquals(  2, operands3[0], 0);
		Assert.assertEquals(100, operands3[1], 0);
		Assert.assertEquals(200, operands3[2], 0);
		
		Mockito.verify(mockOp1, Mockito.times(1)).getNumberOfOperands();
		Mockito.verify(mockOp1, Mockito.times(1)).eval(Mockito.any(double[].class));
		
		Mockito.verify(mockOp2, Mockito.times(1)).getNumberOfOperands();
		Mockito.verify(mockOp2, Mockito.times(1)).eval(Mockito.any(double[].class));
		
		Mockito.verify(mockOp3, Mockito.times(1)).getNumberOfOperands();
		Mockito.verify(mockOp3, Mockito.times(1)).eval(Mockito.any(double[].class));
	}
	
	@Test(expected=EmptyStackException.class)
	public void Test_parseExpression_ArgsGreaterThanStackSizeThrowsException()
	{
		Map<String, Operator> operators = new HashMap<String, Operator>();
		operators.put("ta", mockOp1);
		
		Mockito.doReturn(3).when(mockOp1).getNumberOfOperands();
		
		String[] input = new String[] { "3", "4" , "ta" };
		
		ExpressionParser parser = new ExpressionParser(operators);
		
		parser.parseExpression(input);
	}
	
}
