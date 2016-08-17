package base_gamepad;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

/**
 * Created by robertoguazon on 02/07/2016.
 */
public class AnimatedActor extends BaseActor {

    public float elapsedTime;
    public Animation activeAnim;
    private String activeName;
    private HashMap<String, Animation> animationStorage;

    public AnimatedActor() {
        super();
        elapsedTime = 0;
        activeAnim = null;
        activeName = null;
        animationStorage = new HashMap<>();
    }

    public void storeAnimation(String name, Animation anim) {
        animationStorage.put(name, anim);
        if (activeName == null) {
            setActiveAnimation(name);
        }
    }

    public void storeAnimation(String name, Texture tex) {
        TextureRegion reg = new TextureRegion(tex);
        TextureRegion[] frames = {reg};
        Animation anim = new Animation(1.0f, frames);
        storeAnimation(name, anim);
    }

    public void setActiveAnimation(String name) {
        if (!animationStorage.containsKey(name)) {
            System.out.println("No animation: " + name);
            return;
        }

        //no need to set animation if already running
        if (activeName!= null && activeName.equals(name)) return;

        activeName = name;
        activeAnim = animationStorage.get(name);
        elapsedTime = 0;

        Texture tex = activeAnim.getKeyFrame(0).getTexture();
        setWidth(tex.getWidth());
        setHeight(tex.getHeight());
    }

    public String getAnimationName() {
        return activeName;
    }

    @Override
    public void act(float dt) {
        super.act(dt);
        elapsedTime += dt;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        region.setRegion(activeAnim.getKeyFrame(elapsedTime));
        super.draw(batch,parentAlpha);
    }

}
