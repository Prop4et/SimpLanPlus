// Generated from SVM.g4 by ANTLR 4.7.1
package interpreter.lexer;

import java.util.List;
import java.util.ArrayList;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SVMLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, COL=31, LABEL=32, 
		NUMBER=33, BOOL=34, REGISTER=35, WS=36, LINECOMMENTS=37, ERR=38;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
		"T__17", "T__18", "T__19", "T__20", "T__21", "T__22", "T__23", "T__24", 
		"T__25", "T__26", "T__27", "T__28", "T__29", "COL", "LABEL", "NUMBER", 
		"BOOL", "REGISTER", "WS", "LINECOMMENTS", "ERR"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'push'", "'pop'", "'lw'", "'('", "')'", "'sw'", "'li'", "'add'", 
		"'sub'", "'mult'", "'div'", "'addi'", "'subi'", "'multi'", "'divi'", "'and'", 
		"'or'", "'not'", "'andb'", "'orb'", "'notb'", "'mv'", "'beq'", "'bleq'", 
		"'b'", "'jal'", "'jr'", "'del'", "'print'", "'halt'", "':'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, "COL", "LABEL", "NUMBER", "BOOL", 
		"REGISTER", "WS", "LINECOMMENTS", "ERR"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	private List<String> errors = new ArrayList<>();
	public int errorCount() {
		return errors.size();
	}


	public SVMLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "SVM.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 37:
			ERR_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void ERR_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			 errors.add("Invalid character: "+ getText()); 
			break;
		}
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2(\u010c\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\3\2\3\2\3\2\3\2\3\2\3\3\3"+
		"\3\3\3\3\3\3\4\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\t\3\t"+
		"\3\t\3\t\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\r"+
		"\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\23\3\23"+
		"\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\26\3\26\3\26"+
		"\3\26\3\26\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\31"+
		"\3\32\3\32\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\36"+
		"\3\36\3\36\3\36\3\36\3\36\3\37\3\37\3\37\3\37\3\37\3 \3 \3!\3!\7!\u00cd"+
		"\n!\f!\16!\u00d0\13!\3\"\3\"\5\"\u00d4\n\"\3\"\3\"\7\"\u00d8\n\"\f\"\16"+
		"\"\u00db\13\"\5\"\u00dd\n\"\3#\3#\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$"+
		"\3$\3$\3$\3$\3$\3$\3$\3$\3$\5$\u00f6\n$\3%\6%\u00f9\n%\r%\16%\u00fa\3"+
		"%\3%\3&\3&\7&\u0101\n&\f&\16&\u0104\13&\3&\3&\3\'\3\'\3\'\3\'\3\'\2\2"+
		"(\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20"+
		"\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37"+
		"= ?!A\"C#E$G%I&K\'M(\3\2\6\4\2C\\c|\5\2\62;C\\c|\5\2\13\f\17\17\"\"\4"+
		"\2\f\f\17\17\2\u0117\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2"+
		"\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3"+
		"\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2"+
		"\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2"+
		"\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2"+
		"\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2"+
		"\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\3O\3\2\2\2\5T"+
		"\3\2\2\2\7X\3\2\2\2\t[\3\2\2\2\13]\3\2\2\2\r_\3\2\2\2\17b\3\2\2\2\21e"+
		"\3\2\2\2\23i\3\2\2\2\25m\3\2\2\2\27r\3\2\2\2\31v\3\2\2\2\33{\3\2\2\2\35"+
		"\u0080\3\2\2\2\37\u0086\3\2\2\2!\u008b\3\2\2\2#\u008f\3\2\2\2%\u0092\3"+
		"\2\2\2\'\u0096\3\2\2\2)\u009b\3\2\2\2+\u009f\3\2\2\2-\u00a4\3\2\2\2/\u00a7"+
		"\3\2\2\2\61\u00ab\3\2\2\2\63\u00b0\3\2\2\2\65\u00b2\3\2\2\2\67\u00b6\3"+
		"\2\2\29\u00b9\3\2\2\2;\u00bd\3\2\2\2=\u00c3\3\2\2\2?\u00c8\3\2\2\2A\u00ca"+
		"\3\2\2\2C\u00dc\3\2\2\2E\u00de\3\2\2\2G\u00f5\3\2\2\2I\u00f8\3\2\2\2K"+
		"\u00fe\3\2\2\2M\u0107\3\2\2\2OP\7r\2\2PQ\7w\2\2QR\7u\2\2RS\7j\2\2S\4\3"+
		"\2\2\2TU\7r\2\2UV\7q\2\2VW\7r\2\2W\6\3\2\2\2XY\7n\2\2YZ\7y\2\2Z\b\3\2"+
		"\2\2[\\\7*\2\2\\\n\3\2\2\2]^\7+\2\2^\f\3\2\2\2_`\7u\2\2`a\7y\2\2a\16\3"+
		"\2\2\2bc\7n\2\2cd\7k\2\2d\20\3\2\2\2ef\7c\2\2fg\7f\2\2gh\7f\2\2h\22\3"+
		"\2\2\2ij\7u\2\2jk\7w\2\2kl\7d\2\2l\24\3\2\2\2mn\7o\2\2no\7w\2\2op\7n\2"+
		"\2pq\7v\2\2q\26\3\2\2\2rs\7f\2\2st\7k\2\2tu\7x\2\2u\30\3\2\2\2vw\7c\2"+
		"\2wx\7f\2\2xy\7f\2\2yz\7k\2\2z\32\3\2\2\2{|\7u\2\2|}\7w\2\2}~\7d\2\2~"+
		"\177\7k\2\2\177\34\3\2\2\2\u0080\u0081\7o\2\2\u0081\u0082\7w\2\2\u0082"+
		"\u0083\7n\2\2\u0083\u0084\7v\2\2\u0084\u0085\7k\2\2\u0085\36\3\2\2\2\u0086"+
		"\u0087\7f\2\2\u0087\u0088\7k\2\2\u0088\u0089\7x\2\2\u0089\u008a\7k\2\2"+
		"\u008a \3\2\2\2\u008b\u008c\7c\2\2\u008c\u008d\7p\2\2\u008d\u008e\7f\2"+
		"\2\u008e\"\3\2\2\2\u008f\u0090\7q\2\2\u0090\u0091\7t\2\2\u0091$\3\2\2"+
		"\2\u0092\u0093\7p\2\2\u0093\u0094\7q\2\2\u0094\u0095\7v\2\2\u0095&\3\2"+
		"\2\2\u0096\u0097\7c\2\2\u0097\u0098\7p\2\2\u0098\u0099\7f\2\2\u0099\u009a"+
		"\7d\2\2\u009a(\3\2\2\2\u009b\u009c\7q\2\2\u009c\u009d\7t\2\2\u009d\u009e"+
		"\7d\2\2\u009e*\3\2\2\2\u009f\u00a0\7p\2\2\u00a0\u00a1\7q\2\2\u00a1\u00a2"+
		"\7v\2\2\u00a2\u00a3\7d\2\2\u00a3,\3\2\2\2\u00a4\u00a5\7o\2\2\u00a5\u00a6"+
		"\7x\2\2\u00a6.\3\2\2\2\u00a7\u00a8\7d\2\2\u00a8\u00a9\7g\2\2\u00a9\u00aa"+
		"\7s\2\2\u00aa\60\3\2\2\2\u00ab\u00ac\7d\2\2\u00ac\u00ad\7n\2\2\u00ad\u00ae"+
		"\7g\2\2\u00ae\u00af\7s\2\2\u00af\62\3\2\2\2\u00b0\u00b1\7d\2\2\u00b1\64"+
		"\3\2\2\2\u00b2\u00b3\7l\2\2\u00b3\u00b4\7c\2\2\u00b4\u00b5\7n\2\2\u00b5"+
		"\66\3\2\2\2\u00b6\u00b7\7l\2\2\u00b7\u00b8\7t\2\2\u00b88\3\2\2\2\u00b9"+
		"\u00ba\7f\2\2\u00ba\u00bb\7g\2\2\u00bb\u00bc\7n\2\2\u00bc:\3\2\2\2\u00bd"+
		"\u00be\7r\2\2\u00be\u00bf\7t\2\2\u00bf\u00c0\7k\2\2\u00c0\u00c1\7p\2\2"+
		"\u00c1\u00c2\7v\2\2\u00c2<\3\2\2\2\u00c3\u00c4\7j\2\2\u00c4\u00c5\7c\2"+
		"\2\u00c5\u00c6\7n\2\2\u00c6\u00c7\7v\2\2\u00c7>\3\2\2\2\u00c8\u00c9\7"+
		"<\2\2\u00c9@\3\2\2\2\u00ca\u00ce\t\2\2\2\u00cb\u00cd\t\3\2\2\u00cc\u00cb"+
		"\3\2\2\2\u00cd\u00d0\3\2\2\2\u00ce\u00cc\3\2\2\2\u00ce\u00cf\3\2\2\2\u00cf"+
		"B\3\2\2\2\u00d0\u00ce\3\2\2\2\u00d1\u00dd\7\62\2\2\u00d2\u00d4\7/\2\2"+
		"\u00d3\u00d2\3\2\2\2\u00d3\u00d4\3\2\2\2\u00d4\u00d5\3\2\2\2\u00d5\u00d9"+
		"\4\63;\2\u00d6\u00d8\4\62;\2\u00d7\u00d6\3\2\2\2\u00d8\u00db\3\2\2\2\u00d9"+
		"\u00d7\3\2\2\2\u00d9\u00da\3\2\2\2\u00da\u00dd\3\2\2\2\u00db\u00d9\3\2"+
		"\2\2\u00dc\u00d1\3\2\2\2\u00dc\u00d3\3\2\2\2\u00ddD\3\2\2\2\u00de\u00df"+
		"\4\62\63\2\u00dfF\3\2\2\2\u00e0\u00e1\7&\2\2\u00e1\u00e2\7c\2\2\u00e2"+
		"\u00f6\7\62\2\2\u00e3\u00e4\7&\2\2\u00e4\u00e5\7v\2\2\u00e5\u00f6\7\63"+
		"\2\2\u00e6\u00e7\7&\2\2\u00e7\u00e8\7u\2\2\u00e8\u00f6\7r\2\2\u00e9\u00ea"+
		"\7&\2\2\u00ea\u00eb\7h\2\2\u00eb\u00f6\7r\2\2\u00ec\u00ed\7&\2\2\u00ed"+
		"\u00ee\7c\2\2\u00ee\u00f6\7n\2\2\u00ef\u00f0\7&\2\2\u00f0\u00f1\7t\2\2"+
		"\u00f1\u00f6\7c\2\2\u00f2\u00f3\7&\2\2\u00f3\u00f4\7j\2\2\u00f4\u00f6"+
		"\7r\2\2\u00f5\u00e0\3\2\2\2\u00f5\u00e3\3\2\2\2\u00f5\u00e6\3\2\2\2\u00f5"+
		"\u00e9\3\2\2\2\u00f5\u00ec\3\2\2\2\u00f5\u00ef\3\2\2\2\u00f5\u00f2\3\2"+
		"\2\2\u00f6H\3\2\2\2\u00f7\u00f9\t\4\2\2\u00f8\u00f7\3\2\2\2\u00f9\u00fa"+
		"\3\2\2\2\u00fa\u00f8\3\2\2\2\u00fa\u00fb\3\2\2\2\u00fb\u00fc\3\2\2\2\u00fc"+
		"\u00fd\b%\2\2\u00fdJ\3\2\2\2\u00fe\u0102\7=\2\2\u00ff\u0101\n\5\2\2\u0100"+
		"\u00ff\3\2\2\2\u0101\u0104\3\2\2\2\u0102\u0100\3\2\2\2\u0102\u0103\3\2"+
		"\2\2\u0103\u0105\3\2\2\2\u0104\u0102\3\2\2\2\u0105\u0106\b&\3\2\u0106"+
		"L\3\2\2\2\u0107\u0108\13\2\2\2\u0108\u0109\b\'\4\2\u0109\u010a\3\2\2\2"+
		"\u010a\u010b\b\'\2\2\u010bN\3\2\2\2\n\2\u00ce\u00d3\u00d9\u00dc\u00f5"+
		"\u00fa\u0102\5\2\3\2\b\2\2\3\'\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}