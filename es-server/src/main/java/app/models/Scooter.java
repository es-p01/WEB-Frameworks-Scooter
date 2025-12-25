package app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;


@Entity
@NamedQueries({
        @NamedQuery(name = "Scooter_find_by_status",
                query = "select s from Scooter s where s.status=?1"),
        @NamedQuery(name = "Scooter_find_by_battery",
                query = "select s from Scooter s where s.batteryCharge<=?1")
})

public class Scooter {

    public enum Type {
        Unspecified,
        IDLE,
        INUSE,
        MAINTENANCE;
    }


    @Id
    @SequenceGenerator(name = "scooter_ids", initialValue = 30001)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scooter_ids")
    @JsonView(View.Summary.class)
    private long id;
    @JsonView(View.Summary.class)
    private String tag;
    @JsonView(View.Summary.class)
    @Enumerated(EnumType.STRING)
    private Type status;
    private String gpsLocation;
    private long mileage;
    @JsonView(View.Summary.class)
    private long batteryCharge;

    @OneToOne
    private Trip currentTrip;

    @OneToMany(mappedBy = "scooter", cascade = CascadeType.REMOVE)
//    @JsonBackReference
    @JsonManagedReference
    private Set<Trip> trips = new HashSet<>();

    public Scooter() {
    }

    public Scooter(long id) {
        this.id = id;
    }

    public static Type typeValue(String type) {
        if (type == null) {
            return Type.Unspecified;
        }

        try {
            return Type.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid type: '" + type + "', 'Unspecified' will be used instead");
            return Type.Unspecified;
        }
    }


    private static Random randomizer = new Random();

    public static Scooter createSampleScooter(long id) {
        Scooter scooter = new Scooter(id);
        scooter.setTag("ssss" + Integer.toHexString(1000 + randomizer.nextInt(9000)));

        scooter.setStatus(Type.values()[1 + randomizer.nextInt(Type.values().length - 1)]);

        double lat = 52.30 + randomizer.nextDouble() * 0.10;
        double lon = 4.80 + randomizer.nextDouble() * 0.10;
        scooter.setGpsLocation(String.format("%.5fN, %.5fE", lat, lon));

        scooter.setMileage(1 + randomizer.nextInt(10000));
        scooter.setBatteryCharge(1 + randomizer.nextInt(100));

        return scooter;
    }

    /**
     * Associates the given trip with this scooter, if not yet associated
     *
     * @param trip
     * @return Whether a new association has been added
     */
    public boolean associateTrip(Trip trip) {
        if (trip == null || this.getTrips().contains(trip)) {
            return false;
        }
        this.getTrips().add(trip);
        trip.assaociateScooter(this);
        return true;
    }

    /**
     * Dissociates the given trip from this scooter, if associated
     * also checks upon the current trip
     *
     * @param trip
     * @return Whether on existing association has been removed
     */
    public boolean dissociateTrip(Trip trip) {
        if (trip == null || !this.getTrips().contains(trip)) {
            return false;
        }
        this.getTrips().remove(trip);

        if (this.getCurrentTrip() == trip) {
            this.setCurrentTrip(null);
        }
        trip.assaociateScooter(null);
        return true;
    }

    public long getId() {
        return id;
    }

    public void setId(long nextId) {
        this.id = nextId;
    }

    public String getTag() {
        return tag;
    }

    public Type getStatus() {
        return status;
    }

    public String getGpsLocation() {
        return gpsLocation;
    }

    public long getMileage() {
        return mileage;
    }

    public long getBatteryCharge() {
        return batteryCharge;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setStatus(Type status) {
        this.status = status;

        if (status != Type.INUSE) {
            this.setCurrentTrip(null);
        }
    }

    public void setGpsLocation(String gpsLocation) {
        this.gpsLocation = gpsLocation;
    }

    public void setMileage(long mileage) {
        this.mileage = mileage;
    }

    public void setBatteryCharge(long batteryCharge) {
        this.batteryCharge = batteryCharge;
    }

    public Set<Trip> getTrips() {
        return trips;
    }

    public void setTrips(Set<Trip> trips) {
        this.trips = trips;
    }

    public Trip getCurrentTrip() {
        return currentTrip;
    }

    public void setCurrentTrip(Trip currentTrip) {
        this.currentTrip = currentTrip;
    }

    @Override
    public String toString() {
        return "Scooter{" +
                "id=" + id +
                ", tag='" + tag + '\'' +
                ", status='" + status + '\'' +
                ", gpsLocation='" + gpsLocation + '\'' +
                ", mileage='" + mileage + '\'' +
                ", batteryCharge='" + batteryCharge + '\'' +
                '}';
    }
}
