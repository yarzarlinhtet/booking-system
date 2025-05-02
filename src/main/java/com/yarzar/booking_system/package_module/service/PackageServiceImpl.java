package com.yarzar.booking_system.package_module.service;

import com.yarzar.booking_system.core_module.repository.ClassPackageRepository;
import com.yarzar.booking_system.package_module.controller.response.PackageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageServiceImpl implements IPackageService {

    private static final Logger LOG = LoggerFactory.getLogger(PackageServiceImpl.class);

    private final ClassPackageRepository packageRepository;

    public PackageServiceImpl(ClassPackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    @Override
    public List<PackageResponse> getAllPackage() {
        LOG.info("Fetching all packages");
        return this.packageRepository.findAll(Sort.by("updatedDate").descending())
                .stream()
                .map(PackageResponse::new)
                .toList();
    }
}
