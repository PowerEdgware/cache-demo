package com.study;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JacksonHashMapping {
	@NotNull
	final RedisTemplate<Object, Object> redisTemplate;

	HashOperations<Object, String, Object> hashOperations;
	// hashKey,key,value
	HashMapper<Object, String, Object> mapper = new Jackson2HashMapper(true);//flatten:true
	ObjectMapper jsonMapper = new ObjectMapper();

	@PostConstruct
	void init() {
		setSerializer();

		hashOperations = redisTemplate.opsForHash();

		String key = "jsonKey";
		key += new Random().nextInt(100);
		ImmutableMap<String, Object> personMap = ImmutableMap.of("firstname", "all", "lastname", "shang", "age", 90);
		Person person = jsonMapper.convertValue(personMap, Person.class);
		ImmutableMap<String, Object> addressMap = ImmutableMap.of("city", "shanghai", "country", "China");
		Address address = jsonMapper.convertValue(addressMap, Address.class);
		person.setAddress(address);

		System.err.println("p=" + person);

		normalMapping(key, person);

		person = loadHash(key);
		System.out.println(person);

		String objKey="obj:" + key;
		saveObject(objKey, person);
		
		// person=loadObject(objKey);
		//System.out.println("ObjPerson="+person);
	}
	
	private void setSerializer() {
		redisTemplate.setKeySerializer(RedisSerializer.string());
		GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(
				JsonTypeInfo.Id.CLASS.name());
		redisTemplate.setValueSerializer(serializer);
		redisTemplate.setHashKeySerializer(RedisSerializer.string());
		redisTemplate.setHashValueSerializer(RedisSerializer.json());
	}

	//SAVE OK
	public void saveObject(String key, Person person) {
		redisTemplate.boundValueOps(key).set(person, 300, TimeUnit.SECONDS);
	}
	//NOT SUPPORTED
	public Person loadObject(String objKey) {
		return (Person) redisTemplate.boundValueOps(objKey).get();
	}



	/**
	 * Flattening:false
	 * Hash Field	Value
		firstname 	Jon
		
		lastname	Snow
		
		address	{ "city" : "Castle Black", "country" : "The North" }
		
		date	1561543964015
		
		localDateTime	2018-01-02T12:13:14
	 * 
	 * @param key
	 * @param person
	 */
	public void normalMapping(String key, Person person) {
		// redisTemplate.boundHashOps(key).putAll(m);
		// redisTemplate.opsForHash().putAll(key, m);
		Map<String, Object> mappedHash = mapper.toHash(person);
		hashOperations.putAll(key, mappedHash);
	}

	public Person loadHash(String key) {
		Map<String, Object> loadedHash = hashOperations.entries(key);
		return (Person) mapper.fromHash(loadedHash);
	}

}
