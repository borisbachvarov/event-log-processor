package org.example;

import org.example.output.ReportPrinter;
import org.example.stats.StatsAggregator;
import org.example.parser.EventParser;

public class Main {
    public static void main(String[] args) throws Exception {

        if(args.length < 1){
            System.err.println("Usage: java -jar event-processor.jar <path-to-input-file>");
            System.exit(1);
        }

        String filePath = args[0];

        EventParser parser = new EventParser();
        StatsAggregator aggregator = new StatsAggregator();
        ReportPrinter printer = new ReportPrinter();

        EventParser.ParseResult result = parser.parse(filePath);
        StatsAggregator.Stats stats = aggregator.compute(result.validEvents, result.invalidLineCount);
        printer.print(stats);
    }
}