package com.study.repos;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import lombok.Data;

@Data
@RedisHash(value = "address")
@TypeAlias("addr")//_class  field will be addr instead of qualified class name
public class Address {

	 @Id String id;
	String city;
	String country;
	
	@TimeToLive Long timeout=60*10L;

}
