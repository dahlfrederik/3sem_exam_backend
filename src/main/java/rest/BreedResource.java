/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import utils.DataFetcher;

/**
 *
 * @author Frederik Dahl <cph-fd76@cphbusiness.dk>
 */
@Path("breed")
public class BreedResource {
    
    private ExecutorService es = Executors.newCachedThreadPool();
    
    @Context
    private UriInfo context;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getBreeds() throws IOException, InterruptedException, ExecutionException, TimeoutException {
        return DataFetcher.fetchBreedInformation(es);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{breed}")
    public String getInformationAboutBreed(@PathParam("breed") String breed) throws IOException, InterruptedException, ExecutionException, TimeoutException {
        return DataFetcher.fetchDogInformation(es, breed);
    }
    
}
