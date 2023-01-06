package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
import hr.fer.oprpp1.hw05.shell.Environment;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Class represents command for reading text from file
 *
 * @author Dean Trkulja
 * @version 1.0
 */

public class CatCommand implements ShellCommand {
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
    public CatCommand(){
        this.commandName = "cat";
        this.commandDescription = setCommandDescription();
        this.commandDescription = Collections.unmodifiableList(this.commandDescription);
    }

    /**
     * Executes cat command
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
        String[] commandArray = CommandUtil.directorySeparator(arguments);

        if(commandArray[0].isBlank()){
            env.writeln("Expected path to some directory, but got empty path");
            return ShellStatus.CONTINUE;
        }

        File file = new File(commandArray[0]);
        Path path = Path.of(commandArray[0]);

        if(!file.exists()){
            env.writeln("File "+ path.getFileName()+" doesn't exists");
            return ShellStatus.CONTINUE;
        }

        if(file.isDirectory()){
            env.writeln("Expected file but got directory");
            return ShellStatus.CONTINUE;
        }

        Charset charset = Charset.defaultCharset();

        if(commandArray.length == 2) {

            if (Charset.availableCharsets().get(commandArray[1]) == null) {
                env.writeln("Invalid charset name");
                return ShellStatus.CONTINUE;
            }

            charset = Charset.forName(commandArray[1]);
        }


        try(
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(
                                new BufferedInputStream(
                                        Files.newInputStream(path)),charset))
        ){

            String line;

            while((line = br.readLine()) != null)
                env.writeln(line);


        }catch (IOException e){

            env.writeln("Couldn't open file");
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
        List<String> commandDescription =  new ArrayList<>();

        commandDescription.add("cat command opens given file and writes its content to console");
        commandDescription.add("cat command expects 2 arguments:");
        commandDescription.add("--File name -> Which file do we want to open");
        commandDescription.add("--Charset name -> In what charset to read file. If not given it uses default charset");

        return commandDescription;
    }
}
