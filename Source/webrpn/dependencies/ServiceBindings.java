package webrpn.dependencies;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.servlet.DefaultServlet;

import webrpn.rpn.ExpressionParser;
import webrpn.rpn.InputValidator;
import webrpn.rpn.Operator;
import webrpn.services.CalculatorResource;
import webrpn.services.CalculatorService;

import com.google.inject.TypeLiteral;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class ServiceBindings extends ServletModule 
{	
	final Map<String, Operator> operators;
	final String allowedOperators;
	final String helpToken;
	
    public ServiceBindings(Map<String, Operator> operators, String helpToken) 
    {
		this.operators = operators;
		this.helpToken = helpToken;
		this.allowedOperators = allowedOperators();
	}

	@Override
    protected void configureServlets() 
    {	
    	bind(CalculatorResource.class);
    	bind(CalculatorService.class);
    	
    	bindConstant().annotatedWith(HelpToken.class).to(helpToken);
    	bindConstant().annotatedWith(AllowedOperators.class).to(allowedOperators);
    	bind(new TypeLiteral<Map<String, Operator>>() {}).annotatedWith(Operators.class).toInstance(operators);
    	
    	bind(ExpressionParser.class);
    	bind(InputValidator.class);
        
        Map<String, String> initParams = new HashMap<>();
        initParams.put("com.sun.jersey.config.feature.Trace", "true");
        
        filter("/calculator/*").through(GuiceContainer.class, initParams);	
        
        DefaultServlet defaultServlet = new DefaultServlet();
        serve("*.html", "*.js", "*.css").with(defaultServlet);
    }
	
	private String allowedOperators()
	{
		StringBuilder allowedOperatorBuilder = new StringBuilder();
		for(String token : operators.keySet())
		{
			allowedOperatorBuilder.append(token);
			allowedOperatorBuilder.append(" ");
		}
		return allowedOperatorBuilder.toString();
	}
}
