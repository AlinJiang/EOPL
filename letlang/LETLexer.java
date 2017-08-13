package letlang;

import general.Lexer;
import general.Token;

public class LETLexer extends Lexer<LETTokenType> {
    
    //private LETTokenUtils letUtil;
    
    private final boolean NEGATIVE = true;
    private final boolean POSITIVE = false;
    
    public LETLexer(String input) {
        super(input);
        new LETTokenUtils();
    }

    @Override
    public Token<LETTokenType> nextToken() throws Exception {
        
        while (curr != EOF) {
            char ahead;
            switch (curr) {
            case ' ': case '\n': case '\r': case '\t':
                skipWhiteSpace(); continue;
            case '=':
                ahead = peekAhead();
                if (ahead == '=') {
                    consume(); consume(); consume();
                    return new Token<>(LETTokenType.IF_COND, "==>");
                } else if (ahead == '>') {
                    consume(); consume();
                    return new Token<>(LETTokenType.INTERFACEDERIVE, "=>");
                }
                else {
                    consume();
                    return new Token<>(LETTokenType.EQUAL, "=");
                }
            case '[':
                consume();
                return new Token<>(LETTokenType.LBRACK, "[");
            case ']':
                consume();
                return new Token<>(LETTokenType.RBRACK, "]");
            case '_':
                consume();
                return new Token<>(LETTokenType.UNDERSCORE, "_");
            case '(':
                consume();
                return new Token<>(LETTokenType.LPAREN, "(");
            case ':':
                consume();
                return new Token<>(LETTokenType.COLON, ":");
            case ')':
                consume();
                return new Token<>(LETTokenType.RPAREN, ")");
            case ',':
                consume();
                return new Token<>(LETTokenType.COMMA, ",");
            case '?':
                consume();
                return new Token<>(LETTokenType.OPTION, "?");
            case '-':
                ahead = peekAhead();
                if (ahead == '>') {
                    consume(); consume();
                    return new Token<>(LETTokenType.DERIVE, "->");
                }
                else if (isDigit(ahead)) {
                    return numberToken(NEGATIVE);
                } else if (ahead == '(') {
                    consume();
                    return new Token<>(LETTokenType.MINUS, "-");
                } else {
                    return nameToken();
                }
            default:
                if (isDigit(curr)) {
                    return numberToken(POSITIVE);
                }
                else {
                    return nameToken();
                }
            }
        }
        return new Token<>(LETTokenType.EOF_TYPE, "<EOF>");
    }
    
    private boolean isDigit(char c) {
        return (c >= '0' && c <= '9');
    }
    
    
    private char[] terminals = {
            '-',
            ',',
            '(',
            ')',
            ':',
            '[',
            ']',
            '_',
            '=',
    };
    
    private boolean isTerminal() {
        for (char tml : terminals) {
            if (curr == tml) {
                return true;
            }
        }
        return false;
    }
    
    private Token<LETTokenType> numberToken(boolean negative) {
        StringBuilder buf = new StringBuilder();
        if (negative) {
            buf.append('-');
            consume();
        }
        while (isDigit(curr)) {
            buf.append(curr);
            consume();
        }
        
        String num = buf.toString();
        return new Token<>(LETTokenType.NUMBER, num);
    }
    
    private Token<LETTokenType> nameToken() {
        
        StringBuilder buf = new StringBuilder();
        while (curr != EOF && !isWhiteSpace() && !isTerminal()) {
            buf.append(curr);
            consume();
        }
        
        String var = buf.toString();
        if (LETTokenUtils.dispatchTable.containsKey(var)) {
            // normal terminals
            return new Token<>(LETTokenUtils.dispatchTable.get(var), var);
        } else {
            // only letters
            return  new Token<>(LETTokenType.IDENTIFIER, var);
        }
    }

}
