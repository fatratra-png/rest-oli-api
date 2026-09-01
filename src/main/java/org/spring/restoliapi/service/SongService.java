package org.spring.restoliapi.service;

import org.spring.restoliapi.config.DatabaseManager;
import org.spring.restoliapi.model.Song;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SongService {

    private final DatabaseManager db;

    public SongService(DatabaseManager db) {
        this.db = db;
    }

    private Song mapSong(ResultSet rs) throws SQLException {
        return new Song(
                rs.getString("id"),
                rs.getString("title"),
                rs.getString("album_id"),
                rs.getDate("release_date").toLocalDate(),
                rs.getLong("duration_seconds")
        );
    }

    public List<Song> findAll() {
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT id, title, album_id, release_date, duration_seconds FROM songs";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                songs.add(mapSong(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return songs;
    }

    public Optional<Song> findById(String id) {
        String sql = "SELECT id, title, album_id, release_date, duration_seconds FROM songs WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapSong(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public List<Song> findByAlbumId(String albumId) {
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT id, title, album_id, release_date, duration_seconds FROM songs WHERE album_id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, albumId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    songs.add(mapSong(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return songs;
    }

    public Song save(Song song) {
        String sql = "INSERT INTO songs (id, title, album_id, release_date, duration_seconds) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, song.id());
            ps.setString(2, song.title());
            ps.setString(3, song.albumId());
            ps.setDate(4, java.sql.Date.valueOf(song.releaseDate()));
            ps.setLong(5, song.durationSeconds());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return song;
    }

    public void delete(String id) {
        String sql = "DELETE FROM songs WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
