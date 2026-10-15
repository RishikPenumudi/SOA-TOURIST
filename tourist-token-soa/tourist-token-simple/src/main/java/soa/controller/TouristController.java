package soa.controller;

import soa.models.Tourist;
import soa.service.TouristService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tourists")
public class TouristController {

    private final TouristService service;

    public TouristController(TouristService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> addTourist(
            @RequestHeader(value = "Token", required = false) String token,
            @RequestBody Tourist tourist) {
        if (!service.isValidToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Token Validation Failed"));
        }
        Tourist saved = service.addTourist(tourist);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<?> getTourists(
            @RequestHeader(value = "Token", required = false) String token) {
        if (!service.isValidToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Token Validation Failed"));
        }
        return ResponseEntity.ok(service.getTourists());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTourist(
            @RequestHeader(value = "Token", required = false) String token,
            @PathVariable long id) {
        if (!service.isValidToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Token Validation Failed"));
        }
        Tourist tourist = service.getTourist(id);
        if (tourist == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Tourist not found"));
        }
        return ResponseEntity.ok(tourist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTourist(
            @RequestHeader(value = "Token", required = false) String token,
            @PathVariable long id,
            @RequestBody Tourist tourist) {
        if (!service.isValidToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Token Validation Failed"));
        }
        Tourist updated = service.updateTourist(id, tourist);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Tourist not found"));
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTourist(
            @RequestHeader(value = "Token", required = false) String token,
            @PathVariable long id) {
        if (!service.isValidToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Token Validation Failed"));
        }
        if (!service.deleteTourist(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Tourist not found"));
        }
        return ResponseEntity.ok(Map.of("message", "Tourist deleted successfully"));
    }
}
