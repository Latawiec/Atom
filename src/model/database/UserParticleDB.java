package model.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Latawiec on 26/05/2017.
 */

@DatabaseTable(tableName = "usersParticles")
public class UserParticleDB {

    @DatabaseField(generatedId = true, canBeNull = false)
    private int id;

    @DatabaseField(foreign = true)
    ParticleDB particleTempalte;
    public ParticleDB getParticleTemplate(){ return particleTempalte; }

    @DatabaseField(canBeNull = false)
    private float energy;
    public float getEnergy(){ return energy; }

    @DatabaseField(canBeNull = false, foreign = true)
    UserDB owner;

    public UserParticleDB(){

    }

    public UserParticleDB(UserDB user, ParticleDB particle, float energy){
        this.owner = user;
        this.particleTempalte = particle;
        this.energy = energy;
    }
}
