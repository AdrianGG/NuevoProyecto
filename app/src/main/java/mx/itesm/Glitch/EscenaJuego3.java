package mx.itesm.Glitch;

/**
 *
 */
import android.view.MotionEvent;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.JumpModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

public class EscenaJuego3 extends EscenaBase implements IOnAreaTouchListener {
    private TiledTextureRegion regionPersonaje;
    private TiledTextureRegion regionPersonajeC;
    private ITextureRegion regionBCamina;
    private ITextureRegion regionBRetrocede;
    private ITextureRegion regionBSalta;
    private ITextureRegion regionObstaculo;
    private ITextureRegion regionSensor;
    private ITextureRegion regionPiso;
    private ITextureRegion regionMeta;
    private ITextureRegion regionPlataforma;
    private ITextureRegion regionPlataforma2;
    private ITextureRegion regionPlataforma3;
    private ITextureRegion regionFondo;
    private ITextureRegion regionEnemigo3;
    private ITextureRegion regionEnemigo4;
    private ITextureRegion regionEnemigo5;
    private ITextureRegion regionVida1;
    private ITextureRegion regionVida2;
    private boolean personajeSaltando=false; // siempre se inicializa en falso
    private AnimatedSprite personaje;
    private Sprite obstaculo;
    private Sprite sensor;
    private Sprite piso;
    private Sprite meta;
    private Sprite plataforma1;
    private Sprite plataforma2;
    private Sprite plataforma3;
    private Sprite fondo;
    private Sprite enemigo3;
    private Sprite enemigo4;
    private Sprite enemigo5;
    //variables para el control
    private float px = 0;
    public float py = 0;
    private float avanza = 0;
    private float cae = 20;
    static ParallelEntityModifier paralelo;
    public boolean falls = true;
    //Instanciar botones para que sean accesibles en cualquier parte de esta clase
    public ButtonSprite bCamina;
    public ButtonSprite bRetrocede;
    public ButtonSprite bSalta;
    public int vidas=2;
    public Sprite bVida1;
    public Sprite bVida2;

    @Override
    public void cargarRecursos() {
        regionFondo= cargarImagen("glitchfondomasalto.png");
        regionObstaculo= cargarImagen("base1.png");
        regionSensor= cargarImagen("sensor.png");
        regionPiso = cargarImagen("pisoRosa.png");
        regionPersonaje = cargarImagenMosaico("mildoros.png", 2290, 1091, 5, 10);
        regionBCamina= cargarImagen("boton2.png");
        regionBRetrocede= cargarImagen("boton3.png");
        regionBSalta= cargarImagen("boton1.png");
        regionMeta=cargarImagen("meta2.png");
        regionPlataforma= cargarImagen("base2.png");
        regionPlataforma2=cargarImagen("base3.png");
        regionPlataforma3= cargarImagen("base4.png");
        regionEnemigo3= cargarImagen("engrane3azul.png");
        regionEnemigo4= cargarImagen("engrane4azul.png");
        regionEnemigo5= cargarImagen("engrane5azul.png");
    }

