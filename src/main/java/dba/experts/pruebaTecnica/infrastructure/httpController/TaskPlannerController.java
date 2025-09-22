package dba.experts.pruebaTecnica.infrastructure.httpController;

import dba.experts.pruebaTecnica.application.DTOs.PlanDTO;
import dba.experts.pruebaTecnica.application.ports.in.ScheduleTaskPort;
import dba.experts.pruebaTecnica.domain.Task;
import dba.experts.pruebaTecnica.infrastructure.exceptions.EmptyFileException;
import dba.experts.pruebaTecnica.infrastructure.exceptions.RequiredColumnException;
import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/plan")
@AllArgsConstructor
public class TaskPlannerController {
    private final ScheduleTaskPort scheduleTaskPort;
    private final TaskMapper taskMapper;
    private final List<String> columns = List.of("id", "title", "category", "estimated_minutes", "deadline", "priority", "dependencies");

    @PostMapping
    public ResponseEntity<PlanDTO> plan(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) throw new EmptyFileException();

        InputStreamReader inputStreamReader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        CSVParser csvParser = new CSVParser(bufferedReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withTrim());

        for (String column : columns) {
            if (!csvParser.getHeaderMap().containsKey(column)) throw new RequiredColumnException(column);
        }

        List<Task> tasks = csvParser.getRecords()
                .stream()
                .map(taskMapper::fromCSVRecord)
                .toList();

        LocalDateTime todayAtNine = LocalDateTime.of(LocalDate.now(), java.time.LocalTime.of(9, 0));
        Date date = Date.from(todayAtNine.atZone(ZoneId.systemDefault()).toInstant());

        return ResponseEntity.ok(scheduleTaskPort.execute(tasks, date, 8));
    }
}
