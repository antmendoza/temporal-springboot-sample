package com.antmendoza.temporalio;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyActivityImpl implements MyActivity {

    @Autowired
    MyService myService;

    @Override
    public String execute() {
        return myService.executeMyService();
    }
}
