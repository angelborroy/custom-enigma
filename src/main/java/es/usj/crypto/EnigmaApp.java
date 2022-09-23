package es.usj.crypto;

import es.usj.crypto.enigma.Machine;
import es.usj.crypto.enigma.Plugboard;
import es.usj.crypto.enigma.Reflector;
import es.usj.crypto.enigma.Rotor;
import es.usj.crypto.enigma.constant.ReflectorConfiguration;
import es.usj.crypto.enigma.constant.RotorConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Wehrmacht Enigma machine custom implementation with 3 rotors
 *
 * Rotors and reflector are different from the ones used in the original machine, but complexity has been preserved:
 * - 3-rotor with selection out of 5 rotors
 * - Default reflector
 * - 10 plug cables on the plugboard
 *
 * Combinations of 3 rotors out of 5 = (5 x 4 x 3) = 60
 * Each ring can be set in any of 26 positions = (26 x 26 x 26) = 17,576
 * Notch combinations (most-left rotor is excluded) = (26 x 26) = 676
 * Plugboard combinations (10 cables) = 26! / (26 - 2 · 10)! · 10! · 2 · 10 = 150,738,274,937,250
 *
 * Complexity is equals to 60 x 17,576 x 676 x 150,738,274,937,250 = 107,458,687,327,250,619,360,000
 *
 * That can be expressed as 1.07 x 1023, and it's comparable with a 77 bit key
 *
 * Additional details on Enigma classical configuration available in <a href="https://www.ciphermachinesandcryptology.com/en/enigmatech.htm"/>
 */

@SpringBootApplication
public class EnigmaApp implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(EnigmaApp.class);

    // Input file name including plain text (characters only in ALPHABET or white space)
    private String inFile;

    // Plugboard pairing characters map
    private String plugboard;

    // Left Rotor number (1-5)
    private int leftRotor;
    // Left Rotor initial position
    private int leftRotorPosition;
    // Middle Rotor number (1-5)
    private int middleRotor;
    // Middle Rotor initial position
    private int middleRotorPosition;
    // Right Rotor number (1-5)
    private int rightRotor;
    // Right Rotor initial position
    private int rightRotorPosition;

    // Output file to get the cipher text
    private String outFile;

    public static void main(String[] args) {
        SpringApplication.run(EnigmaApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        parseArguments(args);

        Machine machine = new Machine(
                new Plugboard(plugboard),
                new Rotor(RotorConfiguration.getRotorConfiguration(leftRotor), leftRotorPosition),
                new Rotor(RotorConfiguration.getRotorConfiguration(middleRotor), middleRotorPosition),
                new Rotor(RotorConfiguration.getRotorConfiguration(rightRotor), rightRotorPosition),
                new Reflector(ReflectorConfiguration.REFLECTOR_DEFAULT));

        String input = Files.readString(Path.of(inFile));
        String output = machine.getCipheredText(input);
        Files.writeString(Path.of(outFile), output);

        LOG.debug("IN:  " + input);
        LOG.debug("OUT: " + output);

    }

    private void parseArguments(String... args) {

        PropertySource<?> ps = new SimpleCommandLinePropertySource(args);

        Object inFile = ps.getProperty("input-file");
        if (inFile == null) {
            LOG.error("Use 'input-file' argument to pass a local TXT file name with the plain text, " +
                    "for instance --input-file=plaintext.txt");
            System.exit(-1);
        }
        this.inFile = inFile.toString();

        Object plugboardPairing = ps.getProperty("plugboard");
        if (plugboardPairing == null) {
            LOG.error("Use 'plugboard' argument to pass 10 character pairing map using : as separator, " +
                    "for instance --plugboard=IR:HQ:NT:WZ:VC:OY:GP:LF:BX:AK");
            System.exit(-1);
        }
        this.plugboard = plugboardPairing.toString();

        Object leftRotor = ps.getProperty("left-rotor");
        if (leftRotor == null) {
            LOG.error("Use 'left-rotor' argument to select the left rotor configuration (from 1 to 5), " +
                    "for instance --left-rotor=1");
            System.exit(-1);
        }
        this.leftRotor = Integer.valueOf(leftRotor.toString());

        Object leftRotorPosition = ps.getProperty("left-rotor-position");
        if (leftRotorPosition == null) {
            LOG.error("Use 'left-rotor-position' argument to select the left rotor initial position (from 0 to 25), " +
                    "for instance --left-rotor-position=0");
            System.exit(-1);
        }
        this.leftRotorPosition = Integer.valueOf(leftRotorPosition.toString());

        Object middleRotor = ps.getProperty("middle-rotor");
        if (middleRotor == null) {
            LOG.error("Use 'middle-rotor' argument to select the middle rotor configuration (from 1 to 5), " +
                    "for instance --middle-rotor=2");
            System.exit(-1);
        }
        this.middleRotor = Integer.valueOf(middleRotor.toString());

        Object middleRotorPosition = ps.getProperty("middle-rotor-position");
        if (leftRotorPosition == null) {
            LOG.error("Use 'middle-rotor-position' argument to select the middle rotor initial position (from 0 to 25), " +
                    "for instance --middle-rotor-position=0");
            System.exit(-1);
        }
        this.middleRotorPosition = Integer.valueOf(middleRotorPosition.toString());

        Object rightRotor = ps.getProperty("right-rotor");
        if (rightRotor == null) {
            LOG.error("Use 'right-rotor' argument to select the right rotor configuration (from 1 to 5), " +
                    "for instance --right-rotor=3");
            System.exit(-1);
        }
        this.rightRotor = Integer.valueOf(rightRotor.toString());

        Object rightRotorPosition = ps.getProperty("right-rotor-position");
        if (rightRotorPosition == null) {
            LOG.error("Use 'right-rotor-position' argument to select the right rotor initial position (from 0 to 25), " +
                    "for instance --right-rotor-position=0");
            System.exit(-1);
        }
        this.rightRotorPosition = Integer.valueOf(rightRotorPosition.toString());

        Object outFile = ps.getProperty("output-file");
        if (inFile == null) {
            LOG.error("Use 'output-file' argument to get a local TXT file name with the ciphered text, " +
                    "for instance --input-file=ciphertext.txt");
            System.exit(-1);
        }
        this.outFile = outFile.toString();

    }

}
