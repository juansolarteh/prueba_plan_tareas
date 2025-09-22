package dba.experts.pruebaTecnica.application.DTOs;

import dba.experts.pruebaTecnica.domain.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PlanDTO {
    private List<TaskDTO> plan;
    private List<Task> backlog;
    private MetricsDTO metrics;
    private List<Task> errors;
}
