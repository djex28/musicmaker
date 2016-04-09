package model;

public class Song {
	
	private Section[] sections;
	private String title;
	private String author;
	
	public Song(Section[] sections, String title, String author) {
		this.sections = sections;
		this.title = title;
		this.author = author;
	}
	public Section[] getSections() {
		return sections;
	}
	public void setSections(Section[] sections) {
		this.sections = sections;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}

}
