package webrpn.types;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CalculationRequest 
{
	private String expression = "";
	
	@JsonProperty
	public String getExpression() 
	{
		return expression;
	}
	public void setExpression(String expression) 
	{
		this.expression = expression;
	}
}