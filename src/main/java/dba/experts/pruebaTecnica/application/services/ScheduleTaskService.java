package dba.experts.pruebaTecnica.application.services;

import dba.experts.pruebaTecnica.application.ports.in.ScheduleTaskPort;
import dba.experts.pruebaTecnica.domain.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleTaskService implements ScheduleTaskPort {
    @Override
    public void execute(List<Task> tasks) {

    }
}
