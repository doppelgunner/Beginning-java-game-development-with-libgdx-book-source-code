package com.doppelgunner.cgol.game;

import com.badlogic.gdx.graphics.Color;
import com.doppelgunner.base.BaseGame;
import com.doppelgunner.base.BaseGridScreen;
import com.badlogic.gdx.Gdx;
import com.doppelgunner.base.grid.Cell;

/**
 * Created by robertoguazon on 11/07/2016.
 */
public class CGOLLevel extends BaseGridScreen {

    public enum ShapeDrawType {
        RECTANGLE, CIRCLE
    }

    Cell.State touchDraggedType;

    private ShapeDrawType shapeDrawType;

    public CGOLLevel(BaseGame game) {
        super(game);
    }

    @Override
    public void create() {
        Gdx.app.log("grid", "creating cells... rows = " + gridRows + ", cols = " + gridCols);
        cells = new Cell[gridRows][gridCols];

        for (int row = 0; row < gridRows; row++) {
            for (int col = 0; col < gridCols; col++) {
                cells[row][col] = new Cell(col * cellWidth + gridOffsetX, row * cellHeight + gridOffsetY,
                        cellWidth, cellHeight);
            }
        }

        touchDraggedType = Cell.State.ALIVE;
        clearColor = Color.valueOf("b32020");

    }

    @Override
    public void update(float dt) {

        Gdx.app.debug("grid", "separating shape types - filled and line cells...");
        for (int row = 0; row < gridRows; row++) {
            for (int col = 0; col < gridCols; col++) {
                Cell cell = cells[row][col];

                switch (cell.getShapeType()) {
                    case Filled:
                        filledCells.add(cell);
                        break;
                    case Line:
                        lineCells.add(cell);
                        break;
                }

                Gdx.app.debug("cell", "created " + cell);
            }
        }

        Gdx.app.debug("lists", "lineCells = " + lineCells.size() + ", filledCells = " + filledCells.size());
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Gdx.app.log("touchDragged", "screenX = " + screenX + ", screenY = " + screenY + "; pointer = " + pointer);

        Gdx.app.log("isInsideGrid", "checking if inside grid...");
        if (isInsideGrid(screenX, screenY)) {
            Gdx.app.log("insideGrid", "true");

            for (int row = 0; row < gridRows; row++) {
                for (int col = 0; col < gridCols; col++) {

                    Gdx.app.debug("cell", "checking if cell, row = " + row + ", col = " + col +
                            " contains (" + screenX + ", " + screenY + ")");
                    Cell cell = cells[row][col];
                    if (cell.contains(screenX, screenY)) {

                        cell.setState(touchDraggedType);
                    }

                }
            }

            return true;
        } else {
            Gdx.app.log("insideGrid", "false");
        }

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Gdx.app.log("touchDown", "screenX = " + screenX + ", screenY = " + screenY + "; pointer = " + pointer);

        Gdx.app.log("touchDown", "checking if inside grid...");
        if (isInsideGrid(screenX, screenY)) {
            Gdx.app.log("insideGrid", "true");

            for (int row = 0; row < gridRows; row++) {
                for (int col = 0; col < gridCols; col++) {

                    Gdx.app.debug("cell", "checking if cell, row = " + row + ", col = " + col +
                            " contains (" + screenX + ", " + screenY + ")");
                    Cell cell = cells[row][col];
                    if (cell.contains(screenX, screenY)) {
                        cell.setState(touchDraggedType);
                    }

                }
            }

            return true;
        } else {
            Gdx.app.log("insideGrid", "false");
        }

        return false;
    }



    public void setTouchDraggedType(Cell.State touchDraggedType) {
        this.touchDraggedType = touchDraggedType;
    }

    public Cell.State getTouchDraggedType() {
        return touchDraggedType;
    }

    public boolean isInsideGrid(float x, float y) {
        if (x < topLeft.x || x > bottomRight.x) return false;
        if (y < topLeft.y || y > bottomRight.y) return false;

        return true;
    }
}
