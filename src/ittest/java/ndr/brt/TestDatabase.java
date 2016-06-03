package ndr.brt;

import de.flapdoodle.embed.mongo.Command;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.*;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.config.store.HttpProxyFactory;
import de.flapdoodle.embed.process.runtime.Network;

import java.io.IOException;

public class TestDatabase {

    private MongodExecutable executable;

    public TestDatabase(MongodExecutable executable) {
        this.executable = executable;
    }

    public static TestDatabase build() {
        try {
            MongodStarter starter = startWithProxy();

            int port = 12345;
            IMongodConfig mongodConfig = new MongodConfigBuilder()
                    .version(Version.Main.PRODUCTION)
                    .net(new Net(port, Network.localhostIsIPv6()))
                    .build();

            return new TestDatabase(starter.prepare(mongodConfig));
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static MongodStarter startWithProxy() {
        Command command = Command.MongoD;

        IRuntimeConfig runtimeConfig = new RuntimeConfigBuilder()
                .defaults(command)
                .artifactStore(new ExtractedArtifactStoreBuilder()
                        .defaults(command)
                        .download(new DownloadConfigBuilder()
                                .defaultsForCommand(command)
                                .proxyFactory(new HttpProxyFactory("proxy.paros.local", 3128))).build())
                .build();

        return MongodStarter.getInstance(runtimeConfig);
    }

    public void start() {
        try {
            executable.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (executable != null) {
            executable.stop();
        }
    }
}
