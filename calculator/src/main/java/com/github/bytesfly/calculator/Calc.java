package com.github.bytesfly.calculator;

import cn.hutool.core.io.FileUtil;
import com.github.bytesfly.calculator.antlr.ExprLexer;
import com.github.bytesfly.calculator.antlr.ExprParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class Calc {
    public static void main(String[] args) throws Exception {
        // 读取resources目录下example.expr表达式文件
        String s = FileUtil.readUtf8String("example.expr");

        // 从字符串读取输入数据
        CharStream input = CharStreams.fromString(s);

        // 新建一个词法分析器
        ExprLexer lexer = new ExprLexer(input);

        // 新建一个词法符号的缓冲区，用于存储词法分析器将生成的词法符号
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // 新建一个语法分析器，处理词法符号缓冲区中的内容
        ExprParser parser = new ExprParser(tokens);

        // 针对prog规则，开始语法分析
        ParseTree tree = parser.prog();

        EvalVisitor eval = new EvalVisitor();
        eval.visit(tree);
    }
}
