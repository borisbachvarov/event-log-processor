# Event Log Processor
 
A Java command-line application that reads a JSONL event log file, validates each event, and produces aggregated statistics.
 
---
 
## Project Structure
 
```
src/main/java/org/example/
├── Main.java                     ← Entry point
├── model/Event.java              ← Data model
├── parser/EventParser.java       ← Reads the file line by line
├── validator/EventValidator.java ← Validates each event
├── stats/StatsAggregator.java    ← Computes statistics
└── output/ReportPrinter.java     ← Prints the report
```
 
---
 
## Design Decisions
 
Each class has one responsibility (Single Responsibility Principle). Validation, parsing, aggregation, and output are fully separated so changing one does not affect the others.
 
Jackson handles JSON deserialisation — one line replaces what would otherwise be hundreds of lines of manual string parsing. `BufferedReader` is used for efficient line-by-line file reading. Exceptions around parsing are caught so one bad line never stops the program.
 
`LinkedHashMap` is used instead of `HashMap` to preserve insertion order and keep output consistent across runs.
 
---
 
## Assumptions
 
- Empty lines are skipped silently — a blank line is not an event attempt.
- Negative purchase amounts are treated as invalid.
- Duplicate `eventId` values are accepted — deduplication is an optional bonus, not a requirement.
---
 
## Tradeoffs
 
- **Memory vs streaming** — all events are loaded into memory. Simple and correct at this scale. For very large files, events would need to be aggregated and discarded as they are read.
- **Single-threaded** — parallel processing would improve performance on large files but adds complexity that is not justified at this scale.
- **Console over API** — simpler to run and test. `ReportPrinter` is isolated so swapping to a different output format only requires changing that one class.
---
 
## Dependencies
 
| Dependency | Version | Purpose |
|---|---|---|
| jackson-databind | 2.18.3 | JSON deserialisation |
| junit-jupiter | 5.10.0 | Unit testing (test scope only) |
 

