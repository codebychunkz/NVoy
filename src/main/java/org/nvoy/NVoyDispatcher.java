package org.nvoy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.validation.constraints.NotNull;

import org.apache.wicket.Component;
import org.apache.wicket.IEventDispatcher;
import org.apache.wicket.event.IEvent;
import org.nvoy.cache.ComponentMethodContainer;
import org.nvoy.cache.ConcurrentDispatchRuleCache;
import org.nvoy.cache.IDispatchRuleCache;
import org.nvoy.scanner.ComponentScanner;
import org.nvoy.scanner.IComponentScanner;

public final class NVoyDispatcher implements IEventDispatcher
{
    private final IDispatchRuleCache cache;
    
    private final IComponentScanner scanner;
    
    private NVoyDispatcher(NVoyDispatcherBuilder builder)
    {
	this.cache = builder.cache;
	this.scanner = builder.scanner;
    }

    /**
     * {@inheritDoc}
     */
    public final void dispatchEvent(Object sink, IEvent<?> event, Component component)
    {
	ComponentMethodContainer eventMethods = fetchListeningMethods(component.getClass());
	
	if(eventMethods.hasListeners())
	{
	    fireEvents(event, component, eventMethods.getMethods());
	}
    }

    private final void fireEvents(IEvent<?> event, Component target, Method[] methods)
    {
	Object payload = event.getPayload();
	for (Method method : methods)
	{
	    if (canHandle(payload, method))
	    {
		try
		{
		    method.invoke(target, payload);
		}
		catch (IllegalAccessException | IllegalArgumentException
		        | InvocationTargetException e)
		{
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }
	}
    }

    private boolean canHandle(Object payload, Method method)
    {
	Class<? extends Object> clazz = payload == null ? Null.class : payload.getClass();
	Class<?>[] params = method.getParameterTypes();
	boolean canHandle = false;
	
	//handle multiple arguments in the future...
	if(params.length == 1 && clazz.isAssignableFrom(params[0]))
	{
	    canHandle = true;
	}
	
	return canHandle;
    }

    private final ComponentMethodContainer fetchListeningMethods(Class<? extends Component> clazz)
    {
	ComponentMethodContainer eventMethods = cache.getEventMethods(clazz);
	
	if(eventMethods == null)
	{
	    eventMethods = scanner.seekComponent(clazz);
	    cache.addEventMethod(clazz, eventMethods);
	}
	
	return eventMethods;
    }

    public static final class NVoyDispatcherBuilder
    {
	private IDispatchRuleCache cache = new ConcurrentDispatchRuleCache();
	
	private IComponentScanner scanner = new ComponentScanner();
	
	public NVoyDispatcherBuilder()
	{
	}

	public final NVoyDispatcher build()
	{
	    return new NVoyDispatcher(this);
	}
	
	public final NVoyDispatcherBuilder cache(@NotNull IDispatchRuleCache cache)
	{
	    return this;
	}
	
	public final NVoyDispatcherBuilder scanner(@NotNull IComponentScanner scanner)
	{
	    return this;
	}
    }
}
