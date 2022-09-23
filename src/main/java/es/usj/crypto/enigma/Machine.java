package es.usj.crypto.enigma;

import java.util.Locale;

import static org.junit.Assert.assertTrue;

/**
 * This Machine takes a plainText string and returns a cipherText string performing following operations on each character:
 * - Apply Plugboard substitution (if character is not mapped, the same input character is used)
 * - Apply Rotors substitution from right rotor to left rotor
 * - Apply Reflector substitution
 * - Apply Rotors substitution from left rotor to right rotor
 * - Apply Plugboard substitution (if character is not mapped, the same input character is used)
 *
 * After every character input, rotors position are rotated:
 * - Left rotor is always rotated
 * - Right and Middle rotors are rotated only if position is at Notch
 *
 * plainText >>
 *     plugboard >>
 *         right rotor >> middle rotor >> left rotor >>
 *             reflector >>
 *         left rotor >> middle rotor >> right rotor >>
 *     plugboard >>
 *  cipherText
 *
 */
public class Machine {

    // Accepted input alphabet
    public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // Machine configuration
    private final Plugboard plugboard;
    private final Rotor leftRotor;
    private final Rotor middleRotor;
    private final Rotor rightRotor;
    private final Reflector reflector;

    /**
     * Machine configuration, no rotor configuration repetition is allowed
     * @param plugboard Pair mapping for the alphabet characters (only 10 pairings are accepted)
     * @param leftRotor Rotor configuration to be placed in the left position
     * @param middleRotor Rotor configuration to be placed in the middle position
     * @param rightRotor Rotor configuration to be placed in the right position
     * @param reflector Pair mapping for the alphabet characters (13 pairings are required)
     */
    public Machine(
            Plugboard plugboard,
            Rotor leftRotor,
            Rotor middleRotor,
            Rotor rightRotor,
            Reflector reflector) {
        assertTrue("Each rotor configuration should be different",
                !leftRotor.equals(rightRotor) && !rightRotor.equals(middleRotor) && !middleRotor.equals(rightRotor));
        this.plugboard = plugboard;
        this.leftRotor = leftRotor;
        this.middleRotor = middleRotor;
        this.rightRotor = rightRotor;
        this.reflector = reflector;
    }

    /**
     * Cipher a plainText into a cipherText
     * @param plainText String that contains a sentence including characters from the Engine ALPHABET and spaces
     * @return cipherText String that contains a sentence including characters from the Engine ALPHABET and spaces
     */
    public String getCipheredText(String plainText) {

        plainText = plainText.toUpperCase(Locale.ROOT);
        assertTrue("Plain text includes characters not in the ALPHABET or not considered as blank space", plainText.matches("[A-Z\\t\\n\\f\\r\\s]+"));

        StringBuilder cipherText = new StringBuilder();

        for (char input : plainText.toCharArray()) {

            // Plugboard substitution
            char output = plugboard.getPlug(input);

            // Rotors position update
            rightRotor.update(middleRotor);
            middleRotor.update(leftRotor);
            leftRotor.update(null);

            // Rotor substitution (right-to-left)
            output = rightRotor.forward(output);
            output = middleRotor.forward(output);
            output = leftRotor.forward(output);

            // Reflector substitution
            output = reflector.getReflection(output);

            // Rotor substitution (left-to-right)
            output = leftRotor.backward(output);
            output = middleRotor.backward(output);
            output = rightRotor.backward(output);

            // Plugboard substitution
            output = plugboard.getPlug(output);

            // Save ciphered character
            cipherText.append(output);

        }

        return cipherText.toString();

    }

}
