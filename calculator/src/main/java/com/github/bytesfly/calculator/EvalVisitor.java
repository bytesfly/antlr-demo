package com.github.bytesfly.calculator;

import com.github.bytesfly.calculator.antlr.ExprBaseVisitor;
import com.github.bytesfly.calculator.antlr.ExprParser;

import java.util.HashMap;
import java.util.Map;

public class EvalVisitor extends ExprBaseVisitor<Integer> {

    /**
     * 存放变量名和变量值的对应关系
     */
    private final Map<String, Integer> memory = new HashMap<>();

    /**
     * ID '=' expr NEWLINE  # assign
     */
    @Override
    public Integer visitAssign(ExprParser.AssignContext ctx) {
        // 获取变量名
        String id = ctx.ID().getText();
        // 计算表达式的值
        Integer value = visit(ctx.expr());
        // 暂存到map中
        memory.put(id, value);

        return value;
    }

    /**
     * expr NEWLINE  # printExpr
     */
    @Override
    public Integer visitPrintExpr(ExprParser.PrintExprContext ctx) {
        // 计算表达式的值
        Integer value = visit(ctx.expr());
        // 打印
        System.out.println(value);

        return 0;
    }

    /**
     * INT  # int
     */
    @Override
    public Integer visitInt(ExprParser.IntContext ctx) {
        return Integer.valueOf(ctx.INT().getText());
    }

    /**
     * ID  # id
     */
    @Override
    public Integer visitId(ExprParser.IdContext ctx) {
        String id = ctx.ID().getText();
        return memory.getOrDefault(id, 0);
    }

    /**
     * expr op=('*'|'/') expr  # MulDiv
     */
    @Override
    public Integer visitMulDiv(ExprParser.MulDivContext ctx) {
        // 计算左侧子表达式的值
        Integer left = visit(ctx.expr(0));
        // 计算右侧子表达式的值
        Integer right = visit(ctx.expr(1));

        // 根据不同的操作符做相应的运算
        if (ctx.op.getType() == ExprParser.MUL) {
            return left * right;
        } else {
            return left / right;
        }
    }

    /**
     * expr op=('+'|'-') expr  # AddSub
     */
    @Override
    public Integer visitAddSub(ExprParser.AddSubContext ctx) {
        // 计算左侧子表达式的值
        Integer left = visit(ctx.expr(0));
        // 计算右侧子表达式的值
        Integer right = visit(ctx.expr(1));

        // 根据不同的操作符做相应的运算
        if (ctx.op.getType() == ExprParser.ADD) {
            return left + right;
        } else {
            return left - right;
        }
    }

    /**
     * '(' expr ')'  # parens
     */
    @Override
    public Integer visitParens(ExprParser.ParensContext ctx) {
        // 返回子表达式的值
        return visit(ctx.expr());
    }
}
