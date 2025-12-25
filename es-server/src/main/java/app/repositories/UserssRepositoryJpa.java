package app.repositories;

import app.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class UserssRepositoryJpa implements EntityRepository<User> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> findAll() {
        TypedQuery<User> query =
                this.em.createQuery(
                        "select u from User u", User.class);
        return query.getResultList();
    }

    @Override
    public User findById(long id) {
        return em.find(User.class, id);
    }

    @Override
    public User save(User user) {
        return em.merge(user);
    }

    @Override
    public User deleteById(long id) {
        User user = this.findById(id);
        em.remove(user);
        return user;
    }

    @Override
    public List<User> findByQuery(String jpqlName, Object... params) {
        TypedQuery<User> query =
                this.em.createNamedQuery(jpqlName, User.class);
        int i = 0;
        for (Object p : params) {
            i++;
            query.setParameter(i, p);
        }
        return query.getResultList();
    }
}

