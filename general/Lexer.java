package general;

public abstract class Lexer<TokenType> implements LexerInterface<TokenType> {
    
    public static final char EOF = (char)-1;
    
    protected String input;
    private int inputSize;
    protected int pos;
    protected char curr;
    protected char peek;
    
    public Lexer(String input) {
        this.input = input;
        inputSize = input.length();
        pos = 0;
        curr = input.charAt(pos);
    }
    
    public char peekAhead() {
        if (curr != EOF) {
            return input.charAt(pos + 1);
        } else {
            return EOF;
        }
    }
    
    public boolean notFinish() {
        return (curr != EOF);
    }
    
    public String getInput() {
        return this.input;
    }
    
    public void consume() {
        ++pos;
        if (pos >= inputSize) {
            curr = EOF;
        }
        else {
            curr = input.charAt(pos);
        }
    }
    
    public boolean isWhiteSpace() {
        return (curr == ' ' || curr == '\n' || curr == '\r' || curr == '\t');
    }
    
    public void skipWhiteSpace() {
        while (isWhiteSpace()) {
            consume();
        }
    }
    
}
