package webrpn.dependencies;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceServletContextListener;


public class InjectorProvider extends GuiceServletContextListener 
{	
	final Injector injector;
	
	public InjectorProvider(Module... modules)
	{
		this.injector = Guice.createInjector(modules);
	}
	
    @Override
    protected Injector getInjector() 
    {
        return injector;
    }
}