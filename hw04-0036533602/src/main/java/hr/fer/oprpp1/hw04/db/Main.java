package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        List<String> studentDatabase = Files.readAllLines(
                Path.of("src/database.txt"),
                StandardCharsets.UTF_8
        );

        StudentDatabase database = new StudentDatabase(studentDatabase);
        Scanner sc = new Scanner(System.in);

        while(true){
            System.out.print("> ");

            String query = sc.nextLine();

            if(query.equals("exit")) break;
            String queryReplaced = query.replaceAll("query","");

            QueryParser parser = new QueryParser(queryReplaced);

            if(parser.isDirectQuery()){

                System.out.println("Using index for record retrieval.");
                StudentRecord record = database.forJMBAG(parser.getQueriedJMBAG());
                QueryResultOutput.writeOutput(List.of(record));

            }else{

                QueryResultOutput.writeOutput(database.filter(new QueryFilter(parser.getQuery())));

            }
        }


        System.out.println("Goodbye");

    }
}
