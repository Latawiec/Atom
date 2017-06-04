package model.database;

import com.j256.ormlite.dao.BaseForeignCollection;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.EagerForeignCollection;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.sun.xml.internal.bind.v2.model.core.ID;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 *
 * Created by Latawiec on 26/05/2017.
 */
@DatabaseTable(tableName = "users")
public class UserDB {

    @DatabaseField(generatedId = true)
    private int id;
    public int getID(){ return id; }

    @DatabaseField(canBeNull = false)
    private String username;
    public String getUsername(){ return username; }
    public void setUsername(String value){ username = value; }

    @DatabaseField(canBeNull = false)
    private String password;
    public String getPassword(){ return password; }
    public void setPassword(String value){ password = value; }

    @DatabaseField
    private float energy;
    public float getEnergy(){ return energy; }
    public void setEnergy(float value){ energy = value; }

    @DatabaseField(canBeNull = false, defaultValue = "0")
    private int level;
    public int getLevel(){ return level; }
    public void setLevel(int value){ level = value; }

    @DatabaseField(dataType = DataType.DATE)
    private Date creationDate;

    @DatabaseField
    private boolean accountType;
    public boolean getAccountType(){ return accountType; }

    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private byte[] unlockedParticles = new byte[]{};
    public byte[] getUnlockedParticles(){ return unlockedParticles; }
    public void setUnlockedParticles(byte[] values){ unlockedParticles = values; }

    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private byte[] unlockedTraits;

    public UserDB(String username, String password, int level) throws SQLException {
        this.username = username;
        this.password = password;
        this.level = level;
    }

    public UserDB(String username, String password, int level, boolean type) throws SQLException {
        this.username = username;
        this.password = password;
        this.level = level;
        this.accountType = true;
    }

    public UserDB(){

    }
}
