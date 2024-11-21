package Utils;

import Entities.AbstractEntity;
import javafx.scene.control.Button;

public class Cell {
    private final Position position;
    private AbstractEntity entity;
    private Button button;
    private GameMap gameMap; // Ссылка на карту

    public Cell(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public AbstractEntity getEntity() {
        return entity;
    }

    public void setEntity(AbstractEntity entity) {
        this.entity = entity;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }
}
