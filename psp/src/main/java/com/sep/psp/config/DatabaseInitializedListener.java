package com.sep.psp.config;

import com.sep.psp.service.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializedListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private EncryptionService encryptAccountsService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // This method will be called after the application context is initialized or refreshed
        // You can add your logic here to run after Hibernate has populated the database from data.sql
        System.out.println("Database initialization completed. Running post-initialization logic...");
        runAfterDatabaseInitialization();
    }

    private void runAfterDatabaseInitialization() {
        // Add your custom logic here to run after the database has been populated
        // For example:
        // Do some data verification, initiate some background processes, etc.
        // This will execute after Hibernate has populated the database from data.sql
        System.out.println("Running custom logic after database initialization...");
        encryptAccountsService.encryptAccountInformation();
        encryptAccountsService.encryptWebshops();
        // Your custom logic here
    }
}
