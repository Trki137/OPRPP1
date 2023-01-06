package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.crypto.Util;
import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class enables representing the hex-output of a given file.
 *
 * @author Dean Trkulja
 * @version 1.0
 */


public class HexDumpCommand implements ShellCommand {

    /**
     * The name of the specific command.
     */
    private final String commandName;
    /**
     * The description of the specific command.
     */
    private List<String> commandDescription;

    /**
     * Buffer size for reading from file
     */
    private final static int BUFFER_SIZE = 16;


    /**
     * Constructor that initializes values
     */

    public HexDumpCommand(){
        this.commandName = "hexdump";
        this.commandDescription = setCommandDescription();
        this.commandDescription = Collections.unmodifiableList(commandDescription);
    }

    /**
     * Executes hexdump command
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
            env.writeln("Expected one argument, but got "+arg.length);
            return ShellStatus.CONTINUE;
        }

        if(arg[0].isBlank()){
            env.writeln("Expected file path, but got "+arg[0]);
            return ShellStatus.CONTINUE;
        }

        File f = new File(arg[0]);
        if(!f.exists()){
            env.writeln("File doesn't exist");
            return ShellStatus.CONTINUE;
        }

        if(!f.isFile()){
            env.writeln("Expected file but got directory");
            return ShellStatus.CONTINUE;
        }



        byte[] output = new byte[BUFFER_SIZE];

        try(BufferedInputStream bufferedInputStream =
                    new BufferedInputStream(
                            Files.newInputStream(Path.of(arg[0])))){

            int row = 0;

            do{

                int result = bufferedInputStream.read(output);

                if(result == -1) break;

                env.writeln(printOutput(output, row));

                row += result;

                Arrays.fill(output, (byte) 0);
            }while (true);



        }catch (IOException e){
            env.writeln("Can't open file "+arg[0]);
            return ShellStatus.CONTINUE;
        }

        return ShellStatus.CONTINUE;
    }

    private String printOutput(byte[] output, int row) {
        StringBuilder leftStart = new StringBuilder();
        StringBuilder hexLeft = new StringBuilder();
        StringBuilder hexRight = new StringBuilder();
        StringBuilder text = new StringBuilder();

        String rowString = Integer.toHexString(row);
        leftStart.append("0".repeat(8 - rowString.length() - 1)).append(rowString);
        leftStart.append(": ");

        int half = 8;

        for(int i = 0; i < output.length; i++){
            String hexCode = Util.bytetohex(new byte[]{output[i]});

            if(output[i] == 0){
                if(i < half) hexLeft.append(" ".repeat(4));
                else hexRight.append(" ".repeat(4));
                continue;
            }

            if(i < half) hexLeft.append(" ").append(hexCode).append(" ");
            else hexRight.append(" ").append(hexCode).append(" ");

            if(output[i] < 32 || output[i] > 127) text.append('.');
            else text.append((char)output[i]);
        }
        hexLeft.append("|");
        hexRight.append(" | ");
        return leftStart.append(hexLeft).append(hexRight).append(text).toString();
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
        List<String> commandDesc = new ArrayList<>();

        commandDesc.add("Produces hex-output");
        commandDesc.add("hexdump expect one argument: ");
        commandDesc.add("--File name -> Path to file");

        return commandDesc;
    }
}
