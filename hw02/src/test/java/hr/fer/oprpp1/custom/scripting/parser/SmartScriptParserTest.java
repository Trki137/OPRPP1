package hr.fer.oprpp1.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Few test for SmartScriptParser class
 *
 * @author Dean Trkulja
 * @version 1.0
 */

public class SmartScriptParserTest {


    /**
     * Tests if for same input, parser generates same document
     */
    @Test
    public void testIfGeneratesSameDocument(){
        SmartScriptParser smartScriptParser = new SmartScriptParser("Ovo je \n" +
                "sve jedan text node");
        SmartScriptParser smartScriptParser1 = new SmartScriptParser("Ovo je \n" +
                "sve jedan text node");

        assertEquals(smartScriptParser.getDocumentNode(),smartScriptParser1.getDocumentNode());

    }

    /**
     * Tests if for diffrent input, parser generates diffrent document
     */
    @Test
    public void testIfGeneratesDiffrentDocuments(){
        SmartScriptParser smartScriptParser = new SmartScriptParser("Ovo je \n" +
                "sve jedan text node");
        SmartScriptParser smartScriptParser1 = new SmartScriptParser("Ovo je sve jedan text node");

        assertFalse(smartScriptParser.equals(smartScriptParser1));
    }

    /**
     * Tests if returns same string when it is supposed to
     */
    @Test
    public void testReturnsSameText(){
        String s = "Ovo je \n" +
                "sve jedan text node";
        SmartScriptParser smartScriptParser = new SmartScriptParser(s);

        assertTrue(smartScriptParser.getDocumentNode().toString().equals(s));
    }

    /**
     * Tests if returns diffrent string when parsed when there are
     * blank spaces that are removed
     */
    @Test
    public void testReturnsDiffrentText(){
        String s = "This is sample text.\n" +
                "{$ FOR i 1 10 1 $}\n" +
                " This is {$= i $}-th time this message is generated.\n" +
                "{$END$}\n" +
                "{$FOR i 0 10 2 $}\n" +
                " sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\n" +
                "{$END$}";
        SmartScriptParser smartScriptParser = new SmartScriptParser(s);

        assertFalse(smartScriptParser.getDocumentNode().toString().equals(s));
    }

}
