package Utils;

import Entities.AbstractEntity;
import Enums.CellContent;

public class Cell {
    private Position position;
    private CellContent cellContent;
    private AbstractEntity entity;

    public Cell(Position position) {
        this.position = position;
        cellContent = CellContent.EMPTY;
    }

    public Position getPosition() {
        return position;
    }

    public CellContent getCellContent() {
        return cellContent;
    }
}
