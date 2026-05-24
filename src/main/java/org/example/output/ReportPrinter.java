package org.example.output;

import org.example.stats.StatsAggregator.Stats;

import java.util.Map;

public class ReportPrinter {
    public void print(Stats stats) {
        System.out.println("=== Event Log Report ===");
        System.out.println();

        System.out.println("Total valid events: " + stats.totalValid);
        System.out.println("Total invalid events: " + stats.totalInvalid);
        System.out.println();

        System.out.println("Event count per user: ");
        for(Map.Entry<String, Integer> entry : stats.eventsPerUser.entrySet())
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        System.out.println();

        System.out.printf("Total purchase amount: %.2f%n", stats.totalPurchaseAmount);
        System.out.printf("Average purchase amount: %.2f%n", stats.averagePurchaseAmount);
        System.out.printf("largest purchase: %.2f%n", stats.largestPurchase);
        System.out.println();

        System.out.println("Most active user: " + stats.mostActiveUser);
        System.out.println();

        System.out.println("Top 3 most active users: ");
        int rank = 1;
        for(Map.Entry<String, Integer> entry : stats.top3Users){
            System.out.println("  " + rank + ". " + entry.getKey() + " - " + entry.getValue() + " events");
            rank++;
        }
        System.out.println();

        System.out.println("Event counter per action: ");
        for(Map.Entry<String, Integer> entry : stats.eventsPerAction.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());

        }
    }
}
