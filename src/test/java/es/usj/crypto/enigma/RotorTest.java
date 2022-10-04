package es.usj.crypto.enigma;

import es.usj.crypto.enigma.constant.RotorConfiguration;
import org.junit.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class RotorTest {

    @Test
    public void availableRotors() {
        new Rotor(RotorConfiguration.ROTOR_I, 'F');
        new Rotor(RotorConfiguration.ROTOR_II, 'S');
        new Rotor(RotorConfiguration.ROTOR_III, 'E');
        new Rotor(RotorConfiguration.ROTOR_IV, 'N');
        new Rotor(RotorConfiguration.ROTOR_V, 'B');
    }

    @Test
    public void wrongInitPosition() {
        Error error = assertThrows(AssertionError.class, () -> new Rotor(RotorConfiguration.ROTOR_I, '*'));
        String expectedMessage = "Initial position should be A to Z";
        String actualMessage = error.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}
