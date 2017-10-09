package no.uio.ifi.asp.scanner;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import no.uio.ifi.asp.main.*;

import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Scanner {
    private LineNumberReader sourceFile = null;
    private String curFileName;
    private ArrayList<Token> curLineTokens = new ArrayList<>();
    private int indents[] = new int[100];
    private int numIndents = 0;
    private final int tabDist = 4;

    private final Pattern strPattern = Pattern.compile("([\"'])(?:(?=(\\\\?))\\2.)*?\\1");
    private final Pattern intFloatPattern = Pattern.compile("(\\d+(?:\\.\\d+)?)");
    private final Pattern leftTrimPattern = Pattern.compile("^\\s+");

    public Scanner(String fileName) {
        curFileName = fileName;
        indents[0] = 0;
        numIndents = 1;

        try {
            sourceFile = new LineNumberReader(
                    new InputStreamReader(
                            new FileInputStream(fileName),
                            "UTF-8"));
        } catch (IOException e) {
            scannerError("Cannot read " + fileName + "!");
        }
    }


    private void scannerError(String message) {
        String m = "Asp scanner error";
        if (curLineNum() > 0)
            m += " on line " + curLineNum();
        m += ": " + message;

        Main.error(m);
    }


    public Token curToken() {
        while (curLineTokens.isEmpty()) {
            readNextLine();
        }
        return curLineTokens.get(0);
    }


    public void readNextToken() {
        if (!curLineTokens.isEmpty())
            curLineTokens.remove(0);
    }


    public boolean anyEqualToken() {
        for (Token t : curLineTokens) {
            if (t.kind == equalToken) return true;
        }
        return false;
    }

    private void readNextLine() {
        curLineTokens.clear();

        // Read the next line:
        String line = null;
        try {
            line = sourceFile.readLine();
            if (line == null) {
                sourceFile.close();
                sourceFile = null;
            } else {
                Main.log.noteSourceLine(curLineNum(), line);
            }
        } catch (IOException e) {
            sourceFile = null;
            scannerError("Unspecified I/O error!");
        }

        //-- Must be changed in part 1:
        int curLine = curLineNum();
        if (sourceFile != null) {
            if (!isEmptyLine(line)) {
                // replace tabs and count indents/dedents
                line = replaceTabs(line);
                // trim so we don't re-traverse whitespace
                line = leftTrim(line);
                // jump over lines with only comment blocks
                if (!isComment(line.charAt(0))) {
                    for (; ; ) {
                        line = scanNext(line, curLine);
                        if (line == null) {
                            // Terminate line:
                            curLineTokens.add(new Token(newLineToken, curLine));
                            break;
                        }
                    }
                }
            }
        } else {
            for (int indent : indents) {
                if (indent > 0) {
                    curLineTokens.add(new Token(dedentToken, curLine));
                } else {
                    break;
                }
            }
            curLineTokens.add(new Token(eofToken, curLine));
        }

        for (Token t : curLineTokens)
            Main.log.noteToken(t);
    }

    private String replaceTabs(String line) {
        StringBuilder sb = new StringBuilder(line);
        int n = 0;

        for (int i = 0; i < sb.length(); i++) {
            char c = sb.charAt(i);

            if (isTab(c)) {
                int wsCount = tabDist - (n++ % tabDist); // also post-incrementing n

                for (int j = 0; j < wsCount; j++) {
                    if (j == 0) {
                        sb.setCharAt(i, ' '); // replace \t
                    } else {
                        sb.insert(i + j, ' '); // append space
                    }
                }
            } else if (isWhiteSpace(c)) {
                n++;
            } else {
                break;
            }
        }
        shiftIndentStack(n);
        return sb.toString();
    }

    private void shiftIndentStack(int n) {
        if (n > indents[0]) {
            pushIndent(n);
            curLineTokens.add(new Token(indentToken, curLineNum()));
        } else if (n < indents[0]) {
            while (n < indents[0]) {
                popIndent();
                curLineTokens.add(new Token(dedentToken, curLineNum()));
            }
            if (n != indents[0]) {
                scannerError("indenteringsfeil");
            }
        }
    }

    private void pushIndent(int v) {
        System.arraycopy(indents, 0, indents, 1, indents.length - 1);
        indents[0] = v;
    }

    private int popIndent() {
        int i = indents[0];
        System.arraycopy(indents, 1, indents, 0, indents.length - 1);
        indents[indents.length - 1] = 0;
        return i;
    }

    private String scanNext(String line, int curLine) {
        if (line.isEmpty()) {
            // next line
            return null;
        }
        char c = line.charAt(0);
        int tokenLength = 1;

        if (isComment(c)) {
            // comment
            return null;

        } else if (isWhiteSpace(c)) {
            // gobble whitespace
            return line.substring(tokenLength);

        } else if (isLetterAZ(c)) {
            Token t = getVariableName(line, curLine);
            curLineTokens.add(t);
            tokenLength = t.name.length();

        } else if (isOperator(c)) { // operator or delimiter
            Token operator = getOperator(line, curLine);
            curLineTokens.add(operator);
            tokenLength = operator.kind.toString().length();

        } else if (isStringLiteral(c)) {
            Token strLiteral = getStringLit(line, curLine);
            // not terminated (enclosed) on the same line
            if (strLiteral == null) {
                scannerError(String.format("No closing quote %c.", c));
            }
            curLineTokens.add(strLiteral);
            tokenLength = strLiteral.stringLit.length() + 2; // +2 for the quotes
        } else if (isDigit(c)) {
            Token digitLiteral = getIntOrFloatLit(line, curLine);

            String digitStr = digitLiteral.stringLit;
            digitLiteral.stringLit = null;
            tokenLength = digitStr.length();

            switch (digitLiteral.kind) {
                case integerToken:
                    digitLiteral.integerLit = Integer.valueOf(digitStr);
                    break;
                case floatToken:
                    digitLiteral.floatLit = Double.valueOf(digitStr);
                    break;
            }
            curLineTokens.add(digitLiteral);
        }

        return line.substring(tokenLength);
    }

    private Token getIntOrFloatLit(String s, int lineNum) {
        Matcher m = intFloatPattern.matcher(s);
        if (m.find()) {
            String res = m.group();
            Token t;
            if (res.contains(".")) {
                t = new Token(floatToken, lineNum);
            } else {
                t = new Token(integerToken, lineNum);
            }
            t.stringLit = res;
            return t;
        }
        return null;
    }

    private Token getStringLit(String s, int lineNum) {
        // match either " or '
        // ignore " or ' if preceded by escape \
        // match closing quote with beginning qoute
        Matcher m = strPattern.matcher(s);
        if (m.find()) {
            Token t = new Token(stringToken, lineNum);
            t.stringLit = stripQuotes(m.group());
            return t;
        }
        return null;
    }

    private String leftTrim(String s) {
        // trim the string of whitespaces
        return leftTrimPattern.matcher(s).replaceAll("");
    }

    private String stripQuotes(String s) {
        return s.substring(1, s.length() - 1);
    }

    private Token getOperator(String s, int lineNum) {
        StringBuilder sb = new StringBuilder();
        sb.append(s.charAt(0));
        TokenKind tk = Token.getTokenKind(sb.toString(), ampToken, slashEqualToken);

        for (int i = 1; i < s.length(); i++) {
            sb.append(s.charAt(i));
            // Keep building the operator while the next character is also a valid operator
            TokenKind _tk = Token.getTokenKind(sb.toString(), ampToken, slashEqualToken);
            if (_tk != null) {
                tk = _tk;
            } else {
                break;
            }
        }
        Token op = new Token(tk, lineNum);
        op.name = tk.toString();
        return op;
    }

    private Token getVariableName(String s, int lineNum) {
        int endIndex = 1;
        // traverse char array until a non-letter & non-digit character is hit
        for (int i = endIndex; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!isAlphaNumeric(c)) {
                endIndex = i;
                break;
            } else if (i == s.length() - 1) {
                endIndex = s.length();
            }
        }
        Token t = new Token(nameToken, lineNum);
        t.name = s.substring(0, endIndex);
        t.checkResWords();
        return t;
    }

    private boolean isOperator(char c) {
        for (TokenKind tk : EnumSet.range(ampToken, slashEqualToken)) {
            if (c == '!' || tk.image.equals((Character.toString(c)))) {
                return true;
            }
        }
        return false;
    }

    public int curLineNum() {
        return sourceFile != null ? sourceFile.getLineNumber() : 0;
    }

    private int findIndent(String s) {
        int indent = 0;

        while (indent < s.length() && s.charAt(indent) == ' ') indent++;
        return indent;
    }

    private String expandLeadingTabs(String s) {
        // Discovered this replaceTabs method just after making my own
        String newS = "";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\t') {
                do {
                    newS += " ";
                } while (newS.length() % tabDist != 0);
            } else if (c == ' ') {
                newS += " ";
            } else {
                newS += s.substring(i);
                break;
            }
        }
        return newS;
    }

    private boolean isLetterAZ(char c) {
        return ('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z') || (c == '_');
    }

    private boolean isDigit(char c) {
        return '0' <= c && c <= '9';
    }

    private boolean isAlphaNumeric(char c) {
        return isLetterAZ(c) || isDigit(c);
    }

    private boolean isWhiteSpace(char c) {
        return c == ' ';
    }

    private boolean isComment(char c) {
        return c == '#';
    }

    private boolean isStringLiteral(char c) {
        return c == '\'' || c == '\"';
    }

    private boolean isTab(char c) {
        return c == '\t';
    }

    private boolean isEmptyLine(String s) {
        s = s.trim();
        return s.length() <= 0 || isComment(s.charAt(0));
    }

    public boolean isCompOpr() {
        ArrayList<TokenKind> compOperators = new ArrayList<>();

        compOperators.add(TokenKind.lessToken);
        compOperators.add(TokenKind.greaterToken);
        compOperators.add(TokenKind.doubleEqualToken);
        compOperators.add(TokenKind.greaterEqualToken);
        compOperators.add(TokenKind.lessEqualToken);
        compOperators.add(TokenKind.notEqualToken);

        return compOperators.stream().anyMatch(kind -> kind == curToken().kind);
    }

    public boolean isFactorPrefix() {
        TokenKind k = curToken().kind;
        return k == plusToken || k == minusToken;
    }

    public boolean isFactorOpr() {
        ArrayList<TokenKind> factorOperators = new ArrayList<>();

        factorOperators.add(TokenKind.astToken);
        factorOperators.add(TokenKind.slashToken);
        factorOperators.add(TokenKind.percentToken);
        factorOperators.add(TokenKind.doubleSlashToken);

        return factorOperators.stream().anyMatch(kind -> kind == curToken().kind);
    }

    public boolean isTermOpr() {
        TokenKind k = curToken().kind;
        return k == plusToken || k == minusToken;
    }
}
