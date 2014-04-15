package org.nvoy.test.util;

import org.nvoy.marker.EventListener;

public final class InheritedTestComponent extends TestComponent
{
    private static final long serialVersionUID = 1L;

	@EventListener
	public final void inheritedEvent(){}
}
