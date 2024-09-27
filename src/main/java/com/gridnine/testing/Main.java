package com.gridnine.testing;

import com.gridnine.testing.filter.FlightsFilter;
import com.gridnine.testing.filter.FlightsFilterChained;
import com.gridnine.testing.handler.FlightBuilder;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.predicate.FilterMethod;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        System.out.println("\nCreated List:\n" + flights);

        FlightsFilter filterChained = new FlightsFilterChained(flights);

        // Filtering by one method and then applying filter
        filterChained.filter(
                flight -> flight.getSegments().stream()
                        .allMatch(segment -> segment
                                .getDepartureDate().isAfter(LocalDateTime.now())));
        System.out.println("\n\nFiltered List by departure before current:\n" + filterChained.getFlights());

        filterChained.filter(FilterMethod.ARRIVAL_BEFORE_DEPARTURE);
        System.out.println("\nFiltered List by arrival before departure:\n" + filterChained.getFlights());

        filterChained.filter(FilterMethod.MORE_THAN_2_HOURS_ON_GROUND);
        System.out.println("\nFiltered List by more than 2 hours on ground:\n" + filterChained.getFlights());

        // Filtering by all methods and then applying filter
        System.out.println("\n\nChain Filtered List by all conditions:");
        System.out.println(filterChained.filter(
                        flight -> flight.getSegments().stream()
                                .allMatch(segment -> segment
                                        .getDepartureDate().isAfter(LocalDateTime.now())))
                .filter(FilterMethod.ARRIVAL_BEFORE_DEPARTURE)
                .filter(FilterMethod.MORE_THAN_2_HOURS_ON_GROUND)
                .getFlights());
    }
}