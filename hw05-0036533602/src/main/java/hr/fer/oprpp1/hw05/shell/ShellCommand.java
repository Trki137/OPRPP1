package hr.fer.oprpp1.hw05.shell;

import java.util.List;

/**
 * Interface that represents a model for various commands within the {@link MyShell}
 *
 * @author Dean Trkulja
 * @version 1.0
 */
public interface ShellCommand {
    /**
     * Tries to execute the current command within the given environment {@code env}, with given {@code arguments}.
     *
     * @param env the environment the command is to be executed in.
     * @param arguments a {@code String} instance containing arguments needed for the command's execution.
     * @return shell status describing how the shell should behave after an execution attempt.
     * @throws ShellIOException when some exception occurs while attempting to write in environment
     *
     */
    ShellStatus executeCommand(Environment env, String arguments) throws ShellIOException;
    /**
     * Fetches the command's name.
     *
     * @return the name of the current shell command.
     */
    String getCommandName();
    /**
     * Fetches the command's description.
     *
     * @return the description of the current shell command.
     */
    List<String> getCommandDescription();
}
