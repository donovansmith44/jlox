import com.craftinginterpreters.lox.Scanner;
import com.craftinginterpreters.lox.Token;
import com.craftinginterpreters.lox.TokenType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ScannerTest {
    @Test
    public void tokenScanTest() {
        String tokenSource = "var value = 5;";
        Scanner scanner = new Scanner(tokenSource);
        Token var = new Token(TokenType.VAR, "var", null, 1);
        Token identifier = new Token(TokenType.IDENTIFIER, "value", null, 1);
        Token equal = new Token(TokenType.EQUAL, "=", null, 1);
        Token number = new Token(TokenType.NUMBER, "5", 5D, 1);
        Token semicolon = new Token(TokenType.SEMICOLON, ";", null, 1);
        Token eof = new Token(TokenType.EOF, "", null, 1);
        List<Token> expectedTokens = List.of(var, identifier, equal, number, semicolon, eof);

        List<Token> tokens = scanner.scanTokens();

        assertThat(tokens).usingRecursiveComparison().isEqualTo(expectedTokens);
    }

    @Test
    public void ScanSource_WhereSourceContainsOnlyIdentifiers_Success() {
        final String ARBITRARY_ALPHANUMERIC_IDENTIFIER = "abc123def456";
        final String ARBITRARY_IDENTIFER_STARTSW_UNDERSCORE = "_abc123def456";
        final String tokenSource =
                ARBITRARY_ALPHANUMERIC_IDENTIFIER + " " +
                ARBITRARY_IDENTIFER_STARTSW_UNDERSCORE;
        Scanner scanner = new Scanner(tokenSource);
        Token identifier1 = new Token(TokenType.IDENTIFIER, ARBITRARY_ALPHANUMERIC_IDENTIFIER, null, 1);
        Token identifier2 = new Token(TokenType.IDENTIFIER, ARBITRARY_IDENTIFER_STARTSW_UNDERSCORE, null, 1);
        Token eof = new Token(TokenType.EOF, "", null, 1);
        List<Token> expected = List.of(identifier1, identifier2, eof);

        List<Token> tokens = scanner.scanTokens();

        assertThat(tokens).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void ScanSource_WhereSourceContainsOnlyNumbers_Success() {
        final String ARBITRARY_INTEGER = "1";
        final String ARBITRARY_SINGLE_POINT_DOUBLE = "1.0";
        final String ARBITRARY_MULTI_POINT_DOUBLE = "1.2345";
        final String tokenSource =
                ARBITRARY_INTEGER + " " +
                ARBITRARY_SINGLE_POINT_DOUBLE + " " +
                ARBITRARY_MULTI_POINT_DOUBLE;
        Scanner scanner = new Scanner(tokenSource);
        Token number1 = new Token(TokenType.NUMBER, ARBITRARY_INTEGER, 1D, 1);
        Token number2 = new Token(TokenType.NUMBER, ARBITRARY_SINGLE_POINT_DOUBLE, 1D, 1);
        Token number3 = new Token(TokenType.NUMBER, ARBITRARY_MULTI_POINT_DOUBLE, 1.2345D, 1);
        Token eof = new Token(TokenType.EOF, "", null, 1);
        List<Token> expected = List.of(number1, number2, number3, eof);

        List<Token> tokens = scanner.scanTokens();

        assertThat(tokens).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void ScanSource_WhereSourceContainsOnlyStrings_Success() {
        final String EMPTY_STRING = "\"\"";
        final String ARBITRARY_STRING = "\"string\"";
        final String ARBITRARY_STRING_ON_NEWLINE = "\"\nstring on new line\"";
        final String tokenSource =
                EMPTY_STRING +
                ARBITRARY_STRING +
                ARBITRARY_STRING_ON_NEWLINE;
        Scanner scanner = new Scanner(tokenSource);
        Token emptyString = new Token(TokenType.STRING, EMPTY_STRING, "", 1);
        Token string = new Token(TokenType.STRING, ARBITRARY_STRING, "string", 1);
        Token stringOnNewLine = new Token(TokenType.STRING, ARBITRARY_STRING_ON_NEWLINE, "\nstring on new line", 2);
        Token eof = new Token(TokenType.EOF, "", null, 2);
        List<Token> expected = List.of(emptyString, string, stringOnNewLine, eof);

        List<Token> tokens = scanner.scanTokens();

        assertThat(tokens).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void ScanSource_WithComments_CommentsAreIgnored() {
        final String tokenSource =
                "// This is a comment." +
                "\nvar value = 5; // This is another comment.";
        Token var = new Token(TokenType.VAR, "var", null, 2);
        Token identifier = new Token(TokenType.IDENTIFIER, "value", null, 2);
        Token equal = new Token(TokenType.EQUAL, "=", null, 2);
        Token number = new Token(TokenType.NUMBER, "5", 5D, 2);
        Token semicolon = new Token(TokenType.SEMICOLON, ";", null, 2);
        Token eof = new Token(TokenType.EOF, "", null, 2);
        List<Token> expectedTokens = List.of(var, identifier, equal, number, semicolon, eof);
        final Scanner scanner = new Scanner(tokenSource);

        List<Token> tokens = scanner.scanTokens();

        assertThat(tokens).usingRecursiveComparison().isEqualTo(expectedTokens);
    }
}
