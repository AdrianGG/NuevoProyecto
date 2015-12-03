package mx.itesm.Glitch;

import android.view.MotionEvent;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.JumpModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

/**
 * Created by A. iram on 03/12/2015.
 */
public class EscenaJuego4 extends EscenaBase {
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
    //variables para el control
    private float px = 0;
    public float py = 0;
    private float avanza = 0;
    private float cae = 20;
    static ParallelEntityModifier paralelo;
    static JumpModifier salto;
    public boolean falls = true;
    //Instanciar botones para que sean accesibles en cualquier parte de esta clase
    public ButtonSprite bCamina;
    public ButtonSprite bRetrocede;
    public ButtonSprite bSalta;
    public Sprite bVida1;
    public Sprite bVida2;
    public int vidas=2;
    @Override
    public void cargarRecursos() {
        regionFondo= cargarImagen("glitchfondomasalto.png");
        regionObstaculo= cargarImagen("obstaculo.png");
        regionSensor= cargarImagen("sensor.png");
        regionPiso = cargarImagen("pisoRosa.png");
        regionPersonaje = cargarImagenMosaico("mildoros.png", 2290, 1091, 5, 10);
        regionBCamina= cargarImagen("boton2.png");
        regionBRetrocede= cargarImagen("boton3.png");
        regionBSalta= cargarImagen("boton1.png");
        regionMeta=cargarImagen("meta1.png");
        regionPlataforma= cargarImagen("base2.png");
        regionPlataforma2=cargarImagen("base3.png");
        regionPlataforma3= cargarImagen("base4.png");

        regionVida1 = cargarImagen("vida1.png");//
        regionVida2 = cargarImagen("vida2.png");

    }

