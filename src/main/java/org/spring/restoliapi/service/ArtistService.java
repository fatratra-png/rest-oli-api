package org.spring.restoliapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import org.spring.restoliapi.model.Artist;
import org.spring.restoliapi.repository.ArtistRepository;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public List<Artist> findAll() {
        return artistRepository.findAll();
    }

    public Optional<Artist> findById(String id) {
        return artistRepository.findById(id);
    }

    public Artist save(Artist artist) {
        return artistRepository.save(artist);
    }

    public Optional<Artist> update(String id, Artist artist) {
        return artistRepository.update(id, artist);
    }

    public void delete(String id) {
        artistRepository.delete(id);
    }
}