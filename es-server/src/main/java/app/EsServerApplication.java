package app;

import app.models.Scooter;
import app.models.Trip;
import app.models.User;
import app.repositories.EntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.util.List;

@SpringBootApplication
public class EsServerApplication implements CommandLineRunner {

    @Autowired
    EntityRepository<Scooter> scootersRepo;

    @Autowired
    EntityRepository<Trip> tripsRepo;

    @Autowired
    EntityRepository<User> usersRepo;

    public static void main(String[] args) {
        SpringApplication.run(EsServerApplication.class, args);
    }

    @Transactional
    @Override
    public void run(String... args) {
        System.out.println("Running CommandLine Startup");
        this.createInitialScooters();
        this.createInitialUser();
    }

    private void createInitialUser() {
        List<User> users = this.usersRepo.findAll();
        if (users.size() > 0) return;
        System.out.println("Configuring some initial user data");

        //create and add a new scooter with random data
        User user = User.createSample(0);
        users.add(this.usersRepo.save(user));

    }

    private void createInitialScooters() {
        //check whether the repo is empty
        List<Scooter> scooters = this.scootersRepo.findAll();
        if (scooters.size() > 0) return;
        System.out.println("Configuring some initial scooter data");

        for (int i = 0; i < 10; i++) {
            //create and add a new scooter with random data
            Scooter scooter = Scooter.createSampleScooter(0);
            scooters.add(this.scootersRepo.save(scooter));
        }

        for (Scooter scooter : scooters) {
            int tripNumber = 2;
            for (int j = 0; j < tripNumber; j++) {
                Trip trip = Trip.createSample(0, scooter);
                scooter.associateTrip(trip);
                this.tripsRepo.save(trip);
            }

        }

    }

//         for (int t = 0; t < 3; t++) {
//        Trip trip = Trip.createSample(0,scooter);
//        trip.assaociateScooter(scooter);
//        this.tripsRepo.save(trip);
//    }
}
