package com.objectpartners.plummer.junit5;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/states")
@Api(tags = "States")
public class StatesController {

    @Autowired
    private StatesService service;

    @GetMapping
    public Collection<State> getAll(@RequestParam(value = "pattern", required = false) String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            return service.getAll();
        }

        return service.getAllMatching(pattern);
    }

    @GetMapping("/{name}")
    public State get(@PathVariable("name") String name) {
        return service.findByName(name);
    }
}
