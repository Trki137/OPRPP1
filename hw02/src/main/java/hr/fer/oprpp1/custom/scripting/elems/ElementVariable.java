package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Represents variable element
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public class ElementVariable extends Element {
    /**
     * Name of variable
     */
    private String name;

    /**
     * Constructor for ElementVariable
     *
     * @param name name of variable
     */
    public ElementVariable(String name) {
        super();
        this.name = name;
    }

    /**
     * Returns name of variable
     * @return name
     */

    @Override
    public String asText(){
        return name;
    }
}
