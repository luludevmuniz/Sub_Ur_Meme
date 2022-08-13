package com.alpaca.telalogin.db

import androidx.room.RenameColumn
import androidx.room.migration.AutoMigrationSpec

@RenameColumn(tableName = "usuario", fromColumnName = "usuario", toColumnName = "login")
class DBSpecs : AutoMigrationSpec{
}