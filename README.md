# Librería PUCE

Aplicación web para registrar, consultar, **editar** y eliminar libros. Tecnologías: Java 21, Spring Boot 4.1.2, Spring Data JPA, Thymeleaf y **PostgreSQL**.

## Requisitos

- JDK 21.
- PostgreSQL instalado y en ejecución.
- Una base de datos llamada `libreria`.

## Preparar PostgreSQL

En pgAdmin o psql:

```sql
CREATE DATABASE libreria;
```

La tabla `libros` la crea y actualiza Hibernate automáticamente (`ddl-auto=update`). Los registros **se conservan** al reiniciar la aplicación.

## Configurar la conexión

Por defecto usa `localhost:5432`, usuario `postgres` y la contraseña definida en `application.properties`. Para cambiarlos sin editar el proyecto, define variables de entorno (PowerShell):

```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/libreria"
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="TU_CONTRASENA"
```

## Ejecutar

```powershell
.\mvnw.cmd spring-boot:run
```

| Página | Función |
| --- | --- |
| `/` | Inicio |
| `/libros` | Listar y buscar libros |
| `/libros/formulario` | Registrar un libro |
| `/libros/{id}/editar` | Editar un libro |
| Botón Eliminar en el catálogo | Eliminar un libro |

Validaciones: título obligatorio y único, editorial y género obligatorios, páginas entre 1 y 20.000.

## Pruebas

```powershell
.\mvnw.cmd test
```

Las pruebas usan H2 en memoria (no necesitan PostgreSQL).
