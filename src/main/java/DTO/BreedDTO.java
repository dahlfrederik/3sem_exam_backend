package DTO;

import java.util.List;

/**
 *
 * @author Frederik Dahl <cph-fd76@cphbusiness.dk>
 */
public class BreedDTO {

    private String breed;
    private String info;
    private String wikipedia;
    private String image;
    private List<String> facts;

    public BreedDTO(String breed) {
        this.breed = breed;
    }

    public BreedDTO(String breed, String info, String wikipedia, String image, List<String> facts) {
        this.breed = breed;
        this.info = info;
        this.wikipedia = wikipedia;
        this.image = image;
        this.facts = facts;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getInfo() {
        return info;
    }

    public String getWikipedia() {
        return wikipedia;
    }

    public String getImage() {
        return image;
    }

    public List<String> getFacts() {
        return facts;
    }

}
