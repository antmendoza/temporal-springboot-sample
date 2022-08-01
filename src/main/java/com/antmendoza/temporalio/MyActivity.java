package com.antmendoza.temporalio;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface MyActivity {


    @ActivityMethod
    String execute();
}
