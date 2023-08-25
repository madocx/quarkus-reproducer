package org.acme.dynamodb;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/fruits")
public class FruitResource {
	
	@Inject
	FruitService fruitService;

    @GET
	@Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getFruit(){
        return fruitService.get("orange").chain(response -> Uni.createFrom().item(Response.ok().entity(response).build()));
    }
}