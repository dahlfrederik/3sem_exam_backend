package utils;

import DTO.BreedDTO;
import DTO.BreedDTOs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.BreedFacade;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class DataFetcher {

    private static String breedAPI = "https://dog-info.cooljavascript.dk/api/breed";

    public static String fetchBreedInformation(ExecutorService executorService) throws InterruptedException, ExecutionException, TimeoutException, IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Callable<BreedDTOs> breedTask = new Callable<BreedDTOs>() {

            @Override
            public BreedDTOs call() throws Exception {
                String listOfBreeds = HttpUtils.fetchData(breedAPI);
                BreedDTOs breedDTOs = gson.fromJson(listOfBreeds, BreedDTOs.class);
                return breedDTOs;
            }
        };

        Future<BreedDTOs> futureBreed = executorService.submit(breedTask);

        BreedDTOs breed = futureBreed.get(2, TimeUnit.SECONDS);

        String breedToJson = gson.toJson(breed);
        System.out.println(breedToJson);
        return breedToJson;
    }

    public static String fetchDogInformation(ExecutorService executorService, String breed) throws InterruptedException, ExecutionException, TimeoutException {
        String factApi = "https://dog-info.cooljavascript.dk/api/breed/" + breed;
        String imgApi = "https://dog-image.cooljavascript.dk/api/breed/random-image/" + breed;
        String randomFactApi = "https://dog-api.kinduff.com/api/facts";

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        BreedDTO breedDTO = null;
        Callable<BreedDTO> breedTask1 = new Callable<BreedDTO>() {
            @Override
            public BreedDTO call() throws Exception {
                String listWithFacts = HttpUtils.fetchData(factApi);
                BreedDTO breedDTO = gson.fromJson(listWithFacts, BreedDTO.class);
                return breedDTO;
            }

        };

        Callable<BreedDTO> breedTask2 = new Callable<BreedDTO>() {
            @Override
            public BreedDTO call() throws Exception {
                String listWithFacts = HttpUtils.fetchData(imgApi);
                BreedDTO breedDTO = gson.fromJson(listWithFacts, BreedDTO.class);
                return breedDTO;
            }
        };

        Callable<BreedDTO> breedTask3 = new Callable<BreedDTO>() {
            @Override
            public BreedDTO call() throws Exception {
                String listWithFacts = HttpUtils.fetchData(randomFactApi);

                BreedDTO breedDTO = gson.fromJson(listWithFacts, BreedDTO.class);
                return breedDTO;

            }
        };

        Future<BreedDTO> futureBreed1 = executorService.submit(breedTask1);
        Future<BreedDTO> futureBreed2 = executorService.submit(breedTask2);
        Future<BreedDTO> futureBreed3 = executorService.submit(breedTask3);

        BreedDTO breedResult1 = futureBreed1.get(2, TimeUnit.SECONDS);
        BreedDTO breedResult2 = futureBreed2.get(2, TimeUnit.SECONDS);
        BreedDTO breedResult3 = futureBreed3.get(2, TimeUnit.SECONDS);

        BreedDTO finalBreed = new BreedDTO(breedResult1.getBreed(), breedResult1.getInfo(), breedResult1.getWikipedia(), breedResult2.getImage(), breedResult3.getFacts());

        String breedToJson = gson.toJson(finalBreed);

        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        BreedFacade facade = BreedFacade.getFacadeExample(emf);
        EntityManager em = emf.createEntityManager();

        facade.generateSearchData(finalBreed);

        em.close();

        return breedToJson;

    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException, IOException {
        ExecutorService es = Executors.newCachedThreadPool();

        fetchDogInformation(es, "beagle");
    }
}
