package com.gridnine.testing.filter;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.predicate.FilterMethod;
import com.gridnine.testing.predicate.FilterPredicates;

import java.util.List;

/**
 * Abstract interface implementation with method selection
 */
public abstract class FlightsFilterAbstract implements FlightsFilter {
    protected List<Flight> flights;

    protected FlightsFilterAbstract(List<Flight> flights) {
        this.flights = flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    @Override
    public FlightsFilter filter(FilterMethod filterMethod) {
        if (!FilterPredicates.containsKey(filterMethod)) {
            throw new NullPointerException("FilterMethod not found in FilterPredicates");
        }

        filter(FilterPredicates.getPredicate(filterMethod));
        return this;
    }

}
