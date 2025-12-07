package com.afarhidev.weighttracker.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.afarhidev.weighttracker.model.Session;

@Dao
public interface SessionDao {

    @Query("SELECT * FROM Session LIMIT 1")
    Session getCurrentSession();

    @Insert
    long addSession(Session session);

    @Query("DELETE FROM Session")
    void clearSession();
}
