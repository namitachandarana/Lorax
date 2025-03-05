package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CostAnalysisItem {
    public String YearId;
    public int GeoRegionId;
    public int CountryId;
    public int RegionId;
    public int SchemeId;
    public int SchmTypeId;
    public double Cost;
}

class JSONDataLoader {
    public static List<CostAnalysisItem> loadData(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(filePath), new TypeReference<List<CostAnalysisItem>>() {
        });
    }
}
