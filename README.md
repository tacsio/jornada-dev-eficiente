<h1 align="center">
    <img alt="Jornada Dev Eficiente" title="#JornadaDevEficente" src=".github/logo.png" width="250px" />
</h1>

<h4 align="center"> 
	Jornada Dev Eficiente 🚀
</h4>

![GitHub repo size](https://img.shields.io/github/repo-size/tacsio/jornada-dev-eficiente?color=%2331acbf)
![GitHub language count](https://img.shields.io/github/languages/count/tacsio/jornada-dev-eficiente?color=%2331acbf)
![GitHub top language](https://img.shields.io/github/languages/top/tacsio/jornada-dev-eficiente?color=%2331acbf)
![GitHub last commit](https://img.shields.io/github/last-commit/tacsio/jornada-dev-eficiente?color=%2331acbf)
[![GitHub issues](https://img.shields.io/github/issues-raw/tacsio/jornada-dev-eficiente?color=%2331acbf)](https://github.com/tacsio/jornada-dev-eficiente/issues)
[![GitHub contributors](https://img.shields.io/github/contributors/tacsio/jornada-dev-eficiente?color=%2331acbf)](https://github.com/tacsio/jornada-dev-eficiente/graphs/contributors)
[![GitHub commit activity](https://img.shields.io/github/commit-activity/w/tacsio/jornada-dev-eficiente?color=%2331acbf)](https://github.com/tacsio/jornada-dev-eficiente/graphs/commit-activity)
![Maintenance](https://img.shields.io/maintenance/yes/2020?color=%2331acbf)

## :soccer: Api Bolão ![Java CI with Gradle - Bolão API](https://github.com/tacsio/jornada-dev-eficiente/workflows/Java%20CI%20with%20Gradle%20-%20Bol%C3%A3o%20API/badge.svg)

Implementar a API necessária rodar uma aplicação que possibilite pessoas criarem bolões. No final do treinamento vamos ter passado por atividades que são supostamente mais simples, como cadastros, mas também vamos lidar situações um pouco mais complicadas, como criação de convites de participantes, associação de pessoas a grupos, palpites, validação de palpites etc.

### :pushpin: Stack

- [Java 14][java14] - JDK 14 is the open-source reference implementation of version 14 of the Java SE Platform as specified by by JSR 389 in the Java Community Process.
- [Spring Boot][spring] - Spring makes programming Java quicker, easier, and safer for everybody. Spring’s focus on speed, simplicity, and productivity has made it the world's most popular Java framework.
- [Spring Data JPA][springdata] - Spring Data JPA, part of the larger Spring Data family, makes it easy to easily implement JPA based repositories.
- [Spring Security][springsecurity] - Spring Security is a powerful and highly customizable authentication and access-control framework.
- [PostgreSQL][postgres] - PostgreSQL: The World's Most Advanced Open Source Relational Database
- [Lombok][lombok] - Project Lombok is a java library that automatically plugs into your editor and build tools, spicing up your java.


#### :heavy_dollar_sign: Run Scripts
```bash
./gradlew boot:run
```

## :books: API Casa do Código ![Java CI with Maven - API Casa do Código](https://github.com/tacsio/jornada-dev-eficiente/workflows/Java%20CI%20with%20Maven%20-%20API%20Casa%20do%20C%C3%B3digo/badge.svg)

Desenvolver uma api para suportar parte do funcionamento da casa do código.

### :pushpin: Stack
- [Java 11][java11] - JDK 11 is the open-source reference implementation of version 11 of the Java SE Platform as specified by by JSR 384 in the Java Community Process.
- [Quarkus][quarkus] - A Kubernetes Native Java stack tailored for OpenJDK HotSpot and GraalVM, crafted from the best of breed Java libraries and standards.
- [H2 Database][h2] - The Java SQL database. Very fast, open source, JDBC API. Embedded and server modes; in-memory databases
- [OpenAPI][openapi] - The OpenAPI Specification: a broadly adopted industry standard for describing modern APIs.
- [Lombok][lombok] - Project Lombok is a java library that automatically plugs into your editor and build tools, spicing up your java.


#### :package: Extensions
1. RESTEasy JAX-RS
2. RESTEasy JSON-B
3. RESTEasy JSON-P
4. Hibernate Validator
5. SmallRye OpenAPI
6. Hibernate ORM with Panache
7. JDBC Driver - H2


#### :heavy_dollar_sign: Run Scripts
```bash
./mvnw quarkus:dev
```

## :credit_card: Api Pagamentos ![Java CI with Gradle - API Pagamentos](https://github.com/tacsio/jornada-dev-eficiente/workflows/Java%20CI%20with%20Gradle%20-%20API%20Pagamentos/badge.svg)

Implementar uma aplicação específica para processar pagamentos relativos a um aplicativo famoso de pedido de comida, o YFood.
A ideia é que nossa aplicação liste as possíveis formas de pagamento para uma pessoa dado um restaurante e depois que a gente processe o pagamento em função da escolha final da pessoa.
Esse é um serviço crucial no contexto do YFood. É a conclusão da experiência do usuário e precisamos atender o máximo de pedidos que for possível sob as condições mais extremas. 

### :pushpin: Stack

- [Java 15][java15] - JDK 15 is the open-source reference implementation of version 15 of the Java SE Platform, as specified by by JSR 390 in the Java Community Process.
- [Spring Boot 2.4.0][spring] - Spring makes programming Java quicker, easier, and safer for everybody. Spring’s focus on speed, simplicity, and productivity has made it the world's most popular Java framework.
- [Spring Cloud OpenFeign][openfeign] - Feign is a declarative web service client. It makes writing web service clients easier.
- [Spring Data JPA][springdata] - Spring Data JPA, part of the larger Spring Data family, makes it easy to easily implement JPA based repositories.
- [Spring Validation][springvalidation] - Starter for using Java Bean Validation with Hibernate Validator
- [H2][h2] - PostgreSQL: The World's Most Advanced Open Source Relational Database



#### :heavy_dollar_sign: Run Scripts
```bash
./gradlew boot:run
```

## :package: Api Mercado Livre ![Java CI with Maven - API Mercado Livre](https://github.com/tacsio/jornada-dev-eficiente/workflows/Java%20CI%20with%20Maven%20-%20API%20Mercado%20Livre/badge.svg)

Implementar uma parte do mercado livre. 

### :pushpin: Stack

- [Java 15][java15] - JDK 15 is the open-source reference implementation of version 15 of the Java SE Platform, as specified by by JSR 390 in the Java Community Process.
- [Spring Boot 2.4.2][spring] - Spring makes programming Java quicker, easier, and safer for everybody. Spring’s focus on speed, simplicity, and productivity has made it the world's most popular Java framework.
- [Spring Data JPA][springdata] - Spring Data JPA, part of the larger Spring Data family, makes it easy to easily implement JPA based repositories.
- [Spring Validation][springvalidation] - Starter for using Java Bean Validation with Hibernate Validator
- [Spring Security][springsecurity] - Spring Security is a powerful and highly customizable authentication and access-control framework. It is the de-facto standard for securing Spring-based applications.
- [H2][h2] - PostgreSQL: The World's Most Advanced Open Source Relational Database



#### :heavy_dollar_sign: Run Scripts
```bash
./mvn spring-boot:run
```


## :bullettrain_side: Contributing

1. Fork it
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Added some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create new Pull Request


[java14]: https://openjdk.java.net/projects/jdk/14/
[java15]: https://openjdk.java.net/projects/jdk/15/
[java11]: https://openjdk.java.net/projects/jdk/11/

[spring]: https://spring.io/
[springdata]: https://spring.io/projects/spring-data-jpa
[springsecurity]: https://spring.io/projects/spring-security
[postgres]: https://www.postgresql.org/
[lombok]: https://projectlombok.org/

[quarkus]: http://quarkus.io/
[h2]: http://www.h2database.com/html/main.html
[openapi]: https://www.openapis.org/

[nodejs]: https://nodejs.org
[reactjs]: https://reactjs.org/
[sqlite]:https://www.sqlite.org/index.html
[axios]: https://github.com/axios/axios
[expo]: https://expo.io/
[reactnative]: https://reactnative.dev/

[springvalidation]: https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-2.3-Release-Notes#validation-starter-no-longer-included-in-web-starters
[openfeign]: https://cloud.spring.io/spring-cloud-openfeign/reference/html/
