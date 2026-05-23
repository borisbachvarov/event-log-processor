package org.example.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Event;
import org.example.validator.EventValidator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventParser {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final EventValidator validator = new EventValidator();

    public static class ParseResult {
        public final List<Event> validEvents;
        public final int invalidLineCount;

        public ParseResult(List<Event> validEvents, int InvalidLineCount) {
            this.validEvents = validEvents;
            this.invalidLineCount = InvalidLineCount;
        }
    }

    public ParseResult parse(String filePath) throws IOException {
        List<Event> validEvents = new ArrayList<>();
        int invalidCount = 0;
        int lineNumber = 0;

        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line=reader.readLine()) != null) {
                lineNumber++;

                if(line.trim().isEmpty()) continue;

                try{
                    Event event = objectMapper.readValue(line, Event.class);

                    if(validator.isValid(event, lineNumber)){
                        validEvents.add(event);
                    } else {
                        invalidCount++;

                    }
                } catch(Exception e){
                    System.err.println("Line " + lineNumber + ": " + e.getMessage());
                }
            }
        }

        return new ParseResult(validEvents, invalidCount);

    }
}
