package com.github.bytesfly.arr;

import com.github.bytesfly.arr.antlr.ArrayInitBaseListener;
import com.github.bytesfly.arr.antlr.ArrayInitParser;
import org.antlr.v4.runtime.tree.TerminalNode;

public class IntToStringListener extends ArrayInitBaseListener {

    private final StringBuilder builder = new StringBuilder();

    /**
     * Translate { to [
     */
    @Override
    public void enterInit(ArrayInitParser.InitContext ctx) {
        builder.append('[');
    }

    /**
     * Translate } to ]
     */
    @Override
    public void exitInit(ArrayInitParser.InitContext ctx) {
        // 去除value节点后的逗号
        builder.setLength(builder.length() - 1);
        builder.append(']');

        if (ctx.parent != null) {
            // 嵌套结构需要追加逗号，与enterValue的处理保持一致
            builder.append(',');
        }
    }

    /**
     * Translate integers to 4-digit hexadecimal strings prefixed with \\u
     */
    @Override
    public void enterValue(ArrayInitParser.ValueContext ctx) {
        TerminalNode node = ctx.INT();
        if (node != null) {
            builder.append("\"").append(node.getText()).append("\",");
        }
    }

    public String getResult() {
        return builder.toString();
    }
}
