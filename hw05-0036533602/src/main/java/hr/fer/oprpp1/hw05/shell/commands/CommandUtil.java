package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellIOException;

import java.util.ArrayList;
import java.util.List;

/**
 * Class contains methods used by multiple commands that implement {@link hr.fer.oprpp1.hw05.shell.ShellCommand}.
 *
 * @author Dean Trkulja
 * @version 1.0
 */


public class CommandUtil {
    /**
     * Method for separating arguments
     *
     * @param arguments all arguments in one line
     * @return array of arguments
     */

    public static String[] directorySeparator(String arguments){
        List<String>  paths = new ArrayList<>();
        char[] charArray = arguments.toCharArray();

        int index = 0;
        boolean normalMode = true;
        StringBuilder sb = new StringBuilder();

        while(index  < charArray.length){

            if(charArray[index] == '\"'){
                normalMode = !normalMode;
                index++;

                if(normalMode) {

                    paths.add(sb.toString());
                    sb.delete(0,sb.length());
                }

                continue;
            }

            if(normalMode){
                if(charArray[index] == ' '){
                    if(sb.length() > 0){
                        paths.add(sb.toString());
                    }

                    sb.delete(0,sb.length());
                }else
                    sb.append(charArray[index]);

                index++;
                continue;
            }

            sb.append(charArray[index++]);

        }

        if(sb.length() > 0) {
            paths.add(sb.toString());
        }

        return paths.toArray(new String[0]);
    }

    /**
     * Returns commands used in user input
     *
     * @param commands String that represent user input
     * @return array of commands
     */

    public static String[] getCommands(String commands){
        List<String> finalCommands = new ArrayList<>();

        char[] charArr = commands.toCharArray();
        StringBuilder sb = new StringBuilder();
        int index = 0;

        while(index < charArr.length){
            if(charArr[index] == ' ') {
                if (sb.length() > 0) {
                    finalCommands.add(sb.toString());
                    sb.delete(0, sb.length());
                }

                index++;
                continue;
            }

            sb.append(charArr[index]);
            index++;
        }

        if(sb.length() > 0) finalCommands.add(sb.toString());

        return finalCommands.toArray(new String[0]);
    }

    /**
     *
     * @param env current environment
     * @param arr array of commands
     * @return true if arguments are valid, else false
     * @throws ShellIOException when some exception occurs while attempting to write in environment
     */


    public static boolean checkCommands(Environment env, String[] arr) throws ShellIOException {
        for(String command : arr)
            if(command.contains("\"")) {
                env.writeln("Expected command, but got:" + command);
                return false;
            }

        return true;
    }
}
