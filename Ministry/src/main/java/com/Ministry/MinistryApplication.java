package com.Ministry;

import com.Ministry.EnumUtils.WizardStatus;
import com.Ministry.Records.WizardRegistration;
import com.Ministry.Service.WizardRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

@SpringBootApplication
public class MinistryApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(MinistryApplication.class, args);

        WizardRegistration wizard1 = new WizardRegistration(
                "wizard-1",
                Set.of("READ", "WRITE", "DELETE"),
                WizardStatus.IDLE,
                Instant.now(),
                60,
                () -> System.out.println("Wizard 1")
        );

        WizardRegistration wizard2 = new WizardRegistration(
                "wizard-2",
                Set.of("READ", "WRITE", "EXECUTE"),
                WizardStatus.IDLE,
                Instant.now(),
                120,
                () -> System.out.println("Wizard 2")
        );

        WizardRegistration wizard3 = new WizardRegistration(
                "wizard-3",
                Set.of("ADMIN", "EXECUTE"),
                WizardStatus.IDLE,
                Instant.now(),
                90,
                () -> System.out.println("Wizard 3")
        );
        WizardRegistry wizardRegistry = new WizardRegistry();
        wizardRegistry.registerWizard(wizard1);
        wizardRegistry.registerWizard(wizard2);
        wizardRegistry.registerWizard(wizard3);

        System.out.println(wizardRegistry.findAgentByCapability("READ"));

        System.out.println(wizardRegistry.findAgentByCapability("Test"));

        System.out.println(wizardRegistry.findAgentByCapability("ADMIN"));

//        CountDownLatch startGate = new CountDownLatch(1);
//        for (int i = 0; i < 100; i++) {
//            new Thread(() -> {
//                try {
//                    startGate.await();
//                    System.out.println(Thread.currentThread() + " update status : " + wizardRegistry.updateStatus("wizard-2", WizardStatus.IDLE, WizardStatus.BUSY));
//                } catch (Exception e) {
//                    Thread.currentThread().interrupt();
//                }
//            }).start();
//        }
//
//        Thread.sleep(500);
//
//        System.out.println("--- GO! Releasing all threads ---");
//        startGate.countDown();

        CountDownLatch startGate2 = new CountDownLatch(1);
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    startGate2.await();
                    System.out.println(Thread.currentThread() + " update status : " + wizardRegistry.updateStatusWrong("wizard-2", WizardStatus.IDLE, WizardStatus.BUSY));
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }

        Thread.sleep(500);

        System.out.println("--- GO! Releasing all threads wrong update---");
        startGate2.countDown();

//        System.out.println("Update status response 1 : "+wizardRegistry.updateStatus("wizard-2",WizardStatus.IDLE,WizardStatus.BUSY));
//        System.out.println("Update status response 2 : "+wizardRegistry.updateStatus("wizard-2",WizardStatus.IDLE,WizardStatus.BUSY));
//
//        System.out.println(wizardRegistry.findAgentByCapability("READ"));
//
//        System.out.println(wizardRegistry.registerWizard(wizard1));
    }

}
