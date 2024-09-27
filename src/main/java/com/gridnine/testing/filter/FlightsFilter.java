package com.gridnine.testing.filter;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.predicate.FilterMethod;

import java.util.List;
import java.util.function.Predicate;

/**
 * Flights Filter interface
 */
public interface FlightsFilter {
    // Filtering by preset method
    FlightsFilter filter(FilterMethod filterMethod);

    // Filtering by passed predicate
    FlightsFilter filter(Predicate<Flight> predicate);

    // Applying filter and returning filtered list
    List<Flight> getFlights();

    void setFlights(List<Flight> flights);
}
