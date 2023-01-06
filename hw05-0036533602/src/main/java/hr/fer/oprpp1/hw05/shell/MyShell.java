package hr.fer.oprpp1.hw05.shell;

import hr.fer.oprpp1.hw05.shell.commands.*;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Class represents a simple shell with a number of commands. It implements {@link Environment}.
 *
 * @author Dean Trkulja
 * @version 1.0
 */

public class MyShell implements Environment {

    /**
     * Current prompt symbol
     */
    private Character promptSymbol;
    /**
     * Current multiline symbol
     */
    private Character multilineSymbol;
    /**
     * Current morelines symbol
     */
    private Character morelinesSymbol;
    /**
     * Map of all available commands
     */
    private SortedMap<String, ShellCommand> commands;
    /**
     * Environment current status
     */
    private ShellStatus status;
    /**
     * Scanner for reading inputs
     */
    private final Scanner sc;

    /**
     * Creates and initializes a new environment with corresponding signaling symbols and commands.
     */

    public MyShell(){
        this.promptSymbol = '>';
        this.multilineSymbol = '|';
        this.morelinesSymbol = '\\';
        this.status = ShellStatus.CONTINUE;
        this.commands = new TreeMap<>();
        this.sc = new Scanner(System.in);

        this.commands.put("ls", new LsCommand());
        this.commands.put("tree", new TreeCommand());
        this.commands.put("copy", new CopyCommand());
        this.commands.put("charset", new CharsetCommand());
        this.commands.put("cat", new CatCommand());
        this.commands.put("mkdir", new MkDirCommand());
        this.commands.put("help", new HelpCommand());
        this.commands.put("symbol", new SymbolCommand());
        this.commands.put("hexdump", new HexDumpCommand());

        this.commands = Collections.unmodifiableSortedMap(this.commands);
    }

    /**
     * Program used to execute the shell environment.
     *
     * @param args an array of command-line arguments.
     */

    public static void main(String[] args) {
        MyShell shell = new MyShell();


        try{
            shell.writeln("Welcome to MyShell v 1.0");

            while(shell.status == ShellStatus.CONTINUE){
                shell.write(shell.promptSymbol+" ");
                String command = shell.readLine();

                command = command.replace("\n", "").trim();

                String commandName = extractCommandName(command);
                if(commandName.equals("exit")) {
                    shell.status = ShellStatus.TERMINATE;
                    continue;
                }

                String arg = command.substring(commandName.length()).trim();
                if(arg.length() == 0) arg = null;

                ShellCommand shellCommand = shell.commands.get(commandName);

                if(shellCommand == null){
                    shell.writeln(String.format("%s is not valid command", commandName));
                    continue;
                }

                shell.status = shellCommand.executeCommand(shell,arg);
            }

        }catch (ShellIOException e){
            shell.status = ShellStatus.TERMINATE;
        }
    }

    private static String extractCommandArg(int startIndex, String command, MyShell shell) {
        char[] arr = command.toCharArray();
        StringBuilder sb = new StringBuilder();

        while (startIndex < arr.length){
            if(arr[startIndex] == shell.getMorelinesSymbol())
                if(arr[startIndex + 1] == ' '){
                    startIndex++;
                    continue;
                }

            sb.append(arr[startIndex++]);
        }

        return sb.toString();
    }


    private static String extractCommandName(String command) {
        char[] charArr = command.toCharArray();
        StringBuilder sb = new StringBuilder();
        int index = 0;

        while(index < charArr.length){
            if(charArr[index] == ' '){
                if(sb.length() > 0) return sb.toString();

                index++;
                continue;
            }else sb.append(charArr[index]);

            index++;
        }

        return sb.toString();
    }



    @Override
    public String readLine() throws ShellIOException {
        StringBuilder commandBuilder = new StringBuilder();
        while(true){
            String line = sc.nextLine();
            if(!line.endsWith(String.valueOf(morelinesSymbol))){
                commandBuilder.append(line);
                break;
            }

            commandBuilder.append(line.replace(morelinesSymbol,' '));
            write(multilineSymbol + " ");

        }

        return commandBuilder.toString();
    }

    @Override
    public void write(String text) throws ShellIOException {
        if(text == null) throw new ShellIOException("Text can't be null");

        System.out.print(text);
    }

    @Override
    public void writeln(String text) throws ShellIOException {
        if(text == null) throw new ShellIOException("Text can't be null");

        System.out.println(text);
    }

    @Override
    public SortedMap<String, ShellCommand> commands() {
        return commands;
    }

    @Override
    public Character getMultilineSymbol() {
        return multilineSymbol;
    }

    @Override
    public void setMultilineSymbol(Character symbol) {
        multilineSymbol = symbol;
    }

    @Override
    public Character getPromptSymbol() {
        return promptSymbol;
    }

    @Override
    public void setPromptSymbol(Character symbol) {
        promptSymbol = symbol;
    }

    @Override
    public Character getMorelinesSymbol() {
        return morelinesSymbol;
    }

    @Override
    public void setMorelinesSymbol(Character symbol) {
        morelinesSymbol = symbol;
    }
}
