package com.github.bytesfly.csvloader;

import cn.hutool.core.collection.CollUtil;
import com.github.bytesfly.csvloader.antlr.CSVBaseListener;
import com.github.bytesfly.csvloader.antlr.CSVParser;

import java.util.*;

public class CsvLoaderListener extends CSVBaseListener {

    /**
     * 存储表头字段
     */
    private List<String> header;

    /**
     * 这个列表中的每个Map对应csv文件一行数据 ;
     * Map是从字段名到字段值的映射
     */
    private final List<Map<String, String>> rows = new ArrayList<>();

    /**
     * 存储正在读取的当前行的字段值
     */
    private List<String> row;

    @Override
    public void exitHeader(CSVParser.HeaderContext ctx) {
        header = row;
    }

    @Override
    public void enterRow(CSVParser.RowContext ctx) {
        row = new ArrayList<>();
    }

    @Override
    public void exitRow(CSVParser.RowContext ctx) {
        if (header != null) {
            rows.add(CollUtil.zip(header, row));
        }
    }

    @Override
    public void exitText(CSVParser.TextContext ctx) {
        row.add(ctx.TEXT().getText());
    }

    @Override
    public void exitString(CSVParser.StringContext ctx) {
        row.add(ctx.STRING().getText());
    }

    @Override
    public void exitEmpty(CSVParser.EmptyContext ctx) {
        row.add("");
    }

    public List<Map<String, String>> getRows() {
        return rows;
    }
}
