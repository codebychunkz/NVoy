package org.nvoy.scanner;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.wicket.Component;
import org.nvoy.cache.ComponentMethodContainer;
import org.nvoy.marker.EventListener;

public final class ComponentScanner implements IComponentScanner
{
    /**
     * {@inheritDoc}
     */
    @Override
    public final ComponentMethodContainer seekComponent(Class<? extends Component> comp)
    {
	Method[] methods = comp.getMethods();
	methods = extractListeners(methods);
	
	return new ComponentMethodContainer(methods);
    }

    private final Method[] extractListeners(Method[] methods)
    {
	ArrayList<Method> temp = new ArrayList<>();
	for(Method method : methods)
	{
	    if(method.isAnnotationPresent(EventListener.class))
	    {
		temp.add(method);
	    }
	}
	
	return temp.toArray(new Method[0]);
    }
}
