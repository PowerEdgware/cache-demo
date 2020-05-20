### spring cache

1.默认的auto-configured cahce  

pom.xml

```
<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-cache</artifactId>
		</dependency>
```

org.springframework.cache.CacheManager实现：`org.springframework.cache.concurrent.ConcurrentMapCacheManager`


2.JCache (JSR-107)  
通过classpath下SPI加载，key:`javax.cache.spi.CachingProvider`  

```
<dependency>
			<groupId>org.hibernate</groupId>
			<artifactId>hibernate-jcache</artifactId>
		</dependency>
```

没有加入 javax.cache.spi.CachingProvider的SPI实现时，使用的还是：`org.springframework.cache.concurrent.ConcurrentMapCacheManager`

加入 ehcache3依赖包

```
<dependency>
			<groupId>org.ehcache</groupId>
			<artifactId>ehcache</artifactId>
		</dependency>
```
实现：`org.springframework.cache.jcache.JCacheCacheManager`,其内部包装了：ehcache下的实现 `Eh107CacheManager`

ehcache3 包下SPI的实现：`org.ehcache.jsr107.EhcacheCachingProvider`


3.EhCache 2.x  

classpath下存在`ehcache.xml` 或者配置：`spring.cache.ehcache.config=classpath:config/another-config.xml`
包 EhCache 2.x在类路径下
此时的CacheManager实现:`org.springframework.cache.ehcache.EhCacheCacheManager`  

依赖：

```
<dependency>
    <groupId>net.sf.ehcache</groupId>
    <artifactId>ehcache</artifactId>
    <version>2.10.6</version>
</dependency>
```

4.redis  

依赖包在类路径下，且没有使用ehcache  

```
<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-redis</artifactId>
		</dependency>
```
配置一些属性，application.properties

```
spring.cache.cache-names=cache1,cache2 ##cache名称，类似namespace
spring.cache.redis.time-to-live=600000 ##设置cache时间，mills
```

此时实现类：`org.springframework.data.redis.cache.RedisCacheManager`  


最好自定义 RedisCacheConfiguration  实现key val的序列化，默认就是string序列化和jdk的对象序列化

或者自定义：`RedisCacheManagerBuilderCustomizer `