package app.repositories;

import app.models.Scooter;
import app.models.Trip;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class TripsRepositoryJpa implements EntityRepository<Trip> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Trip> findAll() {
        TypedQuery<Trip> query =
                this.em.createQuery(
                        "select t from Trip t", Trip.class);
        return query.getResultList();
    }

    @Override
    public Trip findById(long id) {
        return em.find(Trip.class, id);
    }

    @Override
    public Trip save(Trip trip) {
        return em.merge(trip);
    }

    @Override
    public Trip deleteById(long id) {
        Trip trip = this.findById(id);
        em.remove(trip);
        return trip;
    }

    @Override
    public List<Trip> findByQuery(String jpqlName, Object... params) {
        TypedQuery<Trip> query =
                this.em.createNamedQuery(jpqlName, Trip.class);
        int i = 0;
        for (Object p : params) {
            i++;
            query.setParameter(i, p);
        }
        return query.getResultList();
    }
}

