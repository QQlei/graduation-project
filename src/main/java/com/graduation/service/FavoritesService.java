package com.graduation.service;

import com.graduation.domain.Favorites;

public interface FavoritesService {
	
	public Favorites saveFavorites(Long userId, Long count, String name);

}
