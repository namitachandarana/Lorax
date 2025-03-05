import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.CostAnalysisItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CostAnalysisTest {
    private List<CostAnalysisItem> costAnalysisList;

    @BeforeEach
    void setup() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        costAnalysisList = objectMapper.readValue(new File("src/test/resources/payloads/CostAnalysis.json"), new TypeReference<List<CostAnalysisItem>>() {
        });
    }

    @Test
    void testCostAnalysisListCount() {
        int totalnumberofItemsInTheList = costAnalysisList.size();
        assertNotNull(costAnalysisList, "The list should not be null");
        assertTrue(costAnalysisList.size() > 0, "The list should contain at least one item");
        assertEquals(totalnumberofItemsInTheList, 53, "The total number of items in the list are 53");
    }

    @Test
    void testTopItemByCost() {
        CostAnalysisItem topItem = costAnalysisList.stream()
                .max(Comparator.comparingDouble(c -> c.Cost))
                .orElse(null);
        assertNotNull(topItem, "Top item should not be null");
        System.out.println("Top CountryId by Cost: " + topItem.CountryId);
        assertTrue(topItem.CountryId > 0, "CountryId should be a valid number");
    }

    @Test
    void testSumCostFor2016() {
        double totalCost2016 = costAnalysisList.stream()
                .filter(c -> "2016".equals(c.YearId))
                .mapToDouble(c -> c.Cost)
                .sum();
        System.out.println("Total Cost for 2016: " + totalCost2016);
        assertTrue(totalCost2016 >= 0, "Total cost for 2016 should be non-negative");
    }
}


