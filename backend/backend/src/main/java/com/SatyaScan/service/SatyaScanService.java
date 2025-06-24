package com.SatyaScan.service;

import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.*;
import java.nio.charset.StandardCharsets;

@Service
public class SatyaScanService {

    private static final String CLAIMBUSTER_URL = "https://idir.uta.edu/claimbuster/api/v2/score/text/";
    private static final String CLAIMBUSTER_API_KEY = "f53d4a6816f94be78584e093f62d54ca"; // Put your API Key
    private static final String GOOGLE_FACT_CHECK_URL = "https://factchecktools.googleapis.com/v1alpha1/claims:search";
    private static final String GOOGLE_API_KEY = "AIzaSyBwv0SaJs3OQ7PYOumZfzvQIS52CAsAMw0"; // Put your API Key

    public String verifyClaim(String claim) {
        try {
            if (!isCheckWorthy(claim)) {
                return "This claim is FALSE. This is a FAKE NEWS.";
            } else {
                return searchFactCheck(claim);
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private boolean isCheckWorthy(String claim) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String jsonPayload = "{\"input_text\":\"" + escapeJson(claim) + "\"}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(CLAIMBUSTER_URL))
                .header("Content-Type", "application/json")
                .header("x-api-key", CLAIMBUSTER_API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            double score = extractScore(response.body());
            return score >= 0.6;
        }
        return false;
    }

    private String searchFactCheck(String claim) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String encodedClaim = URLEncoder.encode(claim, StandardCharsets.UTF_8);
        String url = GOOGLE_FACT_CHECK_URL + "?query=" + encodedClaim + "&key=" + GOOGLE_API_KEY;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            String responseBody = response.body();
            if (responseBody.contains("\"claims\"")) {
                return "Fact-check results found:\n" + responseBody;
            } else {
                return "This is a correct NEWS but no fact-checks found for this claim.";
            }
        } else {
            return "Error from Google Fact Check API: " + response.body();
        }
    }

    private double extractScore(String json) {
        try {
            int scoreIndex = json.indexOf("\"score\":");
            if (scoreIndex != -1) {
                String substring = json.substring(scoreIndex + 8);
                int endIndex = substring.indexOf(",");
                if (endIndex == -1) endIndex = substring.indexOf("}");
                String scoreStr = substring.substring(0, endIndex).trim();
                return Double.parseDouble(scoreStr);
            }
        } catch (Exception e) {
            // Log error
        }
        return 0.0;
    }

    private String escapeJson(String text) {
        return text.replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
