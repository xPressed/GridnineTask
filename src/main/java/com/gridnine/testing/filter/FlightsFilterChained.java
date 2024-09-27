package com.gridnine.testing.filter;

import com.gridnine.testing.model.Flight;

import java.util.List;
import java.util.function.Predicate;

/**
 * Filter with chained predicate storing and applying method.
 */
public class FlightsFilterChained extends FlightsFilterAbstract {
    private Predicate<Flight> predicate;

    public FlightsFilterChained(List<Flight> flights) {
        super(flights);
    }

    @Override
    public FlightsFilter filter(Predicate<Flight> predicate) {
        this.predicate = this.predicate == null ? predicate : this.predicate.and(predicate);
        return this;
    }

    @Override
    public List<Flight> getFlights() {
        if (predicate == null) {
            return flights;
        }

        List<Flight> result = flights.stream().filter(predicate).toList();
        this.predicate = null;
        return result;
    }
}
