package sokoban;

import java.util.ArrayList;
import java.util.List;

public class Game {
	protected final List<Level> allMyLevels = new ArrayList<Level>();
	protected final List<String> levelNames = new ArrayList<String>();
	protected int levelCount;
	
	public void addLevel (String name, int width, int height, String startingData) {
		Level level = new Level (name, width, height, startingData);
		this.allMyLevels.add(level);
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		if (this.allMyLevels.size() == 0) {
			result.append("no levels");
		}
		else {
			Level level = this.getCurrentLevel();
			result.append(level.toString());
		}
		return result.toString();
	}
	
	public List<String> getLevelNames() {
		for (Level level : this.allMyLevels) {
			this.levelNames.add(level.name);
		}
		return levelNames;
	}
	
	public int getLevelCount() {
		levelCount = 0;
		levelCount = this.allMyLevels.size();
		return levelCount;
	}
	
	public Level getCurrentLevel () {
		return this.allMyLevels.get(this.allMyLevels.size() - 1);
	}
	
	public String getCurrentLevelName() {
		StringBuilder result = new StringBuilder();
		if (this.allMyLevels.size() == 0) {
			result.append("no levels");
		}
		else {
			result.append(this.allMyLevels.get(this.allMyLevels.size() - 1).name);
		}
		return result.toString();
	}
	
	public void move(Direction direction) {
		Level level = this.getCurrentLevel();
		level.move(direction);
	}
	}

