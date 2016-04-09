package main;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


/*
 * Ideas: .mm to midi file converter, and midi to .mm file converter!!
 * 
 */

public class Main {
	
	private static MusicMaker maker;
	private static MusicPlayer player;
	private static MusicAnalyzer analyzer;
	
	private static int max = 0;
	private static int min = 10000000;
	private static MusicMaker bestMaker;
	private static MusicMaker worstMaker;

	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
		
		//create multiple makers, analyze each one and take only the best one.		
		//214674 is record so far!
		
		for (int i=0; i<100; i++) {
			maker = new MusicMaker("testing"+i, "Drew Jex");
			for (int j=0; j<1; j++) {
				maker.addMeasure();
				maker.addRhythm();
				maker.addRhythm();
				maker.addRhythm();
				maker.addRhythm();
				maker.addRhythm();
				maker.addRhythm();
				maker.addRhythm();
				//maker.addRhythm();
				//maker.addRhythm();
				//maker.addRhythm();
				//maker.addRhythm();
				maker.developRhythm();
				maker.developRhythm();
				maker.developRhythm();
				maker.developNoteInKey();
				maker.developNoteInKey();
				maker.developNoteInKey();
				maker.developNoteInKey();
				maker.developNoteInKey();
				maker.developNoteInKey();
				maker.developNoteInKey();
				maker.developNoteInKey();
				maker.developNoteInKey();
				maker.developNoteInKey();
				maker.developNoteInKey();
				maker.developNoteInKey();
				maker.developNoteInKey();
				maker.developNoteInKey();
				maker.developNoteInKey();
				maker.developNoteInKey();
				maker.developNoteInKey();
				maker.developNoteInKey();
				maker.developNoteInKey();
				maker.developNoteInKey();
				maker.repeatLastMeasure();
				maker.repeatLastMeasure();
				maker.repeatLastMeasureRhythm();
			}
			
			analyzer = new MusicAnalyzer();
			analyzer.analyzePhrase(maker.getSong().getSections()[0].getPhrases()[0]);
			//System.out.println(analyzer.getScore());
			
			if (analyzer.getScore() > max) {
				max = analyzer.getScore();
				bestMaker = maker;
			} else if (analyzer.getScore() < min) {
				min = analyzer.getScore();
				worstMaker = maker;
			}
		}
		
		System.out.println("Best Score: "+max);
		player = new MusicPlayer(bestMaker.getSong());
		player.play();
		//bestMaker.save();
		
		System.out.println("Worst Score: "+min);
		player = new MusicPlayer(worstMaker.getSong()); //you should get more points for more repetitions, even with a short phrase.
		player.play();
	}

}
