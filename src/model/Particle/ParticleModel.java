package model.Particle;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
	
	
	
	/*private String nameTag;
	private String name;
	private double energy;
	private int neutronsNumber;
	private int protonsNumber;
	private int[] electronsNumber;
	
	public int getNeutronsNumber() {
		return neutronsNumber;
	}
	public void setNeutrons(int number) {
		this.neutronsNumber = number;
	}
	public int getProtonsNumber() {
		return protonsNumber;
	}
	public void setProtonsNumber(int protonsNumber) {
		this.protonsNumber = protonsNumber;
	}
	public int[] getElectronsNumber() {
		return electronsNumber;
	}
	public void setElectronsNumber(int[] electronsNumber) {
		this.electronsNumber = electronsNumber;
	}*/	
}
