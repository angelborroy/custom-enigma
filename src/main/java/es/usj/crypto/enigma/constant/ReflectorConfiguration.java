package es.usj.crypto.enigma.constant;

/**
 * Character pairings set to be used as a Reflector
 */
public enum ReflectorConfiguration {

    // Enigma M4 - Beta
    REFLECTOR_DEFAULT("LE:YJ:VC:NI:XW:PB:QM:DR:TA:KZ:GF:UH:OS");

    private final String sequence;

    ReflectorConfiguration(String sequence) {
        this.sequence = sequence;
    }

    public String getSequence() {
        return sequence;
    }

}
