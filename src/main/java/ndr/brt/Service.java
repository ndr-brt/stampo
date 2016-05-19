package ndr.brt;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class Service {

    private CommandHandler commandHandler;

    public Service(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String test() {
        return "Segnato";
    }

    @POST
    public Response stamp() {
        commandHandler.handle("aaaa");
        return Response.created(null).build();
    }

}
