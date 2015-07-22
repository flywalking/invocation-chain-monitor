package com.demo.monitor.example;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.demo.monitor.example.api.Provider;

@Service
public class ProviderImpl implements Provider {

    @Override
    public Date getTime() {
        return new Date();
    }

}
