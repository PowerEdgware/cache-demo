package com.study;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Object-to-Hash Mapping.
 * 
 * @author yu
 *
 */
@Service
@RequiredArgsConstructor
public class ObjectHashMapping {

	@NotNull
	final RedisTemplate<Object, Object> redisTemplate;// StringRedisTemplate

	HashOperations<Object, byte[], byte[]> hashOperations;
	HashMapper<Object, byte[], byte[]> mapper = new ObjectHashMapper();
	ObjectMapper jsonMapper = new ObjectMapper();

	@PostConstruct
	void init() {
		hashOperations = redisTemplate.opsForHash();

		String key = "HHHH";
		ImmutableMap<String, Object> personMap = ImmutableMap.of("firstname", "all", "lastname", "shang", "age", 90);
		Person person = jsonMapper.convertValue(personMap, Person.class);
		System.err.println("p=" + person);

		//writeHash(key, person);

		//person = loadHash(key);
		System.out.println(person);
	}

	public void writeHash(String key, Person person) {
		Map<byte[], byte[]> mappedHash = mapper.toHash(person);
		System.out.println("hashOperations=" + hashOperations.getClass());
		hashOperations.putAll(key, mappedHash);
	}

	public Person loadHash(String key) {
		Map<byte[], byte[]> loadedHash = hashOperations.entries(key);
		return (Person) mapper.fromHash(loadedHash);
	}

}

@Data
class Person {
	String firstname;
	String lastname;
	int age;
	Address address;
	Date date=Date.from(Instant.now());
	LocalDateTime localDateTime=LocalDateTime.now();
}

@Data
class Address {
	String city;
	String country;
}
