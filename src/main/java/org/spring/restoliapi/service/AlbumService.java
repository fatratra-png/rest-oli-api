package org.spring.restoliapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import org.spring.restoliapi.model.Album;
import org.spring.restoliapi.repository.AlbumRepository;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public List<Album> findAll() {
        return albumRepository.findAll();
    }

    public Optional<Album> findById(String id) {
        return albumRepository.findById(id);
    }

    public List<Album> findByArtistId(String artistId) {
        return albumRepository.findByArtistId(artistId);
    }

    public Album save(Album album) {
        return albumRepository.save(album);
    }

    public Optional<Album> update(String id, Album album) {
        return albumRepository.update(id, album);
    }

    public void delete(String id) {
        albumRepository.delete(id);
    }
}