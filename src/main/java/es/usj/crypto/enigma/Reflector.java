package es.usj.crypto.enigma;

import es.usj.crypto.enigma.constant.ReflectorConfiguration;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Each character is reflected to any other character
 * A character can never be encrypted into itself
 */
public class Reflector {

    private final Map<Character, Character> mapping;

    /**
     * Reflector settings
     * @param reflectorConfiguration Pairing for the 26 characters of the ALPHABET
     */
    public Reflector(ReflectorConfiguration reflectorConfiguration) {

        String input = reflectorConfiguration.getSequence();

        for (int i = 0; i < Machine.ALPHABET.length(); i++) {
            char c = Machine.ALPHABET.charAt(i);
            long count = input.chars().filter(ch -> ch == c).count();
            assertEquals( "Character " + c + " is expected to be only 1 time", 1, count);
        }

        mapping = new HashMap<>();
        for (String reflection : input.split(":")) {
            assertEquals("Mapping " + reflection + " should contains 2 characters", 2, reflection.length());
            mapping.put(reflection.charAt(0), reflection.charAt(1));
            mapping.put(reflection.charAt(1), reflection.charAt(0));
        }

    }

    /**
     * Returns reflected character or the input character if this is not part of the ALPHABET (to support spaces)
     * @param input Character to be reflected
     * @return Reflected character
     */
    public char getReflection(char input) {
        return (mapping.get(input) == null ? input : mapping.get(input));
    }

}
