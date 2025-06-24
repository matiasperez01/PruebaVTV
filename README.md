# Sistema de Verificación Técnica Vehicular (VTV)

Aplicación desarrollada con **Spring Boot** para gestionar inspecciones vehiculares.

## 🔧 Requisitos

- Java 17+
- Maven
- Base de datos configurada MySQL

## ▶️ Cómo ejecutar la aplicación

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/matiasperez01/PruebaVTV.git
   cd PruebaVTV

2. Configurar la base de datos
Editar el archivo src/main/resources/application.properties (si es necesario):

 ```bash
spring.datasource.url=jdbc:mysql://localhost:3306/vtv
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
```

3. Ejecutar la aplicación
Desde la terminal, escribir lo siguiente y ejecutar:

```bash
mvn spring-boot:run
```

4. Acceder a la API
Una vez iniciada, podés probar los endpoints accediendo a:

```bash
http://localhost:8080
```
