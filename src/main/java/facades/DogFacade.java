package facades;

import DTO.DogDTO;
import entities.Dog;
import entities.User;
import errorhandling.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class DogFacade {

    private static DogFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private DogFacade() {
    }

    public static DogFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new DogFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<DogDTO> getAllDogs() {
        EntityManager em = getEntityManager();
        try {

            TypedQuery<Dog> q1
                    = em.createQuery("SELECT d FROM Dog d", Dog.class);
            List<Dog> dogList = q1.getResultList();
            List<DogDTO> dogListDTO = new ArrayList();
            for (Dog dog : dogList) {
                DogDTO dogDTO = new DogDTO(dog);
                dogListDTO.add(dogDTO);
            }

            return dogListDTO;
        } finally {
            em.close();
        }

    }

    public DogDTO addDog(String dogName, int age, String breed, String info, String userName) throws NotFoundException {
        EntityManager em = getEntityManager();
        User user = em.find(User.class, userName);

        if (user == null) {
            throw new NotFoundException("User could not be found");
        }

        try {
            em.getTransaction().begin();

            Dog dog = new Dog(dogName, age, breed, info);
            dog.setUser(user);

            em.persist(dog);
            em.getTransaction().commit();
            return new DogDTO(dog);
        } finally {
            em.close();
        }

    }

    public static void main(String[] args) throws NotFoundException {
        emf = EMF_Creator.createEntityManagerFactory();
        DogFacade facade = DogFacade.getFacadeExample(emf);
        EntityManager em = emf.createEntityManager();

//        instance.addDog("Oggy", 10, "Lagotta", "Info om hunden", "user");
//        instance.addDog("Bastian", 4, "Sheltie", "Info om hunden", "user");
        System.out.println(instance.getAllDogs());
    }

}
