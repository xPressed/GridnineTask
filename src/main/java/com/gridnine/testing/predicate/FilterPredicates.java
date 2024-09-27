package com.gridnine.testing.predicate;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class FilterPredicates {
    // Map to store preset predicates by FilterMethod as key
    private final static Map<FilterMethod, Predicate<Flight>> predicateMap = new HashMap<>();

    public static Predicate<Flight> getPredicate(FilterMethod filterMethod) {
        return predicateMap.get(filterMethod);
    }

    public static boolean containsKey(FilterMethod filterMethod) {
        return predicateMap.containsKey(filterMethod);
    }

    // Initializer to preset predicates
    static {
        predicateMap.put(FilterMethod.DEPARTURE_BEFORE_CURRENT,
                flight -> flight.getSegments().stream()
                        .allMatch(segment -> segment
                                .getDepartureDate().isAfter(LocalDateTime.now())));

        predicateMap.put(FilterMethod.ARRIVAL_BEFORE_DEPARTURE,
                flight -> flight.getSegments().stream()
                        .allMatch(segment -> segment
                                .getArrivalDate().isAfter(segment.getDepartureDate())));

        predicateMap.put(FilterMethod.MORE_THAN_2_HOURS_ON_GROUND,
                flight -> {
                    List<Segment> segments = flight.getSegments();

                    for (int i = 0; i < segments.size() - 1; i++) {
                        Segment current = segments.get(i);
                        Segment next = segments.get(i + 1);

                        if (current.getArrivalDate().plusHours(2).isBefore(next.getDepartureDate())) {
                            return false;
                        }
                    }
                    return true;
                });
    }
}
