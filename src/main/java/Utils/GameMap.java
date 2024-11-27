package Utils;

import java.util.*;

public class GameMap {
    private final Map<Position, Cell> cellMap;
    private final int width;
    private final int height;

    public GameMap(int width, int height) {
        this.cellMap = new HashMap<>();
        this.width = width;
        this.height = height;
        initializeCells();
    }

    private void initializeCells() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Position position = new Position(x, y);
                Cell cell = new Cell(position);
                cell.setGameMap(this);
                cellMap.put(position, cell);
            }
        }
    }

    public Cell getCell(Position position) {
        return cellMap.get(position);
    }

    public List<Cell> getCellsAround(Position centerPosition, int radius) {
        List<Cell> surroundingCells = new ArrayList<>();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                if (dx == 0 && dy == 0) continue;
                int newX = centerPosition.getX() + dx;
                int newY = centerPosition.getY() + dy;

                if (isValidPosition(newX, newY)) {
                    Position newPosition = new Position(newX, newY);
                    Cell cell = getCell(newPosition);
                    if (cell != null) {
                        surroundingCells.add(cell);
                    }
                }
            }
        }

        return surroundingCells;
    }

    public List<Cell> getCellsInLines(Position centerPosition, int range) {
        List<Cell> lineCells = new ArrayList<>();

        int x = centerPosition.getX();
        int y = centerPosition.getY();

        for (int dx = 1; dx <= range; dx++) {
            int newX = x + dx;
            if (isValidPosition(newX, y)) {
                Cell cell = getCell(new Position(newX, y));
                lineCells.add(cell);
            } else {
                break;
            }
        }

        for (int dx = -1; dx >= -range; dx--) {
            int newX = x + dx;
            if (isValidPosition(newX, y)) {
                Cell cell = getCell(new Position(newX, y));
                lineCells.add(cell);
            } else {
                break;
            }
        }

        for (int dy = 1; dy <= range; dy++) {
            int newY = y + dy;
            if (isValidPosition(x, newY)) {
                Cell cell = getCell(new Position(x, newY));
                lineCells.add(cell);
            } else {
                break;
            }
        }

        for (int dy = -1; dy >= -range; dy--) {
            int newY = y + dy;
            if (isValidPosition(x, newY)) {
                Cell cell = getCell(new Position(x, newY));
                lineCells.add(cell);
            } else {
                break;
            }
        }

        return lineCells;
    }

    public Collection<Cell> getCells() {
        return cellMap.values();
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
}
