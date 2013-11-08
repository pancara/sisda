package com.integrasolusi.query.filter;

import java.util.LinkedList;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : Jan 10, 2011
 * Time         : 8:30:06 PM
 */
public class CompositeFilter implements Filter {
    private QueryOperator operator;
    private List<Filter> filterList = new LinkedList<Filter>();

    public CompositeFilter() {
    }

    public CompositeFilter(QueryOperator op) {
        this.setOperator(op);
    }

    public QueryOperator getOperator() {
        return operator;
    }

    public void setOperator(QueryOperator operator) {
        this.operator = operator;
    }

    public void add(Filter filter) {
        filterList.add(filter);
    }

    public void remove(Filter filter) {
        filterList.remove(filter);
    }

    public Filter getFilter(int index) {
        return filterList.get(index);
    }

    public List<Filter> getFilterList() {
        return filterList;
    }

    public boolean isEmpty() {
        if (filterList.size() == 0) return true;

        return false;
    }

    @Override
    public String getExpression() {
        StringBuilder sb = new StringBuilder();
        sb.append(" (");
        for (int i = 0; i < filterList.size(); i++) {
            if (i != 0) sb.append(QueryOperator.getExpression(operator));
            sb.append(filterList.get(i).getExpression());
        }
        sb.append(") ");

        return sb.toString();
    }
}
