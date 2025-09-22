package dba.experts.pruebaTecnica.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Data
public class Task {
    private final String id;
    private final String title;
    private final String category;
    private final int estimated_minutes;
    private final Date deadline;
    private final int priority;
    private final List<String> dependencies;
}
