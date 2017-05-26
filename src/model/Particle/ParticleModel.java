package model.Particle;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "particles")
public class ParticleModel {

	@DatabaseField(generatedId = true)
	private int id;

	//Name Tag Property (H, Br, etc.)
	@DatabaseField
	private String nameTag;
	public final String getNameTag() { return nameTag; }
	public void setNameTag(String value) { nameTag = value; }
	
	//Name Property (Hydrogen, Deuterium etc.)
	@DatabaseField
	private String name;
	public final String getName() { return name; }
	public void setName(String value) { name = value; }

	//Energy Property
	@DatabaseField
	private Double energy;
	public final Double getEnergy() { return energy; }
	public void setEnergy(double value) { energy = value; }
	
	//Mass Property
	@DatabaseField
	private Float mass;
	public final Float getMass() { return mass; }
	public void setMass(float value) { mass = value; }
	
	//Neutrons number
	@DatabaseField
	private Integer neutrons;
	public final Integer getNeutrons() { return neutrons; }
	public void setNeutrons(int value) { neutrons = value; }
	
	//Protons number
	@DatabaseField
	private Integer protons;
	public final Integer getProtons(){ return protons; }
	public void setProtons(int value) { protons = value;}
	
	//Electron shells array
	@DatabaseField(dataType = DataType.SERIALIZABLE)
	private Integer[] electrons = new Integer[7];
	public void setElectronsArray(int[] value) { 
		for(int i=0; i<value.length; i++){
			electrons[i] = value[i];
			}
		}
	public void setElectrons(int index, int value) { electrons[index] = value; }
	public int getElectrons(int index) { return electrons[index]; }
	public Integer[] getElectronsPropertiesArray(){ return electrons; }
	public Integer getElectronsProperty(int index){ return electrons[index]; }
	
	public ParticleModel()	{
		for(int i=0; i<electrons.length; i++){
			electrons[i] = new Integer(0);
		}
	}
	public ParticleModel(int neutrons, int protons, int[] electrons){
		this();
		setNeutrons(neutrons);
		setProtons(protons);
		setElectronsArray(electrons);
	}
	public ParticleModel(int neutrons, int protons, int[] electrons, String name, String nameTag, float mass){
		this(neutrons, protons, electrons);
		setName(name);
		setNameTag(nameTag);
		setMass(mass);
	}
}
