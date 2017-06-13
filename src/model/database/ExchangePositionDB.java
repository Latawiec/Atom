package model.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import model.databaseControllers.DatabaseAccessor;

/**
 * Created by Latawiec on 13/06/2017.
 */
@DatabaseTable(tableName = "exchanges")
public class ExchangePositionDB {


    @DatabaseField(generatedId = true, canBeNull = false)
    private int id;
    public int getId(){return id;};

    @DatabaseField(canBeNull = false, foreignAutoRefresh = true, foreign = true)
    private ParticleDB particle;
    public ParticleDB getParticle(){ return particle; }

    @DatabaseField(canBeNull = false)
    private float energyCost;
    public float getEnergyCost(){ return energyCost; }

    public ExchangePositionDB(){

    }

    public ExchangePositionDB(ParticleDB particle, float energyCost){
        this.particle = particle;
        this.energyCost = energyCost;
    }
}
