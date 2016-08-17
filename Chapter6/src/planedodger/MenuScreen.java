package planedodger;

import base_A.*;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by robertoguazon on 23/07/2016.
 */
public class MenuScreen extends BaseScreen {

    public MenuScreen(BaseGame game) {
        super(game);
    }

    @Override
    public void create() {
        //background sky
        mainStage.addActor(PlaneDodgerGame.background[0]);
        mainStage.addActor(PlaneDodgerGame.background[1]);

        //ground
        mainStage.addActor(PlaneDodgerGame.ground[0]);
        mainStage.addActor(PlaneDodgerGame.ground[1]);

        //player not moving
        AnimatedActor plane = new AnimatedActor();
        plane.storeAnimation("default", game.skin.get("playerAnim", Animation.class));
        plane.setPosition(200,300);
        mainStage.addActor(plane);

        //title
        Label planeDodgerLabel = new Label("Plane Dodger", game.skin, "labelStyle");
        planeDodgerLabel.setFontScale(1.5f);

        //start button
        TextButton startButton = new TextButton("Start", game.skin, "textButtonStyle");
        startButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
            }
        });

        //add gui
        uiTable.add(planeDodgerLabel).padTop(50);
        uiTable.row();
        uiTable.add().expandY();
        uiTable.row();
        uiTable.add(startButton).padBottom(100);
    }

    @Override
    public void update(float dt) {

        //manage scrolling
        for (int i = 0; i < 2; i++) {
            PhysicsActor bg = PlaneDodgerGame.background[i];
            if (bg.getX() + bg.getWidth() < 0) {
                bg.setX(bg.getX() + 2 * bg.getWidth());
            }

            PhysicsActor gr = PlaneDodgerGame.ground[i];
            if (gr.getX() + gr.getWidth() < 0) {
                gr.setX(gr.getX() + 2 * gr.getWidth());
            }
        }

    }

}
