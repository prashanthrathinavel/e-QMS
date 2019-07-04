package com.study.project.quiz;
/**
 * 
 * @author prashanthrathinavel
 *
 */
public class Question {
	private String question;
	private String topic[] = new String[2];
	private int difficulty;
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String[] getTopic() {
		return topic;
	}
	public void setTopic(String[] topic) {
		this.topic = topic;
	}
	public int getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	public Question(String question, String[] topic, int difficulty) {
		super();
		this.question = question;
		this.topic = topic;
		this.difficulty = difficulty;
	}
	
	public Question(String question) {
		this.question = question;
	}
	
}
