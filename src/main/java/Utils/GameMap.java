package Utils;

import java.util.*;

/**
 * Представляет игровую карту, которая хранит клетки {@link Cell} по их позициям {@link Position}.
 */
public class GameMap {
    /**
     * Карта, связывающая позиции с клетками.
     */
    private final Map<Position, Cell> cellMap;

    /**
     * Конструктор по умолчанию, создающий пустую карту клеток.
     */
    public GameMap() {
        this.cellMap = new HashMap<>();
    }

    /**
     * Добавляет клетку на карту.
     *
     * @param cell клетка для добавления
     */
    public void addCell(Cell cell) {
        cellMap.put(cell.getPosition(), cell);
    }

    /**
     * Возвращает клетку по заданной позиции.
     *
     * @param position позиция клетки
     * @return клетка, находящаяся на указанной позиции, или {@code null}, если клетка отсутствует
     */
    public Cell getCell(Position position) {
        return cellMap.get(position);
    }

    /**
     * Возвращает список клеток, окружающих указанную позицию в заданном радиусе.
     *
     * @param centerPosition центральная позиция
     * @param radius радиус области
     * @return список окружающих клеток
     */
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
