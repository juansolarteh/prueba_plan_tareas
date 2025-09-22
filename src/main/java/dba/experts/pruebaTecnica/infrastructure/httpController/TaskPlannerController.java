package dba.experts.pruebaTecnica.infrastructure.httpController;

import dba.experts.pruebaTecnica.application.ports.in.ScheduleTaskPort;
import dba.experts.pruebaTecnica.domain.Task;
import dba.experts.pruebaTecnica.infrastructure.exceptions.EmptyFileException;
import dba.experts.pruebaTecnica.infrastructure.exceptions.generics.RequiredColumnException;
import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/plan")
@AllArgsConstructor
public class TaskPlannerController {
    private final ScheduleTaskPort scheduleTaskPort;
    private final List<String> columns = List.of("id", "title", "category", "estimated_minutes", "deadline", "priority", "dependencies");

    @PostMapping
    public ResponseEntity<?> plan(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) throw new EmptyFileException();

        InputStreamReader inputStreamReader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        CSVParser csvParser = new CSVParser(bufferedReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withTrim());

        for (String column : columns) {
            if (!csvParser.getHeaderMap().containsKey(column)) throw new RequiredColumnException(column);
        }

        List<Task> tasks = new ArrayList<>();

        for (CSVRecord record : csvParser) {
            String id = record.get("id");
            String title = record.get("title");
            String category = record.get("category");
            String estimated_minutes_str = record.get("estimated_minutes");
            String deadline_str = record.get("deadline");
            String priority_str = record.get("priority");
            String dependencies_str = record.get("dependencies");

            int estimated_minutes;
            try {
                estimated_minutes = Integer.parseInt(estimated_minutes_str);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("El valor de 'estimated_minutes' debe ser un número entero. Valor inválido en la fila con id: " + id);
            }

            int priority;
            try {
                priority = Integer.parseInt(priority_str);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("El valor de 'priority' debe ser un número entero. Valor inválido en la fila con id: " + id);
            }

            String format = "YYYY-MM-DDTHH:MM";
            Date deadline;
            try {
                deadline = java.sql.Timestamp.valueOf(deadline_str.replace("T", " ") + ":00");
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("El valor de 'deadline' debe tener el formato " + format + ". Valor inválido en la fila con id: " + id);
            }

            String dependencyDelimiter1 = "|";
            String dependencyDelimiter2 = " ";

            List<String> dependencies;

            if (dependencies_str.contains(dependencyDelimiter1)) {
                dependencies = List.of(dependencies_str.split("\\" + dependencyDelimiter1));
            } else if (dependencies_str.contains(dependencyDelimiter2)) {
                dependencies = List.of(dependencies_str.split(dependencyDelimiter2));
            } else if (dependencies_str.isBlank()) {
                dependencies = List.of();
            } else {
                dependencies = List.of(dependencies_str);
            }



        }

        return ResponseEntity.ok("Hola mundo");
    }
}
