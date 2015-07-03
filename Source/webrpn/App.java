package webrpn;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import webrpn.dependencies.InjectorProvider;
import webrpn.dependencies.ServiceBindings;
import webrpn.rpn.Operator;
import webrpn.rpn.OperatorValidator;
import webrpn.rpn.operators.Add;
import webrpn.rpn.operators.Cosine;
import webrpn.rpn.operators.Divide;
import webrpn.rpn.operators.Multiply;
import webrpn.rpn.operators.Power;
import webrpn.rpn.operators.Subtract;
import webrpn.rpn.validators.DefaultOperatorValidator;

import com.google.inject.servlet.GuiceFilter;

public class App 
{
	static final String HelpToken = "help";
	
	static void addOperator(Map<String, Operator> operators, Operator operator)
	{
		for(String token : operator.getTokens())
		{
			operators.put(token, operator);
		}
	}

	static Map<String, Operator> loadOperators()
	{
		OperatorValidator defaultValidator = new DefaultOperatorValidator();
		
		Map<String, Operator> operators = new HashMap<>();
		
		addOperator(operators, new Add("Add", defaultValidator));
		addOperator(operators, new Cosine("Cosine", defaultValidator));
		addOperator(operators, new Subtract("Subtract", defaultValidator));
		addOperator(operators, new Multiply("Multiply", defaultValidator));
		addOperator(operators, new Divide("Divide", defaultValidator));
		addOperator(operators, new Power("Power", defaultValidator));
		
		return operators;
	}
	
    public static void main(String[] args) throws Exception
    {
    	System.out.println("Building operators...");
    	Map<String, Operator> operators = loadOperators();
    	
        System.out.println("\nBinding services...");
        ServiceBindings dependencyBindings = new ServiceBindings(operators, HelpToken);
        InjectorProvider injectorProvider = new InjectorProvider(dependencyBindings);
        
        Server server = new Server(8080);
        ServletContextHandler servletContext = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);

        servletContext.addEventListener(injectorProvider);
        servletContext.addFilter(GuiceFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
                
        System.out.println("\nSetting static resources...");
        servletContext.setResourceBase("./Web");
        servletContext.setWelcomeFiles(new String[] {"index.html"});
        
        System.out.println("\nStarting server.");
        server.start();
    }
}