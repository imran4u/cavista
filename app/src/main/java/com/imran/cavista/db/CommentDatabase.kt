package com.imran.cavista.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.imran.cavista.db.dao.DAOComment
import com.imran.cavista.db.table.CommentModel

/**
 * Created by imran on 2020-09-26.
 */

@Database(entities = arrayOf(CommentModel::class), version = 1, exportSchema = false)
abstract class CommentDatabase : RoomDatabase() {

    abstract fun comentDao(): DAOComment

    companion object {

        @Volatile
        private var INSTANCE: CommentDatabase? = null

        fun getDatabase(context: Context): CommentDatabase {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, CommentDatabase::class.java, "COMMENT_DATABASE")
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!

            }
        }

    }

}
