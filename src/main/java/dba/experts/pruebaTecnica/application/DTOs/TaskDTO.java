package dba.experts.pruebaTecnica.application.DTOs;

import dba.experts.pruebaTecnica.domain.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class TaskDTO {
    private String startDate;
    private String endDate;
    private Task task;
}
