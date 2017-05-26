package model.databaseControllers;

import javafx.beans.property.*;
import model.database.UserParticleDB;

import java.sql.SQLException;

/**
 * Created by Latawiec on 26/05/2017.
 */
public class ParticleController implements DatabaseSource{

    private UserParticleDB sourceDB;

    private IntegerProperty protons;
    public int getProtons(){ return protons.get(); }
    public void setProtons(int value){ protons.set(value); }
    public IntegerProperty getProtonsProperty(){ return protons; }

    private IntegerProperty neutrons;
    public int getNeutrons(){ return neutrons.get(); }
    public void setNeutrons(int value){ protons.set(value); }
    public IntegerProperty getNeutronsProperty(){ return neutrons; }

    private IntegerProperty[] electrons = new IntegerProperty[7];
    public int getElectrons(int index){ return electrons[index].get(); }
    public int[] getElectronsArray(){ int[] result = new int[]{0,0,0,0,0,0,0}; for(int i=0; i<result.length; i++){ result[i] = electrons[i].get(); }; return result; }
    public void setElectrons(int index, int value){ electrons[index].set(value); }
    public void setElectronsArray(int[] value){ for(int i=0; i<value.length; i++){ electrons[i].set(value[i]); }}
    public IntegerProperty getElectronsProperty(int index){ return electrons[index]; }
    public IntegerProperty[] getElectronsPropertiesArray(){ return electrons; }

    private StringProperty name;
    public String getName(){ return name.get(); }
    public void setName(String value){ name.set(value); }
    public StringProperty getNameProperty(){ return name; }

    private StringProperty nameTag;
    public String getNameTag(){ return nameTag.get(); }
    public void setNameTag(String value){ nameTag.set(value); }
    public StringProperty getNameTagProperty(){ return nameTag; }

    private FloatProperty bindingEnergy;
    public float getBindingEnergy(){ return bindingEnergy.get(); }
    public void setBindingEnergy(float value) { bindingEnergy.set(value); }
    public FloatProperty getBindingEnergyProperty(){ return bindingEnergy; }

    private FloatProperty energy;
    public float getEnergy(){ return energy.get(); }
    public void setEnergy(float value){ energy.set(value); }
    public FloatProperty getEnergyProperty(){ return energy; }

    public ParticleController(UserParticleDB db){
        this.sourceDB = db;
        protons = new SimpleIntegerProperty(sourceDB.getParticleTemplate().getProtons());
        neutrons = new SimpleIntegerProperty(sourceDB.getParticleTemplate().getNeutrons());
        name = new SimpleStringProperty(sourceDB.getParticleTemplate().getName());
        nameTag = new SimpleStringProperty(sourceDB.getParticleTemplate().getNameTag());
        bindingEnergy = new SimpleFloatProperty(sourceDB.getParticleTemplate().getBindingEnergy());
        energy = new SimpleFloatProperty(sourceDB.getEnergy());
        for(int i=0; i<7; i++){
            electrons[i] = new SimpleIntegerProperty(sourceDB.getParticleTemplate().getElectrons()[i]);
        }
    }

    @Override
    public void save() {
        try {
            DatabaseAccessor.Save(sourceDB);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
