package stenka.marcin.heroes.fraction.service;

import stenka.marcin.heroes.controller.servlet.exception.NotFoundException;
import stenka.marcin.heroes.fraction.entity.Fraction;
import stenka.marcin.heroes.fraction.repository.api.FractionRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FractionService {
    private final FractionRepository fractionRepository;

    public FractionService(FractionRepository fractionRepository) {
        this.fractionRepository = fractionRepository;
    }

    public Optional<Fraction> find(UUID id) {
        return fractionRepository.find(id);
    }

    public List<Fraction> findAll() {
        return fractionRepository.findAll();
    }

    public void create(Fraction fraction) {
        fractionRepository.create(fraction);
    }

    public void update(Fraction fraction) {
        fractionRepository.update(fraction);
    }

    public void delete(UUID id) {
        fractionRepository.delete(fractionRepository.find(id).orElseThrow(NotFoundException::new));
    }
}
