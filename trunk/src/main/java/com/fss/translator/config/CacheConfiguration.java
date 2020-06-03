package com.fss.translator.config;

import java.util.Arrays;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * Cache configuration for Tranlator project 
 * @author ravinaganaboyina
 *
 */
public class CacheConfiguration {
	
	public static final String TRANSLATOR_DATA_CACHE = "translateDataCache";

	@Bean(name = "cacheManager")
	@Primary
	public CacheManager caffeineCacheManager() {

		SimpleCacheManager cacheManager = new SimpleCacheManager();

		CaffeineCache dataValidationsDefinitionCache = new CaffeineCache(TRANSLATOR_DATA_CACHE, 
				Caffeine.newBuilder()
				.maximumSize(400)
				.build());

		cacheManager.setCaches(Arrays.asList(dataValidationsDefinitionCache));

		return cacheManager;
	}

}
