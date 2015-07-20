package com.demo.monitor.example;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.monitor.example.api.Provider;

@Service
public class Customer {

    @Autowired
    Provider provider;

    public void refer() {
        provider.getTime();
    }
}
