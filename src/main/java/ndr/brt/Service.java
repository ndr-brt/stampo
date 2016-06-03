package ndr.brt;

import com.google.gson.Gson;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class Service {

    private CommandHandler commandHandler;
    private Repository repository;

    public Service() {
        this(new RealCommandHandler(), new MongoStampsViewRepository());
    }

    public Service(CommandHandler commandHandler, Repository repository) {
        this.commandHandler = commandHandler;
        this.repository = repository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() {
        return Response.ok(new Gson().toJson(repository.getAll())).build();
    }

    @POST
    public Response stamp() {
        commandHandler.handle(new Stamp());
        return Response.created(null).build();
    }

}
