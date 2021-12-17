grammar CSV;

@header {package com.github.bytesfly.csvloader.antlr;}

file : header row+ ;

header : row ;

row : field (',' field)* NEWLINE ;

field
    :   TEXT    # text
    |   STRING  # string
    |           # empty
    ;

TEXT : ~[,\n\r"]+ ;
STRING : '"' ('""'|~'"')* '"' ; // 两个双引号是对双引号的转义
NEWLINE : '\r'? '\n' ;