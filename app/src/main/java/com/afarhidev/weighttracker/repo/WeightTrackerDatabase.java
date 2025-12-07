package com.afarhidev.weighttracker.repo;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.afarhidev.weighttracker.dao.SessionDao;
import com.afarhidev.weighttracker.dao.UserDao;
import com.afarhidev.weighttracker.dao.WeightEntryDao;
import com.afarhidev.weighttracker.model.Session;
import com.afarhidev.weighttracker.model.User;
import com.afarhidev.weighttracker.model.WeightEntry;

@Database(entities = {User.class, WeightEntry.class, Session.class}, version = 2)
public abstract class WeightTrackerDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract WeightEntryDao weightEntryDao();
    public abstract SessionDao sessionDao();
}
