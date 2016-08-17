package starfishcollector;

import base_C.BaseActor;
import base_C.BaseScreen;
import base_C.PhysicsActor;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

/**
 * Created by robertoguazon on 09/07/2016.
 */
public class TurtleLevel extends BaseScreen {

    private BaseActor ocean;
    private ArrayList<BaseActor> rockList;
    private ArrayList<BaseActor> starfishList;
    private PhysicsActor turtle;
    private int mapWidth = 800;
    private int mapHeight = 600;

    public TurtleLevel(Game game) {
        super(game);
    }

    @Override
    public void create() {
        ocean = new BaseActor();
        ocean.setTexture(new Texture(Gdx.files.internal("assets/chapter3/starfishcollector/water.jpg")));
        ocean.setPosition(0,0);
        mainStage.addActor(ocean);

        BaseActor overlay = ocean.clone();
        overlay.setPosition(-50,-50);
        overlay.setColor(1,1,1, 0.25f);
        uiStage.addActor(overlay);

        BaseActor rock = new BaseActor();
        rock.setTexture(new Texture(Gdx.files.internal("assets/chapter3/starfishcollector/rock.png")));
        rock.setEllipseBoundary();

        rockList = new ArrayList<>();
        int[] rockCoords = {200,0, 200,100, 250,200, 360,200, 470,200};

        for (int i = 0; i < 5; i++) {
            BaseActor r = rock.clone();
            //obtain coordinates from the array, both x and y, at the same time
            r.setPosition(rockCoords[2 * i], rockCoords[2 * i + 1]);
            mainStage.addActor(r);
            rockList.add(r);
        }

        BaseActor starfish = new BaseActor();
        starfish.setTexture(new Texture(Gdx.files.internal("assets/chapter3/starfishcollector/starfish.png")));
        starfish.setEllipseBoundary();
        starfishList = new ArrayList<>();

        int[] starfishCoords = {400,100, 100,400, 650,400};
        for (int i = 0; i < 3; i++) {
            BaseActor s = starfish.clone();
            s.setPosition(starfishCoords[2 * i], starfishCoords[2 * i + 1]);
            mainStage.addActor(s);
            starfishList.add(s);
        }

        turtle = new PhysicsActor();
        TextureRegion[] frames = new TextureRegion[6];
        for (int n = 1; n <= 6; n++) {
            String fileName = "assets/chapter3/starfishcollector/turtle-"+n+".png";
            Texture tex = new Texture(Gdx.files.internal(fileName));
            tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            frames[n-1] = new TextureRegion(tex);
        }
        Array<TextureRegion> framesArray = new Array<>(frames);
        Animation anim = new Animation(0.1f, framesArray, Animation.PlayMode.LOOP);
        turtle.storeAnimation("swim", anim);

        Texture frame1 = new Texture(Gdx.files.internal("assets/chapter3/starfishcollector/turtle-1.png"));
        turtle.storeAnimation("rest", frame1);

        turtle.setOrigin(turtle.getWidth() / 2, turtle.getHeight() / 2);
        turtle.setPosition(20,20);
        turtle.setRotation(90);
        turtle.setEllipseBoundary();
        turtle.setMaxSpeed(100);
        turtle.setDeceleration(200);
        mainStage.addActor(turtle);

        for (BaseActor s: starfishList) {
            System.out.println(s.getX() + ","+  s.getY());
            System.out.println("---");
        }
    }

    @Override
    public void update(float dt) {
        //process input
        turtle.setAccelerationXY(0,0);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
            turtle.rotateBy(90 * dt);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
            turtle.rotateBy(-90 * dt);
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W))
            turtle.accelerateForward(100);

        //set correct animation
        if (turtle.getSpeed() > 1 && turtle.getAnimationName().equals("rest"))
            turtle.setActiveAnimation("swim");
        if (turtle.getSpeed() < 1 && turtle.getAnimationName().equals("swim"))
            turtle.setActiveAnimation("rest");

        //bound turtle to screen
        turtle.setX(MathUtils.clamp(turtle.getX(), 0, mapWidth - turtle.getWidth()));
        turtle.setY(MathUtils.clamp(turtle.getY(), 0, mapHeight - turtle.getHeight()));

        for (BaseActor r: rockList) {
            turtle.overlaps(r, true);
        }

        ArrayList<BaseActor> removeList = new ArrayList<>();
        for (BaseActor s: starfishList) {
            if (turtle.overlaps(s, true)) {
                removeList.add(s);
            }
        }

        for (BaseActor b: removeList) {
            b.remove();
            starfishList.remove(b);
        }

    }

}
