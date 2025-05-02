package com.yarzar.booking_system.class_module.service;

import com.yarzar.booking_system.class_module.controller.response.ClassResponse;
import com.yarzar.booking_system.core_module.entity.Classes;

import java.util.List;

public interface IClassService {
    List<ClassResponse> getAllClasses();
}
