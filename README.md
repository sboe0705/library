# Die Bibliothek

## Architektur

### Users Application

Benutzer anzeigen, anlegen und löschen

## Spring Boot

**TODO**

Quelle an hervorragenden Tutorials:

- [Getting Started Guides](https://spring.io/guides/)
- [Baeldung / Spring Boot](https://www.baeldung.com/spring-boot)

### Projekt-Setup

Ein Spring Boot - Projekt kann leicht über den [Spring Initializr](https://start.spring.io/) zusammengestellt und vorkonfiguriert heruntergeladen werden. Nach der Wahl der Programmiersprache, des Build-Tools und der Namenskonfiguration können noch benötigte Spring Dependencies ausgewählt werden. Im Folgenden eine kleine Auswahl der gängisten:

| Dependency           | Beschreibung                                                                                                                                   |
| ---                  | ---                                                                                                                                            |
| Spring Web           | Build web, including RESTful, applications using Spring MVC. Uses Apache Tomcat as the default embedded container.                             |
| Spring Security      | Highly customizable authentication and access-control framework for Spring applications.                                                       |
| Spring Data JPA      | Persist data in SQL stores with Java Persistence API using Spring Data and Hibernate.                                                          |
| PostgreSQL Driver    | A JDBC and R2DBC driver that allows Java programs to connect to a PostgreSQL database using standard, database independent Java code.          |
| Flyway Migration     | Version control for your database so you can migrate from any version (incl. an empty database) to the latest version of the schema.           |
| H2 Database          | Provides a fast in-memory database that supports JDBC API and R2DBC access, with a small (2mb) footprint.                                      |
| Spring Boot Actuator | Supports built in (or custom) endpoints that let you monitor and manage your application - such as application health, metrics, sessions, etc. |
| Spring Boot DevTools | Provides fast application restarts, LiveReload, and configurations for enhanced development experience.                                        |

Das konfigurierte Setup kann nach der Auswahl als ZIP heruntergeladen, entpackt und direkt gebaut werden. Bei einigen Dependencies gibt es schon Hello-World - Implementierungen, die den Einstieg noch weiter erleichern.

### Erste Schritte

Die Einstiegsklasse in einer Spring Boot - Applikation ist in der Regel eine *Application.java, z.B. [UsersApplication.java](https://github.com/sboe0705/users/blob/01d8d0e7ce28ed4c1f5d025523b692a5c28334ba/src/main/java/de/sboe0705/users/UsersApplication.java). Hier steht die ``main`` - Methode, die beim Starten der JAR-Datei geladen wird und die den gesamten Spring-Kontext initialisiert. Neben der benötigten ``@SpringBootApplication`` - Annotation können später, je nach Bedarf, noch weitere Annotationen folgen, z.B. für das Aktivieren von Scheduler- oder Security-Modulen.

Hervorragende Tutorials zum Einstieg:

- [Building an Application with Spring Boot](https://spring.io/guides/gs/spring-boot/)
- [Spring Boot Tutorial – Bootstrap a Simple Application](https://www.baeldung.com/spring-boot-start) (komplexer mit persistent, REST-Service und Security)

### Beans und Services

**TODO**

### Automatisierte Tests

**TODO**

### Application Configuration

**TODO**

https://docs.spring.io/spring-boot/docs/2.1.13.RELEASE/reference/html/boot-features-external-config.html

## REST-Services

**TODO**

Hervorragende Tutorials zum Einstieg:

- (Building a RESTful Web Service)[https://spring.io/guides/gs/rest-service/]

## Persistierung

### Datenbank-Anbindung

**TODO**

application-generate-ddl.yaml

https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config.files.profile-specific

H2 Console

https://docs.spring.io/spring-boot/docs/3.1.x/reference/html/data.html#data.sql.h2-web-console

H2-Console: https://docs.spring.io/spring-boot/docs/current/reference/html/data.html#data.sql.h2-web-console

SCRIPT NODATA

https://stackoverflow.com/questions/5469881/any-easy-way-to-generate-a-build-script-from-an-h2-database

### Objekt-Relationales Mapping (ORM)

Über ein Objekt-Relationales Mapping können Datenbank-Tabllen mit Java-Klassen verknüpft werden. So können Daten geladen und gespeichert werden, ohne dass SQL-Anweisungen geschrieben werden müssen. Spring bietet entsprechende Services, die diese Operationen ausschließlich im Java-Code durchführen lassen.

Ein einfaches Beispiel ist in der Klasse **_User_** umgesetzt, siehe [User.java](https://github.com/sboe0705/users/blob/f1f40573166cdd0c4a60a7c7daacec7a46ec61d8/src/main/java/de/sboe0705/users/model/User.java). Über entsprechende Annotationen werden die Java-Klassen mit den für das ORM notwendigen Informationen angereichert. Das Framework generiert standardmäßig für jede Entitäten-Klasse eine Tabelle mit entsprechenden Spalten für jedes enthaltene Feld. Im Folgenden sind die wichtigsten aufgeführt:

| Annotation | Beschreibung                                                                                                                        |
| ---        | ---                                                                                                                                 |
| @Entity    | Kennzeichnet eine Java-Klasse als Datenbank-Entität                                                                                 |
| @Table     | Enthält zusätzliche Angaben zur Tabelle für diese Entität z.B. kann man den Tabellennamen explizit definieren (``name = "_user"``). |
| @Id        | Definiert ein Feld der Java-Klasse als Primärschlüssel.                                                                                          |
| @Column    | Enthält zusätzliche Angaben zur Spalte für dieses Feld z.B. kann ein Feld als Pflichfeld (``nullable = false``) definiert werden.   |

Weiterführende Informationen findet ihr hier:

- [Spring Data JPA / Getting Started](https://docs.spring.io/spring-data/jpa/reference/jpa/getting-started.html)
- [Baeldung / Defining JPA Entities](https://www.baeldung.com/jpa-entities)

### CrudRepositories

Über die sogenannten CRUD-Repositories (**C**reate, **R**ead, **U**pdate, **D**elete) können Daten über Java-Objekte aus der Datenbank gelesen und in die Datenbank geschrieben werden. Die von Spring angebotenen Interfaces bieten bereits eine Menge an häufig benötigten Methoden, die out-of-the-box direkt verwendet werden können.

Dazu muss lediglich ein Interface erstellt werden, welches von ``CrudRepository`` erbt und per _Generics_ der entsprechenden Entität samt ihres Primärschlüssel-Typs zugewiesen wird, siehe [UserRepository.java](https://github.com/sboe0705/users/blob/f1f40573166cdd0c4a60a7c7daacec7a46ec61d8/src/main/java/de/sboe0705/users/data/UserRepository.java). 

Die CrudRepositories können um eigene Methoden und Abfragen auf unterschiedliche Weisen erweitert werden: Zum Einen können sprechende Interface-Methoden hinzugefügt werden, die entsprechenden SQL-Statements entsprechen. Zum Anderen können Methoden hinzugefügt und mit entsprechenden Queries annotiiert werden, die die Datenbank-Abfragen entweder über HSQL oder gar reines SQL durchführen. Eine Beispiel-Implementierung ist hier zu finden: [RentRepository.java](https://github.com/sboe0705/rentals/blob/32e19bc0b3d30d8be6bb996c42790259e607011f/rentals-core/src/main/java/de/sboe0705/rentals/data/RentRepository.java#L17).

Weiterführende Dokumentation:

- [Spring Boot / Spring Data JPA Repositories](https://docs.spring.io/spring-boot/docs/current/reference/html/data.html#data.sql.jpa-and-spring-data.repositories)
- [Spring Data Commons / ... / Defining Query Methods](https://docs.spring.io/spring-data/rest/reference/data-commons/repositories/query-methods-details.html)
- [Baeldung / CrudRepository, JpaRepository and PagingAndSortingRepository in Spring Data](https://www.baeldung.com/spring-data-repositories)
- [Baeldung / New CRUD Repository Interfaces in Spring Data 3](https://www.baeldung.com/spring-data-3-crud-repository-interfaces)

### Datenbank-Migration mit FlyWay

**TODO**

Weiterführende Dokumentation:

- [Spring Boot / Execute Flyway Database Migrations on Startup](https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto.data-initialization.migration-tool.flyway)

### Automatisierte Tests mit Datenbank-Zugriffen

**TODO**

## REST-Services

**TODO**

### RestController

**TODO**

### Generierte Web-Schnittstellen

**TODO**

**OpenAPI**

https://springdoc.org/

https://www.baeldung.com/spring-rest-openapi-documentation

https://springdoc.org/#swagger-ui-properties

http://localhost:8080/v3/api-docs

http://localhost:8080/swagger-ui/index.html

**Swagger UI Code-Gen**

https://www.baeldung.com/spring-boot-rest-client-swagger-codegen

https://github.com/swagger-api/swagger-codegen/blob/3.0.0/modules/swagger-codegen-maven-plugin/README.md

http://localhost:8083/v3/api-docs.yaml

https://openapi-generator.tech/docs/swagger-codegen-migration/
https://openapi-generator.tech/docs/generators/java/

### Exception-Handling

**TODO**

https://docs.spring.io/spring-boot/docs/current/reference/html/web.html#web.servlet.spring-mvc.error-handling

### Automatisierte Tests des Web-Schnittstellen

**TODO**
