import java.lang.System;
import java.io.*;
class Lexer {
	Yylex tokenizer;
	public  Lexer(String fileName) 
	{
	  try
	  {
	  tokenizer=new Yylex(new BufferedReader(new FileReader(fileName)));
	  }
	  catch(Exception e)
	  {
	  }	 
	}
	public Token nextToken()
	{
		Token next=null;
		try
		{
		 next=  tokenizer.getToken();
		}
		catch(Exception e)
		{
		}
		return next;
	}
	}


class Yylex {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

	//initialize  variables to be used by class
	int curlyBrackets = 0;
	int regularBrackets = 0;
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	Yylex (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	Yylex (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Yylex () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yychar = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;

//Add code to be executed on initialization of the lexer
	}

	private boolean yy_eof_done = false;
	private final int YYINITIAL = 0;
	private final int InCurly = 1;
	private final int InBrack = 2;
	private final int yy_state_dtrans[] = {
		0,
		55,
		55
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yychar = yychar
			+ yy_buffer_index - yy_buffer_start;
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NOT_ACCEPT,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NOT_ACCEPT,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NOT_ACCEPT,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NOT_ACCEPT,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NOT_ACCEPT,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"39:9,3,1,39:2,0,39:18,2,32,38,39:2,8,36,39,4,5,26,28,41,27,9,44,43:10,33,45" +
",31,30,31,39:2,42:26,34,39,35,39:2,40,16,42,10,42,19,23,18,42,20,42,17,25,2" +
"1,12,11,15,42,22,13,14,24,29,42:4,6,37,7,39:2,46:2")[0];

	private int yy_rmap[] = unpackFromString(1,84,
"0,1:9,2,1:2,3,4:3,5,1:3,6,7,1:2,8,1:4,8:2,9,10,8:6,9,11,1,12,1,13,14,15,16:" +
"2,17,18,10,19,1,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39" +
",40,41,42,43,44,45,8,46,47")[0];

	private int yy_nxt[][] = unpackFromString(48,47,
"-1,1,2,3,4,5,6,7,8,9,10,81:4,82,81:3,72,41,81,83,63,81:2,11,12,13,64,14,15," +
"16,17,18,19,43,47,51,54,57,20,81,21,22,23,24,-1:57,81,73,81:14,-1:3,81,-1:1" +
"2,81,65,-1:31,26,-1:48,42,-1:46,27,-1:59,21,-1:47,52,-1:12,81:16,-1:3,81,-1" +
":12,81,65,-1:5,40:36,32,40:7,-1:3,33:44,-1:11,81:11,75,81,25,81:2,-1:3,81,-" +
"1:12,81,65,-1:39,28,-1:11,45,-1:38,44,-1:16,81:12,30,81:3,-1:3,81,-1:12,81," +
"65,-1:40,29,-1:11,49:38,48,49:5,-1:11,81:12,31,81:3,-1:3,81,-1:12,81,65,-1:" +
"5,40:44,-1:11,53:16,-1:3,53,-1:12,53,-1:50,24,-1:10,81:9,34,81:6,-1:3,81,-1" +
":12,81,65,-1:4,45,49:44,-1:11,35,81:15,-1:3,81,-1:12,81,65,-1:13,81:4,36,81" +
":11,-1:3,81,-1:12,81,65,-1:13,81:4,37,81:11,-1:3,81,-1:12,81,65,-1:13,81:2," +
"38,81:13,-1:3,81,-1:12,81,65,-1:13,81:9,39,81:6,-1:3,81,-1:12,81,65,-1:13,8" +
"1,46,81:12,67,81,-1:3,81,-1:12,81,65,-1:13,81:6,50,81:9,-1:3,81,-1:12,81,65" +
",-1:13,53:16,-1:3,53,-1:12,53,65,-1:13,81:3,56,81:12,-1:3,81,-1:12,81,65,-1" +
":13,81:2,58,81:13,-1:3,81,-1:12,81,65,-1:13,81:3,59,81:12,-1:3,81,-1:12,81," +
"65,-1:13,81:12,60,81:3,-1:3,81,-1:12,81,65,-1:13,81:12,61,81:3,-1:3,81,-1:1" +
"2,81,65,-1:13,81:8,62,81:7,-1:3,81,-1:12,81,65,-1:13,81:15,66,-1:3,81,-1:12" +
",81,65,-1:13,81:2,68,81:13,-1:3,81,-1:12,81,65,-1:13,77,81:15,-1:3,81,-1:12" +
",81,65,-1:13,81:5,78,81:10,-1:3,81,-1:12,81,65,-1:13,81:4,79,81:11,-1:3,81," +
"-1:12,81,65,-1:13,81:7,80,81:8,-1:3,81,-1:12,81,65,-1:13,81,69,81:14,-1:3,8" +
"1,-1:12,81,65,-1:13,81:14,70,81,-1:3,81,-1:12,81,65,-1:13,81:6,71,81:9,-1:3" +
",81,-1:12,81,65,-1:13,81:6,74,81:9,-1:3,81,-1:12,81,65,-1:13,81:9,76,81:6,-" +
"1:3,81,-1:12,81,65,-1:3");

	public Token getToken ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

//Add code to be executed when the end of the file is reached
	if(yy_lexical_state == InCurly) {
	return new Token(Token.ERROR, "Missing a }");
	}
	if(yy_lexical_state == InBrack) {
	return new Token(Token.ERROR, "Missing a )");
	}
  	return (new Token(Token.EOF,"Done"));
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						{}
					case -2:
						break;
					case 2:
						{}
					case -3:
						break;
					case 3:
						{}
					case -4:
						break;
					case 4:
						{  regularBrackets++; return (new Token(Token.OPEN_PARAN,yytext()));}
					case -5:
						break;
					case 5:
						{   return (new Token(Token.CLOSE_PARAN,yytext()));}
					case -6:
						break;
					case 6:
						{  curlyBrackets++; return (new Token(Token.OPEN_CURLY,yytext()));}
					case -7:
						break;
					case 7:
						{  return (new Token(Token.CLOSE_CURLY,yytext()));}
					case -8:
						break;
					case 8:
						{ return (new Token(Token.PERCENT,yytext()));}
					case -9:
						break;
					case 9:
						{ return (new Token(Token.DOT,yytext()));}
					case -10:
						break;
					case 10:
						{ return (new Token(Token.IDENTIFIER,yytext()));}
					case -11:
						break;
					case 11:
						{ return (new Token(Token.ASTRISK,yytext()));}
					case -12:
						break;
					case 12:
						{ return (new Token(Token.MINUS,yytext()));}
					case -13:
						break;
					case 13:
						{ return (new Token(Token.PLUS,yytext()));}
					case -14:
						break;
					case 14:
						{ return (new Token(Token.EQUAL,yytext()));}
					case -15:
						break;
					case 15:
						{ return (new Token(Token.REL_OP,yytext()));}
					case -16:
						break;
					case 16:
						{
  return new Token(Token.ERROR, "Invalid input: " + yytext() + " in line " + (yyline+1)) ;
}
					case -17:
						break;
					case 17:
						{ return (new Token(Token.COLON,yytext()));}
					case -18:
						break;
					case 18:
						{ return (new Token(Token.OPEN_SQUARE,yytext()));}
					case -19:
						break;
					case 19:
						{ return (new Token(Token.CLOSE_SQUARE,yytext()));}
					case -20:
						break;
					case 20:
						{ return (new Token(Token.COMMA,yytext()));}
					case -21:
						break;
					case 21:
						{ return (new Token(Token.INT_LIT,yytext()));}
					case -22:
						break;
					case 22:
						{ return (new Token(Token.SLASH,yytext()));}
					case -23:
						break;
					case 23:
						{ return (new Token(Token.SEMI_COLON,yytext()));}
					case -24:
						break;
					case 24:
						
					case -25:
						break;
					case 25:
						{ return (new Token(Token.IF,yytext()));}
					case -26:
						break;
					case 26:
						{ return (new Token(Token.INCREMENT,yytext()));}
					case -27:
						break;
					case 27:
						{ return (new Token(Token.COLON_EQUAL,yytext()));}
					case -28:
						break;
					case 28:
						{ return (new Token(Token.AND_OP,yytext()));}
					case -29:
						break;
					case 29:
						{ return (new Token(Token.OR_OP,yytext()));}
					case -30:
						break;
					case 30:
						{ return (new Token(Token.FOR,yytext()));}
					case -31:
						break;
					case 31:
						{ return (new Token(Token.VAR,yytext()));}
					case -32:
						break;
					case 32:
						{ return (new Token(Token.STRING_LIT,yytext()));}
					case -33:
						break;
					case 33:
						{}
					case -34:
						break;
					case 34:
						{ return (new Token(Token.ELSE,yytext()));}
					case -35:
						break;
					case 35:
						{ return (new Token(Token.FUNC,yytext()));}
					case -36:
						break;
					case 36:
						{ return (new Token(Token.CONST,yytext()));}
					case -37:
						break;
					case 37:
						{ return (new Token(Token.IMPORT,yytext()));}
					case -38:
						break;
					case 38:
						{ return (new Token(Token.RETURN,yytext()));}
					case -39:
						break;
					case 39:
						{ return (new Token(Token.PACKAGE,yytext()));}
					case -40:
						break;
					case 41:
						{ return (new Token(Token.IDENTIFIER,yytext()));}
					case -41:
						break;
					case 42:
						{ return (new Token(Token.REL_OP,yytext()));}
					case -42:
						break;
					case 43:
						{
  return new Token(Token.ERROR, "Invalid input: " + yytext() + " in line " + (yyline+1)) ;
}
					case -43:
						break;
					case 44:
						{ return (new Token(Token.STRING_LIT,yytext()));}
					case -44:
						break;
					case 46:
						{ return (new Token(Token.IDENTIFIER,yytext()));}
					case -45:
						break;
					case 47:
						{
  return new Token(Token.ERROR, "Invalid input: " + yytext() + " in line " + (yyline+1)) ;
}
					case -46:
						break;
					case 48:
						{ return (new Token(Token.STRING_LIT,yytext()));}
					case -47:
						break;
					case 50:
						{ return (new Token(Token.IDENTIFIER,yytext()));}
					case -48:
						break;
					case 51:
						{
  return new Token(Token.ERROR, "Invalid input: " + yytext() + " in line " + (yyline+1)) ;
}
					case -49:
						break;
					case 53:
						{ return (new Token(Token.IDENTIFIER,yytext()));}
					case -50:
						break;
					case 54:
						{
  return new Token(Token.ERROR, "Invalid input: " + yytext() + " in line " + (yyline+1)) ;
}
					case -51:
						break;
					case 56:
						{ return (new Token(Token.IDENTIFIER,yytext()));}
					case -52:
						break;
					case 57:
						{
  return new Token(Token.ERROR, "Invalid input: " + yytext() + " in line " + (yyline+1)) ;
}
					case -53:
						break;
					case 58:
						{ return (new Token(Token.IDENTIFIER,yytext()));}
					case -54:
						break;
					case 59:
						{ return (new Token(Token.IDENTIFIER,yytext()));}
					case -55:
						break;
					case 60:
						{ return (new Token(Token.IDENTIFIER,yytext()));}
					case -56:
						break;
					case 61:
						{ return (new Token(Token.IDENTIFIER,yytext()));}
					case -57:
						break;
					case 62:
						{ return (new Token(Token.IDENTIFIER,yytext()));}
					case -58:
						break;
					case 63:
						{ return (new Token(Token.IDENTIFIER,yytext()));}
					case -59:
						break;
					case 64:
						{ return (new Token(Token.IDENTIFIER,yytext()));}
					case -60:
						break;
					case 65:
						{ return (new Token(Token.IDENTIFIER,yytext()));}
					case -61:
						break;
					case 66:
						{ return (new Token(Token.IDENTIFIER,yytext()));}
					case -62:
						break;
					case 67:
						{ return (new Token(Token.IDENTIFIER,yytext()));}
					case -63:
						break;
					case 68:
						{ return (new Token(Token.IDENTIFIER,yytext()));}
					case -64:
						break;
					case 69:
						{ return (new Token(Token.IDENTIFIER,yytext()));}
					case -65:
						break;
					case 70:
						{ return (new Token(Token.IDENTIFIER,yytext()));}
					case -66:
						break;
					case 71:
						{ return (new Token(Token.IDENTIFIER,yytext()));}
					case -67:
						break;
					case 72:
						{ return (new Token(Token.IDENTIFIER,yytext()));}
					case -68:
						break;
					case 73:
						{ return (new Token(Token.IDENTIFIER,yytext()));}
					case -69:
						break;
					case 74:
						{ return (new Token(Token.IDENTIFIER,yytext()));}
					case -70:
						break;
					case 75:
						{ return (new Token(Token.IDENTIFIER,yytext()));}
					case -71:
						break;
					case 76:
						{ return (new Token(Token.IDENTIFIER,yytext()));}
					case -72:
						break;
					case 77:
						{ return (new Token(Token.IDENTIFIER,yytext()));}
					case -73:
						break;
					case 78:
						{ return (new Token(Token.IDENTIFIER,yytext()));}
					case -74:
						break;
					case 79:
						{ return (new Token(Token.IDENTIFIER,yytext()));}
					case -75:
						break;
					case 80:
						{ return (new Token(Token.IDENTIFIER,yytext()));}
					case -76:
						break;
					case 81:
						{ return (new Token(Token.IDENTIFIER,yytext()));}
					case -77:
						break;
					case 82:
						{ return (new Token(Token.IDENTIFIER,yytext()));}
					case -78:
						break;
					case 83:
						{ return (new Token(Token.IDENTIFIER,yytext()));}
					case -79:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
