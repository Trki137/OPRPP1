package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Represents integer element
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public class ElementConstantInteger extends Element{
    /**
     * Value of integer element
     */
    private int value;

    /**
     * Constructor for ElementConstantInteger
     * @param value value of integer element
     */
    public ElementConstantInteger(int value) {
        super();
        this.value = value;
    }

    /**
     * Return integer value as String
     * @return String value of integer that is stored
     */
    public String asText(){
        return String.valueOf(value);
    }
}
