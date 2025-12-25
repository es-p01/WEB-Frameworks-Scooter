package app.rest;

import app.exceptions.PreConditionFailed;
import app.exceptions.ResourceNotFoundException;
import app.models.Scooter;
import app.models.Trip;
import app.models.View;
import app.repositories.EntityRepository;
import app.repositories.ScootersRepositoryMock;
import app.repositories.TripsRepositoryJpa;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.transaction.Transactional;
import jakarta.websocket.server.PathParam;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Type;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
public class ScooterController {

    @Autowired
    EntityRepository<Scooter> scootersRepo;

    @GetMapping("/scooters")
    public List<Scooter> getAllScooters(@RequestParam(required = false) String status,
                                        @RequestParam(required = false) Long battery) throws BadRequestException {
        // check how many request parameters have been provided
        int paramsCount =
                (status != null ? 1 : 0)
                        + (battery != null ? 1 : 0);

        if (paramsCount == 0) {
            // regular request without query parameters
            return scootersRepo.findAll();
        } else if (status != null && Scooter.typeValue(status) != Scooter.Type.Unspecified) {
            return scootersRepo.findByQuery("Scooter_find_by_status", Scooter.typeValue(status));
        } else if (battery != null && battery < 101) {
            return scootersRepo.findByQuery("Scooter_find_by_battery", battery);
        } else {
            throw new BadRequestException(
                    "status=" + status + " or battery=" + battery + " is not a valid value");
        }
    }

    @GetMapping(path = "/scooters/{id}")
    public ResponseEntity<Scooter> getOneScooter(@PathVariable() long id) {
        Scooter scooter = this.scootersRepo.findById(id);

        if (scooter == null) {
            throw new ResourceNotFoundException("Cannot provide a scooter with id=" + id);
        }

        return ResponseEntity.ok().body(scooter);
    }

    @PostMapping(path = "/scooters")
    public ResponseEntity<Scooter> createScooter(@RequestBody Scooter scooter) {
        Scooter savedScooter = scootersRepo.save(scooter);

        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().path("/{id}").
                buildAndExpand(savedScooter.getId()).toUri();

        return ResponseEntity.created(location).body(savedScooter);
    }

    @PutMapping(path = "/scooters/{id}")
    public void updateScooter(@RequestBody Scooter scooter, @PathVariable long id) {
        if (scooter.getId() != id) {
            throw new PreConditionFailed("Scooter Id=" + scooter.getId() + " does not match path param=" + id);
        }

        scooter.setId(id);
        scootersRepo.save(scooter);
    }

    @DeleteMapping(path = "/scooters/{id}")
    public Scooter deleteOneScooter(@PathVariable() long id) {
        Scooter scooter = this.scootersRepo.deleteById(id);

        if (scooter == null) {
            throw new ResourceNotFoundException("Cannot delete a book with id=" + id);
        }

        return scooter;
    }

    @JsonView(View.Summary.class)
    @GetMapping("/scooters/summary")
    public List<Scooter> getScootersSummary() {
        return scootersRepo.findAll();
    }


    @Autowired
    EntityRepository<Trip> tripsRepo;

    @Transactional
    @PostMapping(path = "/scooters/{scooterId}/trips", produces = "application/json")
    public ResponseEntity<Scooter> addNewTrip(@PathVariable() long scooterId,
                                              @RequestBody Trip newTrip) {
        Scooter scooter = this.scootersRepo.findById(scooterId);

        if (newTrip.getStartDateTime() == null) {
            newTrip.setStartDateTime(LocalDateTime.now());
        }
        Trip savedTrip = this.tripsRepo.save(newTrip);


        if (scooter.getStatus() != Scooter.Type.IDLE || scooter.getBatteryCharge() < 10) {
            throw new PreConditionFailed(
                    String.format("Scooter(Id)=%d with status %s cannot be claimed for another trip",
                            scooter.getId(), scooter.getStatus()));
        }

        savedTrip.setStartPosition(scooter.getGpsLocation());
        scooter.setStatus(Scooter.Type.INUSE);
        scooter.associateTrip(savedTrip);
        // if an exception occured, the newTrip should not have been persisted
        return ResponseEntity.ok().body(scooter);

    }

    @GetMapping(path = "/scooters/{scooterId}/trips")
    @DateTimeFormat
    public List<Trip> getTrips(@PathVariable() long scooterId,
                               @RequestParam(required = false) LocalDateTime from,
                               @RequestParam(required = false) LocalDateTime to) throws BadRequestException {

        Scooter scooter = this.scootersRepo.findById(scooterId);

        if (scooter == null) {
            throw new ResourceNotFoundException("Cannot provide a scooter with id=" + scooterId);
        }

        // check how many request parameters have been provided
        int paramsCount =
                (scooterId != 0 ? 1 : 0) +
                        (from != null ? 1 : 0)
                        + (to != null ? 1 : 0);

        if (from == null && to == null) {
            // regular request without query parameters
            return tripsRepo.findAll();
        } else if (paramsCount == 3) {
            return tripsRepo.findByQuery("Trip_find_by_scooterId_and_period", scooterId, from, to);
        } else {
            throw new BadRequestException(
                    "The values from and to need to be provided.\n " +
                            "from=" + from + " and to=" + to
            );
        }
    }

}
