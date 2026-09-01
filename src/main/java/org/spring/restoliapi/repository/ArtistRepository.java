package org.spring.restoliapi.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import org.spring.restoliapi.model.Artist;

@Repository
public class ArtistRepository {

    private final DatabaseConnection databaseConnection;

    public ArtistRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public List<Artist> findAll() {
        String sql = "SELECT id, name FROM artists ORDER BY name";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            return mapRows(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch artists", e);
        }
    }

    public Optional<Artist> findById(String id) {
        String sql = "SELECT id, name FROM artists WHERE id = ?";
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
            throw new RuntimeException("Failed to fetch artist " + id, e);
        }
    }

    public Artist save(Artist artist) {
        String sql = "INSERT INTO artists (id, name) VALUES (?, ?)";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, artist.id());
            statement.setString(2, artist.name());
            statement.executeUpdate();
            return artist;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save artist", e);
        }
    }

    public Optional<Artist> update(String id, Artist artist) {
        String sql = "UPDATE artists SET name = ? WHERE id = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, artist.name());
            statement.setString(2, id);
            int updated = statement.executeUpdate();
            return updated == 0 ? Optional.empty() : findById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update artist " + id, e);
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM artists WHERE id = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete artist " + id, e);
        }
    }

    private List<Artist> mapRows(ResultSet resultSet) throws SQLException {
        List<Artist> artists = new ArrayList<>();
        while (resultSet.next()) {
            artists.add(mapRow(resultSet));
        }
        return artists;
    }

    private Artist mapRow(ResultSet resultSet) throws SQLException {
        return new Artist(resultSet.getString("id"), resultSet.getString("name"));
    }
}