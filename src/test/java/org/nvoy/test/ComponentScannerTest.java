package org.nvoy.test;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.*;

import org.nvoy.cache.ComponentMethodContainer;
import org.nvoy.scanner.ComponentScanner;
import org.nvoy.scanner.IComponentScanner;
import org.nvoy.test.util.InheritedTestComponent;
import org.nvoy.test.util.TestComponent;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Component.class)
public class ComponentScannerTest
{
    @Before
    public void init()
    {
	suppress(constructor(Component.class, String.class));
	suppress(constructor(Component.class, String.class, IModel.class));
	//suppress(method(Component.class, "setId", String.class));
    }

    @Test
    public final void testScannerNoListeners()
    {
	Component mock = new Component(null){protected void onRender(){}};
	IComponentScanner scanner = new ComponentScanner();
	
	ComponentMethodContainer result = scanner.seekComponent(mock.getClass());

	assertFalse("Expected no listeners for Component",
	        result.hasListeners());

	int numListeners = result.getMethods().length;
	assertEquals(String.format("Expected 0 listenable element but got %s",
	        numListeners), 0, numListeners);
    }

    @Test
    public final void testScannerWithListeners()
    {
	Component comp = new TestComponent();
	IComponentScanner scanner = new ComponentScanner();

	ComponentMethodContainer result = scanner.seekComponent(comp.getClass());
	int numListeners = result.getMethods().length;
	assertEquals(String.format("Expected 1 listenable element but got %s",
	        numListeners), 1, numListeners);
    }

    @Test
    public final void testScannerInheritance()
    {
	Component comp = new InheritedTestComponent();
	IComponentScanner scanner = new ComponentScanner();
	
	ComponentMethodContainer result = scanner.seekComponent(comp.getClass());
	int numListeners = result.getMethods().length;
	assertEquals(String.format("Expected 2 listenable element but got %s",
	        numListeners), 2, numListeners);
    }
}
