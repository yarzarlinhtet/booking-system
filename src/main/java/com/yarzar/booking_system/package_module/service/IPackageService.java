package com.yarzar.booking_system.package_module.service;

import com.yarzar.booking_system.package_module.controller.response.PackageResponse;

import java.util.List;

public interface IPackageService {

    List<PackageResponse> getAllPackage();
}
