package DTO;

import entities.Searches;
import java.sql.Timestamp;

/**
 *
 * @author Frederik Dahl <cph-fd76@cphbusiness.dk>
 */
public class SearchesDTO {

    private Timestamp timeStampForSearch;
    private String breedName;

    public SearchesDTO(Searches search) {
        this.timeStampForSearch = search.getTimeStampForSearch();
        this.breedName = search.getBreedName();
    }

    public Timestamp getTimeStampForSearch() {
        return timeStampForSearch;
    }

    public String getBreedName() {
        return breedName;
    }

}
