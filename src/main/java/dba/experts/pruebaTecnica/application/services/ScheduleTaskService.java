package dba.experts.pruebaTecnica.application.services;

import dba.experts.pruebaTecnica.application.DTOs.MetricsDTO;
import dba.experts.pruebaTecnica.application.DTOs.PlanDTO;
import dba.experts.pruebaTecnica.application.DTOs.PriorityCountDTO;
import dba.experts.pruebaTecnica.application.DTOs.TaskDTO;
import dba.experts.pruebaTecnica.application.ports.in.ScheduleTaskPort;
import dba.experts.pruebaTecnica.domain.Task;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScheduleTaskService implements ScheduleTaskPort {
    @Override
    public PlanDTO execute(List<Task> tasks, Date start, int hours) {
        final int WORKDAY_MINUTES = hours * 60;

        Map<String, Task> taskMap = tasks
                .stream()
                .collect(Collectors.toMap(Task::getId, t -> t));

        Map<String, Integer> indegree = new HashMap<>();
        Map<String, List<String>> adj = new HashMap<>();
        Map<String, Integer> categoryTime = new HashMap<>();
        Map<Integer, Integer> priorityCount = new HashMap<>();
        for (Task t : tasks) {
            indegree.put(t.getId(), t.getDependencies().size());
            for (String dep : t.getDependencies()) {
                adj.computeIfAbsent(dep, k -> new ArrayList<>()).add(t.getId());
            }
        }

        PriorityQueue<Task> available = new PriorityQueue<>((t1, t2) -> {
            int cmp = t1.getDeadline().compareTo(t2.getDeadline());
            if (cmp != 0) return cmp;
            cmp = Integer.compare(t2.getPriority(), t1.getPriority());
            if (cmp != 0) return cmp;
            return Integer.compare(t1.getEstimated_minutes(), t2.getEstimated_minutes());
        });

        for (Task t : tasks) {
            if (indegree.get(t.getId()) == 0) available.add(t);
        }

        List<Task> plan = new ArrayList<>();
        List<Task> backlog = new ArrayList<>();
        int timeUsed = 0;
        Set<String> visited = new HashSet<>();

        while (!available.isEmpty()) {
            Task t = available.poll();

            if (visited.contains(t.getId())) continue;
            visited.add(t.getId());

            if (timeUsed + t.getEstimated_minutes() <= WORKDAY_MINUTES) {
                plan.add(t);
                timeUsed += t.getEstimated_minutes();
                categoryTime.put(t.getCategory(), categoryTime.getOrDefault(t.getCategory(), 0) + t.getEstimated_minutes());
                priorityCount.put(t.getPriority(), priorityCount.getOrDefault(t.getPriority(), 0) + 1);
            } else {
                backlog.add(t);
            }

            for (String dep : adj.getOrDefault(t.getId(), Collections.emptyList())) {
                indegree.put(dep, indegree.get(dep) - 1);
                if (indegree.get(dep) == 0) {
                    available.add(taskMap.get(dep));
                }
            }
        }

        // Detectar ciclos: tareas que nunca quedaron en plan ni backlog
        List<Task> unprocessed = tasks.stream()
                .filter(t -> !visited.contains(t.getId()))
                .toList();

        String hourUsed = String.format("%02d:%02d", timeUsed / 60, timeUsed % 60) + " hs";

        List<String> topCategories = categoryTime.entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(3)
                .map(Map.Entry::getKey)
                .toList();

        List<PriorityCountDTO> priorityCountList = priorityCount.entrySet()
                .stream()
                .map(e -> new PriorityCountDTO(e.getKey(), e.getValue()))
                .toList();

        MetricsDTO metrics = new MetricsDTO(hourUsed, topCategories, priorityCountList);


        Date current = start;
        List<TaskDTO> tasksDTO = new ArrayList<>();
        for (Task t : plan) {
            Date end = new Date(current.getTime() + t.getEstimated_minutes() * 60 * 1000);
            tasksDTO.add(new TaskDTO(current.toString(), end.toString(), t));
            current = end;
        }

        return new PlanDTO(tasksDTO, backlog, metrics, unprocessed);
    }
}
