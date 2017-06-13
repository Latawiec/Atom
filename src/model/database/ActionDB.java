package model.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Latawiec on 11/06/2017.
 */

@DatabaseTable(tableName = "actions")
public class ActionDB {

    @DatabaseField(generatedId = true, canBeNull = false)
    private int id;
    public int getId(){return id;};

    @DatabaseField(canBeNull = false)
    private float energyCost;
    public float getEnergyCost(){ return energyCost; }

    @DatabaseField(canBeNull = false)
    private float energyIncome;
    public float getEnergyIncome(){ return energyIncome; }

    @DatabaseField(canBeNull = false)
    private String name;
    public String getName(){ return name; }

    public ActionDB(){

    }

    public ActionDB(String name, float energyCost, float energyIncome){
        this.name = name;
        this.energyCost = energyCost;
        this.energyIncome = energyIncome;
    }
}
