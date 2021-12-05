package com.github.bytesfly.calculator;

import com.github.bytesfly.calculator.antlr.ExprBaseVisitor;
import com.github.bytesfly.calculator.antlr.ExprParser;

import java.util.HashMap;
import java.util.Map;

public class EvalVisitor extends ExprBaseVisitor<Integer> {

    /**
     * "memory" for our calculator; variable/value pairs go here
     */
    private final Map<String, Integer> memory = new HashMap<>();

    /**
     * ID '=' expr NEWLINE
     */
    @Override
    public Integer visitAssign(ExprParser.AssignContext ctx) {
        // id is left-hand side of '='
        String id = ctx.ID().getText();
        // compute value of expression on right
        int value = visit(ctx.expr());
        // store it in our memory
        memory.put(id, value);
        return value;
    }

    /**
     * expr NEWLINE
     */
    @Override
    public Integer visitPrintExpr(ExprParser.PrintExprContext ctx) {
        // evaluate the expr child
        Integer value = visit(ctx.expr());
        // print the result
        System.out.println(value);
        // return dummy value
        return 0;
    }

    /**
     * INT
     */
    @Override
    public Integer visitInt(ExprParser.IntContext ctx) {
        return Integer.valueOf(ctx.INT().getText());
    }

    /**
     * ID
     */
    @Override
    public Integer visitId(ExprParser.IdContext ctx) {
        String id = ctx.ID().getText();
        if (memory.containsKey(id)) {
            return memory.get(id);
        }
        return 0;
    }

    /**
     * expr op=('*'|'/') expr
     */
    @Override
    public Integer visitMulDiv(ExprParser.MulDivContext ctx) {
        // get value of left subexpression
        int left = visit(ctx.expr(0));
        // get value of right subexpression
        int right = visit(ctx.expr(1));
        if (ctx.op.getType() == ExprParser.MUL) {
            return left * right;
        }
        // must be DIV
        return left / right;
    }

    /**
     * expr op=('+'|'-') expr
     */
    @Override
    public Integer visitAddSub(ExprParser.AddSubContext ctx) {
        // get value of left subexpression
        int left = visit(ctx.expr(0));
        // get value of right subexpression
        int right = visit(ctx.expr(1));
        if (ctx.op.getType() == ExprParser.ADD) {
            return left + right;
        }
        // must be SUB
        return left - right;
    }

    /**
     * '(' expr ')'
     */
    @Override
    public Integer visitParens(ExprParser.ParensContext ctx) {
        // return child expr's value
        return visit(ctx.expr());
    }
}
