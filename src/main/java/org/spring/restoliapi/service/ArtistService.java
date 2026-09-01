package org.spring.restoliapi.service;

import org.spring.restoliapi.config.DatabaseManager;
import org.spring.restoliapi.model.Artist;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {

    private final DatabaseManager db;

    public ArtistService(DatabaseManager db) {
        this.db = db;
    }

    public List<Artist> findAll() {
        List<Artist> artists = new ArrayList<>();
        String sql = "SELECT id, name FROM artists";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                artists.add(new Artist(rs.getString("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return artists;
    }

    public Optional<Artist> findById(String id) {
        String sql = "SELECT id, name FROM artists WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Artist(rs.getString("id"), rs.getString("name")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public Artist save(Artist artist) {
        String sql = "INSERT INTO artists (id, name) VALUES (?, ?)";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, artist.id());
            ps.setString(2, artist.name());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return artist;
    }

    public void delete(String id) {
        String sql = "DELETE FROM artists WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
