

## spring-boot-fat-jar-jsp-sample


* spring boot(4.0.0) fat jar/exploded directory needs extra wiring to expose `META-INF/resources` for JSP rendering.
* [spring-boot-sample-web-jsp](https://github.com/spring-projects/spring-boot/tree/v1.5.6.RELEASE/spring-boot-samples/spring-boot-sample-web-jsp) only support war packaging.

This jsp sample support fat jar and `META-INF/resources` in fat jar/exploded directory.


## How

0. Add jsp/jstl dependencies:

	```xml
	    <dependency>
	        <groupId>org.apache.tomcat.embed</groupId>
	        <artifactId>tomcat-embed-jasper</artifactId>
	    </dependency>
	    <dependency>
	        <groupId>jakarta.servlet.jsp.jstl</groupId>
	        <artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
	    </dependency>
	    <dependency>
	        <groupId>org.glassfish.web</groupId>
	        <artifactId>jakarta.servlet.jsp.jstl</artifactId>
	    </dependency>
	```


0. In servlet 3.0 specification, the app can put static resources in `META-INF/resources`

0. `StaticResourceConfigurer` to add fat jar into tomcat `ResourceSet`.


## Run

* Run as fat jar:

	```bash
	mvn clean package -DskipTests
	java -jar jsp-start/target/jsp-start-0.0.1-SNAPSHOT.jar
	```

	open http://localhost:8080/

* Run in exploded directory

	```bash
	mvn clean package -DskipTests
	cd jsp-start/target/
	unzip -d start jsp-start-0.0.1-SNAPSHOT.jar
	cd start/
	java org.springframework.boot.loader.launch.PropertiesLauncher
	```

	open http://localhost:8080/
