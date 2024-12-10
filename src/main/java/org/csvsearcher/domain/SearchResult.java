package org.csvsearcher.domain;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {

    private String search;
    private List<Integer> result = new ArrayList<>();

    public SearchResult() {
    }

    public SearchResult(String search) {
        this.search = search;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public List<Integer> getResult() {
        return new ArrayList<>(result);
    }

    public void addRow(Integer row) {
        result.add(row);
    }

    public void addAllRows(List<Integer> rows) {
        result.addAll(rows);
    }

    public Integer removeRowByIndex(int index) {
        return result.remove(index);
    }

    public void removeRow(Integer row) {
        result.remove(row);
    }

    public int getRow(int index) {
        return result.get(index);
    }

    public boolean containsRow(Integer row) {
        return result.contains(row);
    }

    public int rowCount() {
        return result.size();
    }
}
