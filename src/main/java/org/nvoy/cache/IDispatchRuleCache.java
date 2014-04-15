package org.nvoy.cache;

import javax.annotation.concurrent.ThreadSafe;

import org.apache.wicket.Component;

@ThreadSafe
public interface IDispatchRuleCache
{
    /**
     * Attempt to fetch a {@link ComponentMethodContainer} for a given class if
     * it exists in the cache, otherwise null.
     * 
     * @param clazz
     *            The key
     * @return ComponentMethodContainer for the given class or null
     */
    ComponentMethodContainer getEventMethods(Class<? extends Component> clazz);

    /**
     * Add an {@link ComponentMethodContainer} for a given class.
     * 
     * @param clazz
     *            The key in the cache
     * @param method
     *            The {@link ComponentMethodContainer}
     */
    void addEventMethod(Class<? extends Component> clazz,
	    ComponentMethodContainer method);
}
