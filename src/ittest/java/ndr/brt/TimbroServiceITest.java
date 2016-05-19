package ndr.brt;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static ndr.brt.Application.initServer;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TimbroServiceITest {

    private Server server;
    private HttpClient client;

    @Before
    public void setUp() throws Exception {
        server = initServer();
        server.start();
        client = new HttpClient();
        client.start();
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
        client.stop();
    }

    @Test
    public void send_and_retrieve() throws Exception {
        ContentResponse response = client.GET("http://localhost:8080/");

        assertThat(response.getContentAsString(), is("Segnato"));
    }

}
