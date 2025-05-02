package com.yarzar.booking_system.package_module.controller;

import com.yarzar.booking_system.core_module.common.annotation.ApiToken;
import com.yarzar.booking_system.core_module.common.response.HttpResponse;
import com.yarzar.booking_system.package_module.service.IPackageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/package")
public class PackageController {
    private static final Logger LOG = LoggerFactory.getLogger(PackageController.class);

    private final IPackageService packageService;

    public PackageController(IPackageService packageService) {
        this.packageService = packageService;
    }

    @ApiToken
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllPackage() {
        LOG.info("Package endpoint called");

        Map<String, Object> data = Map.of(
                "packages", packageService.getAllPackage()
        );

        return HttpResponse.success("Successful", data);
    }
}
