package balloonbuster;

import base_gamepad.BaseActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by robertoguazon on 09/07/2016.
 */
public class Balloon extends BaseActor {

    public float speed;
    public float amplitude;
    public float oscillation;
    public float initialY;
    public float time;
    public int offsetX;

    public Balloon() {
        speed = 80 * MathUtils.random(0.5f,2.0f);
        amplitude = 50 * MathUtils.random(0.5f,2.0f);
        oscillation = 0.01f * MathUtils.random(0.5f,2.0f);
        initialY = 120 * MathUtils.random(0.5f,2.0f);
        time = 0;
        offsetX = -100;
        setTexture(new Texture(Gdx.files.internal("assets/chapter3/balloon_buster/red-balloon.png")));

        //initial spawn location off-screen
        setX(offsetX);
    }

    @Override
    public void act(float dt) {
        super.act(dt);

        time += dt;
        //set starting position to the left of the window
        float xPos = speed * time + offsetX;
        float yPos = amplitude * MathUtils.sin(oscillation * xPos) + initialY;
        setPosition(xPos,yPos);
    }

}
