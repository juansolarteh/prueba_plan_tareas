package dba.experts.pruebaTecnica.infrastructure.httpController;

import dba.experts.pruebaTecnica.domain.Task;
import dba.experts.pruebaTecnica.infrastructure.httpController.csvAttributeConverters.DateConverter;
import dba.experts.pruebaTecnica.infrastructure.httpController.csvAttributeConverters.IntConverter;
import dba.experts.pruebaTecnica.infrastructure.httpController.csvAttributeConverters.ListStringConverter;
import dba.experts.pruebaTecnica.infrastructure.httpController.csvAttributeConverters.StringConverter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class TaskMapper {
    public Task fromCSVRecord(CSVRecord record) {
        String id = StringConverter.convert(record, "id");
        String title = StringConverter.convert(record, "title");
        String category = StringConverter.convert(record, "category");
        int estimated_minutes = IntConverter.convert(record, "estimated_minutes");
        Date deadline = DateConverter.convert(record, "deadline");
        int priority = IntConverter.convert(record, "priority");
        List<String> dependencies = ListStringConverter.convert(record, "dependencies");

        return new Task(id, title, category, estimated_minutes, deadline, priority, dependencies);
    }
}
