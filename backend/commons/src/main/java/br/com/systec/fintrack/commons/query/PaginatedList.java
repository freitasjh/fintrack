package br.com.systec.fintrack.commons.query;

import java.util.List;

public class PaginatedList<T> {

    private List<T> resultList;
    private boolean hasNext;
    private int pageSizeResult;
    private int totalResults;

    public List<T> getResultList() {
        return resultList;
    }

    public void addAll(List<T> list) {
        this.resultList = list;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public int getPageSizeResult() {
        return pageSizeResult;
    }

    public void setPageSizeResult(int pageSizeResult) {
        this.pageSizeResult = pageSizeResult;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
