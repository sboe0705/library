# Die Bibliothek 

## Datenbank - Anbindung

Hier werden Techniken und Methoden beschrieben, die mit der Persistierung von Daten zusammenhängen, z.B. das Mapping auf Java-Klassen, Datenbank-Update und die Modellierung von Relationen zwischen Tabellen.

### Objekt-Relationales Mapping (ORM)

Über ein Objekt-Relationales Mapping können Datenbank-Tabllen mit Java-Klassen verknüpft werden. So können Daten geladen und gespeichert werden, ohne dass SQL-Anweisungen geschrieben werden müssen. Spring bietet entsprechende Services, die diese Operationen ausschließlich im Java-Code durchführen lassen.

Ein einfaches Beispiel ist in der Klasse **_User_** umgesetzt, siehe [User.java](https://github.com/sboe0705/users/blob/f1f40573166cdd0c4a60a7c7daacec7a46ec61d8/src/main/java/de/sboe0705/users/model/User.java). Über entsprechende Annotationen werden die Java-Klassen mit den für das ORM notwendigen Informationen angereichert. Das Framework generiert standardmäßig für jede Entitäten-Klasse eine Tabelle mit entsprechenden Spalten für jedes enthaltene Feld. Im Folgenden sind die wichtigsten aufgeführt:

| Annotation | Beschreibung                                                                                                                        |
| ---        | ---                                                                                                                                 |
| @Entity    | Kennzeichnet eine Java-Klasse als Datenbank-Entität                                                                                 |
| @Table     | Enthält zusätzliche Angaben zur Tabelle für diese Entität z.B. kann man den Tabellennamen explizit definieren (``name = "_user"``). |
| @Id        | Definiert ein Feld der Java-Klasse als ID.                                                                                          |
| @Column    | Enthält zusätzliche Angaben zur Spalte für dieses Feld z.B. kann ein Feld als Pflichfeld (``nullable = false``) definiert werden.   |

Weiterführende Informationen findet ihr hier:

- [Spring Data JPA / Getting Started](https://docs.spring.io/spring-data/jpa/reference/jpa/getting-started.html)
- [Baeldung / Defining JPA Entities](https://www.baeldung.com/jpa-entities)

## OpenAPI

https://www.baeldung.com/spring-rest-openapi-documentation

https://springdoc.org/#swagger-ui-properties

http://localhost:8080/v3/api-docs

http://localhost:8080/swagger-ui/index.html

## Application Configuration

https://docs.spring.io/spring-boot/docs/2.1.13.RELEASE/reference/html/boot-features-external-config.html

## Swagger UI Code-Gen

https://www.baeldung.com/spring-boot-rest-client-swagger-codegen

https://github.com/swagger-api/swagger-codegen/blob/3.0.0/modules/swagger-codegen-maven-plugin/README.md

http://localhost:8083/v3/api-docs.yaml

https://openapi-generator.tech/docs/swagger-codegen-migration/
https://openapi-generator.tech/docs/generators/java/
