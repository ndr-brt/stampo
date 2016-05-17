package ndr.brt;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.server.Server;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static ndr.brt.Application.*;

public class TimbroServiceITest {

    @Test
    public void send_and_retrieve() throws Exception {
        Server server = initServer();
        server.start();

        HttpClient client = new HttpClient();
        client.start();

        ContentResponse response = client.GET("http://localhost:8080/");

        MatcherAssert.assertThat(response.getContentAsString(), CoreMatchers.is("Segnato"));

        client.stop();

        server.stop();
    }

}
