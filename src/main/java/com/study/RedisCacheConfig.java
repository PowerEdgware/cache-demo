package com.study;

import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

import io.netty.util.internal.StringUtil;

@Configuration
public class RedisCacheConfig {

	
	@Bean
	public RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties) {
		RedisCacheConfiguration redisCacheConfiguration=RedisCacheConfiguration.defaultCacheConfig();
		
		redisCacheConfiguration=redisCacheConfiguration.disableCachingNullValues()
		//redisCacheConfiguration.serializeKeysWith(keySerializationPair)
		.serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
		//Jackson2JsonRedisSerializer
		.entryTtl(cacheProperties.getRedis().getTimeToLive());
		
		if(!StringUtil.isNullOrEmpty(cacheProperties.getRedis().getKeyPrefix())) {
		return redisCacheConfiguration.prefixKeysWith(cacheProperties.getRedis().getKeyPrefix());
		}
		return redisCacheConfiguration;
	}
}
