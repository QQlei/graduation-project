package com.graduation.service;

import com.graduation.domain.Config;

public interface ConfigService {
	
	public Config saveConfig(Long userId, String favoritesId);

	public void updateConfig(Long id, String type, String defaultFavorites);
}
