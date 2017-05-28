package model.database;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Latawiec on 26/05/2017.
 */

@DatabaseTable(tableName = "particles")
public class ParticleDB {

    @DatabaseField(generatedId = true, canBeNull = false)
    private int id;
    public int getId(){return id;};

    @DatabaseField( canBeNull = false)
    private int protons;
    public int getProtons(){ return protons; }
    public void setProtons(int value){ protons = value; }

    @DatabaseField(canBeNull = false)
    private int neutrons;
    public int getNeutrons(){ return neutrons; }

    @DatabaseField(canBeNull = false)
    private int nucleons;
    public int getNucleons(){ return nucleons; }

    @DatabaseField(canBeNull = false)
    private String name;
    public String getName(){ return name; }

    @DatabaseField(canBeNull = false)
    private String nameTag;
    public String getNameTag(){ return nameTag; }

    @DatabaseField(canBeNull = false)
    private float mass;
    public float getMass(){ return mass; }

    @DatabaseField(canBeNull = false)
    private float bindingEnergy;
    public float getBindingEnergy() { return bindingEnergy; }

    @DatabaseField(canBeNull = false, dataType = DataType.SERIALIZABLE)
    private int[] electrons = new int[7];
    public int[] getElectrons(){ return electrons; }

    public ParticleDB(){

    }

    public ParticleDB(int neutrons, int protons, int[] electrons, String name, String nameTag, float mass, float bindingEnergy){
        this(neutrons, protons, electrons);
        this.name = name;
        this.nameTag = nameTag;
        this.mass = mass;
        this.bindingEnergy = bindingEnergy;
    }

    public ParticleDB(int neutrons, int protons, int[] electrons){
        this.neutrons = neutrons;
        this.protons = protons;
        this.electrons = electrons;
        this.nucleons = neutrons + protons;
    }
}
