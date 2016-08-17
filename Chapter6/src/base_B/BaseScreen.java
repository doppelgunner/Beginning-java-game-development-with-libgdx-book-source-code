package base_B;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

public abstract class BaseScreen implements Screen, InputProcessor, ControllerListener
{
    protected BaseGame game;

    protected Stage mainStage;
    protected Stage uiStage;

    protected Table uiTable;

    public final int viewWidth  = 800;
    public final int viewHeight = 600;

    private boolean paused;

    public BaseScreen(BaseGame g)
    {
        game = g;

        mainStage = new Stage( new FitViewport(viewWidth, viewHeight) );
        uiStage   = new Stage( new FitViewport(viewWidth, viewHeight) );

        uiTable = new Table();
        uiTable.setFillParent(true);
        uiStage.addActor(uiTable);

        paused = false;

        InputMultiplexer im = new InputMultiplexer(this, uiStage, mainStage);
        Gdx.input.setInputProcessor( im );

        //controllers
        Controllers.clearListeners();
        Controllers.addListener(this);

        create();
    }

    public abstract void create();

    public abstract void update(float dt);

    // this is the gameloop. update, then render.
    public void render(float dt) 
    {
        uiStage.act(dt);

        // only pause gameplay events, not UI events
        if ( !isPaused() )
        {
            mainStage.act(dt);
            update(dt);
        }

        // render
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        mainStage.draw();
        uiStage.draw();
    }

    // pause methods
    public boolean isPaused()
    {  return paused;  }

    public void setPaused(boolean b)
    {  paused = b;  }

    public void togglePaused()
    {  paused = !paused;  }

    // methods required by Screen interface
    public void resize(int width, int height) 
    {    
        mainStage.getViewport().update(width, height, true); 
        uiStage.getViewport().update(width, height, true);
    }

    public void pause()   {  }

    public void resume()  {  }

    public void dispose() {  }

    public void show()    {  }

    public void hide()    {  }

    // methods required by InputProcessor interface
    public boolean keyDown(int keycode)
    {  return false;  }

    public boolean keyUp(int keycode)
    {  return false;  }

    public boolean keyTyped(char c) 
    {  return false;  }

    public boolean mouseMoved(int screenX, int screenY)
    {  return false;  }

    public boolean scrolled(int amount) 
    {  return false;  }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) 
    {  return false;  }

    public boolean touchDragged(int screenX, int screenY, int pointer) 
    {  return false;  }

    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {  return false;  }

    //methods required by ControllerListener
    @Override
    public void connected(Controller controller) {}

    @Override
    public void disconnected(Controller controller) {}

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        return false;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        return false;
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        return false;
    }



}