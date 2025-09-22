package dba.experts.pruebaTecnica.application.ports.in;

import dba.experts.pruebaTecnica.application.DTOs.PlanDTO;
import dba.experts.pruebaTecnica.domain.Task;

import java.util.Date;
import java.util.List;

public interface ScheduleTaskPort {
    PlanDTO execute(List<Task> tasks, Date start, int hours);
}
