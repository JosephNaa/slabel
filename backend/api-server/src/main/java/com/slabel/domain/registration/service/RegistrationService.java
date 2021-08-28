package com.slabel.domain.registration.service;

import com.slabel.domain.registration.dto.RegistrationRequest;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    public String register(RegistrationRequest request) {
        return "works";
    }
}
