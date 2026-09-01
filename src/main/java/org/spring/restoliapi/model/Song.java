package org.spring.restoliapi.model;

import java.time.LocalDate;

public record Song(String id, String title, String albumId, LocalDate releaseDate, long durationSeconds) {
}
