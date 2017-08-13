package general;

public interface LexerInterface<TokenType> {

    void consume();
    boolean isWhiteSpace();
    void skipWhiteSpace();
    
    Token<TokenType> nextToken() throws Exception;
    
}
