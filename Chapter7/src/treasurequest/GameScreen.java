package treasurequest;

import base_A.BaseActor;
import base_A.BaseGame;
import base_A.BaseScreen;
import base_A.PhysicsActor;
import base_A.util.GameUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

/**
 * Created by robertoguazon on 07/08/2016.
 */
public class GameScreen extends BaseScreen {

    private PhysicsActor player;
    private BaseActor door;
    private BaseActor key;
    private boolean hasKey;

    private BaseActor baseCoin;
    private ArrayList<BaseActor> coinList;

    private ArrayList<BaseActor> wallList;
    private ArrayList<BaseActor> removeList;

    private int tileSize = 64;
    private int tileCountWidth = 20;
    private int tileCountHeight = 20;

    //calculate game world dimensions
    public final int mapWidth = tileSize * tileCountWidth;
    public final int mapHeight = tileSize * tileCountHeight;

    private TiledMap tiledMap;

    private OrthographicCamera tiledCamera;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private int[] backgroundLayers = {0,1};
    private int[] foregroundLeyers = {2};

    private float playerSpeed = 100;

    public GameScreen(BaseGame game) {
        super(game);
    }

    @Override
    public void create() {
        player = new PhysicsActor();
        Texture playerTex = new Texture(Gdx.files.internal("assets/chapter7/treasurequest/general-single.png"));
        float t = 0.15f;
        player.storeAnimation(
                "down", GameUtils.parseSpriteSheet("assets/chapter7/treasurequest/general-48.png",
                        3,4, new int[] {0,1,2}, t, Animation.PlayMode.LOOP_PINGPONG)
        );

        player.storeAnimation(
                "left", GameUtils.parseSpriteSheet("assets/chapter7/treasurequest/general-48.png",
                        3, 4, new int[] {3,4,5}, t, Animation.PlayMode.LOOP_PINGPONG)
        );

        player.storeAnimation(
                "right", GameUtils.parseSpriteSheet("assets/chapter7/treasurequest/general-48.png",
                        3, 4, new int[] {6,7,8}, t, Animation.PlayMode.LOOP_PINGPONG)
        );

        player.storeAnimation(
                "up", GameUtils.parseSpriteSheet("assets/chapter7/treasurequest/general-48.png",
                        3, 4, new int[] {9,10,11}, t, Animation.PlayMode.LOOP_PINGPONG)
        );
        player.setSize(48,48);
        player.setEllipseBoundary();
        mainStage.addActor(player);



        key = new BaseActor();
        key.setTexture(new Texture(Gdx.files.internal("assets/chapter7/treasurequest/key.png")));
        key.setSize(36,24);
        key.setEllipseBoundary();
        mainStage.addActor(key);

        door = new BaseActor();
        door.setTexture(new Texture(Gdx.files.internal("assets/chapter7/treasurequest/door.png")));
        door.setRectangleBoundary();
        mainStage.addActor(door);

        baseCoin = new BaseActor();
        baseCoin.setTexture(new Texture(Gdx.files.internal("assets/chapter7/treasurequest/coin.png")));
        baseCoin.setEllipseBoundary();

        coinList = new ArrayList<BaseActor>();
        wallList = new ArrayList<BaseActor>();
        removeList = new ArrayList<BaseActor>();

        //set up tilemap, renderer, and camera
        tiledMap = new TmxMapLoader().load("assets/chapter7/treasurequest/game-map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        tiledCamera = new OrthographicCamera();
        tiledCamera.setToOrtho(false, viewWidth, viewHeight);
        tiledCamera.update();

        MapObjects objects = tiledMap.getLayers().get("ObjectData").getObjects();
        for (MapObject object : objects) {
            String name = object.getName();
            RectangleMapObject rectangleObject = (RectangleMapObject) object;
            Rectangle r = rectangleObject.getRectangle();

            switch (name) {
                case "player":
                    player.setPosition(r.x,r.y);
                    break;
                case "coin":
                    BaseActor coin = baseCoin.clone();
                    coin.setPosition(r.x,r.y);
                    mainStage.addActor(coin);
                    coinList.add(coin);

                    //set parent list
                    coin.setParentList(coinList);
                    break;
                case "door":
                    door.setPosition(r.x,r.y);
                    break;
                case "key":
                    key.setPosition(r.x,r.y);
                    break;
                default:
                    System.err.println("Unknown tilemap object: " + name);
            }
        }

        objects = tiledMap.getLayers().get("PhysicsData").getObjects();
        for (MapObject object : objects) {
            RectangleMapObject rectangleMapObject = (RectangleMapObject) object;
            Rectangle r = rectangleMapObject.getRectangle();

            BaseActor solid = new BaseActor();
            solid.setPosition(r.x, r.y);
            solid.setSize(r.width, r.height);
            solid.setRectangleBoundary();
            wallList.add(solid);

            //set parent list
            solid.setParentList(wallList);
        }

    }

    @Override
    public void update(float dt) {
        player.setVelocityXY(0,0);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.setVelocityXY(-playerSpeed,0);
            player.setActiveAnimation("left");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.setVelocityXY(playerSpeed,0);
            player.setActiveAnimation("right");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.setVelocityXY(0,playerSpeed);
            player.setActiveAnimation("up");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.setVelocityXY(0,-playerSpeed);
            player.setActiveAnimation("down");
        }
        if (player.getSpeed() < 1) {
            player.pauseAnimation();
            player.setAnimationFrame(1);
        } else {
            player.startAnimation();
        }



        for (BaseActor wall : wallList) {
            player.overlaps(wall, true);
        }

        if (key.getStage() != null && player.overlaps(key, false)) {
            hasKey = true;
            removeList.add(key);
        }

        if (door.getStage() != null && player.overlaps(door,true)) {
            if (hasKey) {
                removeList.add(door);
            }
        }

        for (BaseActor coin : coinList) {
            if (player.overlaps(coin, false)) {
                removeList.add(coin);
            }
        }

        for (BaseActor ba : removeList) {
            ba.destroy();
        }

        //camera adjustment
        Camera mainCamera = mainStage.getCamera();

        //center camera on player
        mainCamera.position.x = player.getX() + player.getOriginX();
        mainCamera.position.y = player.getY() + player.getOriginY();

        //bound camera to layout
        mainCamera.position.x = MathUtils.clamp(mainCamera.position.x, viewWidth / 2, mapWidth - viewWidth / 2);
        mainCamera.position.y = MathUtils.clamp(mainCamera.position.y, viewHeight / 2, mapHeight - viewHeight / 2);
        mainCamera.update();

        //adjust tilemap camera to stay in sync with main camera
        tiledCamera.position.x = mainCamera.position.x;
        tiledCamera.position.y = mainCamera.position.y;
        tiledCamera.update();
        tiledMapRenderer.setView(tiledCamera);

     }

    @Override
    public void render(float dt) {
        uiStage.act(dt);

        //pause only gameplay events, not UI events
        if (!isPaused()) {
            mainStage.act(dt);
            update(dt);
        }

        //render
        Gdx.gl.glClearColor(0,0,0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tiledMapRenderer.render(backgroundLayers);
        mainStage.draw();
        tiledMapRenderer.render(foregroundLeyers);
        uiStage.draw();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.P) {
            togglePaused();
        }

        if (keycode == Input.Keys.R) {
            game.setScreen(new GameScreen(game));
        }

        return false;
    }
}
