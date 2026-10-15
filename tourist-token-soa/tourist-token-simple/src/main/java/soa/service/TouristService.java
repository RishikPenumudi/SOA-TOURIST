package soa.service;

import soa.models.Tourist;
import soa.repo.TouristRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristService {
    private final TouristRepository repository;

    @Value("${app.token}")
    private String validToken;

    public TouristService(TouristRepository repository) {
        this.repository = repository;
    }

    public boolean isValidToken(String token) {
        return token != null && token.equals(validToken);
    }

    public Tourist addTourist(Tourist tourist) {
        return repository.save(tourist);
    }

    public List<Tourist> getTourists() {
        return repository.findAll();
    }

    public Tourist getTourist(long id) {
        return repository.findById(id);
    }

    public Tourist updateTourist(long id, Tourist tourist) {
        if (repository.update(id, tourist) == 0) return null;
        tourist.setId(id);
        return tourist;
    }

    public boolean deleteTourist(long id) {
        return repository.delete(id) > 0;
    }
}
