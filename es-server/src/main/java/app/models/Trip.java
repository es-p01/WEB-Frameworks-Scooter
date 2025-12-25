package app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Entity
@NamedQueries({
        @NamedQuery(name="Trip_find_by_scooterId_and_period",
        query= "select t from Trip t where t.scooter.id= ?1 and t.startDateTime >= ?2 and t.endDateTime <=?3")
})
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String startPosition;
    private String endPosition;
    private double mileage;
    private double cost;

    @ManyToOne
//    @JsonManagedReference
    @JsonBackReference
    private Scooter scooter;

    public Trip() {

    }


    public Trip(long id) {
        this.id = id;
    }


    private static Random randomizer = new Random();

    public static Trip createSample(long id, Scooter scooter) {
        Trip newTrip = Trip.createSample(id);
        // associate the author for bidirectional navigation
        newTrip.assaociateScooter(scooter);
        return newTrip;
    }

    public static Trip createSample(long id) {
        Trip newTrip = new Trip(id);

        LocalDateTime start = LocalDateTime.now().minusDays(randomizer.nextInt(1000));

        newTrip.setStartDateTime(start);
        newTrip.setEndDateTime(start.plusDays(randomizer.nextInt(15) + 1));

        double lat = 52.30 + randomizer.nextDouble() * 0.10;
        double lon = 4.80 + randomizer.nextDouble() * 0.10;
        newTrip.setStartPosition(String.format("%.5fN, %.5fE", lat, lon));
        newTrip.setEndPosition(String.format("%.5fN, %.5fE", lat, lon));
        newTrip.setMileage(1 + randomizer.nextInt(10000));
        newTrip.setCost(3.0 + 0.05 * randomizer.nextInt(3000));

        return newTrip;
    }


    /**
     * Associates the given scooter with this trip, if not yet associated
     *
     * @param scooter Provide null to dissociate
     *                the currently associated scooter
     * @return Whether a new association has been added
     */
    public boolean assaociateScooter(Scooter scooter) {
        if (scooter != null && this.getScooter() == null) {
            scooter.associateTrip(this);
            this.setScooter(scooter);
            return true;
        }

        return false;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(String startPosition) {
        this.startPosition = startPosition;
    }

    public String getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(String endPosition) {
        this.endPosition = endPosition;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Scooter getScooter() {
        return scooter;
    }

    public void setScooter(Scooter scooter) {
        this.scooter = scooter;
    }
}
