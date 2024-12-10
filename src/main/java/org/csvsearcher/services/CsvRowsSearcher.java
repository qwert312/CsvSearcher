package org.csvsearcher.services;

import org.csvsearcher.domain.SearchResult;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvRowsSearcher {
    Trie trie = new Trie();

    public CsvRowsSearcher(String csvPath, int colNumber) throws IOException {
        fillTrieFromCsv(csvPath, colNumber);
    }

    public List<SearchResult> search(String inputFilePath) throws IOException {
        List<SearchResult> searchResults = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                SearchResult searchResult = new SearchResult(line);

                List<Integer> foundRows = trie.searchByPrefix(line);

                if (foundRows == null)
                    searchResult.addRow(-1);
                else
                    searchResult.addAllRows(foundRows);
                searchResults.add(searchResult);
            }
        }

        return searchResults;
    }

    private void fillTrieFromCsv(String csvPath, int colNumber) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(csvPath))) {
            String line;
            int row = 1;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.replace("\"", "");
                String[] lineValues = line.split(",");
                this.trie.insert(lineValues[colNumber - 1], row++);
            }
        }
    }

    private class Trie {

        private TrieNode root = new TrieNode();

        private class TrieNode {
            private Map<Character, TrieNode> children = new HashMap();
            private List<Integer> rows = new ArrayList<>();
        }

        private void insert(String str, int row) {
            if (str == null)
                return;
            TrieNode currentNode = root;
            for (Character c: str.toCharArray()) {
                currentNode = currentNode.children.computeIfAbsent(c, k -> new TrieNode());
                currentNode.rows.add(row);
            }
        }

        private List<Integer> searchByPrefix(String prefix) {
            if (prefix == null)
                return null;

            TrieNode currentNode = root;
            for (Character c: prefix.toCharArray()) {
                currentNode = currentNode.children.get(c);
                if (currentNode == null)
                    return null;
            }
            return currentNode.rows;
        }
    }
}
