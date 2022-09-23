package com.nines.springboot.lab22.validation.controller;

import com.nines.springboot.lab22.validation.dto.UserUpdateGenderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author tanyujie
 * @classname UserController
 * @description TODO
 * @date 2022/8/17 16:42
 * @since 1.0
 */
@RequestMapping("/test")
@RestController
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping("/update_gender")
    public void updateGender(@Valid UserUpdateGenderDTO updateGenderDTO) {
        logger.info("[updateGender][updateGenderDTO: {}]", updateGenderDTO);
    }

}
