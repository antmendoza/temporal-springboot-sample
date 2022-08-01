package com.antmendoza.temporalio;

import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import org.springframework.beans.factory.annotation.Autowired;

import static java.time.Duration.ofSeconds;

public class MyWorkflowImpl implements MyWorkflow {

    @Autowired MyActivity myActivity;


    private final MyActivity activity = Workflow.newActivityStub(MyActivity.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(ofSeconds(2)).build());


    @Override
    public String start() {
        return activity.execute();
    }
}
