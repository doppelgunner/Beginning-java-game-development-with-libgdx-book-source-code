package base_A.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.utils.Array;

/**
 * Created by robertoguazon on 17/07/2016.
 */
public class GameUtils {

    //IMAGES

    public static Animation parseSpriteSheet(String fileName, int frameCols, int frameRows,
                                             float frameDuration, Animation.PlayMode mode) {
        Texture t = new Texture(fileName);
        t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        int frameWidth = t.getWidth() / frameCols;
        int frameHeight = t.getHeight() / frameRows;

        TextureRegion[][] temp = TextureRegion.split(t, frameWidth, frameHeight);
        TextureRegion[] frames = new TextureRegion[frameCols * frameRows];

        for (int index = 0, row = 0; row < frameRows; row++) {
            for (int col = 0; col < frameCols; col++) {
                frames[index] = temp[row][col];
                index++;

                Gdx.app.debug("spritesheet", fileName + "; index = " + index + "; row = " + row + ", col = " + col);
            }
        }

        Array<TextureRegion> framesArray = new Array<>(frames);
        return new Animation(frameDuration,framesArray,mode);
    }

    //creates an animation from a set of image files
    //name format: fileNamePrefix + N + fileNameSuffix, where 0 <= N < frameCount
    public static Animation parseImageFiles(String fileNamePrefix, String fileNameSuffix,
                                            int frameCount, float frameDuration,Animation.PlayMode mode) {

        TextureRegion[] frames = new TextureRegion[frameCount];
        for (int n = 0; n < frameCount; n++) {
            String fileName = fileNamePrefix + n + fileNameSuffix;
            Texture tex = new Texture(Gdx.files.internal(fileName));
            tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            frames[n] = new TextureRegion(tex);
        }

        Array<TextureRegion> framesArray = new Array<TextureRegion>(frames);
        return new Animation(frameDuration,framesArray,mode);
    }

    //creates animation from a single sprite sheet
    //with a subset of the frames, specified by an array
    public static Animation parseSpriteSheet(String fileName, int frameCols, int frameRows,
                                             int[] frameIndices, float frameDuration, Animation.PlayMode mode) {

        Texture t = new Texture(Gdx.files.internal(fileName));
        t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        int frameWidth = t.getWidth() / frameCols;
        int frameHeight = t.getHeight() / frameRows;

        TextureRegion[][] temp = TextureRegion.split(t,frameWidth, frameHeight);
        TextureRegion[] frames = new TextureRegion[frameCols * frameRows];
        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameCols; j++) {
                frames[index] = temp[i][j];
                index++;
            }
        }

        Array<TextureRegion> framesArray = new Array<TextureRegion>();
        for (int n = 0; n < frameIndices.length; n++) {
            int i = frameIndices[n];
            framesArray.add(frames[i]);
        }

        return new Animation(frameDuration, framesArray, mode);
    }



    //CONTACTS - box2d

    public static Object getContactObject(Contact theContact, Class theClass) {
        Object objA = theContact.getFixtureA().getBody().getUserData();
        Object objB = theContact.getFixtureB().getBody().getUserData();

        if (objA.getClass().equals(theClass)) {
            return objA;
        } else if (objB.getClass().equals(theClass)) {
            return objB;
        } else {
            return null;
        }
    }

    public static Object getContactObject(Contact theContact, Class theClass, String fixtureName) {
        Object objA = theContact.getFixtureA().getBody().getUserData();
        String nameA = (String)theContact.getFixtureA().getUserData();

        Object objB = theContact.getFixtureB().getBody().getUserData();
        String nameB = (String)theContact.getFixtureB().getUserData();

        if (objA.getClass().equals(theClass) && nameA.equals(fixtureName)) {
            return objA;
        } else if (objB.getClass().equals(theClass) && nameB.equals(fixtureName)) {
            return objB;
        } else {
            return null;
        }
    }



}
