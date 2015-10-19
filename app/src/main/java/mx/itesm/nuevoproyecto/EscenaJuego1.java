package mx.itesm.nuevoproyecto;

import android.util.Log;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.JumpModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

/**
 * Created by A. iram on 02/10/2015.
 */
public class EscenaJuego1 extends EscenaBase {
    private ITextureRegion regionFondo;
    private TiledTextureRegion regionPersonaje;
    private ITiledTextureRegion regionBCamina;
    private ITiledTextureRegion regionBSalta;
    //private ITextureRegion regionPersonaje;
    private Sprite spriteFondo;
    private ITextureRegion regionObstaculo;

   // private AnimatedSprite personaje;
    private boolean personajeSaltando=true;
    private AnimatedSprite personaje;
    private Sprite obstaculo;
    //variables para el control
    private ITextureRegion regionControlBase;
    private ITextureRegion regionControlBoton;
    public int altoPersonaje= 158;
    public int anchoPersonaje= 600;
    public int columnasPersonaje = 4;



    @Override
    public void cargarRecursos() {
        regionControlBase = cargarImagen("control.png");
        regionControlBoton= cargarImagen("botonControl.png");
        regionFondo = cargarImagen("prueba.jpg");
       // regionPersonaje=cargarImagen("personaje.jpg");
        regionObstaculo= cargarImagen("obstaculo.png");
        regionPersonaje= cargarImagenMosaico("personaje2.png",3276,350,1, 10);
        regionBCamina= cargarImagenMosaico("botoncamina.png",204,40,1,2);
        regionBSalta= cargarImagenMosaico("botonsalta.png",40,204,2,1);

    }


    @Override
    public void crearEscena() {
        spriteFondo = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, regionFondo);
       // agregarPersonaje(spriteFondo);
        //attachChild(spriteFondo);

        AutoParallaxBackground fondoAnimado	=	new	AutoParallaxBackground(0,0,0,10);
        fondoAnimado.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-3,spriteFondo));
        setBackground(fondoAnimado);
        setBackgroundEnabled(true);
        personaje=new	AnimatedSprite(ControlJuego.ANCHO_CAMARA/4,
                ControlJuego.ALTO_CAMARA/3,	regionPersonaje,
                actividadJuego.getVertexBufferObjectManager());
        personaje.animate(200);
        attachChild(personaje);
        /*obstaculo =new Sprite(ControlJuego.ANCHO_CAMARA-300,
                ControlJuego.ALTO_CAMARA-250,	regionObstaculo,
                actividadJuego.getVertexBufferObjectManager());
        attachChild(obstaculo);*/
        ButtonSprite bCamina = new ButtonSprite(100, 100, regionBCamina,
                actividadJuego.getVertexBufferObjectManager()){
            @Override
            public boolean onAreaTouched(TouchEvent event, float x, float y) {
                // Responder al touch del botón
                float xa = personaje.getX();
                float ya = personaje.getY();
                float xn = xa;
                float yn = ya;
                //pModifier corre = new JumpModifier(1,xa,xa+10,ya,ya,-100);
                JumpModifier corre = new JumpModifier(0.3f,xa,xn+30,ya,ya,0);
                ParallelEntityModifier paralelo = new ParallelEntityModifier(corre) {
                    @Override

                    protected void onModifierFinished(IEntity pItem) {

                        super.onModifierFinished(pItem);

                    }
                };
                personaje.registerEntityModifier(paralelo);
                return super.onAreaTouched(event, x, y);
            }
        };
        attachChild(bCamina);
        registerTouchArea(bCamina);
        ButtonSprite bSalta = new ButtonSprite(100, 200, regionBSalta,
                actividadJuego.getVertexBufferObjectManager()){
            @Override
            public boolean onAreaTouched(TouchEvent event, float x, float y) {
                // Responder al touch del botón
                personajeSaltando=true;
                // Saltar
                float xa = personaje.getX();
                float ya = personaje.getY();
                float xn = xa;
                float yn = ya;
                JumpModifier salto = new JumpModifier(1,xa,xn,ya,yn,-200);
                ParallelEntityModifier paralelo = new ParallelEntityModifier(salto) {
                    @Override
                    protected void onModifierFinished(IEntity pItem) {
                        /*
                        //Aqui cambian la animacion del personaje cuando cae
                        long tiempos[] = new long[8];
                        for(int i=0; i<tiempos.length; i++) {
                            tiempos[i] = 48;

                        }
                        personaje.animate(tiempos,0,tiempos.length-1,true);
                        */
                        super.onModifierFinished(pItem);
                        personajeSaltando=false;
                    }

                };
                personaje.registerEntityModifier(paralelo);
                return super.onAreaTouched(event, x, y);

            }

        };
        attachChild(bSalta);
        registerTouchArea(bSalta);


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
