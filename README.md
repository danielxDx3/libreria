# Librería PUCE — versión sin PostgreSQL

Aplicación web de gestión de libros con **Java 21, Spring Boot 4.1.2, Thymeleaf e H2 en memoria**.

✅ **No necesitas instalar PostgreSQL, usar pgAdmin ni introducir una contraseña.**

## Antes de empezar

- Tener **Java JDK 21** instalado (igual que tu proyecto actual).
- Abrir esta carpeta como **proyecto Maven** en IntelliJ IDEA.
- Permitir que IntelliJ descargue/sincronice las dependencias Maven; para la primera descarga puede requerirse conexión a Internet.

## Ejecutar desde IntelliJ IDEA

1. Extrae el archivo ZIP.
2. En IntelliJ, selecciona **File > Open** y abre la carpeta `Libreria_Sin_PostgreSQL` (o el archivo `pom.xml`).
3. Cuando aparezca la sugerencia, presiona **Load Maven Changes / Reload All Maven Projects** para actualizar H2.
4. Abre `src/main/java/com/puce/libreria/LibreriaApplication.java`.
5. Haz clic en **Run** (triángulo verde) junto al método `main()`.
6. En el navegador entra a **http://localhost:8080**.

**Importante:** Si ejecutas mediante una configuración antigua que use el módulo de otro proyecto, elige la aplicación dentro de esta nueva carpeta. No hay que definir `DB_PASSWORD` ni ninguna variable relacionada con PostgreSQL.

## Ejecutar desde terminal

```powershell
.\mvnw.cmd spring-boot:run
```

Para pruebas automáticas:

```powershell
.\mvnw.cmd test
```

> Necesitas que Maven pueda descargar las dependencias la primera vez (si no están en tu caché).

## ¿Qué puedes hacer?

| Página | Función |
| --- | --- |
| `/` | Inicio |
| `/libros` | Listar y buscar libros |
| `/libros/formulario` | Registrar un libro |
| `/libros/{id}/editar` | Editar un libro |
| Botón Eliminar en el catálogo | Eliminar un libro |

El sistema mantiene las validaciones: título obligatorio y único, editorial y género obligatorios, páginas entre 1 y 20.000.

## ¿Dónde están los datos?

Los libros se guardan temporalmente en **H2 en memoria**, dentro de la misma aplicación Java.

- No usa PostgreSQL.
- No necesita instalar ni configurar un servidor de base de datos.
- Hibernate crea la tabla `libros` automáticamente al iniciar.
- **Al detener o reiniciar la aplicación, se pierden los registros.** Esto es normal en esta versión de demostración.

La configuración está en `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:h2:mem:libreria;DB_CLOSE_DELAY=-1
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create-drop
```

## Cuando quieras conservar los registros

Se puede cambiar H2 en memoria por **H2 en archivo local**, sin PostgreSQL, o volver a PostgreSQL más adelante sin reescribir el CRUD.

## Archivos incluidos

- `pom.xml`: dependencias Spring Boot y H2 (`runtime`), sin el driver PostgreSQL.
- `application.properties`: H2 lista para usar.
- `Controller`, `Entity`, `Repositorio` y `Service`: código Java del CRUD.
- `templates/` y `static/`: páginas web y estilos.
- `src/test/`: pruebas Java para el servicio y el contexto.

No incluye claves ni configuraciones privadas.

**Nota sobre la verificación:** archivos revisados y validados estructuralmente; la compilación y ejecución reales de Maven deben comprobarse en tu equipo.
