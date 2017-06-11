package model.databaseControllers;

import com.j256.ormlite.stmt.DeleteBuilder;
import javafx.beans.property.*;
import model.database.UserDB;
import model.database.UserParticleDB;

import javax.jws.soap.SOAPBinding;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Latawiec on 26/05/2017.
 */
public class UserController implements DatabaseSource {

    private UserDB sourceDB;
    public UserDB getSourceDB(){ return sourceDB ;}

    private StringProperty username;
    public String getUsername(){ return username.get(); }
    public void setUsername(String value){ username.set(value); }
    public StringProperty getUsernameProperty(){ return username; }

    private StringProperty password;
    public String getPassword(){ return password.get(); }
    public void setPassword(String value){ password.set(value); }
    public StringProperty getPasswordProperty(){ return password; }

    private FloatProperty energy;
    public float getEnergy(){ return energy.get(); }
    public void setEnergy(float value){ energy.set(value); }
    public FloatProperty getEnergyProperty(){ return energy; }

    private IntegerProperty level;
    public int getLevel(){ return level.get(); }
    public void setLevel(int value){ level.set(value); }
    public IntegerProperty getLevelProperty(){ return level; }

    private ArrayList<Byte> unlockedParticles;
    public void unlockParticle(byte number){
        boolean contains = false;
        for(Byte val : unlockedParticles) {
            if(val == number){
                contains = true;
                return;
            }
        }
        unlockedParticles.add(number);
    }

    private ArrayList<ParticleController> particles;
    public ParticleController getParticle(int index){ return particles.get(index); }
    public ArrayList<ParticleController> getParticlesArray(){ return particles; }
    public void setParticlesArray(ArrayList<ParticleController> value){ particles = value; }
    public int getParticlesCount(){ return particles.size(); }

    public UserController(UserDB source){
        this.sourceDB = source;

        username = new SimpleStringProperty(source.getUsername());
        password = new SimpleStringProperty(source.getPassword());
        energy = new SimpleFloatProperty(source.getEnergy());
        level = new SimpleIntegerProperty(source.getLevel());
        unlockedParticles = new ArrayList<Byte>();
        particles = new ArrayList<ParticleController>();
        loadParticles();
        loadUnlockedParticles();
    }

    private void loadUnlockedParticles(){
        for(byte val : sourceDB.getUnlockedParticles()){
            unlockedParticles.add(val);
        }
    }

    private void loadParticles(){
        ArrayList<UserParticleDB> result = new ArrayList<UserParticleDB>();
        try {
            result = (ArrayList<UserParticleDB>) DatabaseAccessor.getInstance().getUserParticles(sourceDB);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(UserParticleDB p : result){
            particles.add(new ParticleController(p));
        }
    }

    private byte[] unlockedParticlesToArray(){
        byte[] result = new byte[unlockedParticles.size()];
        for(int i=0; i< unlockedParticles.size(); i++){
            result[i] = unlockedParticles.get(i);
        }
        return result;
    }

    @Override
    public void save() {
        sourceDB.setEnergy(getEnergy());
        sourceDB.setLevel(getLevel());
        sourceDB.setUnlockedParticles(unlockedParticlesToArray());
        saveParticles();
        try {
            DatabaseAccessor.getInstance().Save(sourceDB);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveParticles(){
        try {
            DatabaseAccessor.getInstance().deleteUserParticles(sourceDB);
            DatabaseAccessor.getInstance().updateUserParticles(particles);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
