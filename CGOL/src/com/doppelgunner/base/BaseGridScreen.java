package com.doppelgunner.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.doppelgunner.base.grid.Cell;

import java.util.ArrayList;

/**
 * Created by robertoguazon on 06/07/2016.
 */
public abstract class BaseGridScreen implements Screen, InputProcessor {

    protected BaseGame game;
    protected Stage mainStage;
    protected Stage uiStage;
    protected Table uiTable;

    protected OrthographicCamera cam;

    public final int viewWidth;
    public final int viewHeight;

    private boolean paused;

    private ShapeRenderer shapeRenderer;

    protected ArrayList<Cell> lineCells;
    protected ArrayList<Cell> filledCells;

    protected float gridWidth;
    protected float gridHeight;
    protected int gridCols;
    protected int gridRows;

    protected float cellWidth;
    protected float cellHeight;

    protected float gridOffsetX;
    protected float gridOffsetY;

    protected Vector2 topLeft;
    protected Vector2 bottomRight;

    protected float screenWidth;
    protected float screenHeight;

    protected float dsvWidth;
    protected float dsvHeight;

    protected float dwRatio;
    protected float dhRatio;

    protected Cell[][] cells;

    protected Color clearColor;

    public BaseGridScreen(BaseGame game) {
        this.game = game;

        viewWidth = Gdx.graphics.getWidth();
        viewHeight = Gdx.graphics.getHeight();

        mainStage = new Stage(new FitViewport(viewWidth,viewHeight));
        uiStage = new Stage(new FitViewport(viewWidth, viewHeight));

        paused = false;

        InputMultiplexer im = new InputMultiplexer(this, uiStage,mainStage);
        Gdx.input.setInputProcessor(im);

        uiTable = new Table();
        uiTable.setFillParent(true);
        uiStage.addActor(uiTable);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        cam = new OrthographicCamera(viewWidth, viewHeight);
        cam.setToOrtho(true, viewWidth, viewHeight);
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();

        shapeRenderer = new ShapeRenderer();

        lineCells = new ArrayList<>();
        filledCells = new ArrayList<>();

        //instantiate grid
        gridHeight = gridWidth = viewWidth - viewWidth / 3f;
        Gdx.app.log("grid", "width = " + gridWidth + ", height = " + gridHeight);

        gridOffsetX = 20;
        gridOffsetY = (viewHeight - gridHeight) / 2;
        Gdx.app.log("grid", "offsetX = " + gridOffsetX + ", offsetY = " + gridOffsetY);

        gridRows = gridCols = 30;
        Gdx.app.log("grid", "rows = " + gridRows + ", cols = " + gridCols);

        //pixels
        cellHeight = gridHeight / gridRows;
        cellWidth = gridWidth / gridCols;
        Gdx.app.log("grid", "cell width = " + cellWidth + ", cell height = " + cellHeight);

        //init corners
        topLeft = new Vector2(gridOffsetX, gridOffsetY);
        bottomRight = new Vector2(gridCols * cellWidth + gridOffsetX, gridRows * cellHeight + gridOffsetY);
        Gdx.app.log("grid", "");

        screenWidth = viewWidth;
        screenHeight = viewHeight;
        dhRatio = 1;
        dwRatio = 1;
        setScreenWidth(viewWidth);
        setScreenHeight(viewHeight);
        Gdx.app.log("screen", "width = " + screenWidth + ", height = " + screenHeight);

        clearColor = Color.BLACK;

        create();
    }

    public abstract void create();
    public abstract void update(float dt);

    //game loop code; update, then render
    @Override
    public void render(float dt) {
        uiStage.act(dt);

        //only pause gameplay events, not UI events
        if (!isPaused()) {
            mainStage.act(dt);
            update(dt);
        }

        cam.update();
        mainStage.getBatch().setProjectionMatrix(cam.combined);
        uiStage.getBatch().setProjectionMatrix(cam.combined);
        shapeRenderer.setProjectionMatrix(cam.combined);

        //render
        Gdx.app.debug("clear", "clearing...");
        Gdx.gl.glClearColor(clearColor.r,clearColor.g,clearColor.b,clearColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        Gdx.app.debug("stage", "drawing actors...");
        mainStage.draw();
        uiStage.draw();

        //shape renderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        Gdx.app.debug("shapeRenderer", "shape type = Line");
        Gdx.app.debug("shapeRenderer", "rendering shapes...");
        for (Cell lineCell : lineCells) {
            renderShape(lineCell);
        }
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Gdx.app.debug("shapeRenderer", "shape type = Filled");
        Gdx.app.debug("shapeRenderer", "rendering shapes...");
        for (Cell filledCell : filledCells) {
            renderShape(filledCell);
        }
        shapeRenderer.end();

        Gdx.app.debug("lists", "clearing lineCells and filledCills...");
        lineCells.clear();
        filledCells.clear();
    }

    private void renderShape(Cell cell) {
        switch (cell.getShapeDrawType()) {
            case CIRCLE:
                Gdx.app.debug("drawShape", "drawing circle...");
                drawShape(cell.x, cell.y,
                        (cell.width <= cell.height) ? cell.width / 2f: cell.height / 2f, cell.getActiveColor());
                break;

            case RECTANGLE:
            default:
                Gdx.app.debug("drawShape", "drawing rectangle...");
                drawShape(cell.x, cell.y, cell.width, cell.height,
                        cell.getActiveColor());

        }
    }

    //pause methods
    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void togglePaused() {
        this.paused = !this.paused;
    }


    public void resize(int width, int height) {
        Gdx.app.log("resize", "resizing stages..." );
        mainStage.getViewport().update(width,height, true);
        uiStage.getViewport().update(width,height, true);

        Gdx.app.log("stages", "mainStage and uiStage screen width = " + width + ", height = " + height);

        setScreenWidth(width);
        setScreenHeight(height);
    }

    //methods required by screen interface
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void dispose() {}
    @Override public void show() {}
    @Override public void hide() {}

    // methods required by InputProcessor interface
    @Override
    public boolean keyDown(int keyCode) {
        return false;
    }

    @Override
    public boolean keyUp(int keyCode) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    protected void drawShape(float x, float y, float width, float height, Color color) {
        shapeRenderer.setColor(color);
        shapeRenderer.rect(x,y,width,height);

        Gdx.app.debug("drawShape", "rectangle with x = " + x + ", y = " + y + "; width = " + width + ", height = " + height +
                "; color = " + color);
    }

    protected void drawShape(float x, float y, float radius, Color color) {
        shapeRenderer.setColor(color);
        //draw circle starting from top left corner
        shapeRenderer.circle(x + radius,y + radius,radius);

        Gdx.app.debug("drawShape", "circle with x = " + x + ", y = " + y + "; radius = " + radius + "; color = " + color);
    }

    public void setScreenWidth(float width) {
        this.screenWidth = width;
        dsvWidth = this.screenWidth - viewWidth;
        dwRatio = this.screenWidth / viewWidth;

        Gdx.app.log("screen", "width = " + this.screenWidth);
        Gdx.app.log("screen", "dsvWidth = " + dsvWidth);
        Gdx.app.log("screen", "dwRatio = " + dwRatio);
    }

    public void setScreenHeight(float height) {
        this.screenHeight = height;
        dsvHeight = this.screenHeight - viewHeight;
        dhRatio = this.screenHeight / viewHeight;

        Gdx.app.log("screen", "height = " + this.screenHeight);
        Gdx.app.log("screen", "dsvHeight = " + dsvHeight);
        Gdx.app.log("screen", "dhRatio = " + dhRatio);
    }
}
