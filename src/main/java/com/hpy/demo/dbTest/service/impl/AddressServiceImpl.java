package com.hpy.demo.dbTest.service.impl;

import com.hpy.demo.dbTest.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author beichenhpy
 * @version 1.0
 * @description TODO
 * @since 2021/2/9 15:23
 */
@Slf4j
@Service
public class AddressServiceImpl implements AddressService {
    @Override
    public void addressLog() {
        log.info(Thread.currentThread().getName()+"-----------------addressLog");
    }
}
