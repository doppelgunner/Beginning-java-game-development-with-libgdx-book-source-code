package rectangledestroyer;

import base_B.PhysicsActor;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by robertoguazon on 30/07/2016.
 */
public class Ball extends PhysicsActor {

    private Circle prevCircle;
    private Circle currCircle;

    public Ball() {
        super();
    }

    public Circle getCircle() {
        return new Circle(getX() + getWidth() / 2, getY() + getHeight() / 2, getWidth() / 2);

    }

    public boolean overlaps(Paddle paddle, boolean bounceOff) {
        if (!Intersector.overlaps(this.getCircle(), paddle.getRectangle())) {
            return false;
        }

        if (bounceOff) {

            float ballCenterX = this.getX() + this.getWidth() / 2;
            float percent = (ballCenterX - paddle.getX()) / paddle.getWidth();
            //interpolate value between 150 and 30
            float bounceAngle = 150 - percent * 120;
            System.out.println("percent = " + percent);
            System.out.println("bounceAngle = " + bounceAngle);
            this.setVelocityAS(bounceAngle,this.getSpeed());
        }

        return true;
    }

    public boolean overlaps(Brick brick, boolean bounceOff) {
        if (!Intersector.overlaps(this.getCircle(), brick.getRectangle())) {
            return false;
        }

        if (bounceOff) {
            Rectangle rect = brick.getRectangle();
            boolean sideHit = false;

            if (velocity.x > 0 && Intersector.intersectSegments(getRight(prevCircle), getRight(currCircle),
                    getTopLeft(rect), getBottomLeft(rect), null)) {
                multVelocityX(-1);
                sideHit = true;
            } else if (velocity.x < 0 && Intersector.intersectSegments(getLeft(prevCircle), getLeft(currCircle),
                    getTopRight(rect), getBottomRight(rect), null)) {
                multVelocityX(-1);
                sideHit = true;
            }

            if (velocity.y > 0 && Intersector.intersectSegments(getTop(prevCircle), getTop(currCircle),
                    getBottomLeft(rect), getBottomRight(rect), null)) {
                multVelocityY(-1);
                sideHit = true;
            } else if (velocity.y < 0 && Intersector.intersectSegments(getBottom(prevCircle), getBottom(currCircle),
                    getTopLeft(rect), getTopRight(rect), null)) {
                multVelocityY(-1);
                sideHit = true;
            }

            if (!sideHit) {
                multVelocityX(-1);
                multVelocityY(-1);
            }
        }

        return true;
    }

    public void multVelocityX(float m) {
        velocity.x *= m;
    }

    public void multVelocityY(float m) {
        velocity.y*= m;
    }

    @Override
    public void act(float dt) {
        //store previous position before and after updating
        prevCircle = getCircle();
        super.act(dt);
        currCircle = getCircle();
    }

    public Vector2 getTop(Circle c) {
        return new Vector2(c.x, c.y + c.radius);
    }
    public Vector2 getBottom(Circle c) {
        return new Vector2(c.x, c.y - c.radius);
    }

    public Vector2 getLeft(Circle c) {
        return new Vector2(c.x - c.radius, c.y);
    }

    public Vector2 getRight(Circle c) {
        return new Vector2(c.x + c.radius, c.y);
    }

    public Vector2 getBottomLeft(Rectangle r) {
        return new Vector2(r.getX(),r.getY());
    }

    public Vector2 getBottomRight(Rectangle r) {
        return new Vector2(r.getX() + r.getWidth(), r.getY());
    }

    public Vector2 getTopLeft(Rectangle r) {
        return new Vector2(r.getX(), r.getY() + r.getHeight());
    }

    public Vector2 getTopRight(Rectangle r) {
        return new Vector2(r.getX() + r.getWidth(), r.getY() + r.getHeight());
    }

}
