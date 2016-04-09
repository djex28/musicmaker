package model;

public class Measure {
	
	private Note[] notes;
	private int size;
	private int power; //default 4 (2^4 = 16, leaving room for 1/16th notes)
	private Note[] bass;
	
	public Measure(Note[] notes, int size, Note[] bass) {
		this.notes = notes;
		this.size = size;
		this.power = 4;
		this.bass = bass;
	}
	
	public Measure getClone() {
		Note[] notes = new Note[this.getNotes().length];
    	for (int i=0; i<notes.length; i++) {
    		if (this.getNotes()[i] != null)
    			notes[i] = new Note(this.getNotes()[i]);
    	}
    	Note[] bass = new Note[this.getBass().length];
    	for (int i=0; i<bass.length; i++) {
    		if (this.getBass()[i] != null)
    			bass[i] = new Note(this.getBass()[i]);
    	}
    	return new Measure(notes, this.getSize(), bass);
	}
	
	/**
    * Copy constructor.
    */
    public Measure(Measure measure) {
        this(measure.getNotes(), measure.getSize(), measure.getBass());
    }
    
	public Note[] getNotes() {
		return notes;
	}
	public void setNotes(Note[] notes) {
		this.notes = notes;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}
	public Note[] getBass() {
		return bass;
	}
	public void setBass(Note[] bass) {
		this.bass = bass;
	}

}
