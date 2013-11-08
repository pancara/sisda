package com.integrasolusi.query.filter;

/**
 * Programmer   : pancara
 * Date         : Jan 10, 2011
 * Time         : 8:31:07 PM
 */
public enum QueryOperator {
    LESS, LESS_OR_EQUALS, GREATER, GREATER_OR_EQUALS, EQUALS, NOT_EQUALS, LIKE, AND, OR, IN, IS;

    public static String getExpression(QueryOperator operator) {
        switch (operator) {
            case LESS:
                return "<";
            case LESS_OR_EQUALS:
                return "<=";
            case GREATER:
                return ">";
            case GREATER_OR_EQUALS:
                return ">=";
            case EQUALS:
                return "=";
            case NOT_EQUALS:
                return "<>";
            case LIKE:
                return "LIKE";
            case AND:
                return "AND";
            case OR:
                return "OR";
            case IN:
                return "IN";
            case IS:
                return "IS";
        }
        throw new RuntimeException("Operator expression not available.");
    }
}
