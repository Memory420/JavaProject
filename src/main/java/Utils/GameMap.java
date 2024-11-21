package Utils;

import java.util.*;

public class GameMap {
    private final Map<Position, Cell> cellMap;

    public GameMap() {
        this.cellMap = new HashMap<>();
    }

    public void addCell(Cell cell) {
        cellMap.put(cell.getPosition(), cell);
    }

    public Cell getCell(Position position) {
        return cellMap.get(position);
    }

    public List<Cell> getCellsAround(Position centerPosition, int radius) {
        List<Cell> surroundingCells = new ArrayList<>();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                if (dx == 0 && dy == 0) continue;
                Position newPosition = new Position(
                        centerPosition.getX() + dx,
                        centerPosition.getY() + dy
                );

                Cell cell = getCell(newPosition);
                if (cell != null) {
                    surroundingCells.add(cell);
                }
            }
        }

        return surroundingCells;
    }
    /**
     * Возвращает все клетки на карте.
     *
     * @return коллекция клеток
     */
    public Collection<Cell> getCells() {
        return cellMap.values();
    }
}
