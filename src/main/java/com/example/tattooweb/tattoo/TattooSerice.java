package com.example.tattooweb.tattoo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TattooSerice {
    private final TattooRepository tattooRepository;

    @Autowired
    public TattooSerice(TattooRepository tattooRepository) {
        this.tattooRepository = tattooRepository;
    }
    public List<Tattoo> getTattoos() {
        return tattooRepository.findAll();
    }

    public void addNewTattoo(Tattoo tattoo) {
        tattooRepository.save(tattoo);
    }
}
