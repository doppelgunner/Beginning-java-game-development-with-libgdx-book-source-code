import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by robertoguazon on 02/07/2016.
 */
public class AnimatedActor extends BaseActor {

    public float elapsedTime;
    public Animation anim;

    public AnimatedActor() {
        super();
        elapsedTime = 0;
    }

    public void setAnimation(Animation anim) {
        Texture t = anim.getKeyFrame(0).getTexture();
        setTexture(t);
        this.anim = anim;
    }

    @Override
    public void act(float dt) {
        super.act(dt);
        elapsedTime += dt;
        if (velocityX != 0 || velocityY != 0) {
            setRotation(MathUtils.atan2(velocityY,velocityX) * MathUtils.radiansToDegrees);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        region.setRegion(anim.getKeyFrame(elapsedTime));
        super.draw(batch,parentAlpha);
    }


}
