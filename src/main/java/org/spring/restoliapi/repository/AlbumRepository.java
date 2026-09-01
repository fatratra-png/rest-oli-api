package org.spring.restoliapi.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import org.spring.restoliapi.model.Album;

@Repository
public class AlbumRepository {

    private final DatabaseConnection databaseConnection;

    public AlbumRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public List<Album> findAll() {
        String sql = "SELECT id, title, artist_id, release_date FROM albums ORDER BY title";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            return mapRows(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch albums", e);
        }
    }

    public Optional<Album> findById(String id) {
        String sql = "SELECT id, title, artist_id, release_date FROM albums WHERE id = ?";
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
            throw new RuntimeException("Failed to fetch album " + id, e);
        }
    }

    public List<Album> findByArtistId(String artistId) {
        String sql = "SELECT id, title, artist_id, release_date FROM albums WHERE artist_id = ? ORDER BY release_date";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, artistId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return mapRows(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch albums for artist " + artistId, e);
        }
    }

    public Album save(Album album) {
        String sql = "INSERT INTO albums (id, title, artist_id, release_date) VALUES (?, ?, ?, ?)";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, album.id());
            statement.setString(2, album.title());
            statement.setString(3, album.artistId());
            statement.setDate(4, java.sql.Date.valueOf(album.releaseDate()));
            statement.executeUpdate();
            return album;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save album", e);
        }
    }

    public Optional<Album> update(String id, Album album) {
        String sql = "UPDATE albums SET title = ?, artist_id = ?, release_date = ? WHERE id = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, album.title());
            statement.setString(2, album.artistId());
            statement.setDate(3, java.sql.Date.valueOf(album.releaseDate()));
            statement.setString(4, id);
            int updated = statement.executeUpdate();
            return updated == 0 ? Optional.empty() : findById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update album " + id, e);
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM albums WHERE id = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete album " + id, e);
        }
    }

    private List<Album> mapRows(ResultSet resultSet) throws SQLException {
        List<Album> albums = new ArrayList<>();
        while (resultSet.next()) {
            albums.add(mapRow(resultSet));
        }
        return albums;
    }

    private Album mapRow(ResultSet resultSet) throws SQLException {
        return new Album(
                resultSet.getString("id"),
                resultSet.getString("title"),
                resultSet.getString("artist_id"),
                resultSet.getDate("release_date").toLocalDate()
        );
    }
}