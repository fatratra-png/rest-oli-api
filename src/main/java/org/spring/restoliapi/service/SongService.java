package org.spring.restoliapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import org.spring.restoliapi.model.Song;
import org.spring.restoliapi.repository.SongRepository;

@Service
public class SongService {

    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public List<Song> findAll() {
        return songRepository.findAll();
    }

    public Optional<Song> findById(String id) {
        return songRepository.findById(id);
    }

    public List<Song> findByAlbumId(String albumId) {
        return songRepository.findByAlbumId(albumId);
    }

    public Song save(Song song) {
        return songRepository.save(song);
    }

    public Optional<Song> update(String id, Song song) {
        return songRepository.update(id, song);
    }

    public void delete(String id) {
        songRepository.delete(id);
    }
}