package com.Ministry.Records;

import com.Ministry.EnumUtils.WizardStatus;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public record WizardRegistration(String wizardId, Set<String> capabilities, WizardStatus status,
                                 Instant lastHeartbeatAt, int maxRunSeconds, Runnable endpoint) {

    public WizardRegistration{
        Objects.requireNonNull(wizardId);
        Objects.requireNonNull(capabilities);
        Objects.requireNonNull(status);
        if(wizardId.trim().isEmpty()){
            throw new IllegalArgumentException("wizard id is null");
        }
        if(capabilities.isEmpty()){
            throw new IllegalArgumentException("Capabilites is empty");
        }
        capabilities = Set.copyOf(capabilities);
    }

    public WizardRegistration withStatus(WizardStatus status){
        return new WizardRegistration(wizardId,capabilities,status, lastHeartbeatAt,maxRunSeconds,endpoint);
    }

    public WizardRegistration withCapabilites(Set<String> capabilities){
        return new WizardRegistration(wizardId,capabilities,status,lastHeartbeatAt,maxRunSeconds,endpoint);
    }
}
