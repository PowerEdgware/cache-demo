package com.study;

import java.io.IOException;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

@EnableCaching
@SpringBootApplication
public class CahceApp {

	void cache(){
		//RedisCacheManager
//		CacheProperties
		//ConcurrentMapCacheManager
		//JCacheCacheManager 
		//EhcacheCachingProvider
		//RedisCacheConfiguration 
		//JCacheManagerCustomizer 
		//EhCacheCacheManager
	}
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context=SpringApplication.run(CahceApp.class, args);
		
//		CachingProvider cachingProvider=context.getBean(CachingProvider.class);
//		System.out.println(cachingProvider.getClass());
//		System.out.println(cachingProvider.getCacheManager());
		try {
			CacheManager cacheManager=context.getBean(CacheManager.class);
			System.out.println(cacheManager);//org.springframework.cache.jcache.JCacheCacheManager@3f6cce7f
			Cache cache=cacheManager.getCache("mycachexx");
			Object nativeCache=cache.getNativeCache();//DefaultRedisCacheWriter
			System.out.println(nativeCache);
			DmeoCache demoC=new DmeoCache();
			demoC.id=100;
			demoC.name="allger";
			cache.put("cachedemo", demoC);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			System.in.read();
			context.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
class DmeoCache{
	String name="";
	int id;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
