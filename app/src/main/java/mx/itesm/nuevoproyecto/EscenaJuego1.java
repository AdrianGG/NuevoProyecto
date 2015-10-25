package mx.itesm.nuevoproyecto;

import android.util.Log;
import android.view.MotionEvent;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.JumpModifier;
import org.andengine.entity.modifier.MoveModifier;
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
    private ITiledTextureRegion regionBRetrocede;
    private ITiledTextureRegion regionBSalta;
    //private ITextureRegion regionPersonaje;
    private Sprite spriteFondo;
    private ITextureRegion regionObstaculo;

   // private AnimatedSprite personaje;
    private boolean personajeSaltando=false; // siempre se inicializa en falso
    private AnimatedSprite personaje;
    private Sprite obstaculo;
    //variables para el control
    private float px = 0;
    private float avanza = 0;
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
        regionPersonaje= cargarImagenMosaico("personajeStand.png",1268,200,1, 5);
        regionBCamina= cargarImagenMosaico("botoncamina.png",204,40,1,2);
        regionBRetrocede= cargarImagenMosaico("botoncamina.png",204,40,1,2);
        regionBSalta= cargarImagenMosaico("botonsalta.png",40,204,2,1);

    }


    @Override
    public void crearEscena() {
        spriteFondo = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, regionFondo);
       // agregarPersonaje(spriteFondo);
        //attachChild(spriteFondo);

        AutoParallaxBackground fondoAnimado	=	new	AutoParallaxBackground(0,0,0,10);
        fondoAnimado.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-3, spriteFondo));
        setBackground(fondoAnimado);
        setBackgroundEnabled(true);
        personaje= new AnimatedSprite(ControlJuego.ANCHO_CAMARA/4, ControlJuego.ALTO_CAMARA/3,	regionPersonaje, actividadJuego.getVertexBufferObjectManager());
        // Animacion Idle del personaje
        personaje.animate(100);
        attachChild(personaje);
        obstaculo =new Sprite(ControlJuego.ANCHO_CAMARA-300, ControlJuego.ALTO_CAMARA-250,	regionObstaculo,actividadJuego.getVertexBufferObjectManager());
        attachChild(obstaculo);
        final ButtonSprite bCamina = new ButtonSprite(210, 100, regionBCamina, actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed) {
                //Mantiene el boton dentro de la camara
                this.setPosition(ControlJuego.camara.getCenterX() - 390, ControlJuego.camara.getCenterY()- 200);
            }
            @Override
            public boolean onAreaTouched(TouchEvent event, float x, float y) {
                // Responder al touch del botón
                if (event.isActionDown())
                {
                    //El personaje mira hacia la derecha cuando se mueve a esa direccion
                    personaje.setScale(Math.abs(personaje.getScaleX()),personaje.getScaleY());
                    avanza = 10f;
                }
                else
                {
                    if(event.isActionUp()) {
                        avanza = 0;
                    }
                }

                return super.onAreaTouched(event, x, y);
            }
        };
        attachChild(bCamina);
        registerTouchArea(bCamina);
        ButtonSprite bRetrocede = new ButtonSprite(100, 100, regionBCamina, actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed) {
                //Mantiene el boton dentro de la camara
                this.setPosition(ControlJuego.camara.getCenterX()-500,ControlJuego.camara.getCenterY()-200);
            }
            @Override
            public boolean onAreaTouched(TouchEvent event, float x, float y) {
                // Responder al touch del botón
                if (event.isActionDown())
                {
                    //El personaje mira hacia la izquierda cuando se mueve a esa direccion
                    personaje.setScale(-Math.abs(personaje.getScaleX()),personaje.getScaleY());
                    avanza = -10f;
                }
                else
                {
                    if(event.isActionUp()) {
                        avanza = 0;
                    }
                }

                return super.onAreaTouched(event, x, y);
            }
        };
        attachChild(bRetrocede);
        registerTouchArea(bRetrocede);
        ButtonSprite bSalta = new ButtonSprite(1200, 100, regionBSalta,actividadJuego.getVertexBufferObjectManager()) {
            @Override
            protected void onManagedUpdate(float pSecondsElapsed) {
                //Mantiene el boton dentro de la camara
                this.setPosition(ControlJuego.camara.getCenterX()+500,ControlJuego.camara.getCenterY()-200);
            }
            @Override
            public boolean onAreaTouched(TouchEvent event, float x, float y) {
                // Responder al touch del botón

                if (event.getAction()==MotionEvent.ACTION_DOWN&&!personajeSaltando) {
                    // Saltar
                    float xa = personaje.getX();
                    float ya = personaje.getY();
                    float xn = xa;
                    float yn = ya;
                    JumpModifier salto = new JumpModifier(1, xa, px, ya, yn, -300);
                    personajeSaltando = true;
                    /*
                    //Maneja la animacion durante el salto
                    long tiempos[] = new long[2];
                    for (int i = 0; i < tiempos.length; i++) {
                        tiempos[i] = 0;
                    }
                    */
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
                            personajeSaltando = false;
                        }
                    };
                    personaje.registerEntityModifier(paralelo);
                } else {

                }
                return super.onAreaTouched(event, x, y);
            }
        };
            attachChild(bSalta);
            registerTouchArea(bSalta);
    }

    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        //Movimiento de izquierda a derecha
        super.onManagedUpdate(pSecondsElapsed);
        px = (float) (personaje.getX()+avanza);
        //---------------------------------
        //Esto sigue al personaje, asegurarse que los controles se queden dentro de la camara
        ControlJuego.camara.setChaseEntity(personaje);
        //-------------------------------------------------------------------------------------------------
        personaje.setPosition(px,personaje.getY());
        if(personaje.collidesWith(obstaculo)){
            personaje.setX(personaje.getX()-100);
            personaje.setY(obstaculo.getY()-100);
        }
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
        //Estas dos condiciones resetean el centro de la cámara para que otras escenas no se queden con el
        //de esta. NOTAS: se debe respetar el orden, se espera que este al liberar las escenas de todos los niveles
        ControlJuego.camara.setChaseEntity(null);
        ControlJuego.camara.setCenter(ControlJuego.ANCHO_CAMARA/2,ControlJuego.ALTO_CAMARA/2);
        //---------------------------------------------------------------------------------------------------------
        this.detachSelf();
        this.dispose();

    }

    @Override
    public void liberarRecursos() {
        regionFondo.getTexture().unload();
        regionFondo = null;
    }
}
