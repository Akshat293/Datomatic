package com.example.datomatic.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.datomatic.models.Doctor_list

@Dao
interface DocDao {
    @Query("SELECT * FROM Doctor_list")
    fun getAllDoctor_lists(): LiveData<List<Doctor_list>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(Doctor_list: Doctor_list)

    @Delete
    suspend fun delete(Doctor_list: Doctor_list)
}