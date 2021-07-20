package sokoban;

interface GetLevelInfo {
    String getCurrentLevelName();
    int getTargetsCount();
    int getCompletedTargetsCount();
    int getMoveCount();
        }

    interface UpdateLevel{
    void removeLevel();
    int[][] getDrawables();
        }

public class SokobanController implements GetLevelInfo, UpdateLevel {
    private final Game model;
    private static SokobanController sController;

    private SokobanController() {
        this.model = new Game();
    }

    public static SokobanController create() {
        if (sController == null) {
            sController = new SokobanController();
        }
        return sController;
    }

    public void addLevel(String name, int width, int height, String startingData) {
        this.model.addLevel(name, width, height, startingData);
    }

    public String getCurrentLevelName() {
        return this.model.getCurrentLevelName();
    }

    public int getTargetsCount() {
        return this.model.getCurrentLevel().targetCount;
    }

    public int getCompletedTargetsCount() {
        return this.model.getCurrentLevel().completedCount;
    }

    public int getMoveCount() {
        return this.model.getCurrentLevel().moveCount;
    }

    public void move(String direction) {
        switch (direction) {
            case "up": {
                this.model.move(Direction.UP);
                break;
            }
            case "left": {
                this.model.move(Direction.LEFT);
                break;
            }
            case "down": {
                this.model.move(Direction.DOWN);
                break;
            }
            case "right": {
                this.model.move(Direction.RIGHT);
                break;
            }
        }
    }

    public int[][] getDrawables() {
        return this.model.getCurrentLevel().getDrawables();
    }

    public void loadLevel(int id) {
        id++;
        switch (id) {
            case 1: {
                this.model.addLevel("1", 6, 5,
                        "######" +
                                "#+.+.#" +
                                "#x.x.#" +
                                "#.w..#" +
                                "######");
                break;
            }
            case 2: {
                this.model.addLevel("2", 6, 5,
                        "######" +
                                "#w...#" +
                                "#..x+#" +
                                "#.+x.#" +
                                "######");
                break;
            }
            case 3: {
                this.model.addLevel("3", 8, 9,
                        "..#####." +
                                "###...#." +
                                "#+wx..#." +
                                "###.x+#." +
                                "#+##x.#." +
                                "#.#.+.##" +
                                "#x.Xxx+#" +
                                "#...+..#" +
                                "########");
                break;
            }

            case 4: {
                this.model.addLevel("4", 9, 8,
                        "######..." +
                                "#w..+####" +
                                "#..xxx..#" +
                                "#+##+##+#" +
                                "#...x...#" +
                                "##.#.x..#" +
                                ".#..+####" +
                                ".#####...");
                break;
            }
            case 5: {
                this.model.addLevel("5", 11, 9,
                        ".###......." +
                                ".#+########" +
                                ".#........#" +
                                ".#..x.x.x.#" +
                                "##.########" +
                                "#+..x....+#" +
                                "#+..##xw###" +
                                "##+.##..#.."+
                                ".########..");
                break;
            }
        }
    }
    public boolean levelCompleted () {
        return this.model.getCurrentLevel().completeLevel();
    }

    public void removeLevel() {
        this.model.allMyLevels.remove(this.model.getCurrentLevel());
    }

}
