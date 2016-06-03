package ndr.brt;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import de.flapdoodle.embed.mongo.Command;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.*;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.config.store.HttpProxyFactory;
import de.flapdoodle.embed.process.runtime.Network;
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
    public void stamp_time_and_then_read_it() throws Exception {
        Command command = Command.MongoD;

        IRuntimeConfig runtimeConfig = new RuntimeConfigBuilder()
                .defaults(command)
                .artifactStore(new ExtractedArtifactStoreBuilder()
                        .defaults(command)
                        .download(new DownloadConfigBuilder()
                                .defaultsForCommand(command)
                                .proxyFactory(new HttpProxyFactory("proxy.paros.local", 3128))))
                .build();


        MongodStarter starter = MongodStarter.getInstance(runtimeConfig);


        int port = 12345;
        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(port, Network.localhostIsIPv6()))
                .build();

        MongodExecutable mongodExecutable = null;
        try {
            mongodExecutable = starter.prepare(mongodConfig);
            MongodProcess mongod = mongodExecutable.start();

            client.POST("http://localhost:8080/").send();

            ContentResponse response = client.GET("http://localhost:8080/");

            String result = response.getContentAsString();
            Type listType = new TypeToken<List<Date>>() {}.getType();
            Gson gson = new Gson();

            List<Date> stamps = gson.fromJson(result, listType);

            assertEquals(1, stamps.size());
            assertEquals(Date.class, stamps.get(0).getClass());

        } finally {
            if (mongodExecutable != null) {
                mongodExecutable.stop();
            }
        }
    }

    @Test
    public void stamp_time() throws Exception {
        ContentResponse send = client.POST("http://localhost:8080/").send();

        assertThat(send.getStatus(), is(CREATED.getStatusCode()));
    }
}
