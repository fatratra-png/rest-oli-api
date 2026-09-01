package org.spring.restoliapi.controller;

import org.spring.restoliapi.model.Artist;
import org.spring.restoliapi.service.ArtistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public List<Artist> getAll() {
        return artistService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artist> getById(@PathVariable String id) {
        return artistService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Artist create(@RequestBody Artist artist) {
        return artistService.save(artist);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        artistService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
