package com.study;

import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.StringUtils;


@Configuration
public class RedisCacheConfig {

	//RedisStandaloneConfiguration
	@Bean
	public RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties) {
		RedisCacheConfiguration redisCacheConfiguration=RedisCacheConfiguration.defaultCacheConfig();
		
		redisCacheConfiguration=redisCacheConfiguration.disableCachingNullValues()
		//redisCacheConfiguration.serializeKeysWith(keySerializationPair)
		.serializeValuesWith(SerializationPair.fromSerializer(RedisSerializer.json()))
		//Jackson2JsonRedisSerializer
		.entryTtl(cacheProperties.getRedis().getTimeToLive());
		
		if(!StringUtils.isEmpty(cacheProperties.getRedis().getKeyPrefix())) {
		return redisCacheConfiguration.prefixKeysWith(cacheProperties.getRedis().getKeyPrefix());
		}
		return redisCacheConfiguration;
	}
}
