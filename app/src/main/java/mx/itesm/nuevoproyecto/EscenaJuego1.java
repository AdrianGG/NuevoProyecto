package mx.itesm.nuevoproyecto;

import android.util.Log;

import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.andengine.entity.modifier.MoveByModifier;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

/**
 * Created by A. iram on 02/10/2015.
 */
public class EscenaJuego1 extends EscenaBase {
    private ITextureRegion regionFondo;
    //private TiledTextureRegion regionPersonaje;
    private ITextureRegion regionPersonaje;
    private Sprite spriteFondo;
    private ITextureRegion regionObstaculo;

   // private AnimatedSprite personaje;
    private Sprite personaje;
    public static final int ANCHO_CAMARA = 1280;
    public static final int ALTO_CAMARA= 800;
    private Sprite obstaculo;
    //variables para el control
    private ITextureRegion regionControlBase;
    private ITextureRegion regionControlBoton;
    public int altoPersonaje= 100;
    public int anchoPersonaje= 100;
    public int filasPersonaje= 2;

    @Override
    public void cargarRecursos() {
        regionControlBase = cargarImagen("baseControl.png");
        regionControlBoton= cargarImagen("botonControl.png");
        regionFondo = cargarImagen("escenajuego.jpg");
        regionPersonaje=cargarImagen("personaje.jpg");
        regionObstaculo= cargarImagen("obstaculo.jpg");
       // regionPersonaje= cargarImagenMosaico("personaje.jpg",anchoPersonaje,altoPersonaje,1,filasPersonaje);

    }


    @Override
    public void crearEscena() {
        spriteFondo = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, regionFondo);
       // agregarPersonaje(spriteFondo);
        //attachChild(spriteFondo);

        AutoParallaxBackground fondoAnimado	=	new	AutoParallaxBackground(1,1,1,5);
        fondoAnimado.attachParallaxEntity(new	ParallaxBackground.ParallaxEntity(-3,spriteFondo));
        setBackground(fondoAnimado);
        personaje=	new Sprite(ControlJuego.ANCHO_CAMARA/2,
                ControlJuego.ALTO_CAMARA/2,	regionPersonaje,
                actividadJuego.getVertexBufferObjectManager());
               /* new	AnimatedSprite(ControlJuego.ANCHO_CAMARA/2,
                ControlJuego.ALTO_CAMARA/2,	regionPersonaje,
                actividadJuego.getVertexBufferObjectManager());
        personaje.animate(200);*/
        attachChild(personaje);
        obstaculo =new Sprite(ControlJuego.ANCHO_CAMARA/2,
                ControlJuego.ALTO_CAMARA/2,	regionObstaculo,
                actividadJuego.getVertexBufferObjectManager());
        attachChild(obstaculo);
        DigitalOnScreenControl controlDigital	=	new	DigitalOnScreenControl(64,	64,actividadJuego.getEngine().getCamera(),regionControlBase,
                regionControlBoton,	0.1f,	true,actividadJuego.getVertexBufferObjectManager(),	new	BaseOnScreenControl.IOnScreenControlListener()	{

            @Override
            public void onControlChange(BaseOnScreenControl pBaseOnScreenControl,
                                        float pValueX,	float pValueY)	{
                int offsetX	=	(int)(pValueX*40);
                int offsetY	=	(int)(pValueY*40);
                MoveByModifier movimiento=new MoveByModifier(0.1f,	offsetX,	offsetY);
                personaje.registerEntityModifier(movimiento);
            }
        });
        controlDigital.setAlpha(0.4f); //	Transparencia
        setChildScene(controlDigital);


    }

    private TiledTextureRegion cargarImagenMosaico(String archivo,int ancho,int alto,int renglones,int columnas) {
        BuildableBitmapTextureAtlas texturaMosaico= new BuildableBitmapTextureAtlas(actividadJuego.getTextureManager(),ancho,alto);
        TiledTextureRegion region=	BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(texturaMosaico, actividadJuego, archivo, columnas, renglones);
        texturaMosaico.load();
        try	{
            texturaMosaico.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource,BitmapTextureAtlas>(0,	0,	0));
        }catch(ITextureAtlasBuilder.TextureAtlasBuilderException	e){
            Log.d("cargarImagenMosaico()", "No	se	puede	cargar	la	imagen:	" + archivo);
        }
        return region;
    }

    @Override
    public void onBackKeyPressed() {
        // en esta parte no se regresa a la animacion, regresa hasta el menu principal
        admEscenas.crearEscenaMenu();
        admEscenas.setEscena(TipoEscena.ESCENA_MENU);
        admEscenas.liberarEscenaJuego1();
    }

    @Override
    public TipoEscena getTipoEscena() {
        return TipoEscena.ESCENA_JUEGO1;
    }

    @Override
    public void liberarEscena() {
        this.detachSelf();
        this.dispose();

    }

    @Override
    public void liberarRecursos() {
        regionFondo.getTexture().unload();
        regionFondo = null;
    }
}
