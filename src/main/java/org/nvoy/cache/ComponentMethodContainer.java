package org.nvoy.cache;

import java.lang.reflect.Method;

import org.nvoy.marker.EventListener;

public final class ComponentMethodContainer
{
    private final Method[] methods;

    private final boolean isListener;

    public ComponentMethodContainer(Method[] methods)
    {
	this.methods = methods;
	this.isListener = methods == null ? false : methods.length != 0;
    }

    /**
     * Whether the component has listeners or not.
     * 
     * @return true if the component has listeners, false otherwise
     */
    public final boolean hasListeners()
    {
	return isListener;
    }

    /**
     * Returns all the methods annotated with {@link EventListener}
     * 
     * @return An array with listening methods
     */
    public final Method[] getMethods()
    {
	return methods;
    }
}
