# Die Bibliothek

"Die Bibliothek" ist eine kleine Beispielanwendung, in der die wichtigsten Spring Boot - Features umgesetzt sind und hier in dieser Dokumentation kurz vorgestellt werden. Es sind lediglich kurze Beschreibungen, die mit weiterführenden Links z.B. zur Spring Dokumentation oder etablierte Tutorial-Seiten ergänzt werden. Somit kann diese _README.md_ als schnell einsehbare Quelle für viele Best-Practices dienen.

## Architektur

Diese Anwendung ist in einer Micro-Service - Architektur designed. Sie besteht aus kleinen, für einen bestimmten Zweck entwickelte Anwendungen, die miteinander kommunizieren und zusammen die Use Cases einer Bibliothek anbieten.

![Architektur Übersicht](/library-architecture.png)
Quelle: [Excalidraw](https://excalidraw.com/#json=vyZH_m7GpCi4M753MG2ut,y-MdwhP28leTeYnokqhe9g)

### Users Application

Benutzer anzeigen, anlegen und löschen

**TODO**

### Books Application

**TODO**

### Rentals Application

**TODO**

### Library Application

**TODO**

### GUI Vaadin

**TODO**

### GUI Vue.js

**TODO**

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

### Spring Beans

In Spring Boot bzw. Spring Anwendungen können interne Implementierungen in Services zusammengefasst und gekapselt werden. Diese sogenannte Beans werden zur Laufzeit vom Spring Framework zusammengestöpselt (_Dependency Injection_), sodass alle Abhängigkeiten bedient sind. Es gibt verschiedene Ausprägungen von Beans, darunter:

_@Repositories_ sind Beans mit einer Datenbank-Anbindung. In den Beispiel-Projekten sind diese jedoch lediglich als Interfaces definiert, z.B. [RentRepository](https://github.com/sboe0705/rentals/blob/32e19bc0b3d30d8be6bb996c42790259e607011f/rentals-core/src/main/java/de/sboe0705/rentals/data/RentRepository.java). Die Implementierungen werden in diesen Fällen zur Laufzeit von Spring generiert, könnten aber auch programmatisch bereitgestellt werden.

_@Service_ Beans sind "gewöhnliche" Java-Klasse, die irgendwelche Geschäftslogiken enthalten, z.B. [RentalServiceImpl](https://github.com/sboe0705/rentals/blob/32e19bc0b3d30d8be6bb996c42790259e607011f/rentals-core/src/main/java/de/sboe0705/rentals/service/impl/RentalServiceImpl.java). Sie implementieren ein Interface (z.B. [RentalService](https://github.com/sboe0705/rentals/blob/32e19bc0b3d30d8be6bb996c42790259e607011f/rentals-core/src/main/java/de/sboe0705/rentals/service/RentalService.java)), über dass andere Beans auf sie zugreifen können, z.B. [RentalsController#37](https://github.com/sboe0705/rentals/blob/32e19bc0b3d30d8be6bb996c42790259e607011f/rentals-rest/src/main/java/de/sboe0705/rentals/rest/RentalsController.java#L37).

_@RestController_ sind Beans, deren öffentliche Methoden als REST-Services nach außen hin freigegeben sind, z.B. [RentalsController](https://github.com/sboe0705/rentals/blob/32e19bc0b3d30d8be6bb996c42790259e607011f/rentals-rest/src/main/java/de/sboe0705/rentals/rest/RentalsController.java). Diese können über entsprechende Annotationen konfiguriert werden. Das Spring Framework übernimmt dabei die Serialisierung und Deserialisierung der Parameter und Rückgabewerte.

Weiterführende Links:

- [Spring / Classpath Scanning and Managed Components](https://docs.spring.io/spring-framework/reference/core/beans/classpath-scanning.html)
- [Baeldung / Spring Bean Annotations](https://www.baeldung.com/spring-bean-annotations)
- [Baeldung / @Component vs @Repository and @Service in Spring](https://www.baeldung.com/spring-component-repository-service)

Des Weiteren können für alle Beans entsprechende Geltungsbereiche (_Scopes_) definiert werden, die bestimmen in welchem Kontext eine Bean existiert, z.B. als Singleton oder einmal für jeden Request. Siehe dazu die Dokumentation unter [Spring / Bean Scopes](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html).

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