    @Override
    public void crearEscena() {
        float xF = ControlJuego.ANCHO_CAMARA/2-3158;
        for(int i = 0; i<=6; i++){
            fondo=new Sprite(xF+(3158*i),(ControlJuego.ALTO_CAMARA/2+150),regionFondo,actividadJuego.getVertexBufferObjectManager());
            attachChild(fondo);
        }
        for(int i = 0; i<=5; i++){
            fondo=new Sprite(xF+(3158*i),(ControlJuego.ALTO_CAMARA/2+1320),regionFondo,actividadJuego.getVertexBufferObjectManager());
            attachChild(fondo);
        }
        //Meta a llegar en el nivel
        meta= new Sprite(ControlJuego.ANCHO_CAMARA+10200,ControlJuego.ALTO_CAMARA-50,regionMeta,actividadJuego.getVertexBufferObjectManager());
        attachChild(meta);
        personaje= new AnimatedSprite(ControlJuego.ANCHO_CAMARA/4, ControlJuego.ALTO_CAMARA/3,	regionPersonaje, actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed){
                if(this.getY() < ControlJuego.ALTO_CAMARA/3 -800){
                    //System.out.println("MORIÍ");
                    personaje.setPosition(ControlJuego.ANCHO_CAMARA/4, ControlJuego.ALTO_CAMARA/3);


                }
                super.onManagedUpdate(pSecondsElapsed);
            }

        };
        sensor = new Sprite(personaje.getX(),personaje.getY()-100,regionSensor,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if(personaje.getScaleX()>0){
                    this.setPosition(personaje.getX()+35,personaje.getY()-100);
                }
                if(personaje.getScaleX()<0){
                    this.setPosition(personaje.getX()-35,personaje.getY()-100);
                }
            };
        };
        attachChild(sensor);
        // personaje= new AnimatedSprite(ControlJuego.ANCHO_CAMARA/4, ControlJuego.ALTO_CAMARA/3,	regionPersonaje, actividadJuego.getVertexBufferObjectManager());
        // Animacion Idle del personaje
        long tiempos[] = new long[50];
        for(int i=20; i<24; i++) {
            tiempos[i] = 100;
        }
        personaje.animate(tiempos, 0, tiempos.length - 1, true);
        attachChild(personaje);//

        // Aqui iran todas las plataformas NOTA: todas se llaman obstaculo o plataforma
        piso = new Sprite(personaje.getX(),personaje.getY()-200,regionPiso,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if (sensor.collidesWith(this)&&!personajeSaltando)
                {

                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight() + 75));


                }

            };
        };
        ;
        attachChild(piso);

        piso = new Sprite(piso.getX()+(piso.getWidth()/2),personaje.getY()-200,regionPiso,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if (sensor.collidesWith(this)&&!personajeSaltando)
                {

                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight() + 75));
                }

            };
        };
        ;
        attachChild(piso);
        piso = new Sprite(personaje.getX()+(piso.getWidth()),personaje.getY()-200,regionPiso,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if (sensor.collidesWith(this)&&!personajeSaltando)
                {
                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight() + 75));
                }

            };
        };
        ;
        attachChild(piso);
        piso = new Sprite(personaje.getX()+(piso.getWidth()+800),personaje.getY()-200,regionPiso,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if (sensor.collidesWith(this)&&!personajeSaltando)
                {

                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight() + 75));


                }

            };
        };

        ;
        attachChild(piso);
        piso = new Sprite(personaje.getX()+(piso.getWidth()+1100),personaje.getY()-200,regionPiso,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if (sensor.collidesWith(this)&&!personajeSaltando)
                {

                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight() + 75));


                }

            };
        };

        ;
        attachChild(piso);
        piso = new Sprite(personaje.getX()+(piso.getWidth()+1500),personaje.getY()-200,regionPiso,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if (sensor.collidesWith(this)&&!personajeSaltando)
                {

                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight() + 75));


                }

            };
        };

        ;
        attachChild(piso);
        piso = new Sprite(personaje.getX()+(piso.getWidth()+1900),personaje.getY()-200,regionPiso,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if (sensor.collidesWith(this)&&!personajeSaltando)
                {

                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight() + 75));


                }

            };
        };

        ;
        attachChild(piso);
        piso = new Sprite(personaje.getX()+(piso.getWidth()+2400),personaje.getY()-200,regionPiso,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if (sensor.collidesWith(this)&&!personajeSaltando)
                {

                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight() + 75));


                }

            };
        };

        ;
        attachChild(piso);

        piso = new Sprite(personaje.getX()+(piso.getWidth()+3000),personaje.getY()-200,regionPiso,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if (sensor.collidesWith(this)&&!personajeSaltando)
                {

                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight() + 75));


                }

            };
        };

        ;
        attachChild(piso);

        piso = new Sprite(personaje.getX()+(piso.getWidth()+3600),personaje.getY()-200,regionPiso,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if (sensor.collidesWith(this)&&!personajeSaltando)
                {

                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight() + 75));


                }

            };
        };

        ;
        attachChild(piso);

        //piso cortado

        //piso = new Sprite(piso.getX()+2*(piso.getWidth()/2),personaje.getY()-200,regionPiso,actividadJuego.getVertexBufferObjectManager());
        //attachChild(piso);
        setBackgroundEnabled(true);

        //-450 altura minima de las plataformas
        obstaculo = new Sprite(ControlJuego.ANCHO_CAMARA-300, ControlJuego.ALTO_CAMARA-450,	regionObstaculo,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed) {
                if (sensor.collidesWith(this)&&!bSalta.isPressed())
                {
                    personaje.unregisterEntityModifier(paralelo);
                    personajeSaltando=false;
                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight() + 30));
                }
            };
        };
        attachChild(obstaculo); //1ra en el nivel
        plataforma1 = new Sprite(ControlJuego.ANCHO_CAMARA+450, ControlJuego.ALTO_CAMARA-230,	regionPlataforma,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if (sensor.collidesWith(this)&&!bSalta.isPressed()){
                    personaje.unregisterEntityModifier(paralelo);
                    personajeSaltando=false;
                    personaje.setPosition(personaje.getX(),this.getY() + (this.getHeight()));
                }

            };
        };;
        attachChild(plataforma1); //2da en el nivel
        plataforma2 = new Sprite(ControlJuego.ANCHO_CAMARA+1200, ControlJuego.ALTO_CAMARA-450,	regionPlataforma2,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if (sensor.collidesWith(this)&&!bSalta.isPressed()){
                    personaje.unregisterEntityModifier(paralelo);
                    personajeSaltando=false;
                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight() + 30));

                }

            };
        };;
        attachChild(plataforma2); //3ra en el nivel
        plataforma1 = new Sprite(ControlJuego.ANCHO_CAMARA+1450, ControlJuego.ALTO_CAMARA-250,	regionPlataforma,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if (sensor.collidesWith(this)&&!bSalta.isPressed()){
                    personaje.unregisterEntityModifier(paralelo);
                    personajeSaltando=false;
                    personaje.setPosition(personaje.getX(),this.getY() + (this.getHeight()));
                }

            };
        };;
        attachChild(plataforma1); //4ta en el nivel
        obstaculo = new Sprite(ControlJuego.ANCHO_CAMARA+2450, ControlJuego.ALTO_CAMARA-250,	regionObstaculo,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed) {
                if (sensor.collidesWith(this)&&!bSalta.isPressed())
                {
                    personaje.unregisterEntityModifier(paralelo);
                    personajeSaltando=false;
                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight() + 30));
                }
            };
        };
        attachChild(obstaculo); //5ta en el nivel

        plataforma3 = new Sprite(ControlJuego.ANCHO_CAMARA+3200, ControlJuego.ALTO_CAMARA-125,	regionPlataforma3,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if (sensor.collidesWith(this)&&!bSalta.isPressed()){
                    personaje.unregisterEntityModifier(paralelo);
                    personajeSaltando=false;
                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight() -30));
                }

            };
        };;
        attachChild(plataforma3); //6ta en el nivel

        plataforma2 = new Sprite(ControlJuego.ANCHO_CAMARA+3950, ControlJuego.ALTO_CAMARA-300,	regionPlataforma2,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if (sensor.collidesWith(this)&&!bSalta.isPressed()){
                    personaje.unregisterEntityModifier(paralelo);
                    personajeSaltando=false;
                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight()+30));


                }

            };
        };;
        attachChild(plataforma2); //7ma en el nivel

        plataforma1 = new Sprite(ControlJuego.ANCHO_CAMARA+4450, ControlJuego.ALTO_CAMARA-455,	regionPlataforma,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if (sensor.collidesWith(this)&&!bSalta.isPressed()){
                    personaje.unregisterEntityModifier(paralelo);
                    personajeSaltando=false;
                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight()));
                }

            };
        };;
        attachChild(plataforma1); //8va en el nivel
        plataforma1 = new Sprite(ControlJuego.ANCHO_CAMARA+5200, ControlJuego.ALTO_CAMARA-250,	regionPlataforma,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if (sensor.collidesWith(this)&&!bSalta.isPressed()){
                    personaje.unregisterEntityModifier(paralelo);
                    personajeSaltando=false;
                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight()));
                }

            };
        };;
        attachChild(plataforma1); //9na en el nivel
        plataforma3 = new Sprite(ControlJuego.ANCHO_CAMARA+5950, ControlJuego.ALTO_CAMARA-450,	regionPlataforma3,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if (sensor.collidesWith(this)&&!bSalta.isPressed()){
                    personaje.unregisterEntityModifier(paralelo);
                    personajeSaltando=false;
                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight() -30));
                }

            };
        };;
        attachChild(plataforma3); //10ma en el nivel
        plataforma2 = new Sprite(ControlJuego.ANCHO_CAMARA+6450, ControlJuego.ALTO_CAMARA-375,	regionPlataforma2,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if (sensor.collidesWith(this)&&!bSalta.isPressed()){
                    personaje.unregisterEntityModifier(paralelo);
                    personajeSaltando=false;
                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight()+30));


                }

            };
        };;
        attachChild(plataforma2); //11va en el nivel
        obstaculo = new Sprite(ControlJuego.ANCHO_CAMARA+7200, ControlJuego.ALTO_CAMARA-300,	regionObstaculo,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed) {
                if (sensor.collidesWith(this)&&!bSalta.isPressed())
                {
                    personaje.unregisterEntityModifier(paralelo);
                    personajeSaltando=false;
                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight() + 30));
                }
            };
        };
        attachChild(obstaculo); //12va en el nivel
        plataforma2 = new Sprite(ControlJuego.ANCHO_CAMARA+7950, ControlJuego.ALTO_CAMARA-125,	regionPlataforma2,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if (sensor.collidesWith(this)&&!bSalta.isPressed()){
                    personaje.unregisterEntityModifier(paralelo);
                    personajeSaltando=false;
                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight()+30));


                }

            };
        };;
        attachChild(plataforma2); //13va en el nivel
        plataforma3 = new Sprite(ControlJuego.ANCHO_CAMARA+8594, ControlJuego.ALTO_CAMARA-250,	regionPlataforma3,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if (sensor.collidesWith(this)&&!bSalta.isPressed()){
                    personaje.unregisterEntityModifier(paralelo);
                    personajeSaltando=false;
                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight() -30));
                }

            };
        };;
        attachChild(plataforma3); //14va en el nivel
        obstaculo = new Sprite(ControlJuego.ANCHO_CAMARA+9450, ControlJuego.ALTO_CAMARA-250,	regionObstaculo,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed) {
                if (sensor.collidesWith(this)&&!bSalta.isPressed())
                {
                    personaje.unregisterEntityModifier(paralelo);
                    personajeSaltando=false;
                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight() + 30));
                }
            };
        };
        attachChild(obstaculo); //15va en el nivel

        //Enemigos
        enemigo5 = new Sprite(ControlJuego.ANCHO_CAMARA+3200, ControlJuego.ALTO_CAMARA-325,	regionEnemigo5,actividadJuego.getVertexBufferObjectManager()) {
            protected void onManagedUpdate(float pSecondsElapsed) {
                if(personaje.collidesWith(this)){
                    vidas --;
                }
            };
        };
        attachChild(enemigo5);
        enemigo5 = new Sprite(ControlJuego.ANCHO_CAMARA+3200, ControlJuego.ALTO_CAMARA-600,	regionEnemigo5,actividadJuego.getVertexBufferObjectManager()) {
            protected void onManagedUpdate(float pSecondsElapsed) {
                if(personaje.collidesWith(this)){
                    vidas --;
                }
            };
        };
        attachChild(enemigo5);
        enemigo4 = new Sprite(ControlJuego.ANCHO_CAMARA+1900, ControlJuego.ALTO_CAMARA-500,	regionEnemigo4,actividadJuego.getVertexBufferObjectManager()) {
            protected void onManagedUpdate(float pSecondsElapsed) {
                if(personaje.collidesWith(this)){
                    vidas --;
                }
            };
        };
        attachChild(enemigo4);
        enemigo4 = new Sprite(ControlJuego.ANCHO_CAMARA+5700, ControlJuego.ALTO_CAMARA+100,	regionEnemigo4,actividadJuego.getVertexBufferObjectManager()) {
            protected void onManagedUpdate(float pSecondsElapsed) {
                if(personaje.collidesWith(this)){
                    vidas --;
                }
            };
        };
        attachChild(enemigo4);
        enemigo3 = new Sprite(ControlJuego.ANCHO_CAMARA+500, ControlJuego.ALTO_CAMARA-500,	regionEnemigo3,actividadJuego.getVertexBufferObjectManager()) {
            protected void onManagedUpdate(float pSecondsElapsed) {
                if(personaje.collidesWith(this)){
                    vidas --;
                }
            };
        };
        attachChild(enemigo3);
        enemigo3 = new Sprite(ControlJuego.ANCHO_CAMARA+6950, ControlJuego.ALTO_CAMARA-600,	regionEnemigo3,actividadJuego.getVertexBufferObjectManager()) {
            protected void onManagedUpdate(float pSecondsElapsed) {
                if(personaje.collidesWith(this)){
                    vidas --;
                }
            };
        };
        attachChild(enemigo3);
        enemigo4 = new Sprite(ControlJuego.ANCHO_CAMARA+1200, ControlJuego.ALTO_CAMARA-450,	regionEnemigo4,actividadJuego.getVertexBufferObjectManager()) {
            protected void onManagedUpdate(float pSecondsElapsed) {
                if(personaje.collidesWith(this)){
                    vidas --;
                }
            };
        };
        attachChild(enemigo4);
        enemigo4 = new Sprite(ControlJuego.ANCHO_CAMARA+4450, ControlJuego.ALTO_CAMARA-125,	regionEnemigo4,actividadJuego.getVertexBufferObjectManager()) {
            protected void onManagedUpdate(float pSecondsElapsed) {
                if(personaje.collidesWith(this)){
                    vidas --;
                }
            };
        };
        attachChild(enemigo4);
        enemigo4 = new Sprite(ControlJuego.ANCHO_CAMARA+5950, ControlJuego.ALTO_CAMARA-625,	regionEnemigo4,actividadJuego.getVertexBufferObjectManager()) {
            protected void onManagedUpdate(float pSecondsElapsed) {
                if(personaje.collidesWith(this)){
                    vidas --;
                }
            };
        };
        attachChild(enemigo4);
        enemigo5 = new Sprite(ControlJuego.ANCHO_CAMARA+8450, ControlJuego.ALTO_CAMARA-500,	regionEnemigo5,actividadJuego.getVertexBufferObjectManager()) {
            protected void onManagedUpdate(float pSecondsElapsed) {
                if(personaje.collidesWith(this)){
                    vidas --;
                }
            };
        };
        attachChild(enemigo5);
        enemigo5 = new Sprite(ControlJuego.ANCHO_CAMARA+9450, ControlJuego.ALTO_CAMARA-500,	regionEnemigo5,actividadJuego.getVertexBufferObjectManager()) {
            protected void onManagedUpdate(float pSecondsElapsed) {
                if(personaje.collidesWith(this)){
                    vidas --;
                }
            };
        };
        attachChild(enemigo5);
        //-------------------------------------------------
        bCamina = new ButtonSprite(210, 100, regionBCamina, actividadJuego.getVertexBufferObjectManager()){

            @Override
            public boolean onAreaTouched(TouchEvent event, float x, float y) {
                // Responder al touch del botón
                if (event.isActionDown())
                {
                    unregisterTouchArea(bRetrocede);
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
        bCamina.setScale(0.5f, 0.5f);
        attachChild(bCamina);
        bRetrocede = new ButtonSprite(100, 100, regionBRetrocede, actividadJuego.getVertexBufferObjectManager()){
            @Override
            public boolean onAreaTouched(TouchEvent event, float x, float y) {
                // Responder al touch del botón
                unregisterTouchArea(bCamina);
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
        bRetrocede.setScale(0.5f, 0.5f);
        attachChild(bRetrocede);
        bVida1 = new Sprite(personaje.getX(),personaje.getY()-200,regionVida1,actividadJuego.getVertexBufferObjectManager());
        bVida1.setScale(0.5f,0.5f);
        attachChild(bVida1);
        bVida2 = new Sprite(personaje.getX(),personaje.getY()-200,regionVida2,actividadJuego.getVertexBufferObjectManager());
        bVida2.setScale(0.5f,0.5f);
        attachChild(bVida2);

        bSalta = new ButtonSprite(1200, 100, regionBSalta,actividadJuego.getVertexBufferObjectManager()) {

            @Override
            public boolean onAreaTouched(TouchEvent event, float x, float y) {
                // Responder al touch del botón

                if (event.getAction()== MotionEvent.ACTION_DOWN&&!personajeSaltando) {
                    // Saltar
                    float xa = personaje.getX();
                    float ya = personaje.getY();
                    float xn = xa;
                    float yn = ya;
                    //El parámetro avanza*50 sirve para "conservar" el momentum en el salto
                    JumpModifier salto = new JumpModifier(1, xa,xn+(avanza*50), ya, yn, -400);
                    personajeSaltando = true;
                    long tiempos[] = new long[50];
                    for (int i = 40; i < 42; i++) {
                        tiempos[i] = 200;
                    }
                    personaje.animate(tiempos, 0, tiempos.length - 1, false);

                    //registerTouchArea(bCamina);
                    //registerTouchArea(bRetrocede);

                    paralelo = new ParallelEntityModifier(salto) {

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
                            super.onModifierFinished(pItem);
                            personajeSaltando = false;

                        }

                    };
                    sensor.registerEntityModifier(paralelo);
                    personaje.registerEntityModifier(paralelo);
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
        py = (float) (personaje.getY()-cae);
        personaje.setPosition(px,py);
        //---------------------------------
        //Esto sigue al personaje, asegurarse que los controles se queden dentro de la camara
        ControlJuego.camara.setChaseEntity(personaje);
        bCamina.setPosition(personaje.getX() - 390, personaje.getY() - 300);
        bRetrocede.setPosition(personaje.getX()-500,personaje.getY()-300);
        bSalta.setPosition(personaje.getX() + 500, personaje.getY()-300);
        bVida1.setPosition(personaje.getX()+bVida1.getWidth()/4, personaje.getY() - 300);
        bVida2.setPosition(personaje.getX()-bVida2.getWidth()/4, personaje.getY() - 300);
        //-------------------------------------------------------------------------------------------------
        //
        if(vidas<=1){
            bVida2.detachSelf();
        }
        if(vidas==0){
            admEscenas.crearGameover();
            admEscenas.setEscena(TipoEscena.ESCENA_GAMEOVER);
            admEscenas.liberarEscenaJuego3();
        }
        //-------------------------------------------------------------------------------------------------
        // distancia de personaje-meta para detectar el paso de nivel/
        double d;
        float xp = personaje.getX();
        float xm= meta.getX();
        float yp= personaje.getY();
        float ym= meta.getY();
        d= Math.sqrt((xp - xm) * (xp - xm) + (yp - ym) * (yp - ym));
        if (d<120){
            actividadJuego.getEngine().vibrate(100);
            admEscenas.crearEscenaMenu();
            admEscenas.setEscena(TipoEscena.ESCENA_MENU);
            admEscenas.liberarEscenaJuego3();
        }
    }

    @Override
    public void onBackKeyPressed() {
        admEscenas.crearEscenaMenu();
        admEscenas.setEscena(TipoEscena.ESCENA_MENU);
        admEscenas.liberarEscenaJuego3();
    }

    @Override
    public TipoEscena getTipoEscena() {
        return TipoEscena.ESCENA_JUEGO3;
    }

    @Override
    public void liberarEscena() {
        ControlJuego.camara.setChaseEntity(null);
        ControlJuego.camara.setCenter(ControlJuego.ANCHO_CAMARA/2,ControlJuego.ALTO_CAMARA/2);
        //---------------------------------------------------------------------------------------------------------
        this.detachSelf();
        this.dispose();
    }

    @Override
    public void liberarRecursos() {
        this.detachSelf();      // La escena se deconecta del engine
        this.dispose();         // Libera la memoria
        liberarRecursos();
    }

    @Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, ITouchArea pTouchArea, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        return true;
    }
}