    @Override
    public void crearEscena() {
        float xF = ControlJuego.ANCHO_CAMARA/2-3158;
        for(int i = 0; i<=5; i++){
            fondo=new Sprite(xF+(3158*i),(ControlJuego.ALTO_CAMARA/2+150),regionFondo,actividadJuego.getVertexBufferObjectManager());
            attachChild(fondo);
        }
        for(int i = 0; i<=5; i++){
            fondo=new Sprite(xF+(3158*i),(ControlJuego.ALTO_CAMARA/2+1320),regionFondo,actividadJuego.getVertexBufferObjectManager());
            attachChild(fondo);
        }
        //CHECAR DONDE ESTARA LA META
        meta= new Sprite(ControlJuego.ANCHO_CAMARA+1600,ControlJuego.ALTO_CAMARA+500,regionMeta,actividadJuego.getVertexBufferObjectManager());
        attachChild(meta);
        personaje= new AnimatedSprite(ControlJuego.ANCHO_CAMARA-2000, ControlJuego.ALTO_CAMARA/3,	regionPersonaje, actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed){
                if(this.getY() < ControlJuego.ALTO_CAMARA/3 -800){
                    //System.out.println("MORIÍ");
                    personaje.setPosition(ControlJuego.ANCHO_CAMARA/4, ControlJuego.ALTO_CAMARA/3);
                    if(vidas==2){
                        vidas--;
                        detachChild(bVida1);
                    }
                    else if(vidas==1){
                        System.out.println("MORISTE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        /// admEscenas.liberarEscenaJuego1();
                      /*  ControlJuego.camara.setChaseEntity(null);
                        ControlJuego.camara.setCenter(ControlJuego.ANCHO_CAMARA/2,ControlJuego.ALTO_CAMARA/2);
                        admEscenas.crearEscenaPerder();
                        admEscenas.setEscena(TipoEscena.ESCENA_PERDER);*/


                    }


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


        //piso = new Sprite(piso.getX()+2*(piso.getWidth()/2),personaje.getY()-200,regionPiso,actividadJuego.getVertexBufferObjectManager());
        //attachChild(piso);
        setBackgroundEnabled(true);
        //-450 altura minima de las plataformas
        obstaculo = new Sprite(ControlJuego.ANCHO_CAMARA-3000, ControlJuego.ALTO_CAMARA-450,	regionObstaculo,actividadJuego.getVertexBufferObjectManager()){
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
        attachChild(obstaculo); //1ra en el nivel1
        plataforma1 = new Sprite(ControlJuego.ANCHO_CAMARA-2500, ControlJuego.ALTO_CAMARA-150,	regionPlataforma,actividadJuego.getVertexBufferObjectManager()){
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
        attachChild(plataforma1); //2da en el nivel1
      /*  plataforma2 = new Sprite(ControlJuego.ANCHO_CAMARA-1700, ControlJuego.ALTO_CAMARA-50,	regionPlataforma2,actividadJuego.getVertexBufferObjectManager()){
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
        attachChild(plataforma2); //3ra en el nivel1*/
        plataforma1 = new Sprite(ControlJuego.ANCHO_CAMARA-1700, ControlJuego.ALTO_CAMARA-50,	regionPlataforma,actividadJuego.getVertexBufferObjectManager()){
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
        attachChild(plataforma1);
        plataforma3 = new Sprite(ControlJuego.ANCHO_CAMARA-1000, ControlJuego.ALTO_CAMARA-150,	regionPlataforma3,actividadJuego.getVertexBufferObjectManager()){
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
        attachChild(plataforma3); //4ta en el nivel1

        plataforma1 = new Sprite(ControlJuego.ANCHO_CAMARA-130, ControlJuego.ALTO_CAMARA-200,	regionPlataforma,actividadJuego.getVertexBufferObjectManager()){
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
        attachChild(plataforma1); //5ta en el nivel1

        //plataforma2 = new Sprite(ControlJuego.ANCHO_CAMARA+2550, ControlJuego.ALTO_CAMARA-240,	regionPlataforma2,actividadJuego.getVertexBufferObjectManager()){
        //  @Override
        //    protected void onManagedUpdate(float pSecondsElapsed)
        //      {
        //            if (sensor.collidesWith(this)&&!bSalta.isPressed()){
        //                  personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight() + 30));

//                }

        //  };
        //};;
        //attachChild(plataforma2); //6ta en el nivel1

        plataforma2 = new Sprite(ControlJuego.ANCHO_CAMARA+700, ControlJuego.ALTO_CAMARA-200,	regionPlataforma2,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if (sensor.collidesWith(this)&&!bSalta.isPressed()){


                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight() + 30));


                    personaje.unregisterEntityModifier(paralelo);
                    personajeSaltando=false;



                }

            };
        };;
        attachChild(plataforma2); //7ma en el nivel1

        plataforma1 = new Sprite(ControlJuego.ANCHO_CAMARA+1500, ControlJuego.ALTO_CAMARA-105,	regionPlataforma,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if (sensor.collidesWith(this)&&!bSalta.isPressed()){

                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight()));
                }

            };
        };;
        attachChild(plataforma1); //8va en el nivel1
        plataforma3 = new Sprite(ControlJuego.ANCHO_CAMARA+2300, ControlJuego.ALTO_CAMARA-160,	regionPlataforma3,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if (sensor.collidesWith(this)&&!bSalta.isPressed()){

                    personaje.unregisterEntityModifier(paralelo);
                    personajeSaltando=false;
                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight()+30));



                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight() -30));
                }

            };
        };;
        attachChild(plataforma3); //9na en el nivel1
        obstaculo = new Sprite(ControlJuego.ANCHO_CAMARA+3000, ControlJuego.ALTO_CAMARA-200,	regionObstaculo,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed) {
                if (sensor.collidesWith(this)&&!bSalta.isPressed())
                {

                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight() + 30));
                }
            };
        };
        attachChild(obstaculo); //10ma en el nivel1

        plataforma2 = new Sprite(ControlJuego.ANCHO_CAMARA+3000, ControlJuego.ALTO_CAMARA+200,	regionPlataforma2,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if (sensor.collidesWith(this)&&!bSalta.isPressed()){


                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight() + 30));


                    personaje.unregisterEntityModifier(paralelo);
                    personajeSaltando=false;



                }

            };
        };;
        attachChild(plataforma2); //11va en el nivel1
        obstaculo = new Sprite(ControlJuego.ANCHO_CAMARA+2300, ControlJuego.ALTO_CAMARA+400,	regionObstaculo,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed) {
                if (sensor.collidesWith(this)&&!bSalta.isPressed())
                {

                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight() + 30));
                }
            };
        };
        attachChild(obstaculo);//12va plataforma
        plataforma2 = new Sprite(ControlJuego.ANCHO_CAMARA+1900, ControlJuego.ALTO_CAMARA+400,	regionPlataforma2,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if (sensor.collidesWith(this)&&!bSalta.isPressed()){


                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight() + 30));


                    personaje.unregisterEntityModifier(paralelo);
                    personajeSaltando=false;



                }

            };
        };

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
        // distancia de personaje-meta para detectar el paso de nivel/
        double d;
        float xp = personaje.getX();
        float xm= meta.getX();
        float yp= personaje.getY();
        float ym= meta.getY();
        d= Math.sqrt((xp - xm) * (xp - xm) + (yp - ym) * (yp - ym));
        if (d<120){
            actividadJuego.getEngine().vibrate(100);
            admEscenas.crearEscenaJuego2();
            admEscenas.setEscena(TipoEscena.ESCENA_JUEGO2);
            admEscenas.liberarEscenaJuego4();
        }
    }



    @Override
    public void onBackKeyPressed() {
        admEscenas.crearEscenaMenu();
        admEscenas.setEscena(TipoEscena.ESCENA_MENU);
        admEscenas.liberarEscenaJuego4();
    }

    @Override
    public TipoEscena getTipoEscena() {
        return TipoEscena.ESCENA_JUEGO4;
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
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, ITouchArea pTouchArea, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        return true;
    }
}
