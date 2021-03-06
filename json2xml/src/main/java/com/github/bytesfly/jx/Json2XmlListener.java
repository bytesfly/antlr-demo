package com.github.bytesfly.jx;

import cn.hutool.core.text.CharPool;
import com.github.bytesfly.jx.antlr.JSONBaseListener;
import com.github.bytesfly.jx.antlr.JSONParser;

/*
{
  "description" : "An imaginary server config file",
  "logs" : {"level":"verbose", "dir":"/var/log"},
  "host" : "antlr.org",
  "bool": true,
  "null": null,
  "pi": 3.14,
  "admin": ["parrt", "tombu"],
  "aliases": []
}


<description>An imaginary server config file</description>
<logs>
    <level>verbose</level>
    <dir>/var/log</dir>
</logs>
<host>antlr.org</host>
<bool>true</bool>
<null>null</null>
<pi>3.14</pi>
<admin>
    <element>parrt</element>
    <element>tombu</element>
</admin>
<aliases></aliases>

*/


public class Json2XmlListener extends JSONBaseListener {

    private final StringBuilder builder = new StringBuilder();

    @Override
    public void enterPair(JSONParser.PairContext ctx) {
        // <key>
        builder.append("<")
                .append(stripQuotes(ctx.STRING().getText()))
                .append(">");
    }

    @Override
    public void exitPair(JSONParser.PairContext ctx) {
        // </key>
        builder.append("</")
                .append(stripQuotes(ctx.STRING().getText()))
                .append(">\n");
    }

    @Override
    public void enterString(JSONParser.StringContext ctx) {
        ifEnterArray(ctx);
        builder.append(stripQuotes(ctx.STRING().getText()));
    }

    @Override
    public void exitString(JSONParser.StringContext ctx) {
        ifExitArray(ctx);
    }

    @Override
    public void enterAtom(JSONParser.AtomContext ctx) {
        ifEnterArray(ctx);
        builder.append(ctx.getText());
    }

    @Override
    public void exitAtom(JSONParser.AtomContext ctx) {
        ifExitArray(ctx);
    }

    @Override
    public void enterObjectValue(JSONParser.ObjectValueContext ctx) {
        ifEnterArray(ctx);
        builder.append("\n");
    }

    @Override
    public void exitObjectValue(JSONParser.ObjectValueContext ctx) {
        ifExitArray(ctx);
    }

    @Override
    public void enterArrayValue(JSONParser.ArrayValueContext ctx) {
        ifEnterArray(ctx);
        builder.append("\n");
    }

    @Override
    public void exitArrayValue(JSONParser.ArrayValueContext ctx) {
        ifExitArray(ctx);
    }

    /**
     * ????????????????????????????????????
     */
    private static String stripQuotes(String s) {
        if (s == null || s.charAt(0) != CharPool.DOUBLE_QUOTES) {
            return s;
        }
        return s.substring(1, s.length() - 1);
    }

    /**
     * ?????????????????????????????????
     */
    private void ifEnterArray(JSONParser.ValueContext ctx) {
        // ???????????????????????????
        if (ctx.getParent().getRuleIndex() == JSONParser.RULE_array) {
            builder.append("<element>");
        }
    }

    /**
     * ?????????????????????????????????
     */
    private void ifExitArray(JSONParser.ValueContext ctx) {
        // ???????????????????????????
        if (ctx.getParent().getRuleIndex() == JSONParser.RULE_array) {
            builder.append("</element>\n");
        }
    }

    /**
     * ??????JSON???XML?????????
     */
    public String getResult() {
        return builder.toString();
    }
}
