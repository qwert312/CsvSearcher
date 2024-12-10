package org.csvsearcher;

import org.csvsearcher.domain.SearchResult;
import org.csvsearcher.services.CsvRowsSearcher;
import org.csvsearcher.services.SearchAppResultSaver;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        Map<String, String> parameters = receiveParameters(args);

        String csvPath = parameters.get("--data");
        short colNumber = Short.parseShort(parameters.get("--indexed-column-id"));
        String inputFilePath = parameters.get("--input-file");
        String outputJsonPath = parameters.get("--output-file");

        if (csvPath != null && colNumber != 0 && inputFilePath != null && outputJsonPath != null) {
            CsvRowsSearcher csvRowsSearcher = new CsvRowsSearcher(csvPath, colNumber);

            List<SearchResult> searchAppResult = (csvRowsSearcher.search(inputFilePath));

            SearchAppResultSaver jsonSaver = new SearchAppResultSaver();
            jsonSaver.save(searchAppResult, outputJsonPath);
        } else
            System.err.println("Some of parameters are incorrect.");
    }

    private static Map<String, String> receiveParameters(String[] args) {
        Map<String, String> parameters = new HashMap<>();

        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("--")) {
                String key = args[i];
                String value = i + 1 < args.length
                        && !args[i + 1].startsWith("--") ? args[++i] : null;
                parameters.put(key, value);
            }
        }

        return parameters;
    }
}
