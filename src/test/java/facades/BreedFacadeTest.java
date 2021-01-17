/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import DTO.BreedDTO;
import DTO.SearchesDTO;
import entities.Dog;
import entities.Role;
import entities.Searches;
import entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

/**
 *
 * @author Frederik Dahl <cph-fd76@cphbusiness.dk>
 */
public class BreedFacadeTest {

    private static EntityManagerFactory emf;
    private static BreedFacade facade;

    @BeforeAll
    public static void setupClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = BreedFacade.getFacadeExample(emf);

    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {

            em.getTransaction().begin();
            em.createQuery("DELETE from Dog").executeUpdate();
            em.createQuery("DELETE from Role").executeUpdate();
            em.createQuery("DELETE from User").executeUpdate();
            User user = new User("testUser", "kode123");
            Role userRole = new Role("user");
            user.addRole(userRole);
            Dog dog = new Dog("Oggy", 10, "Lagotta", "Info om hunden");
            dog.setUser(user);

            em.persist(userRole);
            em.persist(user);
            em.persist(dog);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void destroy() {
        EntityManager em = emf.createEntityManager();
        try {

            em.getTransaction().begin();

            em.createQuery("DELETE from Dog").executeUpdate();
            em.createQuery("DELETE from Role").executeUpdate();
            em.createQuery("DELETE from User").executeUpdate();
            em.createQuery("DELETE from Searches").executeUpdate();
            em.createQuery("DELETE from Breed").executeUpdate();

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testGenerateSearchData() {
        EntityManager em = emf.createEntityManager();
        BreedDTO breedDTO = new BreedDTO("beagle");
        facade.generateSearchData(breedDTO);
        BreedDTO breedDTO2 = new BreedDTO("boxer");
        facade.generateSearchData(breedDTO2);
        List<SearchesDTO> searchDTOList = new ArrayList();

        try {
            em.getTransaction().begin();

            TypedQuery<Searches> q1
                    = em.createQuery("SELECT s FROM Searches s", Searches.class);
            List<Searches> searches = q1.getResultList();

            for (Searches search : searches) {
                SearchesDTO searchesDTO = new SearchesDTO(search);
                searchDTOList.add(searchesDTO);
            }

            em.getTransaction().commit();
        } finally {
            em.close();
        }
        assertEquals(2, searchDTOList.size(), "We expect one search since we only have this search");

    }

    /**
     * Test of searchCounterForSpecificBreed method, of class BreedFacade.
     */
    @Test
    public void testSearchCounterForSpecificBreed() {
        EntityManager em = emf.createEntityManager();
        BreedDTO breedDTO = new BreedDTO("beagle");
        facade.generateSearchData(breedDTO);
        BreedDTO breedDTO2 = new BreedDTO("beagle");
        facade.generateSearchData(breedDTO2);
        BreedDTO breedDTO3 = new BreedDTO("boxer");
        facade.generateSearchData(breedDTO3);

        List<SearchesDTO> searchDTOList = facade.searchCounterForSpecificBreed("beagle");

        assertEquals(2, searchDTOList.size(), "We expect the size 2");
    }

    /**
     * Test of searchCounterForAll method, of class BreedFacade.
     */
    @Test
    public void testSearchCounterForAll() {
        EntityManager em = emf.createEntityManager();
        BreedDTO breedDTO = new BreedDTO("beagle");
        facade.generateSearchData(breedDTO);
        BreedDTO breedDTO2 = new BreedDTO("beagle");
        facade.generateSearchData(breedDTO2);
        BreedDTO breedDTO3 = new BreedDTO("boxer");
        facade.generateSearchData(breedDTO3);

        List<SearchesDTO> searchDTOList = facade.searchCounterForAll();

        assertEquals(3, searchDTOList.size(), "We expect the size 3");
    }

}
