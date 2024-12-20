package com.example.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/codeforces")
public class CodeforcesController {

    private final String BASE_API_URL = "https://codeforces.com/api/";

    @Autowired
    private RestTemplate restTemplate;

    // Endpoint to fetch user rating by handle
    @GetMapping("/user/{handle}/rating")
    public ResponseEntity<?> getUserRating(@PathVariable String handle) {
        String url = BASE_API_URL + "user.info?handles=" + handle;
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            Map<String, Object> responseBody = response.getBody();

            if (responseBody != null && "OK".equals(responseBody.get("status"))) {
                Map<String, Object> user = ((java.util.List<Map<String, Object>>) responseBody.get("result")).get(0);
                return ResponseEntity.ok(user.get("rating"));
            } else {
                return ResponseEntity.badRequest().body("Invalid handle or no data found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching data: " + e.getMessage());
        }
    }
}
