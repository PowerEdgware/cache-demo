package com.study.repos;


import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Data;

@Data
@RedisHash("people")
public class Person {

  @Id String id;
  
  @Indexed String firstname;//redis indexes
  String lastname;
  Address address;
  
  @TimeToLive Long timeout=60*10L;
  
  Date date=Date.from(Instant.now());
	LocalDateTime localDateTime=LocalDateTime.now();
}
