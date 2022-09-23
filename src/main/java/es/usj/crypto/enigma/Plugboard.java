package es.usj.crypto.enigma;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Each character is replaced by some other character
 * A character can never be replaced by itself
 * Each machine was equipped only with a set of 10 cables, missing wired characters aren't replaced
 */
public class Plugboard {

    private static final int PLUGBOARD_PAIRINGS = 10;

    private final Map<Character, Character> mapping;

    /**
     * Plugboard settings
     * @param input Pairing characters for 10 pairs of characters only, pairings are separated by :
     */
    public Plugboard(String input) {

        mapping = new HashMap<>();

        if (input.length() > 0) {

            for (int i = 0; i < Machine.ALPHABET.length(); i++) {
                char c = Machine.ALPHABET.charAt(i);
                long count = input.chars().filter(ch -> ch == c).count();
                assertTrue("Character " + c + " is expected to be 0 or 1 time, not " + count, count == 0 || count == 1);
            }
            assertEquals("Plugboard accepts exactly " + PLUGBOARD_PAIRINGS + " mappings", PLUGBOARD_PAIRINGS, input.split(":").length);
            for (String reflection : input.split(":")) {
                assertEquals("Mapping " + reflection + " should contains 2 characters", 2, reflection.length());
                mapping.put(reflection.charAt(0), reflection.charAt(1));
                mapping.put(reflection.charAt(1), reflection.charAt(0));
            }
        }

    }

    /**
     * Returns replaced character or the input character if this is not part of the ALPHABET (to support spaces)
     * or if this is not included in the Plugboard setting
     * @param input Character to be replaced
     * @return Replaced character
     */
    public char getPlug(char input) {
        return mapping.get(input) == null ? input : mapping.get(input);
    }

}
