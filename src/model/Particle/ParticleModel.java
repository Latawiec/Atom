package model.Particle;

import java.util.Arrays;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ParticleModel {
	
	//Name Tag Property (H, Br, etc.)
	private StringProperty nameTag = new SimpleStringProperty();
	public void setNameTag(String value){ nameTag.set(value); }
	public String getNameTag() { return nameTag.get(); }
	public StringProperty getNameTagProperty(){ return nameTag; }
	
	//Name Property (Hydrogen, Deuterium etc.)
	private StringProperty name = new SimpleStringProperty();
	public void setName(String value){ name.set(value); }
	public String getName() { return name.get(); }
	public StringProperty getNameProperty(){ return name; }
	
	//Energy Property
	private DoubleProperty energy = new SimpleDoubleProperty();
	public void setEnergy(double value){ energy.set(value); }
	public double getEnergy() { return energy.get(); }
	public DoubleProperty getEnergyProperty(){ return energy; }
	
	//Mass Property
	private FloatProperty mass = new SimpleFloatProperty();
	public void setMass(float value){ mass.set(value); }
	public float getMass(){ return mass.get(); }
	public FloatProperty getMassProperty(){ return mass; }
	
	//Neutrons number
	private IntegerProperty neutrons = new SimpleIntegerProperty();
	public void setNeutrons(int value){ neutrons.set(value); }
	public int getNeutrons(){ return neutrons.get(); }
	public IntegerProperty getNeutronsProperty(){ return neutrons; }
	
	//Protons number
	private IntegerProperty protons = new SimpleIntegerProperty();
	public void setProtons(int value){ protons.set(value); }
	public int getProtons(){ return protons.get(); }
	public IntegerProperty getProtonsProperty(){ return protons; }
	
	//Electron shells array
	private IntegerProperty[] electrons = new IntegerProperty[7];
	public void setElectronsArray(int[] value) { 
		for(int i=0; i<value.length; i++){
			electrons[i].set(value[i]);
			}
		}
	public void setElectrons(int index, int value) { electrons[index].set(value); }
	public int getElectrons(int index) { return electrons[index].get(); }
	public IntegerProperty[] getElectronsPropertiesArray(){ return electrons; }
	public IntegerProperty getElectronsProperty(int index){ return electrons[index]; }
	
	public ParticleModel()
	{
		for(int i=0; i<7; i++){
			electrons[i] = new SimpleIntegerProperty(0);
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
