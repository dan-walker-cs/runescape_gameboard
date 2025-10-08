package com.nastyhaze.homeworld.hwe_app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 *  Service layer between PlayerController & PlayerRepository.
 *  Provides logic to retrieve and mutate repository data for the API.
 */
@Service
@Validated
@Transactional(readOnly = true)
public class PlayerService {

}
