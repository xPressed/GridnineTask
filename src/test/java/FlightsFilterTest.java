import com.gridnine.testing.filter.FlightsFilter;
import com.gridnine.testing.filter.FlightsFilterChained;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.predicate.FilterMethod;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FlightsFilterTest {
    private FlightsFilter flightsFilter;
    private List<Flight> initialFlights;

    @BeforeEach
    void before() {
        initialFlights = List.of(new Flight(null), new Flight(null));
        flightsFilter = new FlightsFilterChained(initialFlights);
    }

    @Test
    @DisplayName("Flights List successful update")
    void testSetFlights_SuccessfulUpdate() {
        List<Flight> newFlights = List.of(new Flight(null));
        flightsFilter.setFlights(newFlights);
        Assertions.assertEquals(newFlights, flightsFilter.getFlights(), "List must be updated.");
    }

    @Test
    @DisplayName("Flights List set empty list")
    void testSetFlights_SetEmptyList() {
        List<Flight> empty = new ArrayList<>();
        flightsFilter.setFlights(empty);
        Assertions.assertTrue(flightsFilter.getFlights().isEmpty(), "List must be empty.");
    }

    @Test
    @DisplayName("Filter with valid Method")
    void testFilterMethod_WithValidMethod() {
        Assertions.assertDoesNotThrow(() -> new FlightsFilterChained(initialFlights).filter(FilterMethod.ARRIVAL_BEFORE_DEPARTURE), "No exception throw.");
    }

    @Test
    @DisplayName("Filter with invalid Method")
    void testFilterMethod_WithInvalidMethod() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new FlightsFilterChained(initialFlights).filter(FilterMethod.valueOf("Random Method")), "Throws IllegalArgumentException");
    }

    @Test
    @DisplayName("Filter with valid Predicate")
    void testFilter_WithValidPredicate() {
        Predicate<Flight> predicate = flight -> true;
        Assertions.assertDoesNotThrow(() -> flightsFilter.filter(predicate), "No exception throw.");
    }

    @Test
    @DisplayName("Filter with null Predicate")
    void testFilter_WithNullPredicate_ShouldThrowException() {
        Assertions.assertThrows(NullPointerException.class, () -> flightsFilter.filter((FilterMethod) null));
    }

    @Test
    @DisplayName("Get Flights with true Predicate")
    void testGetFlights_WithTruePredicate() {
        flightsFilter.filter(flight -> true);
        List<Flight> result = flightsFilter.getFlights();
        Assertions.assertEquals(2, result.size(), "All flights must be filtered.");
    }

    @Test
    @DisplayName("Get Flights with false Predicate")
    void testGetFlights_WithFalsePredicate() {
        flightsFilter.filter(flight -> false);
        List<Flight> result = flightsFilter.getFlights();
        Assertions.assertEquals(0, result.size(), "None flights must be filtered.");
    }

    @Test
    @DisplayName("Get Flights without filtering")
    void testGetFlights_WithoutFiltering_ShouldReturnAllFlights() {
        List<Flight> result = flightsFilter.getFlights();
        Assertions.assertEquals(2, result.size(), "All flights returned.");
    }
}
