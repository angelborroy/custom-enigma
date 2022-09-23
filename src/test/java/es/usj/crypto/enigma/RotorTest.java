package es.usj.crypto.enigma;

import es.usj.crypto.enigma.constant.RotorConfiguration;
import org.junit.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class RotorTest {

    @Test
    public void availableRotors() {
        new Rotor(RotorConfiguration.ROTOR_I, 0);
        new Rotor(RotorConfiguration.ROTOR_II, 0);
        new Rotor(RotorConfiguration.ROTOR_III, 0);
        new Rotor(RotorConfiguration.ROTOR_IV, 0);
        new Rotor(RotorConfiguration.ROTOR_V, 0);
    }

    @Test
    public void wrongInitPosition() {
        Error error = assertThrows(AssertionError.class, () -> new Rotor(RotorConfiguration.ROTOR_I, 100));
        String expectedMessage = "Initial position should be 0 to 26";
        String actualMessage = error.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}
