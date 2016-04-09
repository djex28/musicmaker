package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.google.gson.Gson;

import model.Measure;
import model.Note;
import model.NoteValue;
import model.Phrase;
import model.Section;
import model.Song;
import model.TimeSignature;

public class MusicMaker {
	
	private final static String SONG_FILE_PATH = "songs/";
	private final static Random random = new Random();
	
	public Song song;
	public Section currentSection = null;
	public Phrase currentPhrase = null;
	public Measure currentMeasure = null; //relative to the phrase

	public MusicMaker(String title, String author) {
		this.song = generateBaseSong(title, author);
		this.currentSection = song.getSections()[0];
		this.currentPhrase = song.getSections()[0].getPhrases()[0];
		this.currentMeasure = song.getSections()[0].getPhrases()[0].getMeasures()[0];
	}
	
	public Song getSong() {
		return song;
	}

	public void setSong(Song currentSong) {
		this.song = currentSong;
	}
	
	public Section getCurrentSection() {
		return currentSection;
	}

	public void setCurrentSection(Section currentSection) {
		this.currentSection = currentSection;
	}
	
	public Phrase getCurrentPhrase() {
		return currentPhrase;
	}

	public void setCurrentPhrase(Phrase currentPhrase) {
		this.currentPhrase = currentPhrase;
	}
	
	public Measure getCurrentMeasure() {
		return currentMeasure;
	}

	public void setCurrentMeasure(Measure currentMeasure) {
		this.currentMeasure = currentMeasure;
	}
	
