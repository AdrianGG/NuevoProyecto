package mx.itesm.nuevoproyecto;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * Created by A. iram on 25/10/2015.
 */
public class Plataforma {
    public float x;
    public float y;
    public Sprite p;
    public ITextureRegion region;


    public Plataforma(float x, float y, Sprite p, ITextureRegion region){
        this.x=x; //
        this.y=y;
        this.region= region;
        this.p=p;
    }
}
