package project3d;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by robertoguazon on 13/08/2016.
 */
public class TheTest implements ApplicationListener {

    public Environment environment;
    public PerspectiveCamera camera;
    public ModelBatch modelBatch;
    public ModelInstance boxInstance;

    @Override
    public void create() {
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f,0.4f,0.4f, 0.1f));

        DirectionalLight dLight = new DirectionalLight();
        Color lightColor = new Color(0.75f,0.75f,0.75f,1f);
        Vector3 lightVector = new Vector3(-1.0f,-0.75f,-0.25f);
        dLight.set(lightColor, lightVector);
        environment.add(dLight);

        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.near = 0.1f;
        camera.far = 1000f;
        camera.position.set(10f, 10f, 10f);
        camera.lookAt(0,0,0);
        camera.update();

        modelBatch = new ModelBatch();

        ModelBuilder modelBuilder = new ModelBuilder();
        Material boxMaterial = new Material();
        boxMaterial.set(ColorAttribute.createDiffuse(Color.MAROON));

        int usageCode = VertexAttributes.Usage.Position + VertexAttributes.Usage.ColorPacked + VertexAttributes.Usage.Normal;

        Model boxModel = modelBuilder.createBox(5,5,5,boxMaterial,usageCode);
        boxInstance = new ModelInstance(boxModel);
    }

    public void update(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            boxInstance.transform.rotate(0,0.5f,0, -90 * dt);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            boxInstance.transform.rotate(0,0.5f,0, 90 * dt);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            boxInstance.transform.rotate(0.5f,0,0, -90 * dt);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            boxInstance.transform.rotate(0.5f,0,0, 90 * dt);
        }
    }

    @Override
    public void render() {
        update(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClearColor(1,1,1, 1);
        Gdx.gl.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(camera);
        modelBatch.render(boxInstance,environment);
        modelBatch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
