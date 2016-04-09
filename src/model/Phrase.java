package model;

public class Phrase {
	
	private Measure[] measures;

	public Phrase(Measure[] measures) {
		this.measures = measures;
	}

	public Measure[] getMeasures() {
		return measures;
	}

	public void setMeasures(Measure[] measures) {
		this.measures = measures;
	}
}
