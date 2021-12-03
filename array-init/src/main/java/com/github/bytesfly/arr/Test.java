package com.github.bytesfly.arr;

import com.github.bytesfly.arr.antlr.ArrayInitLexer;
import com.github.bytesfly.arr.antlr.ArrayInitParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.IOException;

/**
 * 识别语法测试
 */
public class Test {

    public static void main(String[] args) throws IOException {
        // 从标准输入读取数据(注意：文件结束符在类UNIX系统上的输入方法是Ctrl+D，Windows上的方法是Ctrl+Z)
        CharStream input = CharStreams.fromStream(System.in);

        // 新建一个词法分析器
        ArrayInitLexer lexer = new ArrayInitLexer(input);

        // 新建一个词法符号的缓冲区，用于存储词法分析器将生成的词法符号
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // 新建一个语法分析器，处理词法符号缓冲区中的内容
        ArrayInitParser parser = new ArrayInitParser(tokens);

        // 针对init规则，开始语法分析
        ParseTree tree = parser.init();

        // 打印生成的语法树
        System.out.println(tree.toStringTree(parser));
    }
}
