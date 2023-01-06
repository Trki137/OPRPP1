package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.util.*;

/**
 * Class that represents help command
 *
 * @author Dean Trkulja
 * @version 1.0
 */


public class HelpCommand implements ShellCommand {
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

    public HelpCommand(){
        this.commandName = "help";
        this.commandDescription = setCommandDescription();
        this.commandDescription = Collections.unmodifiableList(this.commandDescription);
    }


    /**
     * Executes help command.
     * If there are no arguments then lists all available commands.
     * If there is an argument then it checks if it is a command and if it is then writes description of command into environment
     *
     * @param env the environment the command is to be executed in.
     * @param arguments a {@code String} instance containing arguments needed for the command's execution.
     * @return shell status describing how the shell should behave after an execution attempt.
     * @throws ShellIOException when some exception occurs while attempting to write in environment
     */

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) throws ShellIOException {
        String[] arg = null;

        if(arguments != null)
            arg = CommandUtil.getCommands(arguments);

        Map<String,ShellCommand> commands = env.commands();

        if(arg == null){
            for(String command : commands.keySet())
                env.writeln(command);

            return ShellStatus.CONTINUE;
        }

        ShellCommand shellCommand = commands.get(arg[0]);

        if(shellCommand == null){
            env.writeln("Invalid command");
            return ShellStatus.CONTINUE;
        }

        env.writeln(shellCommand.getCommandName());

        for(String desc : shellCommand.getCommandDescription())
            env.writeln(desc);

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

        commandDesc.add("Help gives description of commands");
        commandDesc.add("If argument isn't given then lists all available commands");
        commandDesc.add("If as argument is given valid command will print out name and description of command");


        return commandDesc;
    }
}
