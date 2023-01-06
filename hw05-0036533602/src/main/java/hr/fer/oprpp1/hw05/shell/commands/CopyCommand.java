package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class represents command for copying content from one file to another
 *
 * @author Dean Trkulja
 * @version 1.0
 */

public class CopyCommand implements ShellCommand {

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

    private final static int BUFFER_SIZE = 4096;

    public CopyCommand(){
        this.commandName = "copy";
        this.commandDescription = setCommandDescription();
        this.commandDescription = Collections.unmodifiableList(this.commandDescription);
    }

    /**
     * Executes copy command
     *
     * @param env the environment the command is to be executed in.
     * @param arguments a {@code String} instance containing arguments needed for the command's execution.
     * @return shell status describing how the shell should behave after an execution attempt.
     * @throws ShellIOException when some exception occurs while attempting to write in environment
     */

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) throws ShellIOException {
        if(arguments == null){
            env.writeln("Must provide 2 argument");
            return ShellStatus.CONTINUE;
        }

        String[] paths = CommandUtil.directorySeparator(arguments);

        if(paths.length != 2){
            env.writeln("Expected 2 arguments, but got: "+paths.length);
            return ShellStatus.CONTINUE;
        }

        if(paths[0].isBlank()){
            env.writeln("Source file can't be blank");
            return ShellStatus.CONTINUE;
        }
        if(paths[1].isBlank()){
            env.writeln("Destination file can't be blank");
            return ShellStatus.CONTINUE;
        }

        String source = paths[0];
        String destination = paths[1];

        File sourceFile = new File(source);
        File destinationFile = new File(destination);

        Path sourcePath = Path.of(source);

        if(source.equals(destination)){
            env.writeln("Source and destination file can't be the same");
            return ShellStatus.CONTINUE;
        }
        if(!sourceFile.exists()){
            env.writeln("File "+ sourcePath.getFileName()+" doesn't exist");
            return ShellStatus.CONTINUE;
        }

        if(sourceFile.isDirectory()){
            env.writeln("Source file can't be directory");
            return ShellStatus.CONTINUE;
        }

        if(destinationFile.isDirectory()) {
            destination = getNewFile(source, destination);
            destinationFile = new File(destination);
        }

        if(destinationFile.exists()) {
            while(true){
                env.write("> File already exists. It will override data if proceeded.Should we proceed? (y/n)");

                String answer = env.readLine();

                if(answer.equals("y")) break;
                if(answer.equals("n")) return ShellStatus.CONTINUE;
            }
        }

        /*byte[] output = new byte[BUFFER_SIZE];
        try(BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(sourcePath));
            BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(Path.of(destination)))
        ){

            while(true) {
                int result = bis.read(output);

                if (result == -1) break;

                bos.write(output, 0, result);
            }
            env.writeln("Copying ended successfully");
        }catch (IOException e){
            env.writeln("Couldn't open file");
            return ShellStatus.CONTINUE;
        }*/

       try{
            Files.copy(sourcePath, Path.of(destination), StandardCopyOption.REPLACE_EXISTING);
            env.writeln("Copying ended successfully");

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
        List<String> commandDesc = new ArrayList<>();

        commandDesc.add("Copy copies content from source file to destination file.:");
        commandDesc.add("Expects 2 arguments: ");
        commandDesc.add("--source file -> path from which file to copy");
        commandDesc.add("--destination file -> path where to copy, if path points to directory it creates file with same name as source file");

        return commandDesc;
    }

    private String getNewFile(String pathFromString,String pathToString) {
        String fileNameFrom = pathFromString.substring(
                pathFromString.lastIndexOf("\\") +1
        );
        fileNameFrom = "\\" + fileNameFrom;
        return pathToString + fileNameFrom;

    }



}
