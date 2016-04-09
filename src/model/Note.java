package model;

public class Note {
	
	private NoteValue value;
	//public int rhythm;
	private int length;
	
	public Note(NoteValue value, int length) {
		this.value = value;
		this.length = length;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Note other = (Note) obj;
		if (length != other.length)
			return false;
		return true;
	}

	public Note(Note note) {
		this(note.getValue(), note.getLength());
	}

	public NoteValue getValue() {
		return value;
	}
	public void setValue(NoteValue value) {
		this.value = value;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}

}
