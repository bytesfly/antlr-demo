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
        // 从键盘输入
        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {

            String s = sc.nextLine();

            // 从字符串读取输入数据
            CharStream input = CharStreams.fromString(s);

            // 新建一个词法分析器
            ArrayInitLexer lexer = new ArrayInitLexer(input);

            // 新建一个词法符号的缓冲区，用于存储词法分析器将生成的词法符号
            CommonTokenStream tokens = new CommonTokenStream(lexer);

            // 新建一个语法分析器，处理词法符号缓冲区中的内容
            ArrayInitParser parser = new ArrayInitParser(tokens);

            // 针对init规则，开始语法分析
            ParseTree tree = parser.init();

            // 新建一个通用的、能够触发回调函数的语法分析树遍历器
            ParseTreeWalker walker = new ParseTreeWalker();

            // 创建我们自定义的监听器
            IntToStringListener listener = new IntToStringListener();

            // 遍历语法分析过程中生成的语法分析树，触发回调
            walker.walk(listener, tree);

            // 打印转换结果
            System.out.println(listener.getResult());
        }
    }
}
