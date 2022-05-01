package com.keshk.ign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecordController {

    @GetMapping("/record")
    public String getRecordById(@RequestParam(value = "id") int id) {
        return "hello";
    }

    @GetMapping("/record")
    public String getRecordByReviewURL(@RequestParam(value = "review_url") String url) {
        return "hello";
    }

    @GetMapping("/record")
    public String getRecordByGenres(@RequestParam(value = "genres") List<String> genre) {
        return "hello";
    }
}
