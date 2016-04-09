package main;

/*import java.io.File;
import java.io.IOException;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import model.Song;

import com.google.gson.Gson;

import javazoom.jl.decoder.Manager;
import javazoom.jl.player.Player;

import java.net.*;
import java.io.*;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;*/

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import model.Measure;
import model.Note;
import model.NoteValue;
import model.Phrase;
import model.Section;
import model.Song;

public class MusicPlayer {
	
	private final static String NOTE_FILE_PATH = "piano/";
	private final static String SOUND_FILE_PATH = "sounds/";
	private final static String HARRISON_FILE_PATH = "sounds/Harrison/";
	private final static String DREW_FILE_PATH = "sounds/Drew/";
	private static int Tempo = 120; //higher = slower, i can eventually fix that.
	private static int Timer = 0; //for 16 beats per measure - will be made so it's adjustable.
	private static Song song;
	
	private static boolean hitSnare = true;

	public MusicPlayer(Song song) {
		MusicPlayer.song = song;
	}
	
	public static int getTempo() {
		return Tempo;
	}

	public static void setTempo(int tempo) {
		Tempo = tempo;
	}

	public static Song getSong() {
		return song;
	}

	public static void setSong(Song song) {
		MusicPlayer.song = song;
	}
	
	public void play() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
		/*playMultiNote(NoteValue.C, 1);
		playMultiNote(NoteValue.E, 2);
		playMultiNote(NoteValue.G, 3);
		Thread.sleep(600);*/
		
		for (Section section : getSong().getSections()) {
			for (Phrase phrase: section.getPhrases()) {
				for (Measure measure: phrase.getMeasures()) {
					playMultiNote(NoteValue.C1, 16); //could be bass note and chord
					playMultiNote(NoteValue.C, 16);
					playMultiNote(NoteValue.E, 16);
					playMultiNote(NoteValue.G, 16);
					playMultiSound("lowbass2", 16);
					//playMultiSound("bassbg", 16);
					for (Note note: measure.getNotes()) {
						playMultiSound("highhat2", 16);
						if (note != null) { //currently, my format doesn't define multiple notes to be played at once...
							playNote(note.getValue(), note.getLength());
						} else {
							if (Timer > getTempo()) { 
								Timer -= getTempo();
								Thread.sleep(getTempo());
							} else {
								Thread.sleep(Timer);
								playMultiSound("bass", 16);
								if (hitSnare) {
									playMultiSound("snare", 16);
									hitSnare = false;
								} else {
									hitSnare = true;
								}
								//playMetranome(getTempo()-Timer);
								Timer = getTempo()*4;
							}
						}
					}
				}
			}
		}
		
		Thread.sleep(600);
		
	}
	
	public void playNote(NoteValue note, int length) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
		String noteFile = note.toString();
		if (!note.equals(NoteValue.Fill)) {
			File audioFile = new File(NOTE_FILE_PATH + noteFile+".wav");
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(audioFile.getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
			if (Timer > length*getTempo()) {
				Timer -= length*getTempo();
				Thread.sleep(length*getTempo());
			} else {
				Thread.sleep(Timer);
				playMultiSound("bass", 16);
				if (hitSnare) {
					playMultiSound("snare", 16);
					hitSnare = false;
				} else {
					hitSnare = true;
				}
				//playMetranome((length*getTempo())-Timer);
				Timer = Tempo*4;
			}
		}
	}
	
	public void playMetranome(int length) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
		File audioFile = new File(NOTE_FILE_PATH + "BeepQuick.wav");
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(audioFile.getAbsoluteFile());
		Clip clip = AudioSystem.getClip();
		clip.open(audioIn);
		clip.start();
		Thread.sleep(length);
	}
	
	public void playMultiNote(NoteValue note, int length) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
		String noteFile = note.toString();
		if (!note.equals(NoteValue.Fill)) {
			File audioFile = new File(HARRISON_FILE_PATH + noteFile+".wav");
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(audioFile.getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
		}
	}
	
	public void playMultiSound(String sound, int length) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
		if (!sound.equals(NoteValue.Fill)) {
			File audioFile = new File(SOUND_FILE_PATH + sound+".wav");
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(audioFile.getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(5.0f);
			clip.start();
		}
	}
}


