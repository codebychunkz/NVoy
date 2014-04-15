package org.nvoy.scanner;

import javax.annotation.concurrent.ThreadSafe;
import javax.validation.constraints.NotNull;

import org.apache.wicket.Component;
import org.nvoy.cache.ComponentMethodContainer;
import org.nvoy.marker.EventListener;

@ThreadSafe
public interface IComponentScanner
{
    /**
     * Seeks and return a {@link ComponentMethodContainer} for a given component
     * containing all methods annotated with {@link EventListener}.
     * 
     * @param comp
     *            The component to scan
     * @return The {@link ComponentMethodContainer} for the component, may be
     *         null
     */
    @NotNull
    ComponentMethodContainer seekComponent(Class<? extends Component> comp);
}
