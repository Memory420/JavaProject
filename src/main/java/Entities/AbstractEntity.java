package Entities;

import Utils.Cell;
import javafx.scene.control.Button;

public abstract class AbstractEntity {
    private Cell currentCell;
    private Button button;

    public Cell getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(Cell cell) {
        this.currentCell = cell;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public abstract void update();
}
