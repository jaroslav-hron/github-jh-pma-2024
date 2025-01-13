package com.example.moje_fm_app


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class AssetDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "assets.db"
        private const val DATABASE_VERSION = 3 // Zvyšte verzi databáze
        const val TABLE_NAME = "assets"
        const val COLUMN_DEVICE_CODE = "device_code"
        const val COLUMN_BUILDING = "building"
        const val COLUMN_FLOOR = "floor"
        const val COLUMN_ROOM = "room"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_STANDARD = "standard"
        const val COLUMN_STATUS = "status"
        const val COLUMN_ORGANIZATIONAL_UNIT = "organizational_unit"
        const val COLUMN_IS_FINALIZED = "is_finalized" // Nový sloupec
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_DEVICE_CODE TEXT PRIMARY KEY,
                $COLUMN_BUILDING TEXT,
                $COLUMN_FLOOR TEXT,
                $COLUMN_ROOM TEXT,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_STANDARD TEXT,
                $COLUMN_STATUS TEXT,
                $COLUMN_ORGANIZATIONAL_UNIT TEXT,
                $COLUMN_IS_FINALIZED INTEGER DEFAULT 0
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 3) {
            db.execSQL("ALTER TABLE $TABLE_NAME ADD COLUMN $COLUMN_IS_FINALIZED INTEGER DEFAULT 0")
        }
    }


    fun insertAsset(values: ContentValues): Boolean {
        val db = writableDatabase
        return try {
            db.insertOrThrow(TABLE_NAME, null, values)
            true
        } catch (e: Exception) {
            false
        } finally {
            db.close()
        }
    }

    fun getLastNumberWithPrefix(prefix: String): Int {
        val db = readableDatabase
        val query = """
            SELECT MAX(CAST(SUBSTR($COLUMN_DEVICE_CODE, LENGTH(?) + 1) AS INTEGER)) 
            FROM $TABLE_NAME 
            WHERE $COLUMN_DEVICE_CODE LIKE ?
        """
        val cursor = db.rawQuery(query, arrayOf(prefix, "$prefix%"))
        var lastNumber = 0
        if (cursor.moveToFirst() && !cursor.isNull(0)) {
            lastNumber = cursor.getInt(0)
        }
        cursor.close()
        db.close()
        return lastNumber
    }

    fun getUnfinalizedDevices(): List<Map<String, Any>> {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_IS_FINALIZED = 0"
        val cursor = db.rawQuery(query, null)
        val devices = mutableListOf<Map<String, Any>>()

        if (cursor.moveToFirst()) {
            do {
                val device = mutableMapOf<String, Any>()
                device[COLUMN_DEVICE_CODE] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEVICE_CODE)) ?: "Unknown Code"
                device[COLUMN_BUILDING] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BUILDING)) ?: "Unknown Building"
                device[COLUMN_FLOOR] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FLOOR)) ?: "Unknown Floor"
                device[COLUMN_ROOM] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROOM)) ?: "Unknown Room"
                device[COLUMN_DESCRIPTION] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)) ?: "No Description"
                device[COLUMN_STANDARD] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STANDARD)) ?: "Unknown Standard"
                device[COLUMN_STATUS] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS)) ?: "Unknown Status"
                device[COLUMN_ORGANIZATIONAL_UNIT] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORGANIZATIONAL_UNIT)) ?: "Unknown Unit"
                device[COLUMN_IS_FINALIZED] = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_FINALIZED)) == 1
                devices.add(device)
            } while (cursor.moveToNext())
        } else {
            // Log if no data is found
            println("No unfinalized devices found in the database.")
        }

        cursor.close()
        db.close()
        return devices
    }

    fun getAllDevices(): List<Map<String, Any>> {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)
        val devices = mutableListOf<Map<String, Any>>()

        if (cursor.moveToFirst()) {
            do {
                val device = mutableMapOf<String, Any>()
                device[COLUMN_DEVICE_CODE] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEVICE_CODE)) ?: "Unknown Code"
                device[COLUMN_BUILDING] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BUILDING)) ?: "Unknown Building"
                device[COLUMN_FLOOR] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FLOOR)) ?: "Unknown Floor"
                device[COLUMN_ROOM] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROOM)) ?: "Unknown Room"
                device[COLUMN_DESCRIPTION] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)) ?: "No Description"
                device[COLUMN_STANDARD] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STANDARD)) ?: "Unknown Standard"
                device[COLUMN_STATUS] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS)) ?: "Unknown Status"
                device[COLUMN_ORGANIZATIONAL_UNIT] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORGANIZATIONAL_UNIT)) ?: "Unknown Unit"
                device[COLUMN_IS_FINALIZED] = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_FINALIZED)) == 1
                devices.add(device)
            } while (cursor.moveToNext())
        } else {
            // Log if no data is found
            println("No devices found in the database.")
        }

        cursor.close()
        db.close()
        return devices
    }




    fun finalizeDevice(deviceCode: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_IS_FINALIZED, 1)
        }
        val rowsUpdated = db.update(TABLE_NAME, values, "$COLUMN_DEVICE_CODE = ?", arrayOf(deviceCode))
        db.close()
        return rowsUpdated > 0
    }

    fun getDeviceStandards(): List<String> {
        val db = readableDatabase
        val query = "SELECT DISTINCT $COLUMN_STANDARD FROM $TABLE_NAME WHERE $COLUMN_STANDARD IS NOT NULL"
        val cursor = db.rawQuery(query, null)
        val standards = mutableListOf<String>()

        while (cursor.moveToNext()) {
            standards.add(cursor.getString(0))
        }

        cursor.close()
        db.close()
        return standards
    }

    fun addDeviceStandard(standard: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_STANDARD, standard)
        }
        return try {
            db.insertOrThrow(TABLE_NAME, null, values)
            true
        } catch (e: Exception) {
            false
        } finally {
            db.close()
        }
    }
    fun getDistinctValues(column: String): List<String> {
        val db = readableDatabase
        val query = "SELECT DISTINCT $column FROM $TABLE_NAME WHERE $column IS NOT NULL"
        val cursor = db.rawQuery(query, null)
        val values = mutableListOf<String>()

        while (cursor.moveToNext()) {
            values.add(cursor.getString(0))
        }

        cursor.close()
        db.close()
        return values
    }

    fun getFilteredDevices(building: String?, standard: String?): List<Map<String, Any>> {
        val db = readableDatabase
        val whereClauses = mutableListOf<String>()
        val whereArgs = mutableListOf<String>()

        if (building != null) {
            whereClauses.add("$COLUMN_BUILDING = ?")
            whereArgs.add(building)
        }
        if (standard != null) {
            whereClauses.add("$COLUMN_STANDARD = ?")
            whereArgs.add(standard)
        }

        val whereClause = if (whereClauses.isNotEmpty()) whereClauses.joinToString(" AND ") else "1=1"
        val query = "SELECT * FROM $TABLE_NAME WHERE $whereClause"
        val cursor = db.rawQuery(query, whereArgs.toTypedArray())

        val devices = mutableListOf<Map<String, Any>>()
        if (cursor.moveToFirst()) {
            do {
                val device = mutableMapOf<String, Any>()
                device[COLUMN_DEVICE_CODE] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEVICE_CODE))
                device[COLUMN_BUILDING] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BUILDING))
                device[COLUMN_FLOOR] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FLOOR))
                device[COLUMN_ROOM] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROOM))
                device[COLUMN_DESCRIPTION] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                device[COLUMN_STANDARD] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STANDARD))
                device[COLUMN_STATUS] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS))
                device[COLUMN_IS_FINALIZED] = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_FINALIZED)) == 1
                devices.add(device)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return devices
    }



}

