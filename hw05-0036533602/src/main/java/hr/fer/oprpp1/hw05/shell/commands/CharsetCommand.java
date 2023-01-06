package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class represents command for listing all available charsets
 *
 * @author Dean Trkulja
 * @version 1.0
 */

public class CharsetCommand implements ShellCommand {
    /**
     * The name of the specific command.
     */
    private final String commandName;
    /**
     * The description of the specific command.
     */
    private List<String> commandDescription;

    /**
     * Constructor that initializes values
     */

    public CharsetCommand(){
        this.commandName = "charset";
        this.commandDescription = setCommandDescription();
        this.commandDescription = Collections.unmodifiableList(commandDescription);
    }

    /**
     * Executes charset command
     *
     * @param env the environment the command is to be executed in.
     * @param arguments a {@code String} instance containing arguments needed for the command's execution.
     * @return shell status describing how the shell should behave after an execution attempt.
     * @throws ShellIOException when some exception occurs while attempting to write in environment
     */

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) throws ShellIOException {
        if(arguments != null){
            env.writeln("Charset command didn't expect any arguments, but got: "+ arguments);
            return ShellStatus.CONTINUE;
        }

        for(String  charsetName : Charset.availableCharsets().keySet())
            env.writeln(charsetName);


        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return commandName;
    }

    @Override
    public List<String> getCommandDescription() {
        return commandDescription;
    }

    private List<String> setCommandDescription(){
        List<String> commandDescription = new ArrayList<>();

        commandDescription.add("Charset command lists all available charsets.");
        commandDescription.add("It also doesn't have any arguments");

        return commandDescription;
    }
}
