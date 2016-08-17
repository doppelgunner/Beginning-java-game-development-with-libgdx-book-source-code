package base_A;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by robertoguazon on 09/08/2016.
 */
public class Box2DActor extends AnimatedActor {

    protected BodyDef bodyDef;
    protected Body body;
    protected FixtureDef fixtureDef;

    protected Float maxSpeed;
    protected Float maxSpeedX;
    protected Float maxSpeedY;

    public Box2DActor() {
        body = null;
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();

        maxSpeed = null;
        maxSpeedX = null;
        maxSpeedY = null;
    }

    public void setStatic() {
        bodyDef.type = BodyDef.BodyType.StaticBody;
    }

    public void setDynamic() {
        bodyDef.type = BodyDef.BodyType.DynamicBody;
    }

    public void setFixedRotation() {
        bodyDef.fixedRotation = true;
    }

    public void setShapeRectangle() {
        setOriginCenter();
        bodyDef.position.set((getX() + getOriginX()) / 100, (getY() + getOriginY()) / 100);
        PolygonShape rect = new PolygonShape();
        rect.setAsBox(getWidth() / 200, getHeight() / 200);
        fixtureDef.shape = rect;
    }

    public void setShapeCircle() {
        setOriginCenter();
        bodyDef.position.set((getX() + getOriginX()) / 100,(getY() + getOriginY()) / 100);
        CircleShape circ = new CircleShape();
        circ.setRadius(getWidth() / 200);
        fixtureDef.shape = circ;
    }

    public void setPhysicsProperties(float density, float friction, float restitution) {
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;
    }

    public void setMaxSpeed(float f) {
        maxSpeed = f;
    }

    public void setMaxSpeedX(float f) {
        maxSpeedX = f;
    }

    public void setMaxSpeedY(float f) {
        maxSpeedY = f;
    }

    public void initializePhysics(World w) {
        body = w.createBody(bodyDef);
        Fixture f = body.createFixture(fixtureDef);
        f.setUserData("main");
        body.setUserData(this);
    }

    public Body getBody() {
        return body;
    }

    public void applyForce(Vector2 force) {
        body.applyForceToCenter(force, true);
    }

    public void applyImpulse(Vector2 impulse) {
        body.applyLinearImpulse(impulse,body.getPosition(), true);
    }

    public Vector2 getVelocity() {
        return body.getLinearVelocity();
    }

    public float getSpeed() {
        return getVelocity().len();
    }

    public void setVelocity(float vx, float vy) {
        body.setLinearVelocity(vx,vy);
    }

    public void setVelocity(Vector2 v) {
        body.setLinearVelocity(v);
    }

    public void setSpeed(float s) {
        setVelocity(getVelocity().setLength(s));
    }

    @Override
    public void act(float dt) {
        super.act(dt);

        //cap max speeds if they have been set
        if (maxSpeedX != null) {
            Vector2 v = getVelocity();
            v.x = MathUtils.clamp(v.x, -maxSpeedX, maxSpeedX);
            setVelocity(v);
        }
        if (maxSpeedY != null) {
            Vector2 v = getVelocity();
            v.y = MathUtils.clamp(v.y, -maxSpeedY, maxSpeedY);
            setVelocity(v);
        }
        if (maxSpeed != null) {
            float s = getSpeed();
            if (s > maxSpeed) {
                setSpeed(maxSpeed);
            }
        }

        //update image data - position and rotation - based on physics data
        Vector2 center = body.getWorldCenter();
        setPosition(100 * center.x - getOriginX(), 100 * center.y - getOriginY());

        float a = body.getAngle();
        setRotation(a * MathUtils.radiansToDegrees);
    }

    @Override
    public Box2DActor clone() {
        Box2DActor newbie = new Box2DActor();
        newbie.copy(this);
        return newbie;
    }



}
