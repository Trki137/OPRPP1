package hr.fer.oprpp1.hw05.shell;

import java.util.SortedMap;

/**
 *Interface that represents a model for building an environment to execute certain commands in.
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public interface Environment {
    /**
     * Reads user input
     * @return String that was entered from user
     * @throws ShellIOException when some exception occurs while attempting to read from the current environment.
     */
    String readLine() throws ShellIOException;

    /**
     * Writes into the current environment
     * @param text text to be written
     * @throws ShellIOException when some exception occurs while attempting to write in environment
     */
    void write(String text) throws ShellIOException;

    /**
     * Writes into the current environment and in the end goes to the new line
     * @param text text to be written
     * @throws ShellIOException when some exception occurs while attempting to write in environment
     */
    void writeln(String text) throws ShellIOException;

    /**
     * Fetches all commands
     * @return unmodifiable map of commands
     */
    SortedMap<String, ShellCommand> commands();

    /**
     * Fetches current multiline symbol for current environment
     * @return  current multiline symbol
     */
    Character getMultilineSymbol();
    /**
     * Sets new multiline symbol
     * @param symbol new symbol
     */
    void setMultilineSymbol(Character symbol);
    /**
     * Fetches current prompt symbol for current environment
     * @return  current prompt symbol
     */
    Character getPromptSymbol();

    /**
     * Sets new prompt symbol
     * @param symbol new symbol
     */
    void setPromptSymbol(Character symbol);
    /**
     * Fetches current moreline symbol for current environment
     * @return  current moreline symbol
     */
    Character getMorelinesSymbol();

    /**
     * Sets new moreline symbol
     * @param symbol new symbol
     */
    void setMorelinesSymbol(Character symbol);
}
