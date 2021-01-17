/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import DTO.SearchesDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.BreedFacade;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import utils.DataFetcher;
import utils.EMF_Creator;

/**
 *
 * @author Frederik Dahl <cph-fd76@cphbusiness.dk>
 */
@Path("breed")
public class BreedResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final BreedFacade bf = BreedFacade.getFacadeExample(EMF);
    private ExecutorService es = Executors.newCachedThreadPool();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/searches")
    public String getAllSearches() throws IOException, InterruptedException, ExecutionException, TimeoutException {
        List<SearchesDTO> searches = bf.searchCounterForAll();
        return GSON.toJson(searches);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/searches/{breed}")
    public String getSearchesForSpecificBreed(@PathParam("breed") String breed) throws IOException, InterruptedException, ExecutionException, TimeoutException {
        List<SearchesDTO> searchForSpecificBreed = bf.searchCounterForSpecificBreed(breed);
        return GSON.toJson(searchForSpecificBreed);
    }

}
