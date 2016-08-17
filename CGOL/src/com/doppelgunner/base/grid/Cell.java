package com.doppelgunner.base.grid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.doppelgunner.base.BaseActor;
import com.doppelgunner.cgol.game.CGOLLevel;

/**
 * Created by robertoguazon on 14/07/2016.
 */
public class Cell extends BaseActor {

    public float x;
    public float y;
    public float width;
    public float height;

    private ShapeRenderer.ShapeType shapeType;
    private State state;
    private CGOLLevel.ShapeDrawType shapeDrawType;

    private Color dyingColor;
    private Color aliveColor;
    private Color bornColor;
    private Color spaceColor;

    private Color activeColor;

    public enum State {
        ALIVE, DYING, BORN, SPACE
    }

    public Cell(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        shapeType = ShapeRenderer.ShapeType.Line;
        state = State.SPACE;
        shapeDrawType = CGOLLevel.ShapeDrawType.RECTANGLE;

        dyingColor = Color.valueOf("c86464");
        aliveColor = Color.valueOf("f0be6e");
        bornColor = Color.valueOf("f09696");
        spaceColor = Color.valueOf("c86464");
        activeColor = spaceColor;
    }

    public void setState(State state) {

        if (this.state.equals(state)) {
            Gdx.app.log("cell", "this cell is already " + state + ", no change needed. exiting through return...");
            return;
        }

        Gdx.app.debug("cell", "(" + x +", " + y + "): setting state...");
        this.state = state;
        switch (state) {
            case ALIVE:
                activeColor = aliveColor;
                setShapeType(ShapeRenderer.ShapeType.Filled);
                break;

            case BORN:
                activeColor = bornColor;
                setShapeType(ShapeRenderer.ShapeType.Filled);
                break;

            case DYING:
                activeColor = dyingColor;
                setShapeType(ShapeRenderer.ShapeType.Filled);
                break;

            case SPACE:
            default:
                activeColor = spaceColor;
                setShapeType(ShapeRenderer.ShapeType.Line);
        }

        Gdx.app.debug("cell", "(" + x +", " + y + "): state = " + state);
        Gdx.app.debug("cell", "(" + x +", " + y + "): color = " + activeColor);
        Gdx.app.debug("cell", "(" + x +", " + y + "): shapeType = " + shapeType);
    }

    private void setShapeType(ShapeRenderer.ShapeType shapeType) {
        this.shapeType = shapeType;
    }

    public void setShapeDrawType(CGOLLevel.ShapeDrawType shapeDrawType) {
        this.shapeDrawType = shapeDrawType;
    }

    public CGOLLevel.ShapeDrawType getShapeDrawType() {
        return shapeDrawType;
    }

    public State getState() {
        return state;
    }

    public ShapeRenderer.ShapeType getShapeType() {
        return shapeType;
    }

    public Color getActiveColor() {
        return activeColor;
    }

    public String toString() {
        return "cell, x = " + x + ", y = " + y + "; width = " + width + ", height = " + height + "; state = " + state;
    }

    public boolean contains(float x, float y) {
        if (x < this.x || x > this.x + this.width) return false;
        if (y < this.y || y > this.y + this.height) return false;

        return true;
    }

    public void setResize(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

}
