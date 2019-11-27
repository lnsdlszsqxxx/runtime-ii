# How to use redis in Spring Boot

## What is cache

A cache server is a dedicated network server or service acting as a server that saves Web pages or other Internet request/content locally. By placing previously requested information in temporary storage, or cache, a cache server both speeds up access to data and reduces demand on bandwidth and repeatable computing cost.

## Why use cache

1. saving computing power
2. reducing system hotspot (reducing response pressure)
3. durability

## [Cache strategy](https://codeahoy.com/2017/08/11/caching-strategies-and-how-to-choose-the-right-one/)
1.  Cache-Aside
2.  Read-Through Cache
3.  Write-Through Cache
4.  Write-Around
5.  Write-Back

## Run a redis server from docker
For demo purpose, we run a redis server locally in docker with the following two commands:
```
 docker pull redis
 docker run -p 6379:6379 --name some-redis redis
```

When the redis server is build successfully, you can access the server by the following command:
```
 docker exec -it some-redis /bin/bash
```

## Some redis commands

Once we access the server sucessfull, we can use the following commands:
```
 redis-cli keys *
 redis-cli FLUSHDB
 redis-cli info stats | grep 'keyspace_*'
```

Explanation:
- redis-cli keys *: check current data in redis database
- redis-cli FLUSHDB: clean redis database
- redis-cli info stats | grep 'keyspace_*': check miss and hit. 

## Setup redis in Spring framework

1. add dependency in pom.xml
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```
2. in file application.properties (if you don't have this file, create one under src/main/resources/) add the following, so Spring can find redis server:
```
#redis config
spring.cache.type=redis
spring.redis.host=localhost
spring.redis.port=6379
```

3. in file AppInitializer add @EnableCaching in front of the class name

4. add the following in front of the controller you want to use cache:
```
@Cacheable(value = "departments")
@CachePut(value = "departments", key = "#department.id", unless = “#department.name==null”)
```
Explanation:
- add _@Cacheable(value = "departments")_ in front of GET methods for a Read-Through Cache
- add _@CachePut(value = "departments", key = "#department.id", unless = “#department.name==null”)_ in front of POST methods for a Write-Through Cache. Key word _unless_ will exclude some scenarios. 

5. Model classes need to implement Serializable (object needs to be serializable first so the object can be transferred to cache), for example:

```
@Entity  
@Table(name="departments")  
public class Department implements Serializable {
...
}
```

Or other wise you may get the following error:

```
Request processing failed; nested exception is org.springframework.data.redis.serializer.SerializationException: Cannot deserialize; nested exception is org.springframework.core.serializer.support.SerializationFailedException: Failed to deserialize payload. Is the byte array a result of corresponding serialization for DefaultDeserializer?; nested exception is java.io.InvalidClassException: com.ascending.training.model.Department; class invalid for deserialization
```

## An example for Read-Through Cache
1. add @Cacheable(value = "departments") in front of getDepartmentByName method in the DepartmentController

```
@Cacheable(value = "departments")  
@GetMapping(value = "/{deptName}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})  
public Department getDepartmentByName(@PathVariable(name = "deptName") String deptName1){  
    return departmentService.getDepartmentByName(deptName1);  
}
```

2. start a redis server in docker container, and run _docker exec -it some-redis /bin/bash_ to get into the redis server. Run _redis-cli keys *_ and find that there is no key in it. Run _redis-cli info stats | grep 'keyspace_*'_ and 0 misses/0 hits
3. start Spring Boot and then in postman submit a request to get department by department name as usual.
4. run _redis-cli info stats | grep 'keyspace_*'_ and find that 1 misses/0 hits, as redis server is empty so it misses once. If you send the same request again by postman and run _redis-cli info stats | grep 'keyspace_*'_, you will find that 1 misses/1 hits, because after the first request redis has stored the data in the database and for the second request the program gets the data from the redis server successfully (1 hit). If you check the server end output from the IntelliJ IDE, you will find that there is no Hibernate output, which proves that the second request gets data from cache instead of database. You may also notice that the second time getting data is way faster than the first time.
