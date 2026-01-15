package com.wacdo.services;

import com.wacdo.dto.RegisterRequest;
import com.wacdo.entities.Collaborateur;
import com.wacdo.exception.FunctionalException;
import com.wacdo.exception.TechnicalException;
import lombok.NonNull;

public interface RegisterService {
    Collaborateur register (@NonNull RegisterRequest request) throws FunctionalException, TechnicalException;
}
