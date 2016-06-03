package ndr.brt;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import static javax.ws.rs.core.Response.Status.CREATED;
import static ndr.brt.Application.initServer;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

public class TimbroServiceITest {

    private Server server;
    private HttpClient client;
    private TestDatabase database;

    @Before
    public void setUp() throws Exception {
        server = initServer();
        server.start();
        client = new HttpClient();
        client.start();
        database = TestDatabase.build();
        database.start();
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
        client.stop();
        database.stop();
    }

    @Test
    public void stamp_time_and_then_read_it() throws Exception {
        client.POST("http://localhost:8080/").send();

        List<Date> stamps = getStampsFrom(client.GET("http://localhost:8080/"));

        assertEquals(1, stamps.size());
        assertEquals(Date.class, stamps.get(0).getClass());
    }

    @Test
    public void stamp_time() throws Exception {
        ContentResponse send = client.POST("http://localhost:8080/").send();

        assertThat(send.getStatus(), is(CREATED.getStatusCode()));
    }

    private List<Date> getStampsFrom(ContentResponse response) {
        String result = response.getContentAsString();
        Type listType = new TypeToken<List<Date>>() {}.getType();
        Gson gson = new Gson();
        return gson.fromJson(result, listType);
    }

}
