package com.afarhidev.weighttracker.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.afarhidev.weighttracker.model.User;

@Dao
public interface UserDao {

    @Query("SELECT * FROM User WHERE email = :email AND password = :password LIMIT 1")
    User getUser(String email, String password);

    @Insert
    long addUser(User user);
}
