package com.integrasolusi.query.filter;

/**
 * Programmer   : pancara
 * Date         : Jan 10, 2011
 * Time         : 8:30:28 PM
 */
public class NegateFilter implements Filter {
    private Filter filter;

    public NegateFilter() {
    }

    public NegateFilter(Filter filter) {
        this.setFilter(filter);
    }

    @Override
    public String getExpression() {
        return " NOT " + getFilter().getExpression();
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }
}
