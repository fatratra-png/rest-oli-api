package org.spring.restoliapi.controller;

import org.spring.restoliapi.model.Song;
import org.spring.restoliapi.service.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/songs")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping
    public List<Song> getAll() {
        return songService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> getById(@PathVariable String id) {
        return songService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/album/{albumId}")
    public List<Song> getByAlbumId(@PathVariable String albumId) {
        return songService.findByAlbumId(albumId);
    }

    @PostMapping
    public Song create(@RequestBody Song song) {
        return songService.save(song);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        songService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
