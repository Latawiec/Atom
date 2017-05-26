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

    @DatabaseField(canBeNull = false)
    private String username;

    @DatabaseField(canBeNull = false)
    private String password;

    @DatabaseField
    private float energy;

    @DatabaseField(canBeNull = false, defaultValue = "0")
    private int level;

    @DatabaseField(dataType = DataType.DATE)
    private Date creationDate;

    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private byte[] unlockedParticles;

    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private byte[] unlockedTraits;

    public UserDB(String username, String password, int level) throws SQLException {
        this.username = username;
        this.password = password;
        this.level = level;
    }

    public UserDB(){

    }
}
