/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import entities.Dog;

/**
 *
 * @author Frederik Dahl <cph-fd76@cphbusiness.dk>
 */
public class DogDTO {

    private String dogName;
    private int age;
    private String breed;
    private String info;
    private String userName;

    public DogDTO(Dog dog) {
        this.dogName = dog.getDogName();
        this.age = dog.getAge();
        this.breed = dog.getBreed();
        this.info = dog.getInfo();
        this.userName = dog.getUser().getUserName();
    }

    public String getDogName() {
        return dogName;
    }

    public int getAge() {
        return age;
    }

    public String getBreed() {
        return breed;
    }

    public String getInfo() {
        return info;
    }

    public String getUserName() {
        return userName;
    }

}
