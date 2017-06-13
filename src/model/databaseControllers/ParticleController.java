package model.databaseControllers;

import javafx.beans.property.*;
import model.database.ParticleDB;
import model.database.UserDB;
import model.database.UserParticleDB;
import obv.particle.ParticlesContainer;

import java.sql.SQLException;

/**
 * Created by Latawiec on 26/05/2017.
 */
public class ParticleController implements DatabaseSource{

    private UserParticleDB sourceDB;
    public UserParticleDB getSourceDB(){ return sourceDB; }

    private IntegerProperty protons = new SimpleIntegerProperty();
    public int getProtons(){ return protons.get(); }
    public void setProtons(int value){ protons.set(value); }
    public IntegerProperty getProtonsProperty(){ return protons; }

    private IntegerProperty neutrons = new SimpleIntegerProperty();
    public int getNeutrons(){ return neutrons.get(); }
    public void setNeutrons(int value){ neutrons.set(value); }
    public IntegerProperty getNeutronsProperty(){ return neutrons; }

    public int getNucleons(){ return getProtons() + getNeutrons(); }

    private IntegerProperty[] electrons = new IntegerProperty[7];
    public int getElectrons(int index){ return electrons[index].get(); }
    public int[] getElectronsArray(){ int[] result = new int[]{0,0,0,0,0,0,0}; for(int i=0; i<result.length; i++){ result[i] = electrons[i].get(); }; return result; }
    public void setElectrons(int index, int value){ electrons[index].set(value); }
    public void setElectronsArray(int[] value){ for(int i=0; i<value.length; i++){ electrons[i].set(value[i]); }}
    public IntegerProperty getElectronsProperty(int index){ return electrons[index]; }
    public IntegerProperty[] getElectronsPropertiesArray(){ return electrons; }

    private StringProperty name = new SimpleStringProperty();
    public String getName(){ return name.get(); }
    public void setName(String value){ name.set(value); }
    public StringProperty getNameProperty(){ return name; }

    private StringProperty nameTag = new SimpleStringProperty();
    public String getNameTag(){ return nameTag.get(); }
    public void setNameTag(String value){ nameTag.set(value); }
    public StringProperty getNameTagProperty(){ return nameTag; }

    private FloatProperty bindingEnergy = new SimpleFloatProperty();
    public float getBindingEnergy(){ return bindingEnergy.get(); }
    public void setBindingEnergy(float value) { bindingEnergy.set(value); }
    public FloatProperty getBindingEnergyProperty(){ return bindingEnergy; }

    private FloatProperty energy = new SimpleFloatProperty();
    public float getEnergy(){ return energy.get(); }
    public void setEnergy(float value){
        if(value >= bindingEnergy.get()){
            energy.setValue(bindingEnergy.get());
            setEnergized(true);
        }else{
            energy.set(value);
            setEnergized(false);
        }
    }
    public FloatProperty getEnergyProperty(){ return energy; }

    private BooleanProperty energized = new SimpleBooleanProperty();
    public boolean isEnergized(){ return energized.get(); }
    public void setEnergized(boolean value){ energized.set(value); }
    public BooleanProperty getEnergizedProperty(){ return energized; }

    private FloatProperty mass = new SimpleFloatProperty();
    public float getMass(){ return mass.get(); }
    public void setMass(float value){ mass.set(value);}
    public FloatProperty getMassProperty(){ return mass; }

    public ParticleController(ParticleDB db){
        this.sourceDB = null;
        setProtons(db.getProtons());
        setNeutrons(db.getNeutrons());
        setName(db.getName());
        setNameTag(db.getNameTag());
        setEnergized(false);
        setBindingEnergy(db.getBindingEnergy());
        setMass(db.getMass());
        for(int i=0; i<7; i++){
            electrons[i] = new SimpleIntegerProperty(db.getElectrons()[i]);
        }
    }

    public ParticleController(UserParticleDB db){
        this.sourceDB = db;
        setProtons(sourceDB.getParticleTemplate().getProtons());
        setNeutrons(sourceDB.getParticleTemplate().getNeutrons());
        setName(sourceDB.getParticleTemplate().getName());
        setNameTag(sourceDB.getParticleTemplate().getNameTag());
        setEnergized(false);
        setBindingEnergy(sourceDB.getParticleTemplate().getBindingEnergy());
        setEnergy(sourceDB.getEnergy());
        setMass(sourceDB.getParticleTemplate().getMass());
        for(int i=0; i<7; i++){
            electrons[i] = new SimpleIntegerProperty(sourceDB.getParticleTemplate().getElectrons()[i]);
        }
    }

    public void updateEnergy(){
    	getSourceDB().setEnergy(getEnergy());
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
