package hr.fer.oprpp1.hw02.prob1;


public class Main {
    public static void main(String[] args) {
        String ulaz = "  \\1  ";
        Lexer lexer = new Lexer(ulaz);

        while(true){
                lexer.nextToken();

                TokenType type = lexer.getToken().getType();
                Object value = lexer.getToken().getValue();
                System.out.println("(" + type + ", "+ value+")");

                if(type == TokenType.EOF) break;

        }


    }
}
