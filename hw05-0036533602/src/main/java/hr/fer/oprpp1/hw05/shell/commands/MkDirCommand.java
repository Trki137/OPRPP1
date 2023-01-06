package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class represents command for creating directories
 *
 * @author Dean Trkulja
 * @version 1.0
 */


public class MkDirCommand implements ShellCommand {

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

    public MkDirCommand(){
        this.commandName = "mkdir";
        this.commandDescription = setCommandDescription();
        this.commandDescription = Collections.unmodifiableList(this.commandDescription);
    }

    /**
     * Executes mkdir command
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

        String[] arr = CommandUtil.directorySeparator(arguments);

        if(arr.length != 1) {
            env.writeln("Expected one argument, but got " + arr.length);
            return ShellStatus.CONTINUE;
        }

        if(arr[0].isBlank()) {
            env.writeln("Directory path is blank");
            return ShellStatus.CONTINUE;
        }

        Path path = Path.of(arr[0]);
        try{
            Files.createDirectories(path);
            env.writeln("Directories made successfully");
        }catch (IOException e){
            env.writeln("Invalid path");
            return ShellStatus.CONTINUE;
        }

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

    private List<String> setCommandDescription() {
        List<String> commandDesc = new ArrayList<>();

        commandDesc.add("For given path creates that folder structure.");
        commandDesc.add("mkdir takes one argument:");
        commandDesc.add("--It takes directory name");

        return commandDesc;
    }

}
