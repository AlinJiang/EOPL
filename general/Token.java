package general;

public class Token<TokenType>{

    public TokenType type;
    public String text;
    
    public Token(TokenType type, String text) {
        this.type = type;
        this.text = text;
    }
    
    public TokenType getType() {
        return this.type;
    }
    
    public String getText() {
        return this.text;
    }
    
    @Override
    public String toString() {
        return "<" + type.toString() + ", " + text + ">";
    }
    
}
