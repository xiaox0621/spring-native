Spring Boot project with Spring HATEOAS.

Original Example taken from [spring-projects/spring-hateoas-examples](https://github.com/spring-projects/spring-hateoas-examples/tree/main/hypermedia).

To build and run the native application packaged in a lightweight container:
```
mvn spring-boot:build-image
docker-compose up
```

As an alternative, you can use `build.sh` (with a local GraalVM installation or combined with
`run-dev-container.sh` at the root of `spring-native` project). See also the related issue
[Take advantage of Paketo dev-oriented images](https://github.com/spring-projects-experimental/spring-native/issues/227).
