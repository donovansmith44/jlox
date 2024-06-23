import com.craftinginterpreters.lox.Token;
import com.craftinginterpreters.lox.TokenType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TokenTest {
    @Test
    public void tokenToString() {
        Token token = new Token(TokenType.IDENTIFIER, "testIdentifier", null, 1);
        String expected = "IDENTIFIER testIdentifier null";
        Assertions.assertEquals(expected, token.toString());
    }
}
