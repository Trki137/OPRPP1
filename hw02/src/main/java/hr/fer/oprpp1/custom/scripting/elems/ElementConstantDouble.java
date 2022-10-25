package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Represents double element
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public class ElementConstantDouble extends Element{
    /**
     * Value of double element
     */
    private double value;

    /**
     * Constructor for ElementConstantDouble with {@param value}
     *
     * @param value value of double element
     */
    public ElementConstantDouble(double value) {
        super();
        this.value = value;
    }

    /**
     * Returns double value as a String
     *
     * @return String value as string
     */

    public String asText(){
        return String.valueOf(value);
    }
}
