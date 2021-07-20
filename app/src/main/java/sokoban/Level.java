package sokoban;


interface PlayLevel {
    void move(Direction direction);
}

public class Level implements PlayLevel{
    protected final String name;
    private final int width;
    private final int height;
    public int moveCount = 0;
    protected int completedCount = 0;
    protected int targetCount = 0;
    protected int crateCount = 0;
    protected final String startingData;
    public final Placeable[][] allPlaceables;
    protected Worker myWorker;

    public Level(String name, int width, int height, String startingData) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.allPlaceables = new Placeable[height][width];
        this.startingData = startingData;
        this.restart();
    }

    protected void restart() {
        this.moveCount = 0;
        this.completedCount = 0;
        this.targetCount = 0;
        this.crateCount = 0;
        Integer i = new Integer(0);
        int index = i;
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                Character data = this.startingData.charAt(index);
                char symbol = data;
                Placeable newTile = this.makePlaceable(x, y, symbol);
                this.allPlaceables[x][y] = newTile;
                index++;
            }
        }
    }

    protected Placeable makePlaceable(int x, int y, char symbol) {
        Placeable newPlaceable = new Empty(x, y);
        switch (symbol) {
            case '#': {
                newPlaceable = new Wall(x, y);
                break;
            }
            case '.': {
                newPlaceable = new Empty(x, y);
                break;
            }
            case '+': {
                newPlaceable = new Target(x, y);
                this.targetCount++;
                break;
            }
            case 'x': {
                Empty empty = new Empty(x, y);
                Crate crate = new Crate(x, y);
                empty.addCrate(crate);
                newPlaceable = empty;
                crateCount++;
                break;
            }
            case 'X': {
                Target target = new Target(x, y);
                Crate crate = new Crate(x, y);
                target.addCrate(crate);
                newPlaceable = target;
                this.targetCount++;
                break;
            }
            case 'w': {
                Empty empty = new Empty(x, y);
                this.myWorker = new Worker(x, y);
                empty.addWorker(this.myWorker);
                newPlaceable = empty;
                break;
            }
        }
        return newPlaceable;
    }


    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int x = 0; x < this.allPlaceables.length; x++) { //2D
            for (int y = 0; y < this.allPlaceables[x].length; y++) { // 2D
                result.append(this.allPlaceables[x][y]);
            }
            result.append('\n');
        }
        return result.toString();
    }

    public int[][] getDrawables() {
        int[][] levelImages = new int[this.height][this.width];
        for (int x = 0; x < this.allPlaceables.length; x++) { //2D
            for (int y = 0; y < this.allPlaceables[x].length; y++) { // 2D
                levelImages[x][y] = this.allPlaceables[x][y].image;
            }

    }
        return levelImages;
    }

    private void increaseMoveCount() {
        this.moveCount++;
    }

    public Placeable whoIsAt(Point where) {
        return this.allPlaceables[where.x()][where.y()];
    }

    public void move(Direction direction) {
        Point workerCurrentPoint = this.myWorker.where();
        Placeable workerCurrentPlace = this.whoIsAt(workerCurrentPoint);

        Point workerDestinationPoint = direction.adjust(this.myWorker.where());
        Placeable workerDestinationPlace = this.whoIsAt(workerDestinationPoint);

        if (workerDestinationPlace.isEmpty()) {
            Enterable currentEnterable = (Enterable) workerCurrentPlace;
            currentEnterable.removeWorker();

            Enterable destinationEnterable = (Enterable) workerDestinationPlace;
            destinationEnterable.addWorker(this.myWorker);
        }

        if (workerDestinationPlace.hasCrate()) {
            Point crateDestinationPoint = direction.adjust(workerDestinationPoint);
            Placeable crateDestinationPlace = this.whoIsAt(crateDestinationPoint);
            Enterable crateEnterable = (Enterable) workerDestinationPlace;
            Crate crate = crateEnterable.getCrate();
                if (crateDestinationPlace.isEmpty()) {
                    Enterable cratePlace = (Enterable) workerDestinationPlace;
                    cratePlace.removeCrate();
                    if (cratePlace.isTarget()) {this.completedCount--;}
                    Enterable crateDestinationEnterable = (Enterable) crateDestinationPlace;
                    crateDestinationEnterable.addCrate(crate);
                    if (crateDestinationEnterable.isTarget() && completedCount < crateCount) {
                        this.completedCount++;
                    }
                    Enterable currentEnterable = (Enterable) workerCurrentPlace;
                    currentEnterable.removeWorker();

                    Enterable destinationEnterable = (Enterable) workerDestinationPlace;
                    destinationEnterable.addWorker(this.myWorker);

                }
            }
            //if workerDestinationPlace isWall == false then increase
            if (!workerDestinationPlace.isWall()) {
                this.increaseMoveCount();
            }

        }
        public boolean completeLevel () {
            return this.targetCount == this.completedCount;
    }


    }

