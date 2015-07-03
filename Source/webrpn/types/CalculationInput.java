package webrpn.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.escape.Escaper;
import com.google.common.html.HtmlEscapers;

public class CalculationInput
{
    private final Escaper html = HtmlEscapers.htmlEscaper();

    private String expression = "";
	
	@JsonProperty
	public String getExpression() 
	{
		return expression;
	}
	public void setExpression(String expression) 
	{
		this.expression = html.escape(expression);
	}
}