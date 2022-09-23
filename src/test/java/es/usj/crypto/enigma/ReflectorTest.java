package es.usj.crypto.enigma;

import es.usj.crypto.enigma.constant.ReflectorConfiguration;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReflectorTest {

    @Test
    public void basicUseCase() {
        Reflector reflector = new Reflector(ReflectorConfiguration.REFLECTOR_DEFAULT);
        assertEquals('L', reflector.getReflection('E'));
        assertEquals('E', reflector.getReflection('L'));
        assertEquals('Y', reflector.getReflection('J'));
        assertEquals('J', reflector.getReflection('Y'));
        assertEquals('V', reflector.getReflection('C'));
        assertEquals('C', reflector.getReflection('V'));
        assertEquals('N', reflector.getReflection('I'));
        assertEquals('I', reflector.getReflection('N'));
        assertEquals('X', reflector.getReflection('W'));
        assertEquals('W', reflector.getReflection('X'));
        assertEquals('P', reflector.getReflection('B'));
        assertEquals('B', reflector.getReflection('P'));
        assertEquals('Q', reflector.getReflection('M'));
        assertEquals('M', reflector.getReflection('Q'));
        assertEquals('D', reflector.getReflection('R'));
        assertEquals('R', reflector.getReflection('D'));
        assertEquals('T', reflector.getReflection('A'));
        assertEquals('A', reflector.getReflection('T'));
        assertEquals('K', reflector.getReflection('Z'));
        assertEquals('Z', reflector.getReflection('K'));
        assertEquals('G', reflector.getReflection('F'));
        assertEquals('F', reflector.getReflection('G'));
        assertEquals('U', reflector.getReflection('H'));
        assertEquals('H', reflector.getReflection('U'));
        assertEquals('O', reflector.getReflection('S'));
        assertEquals('S', reflector.getReflection('O'));
    }

}
