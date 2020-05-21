package com.study;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HandlerGather {

	@Autowired
	Map<String, IHandler> handlerMap;
	
	@PostConstruct
	public void print(){
		System.out.println(handlerMap+"  Print-------------");
	}
}
