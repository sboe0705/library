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

So wie es verschiedene Typen von Spring Beans gibt, so gibt es auch verschiedene Testtypen. Sie setzen den Schwerpunkt auf unterscheidliche Teile des Codes bzw. können für einen Test unrelevante Komponenten ausblenden und somit Laufzeit und Ressourcen sparen.

#### Simpler Unit-Test genau einer Klasse

Die Logik eines Services wird in einem Unit-Test getestet, dabei werden benötigte Abhängigkeiten gemockt, d.h. durch Dummy-Service, deren Verhalten man im Test definieren kann, ersetzt. Zum Beispiel wird im [RentalServiceImplTest](https://github.com/sboe0705/rentals/blob/32e19bc0b3d30d8be6bb996c42790259e607011f/rentals-core/src/test/java/de/sboe0705/rentals/service/impl/RentalServiceImplTest.java) in der Methode _setUp_ die zu testende Klasse mit einfachen Java-Mitteln erstellt und ein Mock-Repository per Reflection injiziert. Später wird dieser Mock über _Mockito.when(...)_ konfiguriert bzw. dessen Aufrufe über _Mockito.verify(...)_ geprüft. Diese Test-Klasse fährt keinen Spring-ApplicationContext hoch und läuft dadurch extrem schnell durch.

Weiterführende Dokumentation:

- [Baeldung / Mockito Tutorial](https://www.baeldung.com/mockito-series)
- [Mockito / JavaDoc](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)

#### Unit-Test mit Spring-ApplicationContext

**TODO**

### Application Properties

Spring Boot Anwendungen können über Properties konfiguriert werden. Direkt im Source-Code können bereits Default-Konfigurationen mitgeliefert werden (als Properties- oder Yaml-Dateien, siehe z.B. [application.yaml](https://github.com/sboe0705/library/blob/main/src/main/resources/application.yaml)), die später bei Bedarf überschrieben werden können. Das Überschreiben von Properties kann auf unterschiedliche Arten erfolgen (z.B. über Umgebungsvariablen oder Programm-Argumente) und folgt bei der Bestimmung des finalen Wertes einer festen Reihenfolge, siehe weiterführenden Link zu _Externalized Configuration_.

Weiterführende Dokumentation:

- [Spring Boot / Properties & Configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto.properties-and-configuration)
- [Spring Boot / Externalized Configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config)
- [Baeldung / Properties with Spring and Spring Boot](https://www.baeldung.com/properties-with-spring)

#### Vordefinierte Properties

Für die Einstellungen von Spring Boot bzw. den mitgelieferten Zusatz-Frameworks gibt es eine Vielzahl an vordefinierten Properties (z.B. Datenbank-Einstellungen, Security, DevTools, etc.).

Weiterführende Dokumentation:

- [Spring Boot / Common Application Properties](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#appendix.application-properties.web)

#### Mapping auf Configuration Klassen

Der Zugriff auf die Application Properties erfolgt bequem über entsprechende Java-Klassen, auf die die Application Properties gemappt werden. Dabei wird über die Annotation _@ConfigurationProperties_ ein Wurzel-Knotenpunkt definiert unter dem alle Properties auf die entsprechenden Felder der annotiierten Java-Klasse/Bean zur Laufzeit gemappt werden, siehe [LibraryConfiguration](https://github.com/sboe0705/library/blob/main/src/main/java/de/sboe0705/library/configuration/LibraryConfiguration.java). Dabei werden verschachtelte Klassen-Strukturen entsprechend berücksichtigt, siehe [RestClientConfiguration](https://github.com/sboe0705/library/blob/main/src/main/java/de/sboe0705/library/configuration/RestClientConfiguration.java) und vergleiche mit [application.yaml](https://github.com/sboe0705/library/blob/main/src/main/resources/application.yaml). Soweit nicht anders definiert, mapped Spring die Properties-Namen auf die gleichnamigen Klassen-Felder und ignoriert dabei Bindestriche und Groß-/Kleinschreibung.

Über die Annotation _@Validated_ und entsprechende Constraint-Annotationen aus dem Package _jakarta.validation.constraints_ können die Application Properties beim Start der Spring Boot Anwendung bereits validiert werden. Bei einem Fehler bricht der Startvorgang direkt ab. Ein Beispiel ist hier implementiert: [RestClientConfiguration](https://github.com/sboe0705/library/blob/main/src/main/java/de/sboe0705/library/configuration/RestClientConfiguration.java).

Es ist weiterhin möglich das Mapping der Application Properties auf Java-Klassen und die Validierung zu testen. Dabei wird im JUnit-Test über die Annotation _@EnableConfigurationProperties_ die zu testende Configuration-Klasse definiert und mit _@TestPropertySource_ die Werte geladen. Anschließend kann die per _@Autowired_ injizierte Klasse getestet werden.

**TODO: Beispiel nach [LibraryConfigurationTest](https://github.com/sboe0705/library/blob/main/src/test/java/de/sboe0705/library/configuration/LibraryConfigurationTest.java)***

Weiterführende Dokumentation:

- [Spring Boot / Type-safe Configuration Properties](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config.typesafe-configuration-properties)
- [Baeldung / Testing Spring Boot @ConfigurationProperties]()

#### Configuration Profiles

Standardmäßig wird für die gültigen Application Properties die Datei _application.properties_ (oder der Yaml-Pendant) geladen. Es können aber auch Properties für spezielle Zwecke angelegt werden, die dann über ein Profil aktiviert werden. Dazu wird eine mit dem Profilnamen gesuffixte Properties-Datei angelegt, z.B. _application-production.properties_. Das Profile kann dann z.B. über die Programm-Parameter _-Dspring.profiles.active_ aktiviert werden.

Weiterführende Dokumentation:

- [Spring Boot / Profile Specific Files](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config.files.profile-specific)
- [Baeldung / Spring Profiles](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config.files.profile-specific)

## Persistierung

### Datenbank-Anbindung

Spring Boot bietet out-of-the-box eine Standard-Konfiguration für Datenbank-Anbindung. Sobald eine Datenbank-Dependency im Projekt enthalten ist (siehe z.B. [pom.xml#38-47](https://github.com/sboe0705/users/blob/01d8d0e7ce28ed4c1f5d025523b692a5c28334ba/pom.xml#L38-L47)), wird diese Datenbank in der Application konfiguriert. Als Default wird die **H2** Datenbank konfiguriert, die auch als In-Memory-Datenbank (z.B. für ulta-schnelle Datenbank-Tests) genutzt werden kann.

Die Konfiguration einer anderen Datenbank ist im [Spring Guide / Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/) hervorragend erklärt.

Weiterführende Dokumentation:

- [Spring Boot / Data Access](https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto.data-access.configure-custom-datasource)

### H2-Console

Da der Zugriff auf eine In-Memory-Datenbank mit externen Tools nicht trivial ist, bietet H2 eine Web-Oberfläche, über die man während der laufenden Spring Boot Anwendung die persistierten Daten einsehen kann. Man aktiviert dieses Feature über die Property _spring.h2.console.enabled=true_ und dann erhält Zugriff über die URL _http://localhost:8080/h2-console_.

- [Spring Boot / Using H2’s Web Console](https://docs.spring.io/spring-boot/docs/current/reference/html/data.html#data.sql.h2-web-console)
- [Baeldung / Spring Boot With H2 Database](https://www.baeldung.com/spring-boot-h2-database)

### Objekt-Relationales Mapping (ORM)

Über ein Objekt-Relationales Mapping können Datenbank-Tabllen mit Java-Klassen verknüpft werden. So können Daten geladen und gespeichert werden, ohne dass SQL-Anweisungen geschrieben werden müssen. Spring bietet entsprechende Services, die diese Operationen ausschließlich im Java-Code durchführen lassen.

Ein einfaches Beispiel ist in der Klasse **_User_** umgesetzt, siehe [User.java](https://github.com/sboe0705/users/blob/f1f40573166cdd0c4a60a7c7daacec7a46ec61d8/src/main/java/de/sboe0705/users/model/User.java). Über entsprechende Annotationen werden die Java-Klassen mit den für das ORM notwendigen Informationen angereichert. Das Framework generiert standardmäßig für jede Entitäten-Klasse eine Tabelle mit entsprechenden Spalten für jedes enthaltene Feld. Im Folgenden sind die wichtigsten aufgeführt:

| Annotation      | Beschreibung                                                                                                                        |
| ---             | ---                                                                                                                                 |
| @Entity         | Kennzeichnet eine Java-Klasse als Datenbank-Entität                                                                                 |
| @Table          | Enthält zusätzliche Angaben zur Tabelle für diese Entität z.B. kann man den Tabellennamen explizit definieren (``name = "_user"``). |
| @Id             | Definiert ein Feld der Java-Klasse als Primärschlüssel.                                                                             |
| @GeneratedValue | Lässt die Werte des Primärschlüssels automatisch generieren.                                                                        |
| @Column         | Enthält zusätzliche Angaben zur Spalte für dieses Feld z.B. kann ein Feld als Pflichfeld (``nullable = false``) definiert werden.   |

Weiterführende Informationen findet ihr hier:

- [Spring Data JPA / Getting Started](https://docs.spring.io/spring-data/jpa/reference/jpa/getting-started.html)
- [Spring Boot / JPA and Spring Data JPA](https://docs.spring.io/spring-boot/docs/current/reference/html/data.html#data.sql.jpa-and-spring-data)
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

Ist eine Version der Anwendung bereits rproduktiv und hat ihr Datenbank-Modell bereits angelegt und mit Daten gefüllt, ist es nicht mehr so einfach neue Versionen der Anwendung mit Datenbank-Erweiterungen einzuspielen. Spring Boot enthält jedoch zwei Bibliotheken für eine komfortable Datenkbank-Migration: _FlyWay_ und _Liquibase_. Während FlyWay die Schema-Änderungen in versionierten SQL-Skripten beim Start nacheinander einspielt, unterstützt Liquibase hier mehrere Formate und bietet sogar Möglichkeiten, Datenbank-Migrationsskripte (sowohl initiale als auch Deltas) automatisch zu generieren.

Hier wird (aufgrund der perönlichen Erfahrung) mit FlyWay gearbeitet, aber Liquibase ist auf jeden Fall einen Versuch wert! 



**TODO**

application-generate-ddl.yaml
SCRIPT NODATA
https://stackoverflow.com/questions/5469881/any-easy-way-to-generate-a-build-script-from-an-h2-database


Weiterführende Dokumentation:

- [Spring Boot / Execute Flyway Database Migrations on Startup](https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto.data-initialization.migration-tool.flyway)
- [Spring Boot / Execute Liquibase Database Migrations on Startup](https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto.data-initialization.migration-tool.liquibase)
- [Baeldung / Liquibase vs Flyway](https://www.baeldung.com/liquibase-vs-flyway)
- [Database Migrations with Flyway](https://www.baeldung.com/database-migrations-with-flyway)
- [Use Liquibase to Safely Evolve Your Database Schema](https://www.baeldung.com/liquibase-refactor-schema-of-java-app)

### Automatisierte Tests mit Datenbank-Anbindung

Im Test [BooksRepositoryTest](https://github.com/sboe0705/books/blob/main/src/test/java/de/sboe0705/books/data/BooksRepositoryTest.java) wird das _BooksRepository_ getestet. Da die _CrudRepository_ von Spring generiert werden, müssen die Standard-Methoden eigentlich nicht getestet werden. Allerdings können bei der Definition von eigenen Methoden Fehler passieren, die diese Tests dann wieder sinnvoll machen. Auch die Funktionsfähigkeit von Relationen kann sogar Tests des Standard-Methoden rechtfertigen (z.B. beim kaskadierenden Löschen).

Tests mit Datenbank-Anbindungen sind mit _@DataJpaTest_ zu annotieren. Dadurch werden die Datenbank-Konfiguration aus den Application-Properties genommen. Über die Annotation _@Sql_ können SQL-Skripte mit Testdaten vor der Ausführung des Tests eingespielt werden, siehe z.B. [BooksRepositoryTest#33](https://github.com/sboe0705/books/blob/ac7f61dff70eaf679f935d5513dc15efb6158ad2/src/test/java/de/sboe0705/books/data/BooksRepositoryTest.java#L33).

Beim optionalen Injizieren eines RestControllers (siehe [BooksRepositoryTest#22-30](https://github.com/sboe0705/books/blob/main/src/test/java/de/sboe0705/books/data/BooksRepositoryTest.java#L22-L30)) sieht man auch, dass bei diesen speziellen Datenbank-Tests auch nur die für den Tests gegen die Datenbenk verantwortlichen Repository-Beans instanziiert werden.

Weiterführende Dokumentation:

- [Auto-configured Data JPA Tests](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#features.testing.spring-boot-applications.autoconfigured-spring-data-jpa)
- [Baeldung / Integration Testing With @DataJpaTest](https://www.baeldung.com/spring-boot-testing#integration-testing-with-datajpatest)

## REST-Services

**TODO**

### RestController

**TODO**

https://github.com/sboe0705/users/blob/main/src/test/java/de/sboe0705/users/rest/UserControllerTest.java

Hervorragende Tutorials zum Einstieg:

- (Building a RESTful Web Service)[https://spring.io/guides/gs/rest-service/]

### REST-Clients

**TODO**

https://github.com/sboe0705/library/blob/main/src/test/java/de/sboe0705/library/client/rentals/impl/RentalsClientImplTest.java

https://github.com/sboe0705/library/blob/main/src/test/java/de/sboe0705/library/client/books/impl/BooksClientImplTest.java

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
