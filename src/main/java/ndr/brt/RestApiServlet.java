package ndr.brt;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class RestApiServlet {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String test() {
        return "Segnato";
    }

    @POST
    public Response stamp() {
        return Response.created(null).build();
    }

}
