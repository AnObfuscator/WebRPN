package webrpn.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.escape.Escaper;
import com.google.common.html.HtmlEscapers;

public class CalculationResult 
{
    private final Escaper html = HtmlEscapers.htmlEscaper();

    private String expression;
	private String result;
    private boolean isError;
	
	@JsonProperty
	public String getExpression() 
	{
		return expression;
	}
	public void setExpression(String expression) 
	{
		this.expression = html.escape(expression);
	}
	
	@JsonProperty
	public String getResult() 
	{
		return result;
	}
	public void setResult(String result) 
	{
		this.result = html.escape(result);
	}
    
    @JsonProperty
    public boolean getIsError() 
    { 
        return isError; 
    }
    public void setIsError(boolean isError) 
    {
        this.isError = isError;
    }
}
