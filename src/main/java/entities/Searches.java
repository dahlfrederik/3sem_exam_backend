/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * @author Frederik Dahl <cph-fd76@cphbusiness.dk>
 */
@Entity
public class Searches implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany(mappedBy = "searches", cascade = CascadeType.ALL)
    List<Breed> breeds;

    private Timestamp timeStampForSearch;

    private String breedName;

    public Searches() {
    }

    public Searches(Breed breed) {
        breeds = new ArrayList();
        this.breeds.add(breed);
        this.breedName = breed.getBreed();
        setTimeStampForSearch();
    }

    public void addBreed(Breed breed) {
        this.breeds.add(breed);
        breed.getSearches().add(this);

    }

    public void setTimeStampForSearch() {
        this.timeStampForSearch = new Timestamp(System.currentTimeMillis());;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Breed> getBreeds() {
        return breeds;
    }

    public Timestamp getTimeStampForSearch() {
        return timeStampForSearch;
    }

    public String getBreedName() {
        return breedName;
    }

}
