package com.yarzar.booking_system.class_module.controller;

import com.yarzar.booking_system.class_module.service.IClassService;
import com.yarzar.booking_system.core_module.common.annotation.ApiToken;
import com.yarzar.booking_system.core_module.common.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/class")
public class ClassController {

    private static final Logger LOG = LoggerFactory.getLogger(ClassController.class);

    private final IClassService classService;

    public ClassController(IClassService classService) {
        this.classService = classService;
    }

    @ApiToken
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllClass() {
        LOG.info("Fetching all classes");

        Map<String, Object> data = Map.of(
                "classes", classService.getAllClasses()
        );

        return HttpResponse.success("Successful", data);
    }
}
