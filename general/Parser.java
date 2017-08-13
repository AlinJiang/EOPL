package general;

public abstract class Parser<TokenType> implements ParserInterface {

    protected Lexer<TokenType> lexer;
    protected Token<TokenType> lookahead;
    
    public Parser(Lexer<TokenType> lexer) throws Exception {
        this.lexer = lexer;
        lookahead = lexer.nextToken();
    }
    
    public void consume() throws Exception {
        lookahead = lexer.nextToken();
    }
    
    
}
