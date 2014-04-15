package org.nvoy.test;

import static org.powermock.api.support.membermodification.MemberMatcher.constructor;
import static org.powermock.api.support.membermodification.MemberModifier.suppress;

import java.util.Date;

import org.apache.wicket.Component;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.model.IModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.nvoy.NVoyDispatcher;
import org.nvoy.NVoyDispatcher.NVoyDispatcherBuilder;
import org.nvoy.test.util.StringListeningComponent;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Component.class)
public class DispatcherTest
{
    @Before
    public void init()
    {
	suppress(constructor(Component.class, String.class));
	suppress(constructor(Component.class, String.class, IModel.class));
    }
    
    @Test
    public void testDefaultDispatcherWorkingEvent()
    {
	IEvent event = mock(IEvent.class);
	when(event.getPayload()).thenReturn("Hello, World!");
	
	StringListeningComponent comp = mock(StringListeningComponent.class);
	
	NVoyDispatcher dispatcher = new NVoyDispatcherBuilder().build();
	dispatcher.dispatchEvent(new Object(), event, comp);
	
	Mockito.verify(comp, Mockito.times(1)).stringEvent(Mockito.anyString());
    }
    
    @Test
    public void testDefaultDispatcherFailingEvent()
    {
	IEvent event = mock(IEvent.class);
	when(event.getPayload()).thenReturn(new Date());
	
	StringListeningComponent comp = mock(StringListeningComponent.class);
	
	NVoyDispatcher dispatcher = new NVoyDispatcherBuilder().build();
	dispatcher.dispatchEvent(new Object(), event, comp);
	
	Mockito.verify(comp, Mockito.never()).stringEvent(Mockito.anyString());
    } 
    
    @Test
    public void testDefaultDispatcherNullEvent()
    {
	IEvent<?> event = (IEvent<?>)mock(IEvent.class);
	
	
	when(event.getPayload()).thenReturn(null);
	
	StringListeningComponent comp = mock(StringListeningComponent.class);
	
	NVoyDispatcher dispatcher = new NVoyDispatcherBuilder().build();
	dispatcher.dispatchEvent(new Object(), event, comp);
	
	Mockito.verify(comp, Mockito.never()).stringEvent(Mockito.anyString());
    }
}
