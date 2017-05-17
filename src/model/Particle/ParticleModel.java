package model.Particle;

public class ParticleModel {
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
	}	
}
