package webrpn.services;

import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import webrpn.types.CalculationResult;

import com.google.common.escape.Escaper;
import com.google.common.html.HtmlEscapers;

@Path("calculator")
public class CalculatorResource 
{
	
	final CalculatorService calculatorService;
	
	@Inject
	public CalculatorResource(CalculatorService calculatorService)
	{
		this.calculatorService = calculatorService;
	}
	
	@POST
	@Path("calculate")
	@Produces(MediaType.APPLICATION_JSON)
	public CalculationResult calculate(@FormParam("expression") String expression)
	{		
		CalculationResult response = new CalculationResult();
		Escaper html = HtmlEscapers.htmlEscaper();
		
		response.setExpression(html.escape(expression));
		String result = calculatorService.calculate(expression);
		response.setResult(html.escape(result));

		return response;
	}
	
}
