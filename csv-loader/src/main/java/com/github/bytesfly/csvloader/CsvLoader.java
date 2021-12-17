package com.github.bytesfly.csvloader;

import cn.hutool.core.io.FileUtil;
import com.github.bytesfly.csvloader.antlr.CSVLexer;
import com.github.bytesfly.csvloader.antlr.CSVParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class CsvLoader {

    public static void main(String[] args) {
        // 读取resources目录下example.csv文件
        String s = FileUtil.readUtf8String("example.csv");

        // 从字符串读取输入数据
        CharStream input = CharStreams.fromString(s);

        // 新建一个词法分析器
        CSVLexer lexer = new CSVLexer(input);

        // 新建一个词法符号的缓冲区，用于存储词法分析器将生成的词法符号
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // 新建一个语法分析器，处理词法符号缓冲区中的内容
        CSVParser parser = new CSVParser(tokens);

        // 针对file规则，开始语法分析
        ParseTree tree = parser.file();

        // 新建一个通用的、能够触发回调函数的语法分析树遍历器
        ParseTreeWalker walker = new ParseTreeWalker();

        // 创建我们自定义的监听器
        CsvLoaderListener listener = new CsvLoaderListener();

        // 遍历语法分析过程中生成的语法分析树，触发回调
        walker.walk(listener, tree);

        // 打印从csv文件加载的数据
        System.out.println(listener.getRows());
    }
}
