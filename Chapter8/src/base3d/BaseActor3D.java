package base3d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by robertoguazon on 14/08/2016.
 */
public class BaseActor3D {

    private ModelInstance modelData;
    private final Vector3 position;
    private final Quaternion rotation;
    private final Vector3 scale;

    public BaseActor3D() {
        modelData = null;
        position = new Vector3(0,0,0);
        rotation = new Quaternion();
        scale = new Vector3(1,1,1);
    }

    public void setModelInstance(ModelInstance modelData) {
        this.modelData = modelData;
    }

    public Matrix4 calculateTransform() {
        return new Matrix4(position, rotation, scale);
    }

    public void act(float dt) {
        modelData.transform.set(calculateTransform());
    }

    public void draw(ModelBatch batch, Environment env) {
        batch.render(modelData, env);
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position.set(position);
    }

    public void setPosition(float x, float y, float z) {
        this.position.set(x,y,z);
    }

    public void addPosition(Vector3 v) {
        position.add(v);
    }

    public void addPosition(float x, float y, float z) {
        position.add(x,y,z);
    }

    public float getTurnAngle() {
        return rotation.getAngleAround(0,-1,0);
    }

    public void setTurnAngle(float degrees) {
        rotation.set(new Quaternion(Vector3.Y, degrees));
    }

    public void turn(float degrees) {
        rotation.mul(new Quaternion(Vector3.Y,-degrees));
    }

    public void moveForward(float dist) {
        addPosition(rotation.transform(new Vector3(0,0,-1)).scl(dist));
    }

    public void moveUp(float dist) {
        addPosition(rotation.transform(new Vector3(0,1,0)).scl(dist));
    }

    public void moveRight(float dist) {
        addPosition(rotation.transform(new Vector3(1,0,0)).scl(dist));
    }

    public void setColor(Color c) {
        for (Material m : modelData.materials) {
            m.set(ColorAttribute.createDiffuse(c));
        }
    }

    public BaseActor3D clone() {
        BaseActor3D newbie = new BaseActor3D();
        newbie.copy(this);
        return newbie;
    }

    public void copy(BaseActor3D orig) {
        this.modelData = new ModelInstance(orig.modelData);
        this.position.set(orig.position);
        this.rotation.set(orig.rotation);
        this.scale.set(orig.scale);
    }

}
