package es.usj.crypto.enigma.constant;

/**
 * Available configurations (ring sequence and notch) to be used as Rotor
 */
public enum RotorConfiguration {

    ROTOR_I   ("FKQHTLXOCBJSPDZRAMEWNIUYGV", 'H'),
    ROTOR_II  ("SLVGBTFXJQOHEWIRZYAMKPCNDU", 'M'),
    ROTOR_III ("EHRVXGAOBQUSIMZFLYNWKTPDJC", 'V'),
    ROTOR_IV  ("NTZPSFBOKMWRCJDIVLAEYUXHGQ", 'M'),
    ROTOR_V   ("BDFHJLCPRTXVZNYEIWGAKMUSQO", 'D');

    private final String ringSequence;
    private final Character notch;

    RotorConfiguration(String sequence, Character notch) {
        this.ringSequence = sequence;
        this.notch = notch;
    }

    public static RotorConfiguration getRotorConfiguration(int rotorNumber) {
        switch (rotorNumber)
        {
            case 1: return ROTOR_I;
            case 2: return ROTOR_II;
            case 3: return ROTOR_III;
            case 4: return ROTOR_IV;
            case 5: return ROTOR_V;
            default:
                return null;
        }
    }

    public String getRingSequence() {
        return ringSequence;
    }

    public Character getNotch() {
        return notch;
    }

}
