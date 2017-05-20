package model.Particle;

import java.util.ArrayList;
import java.util.List;

public class UserdataModel {

	private List<ParticleModel> particles = new ArrayList<ParticleModel>();
	private String username;
	
	
	public List<ParticleModel> getParticles() { return particles; }
	public void setParticles(List<ParticleModel> particles) { this.particles = particles; }
	
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }
}
