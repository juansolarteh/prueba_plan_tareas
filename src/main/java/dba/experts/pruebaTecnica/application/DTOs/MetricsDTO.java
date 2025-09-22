package dba.experts.pruebaTecnica.application.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class MetricsDTO {
    private String totalTimePlan;
    private List<String> categoryTop;
    private List<PriorityCountDTO> priorityCount;
}
