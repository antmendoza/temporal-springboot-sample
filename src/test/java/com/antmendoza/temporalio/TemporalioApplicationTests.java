package com.antmendoza.temporalio;

import io.temporal.client.WorkflowOptions;
import io.temporal.common.RetryOptions;
import io.temporal.testing.TestWorkflowEnvironment;
import io.temporal.worker.Worker;
import org.junit.After;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class TemporalioApplicationTests {

    @MockBean
    MyService myService;

    @Autowired
    MyActivity myActivity;

    private TestWorkflowEnvironment testEnv;

    @BeforeEach
    public void setUp() {
        testEnv = TestWorkflowEnvironment.newInstance();
    }

    @AfterEach
    public void tearDown() {
        testEnv.close();
    }

    @After
    public void cleanUp() {
        testEnv.shutdown();
    }

    @Test
    void contextLoads() {


        String myServiceResponseMocked = String.valueOf(Math.random());
        Mockito.when(myService.executeMyService()).thenReturn(myServiceResponseMocked);


        final Worker worker = testEnv.newWorker("task_queue");
        worker.registerWorkflowImplementationTypes(MyWorkflowImpl.class);

        worker.registerActivitiesImplementations(myActivity);
        testEnv.start();

        final MyWorkflow myWorkflow = testEnv.getWorkflowClient().newWorkflowStub(MyWorkflow.class,
                WorkflowOptions.newBuilder()
                        .setRetryOptions(RetryOptions
                                .newBuilder()
                                .build())
                        .setTaskQueue("task_queue")
                        .build());


        Assert.assertEquals(myServiceResponseMocked, myWorkflow.start());
    }

}
