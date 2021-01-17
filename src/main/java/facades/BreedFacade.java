/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import DTO.BreedDTO;
import DTO.SearchesDTO;
import entities.Breed;
import entities.Searches;
import errorhandling.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import utils.EMF_Creator;

/**
 *
 * @author Frederik Dahl <cph-fd76@cphbusiness.dk>
 */
public class BreedFacade {

    private static BreedFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private BreedFacade() {
    }

    public static BreedFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BreedFacade();
        }
        return instance;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void generateSearchData(BreedDTO breedDTO) {
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            Breed breedForData = new Breed(breedDTO.getBreed());

            Searches search = new Searches(breedForData);

            em.persist(breedForData);
            em.persist(search);
            em.getTransaction().commit();

        } finally {
            em.close();
        }

    }

    public List<SearchesDTO> searchCounterForSpecificBreed(String breedName) {
        EntityManager em = getEntityManager();

        try {

            em.getTransaction().begin();
            TypedQuery<Searches> q1
                    = em.createQuery("SELECT s FROM Searches s WHERE s.breedName like :name", Searches.class);
            List<Searches> searchForSpecificBreed = q1.setParameter("name", breedName).getResultList();
            List<SearchesDTO> searchDTOList = new ArrayList();
            for (Searches search : searchForSpecificBreed) {
                SearchesDTO searchesDTO = new SearchesDTO(search);
                searchDTOList.add(searchesDTO);
            }
            em.getTransaction().commit();

            return searchDTOList;
        } finally {
            em.close();
        }

    }

    public List<SearchesDTO> searchCounterForAll() {
        EntityManager em = getEntityManager();

        try {

            em.getTransaction().begin();
            TypedQuery<Searches> q1
                    = em.createQuery("SELECT s FROM Searches s", Searches.class);
            List<Searches> searches = q1.getResultList();
            List<SearchesDTO> searchDTOList = new ArrayList();
            for (Searches search : searches) {
                SearchesDTO searchesDTO = new SearchesDTO(search);
                searchDTOList.add(searchesDTO);
            }
            em.getTransaction().commit();

            return searchDTOList;
        } finally {
            em.close();
        }

    }

    public static void main(String[] args) throws NotFoundException {
        emf = EMF_Creator.createEntityManagerFactory();
        BreedFacade facade = BreedFacade.getFacadeExample(emf);
        EntityManager em = emf.createEntityManager();

        System.out.println(facade.searchCounterForSpecificBreed("beagle"));
        System.out.println(facade.searchCounterForAll());
    }
}
