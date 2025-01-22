package com.simon.api_alejandro.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Product::class], version = 1)
abstract class ProductDatabase: RoomDatabase(){
    abstract val dao : ProductDao
}
