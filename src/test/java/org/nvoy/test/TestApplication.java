package org.nvoy.test;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class TestApplication extends WebApplication
{

    @Override
    public Class<? extends Page> getHomePage()
    {
	return null;
    }

}
