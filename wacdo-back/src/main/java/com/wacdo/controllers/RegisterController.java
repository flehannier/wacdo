package com.wacdo.controllers;

import com.wacdo.dto.RegisterRequest;
import com.wacdo.exception.FunctionalException;
import com.wacdo.exception.TechnicalException;
import com.wacdo.services.RegisterService;
import jakarta.annotation.Nonnull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@CrossOrigin
public class RegisterController {

    private final RegisterService  registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping()
    public void register(@Nonnull @RequestBody RegisterRequest request) throws FunctionalException, TechnicalException {
        registerService.register(request);
    }
}
