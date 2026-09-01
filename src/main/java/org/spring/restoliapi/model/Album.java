package org.spring.restoliapi.model;

import java.time.LocalDate;

public record Album(String id, String title, String artistId, LocalDate releaseDate) {
}
