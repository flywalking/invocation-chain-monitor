package com.demo.monitor.example;

import java.util.Date;

import com.demo.monitor.example.api.Provider;

public class ProviderImpl implements Provider {

    @Override
    public Date getTime() {
        return new Date();
    }

}
