# Prueba Técnica DbaExperts
Prueba Técnica para proceso de ingreso a DbaExperts

**Desarrollado por:** Juan Pablo Solarte

# Instrucciones de ejecución

## Ejecución directa del proyecto

### Requisitos previos
- Java 17 o superior
- Maven 3.8.6

### Pasos para la ejecución
1. Ejecutar el proyecto:
```bash
   mvn spring-boot:run
```

Listo! Ahora el servicio está corriendo y puedes interactuar con él.

---

## Ejecución mediante docker-compose

### Requisitos previos
- Docker instalado y en ejecución

### Pasos para la ejecución
3. Construir y levantar el contenedor del servicio:
```bash
   docker-compose up
```

Listo! Ahora el servicio está corriendo y puedes interactuar con él.

# Dependencias

Para este proyecto se utilizo:

1. commons-csv, para gestión del archivo CSV.
2. mockito-core para pruebas unitarias.

#  Ejemplo de uso

Puedes probar el planificador desde Postman (o cualquier cliente HTTP):
1. Endpoint
```bash
   POST http://localhost:8080/plan
```

2. En la pestaña Body, selecciona form-data.
3. Añade un parámetro con:
  - Key -> file
  - Type -> File
  - Value -> selecciona el archivo CSV con las tareas
4. Envía la petición.

El servicio procesará el archivo y devolverá en la respuesta:

# Limitaciones

El tiempo me limita a terminar la funcionalidad como tal, No es posible implementar la capa de aplicación (servicio y lógica del planeador).
