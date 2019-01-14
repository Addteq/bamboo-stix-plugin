package com.addteq.bamboo.plugin.rest;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.mockito.Mockito;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.addteq.bamboo.plugin.rest.addteqRestResource;
import com.addteq.bamboo.plugin.rest.addteqRestResourceModel;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.GenericEntity;

public class addteqRestResourceTest {

    @Before
    public void setup() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void messageIsValid() {
    	/*
        addteqRestResource resource = new addteqRestResource();

        Response response = resource.getMessage();
        final addteqRestResourceModel message = (addteqRestResourceModel) response.getEntity();

        assertEquals("wrong message","Hello World",message.getMessage());
        */
    }
}
