package dba.experts.pruebaTecnica.application.services;

import dba.experts.pruebaTecnica.application.DTOs.MetricsDTO;
import dba.experts.pruebaTecnica.application.DTOs.PlanDTO;
import dba.experts.pruebaTecnica.application.DTOs.TaskDTO;
import dba.experts.pruebaTecnica.domain.Task;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleTaskServiceTest {
    private final ScheduleTaskService service = new ScheduleTaskService();
    @Test
    void testExecuteBasicScenario() {
        // Crear tareas
        Task task1 = new Task(
                "1",
                "Tarea 1",
                "Cat1",
                50,
                new Date(System.currentTimeMillis() + 3600_000), // 1h desde ahora
                5,
                List.of() // sin dependencias
        );

        Task task2 = new Task(
                "2",
                "Tarea 2",
                "Cat1",
                50, // 40 minutos > 15
                new Date(System.currentTimeMillis() + 10800_000),
                4,
                List.of("1") // depende de task1
        );

        Task task3 = new Task(
                "3",
                "Tarea 3",
                "Cat2",
                90, // 1h30
                new Date(System.currentTimeMillis() + 7200_000),
                3,
                List.of() // sin dependencias
        );

        List<Task> tasks = List.of(task1, task2, task3);

        Date start = new Date(); // hora de inicio
        int hours = 3;           // jornada laboral 3h = 180 minutos

        // Ejecutar planificador
        PlanDTO planDTO = service.execute(tasks, start, hours);

        // Validaciones generales
        assertNotNull(planDTO);
        assertEquals(2, planDTO.getPlan().size()); // Solo caben task1 + task3
        assertEquals(1, planDTO.getBacklog().size()); // task2 queda en backlog porque depende de task1 y jornada limitada
        assertEquals(0, planDTO.getErrors().size()); // No hay ciclos

        // Validar que las métricas se calculen correctamente
        MetricsDTO metrics = planDTO.getMetrics();
        assertEquals("02:20 hs", metrics.getTotalTimePlan()); // 50 + 90 = 140 min = 2h20
        assertEquals(2, metrics.getCategoryTop().size());
        assertTrue(metrics.getCategoryTop().contains("Cat1"));
        assertTrue(metrics.getCategoryTop().contains("Cat2"));
        assertTrue(metrics.getPriorityCount().stream().anyMatch(p -> p.getPriority() == 5 && p.getNumberOfTasks() == 1));
        assertTrue(metrics.getPriorityCount().stream().anyMatch(p -> p.getPriority() == 3 && p.getNumberOfTasks() == 1));

        // Validar horarios de TaskDTO
        TaskDTO firstTaskDTO = planDTO.getPlan().get(0);
        TaskDTO secondTaskDTO = planDTO.getPlan().get(1);

        assertEquals("Tarea 1", firstTaskDTO.getTask().getTitle());
        assertEquals("Tarea 3", secondTaskDTO.getTask().getTitle());
    }

    @Test
    void testExecuteWithCycle() {
        Task t1 = new Task("1", "T1", "Cat1", 60, new Date(), 5, List.of("2"));
        Task t2 = new Task("2", "T2", "Cat2", 45, new Date(), 4, List.of("1")); // ciclo entre t1 y t2

        List<Task> tasks = List.of(t1, t2);

        PlanDTO planDTO = service.execute(tasks, new Date(), 8);

        // Validar que detectó ciclo
        assertEquals(0, planDTO.getPlan().size());
        assertEquals(0, planDTO.getBacklog().size());
        assertEquals(2, planDTO.getErrors().size());
    }
}