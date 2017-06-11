package model.Particle;

import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.annotation.Generated;


public class UserdataModel {

	public UserdataModel(){
		
	}

	private List<ParticleModel> particles = new ArrayList<ParticleModel>();
	private StringProperty username = new SimpleStringProperty();

	public List<ParticleModel> getParticles() { return particles; }
	public void setParticles(List<ParticleModel> particles) { this.particles = particles; }

	public String getUsername() { return username.get(); }
	public void setUsername(String value) { username.set(value); }
	
	public int getParticlesCount() { return particles.size(); }

}
