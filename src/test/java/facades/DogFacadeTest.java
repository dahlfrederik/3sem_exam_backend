/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Dog;
import entities.Role;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

/**
 *
 * @author Frederik Dahl <cph-fd76@cphbusiness.dk>
 */
@Disabled
public class DogFacadeTest {

    private static EntityManagerFactory emf;
    private static DogFacade facade;

    @BeforeAll
    public static void setupClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = DogFacade.getFacadeExample(emf);

    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE from Dog").executeUpdate();
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
    public void tearDown() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            em.createQuery("DELETE from Dog").executeUpdate();
            em.createQuery("DELETE from User").executeUpdate();

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testAddDog() throws Exception {
        int result = facade.getAllDogs().size();
        assertEquals(1, result, "Expects one row in the database");
        facade.addDog("Bastian", 4, "Sheltie", "Info om hunden", "testUser");
        int result2 = facade.getAllDogs().size();
        assertEquals(2, result2, "Expected two rows in database");
    }

}
