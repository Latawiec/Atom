package model.databaseControllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import model.database.ParticleDB;
import model.database.UserDB;
import model.database.UserParticleDB;

import java.sql.SQLException;

/**
 * Created by Latawiec on 26/05/2017.
 */
public class DatabaseAccessor {

    private static String databaseURL = "jdbc:sqlite:database.db";

    private static ConnectionSource source;
    private static DatabaseAccessor instance = null;

    private static Dao<ParticleDB, ?> daoParticles;
    private static Dao<UserDB, ?> daoUsers;
    private static Dao<UserParticleDB, ?> daoUserParticles;

    protected DatabaseAccessor() throws SQLException {
        source = new JdbcConnectionSource(databaseURL);

        TableUtils.createTableIfNotExists(source, ParticleDB.class);
        TableUtils.createTableIfNotExists(source, UserDB.class);
        TableUtils.createTableIfNotExists(source, UserParticleDB.class);

        daoParticles = DaoManager.createDao(source, ParticleDB.class);
        daoUsers = DaoManager.createDao(source, UserDB.class);
        daoUserParticles = DaoManager.createDao(source, UserParticleDB.class);
    }

    public static DatabaseAccessor getInstance() throws SQLException {
        if(instance == null){
            instance = new DatabaseAccessor();
        }
        return instance;
    }

    public static void Save(ParticleDB ob) throws SQLException {
        daoParticles.createOrUpdate(ob);
    }

    public static void Save(UserDB ob) throws SQLException {
        daoUsers.createOrUpdate(ob);
    }

    public static void Save(UserParticleDB ob) throws SQLException {
        daoUserParticles.createOrUpdate(ob);
    }
}
