package org.spring.restoliapi.controller;

import org.spring.restoliapi.model.Album;
import org.spring.restoliapi.service.AlbumService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping
    public List<Album> getAll() {
        return albumService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> getById(@PathVariable String id) {
        return albumService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/artist/{artistId}")
    public List<Album> getByArtistId(@PathVariable String artistId) {
        return albumService.findByArtistId(artistId);
    }

    @PostMapping
    public Album create(@RequestBody Album album) {
        return albumService.save(album);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        albumService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
