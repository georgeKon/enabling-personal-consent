package dbconsent.PostgreSQLParser;// Generated from PostgreSQLParser.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PostgreSQLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WHITESPACE=1, BLOCK_COMMENT=2, LINE_COMMENT=3, A_=4, ABORT=5, ABS=6, ABSOLUTE=7, 
		ACCESS=8, ACTION=9, ADA=10, ADD=11, ADMIN=12, AFTER=13, AGGREGATE=14, 
		ALIAS=15, ALL=16, ALLOCATE=17, ALSO=18, ALTER=19, ALWAYS=20, ANALYSE=21, 
		ANALYZE=22, AND=23, ANY=24, ARE=25, ARRAY=26, AS=27, ASC=28, ASENSITIVE=29, 
		ASSERTION=30, ASSIGNMENT=31, ASYMMETRIC=32, AT=33, ATOMIC=34, ATTRIBUTE=35, 
		ATTRIBUTES=36, AUTHORIZATION=37, AVG=38, BACKWARD=39, BEFORE=40, BEGIN=41, 
		BERNOULLI=42, BETWEEN=43, BIGINT=44, BINARY=45, BIT=46, BITVAR=47, BIT_LENGTH=48, 
		BLOB=49, BOOLEAN=50, BOTH=51, BREADTH=52, BY=53, C_=54, CACHE=55, CALL=56, 
		CALLED=57, CARDINALITY=58, CASCADE=59, CASCADED=60, CASE=61, CAST=62, 
		CATALOG=63, CATALOG_NAME=64, CEIL=65, CEILING=66, CHAIN=67, CHAR=68, CHARACTER=69, 
		CHARACTERISTICS=70, CHARACTERS=71, CHARACTER_LENGTH=72, CHARACTER_SET_CATALOG=73, 
		CHARACTER_SET_NAME=74, CHARACTER_SET_SCHEMA=75, CHAR_LENGTH=76, CHECK=77, 
		CHECKED=78, CHECKPOINT=79, CLASS=80, CLASS_ORIGIN=81, CLOB=82, CLOSE=83, 
		CLUSTER=84, COALESCE=85, COBOL=86, COLLATE=87, COLLATION=88, COLLATION_CATALOG=89, 
		COLLATION_NAME=90, COLLATION_SCHEMA=91, COLLECT=92, COLUMN=93, COLUMN_NAME=94, 
		COMMAND_FUNCTION=95, COMMAND_FUNCTION_CODE=96, COMMENT=97, COMMIT=98, 
		COMMITTED=99, COMPLETION=100, CONDITION=101, CONDITION_NUMBER=102, CONNECT=103, 
		CONNECTION=104, CONNECTION_NAME=105, CONSTRAINT=106, CONSTRAINTS=107, 
		CONSTRAINT_CATALOG=108, CONSTRAINT_NAME=109, CONSTRAINT_SCHEMA=110, CONSTRUCTOR=111, 
		CONTAINS=112, CONTINUE=113, CONVERSION=114, CONVERT=115, COPY=116, CORR=117, 
		CORRESPONDING=118, COUNT=119, COVAR_POP=120, COVAR_SAMP=121, CREATE=122, 
		CREATEDB=123, CREATEUSER=124, CROSS=125, CSV=126, CUBE=127, CUME_DIST=128, 
		CURRENT=129, CURRENT_DATE=130, CURRENT_DEFAULT_TRANSFORM_GROUP=131, CURRENT_PATH=132, 
		CURRENT_ROLE=133, CURRENT_TIME=134, CURRENT_TIMESTAMP=135, CURRENT_TRANSFORM_GROUP_FOR_TYPE=136, 
		CURRENT_USER=137, CURSOR=138, CURSOR_NAME=139, CYCLE=140, DATA=141, DATABASE=142, 
		DATE=143, DATETIME_INTERVAL_CODE=144, DATETIME_INTERVAL_PRECISION=145, 
		DAY=146, DEALLOCATE=147, DEC=148, DECIMAL=149, DECLARE=150, DEFAULT=151, 
		DEFAULTS=152, DEFERRABLE=153, DEFERRED=154, DEFINED=155, DEFINER=156, 
		DEGREE=157, DELETE=158, DELIMITER=159, DELIMITERS=160, DENSE_RANK=161, 
		DEPTH=162, DEREF=163, DERIVED=164, DESC=165, DESCRIBE=166, DESCRIPTOR=167, 
		DESTROY=168, DESTRUCTOR=169, DETERMINISTIC=170, DIAGNOSTICS=171, DICTIONARY=172, 
		DISCONNECT=173, DISPATCH=174, DISTINCT=175, DO=176, DOMAIN=177, DOUBLE=178, 
		DROP=179, DYNAMIC=180, DYNAMIC_FUNCTION=181, DYNAMIC_FUNCTION_CODE=182, 
		EACH=183, ELEMENT=184, ELSE=185, ENCODING=186, ENCRYPTED=187, END=188, 
		END_EXEC=189, EQUALS=190, ESCAPE=191, EVERY=192, EXCEPT=193, EXCEPTION=194, 
		EXCLUDE=195, EXCLUDING=196, EXCLUSIVE=197, EXEC=198, EXECUTE=199, EXISTING=200, 
		EXISTS=201, EXP=202, EXPLAIN=203, EXTERNAL=204, EXTRACT=205, FALSE=206, 
		FETCH=207, FILTER=208, FINAL=209, FIRST=210, FLOAT=211, FLOOR=212, FOLLOWING=213, 
		FOR=214, FORCE=215, FOREIGN=216, FORTRAN=217, FORWARD=218, FOUND=219, 
		FREE=220, FREEZE=221, FROM=222, FULL=223, FUNCTION=224, FUSION=225, G_=226, 
		GENERAL=227, GENERATED=228, GET=229, GLOBAL=230, GO=231, GOTO=232, GRANT=233, 
		GRANTED=234, GROUP=235, GROUPING=236, HANDLER=237, HAVING=238, HIERARCHY=239, 
		HOLD=240, HOST=241, HOUR=242, IDENTITY=243, IGNORE=244, ILIKE=245, IMMEDIATE=246, 
		IMMUTABLE=247, IMPLEMENTATION=248, IMPLICIT=249, IN=250, INCLUDING=251, 
		INCREMENT=252, INDEX=253, INDICATOR=254, INFIX=255, INHERITS=256, INITIALIZE=257, 
		INITIALLY=258, INNER=259, INOUT=260, INPUT=261, INSENSITIVE=262, INSERT=263, 
		INSTANCE=264, INSTANTIABLE=265, INSTEAD=266, INT=267, INTEGER=268, INTERSECT=269, 
		INTERSECTION=270, INTERVAL=271, INTO=272, INVOKER=273, IS=274, ISNULL=275, 
		ISOLATION=276, ITERATE=277, JOIN=278, K_=279, KEY=280, KEY_MEMBER=281, 
		KEY_TYPE=282, LANCOMPILER=283, LANGUAGE=284, LARGE=285, LAST=286, LATERAL=287, 
		LEADING=288, LEFT=289, LENGTH=290, LESS=291, LEVEL=292, LIKE=293, LIMIT=294, 
		LISTEN=295, LN=296, LOAD=297, LOCAL=298, LOCALTIME=299, LOCALTIMESTAMP=300, 
		LOCATION=301, LOCATOR=302, LOCK=303, LOCKED=304, LOWER=305, M_=306, MAP=307, 
		MATCH=308, MATCHED=309, MAX=310, MAXVALUE=311, MEMBER=312, MERGE=313, 
		MESSAGE_LENGTH=314, MESSAGE_OCTET_LENGTH=315, MESSAGE_TEXT=316, METHOD=317, 
		MIN=318, MINUTE=319, MINVALUE=320, MOD=321, MODE=322, MODIFIES=323, MODIFY=324, 
		MODULE=325, MONTH=326, MORE_=327, MOVE=328, MULTISET=329, MUMPS=330, NAME=331, 
		NAMES=332, NATIONAL=333, NATURAL=334, NCHAR=335, NCLOB=336, NESTING=337, 
		NEW=338, NEXT=339, NO=340, NOCREATEDB=341, NOCREATEUSER=342, NONE=343, 
		NORMALIZE=344, NORMALIZED=345, NOT=346, NOTHING=347, NOTIFY=348, NOTNULL=349, 
		NOWAIT=350, NULL=351, NULLABLE=352, NULLIF=353, NULLS=354, NUMBER=355, 
		NUMERIC=356, OBJECT=357, OCTETS=358, OCTET_LENGTH=359, OF=360, OFF=361, 
		OFFSET=362, OIDS=363, OLD=364, ON=365, ONLY=366, OPEN=367, OPERATION=368, 
		OPERATOR=369, OPTION=370, OPTIONS=371, OR=372, ORDER=373, ORDERING=374, 
		ORDINALITY=375, OTHERS=376, OUT=377, OUTER=378, OUTPUT=379, OVER=380, 
		OVERLAPS=381, OVERLAY=382, OVERRIDING=383, OWNER=384, PAD=385, PARAMETER=386, 
		PARAMETERS=387, PARAMETER_MODE=388, PARAMETER_NAME=389, PARAMETER_ORDINAL_POSITION=390, 
		PARAMETER_SPECIFIC_CATALOG=391, PARAMETER_SPECIFIC_NAME=392, PARAMETER_SPECIFIC_SCHEMA=393, 
		PARTIAL=394, PARTITION=395, PASCAL=396, PASSWORD=397, PATH=398, PERCENTILE_CONT=399, 
		PERCENTILE_DISC=400, PERCENT_RANK=401, PLACING=402, PLI=403, POSITION=404, 
		POSTFIX=405, POWER=406, PRECEDING=407, PRECISION=408, PREFIX=409, PREORDER=410, 
		PREPARE=411, PRESERVE=412, PRIMARY=413, PRIOR=414, PRIVILEGES=415, PROCEDURAL=416, 
		PROCEDURE=417, PUBLIC=418, QUOTE=419, RANGE=420, RANK=421, READ=422, READS=423, 
		REAL=424, RECHECK=425, RECURSIVE=426, REF=427, REFERENCES=428, REFERENCING=429, 
		REGR_AVGX=430, REGR_AVGY=431, REGR_COUNT=432, REGR_INTERCEPT=433, REGR_R2=434, 
		REGR_SLOPE=435, REGR_SXX=436, REGR_SXY=437, REGR_SYY=438, REINDEX=439, 
		RELATIVE=440, RELEASE=441, RENAME=442, REPEATABLE=443, REPLACE=444, RESET=445, 
		RESTART=446, RESTRICT=447, RESULT=448, RETURN=449, RETURNED_CARDINALITY=450, 
		RETURNED_LENGTH=451, RETURNED_OCTET_LENGTH=452, RETURNED_SQLSTATE=453, 
		RETURNS=454, REVOKE=455, RIGHT=456, ROLE=457, ROLLBACK=458, ROLLUP=459, 
		ROUTINE=460, ROUTINE_CATALOG=461, ROUTINE_NAME=462, ROUTINE_SCHEMA=463, 
		ROW=464, ROWS=465, ROW_COUNT=466, ROW_NUMBER=467, RULE=468, SAVEPOINT=469, 
		SCALE=470, SCHEMA=471, SCHEMA_NAME=472, SCOPE=473, SCOPE_CATALOG=474, 
		SCOPE_NAME=475, SCOPE_SCHEMA=476, SCROLL=477, SEARCH=478, SECOND=479, 
		SECTION=480, SECURITY=481, SELECT=482, SELF=483, SENSITIVE=484, SEQUENCE=485, 
		SERIALIZABLE=486, SERVER_NAME=487, SESSION=488, SESSION_USER=489, SET=490, 
		SETOF=491, SETS=492, SHARE=493, SHOW=494, SIMILAR=495, SIMPLE=496, SIZE=497, 
		SKIP_=498, SMALLINT=499, SOME=500, SOURCE=501, SPACE=502, SPECIFIC=503, 
		SPECIFICTYPE=504, SPECIFIC_NAME=505, SQL=506, SQLCODE=507, SQLERROR=508, 
		SQLEXCEPTION=509, SQLSTATE=510, SQLWARNING=511, SQRT=512, STABLE=513, 
		START=514, STATE=515, STATEMENT=516, STATIC=517, STATISTICS=518, STDDEV_POP=519, 
		STDDEV_SAMP=520, STDIN=521, STDOUT=522, STORAGE=523, STRICT=524, STRUCTURE=525, 
		STYLE=526, SUBCLASS_ORIGIN=527, SUBLIST=528, SUBMULTISET=529, SUBSTRING=530, 
		SUM=531, SYMMETRIC=532, SYSID=533, SYSTEM=534, SYSTEM_USER=535, TABLE=536, 
		TABLESAMPLE=537, TABLESPACE=538, TABLE_NAME=539, TEMP=540, TEMPLATE=541, 
		TEMPORARY=542, TERMINATE=543, THAN=544, THEN=545, TIES=546, TIME=547, 
		TIME_TZ=548, TIMESTAMP=549, TIMESTAMP_TZ=550, TIMEZONE_HOUR=551, TIMEZONE_MINUTE=552, 
		TO=553, TOAST=554, TOP_LEVEL_COUNT=555, TRAILING=556, TRANSACTION=557, 
		TRANSACTIONS_COMMITTED=558, TRANSACTIONS_ROLLED_BACK=559, TRANSACTION_ACTIVE=560, 
		TRANSFORM=561, TRANSFORMS=562, TRANSLATE=563, TRANSLATION=564, TREAT=565, 
		TRIGGER=566, TRIGGER_CATALOG=567, TRIGGER_NAME=568, TRIGGER_SCHEMA=569, 
		TRIM=570, TRUE=571, TRUNCATE=572, TRUSTED=573, TYPE=574, UESCAPE=575, 
		UNBOUNDED=576, UNCOMMITTED=577, UNDER=578, UNENCRYPTED=579, UNION=580, 
		UNIQUE=581, UNKNOWN=582, UNLISTEN=583, UNNAMED=584, UNNEST=585, UNTIL=586, 
		UPDATE=587, UPPER=588, USAGE=589, USER=590, USER_DEFINED_TYPE_CATALOG=591, 
		USER_DEFINED_TYPE_CODE=592, USER_DEFINED_TYPE_NAME=593, USER_DEFINED_TYPE_SCHEMA=594, 
		USING=595, VACUUM=596, VALID=597, VALIDATOR=598, VALUE=599, VALUES=600, 
		VARCHAR=601, VARIABLE=602, VARIADIC=603, VARYING=604, VAR_POP=605, VAR_SAMP=606, 
		VERBOSE=607, VIEW=608, VOLATILE=609, WHEN=610, WHENEVER=611, WHERE=612, 
		WIDTH_BUCKET=613, WINDOW=614, WITH=615, WITHIN=616, WITHOUT=617, WORK=618, 
		WRITE=619, YEAR=620, ZONE=621, ABSTIME=622, BOOL=623, BOX=624, FLOAT4=625, 
		FLOAT8=626, INT2=627, INT4=628, INT8=629, JSON=630, JSONB=631, LINE=632, 
		POINT=633, RELTIME=634, TEXT=635, COMMA=636, COLON=637, COLON_COLON=638, 
		DOLLAR=639, DOLLAR_DOLLAR=640, STAR=641, OPEN_PAREN=642, CLOSE_PAREN=643, 
		OPEN_BRACKET=644, CLOSE_BRACKET=645, BIT_STRING=646, REGEX_STRING=647, 
		NUMERIC_LITERAL=648, INTEGER_LITERAL=649, HEX_INTEGER_LITERAL=650, DOT=651, 
		SINGLEQ_STRING_LITERAL=652, DOUBLEQ_STRING_LITERAL=653, IDENTIFIER=654, 
		AMP=655, AMP_AMP=656, AMP_LT=657, AT_AT=658, AT_GT=659, AT_SIGN=660, BANG=661, 
		BANG_BANG=662, BANG_EQUAL=663, CARET=664, EQUAL=665, EQUAL_GT=666, GT=667, 
		GTE=668, GT_GT=669, HASH=670, HASH_EQ=671, HASH_GT=672, HASH_GT_GT=673, 
		HASH_HASH=674, HYPHEN_GT=675, HYPHEN_GT_GT=676, HYPHEN_PIPE_HYPHEN=677, 
		LT=678, LTE=679, LT_AT=680, LT_CARET=681, LT_GT=682, LT_HYPHEN_GT=683, 
		LT_LT=684, LT_LT_EQ=685, LT_QMARK_GT=686, MINUS=687, PERCENT=688, PIPE=689, 
		PIPE_PIPE=690, PIPE_PIPE_SLASH=691, PIPE_SLASH=692, PLUS=693, QMARK=694, 
		QMARK_AMP=695, QMARK_HASH=696, QMARK_HYPHEN=697, QMARK_PIPE=698, SLASH=699, 
		TIL=700, TIL_EQ=701, TIL_GTE_TIL=702, TIL_GT_TIL=703, TIL_LTE_TIL=704, 
		TIL_LT_TIL=705, TIL_STAR=706, TIL_TIL=707;
	public static final int
		RULE_select_stmt = 0, RULE_selector_clause = 1, RULE_column_selection = 2, 
		RULE_from_clause = 3, RULE_where_clause = 4, RULE_combine_clause = 5, 
		RULE_predicate = 6, RULE_condition = 7, RULE_predicateArgument = 8, RULE_value = 9, 
		RULE_value_list = 10, RULE_bool_expr = 11, RULE_type_literal = 12, RULE_output_name = 13, 
		RULE_table_name = 14, RULE_from_item = 15, RULE_join_type = 16, RULE_join_clause = 17, 
		RULE_identifier = 18, RULE_column_name = 19;
	public static final String[] ruleNames = {
		"select_stmt", "selector_clause", "column_selection", "from_clause", "where_clause", 
		"combine_clause", "predicate", "condition", "predicateArgument", "value", 
		"value_list", "bool_expr", "type_literal", "output_name", "table_name", 
		"from_item", "join_type", "join_clause", "identifier", "column_name"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		"','", "':'", "'::'", "'$'", "'$$'", "'*'", "'('", "')'", "'['", "']'", 
		null, null, null, null, null, "'.'", null, null, null, "'&'", "'&&'", 
		"'&<'", "'@@'", "'@>'", "'@'", "'!'", "'!!'", "'!='", "'^'", "'='", "'=>'", 
		"'>'", "'>='", "'>>'", "'#'", "'#='", "'#>'", "'#>>'", "'##'", "'->'", 
		"'->>'", "'-|-'", "'<'", "'<='", "'<@'", "'<^'", "'<>'", "'<->'", "'<<'", 
		"'<<='", "'<?>'", "'-'", "'%'", "'|'", "'||'", "'||/'", "'|/'", "'+'", 
		"'?'", "'?&'", "'?#'", "'?-'", "'?|'", "'/'", "'~'", "'~='", "'~>=~'", 
		"'~>~'", "'~<=~'", "'~<~'", "'~*'", "'~~'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "WHITESPACE", "BLOCK_COMMENT", "LINE_COMMENT", "A_", "ABORT", "ABS", 
		"ABSOLUTE", "ACCESS", "ACTION", "ADA", "ADD", "ADMIN", "AFTER", "AGGREGATE", 
		"ALIAS", "ALL", "ALLOCATE", "ALSO", "ALTER", "ALWAYS", "ANALYSE", "ANALYZE", 
		"AND", "ANY", "ARE", "ARRAY", "AS", "ASC", "ASENSITIVE", "ASSERTION", 
		"ASSIGNMENT", "ASYMMETRIC", "AT", "ATOMIC", "ATTRIBUTE", "ATTRIBUTES", 
		"AUTHORIZATION", "AVG", "BACKWARD", "BEFORE", "BEGIN", "BERNOULLI", "BETWEEN", 
		"BIGINT", "BINARY", "BIT", "BITVAR", "BIT_LENGTH", "BLOB", "BOOLEAN", 
		"BOTH", "BREADTH", "BY", "C_", "CACHE", "CALL", "CALLED", "CARDINALITY", 
		"CASCADE", "CASCADED", "CASE", "CAST", "CATALOG", "CATALOG_NAME", "CEIL", 
		"CEILING", "CHAIN", "CHAR", "CHARACTER", "CHARACTERISTICS", "CHARACTERS", 
		"CHARACTER_LENGTH", "CHARACTER_SET_CATALOG", "CHARACTER_SET_NAME", "CHARACTER_SET_SCHEMA", 
		"CHAR_LENGTH", "CHECK", "CHECKED", "CHECKPOINT", "CLASS", "CLASS_ORIGIN", 
		"CLOB", "CLOSE", "CLUSTER", "COALESCE", "COBOL", "COLLATE", "COLLATION", 
		"COLLATION_CATALOG", "COLLATION_NAME", "COLLATION_SCHEMA", "COLLECT", 
		"COLUMN", "COLUMN_NAME", "COMMAND_FUNCTION", "COMMAND_FUNCTION_CODE", 
		"COMMENT", "COMMIT", "COMMITTED", "COMPLETION", "CONDITION", "CONDITION_NUMBER", 
		"CONNECT", "CONNECTION", "CONNECTION_NAME", "CONSTRAINT", "CONSTRAINTS", 
		"CONSTRAINT_CATALOG", "CONSTRAINT_NAME", "CONSTRAINT_SCHEMA", "CONSTRUCTOR", 
		"CONTAINS", "CONTINUE", "CONVERSION", "CONVERT", "COPY", "CORR", "CORRESPONDING", 
		"COUNT", "COVAR_POP", "COVAR_SAMP", "CREATE", "CREATEDB", "CREATEUSER", 
		"CROSS", "CSV", "CUBE", "CUME_DIST", "CURRENT", "CURRENT_DATE", "CURRENT_DEFAULT_TRANSFORM_GROUP", 
		"CURRENT_PATH", "CURRENT_ROLE", "CURRENT_TIME", "CURRENT_TIMESTAMP", "CURRENT_TRANSFORM_GROUP_FOR_TYPE", 
		"CURRENT_USER", "CURSOR", "CURSOR_NAME", "CYCLE", "DATA", "DATABASE", 
		"DATE", "DATETIME_INTERVAL_CODE", "DATETIME_INTERVAL_PRECISION", "DAY", 
		"DEALLOCATE", "DEC", "DECIMAL", "DECLARE", "DEFAULT", "DEFAULTS", "DEFERRABLE", 
		"DEFERRED", "DEFINED", "DEFINER", "DEGREE", "DELETE", "DELIMITER", "DELIMITERS", 
		"DENSE_RANK", "DEPTH", "DEREF", "DERIVED", "DESC", "DESCRIBE", "DESCRIPTOR", 
		"DESTROY", "DESTRUCTOR", "DETERMINISTIC", "DIAGNOSTICS", "DICTIONARY", 
		"DISCONNECT", "DISPATCH", "DISTINCT", "DO", "DOMAIN", "DOUBLE", "DROP", 
		"DYNAMIC", "DYNAMIC_FUNCTION", "DYNAMIC_FUNCTION_CODE", "EACH", "ELEMENT", 
		"ELSE", "ENCODING", "ENCRYPTED", "END", "END_EXEC", "EQUALS", "ESCAPE", 
		"EVERY", "EXCEPT", "EXCEPTION", "EXCLUDE", "EXCLUDING", "EXCLUSIVE", "EXEC", 
		"EXECUTE", "EXISTING", "EXISTS", "EXP", "EXPLAIN", "EXTERNAL", "EXTRACT", 
		"FALSE", "FETCH", "FILTER", "FINAL", "FIRST", "FLOAT", "FLOOR", "FOLLOWING", 
		"FOR", "FORCE", "FOREIGN", "FORTRAN", "FORWARD", "FOUND", "FREE", "FREEZE", 
		"FROM", "FULL", "FUNCTION", "FUSION", "G_", "GENERAL", "GENERATED", "GET", 
		"GLOBAL", "GO", "GOTO", "GRANT", "GRANTED", "GROUP", "GROUPING", "HANDLER", 
		"HAVING", "HIERARCHY", "HOLD", "HOST", "HOUR", "IDENTITY", "IGNORE", "ILIKE", 
		"IMMEDIATE", "IMMUTABLE", "IMPLEMENTATION", "IMPLICIT", "IN", "INCLUDING", 
		"INCREMENT", "INDEX", "INDICATOR", "INFIX", "INHERITS", "INITIALIZE", 
		"INITIALLY", "INNER", "INOUT", "INPUT", "INSENSITIVE", "INSERT", "INSTANCE", 
		"INSTANTIABLE", "INSTEAD", "INT", "INTEGER", "INTERSECT", "INTERSECTION", 
		"INTERVAL", "INTO", "INVOKER", "IS", "ISNULL", "ISOLATION", "ITERATE", 
		"JOIN", "K_", "KEY", "KEY_MEMBER", "KEY_TYPE", "LANCOMPILER", "LANGUAGE", 
		"LARGE", "LAST", "LATERAL", "LEADING", "LEFT", "LENGTH", "LESS", "LEVEL", 
		"LIKE", "LIMIT", "LISTEN", "LN", "LOAD", "LOCAL", "LOCALTIME", "LOCALTIMESTAMP", 
		"LOCATION", "LOCATOR", "LOCK", "LOCKED", "LOWER", "M_", "MAP", "MATCH", 
		"MATCHED", "MAX", "MAXVALUE", "MEMBER", "MERGE", "MESSAGE_LENGTH", "MESSAGE_OCTET_LENGTH", 
		"MESSAGE_TEXT", "METHOD", "MIN", "MINUTE", "MINVALUE", "MOD", "MODE", 
		"MODIFIES", "MODIFY", "MODULE", "MONTH", "MORE_", "MOVE", "MULTISET", 
		"MUMPS", "NAME", "NAMES", "NATIONAL", "NATURAL", "NCHAR", "NCLOB", "NESTING", 
		"NEW", "NEXT", "NO", "NOCREATEDB", "NOCREATEUSER", "NONE", "NORMALIZE", 
		"NORMALIZED", "NOT", "NOTHING", "NOTIFY", "NOTNULL", "NOWAIT", "NULL", 
		"NULLABLE", "NULLIF", "NULLS", "NUMBER", "NUMERIC", "OBJECT", "OCTETS", 
		"OCTET_LENGTH", "OF", "OFF", "OFFSET", "OIDS", "OLD", "ON", "ONLY", "OPEN", 
		"OPERATION", "OPERATOR", "OPTION", "OPTIONS", "OR", "ORDER", "ORDERING", 
		"ORDINALITY", "OTHERS", "OUT", "OUTER", "OUTPUT", "OVER", "OVERLAPS", 
		"OVERLAY", "OVERRIDING", "OWNER", "PAD", "PARAMETER", "PARAMETERS", "PARAMETER_MODE", 
		"PARAMETER_NAME", "PARAMETER_ORDINAL_POSITION", "PARAMETER_SPECIFIC_CATALOG", 
		"PARAMETER_SPECIFIC_NAME", "PARAMETER_SPECIFIC_SCHEMA", "PARTIAL", "PARTITION", 
		"PASCAL", "PASSWORD", "PATH", "PERCENTILE_CONT", "PERCENTILE_DISC", "PERCENT_RANK", 
		"PLACING", "PLI", "POSITION", "POSTFIX", "POWER", "PRECEDING", "PRECISION", 
		"PREFIX", "PREORDER", "PREPARE", "PRESERVE", "PRIMARY", "PRIOR", "PRIVILEGES", 
		"PROCEDURAL", "PROCEDURE", "PUBLIC", "QUOTE", "RANGE", "RANK", "READ", 
		"READS", "REAL", "RECHECK", "RECURSIVE", "REF", "REFERENCES", "REFERENCING", 
		"REGR_AVGX", "REGR_AVGY", "REGR_COUNT", "REGR_INTERCEPT", "REGR_R2", "REGR_SLOPE", 
		"REGR_SXX", "REGR_SXY", "REGR_SYY", "REINDEX", "RELATIVE", "RELEASE", 
		"RENAME", "REPEATABLE", "REPLACE", "RESET", "RESTART", "RESTRICT", "RESULT", 
		"RETURN", "RETURNED_CARDINALITY", "RETURNED_LENGTH", "RETURNED_OCTET_LENGTH", 
		"RETURNED_SQLSTATE", "RETURNS", "REVOKE", "RIGHT", "ROLE", "ROLLBACK", 
		"ROLLUP", "ROUTINE", "ROUTINE_CATALOG", "ROUTINE_NAME", "ROUTINE_SCHEMA", 
		"ROW", "ROWS", "ROW_COUNT", "ROW_NUMBER", "RULE", "SAVEPOINT", "SCALE", 
		"SCHEMA", "SCHEMA_NAME", "SCOPE", "SCOPE_CATALOG", "SCOPE_NAME", "SCOPE_SCHEMA", 
		"SCROLL", "SEARCH", "SECOND", "SECTION", "SECURITY", "SELECT", "SELF", 
		"SENSITIVE", "SEQUENCE", "SERIALIZABLE", "SERVER_NAME", "SESSION", "SESSION_USER", 
		"SET", "SETOF", "SETS", "SHARE", "SHOW", "SIMILAR", "SIMPLE", "SIZE", 
		"SKIP_", "SMALLINT", "SOME", "SOURCE", "SPACE", "SPECIFIC", "SPECIFICTYPE", 
		"SPECIFIC_NAME", "SQL", "SQLCODE", "SQLERROR", "SQLEXCEPTION", "SQLSTATE", 
		"SQLWARNING", "SQRT", "STABLE", "START", "STATE", "STATEMENT", "STATIC", 
		"STATISTICS", "STDDEV_POP", "STDDEV_SAMP", "STDIN", "STDOUT", "STORAGE", 
		"STRICT", "STRUCTURE", "STYLE", "SUBCLASS_ORIGIN", "SUBLIST", "SUBMULTISET", 
		"SUBSTRING", "SUM", "SYMMETRIC", "SYSID", "SYSTEM", "SYSTEM_USER", "TABLE", 
		"TABLESAMPLE", "TABLESPACE", "TABLE_NAME", "TEMP", "TEMPLATE", "TEMPORARY", 
		"TERMINATE", "THAN", "THEN", "TIES", "TIME", "TIME_TZ", "TIMESTAMP", "TIMESTAMP_TZ", 
		"TIMEZONE_HOUR", "TIMEZONE_MINUTE", "TO", "TOAST", "TOP_LEVEL_COUNT", 
		"TRAILING", "TRANSACTION", "TRANSACTIONS_COMMITTED", "TRANSACTIONS_ROLLED_BACK", 
		"TRANSACTION_ACTIVE", "TRANSFORM", "TRANSFORMS", "TRANSLATE", "TRANSLATION", 
		"TREAT", "TRIGGER", "TRIGGER_CATALOG", "TRIGGER_NAME", "TRIGGER_SCHEMA", 
		"TRIM", "TRUE", "TRUNCATE", "TRUSTED", "TYPE", "UESCAPE", "UNBOUNDED", 
		"UNCOMMITTED", "UNDER", "UNENCRYPTED", "UNION", "UNIQUE", "UNKNOWN", "UNLISTEN", 
		"UNNAMED", "UNNEST", "UNTIL", "UPDATE", "UPPER", "USAGE", "USER", "USER_DEFINED_TYPE_CATALOG", 
		"USER_DEFINED_TYPE_CODE", "USER_DEFINED_TYPE_NAME", "USER_DEFINED_TYPE_SCHEMA", 
		"USING", "VACUUM", "VALID", "VALIDATOR", "VALUE", "VALUES", "VARCHAR", 
		"VARIABLE", "VARIADIC", "VARYING", "VAR_POP", "VAR_SAMP", "VERBOSE", "VIEW", 
		"VOLATILE", "WHEN", "WHENEVER", "WHERE", "WIDTH_BUCKET", "WINDOW", "WITH", 
		"WITHIN", "WITHOUT", "WORK", "WRITE", "YEAR", "ZONE", "ABSTIME", "BOOL", 
		"BOX", "FLOAT4", "FLOAT8", "INT2", "INT4", "INT8", "JSON", "JSONB", "LINE", 
		"POINT", "RELTIME", "TEXT", "COMMA", "COLON", "COLON_COLON", "DOLLAR", 
		"DOLLAR_DOLLAR", "STAR", "OPEN_PAREN", "CLOSE_PAREN", "OPEN_BRACKET", 
		"CLOSE_BRACKET", "BIT_STRING", "REGEX_STRING", "NUMERIC_LITERAL", "INTEGER_LITERAL", 
		"HEX_INTEGER_LITERAL", "DOT", "SINGLEQ_STRING_LITERAL", "DOUBLEQ_STRING_LITERAL", 
		"IDENTIFIER", "AMP", "AMP_AMP", "AMP_LT", "AT_AT", "AT_GT", "AT_SIGN", 
		"BANG", "BANG_BANG", "BANG_EQUAL", "CARET", "EQUAL", "EQUAL_GT", "GT", 
		"GTE", "GT_GT", "HASH", "HASH_EQ", "HASH_GT", "HASH_GT_GT", "HASH_HASH", 
		"HYPHEN_GT", "HYPHEN_GT_GT", "HYPHEN_PIPE_HYPHEN", "LT", "LTE", "LT_AT", 
		"LT_CARET", "LT_GT", "LT_HYPHEN_GT", "LT_LT", "LT_LT_EQ", "LT_QMARK_GT", 
		"MINUS", "PERCENT", "PIPE", "PIPE_PIPE", "PIPE_PIPE_SLASH", "PIPE_SLASH", 
		"PLUS", "QMARK", "QMARK_AMP", "QMARK_HASH", "QMARK_HYPHEN", "QMARK_PIPE", 
		"SLASH", "TIL", "TIL_EQ", "TIL_GTE_TIL", "TIL_GT_TIL", "TIL_LTE_TIL", 
		"TIL_LT_TIL", "TIL_STAR", "TIL_TIL"
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

	@Override
	public String getGrammarFileName() { return "PostgreSQLParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public PostgreSQLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class Select_stmtContext extends ParserRuleContext {
		public TerminalNode SELECT() { return getToken(PostgreSQLParser.SELECT, 0); }
		public Selector_clauseContext selector_clause() {
			return getRuleContext(Selector_clauseContext.class,0);
		}
		public From_clauseContext from_clause() {
			return getRuleContext(From_clauseContext.class,0);
		}
		public TerminalNode EOF() { return getToken(PostgreSQLParser.EOF, 0); }
		public Where_clauseContext where_clause() {
			return getRuleContext(Where_clauseContext.class,0);
		}
		public Select_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_select_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).enterSelect_stmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).exitSelect_stmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PostgreSQLParserVisitor ) return ((PostgreSQLParserVisitor<? extends T>)visitor).visitSelect_stmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Select_stmtContext select_stmt() throws RecognitionException {
		Select_stmtContext _localctx = new Select_stmtContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_select_stmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(40);
			match(SELECT);
			setState(41);
			selector_clause();
			setState(42);
			from_clause();
			setState(44);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WHERE) {
				{
				setState(43);
				where_clause();
				}
			}

			setState(46);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Selector_clauseContext extends ParserRuleContext {
		public List<Column_selectionContext> column_selection() {
			return getRuleContexts(Column_selectionContext.class);
		}
		public Column_selectionContext column_selection(int i) {
			return getRuleContext(Column_selectionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(PostgreSQLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(PostgreSQLParser.COMMA, i);
		}
		public Selector_clauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selector_clause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).enterSelector_clause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).exitSelector_clause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PostgreSQLParserVisitor ) return ((PostgreSQLParserVisitor<? extends T>)visitor).visitSelector_clause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Selector_clauseContext selector_clause() throws RecognitionException {
		Selector_clauseContext _localctx = new Selector_clauseContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_selector_clause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(48);
			column_selection();
			setState(53);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(49);
				match(COMMA);
				setState(50);
				column_selection();
				}
				}
				setState(55);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Column_selectionContext extends ParserRuleContext {
		public TerminalNode STAR() { return getToken(PostgreSQLParser.STAR, 0); }
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public Output_nameContext output_name() {
			return getRuleContext(Output_nameContext.class,0);
		}
		public TerminalNode AS() { return getToken(PostgreSQLParser.AS, 0); }
		public Column_selectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_column_selection; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).enterColumn_selection(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).exitColumn_selection(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PostgreSQLParserVisitor ) return ((PostgreSQLParserVisitor<? extends T>)visitor).visitColumn_selection(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Column_selectionContext column_selection() throws RecognitionException {
		Column_selectionContext _localctx = new Column_selectionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_column_selection);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(64);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case STAR:
				{
				setState(56);
				match(STAR);
				}
				break;
			case IDENTIFIER:
				{
				{
				setState(57);
				identifier();
				setState(62);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==AS || _la==IDENTIFIER) {
					{
					setState(59);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==AS) {
						{
						setState(58);
						match(AS);
						}
					}

					setState(61);
					output_name();
					}
				}

				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class From_clauseContext extends ParserRuleContext {
		public TerminalNode FROM() { return getToken(PostgreSQLParser.FROM, 0); }
		public List<From_itemContext> from_item() {
			return getRuleContexts(From_itemContext.class);
		}
		public From_itemContext from_item(int i) {
			return getRuleContext(From_itemContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(PostgreSQLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(PostgreSQLParser.COMMA, i);
		}
		public From_clauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_from_clause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).enterFrom_clause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).exitFrom_clause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PostgreSQLParserVisitor ) return ((PostgreSQLParserVisitor<? extends T>)visitor).visitFrom_clause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final From_clauseContext from_clause() throws RecognitionException {
		From_clauseContext _localctx = new From_clauseContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_from_clause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66);
			match(FROM);
			setState(67);
			from_item(0);
			setState(72);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(68);
				match(COMMA);
				setState(69);
				from_item(0);
				}
				}
				setState(74);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Where_clauseContext extends ParserRuleContext {
		public TerminalNode WHERE() { return getToken(PostgreSQLParser.WHERE, 0); }
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class,0);
		}
		public Where_clauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_where_clause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).enterWhere_clause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).exitWhere_clause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PostgreSQLParserVisitor ) return ((PostgreSQLParserVisitor<? extends T>)visitor).visitWhere_clause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Where_clauseContext where_clause() throws RecognitionException {
		Where_clauseContext _localctx = new Where_clauseContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_where_clause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			match(WHERE);
			setState(76);
			predicate(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Combine_clauseContext extends ParserRuleContext {
		public Select_stmtContext select_stmt() {
			return getRuleContext(Select_stmtContext.class,0);
		}
		public TerminalNode UNION() { return getToken(PostgreSQLParser.UNION, 0); }
		public TerminalNode INTERSECT() { return getToken(PostgreSQLParser.INTERSECT, 0); }
		public TerminalNode EXCEPT() { return getToken(PostgreSQLParser.EXCEPT, 0); }
		public TerminalNode ALL() { return getToken(PostgreSQLParser.ALL, 0); }
		public TerminalNode DISTINCT() { return getToken(PostgreSQLParser.DISTINCT, 0); }
		public Combine_clauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_combine_clause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).enterCombine_clause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).exitCombine_clause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PostgreSQLParserVisitor ) return ((PostgreSQLParserVisitor<? extends T>)visitor).visitCombine_clause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Combine_clauseContext combine_clause() throws RecognitionException {
		Combine_clauseContext _localctx = new Combine_clauseContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_combine_clause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78);
			_la = _input.LA(1);
			if ( !(_la==EXCEPT || _la==INTERSECT || _la==UNION) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(80);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ALL || _la==DISTINCT) {
				{
				setState(79);
				_la = _input.LA(1);
				if ( !(_la==ALL || _la==DISTINCT) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(82);
			select_stmt();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PredicateContext extends ParserRuleContext {
		public PredicateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_predicate; }
	 
		public PredicateContext() { }
		public void copyFrom(PredicateContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class PredicateNOTContext extends PredicateContext {
		public TerminalNode NOT() { return getToken(PostgreSQLParser.NOT, 0); }
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class,0);
		}
		public PredicateNOTContext(PredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).enterPredicateNOT(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).exitPredicateNOT(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PostgreSQLParserVisitor ) return ((PostgreSQLParserVisitor<? extends T>)visitor).visitPredicateNOT(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PredicateConditionalContext extends PredicateContext {
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public PredicateConditionalContext(PredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).enterPredicateConditional(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).exitPredicateConditional(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PostgreSQLParserVisitor ) return ((PostgreSQLParserVisitor<? extends T>)visitor).visitPredicateConditional(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PredicateANDContext extends PredicateContext {
		public List<PredicateContext> predicate() {
			return getRuleContexts(PredicateContext.class);
		}
		public PredicateContext predicate(int i) {
			return getRuleContext(PredicateContext.class,i);
		}
		public TerminalNode AND() { return getToken(PostgreSQLParser.AND, 0); }
		public PredicateANDContext(PredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).enterPredicateAND(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).exitPredicateAND(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PostgreSQLParserVisitor ) return ((PostgreSQLParserVisitor<? extends T>)visitor).visitPredicateAND(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PredicateParenContext extends PredicateContext {
		public TerminalNode OPEN_PAREN() { return getToken(PostgreSQLParser.OPEN_PAREN, 0); }
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class,0);
		}
		public TerminalNode CLOSE_PAREN() { return getToken(PostgreSQLParser.CLOSE_PAREN, 0); }
		public PredicateParenContext(PredicateContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).enterPredicateParen(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).exitPredicateParen(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PostgreSQLParserVisitor ) return ((PostgreSQLParserVisitor<? extends T>)visitor).visitPredicateParen(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PredicateContext predicate() throws RecognitionException {
		return predicate(0);
	}

	private PredicateContext predicate(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		PredicateContext _localctx = new PredicateContext(_ctx, _parentState);
		PredicateContext _prevctx = _localctx;
		int _startState = 12;
		enterRecursionRule(_localctx, 12, RULE_predicate, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(92);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				{
				_localctx = new PredicateConditionalContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(85);
				condition();
				}
				break;
			case 2:
				{
				_localctx = new PredicateParenContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(86);
				match(OPEN_PAREN);
				setState(87);
				predicate(0);
				setState(88);
				match(CLOSE_PAREN);
				}
				break;
			case 3:
				{
				_localctx = new PredicateNOTContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(90);
				match(NOT);
				setState(91);
				predicate(1);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(99);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new PredicateANDContext(new PredicateContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_predicate);
					setState(94);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(95);
					match(AND);
					setState(96);
					predicate(3);
					}
					} 
				}
				setState(101);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ConditionContext extends ParserRuleContext {
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
	 
		public ConditionContext() { }
		public void copyFrom(ConditionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ConditionComparisonContext extends ConditionContext {
		public List<PredicateArgumentContext> predicateArgument() {
			return getRuleContexts(PredicateArgumentContext.class);
		}
		public PredicateArgumentContext predicateArgument(int i) {
			return getRuleContext(PredicateArgumentContext.class,i);
		}
		public TerminalNode LT() { return getToken(PostgreSQLParser.LT, 0); }
		public TerminalNode GT() { return getToken(PostgreSQLParser.GT, 0); }
		public TerminalNode LTE() { return getToken(PostgreSQLParser.LTE, 0); }
		public TerminalNode GTE() { return getToken(PostgreSQLParser.GTE, 0); }
		public ConditionComparisonContext(ConditionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).enterConditionComparison(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).exitConditionComparison(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PostgreSQLParserVisitor ) return ((PostgreSQLParserVisitor<? extends T>)visitor).visitConditionComparison(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ConditionIsBooleanOrNullContext extends ConditionContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public TerminalNode ISNULL() { return getToken(PostgreSQLParser.ISNULL, 0); }
		public TerminalNode NOTNULL() { return getToken(PostgreSQLParser.NOTNULL, 0); }
		public TerminalNode IS() { return getToken(PostgreSQLParser.IS, 0); }
		public Bool_exprContext bool_expr() {
			return getRuleContext(Bool_exprContext.class,0);
		}
		public TerminalNode NULL() { return getToken(PostgreSQLParser.NULL, 0); }
		public TerminalNode NOT() { return getToken(PostgreSQLParser.NOT, 0); }
		public ConditionIsBooleanOrNullContext(ConditionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).enterConditionIsBooleanOrNull(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).exitConditionIsBooleanOrNull(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PostgreSQLParserVisitor ) return ((PostgreSQLParserVisitor<? extends T>)visitor).visitConditionIsBooleanOrNull(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ConditionEqualContext extends ConditionContext {
		public List<PredicateArgumentContext> predicateArgument() {
			return getRuleContexts(PredicateArgumentContext.class);
		}
		public PredicateArgumentContext predicateArgument(int i) {
			return getRuleContext(PredicateArgumentContext.class,i);
		}
		public TerminalNode EQUAL() { return getToken(PostgreSQLParser.EQUAL, 0); }
		public ConditionEqualContext(ConditionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).enterConditionEqual(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).exitConditionEqual(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PostgreSQLParserVisitor ) return ((PostgreSQLParserVisitor<? extends T>)visitor).visitConditionEqual(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ConditionNotEqualContext extends ConditionContext {
		public List<PredicateArgumentContext> predicateArgument() {
			return getRuleContexts(PredicateArgumentContext.class);
		}
		public PredicateArgumentContext predicateArgument(int i) {
			return getRuleContext(PredicateArgumentContext.class,i);
		}
		public TerminalNode LT_GT() { return getToken(PostgreSQLParser.LT_GT, 0); }
		public TerminalNode BANG_EQUAL() { return getToken(PostgreSQLParser.BANG_EQUAL, 0); }
		public ConditionNotEqualContext(ConditionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).enterConditionNotEqual(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).exitConditionNotEqual(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PostgreSQLParserVisitor ) return ((PostgreSQLParserVisitor<? extends T>)visitor).visitConditionNotEqual(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_condition);
		int _la;
		try {
			setState(126);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				_localctx = new ConditionEqualContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(102);
				predicateArgument();
				setState(103);
				match(EQUAL);
				setState(104);
				predicateArgument();
				}
				break;
			case 2:
				_localctx = new ConditionNotEqualContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(106);
				predicateArgument();
				setState(107);
				_la = _input.LA(1);
				if ( !(_la==BANG_EQUAL || _la==LT_GT) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(108);
				predicateArgument();
				}
				break;
			case 3:
				_localctx = new ConditionComparisonContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(110);
				predicateArgument();
				setState(111);
				_la = _input.LA(1);
				if ( !(((((_la - 667)) & ~0x3f) == 0 && ((1L << (_la - 667)) & ((1L << (GT - 667)) | (1L << (GTE - 667)) | (1L << (LT - 667)) | (1L << (LTE - 667)))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(112);
				predicateArgument();
				}
				break;
			case 4:
				_localctx = new ConditionIsBooleanOrNullContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(114);
				identifier();
				setState(124);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case IS:
					{
					{
					setState(115);
					match(IS);
					setState(117);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
					case 1:
						{
						setState(116);
						match(NOT);
						}
						break;
					}
					setState(121);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case FALSE:
					case NOT:
					case TRUE:
						{
						setState(119);
						bool_expr(0);
						}
						break;
					case NULL:
						{
						setState(120);
						match(NULL);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					}
					break;
				case ISNULL:
				case NOTNULL:
					{
					setState(123);
					_la = _input.LA(1);
					if ( !(_la==ISNULL || _la==NOTNULL) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PredicateArgumentContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public PredicateArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_predicateArgument; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).enterPredicateArgument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).exitPredicateArgument(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PostgreSQLParserVisitor ) return ((PostgreSQLParserVisitor<? extends T>)visitor).visitPredicateArgument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PredicateArgumentContext predicateArgument() throws RecognitionException {
		PredicateArgumentContext _localctx = new PredicateArgumentContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_predicateArgument);
		try {
			setState(130);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(128);
				identifier();
				}
				break;
			case FALSE:
			case NOT:
			case NULL:
			case TRUE:
			case INTEGER_LITERAL:
			case SINGLEQ_STRING_LITERAL:
			case DOUBLEQ_STRING_LITERAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(129);
				value();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValueContext extends ParserRuleContext {
		public TerminalNode NULL() { return getToken(PostgreSQLParser.NULL, 0); }
		public TerminalNode INTEGER_LITERAL() { return getToken(PostgreSQLParser.INTEGER_LITERAL, 0); }
		public Bool_exprContext bool_expr() {
			return getRuleContext(Bool_exprContext.class,0);
		}
		public TerminalNode SINGLEQ_STRING_LITERAL() { return getToken(PostgreSQLParser.SINGLEQ_STRING_LITERAL, 0); }
		public TerminalNode DOUBLEQ_STRING_LITERAL() { return getToken(PostgreSQLParser.DOUBLEQ_STRING_LITERAL, 0); }
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).exitValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PostgreSQLParserVisitor ) return ((PostgreSQLParserVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_value);
		try {
			setState(137);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NULL:
				enterOuterAlt(_localctx, 1);
				{
				setState(132);
				match(NULL);
				}
				break;
			case INTEGER_LITERAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(133);
				match(INTEGER_LITERAL);
				}
				break;
			case FALSE:
			case NOT:
			case TRUE:
				enterOuterAlt(_localctx, 3);
				{
				setState(134);
				bool_expr(0);
				}
				break;
			case SINGLEQ_STRING_LITERAL:
				enterOuterAlt(_localctx, 4);
				{
				setState(135);
				match(SINGLEQ_STRING_LITERAL);
				}
				break;
			case DOUBLEQ_STRING_LITERAL:
				enterOuterAlt(_localctx, 5);
				{
				setState(136);
				match(DOUBLEQ_STRING_LITERAL);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Value_listContext extends ParserRuleContext {
		public TerminalNode OPEN_PAREN() { return getToken(PostgreSQLParser.OPEN_PAREN, 0); }
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public TerminalNode CLOSE_PAREN() { return getToken(PostgreSQLParser.CLOSE_PAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(PostgreSQLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(PostgreSQLParser.COMMA, i);
		}
		public Value_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).enterValue_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).exitValue_list(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PostgreSQLParserVisitor ) return ((PostgreSQLParserVisitor<? extends T>)visitor).visitValue_list(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Value_listContext value_list() throws RecognitionException {
		Value_listContext _localctx = new Value_listContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_value_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(139);
			match(OPEN_PAREN);
			setState(140);
			value();
			setState(145);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(141);
				match(COMMA);
				setState(142);
				value();
				}
				}
				setState(147);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(148);
			match(CLOSE_PAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Bool_exprContext extends ParserRuleContext {
		public TerminalNode TRUE() { return getToken(PostgreSQLParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(PostgreSQLParser.FALSE, 0); }
		public TerminalNode NOT() { return getToken(PostgreSQLParser.NOT, 0); }
		public List<Bool_exprContext> bool_expr() {
			return getRuleContexts(Bool_exprContext.class);
		}
		public Bool_exprContext bool_expr(int i) {
			return getRuleContext(Bool_exprContext.class,i);
		}
		public TerminalNode AND() { return getToken(PostgreSQLParser.AND, 0); }
		public TerminalNode OR() { return getToken(PostgreSQLParser.OR, 0); }
		public Bool_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bool_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).enterBool_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).exitBool_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PostgreSQLParserVisitor ) return ((PostgreSQLParserVisitor<? extends T>)visitor).visitBool_expr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Bool_exprContext bool_expr() throws RecognitionException {
		return bool_expr(0);
	}

	private Bool_exprContext bool_expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Bool_exprContext _localctx = new Bool_exprContext(_ctx, _parentState);
		Bool_exprContext _prevctx = _localctx;
		int _startState = 22;
		enterRecursionRule(_localctx, 22, RULE_bool_expr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(155);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TRUE:
				{
				setState(151);
				match(TRUE);
				}
				break;
			case FALSE:
				{
				setState(152);
				match(FALSE);
				}
				break;
			case NOT:
				{
				setState(153);
				match(NOT);
				setState(154);
				bool_expr(3);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(165);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(163);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
					case 1:
						{
						_localctx = new Bool_exprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_bool_expr);
						setState(157);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(158);
						match(AND);
						setState(159);
						bool_expr(3);
						}
						break;
					case 2:
						{
						_localctx = new Bool_exprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_bool_expr);
						setState(160);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(161);
						match(OR);
						setState(162);
						bool_expr(2);
						}
						break;
					}
					} 
				}
				setState(167);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Type_literalContext extends ParserRuleContext {
		public TerminalNode ABSTIME() { return getToken(PostgreSQLParser.ABSTIME, 0); }
		public TerminalNode BOOL() { return getToken(PostgreSQLParser.BOOL, 0); }
		public TerminalNode BOX() { return getToken(PostgreSQLParser.BOX, 0); }
		public TerminalNode DATE() { return getToken(PostgreSQLParser.DATE, 0); }
		public TerminalNode FLOAT4() { return getToken(PostgreSQLParser.FLOAT4, 0); }
		public TerminalNode FLOAT8() { return getToken(PostgreSQLParser.FLOAT8, 0); }
		public TerminalNode INTERVAL() { return getToken(PostgreSQLParser.INTERVAL, 0); }
		public TerminalNode JSON() { return getToken(PostgreSQLParser.JSON, 0); }
		public TerminalNode JSONB() { return getToken(PostgreSQLParser.JSONB, 0); }
		public TerminalNode LINE() { return getToken(PostgreSQLParser.LINE, 0); }
		public TerminalNode POINT() { return getToken(PostgreSQLParser.POINT, 0); }
		public TerminalNode NAME() { return getToken(PostgreSQLParser.NAME, 0); }
		public TerminalNode TEXT() { return getToken(PostgreSQLParser.TEXT, 0); }
		public TerminalNode TIMESTAMP() { return getToken(PostgreSQLParser.TIMESTAMP, 0); }
		public List<TerminalNode> TIME() { return getTokens(PostgreSQLParser.TIME); }
		public TerminalNode TIME(int i) {
			return getToken(PostgreSQLParser.TIME, i);
		}
		public TerminalNode ZONE() { return getToken(PostgreSQLParser.ZONE, 0); }
		public TerminalNode WITH() { return getToken(PostgreSQLParser.WITH, 0); }
		public TerminalNode AT() { return getToken(PostgreSQLParser.AT, 0); }
		public TerminalNode WITHOUT() { return getToken(PostgreSQLParser.WITHOUT, 0); }
		public TerminalNode TIMESTAMP_TZ() { return getToken(PostgreSQLParser.TIMESTAMP_TZ, 0); }
		public TerminalNode TIME_TZ() { return getToken(PostgreSQLParser.TIME_TZ, 0); }
		public TerminalNode INT2() { return getToken(PostgreSQLParser.INT2, 0); }
		public TerminalNode INT4() { return getToken(PostgreSQLParser.INT4, 0); }
		public TerminalNode INT8() { return getToken(PostgreSQLParser.INT8, 0); }
		public TerminalNode RELTIME() { return getToken(PostgreSQLParser.RELTIME, 0); }
		public Type_literalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).enterType_literal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).exitType_literal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PostgreSQLParserVisitor ) return ((PostgreSQLParserVisitor<? extends T>)visitor).visitType_literal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Type_literalContext type_literal() throws RecognitionException {
		Type_literalContext _localctx = new Type_literalContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_type_literal);
		int _la;
		try {
			setState(212);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(168);
				match(ABSTIME);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(169);
				match(BOOL);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(170);
				match(BOX);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(171);
				match(DATE);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(172);
				match(FLOAT4);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(173);
				match(FLOAT8);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(174);
				match(INTERVAL);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(175);
				match(JSON);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(176);
				match(JSONB);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(177);
				match(LINE);
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(178);
				match(POINT);
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(179);
				match(NAME);
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(180);
				match(TEXT);
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(181);
				match(TIMESTAMP);
				setState(185);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==AT || _la==WITH) {
					{
					setState(182);
					_la = _input.LA(1);
					if ( !(_la==AT || _la==WITH) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(183);
					match(TIME);
					setState(184);
					match(ZONE);
					}
				}

				}
				break;
			case 15:
				enterOuterAlt(_localctx, 15);
				{
				setState(187);
				match(TIMESTAMP);
				setState(191);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==WITHOUT) {
					{
					setState(188);
					match(WITHOUT);
					setState(189);
					match(TIME);
					setState(190);
					match(ZONE);
					}
				}

				}
				break;
			case 16:
				enterOuterAlt(_localctx, 16);
				{
				setState(193);
				match(TIMESTAMP_TZ);
				}
				break;
			case 17:
				enterOuterAlt(_localctx, 17);
				{
				setState(194);
				match(TIME);
				setState(198);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==WITH) {
					{
					setState(195);
					match(WITH);
					setState(196);
					match(TIME);
					setState(197);
					match(ZONE);
					}
				}

				}
				break;
			case 18:
				enterOuterAlt(_localctx, 18);
				{
				setState(200);
				match(TIME);
				setState(204);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==WITHOUT) {
					{
					setState(201);
					match(WITHOUT);
					setState(202);
					match(TIME);
					setState(203);
					match(ZONE);
					}
				}

				}
				break;
			case 19:
				enterOuterAlt(_localctx, 19);
				{
				setState(206);
				match(TIME_TZ);
				}
				break;
			case 20:
				enterOuterAlt(_localctx, 20);
				{
				setState(207);
				match(INT2);
				}
				break;
			case 21:
				enterOuterAlt(_localctx, 21);
				{
				setState(208);
				match(INT4);
				}
				break;
			case 22:
				enterOuterAlt(_localctx, 22);
				{
				setState(209);
				match(INT8);
				}
				break;
			case 23:
				enterOuterAlt(_localctx, 23);
				{
				setState(210);
				match(INTERVAL);
				}
				break;
			case 24:
				enterOuterAlt(_localctx, 24);
				{
				setState(211);
				match(RELTIME);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Output_nameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(PostgreSQLParser.IDENTIFIER, 0); }
		public Output_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_output_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).enterOutput_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).exitOutput_name(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PostgreSQLParserVisitor ) return ((PostgreSQLParserVisitor<? extends T>)visitor).visitOutput_name(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Output_nameContext output_name() throws RecognitionException {
		Output_nameContext _localctx = new Output_nameContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_output_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(214);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Table_nameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(PostgreSQLParser.IDENTIFIER, 0); }
		public Table_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_table_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).enterTable_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).exitTable_name(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PostgreSQLParserVisitor ) return ((PostgreSQLParserVisitor<? extends T>)visitor).visitTable_name(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Table_nameContext table_name() throws RecognitionException {
		Table_nameContext _localctx = new Table_nameContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_table_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(216);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class From_itemContext extends ParserRuleContext {
		public Table_nameContext table_name() {
			return getRuleContext(Table_nameContext.class,0);
		}
		public Output_nameContext output_name() {
			return getRuleContext(Output_nameContext.class,0);
		}
		public TerminalNode AS() { return getToken(PostgreSQLParser.AS, 0); }
		public List<From_itemContext> from_item() {
			return getRuleContexts(From_itemContext.class);
		}
		public From_itemContext from_item(int i) {
			return getRuleContext(From_itemContext.class,i);
		}
		public Join_typeContext join_type() {
			return getRuleContext(Join_typeContext.class,0);
		}
		public Join_clauseContext join_clause() {
			return getRuleContext(Join_clauseContext.class,0);
		}
		public TerminalNode NATURAL() { return getToken(PostgreSQLParser.NATURAL, 0); }
		public From_itemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_from_item; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).enterFrom_item(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).exitFrom_item(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PostgreSQLParserVisitor ) return ((PostgreSQLParserVisitor<? extends T>)visitor).visitFrom_item(this);
			else return visitor.visitChildren(this);
		}
	}

	public final From_itemContext from_item() throws RecognitionException {
		return from_item(0);
	}

	private From_itemContext from_item(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		From_itemContext _localctx = new From_itemContext(_ctx, _parentState);
		From_itemContext _prevctx = _localctx;
		int _startState = 30;
		enterRecursionRule(_localctx, 30, RULE_from_item, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(219);
			table_name();
			setState(224);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				{
				setState(221);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==AS) {
					{
					setState(220);
					match(AS);
					}
				}

				setState(223);
				output_name();
				}
				break;
			}
			}
			_ctx.stop = _input.LT(-1);
			setState(236);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new From_itemContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_from_item);
					setState(226);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(228);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==NATURAL) {
						{
						setState(227);
						match(NATURAL);
						}
					}

					setState(230);
					join_type();
					setState(231);
					from_item(0);
					setState(232);
					join_clause();
					}
					} 
				}
				setState(238);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Join_typeContext extends ParserRuleContext {
		public TerminalNode JOIN() { return getToken(PostgreSQLParser.JOIN, 0); }
		public TerminalNode INNER() { return getToken(PostgreSQLParser.INNER, 0); }
		public TerminalNode LEFT() { return getToken(PostgreSQLParser.LEFT, 0); }
		public TerminalNode OUTER() { return getToken(PostgreSQLParser.OUTER, 0); }
		public TerminalNode RIGHT() { return getToken(PostgreSQLParser.RIGHT, 0); }
		public TerminalNode FULL() { return getToken(PostgreSQLParser.FULL, 0); }
		public TerminalNode CROSS() { return getToken(PostgreSQLParser.CROSS, 0); }
		public Join_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_join_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).enterJoin_type(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).exitJoin_type(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PostgreSQLParserVisitor ) return ((PostgreSQLParserVisitor<? extends T>)visitor).visitJoin_type(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Join_typeContext join_type() throws RecognitionException {
		Join_typeContext _localctx = new Join_typeContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_join_type);
		int _la;
		try {
			setState(260);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INNER:
			case JOIN:
				enterOuterAlt(_localctx, 1);
				{
				setState(240);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==INNER) {
					{
					setState(239);
					match(INNER);
					}
				}

				setState(242);
				match(JOIN);
				}
				break;
			case LEFT:
				enterOuterAlt(_localctx, 2);
				{
				setState(243);
				match(LEFT);
				setState(245);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==OUTER) {
					{
					setState(244);
					match(OUTER);
					}
				}

				setState(247);
				match(JOIN);
				}
				break;
			case RIGHT:
				enterOuterAlt(_localctx, 3);
				{
				setState(248);
				match(RIGHT);
				setState(250);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==OUTER) {
					{
					setState(249);
					match(OUTER);
					}
				}

				setState(252);
				match(JOIN);
				}
				break;
			case FULL:
				enterOuterAlt(_localctx, 4);
				{
				setState(253);
				match(FULL);
				setState(255);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==OUTER) {
					{
					setState(254);
					match(OUTER);
					}
				}

				setState(257);
				match(JOIN);
				}
				break;
			case CROSS:
				enterOuterAlt(_localctx, 5);
				{
				setState(258);
				match(CROSS);
				setState(259);
				match(JOIN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Join_clauseContext extends ParserRuleContext {
		public TerminalNode ON() { return getToken(PostgreSQLParser.ON, 0); }
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class,0);
		}
		public TerminalNode USING() { return getToken(PostgreSQLParser.USING, 0); }
		public TerminalNode OPEN_PAREN() { return getToken(PostgreSQLParser.OPEN_PAREN, 0); }
		public List<Column_nameContext> column_name() {
			return getRuleContexts(Column_nameContext.class);
		}
		public Column_nameContext column_name(int i) {
			return getRuleContext(Column_nameContext.class,i);
		}
		public TerminalNode CLOSE_PAREN() { return getToken(PostgreSQLParser.CLOSE_PAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(PostgreSQLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(PostgreSQLParser.COMMA, i);
		}
		public Join_clauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_join_clause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).enterJoin_clause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).exitJoin_clause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PostgreSQLParserVisitor ) return ((PostgreSQLParserVisitor<? extends T>)visitor).visitJoin_clause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Join_clauseContext join_clause() throws RecognitionException {
		Join_clauseContext _localctx = new Join_clauseContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_join_clause);
		int _la;
		try {
			setState(276);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ON:
				enterOuterAlt(_localctx, 1);
				{
				setState(262);
				match(ON);
				setState(263);
				predicate(0);
				}
				break;
			case USING:
				enterOuterAlt(_localctx, 2);
				{
				setState(264);
				match(USING);
				setState(265);
				match(OPEN_PAREN);
				setState(266);
				column_name();
				setState(271);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(267);
					match(COMMA);
					setState(268);
					column_name();
					}
					}
					setState(273);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(274);
				match(CLOSE_PAREN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdentifierContext extends ParserRuleContext {
		public List<TerminalNode> IDENTIFIER() { return getTokens(PostgreSQLParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(PostgreSQLParser.IDENTIFIER, i);
		}
		public TerminalNode DOT() { return getToken(PostgreSQLParser.DOT, 0); }
		public IdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).enterIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).exitIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PostgreSQLParserVisitor ) return ((PostgreSQLParserVisitor<? extends T>)visitor).visitIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentifierContext identifier() throws RecognitionException {
		IdentifierContext _localctx = new IdentifierContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_identifier);
		try {
			setState(282);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(278);
				match(IDENTIFIER);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(279);
				match(IDENTIFIER);
				setState(280);
				match(DOT);
				setState(281);
				match(IDENTIFIER);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Column_nameContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public Column_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_column_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).enterColumn_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PostgreSQLParserListener ) ((PostgreSQLParserListener)listener).exitColumn_name(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PostgreSQLParserVisitor ) return ((PostgreSQLParserVisitor<? extends T>)visitor).visitColumn_name(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Column_nameContext column_name() throws RecognitionException {
		Column_nameContext _localctx = new Column_nameContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_column_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(284);
			identifier();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 6:
			return predicate_sempred((PredicateContext)_localctx, predIndex);
		case 11:
			return bool_expr_sempred((Bool_exprContext)_localctx, predIndex);
		case 15:
			return from_item_sempred((From_itemContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean predicate_sempred(PredicateContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean bool_expr_sempred(Bool_exprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 2);
		case 2:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean from_item_sempred(From_itemContext _localctx, int predIndex) {
		switch (predIndex) {
		case 3:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\u02c5\u0121\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\3\2\3\2\3\2\3\2\5\2/\n\2\3\2\3\2\3\3\3"+
		"\3\3\3\7\3\66\n\3\f\3\16\39\13\3\3\4\3\4\3\4\5\4>\n\4\3\4\5\4A\n\4\5\4"+
		"C\n\4\3\5\3\5\3\5\3\5\7\5I\n\5\f\5\16\5L\13\5\3\6\3\6\3\6\3\7\3\7\5\7"+
		"S\n\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b_\n\b\3\b\3\b\3\b\7\b"+
		"d\n\b\f\b\16\bg\13\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\5\tx\n\t\3\t\3\t\5\t|\n\t\3\t\5\t\177\n\t\5\t\u0081\n\t\3\n"+
		"\3\n\5\n\u0085\n\n\3\13\3\13\3\13\3\13\3\13\5\13\u008c\n\13\3\f\3\f\3"+
		"\f\3\f\7\f\u0092\n\f\f\f\16\f\u0095\13\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\5"+
		"\r\u009e\n\r\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u00a6\n\r\f\r\16\r\u00a9\13\r"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\5\16\u00bc\n\16\3\16\3\16\3\16\3\16\5\16\u00c2\n\16\3"+
		"\16\3\16\3\16\3\16\3\16\5\16\u00c9\n\16\3\16\3\16\3\16\3\16\5\16\u00cf"+
		"\n\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u00d7\n\16\3\17\3\17\3\20\3\20"+
		"\3\21\3\21\3\21\5\21\u00e0\n\21\3\21\5\21\u00e3\n\21\3\21\3\21\5\21\u00e7"+
		"\n\21\3\21\3\21\3\21\3\21\7\21\u00ed\n\21\f\21\16\21\u00f0\13\21\3\22"+
		"\5\22\u00f3\n\22\3\22\3\22\3\22\5\22\u00f8\n\22\3\22\3\22\3\22\5\22\u00fd"+
		"\n\22\3\22\3\22\3\22\5\22\u0102\n\22\3\22\3\22\3\22\5\22\u0107\n\22\3"+
		"\23\3\23\3\23\3\23\3\23\3\23\3\23\7\23\u0110\n\23\f\23\16\23\u0113\13"+
		"\23\3\23\3\23\5\23\u0117\n\23\3\24\3\24\3\24\3\24\5\24\u011d\n\24\3\25"+
		"\3\25\3\25\2\5\16\30 \26\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&("+
		"\2\b\5\2\u00c3\u00c3\u010f\u010f\u0246\u0246\4\2\22\22\u00b1\u00b1\4\2"+
		"\u0299\u0299\u02ac\u02ac\4\2\u029d\u029e\u02a8\u02a9\4\2\u0115\u0115\u015f"+
		"\u015f\4\2##\u0269\u0269\2\u0150\2*\3\2\2\2\4\62\3\2\2\2\6B\3\2\2\2\b"+
		"D\3\2\2\2\nM\3\2\2\2\fP\3\2\2\2\16^\3\2\2\2\20\u0080\3\2\2\2\22\u0084"+
		"\3\2\2\2\24\u008b\3\2\2\2\26\u008d\3\2\2\2\30\u009d\3\2\2\2\32\u00d6\3"+
		"\2\2\2\34\u00d8\3\2\2\2\36\u00da\3\2\2\2 \u00dc\3\2\2\2\"\u0106\3\2\2"+
		"\2$\u0116\3\2\2\2&\u011c\3\2\2\2(\u011e\3\2\2\2*+\7\u01e4\2\2+,\5\4\3"+
		"\2,.\5\b\5\2-/\5\n\6\2.-\3\2\2\2./\3\2\2\2/\60\3\2\2\2\60\61\7\2\2\3\61"+
		"\3\3\2\2\2\62\67\5\6\4\2\63\64\7\u027e\2\2\64\66\5\6\4\2\65\63\3\2\2\2"+
		"\669\3\2\2\2\67\65\3\2\2\2\678\3\2\2\28\5\3\2\2\29\67\3\2\2\2:C\7\u0283"+
		"\2\2;@\5&\24\2<>\7\35\2\2=<\3\2\2\2=>\3\2\2\2>?\3\2\2\2?A\5\34\17\2@="+
		"\3\2\2\2@A\3\2\2\2AC\3\2\2\2B:\3\2\2\2B;\3\2\2\2C\7\3\2\2\2DE\7\u00e0"+
		"\2\2EJ\5 \21\2FG\7\u027e\2\2GI\5 \21\2HF\3\2\2\2IL\3\2\2\2JH\3\2\2\2J"+
		"K\3\2\2\2K\t\3\2\2\2LJ\3\2\2\2MN\7\u0266\2\2NO\5\16\b\2O\13\3\2\2\2PR"+
		"\t\2\2\2QS\t\3\2\2RQ\3\2\2\2RS\3\2\2\2ST\3\2\2\2TU\5\2\2\2U\r\3\2\2\2"+
		"VW\b\b\1\2W_\5\20\t\2XY\7\u0284\2\2YZ\5\16\b\2Z[\7\u0285\2\2[_\3\2\2\2"+
		"\\]\7\u015c\2\2]_\5\16\b\3^V\3\2\2\2^X\3\2\2\2^\\\3\2\2\2_e\3\2\2\2`a"+
		"\f\4\2\2ab\7\31\2\2bd\5\16\b\5c`\3\2\2\2dg\3\2\2\2ec\3\2\2\2ef\3\2\2\2"+
		"f\17\3\2\2\2ge\3\2\2\2hi\5\22\n\2ij\7\u029b\2\2jk\5\22\n\2k\u0081\3\2"+
		"\2\2lm\5\22\n\2mn\t\4\2\2no\5\22\n\2o\u0081\3\2\2\2pq\5\22\n\2qr\t\5\2"+
		"\2rs\5\22\n\2s\u0081\3\2\2\2t~\5&\24\2uw\7\u0114\2\2vx\7\u015c\2\2wv\3"+
		"\2\2\2wx\3\2\2\2x{\3\2\2\2y|\5\30\r\2z|\7\u0161\2\2{y\3\2\2\2{z\3\2\2"+
		"\2|\177\3\2\2\2}\177\t\6\2\2~u\3\2\2\2~}\3\2\2\2\177\u0081\3\2\2\2\u0080"+
		"h\3\2\2\2\u0080l\3\2\2\2\u0080p\3\2\2\2\u0080t\3\2\2\2\u0081\21\3\2\2"+
		"\2\u0082\u0085\5&\24\2\u0083\u0085\5\24\13\2\u0084\u0082\3\2\2\2\u0084"+
		"\u0083\3\2\2\2\u0085\23\3\2\2\2\u0086\u008c\7\u0161\2\2\u0087\u008c\7"+
		"\u028b\2\2\u0088\u008c\5\30\r\2\u0089\u008c\7\u028e\2\2\u008a\u008c\7"+
		"\u028f\2\2\u008b\u0086\3\2\2\2\u008b\u0087\3\2\2\2\u008b\u0088\3\2\2\2"+
		"\u008b\u0089\3\2\2\2\u008b\u008a\3\2\2\2\u008c\25\3\2\2\2\u008d\u008e"+
		"\7\u0284\2\2\u008e\u0093\5\24\13\2\u008f\u0090\7\u027e\2\2\u0090\u0092"+
		"\5\24\13\2\u0091\u008f\3\2\2\2\u0092\u0095\3\2\2\2\u0093\u0091\3\2\2\2"+
		"\u0093\u0094\3\2\2\2\u0094\u0096\3\2\2\2\u0095\u0093\3\2\2\2\u0096\u0097"+
		"\7\u0285\2\2\u0097\27\3\2\2\2\u0098\u0099\b\r\1\2\u0099\u009e\7\u023d"+
		"\2\2\u009a\u009e\7\u00d0\2\2\u009b\u009c\7\u015c\2\2\u009c\u009e\5\30"+
		"\r\5\u009d\u0098\3\2\2\2\u009d\u009a\3\2\2\2\u009d\u009b\3\2\2\2\u009e"+
		"\u00a7\3\2\2\2\u009f\u00a0\f\4\2\2\u00a0\u00a1\7\31\2\2\u00a1\u00a6\5"+
		"\30\r\5\u00a2\u00a3\f\3\2\2\u00a3\u00a4\7\u0176\2\2\u00a4\u00a6\5\30\r"+
		"\4\u00a5\u009f\3\2\2\2\u00a5\u00a2\3\2\2\2\u00a6\u00a9\3\2\2\2\u00a7\u00a5"+
		"\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8\31\3\2\2\2\u00a9\u00a7\3\2\2\2\u00aa"+
		"\u00d7\7\u0270\2\2\u00ab\u00d7\7\u0271\2\2\u00ac\u00d7\7\u0272\2\2\u00ad"+
		"\u00d7\7\u0091\2\2\u00ae\u00d7\7\u0273\2\2\u00af\u00d7\7\u0274\2\2\u00b0"+
		"\u00d7\7\u0111\2\2\u00b1\u00d7\7\u0278\2\2\u00b2\u00d7\7\u0279\2\2\u00b3"+
		"\u00d7\7\u027a\2\2\u00b4\u00d7\7\u027b\2\2\u00b5\u00d7\7\u014d\2\2\u00b6"+
		"\u00d7\7\u027d\2\2\u00b7\u00bb\7\u0227\2\2\u00b8\u00b9\t\7\2\2\u00b9\u00ba"+
		"\7\u0225\2\2\u00ba\u00bc\7\u026f\2\2\u00bb\u00b8\3\2\2\2\u00bb\u00bc\3"+
		"\2\2\2\u00bc\u00d7\3\2\2\2\u00bd\u00c1\7\u0227\2\2\u00be\u00bf\7\u026b"+
		"\2\2\u00bf\u00c0\7\u0225\2\2\u00c0\u00c2\7\u026f\2\2\u00c1\u00be\3\2\2"+
		"\2\u00c1\u00c2\3\2\2\2\u00c2\u00d7\3\2\2\2\u00c3\u00d7\7\u0228\2\2\u00c4"+
		"\u00c8\7\u0225\2\2\u00c5\u00c6\7\u0269\2\2\u00c6\u00c7\7\u0225\2\2\u00c7"+
		"\u00c9\7\u026f\2\2\u00c8\u00c5\3\2\2\2\u00c8\u00c9\3\2\2\2\u00c9\u00d7"+
		"\3\2\2\2\u00ca\u00ce\7\u0225\2\2\u00cb\u00cc\7\u026b\2\2\u00cc\u00cd\7"+
		"\u0225\2\2\u00cd\u00cf\7\u026f\2\2\u00ce\u00cb\3\2\2\2\u00ce\u00cf\3\2"+
		"\2\2\u00cf\u00d7\3\2\2\2\u00d0\u00d7\7\u0226\2\2\u00d1\u00d7\7\u0275\2"+
		"\2\u00d2\u00d7\7\u0276\2\2\u00d3\u00d7\7\u0277\2\2\u00d4\u00d7\7\u0111"+
		"\2\2\u00d5\u00d7\7\u027c\2\2\u00d6\u00aa\3\2\2\2\u00d6\u00ab\3\2\2\2\u00d6"+
		"\u00ac\3\2\2\2\u00d6\u00ad\3\2\2\2\u00d6\u00ae\3\2\2\2\u00d6\u00af\3\2"+
		"\2\2\u00d6\u00b0\3\2\2\2\u00d6\u00b1\3\2\2\2\u00d6\u00b2\3\2\2\2\u00d6"+
		"\u00b3\3\2\2\2\u00d6\u00b4\3\2\2\2\u00d6\u00b5\3\2\2\2\u00d6\u00b6\3\2"+
		"\2\2\u00d6\u00b7\3\2\2\2\u00d6\u00bd\3\2\2\2\u00d6\u00c3\3\2\2\2\u00d6"+
		"\u00c4\3\2\2\2\u00d6\u00ca\3\2\2\2\u00d6\u00d0\3\2\2\2\u00d6\u00d1\3\2"+
		"\2\2\u00d6\u00d2\3\2\2\2\u00d6\u00d3\3\2\2\2\u00d6\u00d4\3\2\2\2\u00d6"+
		"\u00d5\3\2\2\2\u00d7\33\3\2\2\2\u00d8\u00d9\7\u0290\2\2\u00d9\35\3\2\2"+
		"\2\u00da\u00db\7\u0290\2\2\u00db\37\3\2\2\2\u00dc\u00dd\b\21\1\2\u00dd"+
		"\u00e2\5\36\20\2\u00de\u00e0\7\35\2\2\u00df\u00de\3\2\2\2\u00df\u00e0"+
		"\3\2\2\2\u00e0\u00e1\3\2\2\2\u00e1\u00e3\5\34\17\2\u00e2\u00df\3\2\2\2"+
		"\u00e2\u00e3\3\2\2\2\u00e3\u00ee\3\2\2\2\u00e4\u00e6\f\3\2\2\u00e5\u00e7"+
		"\7\u0150\2\2\u00e6\u00e5\3\2\2\2\u00e6\u00e7\3\2\2\2\u00e7\u00e8\3\2\2"+
		"\2\u00e8\u00e9\5\"\22\2\u00e9\u00ea\5 \21\2\u00ea\u00eb\5$\23\2\u00eb"+
		"\u00ed\3\2\2\2\u00ec\u00e4\3\2\2\2\u00ed\u00f0\3\2\2\2\u00ee\u00ec\3\2"+
		"\2\2\u00ee\u00ef\3\2\2\2\u00ef!\3\2\2\2\u00f0\u00ee\3\2\2\2\u00f1\u00f3"+
		"\7\u0105\2\2\u00f2\u00f1\3\2\2\2\u00f2\u00f3\3\2\2\2\u00f3\u00f4\3\2\2"+
		"\2\u00f4\u0107\7\u0118\2\2\u00f5\u00f7\7\u0123\2\2\u00f6\u00f8\7\u017c"+
		"\2\2\u00f7\u00f6\3\2\2\2\u00f7\u00f8\3\2\2\2\u00f8\u00f9\3\2\2\2\u00f9"+
		"\u0107\7\u0118\2\2\u00fa\u00fc\7\u01ca\2\2\u00fb\u00fd\7\u017c\2\2\u00fc"+
		"\u00fb\3\2\2\2\u00fc\u00fd\3\2\2\2\u00fd\u00fe\3\2\2\2\u00fe\u0107\7\u0118"+
		"\2\2\u00ff\u0101\7\u00e1\2\2\u0100\u0102\7\u017c\2\2\u0101\u0100\3\2\2"+
		"\2\u0101\u0102\3\2\2\2\u0102\u0103\3\2\2\2\u0103\u0107\7\u0118\2\2\u0104"+
		"\u0105\7\177\2\2\u0105\u0107\7\u0118\2\2\u0106\u00f2\3\2\2\2\u0106\u00f5"+
		"\3\2\2\2\u0106\u00fa\3\2\2\2\u0106\u00ff\3\2\2\2\u0106\u0104\3\2\2\2\u0107"+
		"#\3\2\2\2\u0108\u0109\7\u016f\2\2\u0109\u0117\5\16\b\2\u010a\u010b\7\u0255"+
		"\2\2\u010b\u010c\7\u0284\2\2\u010c\u0111\5(\25\2\u010d\u010e\7\u027e\2"+
		"\2\u010e\u0110\5(\25\2\u010f\u010d\3\2\2\2\u0110\u0113\3\2\2\2\u0111\u010f"+
		"\3\2\2\2\u0111\u0112\3\2\2\2\u0112\u0114\3\2\2\2\u0113\u0111\3\2\2\2\u0114"+
		"\u0115\7\u0285\2\2\u0115\u0117\3\2\2\2\u0116\u0108\3\2\2\2\u0116\u010a"+
		"\3\2\2\2\u0117%\3\2\2\2\u0118\u011d\7\u0290\2\2\u0119\u011a\7\u0290\2"+
		"\2\u011a\u011b\7\u028d\2\2\u011b\u011d\7\u0290\2\2\u011c\u0118\3\2\2\2"+
		"\u011c\u0119\3\2\2\2\u011d\'\3\2\2\2\u011e\u011f\5&\24\2\u011f)\3\2\2"+
		"\2&.\67=@BJR^ew{~\u0080\u0084\u008b\u0093\u009d\u00a5\u00a7\u00bb\u00c1"+
		"\u00c8\u00ce\u00d6\u00df\u00e2\u00e6\u00ee\u00f2\u00f7\u00fc\u0101\u0106"+
		"\u0111\u0116\u011c";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}