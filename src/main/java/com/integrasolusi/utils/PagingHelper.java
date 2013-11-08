package com.integrasolusi.utils;

import java.util.LinkedList;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/19/11
 * Time         : 6:29 PM
 */
public class PagingHelper {
    private Long rowPerPage = 10L;
    private Long displayedPageCount = 8L;

    public Long getRowPerPage() {
        return this.rowPerPage;
    }

    public void setRowPerPage(Long value) {
        this.rowPerPage = value;
    }

    public Long getDisplayedPageCount() {
        return displayedPageCount;
    }

    public void setDisplayedPageCount(Long displayedPageCount) {
        this.displayedPageCount = displayedPageCount;
    }

    public Long calcPageCount(Long totalData) {
        return calcPageCount(totalData, this.rowPerPage);
    }

    public Long calcPageCount(Long totalData, Long rowPerPage) {
        return (long) Math.ceil((double) totalData / (double) rowPerPage);
    }

    public Long getStartRow(Long page) {
        return getStartRow(page, this.rowPerPage);
    }

    public Long getStartRow(Long page, Long rowPerPage) {
        return (page - 1) * rowPerPage;
    }

    public List<Long> getDisplayedPages(Long currentPage, Long dataCount) {
        return getDisplayedPages(currentPage,
                this.displayedPageCount,
                dataCount,
                this.rowPerPage);
    }

    public List<Long> getDisplayedPages(Long current, Long displayedPageCount, Long dataCount, Long rowPerPage) {
        Long totalPage = calcPageCount(dataCount, rowPerPage);

        Long start = (long) Math.max(1, (current - (double) displayedPageCount / 2));

        Long end = Math.min(totalPage, start + displayedPageCount - 1);
        start = Math.max(1, end - displayedPageCount + 1);

        List<Long> pages = new LinkedList<Long>();
        for (long i = start; i <= end; i++)
            pages.add(i);
        return pages;
    }

}
