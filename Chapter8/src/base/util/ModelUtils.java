package base.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by robertoguazon on 15/08/2016.
 */
public class ModelUtils {

    public static ModelBuilder modelBuilder = new ModelBuilder();

    public static ModelInstance createBox(float xSize, float ySize, float zSize, Texture t, Color c) {
        Material boxMaterial = new Material();
        if (t != null) {
            boxMaterial.set(TextureAttribute.createDiffuse(t));
        }

        if (c != null) {
            boxMaterial.set(ColorAttribute.createDiffuse(c));
        }

        int usageCode =
                VertexAttributes.Usage.Position +
                VertexAttributes.Usage.ColorPacked +
                VertexAttributes.Usage.Normal +
                VertexAttributes.Usage.TextureCoordinates;

        Model boxModel = modelBuilder.createBox(xSize, ySize, zSize, boxMaterial,usageCode);
        Vector3 position = new Vector3(0,0,0);
        ModelInstance box = new ModelInstance(boxModel, position);
        return box;
    }


}
