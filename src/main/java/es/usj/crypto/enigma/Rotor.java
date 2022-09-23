package es.usj.crypto.enigma;

import es.usj.crypto.enigma.constant.RotorConfiguration;

import java.util.Objects;

import static es.usj.crypto.enigma.Machine.ALPHABET;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Each Rotor includes a 26-character sequence for the ring, including every character from the ALPHABET without repetitions
 * The Notch position, that makes the Rotor to rotate, is 1 character from the ALPHABET
 * The Rotor Position is the initial character of the sequence expressed as a number (0-A ... 25-Z)
 */
public class Rotor {

    private String ringSequence;
    private final Character notch;
    private final int rotorPosition;

    /**
     * Rotor settings
     * @param rotorConfiguration Ring sequence and notch position
     * @param rotorPosition Starting position for the ring expressed as a number (0-A ... 25-Z)
     */
    public Rotor(RotorConfiguration rotorConfiguration, int rotorPosition) {

        assertTrue("Initial position should be 0 to " + ALPHABET.length(), rotorPosition >= 0 && rotorPosition < ALPHABET.length());
        this.rotorPosition = rotorPosition;

        String input = rotorConfiguration.getRingSequence();
        for (int i = 0; i < ALPHABET.length(); i++) {
            char c = ALPHABET.charAt(i);
            long count = input.chars().filter(ch -> ch == c).count();
            assertEquals( "Character " + c + " is expected to be exactly 1 time", 1, count);
        }
        this.ringSequence = input;

        assertTrue("Notch position should be 0 to " + ALPHABET.length(), ALPHABET.indexOf(rotorConfiguration.getNotch()) > 0);
        this.notch = rotorConfiguration.getNotch();

    }

    /**
     * Get character substitution when moving the rotor from left to right
     * @param c plain character to be substituted
     * @return character substitution
     */
    public char forward(char c) {
        int index = ALPHABET.indexOf(c);
        if (index >= 0) {
            int position = ((index + rotorPosition) % ALPHABET.length()) % ALPHABET.length();
            return ringSequence.charAt(position);
        } else {
            return c;
        }
    }

    /**
     * Get character substitution when moving the rotor from right to left
     * @param c plain character to be substituted
     * @return character substitution
     */
    public char backward(char c) {
        int index = ringSequence.indexOf(c);
        if (index >= 0) {
            int position = ((index + rotorPosition) % ALPHABET.length()) % ALPHABET.length();
            return ALPHABET.charAt(position);
        } else {
            return c;
        }
    }

    /**
     * Rotate the rotor when Notch is in the expected place
     * @param leftRotor Rotor placed to the left of this rotor
     */
    public void update(Rotor leftRotor) {
        if (leftRotor == null) {
            ringSequence = rotate(ringSequence);
        } else {
            int ixL = this.ringSequence.indexOf(this.notch);
            int ixR = leftRotor.ringSequence.indexOf(leftRotor.notch);
            if (ixL != ixR) {
                ringSequence = rotate(ringSequence);
            }
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
