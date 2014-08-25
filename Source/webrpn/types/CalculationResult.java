package webrpn.types;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CalculationResult 
{
	private String expression;
	private String result;
	
	@JsonProperty
	public String getExpression() 
	{
		return expression;
	}
	public void setExpression(String expression) 
	{
		this.expression = expression;
	}
	
	@JsonProperty
	public String getResult() 
	{
		return result;
	}
	public void setResult(String result) 
	{
		this.result = result;
	}
}
