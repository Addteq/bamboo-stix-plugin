package it.com.addteq.bamboo.plugin.rest;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.mockito.Mockito;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.addteq.bamboo.plugin.rest.addteqRestResource;
import com.addteq.bamboo.plugin.rest.addteqRestResourceModel;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;

public class addteqRestResourceFuncTest {

    @Before
    public void setup() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void messageIsValid() {
       /* 
        String baseUrl = System.getProperty("baseurl");
        String resourceUrl = baseUrl + "/rest/addteqrest/1.0/";

        RestClient client = new RestClient();
        Resource resource = client.resource(resourceUrl);

        addteqRestResourceModel message = resource.get(addteqRestResourceModel.class);
        assertEquals("wrong message","Hello World",message.getMessage());
        */
    }
}
