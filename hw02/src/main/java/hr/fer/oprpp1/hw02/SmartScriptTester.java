package hr.fer.oprpp1.hw02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import hr.fer.oprpp1.custom.scripting.lexer.LexerException;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

/**
 *
 * This class tests our parser implementation
 *
 * @author Dean Trkulja
 * @version 1.0
 *
 */
public class SmartScriptTester {
    public static void main(String[] args) throws IOException {
        if(args.length != 1) throw new IllegalArgumentException("Invalid argument");
        String docBody = new String(
                Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8
        );
        try {
            SmartScriptParser parser = new SmartScriptParser(docBody);

            DocumentNode documentNode = parser.getDocumentNode();
            System.out.println(documentNode);
        } catch (SmartScriptParserException  | LexerException e) {
            System.out.println("Unable to parse document!");
            System.exit(-1);
        }
        catch (Exception e) {
            System.out.println("If this line ever executes, you have failed this class!");
            System.exit(-1);
        }

    }



}
