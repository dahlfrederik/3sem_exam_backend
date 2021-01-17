/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
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
public class Breed implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String breed;

    @ManyToMany(cascade = (CascadeType.PERSIST))
    private List<Searches> searches;

    public Breed() {
    }

    public Breed(String breed) {
        this.breed = breed;
    }

    public String getBreed() {
        return breed;
    }

    public List<Searches> getSearches() {
        return searches;
    }

    public Integer getId() {
        return id;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
