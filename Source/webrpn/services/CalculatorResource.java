package webrpn.services;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import webrpn.types.CalculationRequest;
import webrpn.types.CalculationResult;

import com.google.common.escape.Escaper;
import com.google.common.html.HtmlEscapers;

import java.util.ArrayList;
import java.util.List;

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
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CalculationResult calculate(CalculationRequest input, @Context HttpServletRequest request)
	{		
		Escaper html = HtmlEscapers.htmlEscaper();
		
        String expression = input.getExpression();
        String escapedExpression = html.escape(expression);
		
		String result = calculatorService.calculate(escapedExpression);
        String escapedResult = html.escape(result);

        CalculationResult response = new CalculationResult();
        response.setExpression(escapedExpression);
        response.setResult(escapedResult);
        
        HttpSession session = request.getSession();
        Object history = session.getAttribute("history");
        if (history == null) {
            history = new ArrayList<CalculationResult>();
            session.setAttribute("history", history);
        }
        ((List<CalculationResult>)history).add(0, response);
        
		return response;
	}
    
    @GET
    @Path("history")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CalculationResult> history(@Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object history = session.getAttribute("history");
        if (history == null) {
            return new ArrayList<>();
        }
        return (List<CalculationResult>)history;
    }
	
}
