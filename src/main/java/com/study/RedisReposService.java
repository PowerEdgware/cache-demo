package com.study;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.study.repos.Address;
import com.study.repos.Person;
import com.study.repos.PersonRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisReposService {

	final PersonRepository personRepository;
	
	@PostConstruct
	void init() {
		basicCrudOperations();
	}
	
	void readme() {
		//Object-to-Hash conversion:RedisConverter
		//default:Converter 
	}
	
	public void basicCrudOperations() {
		Person andy=new Person();
		andy.setFirstname("andy");
		andy.setLastname("liu");
		Address address=new Address();
		address.setCity("Shanghai");
		address.setCountry("China");
		
		andy.setAddress(address);
		
		Person dbPerson=personRepository.save(andy);
		System.out.println(dbPerson);
		
		Optional<Person> opt=personRepository.findById(andy.getId());
		System.out.println(opt.get());
		
		
		personRepository.deleteById(dbPerson.getId());
		
		
	}
}
