package mx.itesm.nuevoproyecto;

import android.view.MotionEvent;

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

/**
 * Created by A. iram on 02/10/2015.
 */
public class EscenaJuego1 extends EscenaBase implements IOnAreaTouchListener {
    private ITextureRegion regionFondo;
    private TiledTextureRegion regionPersonaje;
    private TiledTextureRegion regionPersonajeC;
    private ITextureRegion regionBCamina;
    private ITextureRegion regionBRetrocede;
    private ITextureRegion regionBSalta;
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
    //Instanciar botones para que sean accesibles en cualquier parte de esta clase
    public ButtonSprite bCamina;
    public ButtonSprite bRetrocede;
    public ButtonSprite bSalta;





    @Override
    public void cargarRecursos() {

        regionFondo = cargarImagen("prueba.jpg");
       // regionPersonaje=cargarImagen("personaje.jpg");
        regionObstaculo= cargarImagen("obstaculo.png");
        regionPersonajeC= cargarImagenMosaico("personajeCorrer.png",1893,200,1,8 );
        regionPersonaje= cargarImagenMosaico("personajeStand.png",1268,200,1, 5);
        regionBCamina= cargarImagen("boton2.png");
        regionBRetrocede= cargarImagen("boton3.png");
        regionBSalta= cargarImagen("boton1.png");

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
        personaje= new AnimatedSprite(ControlJuego.ANCHO_CAMARA/4, ControlJuego.ALTO_CAMARA/3,	regionPersonajeC, actividadJuego.getVertexBufferObjectManager());
       // personaje= new AnimatedSprite(ControlJuego.ANCHO_CAMARA/4, ControlJuego.ALTO_CAMARA/3,	regionPersonaje, actividadJuego.getVertexBufferObjectManager());
        // Animacion Idle del personaje
        personaje.animate(100);
        attachChild(personaje);
        obstaculo =new Sprite(ControlJuego.ANCHO_CAMARA-300, ControlJuego.ALTO_CAMARA-450,	regionObstaculo,actividadJuego.getVertexBufferObjectManager());
        attachChild(obstaculo);
        bCamina = new ButtonSprite(210, 100, regionBCamina, actividadJuego.getVertexBufferObjectManager()){

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
        bCamina.setScale(0.5f,0.5f);
        attachChild(bCamina);
        bRetrocede = new ButtonSprite(100, 100, regionBRetrocede, actividadJuego.getVertexBufferObjectManager()){

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
                    JumpModifier salto = new JumpModifier(1, xa,xn+(avanza*50), ya, yn, -300);
                    personajeSaltando = true;
                    /*
                    //Maneja la animacion durante el salto
                    long tiempos[] = new long[2];
                    for (int i = 0; i < tiempos.length; i++) {
                        tiempos[i] = 0;
                    }
                    */

                    registerTouchArea(bCamina);
                    registerTouchArea(bRetrocede);

                    ParallelEntityModifier paralelo = new ParallelEntityModifier(salto) {

                        @Override
                        protected void onModifierFinished(IEntity pItem) {
                            personaje.setPosition(personaje.getX(),personaje.getY());

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
        personaje.setPosition(px,personaje.getY());
        //---------------------------------
        //Esto sigue al personaje, asegurarse que los controles se queden dentro de la camara
        ControlJuego.camara.setChaseEntity(personaje);
        bCamina.setPosition(ControlJuego.camara.getCenterX() - 390, ControlJuego.camara.getCenterY() - 300);
        bRetrocede.setPosition(ControlJuego.camara.getCenterX()-500,ControlJuego.camara.getCenterY()-300);
        bSalta.setPosition(ControlJuego.camara.getCenterX()+500,ControlJuego.camara.getCenterY()-300);
        //-------------------------------------------------------------------------------------------------

        if(personaje.collidesWith(obstaculo)){
            //personaje.setX(personaje.getX()-100);
            //personaje.setY(obstaculo.getY()-100);
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

    @Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, ITouchArea pTouchArea, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        return true;
    }
}
