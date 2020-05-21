package com.study.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, String>{

	List<Person> findByFirstname(String firstname);
}
