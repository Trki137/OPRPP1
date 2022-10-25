package hr.fer.oprpp1.hw02.prob1;

/**
 * @author Dean Trkulja
 * @version 1.0
 */
public class Lexer {
    /**
     * Data represented as Array of characters
     */
    private final char[] data;
    /**
     * Current token
     */
    private Token token;

    /**
     * Current index, values from 0 to data.length - 1
     */
    private int currentIndex;

    /**
     * Character for chaining lexer state
     */
    private final char changeStateCharachter = '#';

    /**
     * Current lexer state, BASIC state by default
     */
    LexerState state;

    /**
     *
     * Constructor which takes {@code text} as argument and turns it into an array of characters
     *
     * @param text text that we will turn to array of characters
     */
    public Lexer(String text){
        data = text.toCharArray();
        state = LexerState.BASIC;
        currentIndex = 0;

    }

    /**
     * Returns next token and sets it as current token
     * @return Token next token
     */

    public Token nextToken(){
        if(token != null && token.getType() == TokenType.EOF)
            throw new LexerTokenizerException("There are no more tokens");


        removeBlanks();


        if(currentIndex >= data.length){
            token = new Token(TokenType.EOF,null);
            return token;
        }

        if(state == LexerState.EXTENDED) {
            token = new Token(TokenType.WORD,extractWordExtended());
            if(!Character.isSpaceChar(data[currentIndex])) setState(LexerState.BASIC);
            return token;
        }

        if(Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\'){
            token = new Token(TokenType.WORD,extractWord());
            return token;
        }

        if(Character.isDigit(data[currentIndex])){
            token = new Token(TokenType.NUMBER, extractNumber());
            return token;
        }

        if(data[currentIndex] == changeStateCharachter) setState(LexerState.EXTENDED);
        token = new Token(TokenType.SYMBOL, data[currentIndex++]);
        return token;

    }

    /**
     * Returns current token
     *
     * @return Token current token
     */

    public Token getToken(){
        return token;
    }

    /**
     * Sets state of lexer
     *
     * @param state in which state we want him to work
     * @throws NullPointerException when state is null
     */

    public void setState(LexerState state){
        if(state == null) throw new NullPointerException("State can't be null");

        this.state = state;
    }

    /**
     * Method that skips blanks
     */

    private void removeBlanks(){
        while(currentIndex < data.length){
            char character = data[currentIndex];
            if(character == ' ' || character == '\t' || character == '\n' || character == '\r'){
                currentIndex++;
                continue;
            }
            break;
        }
    }

    /**
     * Extracting word when lexer is in BASIC state
     *
     * @return String one word that consists of letters only
     */

    private String extractWord(){
        StringBuilder sb = new StringBuilder();

        while(currentIndex < data.length){
            if(data[currentIndex] == '\\'){
                checkSlash();
                sb.append(data[++currentIndex]);
                currentIndex++;
                continue;
            }
            if(Character.isLetter(data[currentIndex])){
                sb.append(data[currentIndex++]);
                continue;
            }
            break;
        }

        return sb.toString();
    }

    /**
     * Extracting word when lexer is in EXTENDED state
     *
     * @return String word until blank or #
     */

    private String extractWordExtended(){
        StringBuilder sb = new StringBuilder();

        while (currentIndex < data.length && data[currentIndex] != changeStateCharachter){
            if(Character.isSpaceChar(data[currentIndex])) break;
            sb.append(data[currentIndex++]);
        }

        return sb.toString();
    }

    /**
     * Extracts number type of Long
     * @return Long extracted number from array data
     * @throws LexerTokenizerException when number is too long or number is decimal
     */


    private Long extractNumber(){
        int startIndex = currentIndex;
        currentIndex++;

        while (currentIndex < data.length && Character.isDigit(data[currentIndex]))currentIndex++;


        int endIndex = currentIndex;

        String numberString = new String(data, startIndex, endIndex - startIndex);

        if(currentIndex + 1 < data.length)
            if(data[currentIndex + 1] == '.') throw new LexerTokenizerException("Number can't be decimal");

        try{
            return Long.parseLong(numberString);
        }catch (Exception e){
            throw new LexerTokenizerException("Number too big");
        }

    }

    /**
     * Checks if escape expression is valid or not
     *
     * @throws LexerTokenizerException if escape expression is invalid
     */

    private void checkSlash(){

        if(currentIndex + 1 >= data.length) throw new LexerTokenizerException("Invalid backslash use");
        if(!(data[currentIndex + 1] == '\\' || Character.isDigit(data[currentIndex + 1])))
            throw new LexerTokenizerException("Invalid backslash use");
    }

}
