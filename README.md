# FERME Backend Webservice

## Requisitos

* JDK 7
* Maven
* [Oracle JDBC](https://blogs.oracle.com/dev2dev/get-oracle-jdbc-drivers-and-ucp-from-oracle-maven-repository-without-ides)
* Una base de datos con las mismas tablas que entities se han configurado (ver package **cl.duoc.alumnos.ferme.domain.entities**)

## C�mo usar

1) Posicionarse en directorio ra�z de la aplicaci�n, donde reside el archivo *pom.xml*
2) Asegurarse que Maven provea todas las dependencias y el proyecto compile bien. Se recomienda ejecutar el comando:
```
mvn clean generate-sources package install
```
3) De estar todo correcto, el servicio se puede levantar de dos maneras:
  a) Levantar un Tomcat embebido, dirigi�ndose al directorio de los fuentes de la API *ferme-rest-api/* usando el comando:
```
mvn spring-boot:run
```
  b) Desplegar en un servidor, usando el EAR generado en */ferme-ear/target/*

Tambi�n se pueden configurar objetivos de ejecucii�n de Maven (par�metros del comando *mvn* mostrado arriba) que sean ambivalentes a esta gu�a, con los plugins del IDE de preferencia.
