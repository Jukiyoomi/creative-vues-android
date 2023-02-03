package com.frite.creativevues.db;

import com.google.firebase.firestore.FirebaseFirestore;

public class DBProvider {
    private final FirebaseFirestore db;

    private static final DBProvider instance = new DBProvider();

    public DBProvider() {
        this.db = FirebaseFirestore.getInstance();
    }

    public FirebaseFirestore getDb() {
        return this.db;
    }

    public static DBProvider getInstance() {
        return instance;
    }
}
