package com.Ministry.Service;

import com.Ministry.EnumUtils.WizardStatus;
import com.Ministry.Records.WizardRegistration;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class WizardRegistry {

    private final ConcurrentHashMap<String, WizardRegistration> wizardsList = new ConcurrentHashMap<>();

    public boolean registerWizard(WizardRegistration wizardRegistration) {
            return wizardsList.putIfAbsent(wizardRegistration.wizardId(), wizardRegistration) == null;
    }

    public List<String> list() {
        return new ArrayList<>(wizardsList.keySet());
    }

    public List<WizardRegistration> findAgentByCapability(String capability) {
        List<WizardRegistration> wizards = new ArrayList<>();
        for (Map.Entry<String, WizardRegistration> tempWizard : wizardsList.entrySet()) {
            if (tempWizard.getValue().capabilities().contains(capability) && tempWizard.getValue().status().equals(WizardStatus.IDLE)) {
                wizards.add(tempWizard.getValue());
            }
        }
        return wizards;
    }

    public boolean updateStatus(String wizardId, WizardStatus expected, WizardStatus next){
        WizardRegistration wizard = wizardsList.getOrDefault(wizardId, null);
        if(wizard!=null){
            if(wizard.status().equals(expected)){
                WizardRegistration updatedWizard = wizard.withStatus(next);
                return wizardsList.replace(wizardId,wizard,updatedWizard);
            }
            else{
                return false;
            }
        }
        return false;
    }

    public boolean updateStatusWrong(String wizardId, WizardStatus expected, WizardStatus next){
        WizardRegistration wizard = wizardsList.getOrDefault(wizardId, null);
        if(wizard!=null){
            if(wizard.status().equals(expected)){
                wizard = wizard.withStatus(next);
                wizardsList.put(wizardId,wizard);
                return true;
            }
            else{
                return false;
            }
        }
        return false;
    }

}
