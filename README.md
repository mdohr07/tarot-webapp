# Tarot Webapp

Solo project using the [Tarot API](https://github.com/ekelen/tarot-api) to create an interactive web app for drawing tarot cards. Built to practice **Java**, **CSS**, and working with external APIs.

## Features

- Draw tarot cards and view their meanings.  
- Interactive, responsive interface.  
- User-friendly layout.

## Tech Stack

- **Frontend:** Java (with HTML/CSS integration)  
- **Styling:** CSS  
- **API:** [Tarot API](https://github.com/ekelen/tarot-api)

## Code Snippet

```java
@Controller
public class APIcontroller {

    @GetMapping("/api")
    public String api(Model model) {
        String baseURL = "https://tarotapi.dev/api/v1/";
        String endpoint = baseURL + "cards"; 

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(endpoint, String.class);

        String jsonString = response.getBody(); 
        model.addAttribute("jsonString", jsonString); 
        System.out.println(jsonString);

        return "api";
    }
}
```
