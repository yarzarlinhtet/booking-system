package com.yarzar.booking_system.class_module.service;


import com.yarzar.booking_system.class_module.controller.response.ClassResponse;
import com.yarzar.booking_system.core_module.entity.Classes;
import com.yarzar.booking_system.core_module.repository.ClassesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassServiceImpl implements IClassService {

    private static final Logger LOG = LoggerFactory.getLogger(ClassServiceImpl.class);

    private final ClassesRepository classesRepository;

    public ClassServiceImpl(ClassesRepository classesRepository) {
        this.classesRepository = classesRepository;
    }

    @Override
    public List<ClassResponse> getAllClasses() {
        return this.classesRepository.findAll(
                        Sort.sort(Classes.class)
                                .by(Classes::getCreatedDate)
                                .descending()
                )
                .stream()
                .map(ClassResponse::new)
                .toList();
    }
}
