package model.databaseControllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Particle;
import javafx.scene.control.Tab;
import model.database.ParticleDB;
import model.database.UserDB;
import model.database.UserParticleDB;

import javax.management.Query;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Latawiec on 26/05/2017.
 */
public class DatabaseAccessor {

    private static String databaseURL = "jdbc:sqlite:database.db";

    private static ConnectionSource source;
    private static DatabaseAccessor instance = null;

    private static Dao<ParticleDB, Integer> daoParticles;
    private static Dao<UserDB, Integer> daoUsers;
    private static Dao<UserParticleDB, Integer> daoUserParticles;

    protected DatabaseAccessor() throws SQLException {
        source = new JdbcConnectionSource(databaseURL);

        TableUtils.createTableIfNotExists(source, ParticleDB.class);
        TableUtils.createTableIfNotExists(source, UserDB.class);
        TableUtils.createTableIfNotExists(source, UserParticleDB.class);

        daoParticles = DaoManager.createDao(source, ParticleDB.class);
        daoUsers = DaoManager.createDao(source, UserDB.class);
        daoUserParticles = DaoManager.createDao(source, UserParticleDB.class);

        //particlesCreator();
        //usersCreator();
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
    public UserDB getUser(String username, String password) throws SQLException {
        QueryBuilder<UserDB, ?> queryBuilder = daoUsers.queryBuilder();
        queryBuilder.where().eq("username", username).and().eq("password", password);
        return queryBuilder.queryForFirst();
    }

    public List<UserParticleDB> getUserParticles(UserDB user) throws SQLException {
        QueryBuilder<UserParticleDB, ?> queryBuilder = daoUserParticles.queryBuilder();
        queryBuilder.where().eq("owner_id", user.getID());
        return queryBuilder.query();
    }


    private void usersCreator() throws SQLException {
        daoUsers.createOrUpdate(new UserDB("Latawiec", "1", 0));

        daoUserParticles.createOrUpdate(new UserParticleDB( daoUsers.queryForId(1), daoParticles.queryForId(1),100 ));
        daoUserParticles.createOrUpdate(new UserParticleDB( daoUsers.queryForId(1), daoParticles.queryForId(1),100 ));
        daoUserParticles.createOrUpdate(new UserParticleDB( daoUsers.queryForId(1), daoParticles.queryForId(1),100 ));
        daoUserParticles.createOrUpdate(new UserParticleDB( daoUsers.queryForId(1), daoParticles.queryForId(1),100 ));
        daoUserParticles.createOrUpdate(new UserParticleDB( daoUsers.queryForId(1), daoParticles.queryForId(1),100 ));

        daoUserParticles.createOrUpdate(new UserParticleDB( daoUsers.queryForId(1), daoParticles.queryForId(14),10000 ));
        daoUserParticles.createOrUpdate(new UserParticleDB( daoUsers.queryForId(1), daoParticles.queryForId(30),2000 ));
        daoUserParticles.createOrUpdate(new UserParticleDB( daoUsers.queryForId(1), daoParticles.queryForId(18),11000 ));
    }

    public void deleteUserParticles(UserDB user) throws SQLException {
        DeleteBuilder<UserParticleDB, Integer> deleteBuilder = daoUserParticles.deleteBuilder();
        deleteBuilder.where().eq("owner_id", user.getID());
        deleteBuilder.delete();
    }

    public void updateUserParticles(ArrayList<ParticleController> particles) throws SQLException {
        for(ParticleController p : particles){
            daoUserParticles.createOrUpdate(p.getSourceDB());
        }
    }

    public List<ParticleDB> getParticlesWithNucleonsNumber (int nucleons) throws SQLException {
        QueryBuilder<ParticleDB, ?> queryBuilder = daoParticles.queryBuilder();
        queryBuilder.where().eq("nucleons", nucleons);
        return queryBuilder.query();
    }

    private void particlesCreator() throws SQLException {
        //Hydrogen
        daoParticles.createOrUpdate(new ParticleDB(0, 1, new int[]{1,0,0,0,0,0,0}, "Hydrogen", "H", 1.008f, 0));
        //Deuterium
        daoParticles.createOrUpdate(new ParticleDB(1, 1, new int[]{1,0,0,0,0,0,0}, "Deuterium", "H", 2.014f, 2224.52f));
        //Helium-3
        daoParticles.createOrUpdate(new ParticleDB(1, 2, new int[]{2,0,0,0,0,0,0}, "Helium-3", "He", 3.016f, 7718.1f));
        //Helium-4
        daoParticles.createOrUpdate(new ParticleDB(2, 2, new int[]{2,0,0,0,0,0,0}, "Helium", "He", 4.003f, 28300.7f));
        //Lithium-6
        daoParticles.createOrUpdate(new ParticleDB(3, 3, new int[]{2,1,0,0,0,0,0}, "Lithium-6", "Li", 6.015f, 31994.8f));
        //Lithium-7
        daoParticles.createOrUpdate(new ParticleDB(4, 3, new int[]{2,1,0,0,0,0,0}, "Lithium", "Li", 7.016f, 39245.1f));
        //Beryllium
        daoParticles.createOrUpdate(new ParticleDB(5, 4, new int[]{2,2,0,0,0,0,0}, "Beryllium", "Be", 9.012f, 58130.0f));
        //Boron-10
        daoParticles.createOrUpdate(new ParticleDB(5, 5, new int[]{2,3,0,0,0,0,0}, "Boron-10", "B", 0, 	64752.7f));
        //Boron-11
        daoParticles.createOrUpdate(new ParticleDB(6, 5, new int[]{2,3,0,0,0,0,0}, "Boron-11", "B", 0, 76206.3f));
        //Carbon-12
        daoParticles.createOrUpdate(new ParticleDB(6, 6, new int[]{2,4,0,0,0,0,0}, "Carbon", "C", 12.009f, 92161.753f));
        //Carbon-13
        daoParticles.createOrUpdate(new ParticleDB(7, 6, new int[]{2,4,0,0,0,0,0}, "Carbon-13", "C", 13.003f, 	97109.8f));
        //Nitrogen-14
        daoParticles.createOrUpdate(new ParticleDB(7, 7, new int[]{2,5,0,0,0,0,0}, "Nitrogen", "N", 14.007f, 104660.8f));
        //Nitrogen-15
        daoParticles.createOrUpdate(new ParticleDB(8, 7, new int[]{2,5,0,0,0,0,0}, "Nitrogen-15", "N", 0, 	115494.3f));
        //Oxygen-16
        daoParticles.createOrUpdate(new ParticleDB(8, 8, new int[]{2,6,0,0,0,0,0}, "Oxygen", "O", 15.994f, 127621.3f));
        //Oxygen-17
        daoParticles.createOrUpdate(new ParticleDB(9, 8, new int[]{2,6,0,0,0,0,0}, "Oxygen-17", "O", 15.994f, 	131764.7f));
        //Oxygen-18
        daoParticles.createOrUpdate(new ParticleDB(10, 8, new int[]{2,6,0,0,0,0,0}, "Oxygen-18", "O", 0, 139808.4f));
        //Fluorine
        daoParticles.createOrUpdate(new ParticleDB(10, 9, new int[]{2,7,0,0,0,0,0}, "Fluorine", "F", 18.998f, 147803.4f));
        //Neon-20
        daoParticles.createOrUpdate(new ParticleDB(10, 10, new int[]{2,8,0,0,0,0,0}, "Neon-20", "Ne", 19.992f, 160647.3f));
        //Neon-21
        daoParticles.createOrUpdate(new ParticleDB(11, 10, new int[]{2,8,0,0,0,0,0}, "Neon-21", "Ne", 20.994f, 	167408.9f));
        //Neon-22
        daoParticles.createOrUpdate(new ParticleDB(12, 10, new int[]{2,8,0,0,0,0,0}, "Neon-22", "Ne", 21.991f, 	177772.5f));
        //Sodium
        daoParticles.createOrUpdate(new ParticleDB(11, 11, new int[]{2,8,1,0,0,0,0}, "Sodium", "Na", 22.989f, 174148.0f));
        //Magnesium-24
        daoParticles.createOrUpdate(new ParticleDB(12, 12, new int[]{2,8,2,0,0,0,0}, "Magnesium", "Mg", 23.985f, 198259.5f));
        //Magnesium-25
        daoParticles.createOrUpdate(new ParticleDB(13, 12, new int[]{2,8,2,0,0,0,0}, "Magnesium", "Mg", 24.986f, 	205591.0f));
        //Magnesium-26
        daoParticles.createOrUpdate(new ParticleDB(14, 12, new int[]{2,8,2,0,0,0,0}, "Magnesium", "Mg", 25.983f, 216684.6f));
        //Aluminium
        daoParticles.createOrUpdate(new ParticleDB(14, 13, new int[]{2,8,3,0,0,0,0}, "Aluminium", "Al", 26.981f, 224954.6f));
        //Silicon-28
        daoParticles.createOrUpdate(new ParticleDB(14, 14, new int[]{2,8,4,0,0,0,0}, "Silicon", "Si", 27.977f, 236540.6f));
        //Silicon-29
        daoParticles.createOrUpdate(new ParticleDB(15, 14, new int[]{2,8,4,0,0,0,0}, "Silicon-29", "Si", 28.976f, 	245014.1f));
        //Silicon-30
        daoParticles.createOrUpdate(new ParticleDB(16, 14, new int[]{2,8,4,0,0,0,0}, "Silicon-30", "Si", 29.974f, 	255623.6f));
        //Phosphorus
        daoParticles.createOrUpdate(new ParticleDB(16, 15, new int[]{2,8,5,0,0,0,0}, "Phosphorus", "P", 30.974f, 262919.7f));
        //Sulfur-32
        daoParticles.createOrUpdate(new ParticleDB(16, 16, new int[]{2,8,6,0,0,0,0}, "Sulfur", "S", 31.972f, 	271783.7f));
        //Sulfur-33
        daoParticles.createOrUpdate(new ParticleDB(17, 16, new int[]{2,8,6,0,0,0,0}, "Sulfur-33", "S", 32.971f, 280426.2f));
        //Sulfur-34
        daoParticles.createOrUpdate(new ParticleDB(18, 16, new int[]{2,8,6,0,0,0,0}, "Sulfur-34", "S", 33.968f, 	291842.7f));
        //Chlorine-35
        daoParticles.createOrUpdate(new ParticleDB(18, 17, new int[]{2,8,7,0,0,0,0}, "Chlorine", "Cl", 34.969f, 	298214.2f));
        //Chlorine-37
        daoParticles.createOrUpdate(new ParticleDB(20, 17, new int[]{2,8,7,0,0,0,0}, "Chlorine-37", "Cl", 36.966f, 	317105.8f));
        //Argon-36
        daoParticles.createOrUpdate(new ParticleDB(18, 18, new int[]{2,8,8,0,0,0,0}, "Argon-36", "Ar", 36.966f, 306720.8f));
        //Argon-38
        daoParticles.createOrUpdate(new ParticleDB(20, 18, new int[]{2,8,8,0,0,0,0}, "Argon-38", "Ar", 36.966f, 327346.8f));
        //Argon-40
        daoParticles.createOrUpdate(new ParticleDB(22, 18, new int[]{2,8,8,0,0,0,0}, "Argon", "Ar", 36.966f, 	343815.8f));
        //http://cdfe.sinp.msu.ru/services/calc_thr/calc_thr.html
    }
}

