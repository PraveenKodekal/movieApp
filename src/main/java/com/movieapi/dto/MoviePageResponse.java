package com.movieapi.dto;

import java.util.List;

/**
 * This class is for pagination
 */
public record MoviePageResponse(List<MovieDto> movieDtos,
									Integer pageNumber,
									Integer pageSize,
									long totalElements,
									int totalPages,
									boolean isLast) {

}
