package org.example.stats;

import org.example.model.Event;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StatsAggregator {

    public static class Stats {
        public int totalValid;
        public int totalInvalid;
        public Map<String, Integer> eventsPerUser = new LinkedHashMap<>();
        public Map<String, Integer> eventsPerAction = new LinkedHashMap<>();
        public double totalPurchaseAmount;
        public double averagePurchaseAmount;
        public double largestPurchase;
        public String mostActiveUser;
        public List<Map.Entry<String, Integer>> top3Users = new ArrayList<>();
    }

    public Stats compute(List<Event> validEvents, int invalidCount) {
        Stats stats = new Stats();
        stats.totalValid = validEvents.size();
        stats.totalInvalid = invalidCount;

        List<Double> purchaseAmounts = new ArrayList<>();

        for (Event event : validEvents){
            stats.eventsPerUser.merge(event.getUserId(), 1, Integer::sum);
            stats.eventsPerAction.merge(event.getAction(), 1, Integer::sum);

            if("purchase".equals(event.getAction())){
                purchaseAmounts.add(event.getAmount());
            }
        }

        if(!purchaseAmounts.isEmpty()) {
            stats.totalPurchaseAmount = purchaseAmounts.stream().mapToDouble(Double::doubleValue).sum();

            stats.averagePurchaseAmount = stats.totalPurchaseAmount / purchaseAmounts.size();

            stats.largestPurchase = purchaseAmounts.stream().mapToDouble(Double::doubleValue).max().orElse(0);

        }
        List<Map.Entry<String, Integer>> sortedUsers = new ArrayList<>(stats.eventsPerUser.entrySet());

        sortedUsers.sort((a, b) -> b.getValue() - a.getValue());

        if(!sortedUsers.isEmpty()) {
            stats.mostActiveUser = sortedUsers.get(0).getKey();
        }

        stats.top3Users = sortedUsers.subList(0, Math.min(3, sortedUsers.size()));

        return stats;
    }


}
