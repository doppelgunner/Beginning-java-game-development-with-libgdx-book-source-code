package base_gamepad;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by robertoguazon on 01/07/2016.
 */
public class BaseActor extends Actor {

    public TextureRegion region;
    public Rectangle boundary;
    public float velocityX;
    public float velocityY;

    public BaseActor() {
        super();
        region = new TextureRegion();
        boundary = new Rectangle();
        velocityX = 0;
        velocityY = 0;
    }

    public void setTexture(Texture t) {
        int w = t.getWidth();
        int h = t.getHeight();
        setWidth(w);
        setHeight(h);
        region.setRegion(t);
    }

    public Rectangle getBoundingRectangle() {
        boundary.set(getX(), getY(), getWidth(), getHeight());

        return boundary;
    }

    @Override
    public void act(float dt) {
        super.act(dt);
        moveBy(velocityX * dt, velocityY * dt);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color c = getColor();
        batch.setColor(c.r, c.g, c.b, c.a);

        if ( isVisible() ) {
            batch.draw(region, getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }

    }

}
