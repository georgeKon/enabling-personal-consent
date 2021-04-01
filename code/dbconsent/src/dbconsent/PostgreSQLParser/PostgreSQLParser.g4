// The MIT License

// Copyright 2018 Tal Shprecher
// Copyright 2018 Jet Holt

// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:

// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.

// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.

parser grammar PostgreSQLParser;

options { tokenVocab=PostgreSQLLexer; }

// Top Level Description
select_stmt
    : SELECT selector_clause
      from_clause
      where_clause?
      EOF
    ;

selector_clause
    : column_selection (COMMA column_selection)*
    ;

column_selection
    : (STAR | (identifier (AS? output_name)?))
    ;

from_clause
    : FROM from_item (COMMA from_item)*
    ;

where_clause
    : WHERE atom
    ;

combine_clause
    : ( UNION | INTERSECT | EXCEPT ) ( ALL | DISTINCT)? select_stmt
    ;

atom
    : condition                         #PredicateConditional
    | OPEN_PAREN atom CLOSE_PAREN  #PredicateParen
    | atom AND atom           #PredicateAND
// This causes the query to become the union of conjunctive queries
//  | atom OR atom            #PredicateOR
    | NOT atom                     #PredicateNOT
    ;

condition
    : predicateArgument EQUAL predicateArgument                                     #ConditionEqual
    | predicateArgument (LT_GT | BANG_EQUAL) predicateArgument                      #ConditionNotEqual
    | predicateArgument (LT | GT | LTE | GTE) predicateArgument                     #ConditionComparison
    | identifier ((IS NOT? (bool_expr | NULL)) | (ISNULL | NOTNULL))                #ConditionIsBooleanOrNull
// This causes the query to become the union of conjunctive queries
//  | predicateArgument NOT? IN value_list                                          #ConditionInValueList
    ;

predicateArgument
    : identifier
    | value
    ;

value
    : NULL
    | INTEGER_LITERAL
    | bool_expr
    | SINGLEQ_STRING_LITERAL
    | DOUBLEQ_STRING_LITERAL
    ;

value_list
    : OPEN_PAREN value (COMMA value)* CLOSE_PAREN
    ;

bool_expr
    : TRUE
    | FALSE
    | NOT bool_expr
    | bool_expr AND bool_expr
    | bool_expr OR bool_expr
    ;


// TODO: is type_literal necessary or can we just have this be an identifier and match (identifier STRING_LITERAL)?
// TODO: rename prefix notation type casts
type_literal
    : ABSTIME
    | BOOL
    | BOX
    | DATE
    | FLOAT4
    | FLOAT8
    | INTERVAL
    | JSON
    | JSONB
    | LINE
    | POINT
    | NAME
    | TEXT
    | TIMESTAMP ((WITH | AT) TIME ZONE)?
    | TIMESTAMP (WITHOUT TIME ZONE)?
    | TIMESTAMP_TZ
    | TIME (WITH TIME ZONE)?
    | TIME (WITHOUT TIME ZONE)?
    | TIME_TZ
    | INT2
    | INT4
    | INT8
    | INTERVAL
    | RELTIME
    ;

output_name
    : IDENTIFIER
    ;

table_name
    : IDENTIFIER
    ;

from_item
    : table_name (AS? output_name)?
    | from_item NATURAL? join_type from_item join_clause // TODO: fix 'left' being treated as an alias
    ;

join_type
    : INNER? JOIN
    | LEFT OUTER? JOIN
    | RIGHT OUTER? JOIN
    | FULL OUTER? JOIN
    | CROSS JOIN
    ;

join_clause
    : ON atom
    | USING OPEN_PAREN column_name (COMMA column_name)* CLOSE_PAREN
    ;

identifier
    : IDENTIFIER
    | IDENTIFIER DOT IDENTIFIER
    ;

column_name         : identifier;