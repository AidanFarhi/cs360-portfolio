package com.afarhidev.weighttracker.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.afarhidev.weighttracker.model.WeightEntry;

import java.util.List;

@Dao
public interface WeightEntryDao {

    @Query("SELECT * FROM WeightEntry WHERE user_id = :userId AND category = 'daily' ORDER BY date DESC")
    List<WeightEntry> getDailyWeightEntriesForUser(long userId);

    @Query("SELECT * FROM WeightEntry WHERE user_id = :userId AND category = 'target' LIMIT 1")
    WeightEntry getTargetWeightEntryForUser(long userId);

    @Query("UPDATE WeightEntry SET weight = :targetWeight, date = :date WHERE user_id = :userId AND category = 'target'")
    void setTargetWeightEntryForUser(long userId, double targetWeight, String date);

    @Query("SELECT * FROM WeightEntry WHERE user_id = :userId AND category = 'daily' ORDER BY date DESC LIMIT 1")
    WeightEntry getCurrentWeightForUser(long userId);

    @Insert
    void addWeightEntry(WeightEntry weightEntry);

    @Query("UPDATE WeightEntry SET weight = :weight, date = :date WHERE id = :weightEntryId")
    void updateWeightEntry(float weight, String date, long weightEntryId);

    @Query("DELETE FROM WeightEntry WHERE id = :weightEntryId")
    void deleteWeightEntry(long weightEntryId);
}
