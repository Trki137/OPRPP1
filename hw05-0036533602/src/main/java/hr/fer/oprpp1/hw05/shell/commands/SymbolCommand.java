package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class represents command for changing symbols of prompt
 *
 * @author Dean Trkulja
 * @version 1.0
 */


public class SymbolCommand implements ShellCommand {

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
    public SymbolCommand(){
        this.commandName = "symbol";
        this.commandDescription = setCommandDescription();
        this.commandDescription = Collections.unmodifiableList(this.commandDescription);
    }

    private List<String> setCommandDescription() {
        List<String> commandDesc = new ArrayList<>();

        commandDesc.add("Expects 1 or 2 arguments");
        commandDesc.add("-First argument which symbol do you want to change or show.");
        commandDesc.add("--Possible symbol names are PROMPT, MULTILINE, MORELINES");
        commandDesc.add("Second argument is new simpol. It must be one character only");

        return commandDesc;
    }

    /**
     * Executes symbol command
     *
     * @param env the environment the command is to be executed in.
     * @param arguments a {@code String} instance containing arguments needed for the command's execution.
     * @return shell status describing how the shell should behave after an execution attempt.
     * @throws ShellIOException when some exception occurs while attempting to write in environment
     */

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) throws ShellIOException {
        if(arguments == null){
            env.writeln("Must provide argument");
            return ShellStatus.CONTINUE;
        }

        String[] arr = CommandUtil.getCommands(arguments);

        if(arr.length > 2 || arr.length < 1){
            env.writeln("Expected 1 or 2 arguments but got "+ arr.length);
            return ShellStatus.CONTINUE;
        }

        final String PROMPT = "PROMPT";
        final String MORELINES = "MORELINES";
        final String MULTILINE = "MULTILINE";
        if(arr.length == 1){

            switch (arr[0]){
                case PROMPT -> env.writeln("Symbol for PROMPT is '"+env.getPromptSymbol()+"'");
                case MULTILINE -> env.writeln("Symbol for MULTILINE is '"+env.getMultilineSymbol()+"'");
                case MORELINES -> env.writeln("Symbol for MORELINES is '"+env.getMorelinesSymbol()+"'");
                default -> env.writeln(String.format("%s is not valid symbol name", arr[0]));
            }

            return ShellStatus.CONTINUE;
        }

        if(!CommandUtil.checkCommands(env,arr)) return ShellStatus.CONTINUE;

        if(arr[1].length() != 1){
            env.writeln("New symbol has to be 1 character symbol");
            return ShellStatus.CONTINUE;
        }



        Character old = switch (arr[0]){
            case PROMPT -> {
                char oldCharacter = env.getPromptSymbol();
                env.setPromptSymbol(arr[1].toCharArray()[0]);
                yield oldCharacter;
            }
            case MORELINES -> {
                char oldCharacter = env.getMorelinesSymbol();
                env.setMorelinesSymbol(arr[1].toCharArray()[0]);
                yield oldCharacter;
            }
            case MULTILINE -> {
                char oldCharacter = env.getMultilineSymbol();
                env.setMultilineSymbol(arr[1].toCharArray()[0]);
                yield oldCharacter;
            }
            default -> {
                env.writeln(String.format("%s is not valid symbol name", arr[0]));
                yield null;
            }
        };

        if(old == null) return ShellStatus.CONTINUE;

        env.writeln(String.format("Symbol for %s changed from '%c' to '%s'", arr[0],old,arr[1]));

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
}
