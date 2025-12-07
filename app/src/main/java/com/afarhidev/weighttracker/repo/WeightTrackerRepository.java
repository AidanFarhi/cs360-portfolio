package com.afarhidev.weighttracker.repo;


import android.content.Context;

import androidx.room.Room;

import com.afarhidev.weighttracker.dao.SessionDao;
import com.afarhidev.weighttracker.dao.UserDao;
import com.afarhidev.weighttracker.dao.WeightEntryDao;
import com.afarhidev.weighttracker.model.Session;
import com.afarhidev.weighttracker.model.User;
import com.afarhidev.weighttracker.model.WeightEntry;

import java.util.List;

public class WeightTrackerRepository {

    private static WeightTrackerRepository weightTrackerRepository;
    private final UserDao userDao;
    private final WeightEntryDao weightEntryDao;
    private final SessionDao sessionDao;

    public static WeightTrackerRepository getInstance(Context context) {
        if (weightTrackerRepository == null) {
            weightTrackerRepository = new WeightTrackerRepository(context);
        }
        return weightTrackerRepository;
    }

    private WeightTrackerRepository(Context context) {
        WeightTrackerDatabase database = Room.databaseBuilder(
                context, WeightTrackerDatabase.class, "weight_tracker.db")
                .fallbackToDestructiveMigrationFrom(true, 1)
                .allowMainThreadQueries()
                .build();
        userDao = database.userDao();
        weightEntryDao = database.weightEntryDao();
        sessionDao = database.sessionDao();
    }

    public User addUser(String email, String password) {
        User newUser = new User(email, password);
        long newUserId = userDao.addUser(newUser);
        newUser.setId(newUserId);
        return newUser;
    }

    public void addSession(long userId) {
        Session newSession = new Session(userId);
        long newSessionId = sessionDao.addSession(newSession);
        newSession.setId(newSessionId);
    }

    public void clearSession() {
        sessionDao.clearSession();
    }

    public Session getCurrentSession() {
        return sessionDao.getCurrentSession();
    }

    public User getUser(String email, String password) {
        return userDao.getUser(email, password);
    }

    public List<WeightEntry> getDailyWeightEntriesForUser(long userId) {
        return weightEntryDao.getDailyWeightEntriesForUser(userId);
    }

    public WeightEntry getTargetWeightEntryForUser(long userId) {
        return weightEntryDao.getTargetWeightEntryForUser(userId);
    }

    public WeightEntry getCurrentWeightForUser(long userId) {
        return weightEntryDao.getCurrentWeightForUser(userId);
    }

    public void deleteWeightEntry(long weightEntryId) {
        weightEntryDao.deleteWeightEntry(weightEntryId);
    }

    public void addWeightEntry(WeightEntry weightEntry) {
        weightEntryDao.addWeightEntry(weightEntry);
    }

    public void setTargetWeightEntryForUser(long userId, double targetWeight, String date) {
        weightEntryDao.setTargetWeightEntryForUser(userId, targetWeight, date);
    }

    public void updateWeightEntry(float weight, String date, long weightEntryId) {
        weightEntryDao.updateWeightEntry(weight, date, weightEntryId);
    }
}
