package model;

public class Section {
	
	private Phrase[] phrases;
	private NoteValue key;
	private TimeSignature timeSig;
	private Key listOfKeys;
	
	public Section(Phrase[] phrases, NoteValue key, TimeSignature timeSig) {
		this.phrases = phrases;
		this.key = key;
		this.timeSig = timeSig;
		this.listOfKeys = new Key();
	}
	public Phrase[] getPhrases() {
		return phrases;
	}
	public void setPhrases(Phrase[] phrases) {
		this.phrases = phrases;
	}
	public NoteValue getKey() {
		return key;
	}
	public void setKey(NoteValue key) {
		this.key = key;
	}
	public Key getListOfKeys() {
		return listOfKeys;
	}
	public void setListOfKeys(Key listOfKeys) {
		this.listOfKeys = listOfKeys;
	}
	public TimeSignature getTimeSig() {
		return timeSig;
	}
	public void setTimeSig(TimeSignature timeSig) {
		this.timeSig = timeSig;
	}

}
