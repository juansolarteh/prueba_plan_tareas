# Plan

## Suposiciones

1. Se deben tener parámetros configurables en el proyecto como jornada de horas del planificador.
2. Los documentos deben tener fechas deadline igual a la fecha de ejecución del planificador.

## Objetivo (2 horas)

Tener un endpoint estable que valide todos los datos del documento CSV (cabecera y registros) y logre retornar cualquier error de validación en un formato JSON amigable para el usuario siguiendo estándares HTTP.

## Desglose de tareas (2 horas)

| Tarea | Descripción                                                           | Tiempo Estimado |
|-------|-----------------------------------------------------------------------|-----------------|
| 1. Análisis de Requisitos | Leer y entender los requisitos del planificador.                      | 15 minutos      |
| 2. Busqueda de información | Investigar sobre procesamiento de CSV mediante endpoints REST y heuristica Greedy. | 20 minutos      |
| 3. Diseño de la Solución | Definir las capas de la aplicación y la estructura del proyecto. | 10 minutos      |
| 4. Configuración del Proyecto | Crear el proyecto Spring Boot y configurar dependencias necesarias. | 5 minutos       |
| 5. Implementación de la Lógica de Validación | Desarrollar la lógica para validar los datos del CSV. | 40 minutos      |
| 6. Creación del Endpoint REST | Implementar el endpoint que reciba el archivo CSV y retorne los errores de validación. | 20 minutos      |
| 7. Pruebas Unitarias | Escribir pruebas unitarias para la lógica de validación y el endpoint. | 10 minutos      |

## Riesgos y Mitigaciones

| Riesgo | Mitigación |
|--------|------------|
| No comprensión de la información extra reqquerida para realizar la prueba técnica. | Apoyo mediante herramientas de búsqueda e inteligencia artificial para una mayor velocidad en la comprensión. |
| Falta de tiempo para completar todas las tareas. | Priorizar las tareas críticas y dejar las mejoras para después. |
| Problemas técnicos con el entorno de desarrollo. | Tener un entorno de desarrollo alternativo listo para ejecución (Ejecución con docker). |