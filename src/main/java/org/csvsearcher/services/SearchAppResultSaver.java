package org.csvsearcher.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.csvsearcher.domain.SearchResult;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SearchAppResultSaver {

    public void save(List<SearchResult> searchAppResult, String outputJsonPath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.writeValue(new File(outputJsonPath), searchAppResult);
    }
}
