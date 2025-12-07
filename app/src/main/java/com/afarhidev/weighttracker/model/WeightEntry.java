package com.afarhidev.weighttracker.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "user_id",
                onDelete = CASCADE
        )
)
public class WeightEntry {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;

    @ColumnInfo(name = "user_id")
    private long mUserId;

    @ColumnInfo(name = "weight")
    private float mWeight;

    @ColumnInfo(name = "date")
    private String mDate;

    @ColumnInfo(name = "category")
    private String mCategory;

    public WeightEntry(long userId, float weight, String date, String category) {
        this.mUserId = userId;
        this.mWeight = weight;
        this.mDate = date;
        this.mCategory = category;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public long getUserId() {
        return mUserId;
    }

    public void setUserId(long userId) {
        this.mUserId = userId;
    }

    public float getWeight() {
        return mWeight;
    }

    public void setWeight(float weight) {
        this.mWeight = weight;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        this.mDate = date;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String mCategory) {
        this.mCategory = mCategory;
    }
}
