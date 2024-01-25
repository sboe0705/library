# Die Bibliothek

## Architektur

### Users Application

Benutzer anzeigen, anlegen und löschen

## Spring Boot

### Projekt-Setup

**TODO**

### Automatisierte Tests

### Application Configuration

https://docs.spring.io/spring-boot/docs/2.1.13.RELEASE/reference/html/boot-features-external-config.html


## Persistierung

Hier werden Techniken und Methoden beschrieben, die mit der Persistierung von Daten zusammenhängen, z.B. das Mapping auf Java-Klassen, Datenbank-Update und die Modellierung von Relationen zwischen Tabellen.

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
