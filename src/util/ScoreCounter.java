package util;

public class ScoreCounter {
	
	public String name;
	public int value;
	
	public ScoreCounter() {
		name = "Score";
		value = 0;
	}
	
	public ScoreCounter(int value) {
		name = "Score";
		this.value = value;
	}

	public ScoreCounter(int value, String name) {
		this.name = name;
		this.value = value;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setScore(int score) {
		this.value = score;
	}
	
	public int getScore() {
		return value;
	}
	
	public void add(int valToAdd) {
		value += valToAdd;
	}
	
	public void subract(int valToSubtract) {
		value -= valToSubtract;
	}
	
	public int MergeValues(ScoreCounter score) {
		return (this.value + score.getScore());
	}
	
	public int DifferenceBetween(ScoreCounter score) {
		return (this.value - score.getScore());
	}
}
