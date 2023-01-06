package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class represents command for listing all files and directories given path(not recursively)
 *
 * @author Dean Trkulja
 * @version 1.0
 */


public class LsCommand implements ShellCommand {

    /**
     * The name of the specific command.
     */
    private final String commandName;
    /**
     * The description of the specific command.
     */
    private List<String> commandDescription;

    private final SimpleDateFormat simpleDateFormat;

    /**
     * Constructor that initializes values
     */

    public LsCommand(){
        this.commandName = "ls";
        this.commandDescription = setCommandDescription();
        this.commandDescription = Collections.unmodifiableList(this.commandDescription);
        this.simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Executes ls command
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

        String[] arg = CommandUtil.directorySeparator(arguments);
        if(arg.length != 1) {
            env.writeln("Expected one argument, but got "+arg.length);
            return ShellStatus.CONTINUE;
        }

        File f = new File(arg[0]);

        if(f.isFile()){
            env.writeln("Expected directory but got file");
            return ShellStatus.CONTINUE;
        }

        try{
            for(File file: Objects.requireNonNull(f.listFiles())){
                Path path = Paths.get(file.getAbsolutePath());
                BasicFileAttributeView faView = Files.getFileAttributeView(
                        path,BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
                );
                BasicFileAttributes attributes = faView.readAttributes();

                env.writeln(formatOutput(attributes, path));
            }
        }catch (IOException e){
            env.writeln("Something went wrong");
            return ShellStatus.CONTINUE;
        }



        return ShellStatus.CONTINUE;
    }

    /**
     * Formats output for enviroment for given file
     *
     * @param attributes attributes of file
     * @param path path of file
     * @return formatted output
     */
    private String formatOutput(BasicFileAttributes attributes, Path path){
        FileTime fileTime = attributes.creationTime();
        String formattedDateTime = simpleDateFormat.format(new Date(fileTime.toMillis()));

        long size = attributes.size();

        String name = path.getFileName().toString();

        String props = (Files.isDirectory(path) ? "d" : "-") +
                (Files.isReadable(path) ? "r" : "-") +
                (Files.isWritable(path) ? "w" : "-") +
                (Files.isExecutable(path) ? "x" : "-");

        return String.format("%s %10d %s %s", props, size, formattedDateTime, name);
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

        commandDesc.add("Ls  writes a directory listing (not recursive)");
        commandDesc.add("Explanation of shortcuts in first column:");
        commandDesc.add("-- d -> directory");
        commandDesc.add("-- r -> readable");
        commandDesc.add("-- w -> writable");
        commandDesc.add("-- x -> executable");
        commandDesc.add("Second colum is file/directory size.");
        commandDesc.add("Third colum is date and time when file/directory was created.");
        commandDesc.add("Fourth column is the name of the file.");
        commandDesc.add("Ls takes one argument:");
        commandDesc.add("--directoryPath -> path to specific directory");

        return commandDesc;
    }
}
