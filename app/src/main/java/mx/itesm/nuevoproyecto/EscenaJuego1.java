package mx.itesm.nuevoproyecto;

import android.view.MotionEvent;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.JumpModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.util.GLState;

import java.util.ResourceBundle;

/**
 * Created by A. iram on 02/10/2015.
 */
public class EscenaJuego1 extends EscenaBase implements IOnAreaTouchListener {
    private TiledTextureRegion regionPersonaje;
    private TiledTextureRegion regionPersonajeC;
    private ITextureRegion regionBCamina;
    private ITextureRegion regionBRetrocede;
    private ITextureRegion regionBSalta;
    private ITextureRegion regionObstaculo;
    private ITextureRegion regionPiso;
    private boolean personajeSaltando=false; // siempre se inicializa en falso
    private AnimatedSprite personaje;
    private Sprite obstaculo;
    private Sprite piso;
    //variables para el control
    private float px = 0;
    public float py = 0;
    private float avanza = 0;
    JumpModifier salto;
    //Instanciar botones para que sean accesibles en cualquier parte de esta clase
    public ButtonSprite bCamina;
    public ButtonSprite bRetrocede;
    public ButtonSprite bSalta;





    @Override
    public void cargarRecursos() {
        regionObstaculo= cargarImagen("obstaculo.png");
        regionPiso = cargarImagen("pisoRosa.png");
        regionPersonaje = cargarImagenMosaico("mildoros.png", 2290, 1091, 5, 10);
        regionBCamina= cargarImagen("boton2.png");
        regionBRetrocede= cargarImagen("boton3.png");
        regionBSalta= cargarImagen("boton1.png");


    }


