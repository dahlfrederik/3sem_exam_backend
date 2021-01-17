package DTO;

import java.util.List;

/**
 *
 * @author Frederik Dahl <cph-fd76@cphbusiness.dk>
 */
public class BreedDTOs {

    private List<BreedDTO> dogs;

    public BreedDTOs(List<BreedDTO> dogBreeds) {
        this.dogs = dogBreeds;
    }

    public List<BreedDTO> getBreedDTOList() {
        return dogs;
    }

    public void setDogBreeds(List<BreedDTO> dogBreeds) {
        this.dogs = dogBreeds;
    }

}
