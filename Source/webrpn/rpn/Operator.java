package webrpn.rpn;

public interface Operator 
{
	String getName();
	
	String[] getTokens();
	
	int getNumberOfOperands();
	
	double eval(double[] operands);
}
