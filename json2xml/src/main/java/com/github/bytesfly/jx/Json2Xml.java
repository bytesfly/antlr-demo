package com.github.bytesfly.jx;

import cn.hutool.core.io.FileUtil;
import com.github.bytesfly.jx.antlr.JSONLexer;
import com.github.bytesfly.jx.antlr.JSONParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class Json2Xml {

    public static void main(String[] args) {
        // 读取resources目录下example.json文件
        String s = FileUtil.readUtf8String("example.json");

        // 从字符串读取输入数据
        CharStream input = CharStreams.fromString(s);

        // 新建一个词法分析器
        JSONLexer lexer = new JSONLexer(input);

        // 新建一个词法符号的缓冲区，用于存储词法分析器将生成的词法符号
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // 新建一个语法分析器，处理词法符号缓冲区中的内容
        JSONParser parser = new JSONParser(tokens);

        // 针对json规则，开始语法分析
        ParseTree tree = parser.json();

        // 新建一个通用的、能够触发回调函数的语法分析树遍历器
        ParseTreeWalker walker = new ParseTreeWalker();

        // 创建我们自定义的监听器
        Json2XmlListener listener = new Json2XmlListener();

        // 遍历语法分析过程中生成的语法分析树，触发回调
        walker.walk(listener, tree);

        // 打印JSON转XML的结果
        System.out.println(listener.getResult());
    }
}