	public void save() {
		Gson gson = new Gson();
		String json = gson.toJson(getSong());

		try {
			//write converted json data to a file named "file.json"
			FileWriter writer = new FileWriter(SONG_FILE_PATH+getSong().getTitle()+".mm");
			writer.write(json);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void load() {
		Gson gson = new Gson();

		try {
			BufferedReader br = new BufferedReader(
				new FileReader(SONG_FILE_PATH+getSong().getTitle()+".mm"));

			//convert the json string back to object
			Song obj = gson.fromJson(br, Song.class);
			
			this.song = obj;
			this.currentSection = song.getSections()[0];
			this.currentPhrase = song.getSections()[0].getPhrases()[0];
			this.currentMeasure = song.getSections()[0].getPhrases()[0].getMeasures()[1];
			
			//System.out.println(obj.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Song generateBaseSong(String title, String author) {
		Note[] notes = new Note[16];
		Note[] bass = new Note[16];
		Phrase[] phrases = new Phrase[1];
		Measure[] measures = new Measure[1];
		Section[] sections = new Section[1];
		int count = 1;
		
		notes[0] = new Note(NoteValue.C, 4);
		for (int i=1; i<4; i++) {
			notes[i] = new Note(NoteValue.Fill, i); //the fill value will tell you how far away you are from previous note.
		}
		notes[4] = new Note(NoteValue.C, 4);
		for (int i=5; i<8; i++) {
			notes[i] = new Note(NoteValue.Fill, count);
			count++;
		}
		count = 1;
		notes[8] = new Note(NoteValue.C, 4);
		for (int i=9; i<12; i++) {
			notes[i] = new Note(NoteValue.Fill, count);
			count++;
		}
		count = 1;
		notes[12] = new Note(NoteValue.C, 4);
		for (int i=13; i<16; i++) {
			notes[i] = new Note(NoteValue.Fill, count);
			count++;
		}
		bass[0] = new Note(NoteValue.C, 16);
		measures[0] = new Measure(notes, 16, bass);
		phrases[0] = new Phrase(measures);
		sections[0] = new Section(phrases, NoteValue.C, TimeSignature.FOUR_FOUR);
		
		return new Song(sections, title, author);
	}
	
	public void addRhythm() {
		int random = getRandomInactiveRhythm();
		if (getCurrentMeasure().getNotes()[random] != null) {
			//it's a filler, I need to also update the last note value length
			int goBack = getCurrentMeasure().getNotes()[random].getLength();
			getCurrentMeasure().getNotes()[random - goBack].setLength(goBack);
		}
		addNote(getCurrentSection().getKey(), random, 1);
	}
	
	public void developRhythm() {		
		removeNote(getRandomActiveRhythm());
		addRhythm();
		//move it one to either side. If none available, call developRhythm() again... not yet implemented like this
	}
	
	public void revertRhythm() {
		
	}
	
	public void refactorRhythm(int power) { //measure
		
	}
	
	public void developRandomNote() {
		int random = getRandomActiveRhythm();
		if (getCurrentMeasure().getNotes()[random] != null) {
			NoteValue rand = randomEnum(NoteValue.class);
			if (!rand.equals(NoteValue.Fill)) {
				getCurrentMeasure().getNotes()[random].setValue(rand);
			} else {
				developRandomNote();
			}
		}
	}
	
	public void developNoteInKey() {
		int random = getRandomActiveRhythm();
		if (getCurrentMeasure().getNotes()[random] != null) {
			NoteValue rand = getCurrentSection().getListOfKeys().getKeyofC()[randInt(0, 6)];
			getCurrentMeasure().getNotes()[random].setValue(rand);
		}
	}
	
	public void makeRandomNoteC() {
		int random = getRandomActiveRhythm();
		if (getCurrentMeasure().getNotes()[random] != null) {
			NoteValue rand = getCurrentSection().getListOfKeys().getKeyofC()[randInt(0, 0)];
			getCurrentMeasure().getNotes()[random].setValue(rand);
		}
	}
	
	public void copyMeasure(Measure measure) { 
		addMeasure();
		getCurrentPhrase().getMeasures()[getCurrentPhrase().getMeasures().length-1] = measure.getClone(); //deep clone
	}
	
	public void repeatLastMeasure() {
		copyMeasure(getCurrentPhrase().getMeasures()[getCurrentPhrase().getMeasures().length-1]);
		setCurrentMeasure(getCurrentPhrase().getMeasures()[getCurrentPhrase().getMeasures().length-1]);
	}
	
	public void repeatLastMeasureRhythm() {
		repeatLastMeasure();
		for (int i=0; i<getCurrentMeasure().getNotes().length; i++) {
			developNoteInKey(); 
		}
	}
	
	public void revertNote() {
		
	}
	
	public void gotoPhrase(int position) {
		
	}
	
	public boolean addPhrase() {
		return true;
	}
	
	public boolean addMeasure() { //adds measure to end of current phrase
		ArrayList<Measure> list = new ArrayList<Measure>(Arrays.asList(getCurrentPhrase().getMeasures()));
		list.add(new Measure(new Note[16], 16, new Note[16]));
		Measure[] measures = new Measure[list.size()];
		getCurrentPhrase().setMeasures(list.toArray(measures));
		setCurrentMeasure(getCurrentPhrase().getMeasures()[getCurrentPhrase().getMeasures().length-1]);
		return true;
	}
	
	public boolean addNote(NoteValue note, int rhythm, int length) {
		for (int i=rhythm; i<rhythm+length-1; i++) {
			if (getCurrentMeasure().getNotes()[i] != null && getCurrentMeasure().getNotes()[i].getValue() != NoteValue.Fill)
				return false;
		}
		
		getCurrentMeasure().getNotes()[rhythm] = new Note(note, length);
		int count = 1;
		for (int i=rhythm+1; i<rhythm+length; i++) {
			getCurrentMeasure().getNotes()[i] = new Note(NoteValue.Fill, count); //Fill means it's a continuation of a previously played note
			count++;
		}
		
		nullify(rhythm+length);
		
		return true;
	}
	
	public boolean removeNote(int rhythm) {
		if (getCurrentMeasure().getNotes()[rhythm] != null && getCurrentMeasure().getNotes()[rhythm].getValue() != NoteValue.Fill) {
			nullify(rhythm+1);
			getCurrentMeasure().getNotes()[rhythm] = null;
		} else {
			return false;
		}
		
		return true;
	}
	
	public boolean addChord(NoteValue[] notes, int rhythm, int length) {
		return true;
	}
	
	public void nullify(int rhythm) {
		if (rhythm < getCurrentMeasure().getSize()) {
			if (getCurrentMeasure().getNotes()[rhythm] != null && getCurrentMeasure().getNotes()[rhythm].getValue() == NoteValue.Fill) {
				getCurrentMeasure().getNotes()[rhythm] = null;
				nullify(rhythm+1);
			}
		}
	}
	
	public int getRandomInactiveRhythm() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i=0; i<getCurrentMeasure().getSize(); i++) {
			if (getCurrentMeasure().getNotes()[i] == null || getCurrentMeasure().getNotes()[i].getValue() == NoteValue.Fill) {
				list.add(i);
			}
		}
		int ret = list.get(randInt(0, list.size()-1));
		//System.out.println(ret);
		return ret;
	}
	
	public int getRandomActiveRhythm() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i=0; i<getCurrentMeasure().getSize(); i++) {
			if (getCurrentMeasure().getNotes()[i] != null && getCurrentMeasure().getNotes()[i].getValue() != NoteValue.Fill) {
				list.add(i);
			}
		}
		return list.get(randInt(0, list.size()-1));
	}
	
	public int getRandomRhythm() {
		return randInt(0, getCurrentMeasure().getSize()-1);
	}
	
	public static int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
	public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

}
