package com.mycompany.managerapp;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class HandlePreference {

    private final Preferences storage = Preferences.userRoot();

    public void saveToken(String token) {
        storage.put("ManagerToken", token);
        System.out.println("save successful");
    }

    public void saveManagerId(String managerId) {
        storage.put("managerId", managerId);
        System.out.println("save successful");
    }

    public String retrieveToken() {
        String token = storage.get("ManagerToken", "not found");
        return token;
    }

    public String retrieveManagerId() {
        String token = storage.get("managerId", "not found");
        return token;
    }

    public boolean logout() {
        try {
            storage.remove("managerId");
            storage.remove("ManagerToken");
            return true;
        } catch (Exception ex) {
            System.out.println("Failed to logout");
            return false;
        }
    }

}
