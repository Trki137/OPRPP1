package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
import hr.fer.oprpp1.hw05.shell.Environment;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class represents command for rendering tree of files and directories for given path
 *
 * @author Dean Trkulja
 * @version 1.0
 */


public class TreeCommand implements ShellCommand {

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


    public TreeCommand(){
        this.commandName = "tree";
        this.commandDescription = setCommandDescription();
        this.commandDescription = Collections.unmodifiableList(commandDescription);
    }

    private List<String> setCommandDescription() {
        List<String> commandDesc = new ArrayList<>();

        commandDesc.add("Prints tree of directory and files from given directory , recursively");
        commandDesc.add("Tree command expects one argument");
        commandDesc.add("-Directory name argument");

        return commandDesc;
    }


    /**
     * Executes tree command
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

        if(arg.length != 1){
            env.writeln("Expected one argument but got: "+ arg.length);
            return ShellStatus.CONTINUE;
        }

        if(arg[0].isBlank()){
            env.writeln("Directory name can't be blank: "+ arg.length);
            return ShellStatus.CONTINUE;
        }
        File f = new File(arg[0]);

        if(!f.isDirectory()){
            env.writeln("Expected directory but got file");
            return ShellStatus.CONTINUE;
        }

        Path path = Path.of(arg[0]);

        try {
            Files.walkFileTree(path, new TreeFileVisitor(env));
        } catch (IOException e){

            env.writeln("Something went wrong");
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

    /**
     * Class that extends {@link SimpleFileVisitor}
     * Goes recursively and writes into an environment
     */
    private static class TreeFileVisitor extends SimpleFileVisitor{
        /**
         * How much indentation is needed
         */
        private int level;
        /**
         * Current environment
         */
        private final Environment env;

        /**
         * Constructor for visitor
         * @param env current environment
         */
        public TreeFileVisitor(Environment env){
            this.level = 0;
            this.env = env;
        }

        /**
         * Before visiting directory writes name of directory to the environment and increases the level
         *
         * @param dir a reference to the directory
         * @param attrs the directory's basic attributes
         *
         * @return action that follows
         */
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
            try {
                env.writeln(" ".repeat(level*2) + dir.getFileName());
            } catch (ShellIOException e) {
                return FileVisitResult.TERMINATE;
            }
            level++;
            return FileVisitResult.CONTINUE;
        }

        /**
         * Writes name of file to the environment
         *
         * @param file a reference to the file
         * @param attrs the file's basic attributes
         *
         * @return action that follows
         */

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            try {
                env.writeln(" ".repeat((level + 1)*2) + file.getFileName());
            } catch (ShellIOException e) {
                return FileVisitResult.TERMINATE;
            }

            return FileVisitResult.CONTINUE;
        }

        /**
         * When exiting directory lowers level by one
         *
         * @param dir
         *          a reference to the directory
         * @param exc
         *          {@code null} if the iteration of the directory completes without
         *          an error; otherwise the I/O exception that caused the iteration
         *          of the directory to complete prematurely
         *
         * @return action that follows
         */
        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
            level--;
            return FileVisitResult.CONTINUE;
        }

    }
}
