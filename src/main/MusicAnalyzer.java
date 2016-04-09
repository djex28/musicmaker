package main;

import java.util.ArrayList;
import model.Note;
import model.Phrase;
import model.Section;
import model.Song;

public class MusicAnalyzer {
	
	private int score = 0;
	private ArrayList<ArrayList<Integer>> checked;
	
	public MusicAnalyzer() {
		this.checked = new ArrayList<ArrayList<Integer>>();
	}
	
	public int getScore() {
		return score;
	}

	public Phrase[] findPatterns(Phrase phrase) {
		Note[][] notes = breakPhrase(phrase); //break the phrase into beats
		for (int i=4; i<notes.length; i++) {
			for (int j=0; j<notes[i].length; j++) {
				Note[] comparable = findComparable(i, j, 1, notes);
				if (comparable != null) 
					loopPhrase(i, j, comparable, notes);
			}
		}
		
		return null;
	}
	
	public void loopPhrase(int i, int j, Note[] comparable, Note[][] notes) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(i);
		list.add(j);
		list.add(comparable.length); //use a three dimensional array die khap.
		if (!this.checked.contains(list)) {
			int scoreBefore = this.score;
			for (int k=i+1; k<notes.length; k++) { //k+1
				compareNotes(k, j, comparable, notes);
			}
			if (scoreBefore != this.score) {
				Note[] newArray = getPart(i, j, comparable.length+1, notes);
				loopPhrase(i, j, newArray, notes);
			} 
		}
	}
	
	public Note[] findComparable(int beatNum, int beatPos, int length, Note[][] notes) { //4 2 Math.floor(beatPos+length)/4 + beatNum
		if (beatNum + (int) Math.floor((beatPos+(length-1))/4) < notes.length) {
			if (notes[beatNum + (int) Math.floor((beatPos+(length-1))/4)][(beatPos+(length-1))%4] != null) { //4 2 with length 3 = [4][1] THIS IS THE ISSUE!!
				if (length == 1) {
					return getPart(beatNum, beatPos, length+1, notes);
				} else {
					return getPart(beatNum, beatPos, length, notes);
				}
			} else {
				return findComparable(beatNum, beatPos, length+1, notes);
			}
		} 

		return null;
	}
	
	public void compareNotes(int beatNum, int beatPos, Note[] comparable, Note[][] notes) { //each time I call this, i will want to simply increment the beatNum.
		//here's where I could do the add to the checked. - I also need to keep track of position in Note[][]!!!
		Note[] noteSection = getPart(beatNum, beatPos, comparable.length, notes); 
		if (isRhythmMatch(comparable, noteSection)) {
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(beatNum);
			list.add(beatPos);
			list.add(comparable.length);
			this.checked.add(list);
			this.score += comparable.length;
		}
	}
	
	public Note[] getPart(int beatNum, int beatPos, int length, Note[][] notes) {
		Note[] noteSection = new Note[length];
		for (int i=0; i<length; i++) {
			if (beatNum + (int) Math.floor((beatPos+i)/4) < notes.length) {
				noteSection[i] = notes[beatNum + (int) Math.floor((beatPos+i)/4)][((beatPos+i)%4)]; //beatNum originally in front
			} else {
				break;
			}
		}
		return noteSection;
	}
	
	public void printNoteArray(Note[] notes) {
		System.out.print("{");
		for (int i=0; i<notes.length; i++) {
			if (notes[i] != null) {
				System.out.print(notes[i].getValue()+", ");
			} else {
				System.out.print("NULL, ");
			}
			
		}
		System.out.print("}"+'\n');
	}
	
	public void findMatch(Note[] notes) {
		
	}
	
	public boolean isRhythmMatch(Note[] notes1, Note[] notes2) {
		for (int i=0; i<notes1.length; i++) {
			if ((notes1[i] != null && notes2[i] == null) || (notes2[i] != null && notes1[i] == null))
				return false;
		}
		return true;
	}
	
	public Note[][] breakPhrase(Phrase phrase) {
		Note[][] notes = new Note[phrase.getMeasures().length*4][4]; //[beat][position in beat] This 4 can change depending on time sig
		for (int i=0; i<phrase.getMeasures().length; i++) {
			for (int j=0; j<16; j++) {
				notes[(i*4)+(int) Math.floor(j/4)][j%4] = phrase.getMeasures()[i].getNotes()[j];
			}
		}
		return notes;
	}
	
	public void findRhythms() {
		
	}
	
	public int getNoteDistance() {
		return 0;
	}
	
	public Phrase getRhythmPattern() {
		return null;
	}
	
	public Phrase getNotePattern() {
		return null;
	}
	
	public void analyzePhrase(Phrase phrase) {
		findPatterns(phrase);
	}
	
	public void analyzeSection(Section section) {
		
	}

	public void analyzeSong(Song song) {
	
	}
}
