package org.spring.restoliapi.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import org.spring.restoliapi.model.Song;

@Repository
public class SongRepository {

    private final DatabaseConnection databaseConnection;

    public SongRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public List<Song> findAll() {
        String sql = "SELECT id, title, album_id, release_date, duration_seconds FROM songs ORDER BY title";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            return mapRows(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch songs", e);
        }
    }

    public Optional<Song> findById(String id) {
        String sql = "SELECT id, title, album_id, release_date, duration_seconds FROM songs WHERE id = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRow(resultSet));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch song " + id, e);
        }
    }

    public List<Song> findByAlbumId(String albumId) {
        String sql = "SELECT id, title, album_id, release_date, duration_seconds FROM songs WHERE album_id = ? ORDER BY id";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, albumId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return mapRows(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch songs for album " + albumId, e);
        }
    }

    public Song save(Song song) {
        String sql = "INSERT INTO songs (id, title, album_id, release_date, duration_seconds) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, song.id());
            statement.setString(2, song.title());
            statement.setString(3, song.albumId());
            statement.setDate(4, java.sql.Date.valueOf(song.releaseDate()));
            statement.setLong(5, song.durationSeconds());
            statement.executeUpdate();
            return song;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save song", e);
        }
    }

    public Optional<Song> update(String id, Song song) {
        String sql = "UPDATE songs SET title = ?, album_id = ?, release_date = ?, duration_seconds = ? WHERE id = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, song.title());
            statement.setString(2, song.albumId());
            statement.setDate(3, java.sql.Date.valueOf(song.releaseDate()));
            statement.setLong(4, song.durationSeconds());
            statement.setString(5, id);
            int updated = statement.executeUpdate();
            return updated == 0 ? Optional.empty() : findById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update song " + id, e);
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM songs WHERE id = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete song " + id, e);
        }
    }

    private List<Song> mapRows(ResultSet resultSet) throws SQLException {
        List<Song> songs = new ArrayList<>();
        while (resultSet.next()) {
            songs.add(mapRow(resultSet));
        }
        return songs;
    }

    private Song mapRow(ResultSet resultSet) throws SQLException {
        return new Song(
                resultSet.getString("id"),
                resultSet.getString("title"),
                resultSet.getString("album_id"),
                resultSet.getDate("release_date").toLocalDate(),
                resultSet.getLong("duration_seconds")
        );
    }
}