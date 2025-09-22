package dba.experts.pruebaTecnica.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Task {
    private final String id;
    private final String title;
    private final String category;
    private final String estimated_minutes;
    private final String deadline;
    private final String priority;
    private final String dependencies;
}
