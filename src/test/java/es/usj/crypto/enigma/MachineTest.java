package es.usj.crypto.enigma;

import es.usj.crypto.enigma.constant.ReflectorConfiguration;
import es.usj.crypto.enigma.constant.RotorConfiguration;
import org.junit.Test;

import static org.junit.Assert.*;

public class MachineTest {

    @Test
    public void basicUseCase() {
        Machine machine = new Machine(
                new Plugboard("IR:HQ:NT:WZ:VC:OY:GP:LF:BX:AK"),
                new Rotor(RotorConfiguration.ROTOR_I, 'F'),
                new Rotor(RotorConfiguration.ROTOR_II, 'S'),
                new Rotor(RotorConfiguration.ROTOR_III, 'E'),
                new Reflector(ReflectorConfiguration.REFLECTOR_DEFAULT));
        assertEquals("P", machine.getCipheredText("A"));
    }

    @Test
    public void decipher() {

        Machine machine = new Machine(
                new Plugboard("IR:HQ:NT:WZ:VC:OY:GP:LF:BX:AK"),
                new Rotor(RotorConfiguration.ROTOR_I, 'F'),
                new Rotor(RotorConfiguration.ROTOR_II, 'S'),
                new Rotor(RotorConfiguration.ROTOR_III, 'E'),
                new Reflector(ReflectorConfiguration.REFLECTOR_DEFAULT));
        assertEquals("P", machine.getCipheredText("A"));

        // Create again the Machine with initial configuration
        machine = new Machine(
                new Plugboard("IR:HQ:NT:WZ:VC:OY:GP:LF:BX:AK"),
                new Rotor(RotorConfiguration.ROTOR_I, 'F'),
                new Rotor(RotorConfiguration.ROTOR_II, 'S'),
                new Rotor(RotorConfiguration.ROTOR_III, 'E'),
                new Reflector(ReflectorConfiguration.REFLECTOR_DEFAULT));
        assertEquals("A", machine.getCipheredText("P"));

    }

    @Test
    public void initialRotorPosition() {

        Machine machine = new Machine(
                new Plugboard("IR:HQ:NT:WZ:VC:OY:GP:LF:BX:AK"),
                new Rotor(RotorConfiguration.ROTOR_I, 'F'),
                new Rotor(RotorConfiguration.ROTOR_II, 'S'),
                new Rotor(RotorConfiguration.ROTOR_III, 'E'),
                new Reflector(ReflectorConfiguration.REFLECTOR_DEFAULT));
        assertEquals("P", machine.getCipheredText("A"));

        // Create again the Machine with different configuration for initial positions
        machine = new Machine(
                new Plugboard("IR:HQ:NT:WZ:VC:OY:GP:LF:BX:AK"),
                new Rotor(RotorConfiguration.ROTOR_I, 'A'),
                new Rotor(RotorConfiguration.ROTOR_II, 'A'),
                new Rotor(RotorConfiguration.ROTOR_III, 'A'),
                new Reflector(ReflectorConfiguration.REFLECTOR_DEFAULT));
        assertNotEquals("A", machine.getCipheredText("P"));

    }

    @Test
    public void repeatedRotorConfiguration() {
        Error error = assertThrows(AssertionError.class, () -> new Machine(
            new Plugboard("IR:HQ:NT:WZ:VC:OY:GP:LF:BX:AK"),
            new Rotor(RotorConfiguration.ROTOR_I, 'F'),
            new Rotor(RotorConfiguration.ROTOR_II, 'S'),
            new Rotor(RotorConfiguration.ROTOR_I, 'F'),
            new Reflector(ReflectorConfiguration.REFLECTOR_DEFAULT)));
        String expectedMessage = "Each rotor configuration should be different";
        String actualMessage = error.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void longInputPlainText() {
        Machine machine = new Machine(
                new Plugboard("IR:HQ:NT:WZ:VC:OY:GP:LF:BX:AK"),
                new Rotor(RotorConfiguration.ROTOR_I, 'F'),
                new Rotor(RotorConfiguration.ROTOR_II, 'S'),
                new Rotor(RotorConfiguration.ROTOR_III, 'E'),
                new Reflector(ReflectorConfiguration.REFLECTOR_DEFAULT));
        machine.getCipheredText("Hello this is a test to verify \n" +
                "plain text input String containing allowed characters");
    }

    @Test
    public void wrongInputPlainText() {
        Machine machine = new Machine(
                new Plugboard("IR:HQ:NT:WZ:VC:OY:GP:LF:BX:AK"),
                new Rotor(RotorConfiguration.ROTOR_I, 'F'),
                new Rotor(RotorConfiguration.ROTOR_II, 'S'),
                new Rotor(RotorConfiguration.ROTOR_III, 'E'),
                new Reflector(ReflectorConfiguration.REFLECTOR_DEFAULT));

        Error error = assertThrows(AssertionError.class, () -> machine.getCipheredText("Hello!"));

        String expectedMessage = "Plain text includes characters not in the ALPHABET or not considered as blank space";
        String actualMessage = error.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whitespaceDoesNotChangeState() {
        Machine machine1 = new Machine(
                new Plugboard("IR:HQ:NT:WZ:VC:OY:GP:LF:BX:AK"),
                new Rotor(RotorConfiguration.ROTOR_I, 'F'),
                new Rotor(RotorConfiguration.ROTOR_II, 'S'),
                new Rotor(RotorConfiguration.ROTOR_III, 'E'),
                new Reflector(ReflectorConfiguration.REFLECTOR_DEFAULT));
        Machine machine2 = new Machine(
                new Plugboard("IR:HQ:NT:WZ:VC:OY:GP:LF:BX:AK"),
                new Rotor(RotorConfiguration.ROTOR_I, 'F'),
                new Rotor(RotorConfiguration.ROTOR_II, 'S'),
                new Rotor(RotorConfiguration.ROTOR_III, 'E'),
                new Reflector(ReflectorConfiguration.REFLECTOR_DEFAULT));
        assertEquals(machine1.getCipheredText("A B C").replaceAll(" ", ""), machine2.getCipheredText("ABC"));
    }
}
