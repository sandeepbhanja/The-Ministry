package com.Ministry.Records;

import com.Ministry.EnumUtils.TaskStatus;

import java.time.Instant;
import java.util.Objects;

public record Task(String taskId, String requiredCapability, TaskStatus taskStatus, String assignedWizardId,
                   String payload,
                   Instant createdAt, String failureReason,String parentTaskId) {

    public Task{
        Objects.requireNonNull(taskId);
        Objects.requireNonNull(requiredCapability);
        Objects.requireNonNull(taskStatus);

        if(taskId.trim().isEmpty()){
            throw new IllegalArgumentException("taskId is null");
        }
        if(requiredCapability.isEmpty()){
            throw new IllegalArgumentException("requiredCapability is empty");
        }
    }

}
