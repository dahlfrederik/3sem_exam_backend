package utils;

import DTO.BreedDTOs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DataFetcher {

    private static String breedAPI = "https://dog-info.cooljavascript.dk/api/breed";

    public static String fetchDogInformation(ExecutorService executorService) throws InterruptedException, ExecutionException, TimeoutException, IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//       long start = System.nanoTime();
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//
//        String breed = HttpUtils.fetchData(breedAPI);
//        Type collectionType = new TypeToken<List<BreedDTO>>() {
//        }.getType();
//        List<BreedDTO> breedList = (List<BreedDTO>) new Gson()
//                .fromJson(breed, collectionType);
//        return gson.toJson(breedList);

        Callable<BreedDTOs> breedTask = new Callable<BreedDTOs>() {

            @Override
            public BreedDTOs call() throws Exception {
                String listOfBreeds = HttpUtils.fetchData(breedAPI);
                BreedDTOs breedDTOs = gson.fromJson(listOfBreeds, BreedDTOs.class);
                return breedDTOs;
            }
        };
//        
//        Callable<DadDTO> dadTask = new Callable<DadDTO>(){
//            @Override
//            public DadDTO call() throws Exception {
//               String dad = HttpUtils.fetchData(dadJokeApi);
//               DadDTO dadDTO = gson.fromJson(dad, DadDTO.class);
//               return dadDTO; 
//            }         
//        }; 
//        
//        
//        Callable<InsultDTO> insultTask = new Callable<InsultDTO>(){
//            @Override
//            public InsultDTO call() throws Exception {
//               String insult = HttpUtils.fetchData(insultApi);
//               InsultDTO insultDTO = gson.fromJson(insult, InsultDTO.class);
//               return insultDTO; 
//            }         
//        }; 
//
        Future<BreedDTOs> futureBreed = executorService.submit(breedTask);
//        Future<DadDTO> futureDad = executorService.submit(dadTask); 
//        Future<InsultDTO> futureInsult = executorService.submit(insultTask);         

        BreedDTOs breed = futureBreed.get(2, TimeUnit.SECONDS);
//        DadDTO dad = futureDad.get(2,TimeUnit.SECONDS); 
//        InsultDTO insult = futureInsult.get(2,TimeUnit.SECONDS); 

//        CombinedJokeDTO combinedDTO = new CombinedJokeDTO(chuck,dad,insult);
        String breedToJson = gson.toJson(breed);
        System.out.println(breedToJson);
        return breedToJson;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException, IOException {
        ExecutorService es = Executors.newCachedThreadPool();

        fetchDogInformation(es);
    }
}
