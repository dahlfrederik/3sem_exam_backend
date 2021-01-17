/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
