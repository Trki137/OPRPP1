package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Represents function element
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public class ElementFunction extends Element{
    /**
     * name of the function
     */
    private String name;

    /**
     * Constructor for ElementFunction
     *
     * @param name name of the function
     */
    public ElementFunction(String name){
        super();
        this.name = name;
    }

    /**
     * Returns name of function
     *
     * @return String name of function
     */
    public String asText(){
        return name;
    }
}
