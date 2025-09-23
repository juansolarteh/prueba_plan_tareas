package dba.experts.pruebaTecnica.application.services;

import dba.experts.pruebaTecnica.domain.Task;

import java.util.Comparator;

public class TaskComparator implements Comparator<Task> {
    @Override
    public int compare(Task o1, Task o2) {
        int cmp = o1.getDeadline().compareTo(o2.getDeadline());
        if (cmp != 0) return cmp;
        cmp = Integer.compare(o2.getPriority(), o1.getPriority());
        if (cmp != 0) return cmp;
        return Integer.compare(o1.getEstimated_minutes(), o2.getEstimated_minutes());
    }
}
