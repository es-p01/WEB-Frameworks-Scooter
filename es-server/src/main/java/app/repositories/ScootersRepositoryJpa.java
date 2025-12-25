package app.repositories;

import app.models.Scooter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;


import java.util.List;

@Primary
@Repository
@Transactional
public class ScootersRepositoryJpa implements EntityRepository<Scooter> {

    @PersistenceContext
    private EntityManager em;


    @Override
    public List<Scooter> findAll() {
        TypedQuery<Scooter> query =
                this.em.createQuery(
                        "select s from Scooter s", Scooter.class);
        return query.getResultList();
    }

    @Override
    public Scooter findById(long id) {
        return em.find(Scooter.class, id);
    }

    @Override
    public Scooter save(Scooter scooter) {
        return em.merge(scooter);
    }

    @Override
    public Scooter deleteById(long id) {
        Scooter scooter = this.findById(id);
        em.remove(scooter);
        return scooter;
    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<Scooter> findByQuery(String jpqlName, Object... params) {
        TypedQuery<Scooter> query =
                em.createNamedQuery(jpqlName, Scooter.class);
        int i = 0;
        for (Object p : params) {
            i++;
            query.setParameter(i, p);
        }
        return query.getResultList();
    }
}
