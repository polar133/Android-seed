package com.polar.android_seed.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.polar.android_seed.models.data.entities.Usuario;

import java.sql.SQLException;

public class DBHelper extends OrmLiteSqliteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "myapp.db";
    static private DBHelper instance;
    //RuntimeExceptionDao
    private RuntimeExceptionDao<Usuario, Integer> usuarioDaoRuntime;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    static public void init(Context ctx) {
        if (null == instance) {
            instance = new DBHelper(ctx);
        }
    }

    static public DBHelper getInstance() {
        return instance;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Usuario.class);
            //TODO: Mas clases
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createAllTables() {
        try {
            TableUtils.createTable(getConnectionSource(), Usuario.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onDelete() {
        try {
            TableUtils.clearTable(getConnectionSource(), Usuario.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        onCreate(sqLiteDatabase, connectionSource);
    }

    @Override
    public void close() {
        super.close();
        usuarioDaoRuntime = null;
    }

    public RuntimeExceptionDao<Usuario, Integer> getUsuarioDataDao() {
        if (usuarioDaoRuntime == null) {
            usuarioDaoRuntime = getRuntimeExceptionDao(Usuario.class);
        }
        return usuarioDaoRuntime;
    }
}