    @Override
    public void crearEscena() {

        personaje= new AnimatedSprite(ControlJuego.ANCHO_CAMARA/4, ControlJuego.ALTO_CAMARA/3,	regionPersonaje, actividadJuego.getVertexBufferObjectManager());
       // personaje= new AnimatedSprite(ControlJuego.ANCHO_CAMARA/4, ControlJuego.ALTO_CAMARA/3,	regionPersonaje, actividadJuego.getVertexBufferObjectManager());
        // Animacion Idle del personaje
        long tiempos[] = new long[50];
        for(int i=20; i<24; i++) {
            tiempos[i] = 100;
        }
        personaje.animate(tiempos, 0, tiempos.length - 1, true);
        attachChild(personaje);
        // Aqui iran todas las plataformas NOTA: todas se llaman obstaculo
        piso = new Sprite(personaje.getX(),personaje.getY()-200,regionPiso,actividadJuego.getVertexBufferObjectManager());
        attachChild(piso);
        piso = new Sprite(piso.getX()+(piso.getWidth()/2),personaje.getY()-200,regionPiso,actividadJuego.getVertexBufferObjectManager());
        attachChild(piso);
        //piso = new Sprite(piso.getX()+2*(piso.getWidth()/2),personaje.getY()-200,regionPiso,actividadJuego.getVertexBufferObjectManager());
        //attachChild(piso);
        setBackgroundEnabled(true);
        obstaculo = new Sprite(ControlJuego.ANCHO_CAMARA-300, ControlJuego.ALTO_CAMARA-450,	regionObstaculo,actividadJuego.getVertexBufferObjectManager());
        attachChild(obstaculo);
        obstaculo = new Sprite(ControlJuego.ANCHO_CAMARA-1000, ControlJuego.ALTO_CAMARA-250,	regionObstaculo,actividadJuego.getVertexBufferObjectManager());
        attachChild(obstaculo);
        //-------------------------------------------------
        bCamina = new ButtonSprite(210, 100, regionBCamina, actividadJuego.getVertexBufferObjectManager()){

            @Override
            public boolean onAreaTouched(TouchEvent event, float x, float y) {
                // Responder al touch del botón
                if (event.isActionDown())
                {
                    //El personaje mira hacia la derecha cuando se mueve a esa direccion
                    long tiempos[] = new long[50];
                    for(int i=10; i<15; i++) {
                        tiempos[i] = 100;
                    }
                    personaje.animate(tiempos,0,tiempos.length-1,true);
                    personaje.setScale(Math.abs(personaje.getScaleX()),personaje.getScaleY());
                    avanza = 10f;
                }
                else
                {
                    if(event.isActionUp()) {
                        long tiempos[] = new long[50];
                        for(int i=20; i<24; i++) {
                            tiempos[i] = 100;
                        }
                        personaje.animate(tiempos,0,tiempos.length-1,true);
                        avanza = 0;
                    }
                }

                return super.onAreaTouched(event, x, y);
            }
        };
        bCamina.setScale(0.5f,0.5f);
        attachChild(bCamina);
        bRetrocede = new ButtonSprite(100, 100, regionBRetrocede, actividadJuego.getVertexBufferObjectManager()){

            @Override
            public boolean onAreaTouched(TouchEvent event, float x, float y) {
                // Responder al touch del botón
                if (event.isActionDown())
                {
                    long tiempos[] = new long[50];
                    for(int i=10; i<15; i++) {
                        tiempos[i] = 100;
                    }
                    personaje.animate(tiempos,0,tiempos.length-1,true);
                    //El personaje mira hacia la izquierda cuando se mueve a esa direccion
                    personaje.setScale(-Math.abs(personaje.getScaleX()),personaje.getScaleY());
                    avanza = -10f;
                }
                else
                {
                    if(event.isActionUp()) {
                        if (event.isActionUp()) {
                            long tiempos[] = new long[50];
                            for (int i = 20; i < 24; i++) {
                                tiempos[i] = 100;
                            }
                            personaje.animate(tiempos, 0, tiempos.length - 1, true);
                            avanza = 0;
                        }
                    }
                }

                return super.onAreaTouched(event, x, y);
            }
        };
        bRetrocede.setScale(0.5f,0.5f);
        attachChild(bRetrocede);

        bSalta = new ButtonSprite(1200, 100, regionBSalta,actividadJuego.getVertexBufferObjectManager()) {

            @Override
            public boolean onAreaTouched(TouchEvent event, float x, float y) {
                // Responder al touch del botón

                if (event.getAction()==MotionEvent.ACTION_DOWN&&!personajeSaltando) {
                    // Saltar
                    float xa = personaje.getX();
                    float ya = personaje.getY();
                    float xn = xa;
                    float yn = ya;
                    //El parámetro avanza*50 sirve para "conservar" el momentum en el salto
                    salto = new JumpModifier(1, xa,xn+(avanza*50), ya, yn, -400);
                    personajeSaltando = true;
                    long tiempos[] = new long[50];
                    for (int i = 40; i < 42; i++) {
                        tiempos[i] = 200;
                    }
                    personaje.animate(tiempos, 0, tiempos.length - 1, false);

                    //registerTouchArea(bCamina);
                    //registerTouchArea(bRetrocede);

                    ParallelEntityModifier paralelo = new ParallelEntityModifier(salto) {

                        @Override
                        protected void onModifierFinished(IEntity pItem) {
                            if(bRetrocede.isPressed()||bCamina.isPressed()){
                                long tiempos[] = new long[50];
                                for(int i=10; i<15; i++) {
                                    tiempos[i] = 100;
                                }
                                personaje.animate(tiempos,0,tiempos.length-1,true);
                            }
                            else{
                                long tiempos[] = new long[50];
                                for (int i = 20; i < 24; i++) {
                                    tiempos[i] = 100;
                                }
                                personaje.animate(tiempos, 0, tiempos.length - 1, true);
                            }
                            //personaje.setPosition(personaje.getX(),personaje.getY());
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
        bSalta.setScale(0.5f,0.5f);
        attachChild(bSalta);

    }

    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {

        registerTouchArea(bCamina);
        registerTouchArea(bRetrocede);
        registerTouchArea(bSalta);
        //Movimiento de izquierda a derecha
        super.onManagedUpdate(pSecondsElapsed);
        px = (float) (personaje.getX()+avanza);
        py = (float) (personaje.getY()-20);
        personaje.setPosition(px,personaje.getY());
        //---------------------------------
        //Esto sigue al personaje, asegurarse que los controles se queden dentro de la camara
        ControlJuego.camara.setChaseEntity(personaje);
        bCamina.setPosition(personaje.getX() - 390, personaje.getY() - 300);
        bRetrocede.setPosition(personaje.getX()-500,personaje.getY()-300);
        bSalta.setPosition(personaje.getX() + 500, personaje.getY()-300);
        //-------------------------------------------------------------------------------------------------
        if(personaje.collidesWith(piso)){
            //personaje.setPosition(personaje.getX(),piso.getY()+(piso.getHeight()));
            personaje.unregisterEntityModifier(salto);
        }else{
            if(personaje.collidesWith(obstaculo)){
                //personaje.setPosition(personaje.getX(),piso.getY()+obstaculo.getHeight());
                personaje.unregisterEntityModifier(salto);
            }else{

                personaje.setPosition(personaje.getX(),py);
            }
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

    }

    @Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, ITouchArea pTouchArea, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        return true;
    }
}
