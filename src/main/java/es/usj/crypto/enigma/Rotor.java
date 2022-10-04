package es.usj.crypto.enigma;

import es.usj.crypto.enigma.constant.RotorConfiguration;

import java.util.Objects;

import static es.usj.crypto.enigma.Machine.ALPHABET;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Each Rotor includes a 26-character sequence for the ring, including every character from the ALPHABET without repetitions
 * The Notch position, that makes the Rotor to rotate, is 1 character from the ALPHABET
 * The Rotor Position is the initial character of the ring sequence, is 1 character from the ALPHABET
 */
public class Rotor {

    // 26 character sequence including ALPHABET chars in random order with no repetition
    private String ringSequence;
    // Position of the notch in the rotor (enables rotation of rotor to the left)
    private final char notch;
    // Initial position of the rotor
    private final char rotorPosition;

    /**
     * Rotor settings
     * @param rotorConfiguration Ring sequence and notch position
     * @param rotorPosition Starting position for the ring expressed as a char (A .. Z)
     */
    public Rotor(RotorConfiguration rotorConfiguration, char rotorPosition) {

        assertTrue("Initial position should be A to Z", ALPHABET.indexOf(rotorPosition) != -1);
        this.rotorPosition = rotorPosition;

        String input = rotorConfiguration.getRingSequence();
        for (int i = 0; i < ALPHABET.length(); i++) {
            char c = ALPHABET.charAt(i);
            long count = input.chars().filter(ch -> ch == c).count();
            assertEquals( "Character " + c + " is expected to be exactly 1 time", 1, count);
        }
        this.ringSequence = input;

        // Rotate the rotor to the initial rotor position
        while (this.ringSequence.charAt(0) != this.rotorPosition) {
            this.ringSequence = rotate(this.ringSequence);
        }

        assertTrue("Notch position should be A to Z", ALPHABET.indexOf(rotorConfiguration.getNotch()) != -1);
        this.notch = rotorConfiguration.getNotch();

    }

    /**
     * Get character substitution when passing the rotor from left to right
     * @param c plain character to be substituted
     * @return character substitution
     */
    public char forward(char c) {
        int index = ALPHABET.indexOf(c);
        if (index >= 0) {
            return ringSequence.charAt(index);
        } else {
            return c;
        }
    }

    /**
     * Get character substitution when passing the rotor from right to left
     * @param c plain character to be substituted
     * @return character substitution
     */
    public char backward(char c) {
        int index = ringSequence.indexOf(c);
        if (index >= 0) {
            return ALPHABET.charAt(index);
        } else {
            return c;
        }
    }

    /**
     * Rotate the rotor when rotor to the right is at notch position
     * @param rightRotor Rotor to the right
     */
    public void update(Rotor rightRotor) {
        if (rightRotor == null || rightRotor.ringSequence.charAt(0) == rightRotor.notch) {
            ringSequence = rotate(ringSequence);
        }
    }

    /**
     * Rotates the ring of the Rotor, from ABCDE to EABCD
     * @param original Current ring sequence of the rotor
     * @return Rotated ring sequence
     */
    private static String rotate(String original) {
        return original.substring(original.length() - 1) + original.substring(0, original.length() - 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rotor rotor = (Rotor) o;
        return rotorPosition == rotor.rotorPosition &&  Objects.equals(ringSequence, rotor.ringSequence) && Objects.equals(notch, rotor.notch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ringSequence, notch, rotorPosition);
    }
}
