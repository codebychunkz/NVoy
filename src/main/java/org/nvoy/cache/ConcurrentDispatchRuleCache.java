package org.nvoy.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.wicket.Component;

/**
 * Default implementation. Uses a {@link ConcurrentHashMap} as a cache.
 * 
 * @author Chunkz
 * 
 */
public class ConcurrentDispatchRuleCache implements IDispatchRuleCache
{
    private final Map<Class<? extends Component>, ComponentMethodContainer> cache;

    public ConcurrentDispatchRuleCache()
    {
	cache = new ConcurrentHashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ComponentMethodContainer getEventMethods(
	    Class<? extends Component> clazz)
    {
	return cache.get(clazz);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addEventMethod(Class<? extends Component> clazz,
	    ComponentMethodContainer method)
    {
	cache.put(clazz, method);
    }
}
