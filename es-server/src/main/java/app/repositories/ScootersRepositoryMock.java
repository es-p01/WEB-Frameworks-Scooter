package app.repositories;

import app.models.Scooter;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//@Primary
@Repository
public class ScootersRepositoryMock implements EntityRepository<Scooter> {
    private long lastId = 3007;
    private final List<Scooter> scooters;
    static Random randomizer = new Random();
    private long getNextId() {
        this.lastId += 1 + randomizer.nextInt(3);
        return this.lastId;
    }

    public ScootersRepositoryMock() {
        scooters = new ArrayList<>();
        for (long i = 3000; i <= 3007; i++) {
            scooters.add(Scooter.createSampleScooter(i));
        }
    }

    @Override
    public List<Scooter> findAll() {
        return scooters;
    }

    @Override
    public Scooter findById(long id) {
        return this.scooters.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Scooter save(Scooter scooter) {
        if (scooter.getId() == 0) {
            scooter.setId(getNextId());
        }

        Scooter existing = this.findById(scooter.getId());
        if (existing != null) {
            scooters.remove(existing);
        }
        scooters.add(scooter);
        return scooter;
    }

    @Override
    public Scooter deleteById(long id) {
        Scooter scooter = this.findById(id);

        if (scooter != null) {
            scooters.remove(scooter);
        }
        return scooter;

    }

    @Override
    public List<Scooter> findByQuery(String jpqlName, Object... params) {
        return null;
    }
}
