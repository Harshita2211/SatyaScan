package com.SatyaScan;

import com.SatyaScan.service.SatyaScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scan")
public class SatyaScanController {

    @Autowired
    private SatyaScanService scanService;

    @PostMapping
    public String verifyClaim(@RequestBody String claim) {
        return scanService.verifyClaim(claim);
    }
}
