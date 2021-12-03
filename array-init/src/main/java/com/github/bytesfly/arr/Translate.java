package com.github.bytesfly.arr;

import com.github.bytesfly.arr.antlr.ArrayInitLexer;
import com.github.bytesfly.arr.antlr.ArrayInitParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.Scanner;

public class Translate {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {
            String s = sc.nextLine();
            // create a CharStream that reads from standard input
            CharStream input = CharStreams.fromString(s);
            // create a lexer that feeds off of input CharStream
            ArrayInitLexer lexer = new ArrayInitLexer(input);
            // create a buffer of tokens pulled from the lexer
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            // create a parser that feeds off the tokens buffer
            ArrayInitParser parser = new ArrayInitParser(tokens);
            ParseTree tree = parser.init(); // begin parsing at init rule

            // 新建一个通用的、能够触发回调函数的语法分析树遍历器
            ParseTreeWalker walker = new ParseTreeWalker();

            // 遍历语法分析过程中生成的语法分析树，触发回调

            IntToStringListener listener = new IntToStringListener();
            walker.walk(listener, tree);

            System.out.println(listener.getResult());

        }
    }
}
