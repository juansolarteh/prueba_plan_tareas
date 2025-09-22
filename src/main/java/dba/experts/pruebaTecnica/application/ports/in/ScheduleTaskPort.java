package dba.experts.pruebaTecnica.application.ports.in;

import dba.experts.pruebaTecnica.domain.Task;

import java.util.List;

public interface ScheduleTaskPort {
    void execute(List<Task> tasks);
}
