package com.example.datomatic.db

import androidx.room.Database
import com.example.datomatic.models.Doctor_list

@Database(entities = [Doctor_list::class], version = 1, exportSchema = true)
class DocDatabase {

}