package org.nvoy.test.util;

import org.apache.wicket.Component;
import org.nvoy.marker.EventListener;

public class TestComponent extends Component
{
	private static final long serialVersionUID = 1L;
	
	public TestComponent()
	{
	    super(null);
	}

	@Override
	protected void onRender(){}

	@EventListener
	public final void event(){}
}
