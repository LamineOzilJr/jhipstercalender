package com.lamine;
import ORG.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/services/calendar")
public class CalandarController {

    @Autowired
    private RestTemplate restTemplate;


    private HistoricalSearchRepository historicalSearchRepository;

    @PostMapping("/dayfinder")
    public ResponseEntity<Map<String, String>> findDayOfWeek(@RequestBody Map<String, String> request) {
        String dateString = request.get("date");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);
        ResponseEntity<Map> responseEntity = restTemplate.exchange("http://localhost:8083/services/calendar/dayfinder", HttpMethod.POST, entity, Map.class);
        Map<String, String> response = responseEntity.getBody();


        HistoricalSearch historicalSearch = new HistoricalSearch();
        historicalSearch.setSearchDate(LocalDateTime.now());
        historicalSearch.setSearchItems(request);
        historicalSearchRepository.save(historicalSearch);


        return ResponseEntity.ok(response);
    }

    @GetMapping("/historique/all")
    public List<HistoricalSearch> getAllHistoricalSearches() {
        return historicalSearchRepository.findAll();
    }
}
