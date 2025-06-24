import java.net.URI;
import java.net.URLEncoder;
import java.net.http.*;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class SatyaScan {

    static final String CLAIMBUSTER_URL = "https://idir.uta.edu/claimbuster/api/v2/score/text/";
    static final String CLAIMBUSTER_API_KEY = "f53d4a6816f94be78584e093f62d54ca"; // Replace with your key

    static final String GOOGLE_FACT_CHECK_URL = "https://factchecktools.googleapis.com/v1alpha1/claims:search";
    static final String GOOGLE_API_KEY = "AIzaSyBwv0SaJs3OQ7PYOumZfzvQIS52CAsAMw0"; 
    
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n Enter a claim you'd like to verify: ");
        String claim = scanner.nextLine();
        scanner.close();

        boolean worthChecking = isCheckWorthy(claim);
        if (!worthChecking) {
            System.out.println(" \n This claim is FALSE. \nThis is a FAKE NEWS");
            return;
        }

        searchFactCheck(claim);
    }

    public static boolean isCheckWorthy(String claim) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String jsonPayload = "{\"input_text\":\"" + escapeJson(claim) + "\"}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(CLAIMBUSTER_URL))
                .header("Content-Type", "application/json")
                .header("x-api-key", CLAIMBUSTER_API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            String responseBody = response.body();
            double score = extractScore(responseBody);
            System.out.println("ClaimBuster Score: " + score);
            return score >= 0.6;
        } else {
            System.out.println("Error from ClaimBuster API: " + response.body());
            return false;
        }
    }

    public static void searchFactCheck(String claim) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String encodedClaim = URLEncoder.encode(claim, StandardCharsets.UTF_8);
        String url = GOOGLE_FACT_CHECK_URL + "?query=" + encodedClaim + "&key=" + GOOGLE_API_KEY;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            String responseBody = response.body();
            
            // Check if there are claims in the response
            if (responseBody.contains("\"claims\"")) {
                System.out.println("\n Fact-check results found:");
                System.out.println("Raw response: " + responseBody);
            } else {
                System.out.println("This is a correct NEWS but no fact-checks found for this claim.");
            }
        } else {
            System.out.println("Error from Google Fact Check API: " + response.body());
        }
    }
    
    // Helper method to escape JSON strings
    private static String escapeJson(String text) {
        return text.replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
    
    // Simple method to extract score from JSON response
    private static double extractScore(String json) {
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
            System.out.println("Error parsing score: " + e.getMessage());
        }
        return 0.0;
    }
}