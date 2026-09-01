package org.spring.restoliapi.service;

import org.spring.restoliapi.config.DatabaseManager;
import org.spring.restoliapi.model.Album;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {

    private final DatabaseManager db;

    public AlbumService(DatabaseManager db) {
        this.db = db;
    }

    private Album mapAlbum(ResultSet rs) throws SQLException {
        return new Album(
                rs.getString("id"),
                rs.getString("title"),
                rs.getString("artist_id"),
                rs.getDate("release_date").toLocalDate()
        );
    }

    public List<Album> findAll() {
        List<Album> albums = new ArrayList<>();
        String sql = "SELECT id, title, artist_id, release_date FROM albums";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                albums.add(mapAlbum(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return albums;
    }

    public Optional<Album> findById(String id) {
        String sql = "SELECT id, title, artist_id, release_date FROM albums WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapAlbum(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public List<Album> findByArtistId(String artistId) {
        List<Album> albums = new ArrayList<>();
        String sql = "SELECT id, title, artist_id, release_date FROM albums WHERE artist_id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, artistId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    albums.add(mapAlbum(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return albums;
    }

    public Album save(Album album) {
        String sql = "INSERT INTO albums (id, title, artist_id, release_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, album.id());
            ps.setString(2, album.title());
            ps.setString(3, album.artistId());
            ps.setDate(4, java.sql.Date.valueOf(album.releaseDate()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return album;
    }

    public void delete(String id) {
        String sql = "DELETE FROM albums WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
