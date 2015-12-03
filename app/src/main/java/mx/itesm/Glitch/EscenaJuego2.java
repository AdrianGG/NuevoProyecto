package mx.itesm.Glitch;

/*
*
*/
import android.view.MotionEvent;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.JumpModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationByModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

public class EscenaJuego2 extends EscenaBase implements IOnAreaTouchListener {
    private TiledTextureRegion regionPersonaje;
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
    private ITextureRegion regionEnemigo1;
    private ITextureRegion regionEnemigo2;
    private ITextureRegion regionEnemigo3;
    private ITextureRegion regionEnemigo4;
    private ITextureRegion regionEnemigo5;
    private TiledTextureRegion regionEnemigoVerde;
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
    public Sprite bVida1;
    public Sprite bVida2;
    public int vidas=2;
    private ITextureRegion regionVida1;
    private ITextureRegion regionVida2;


    @Override
    public void cargarRecursos() {
        regionFondo= cargarImagen("glitchfondomasalto.png");
        regionObstaculo= cargarImagen("base1A.png");
        regionSensor= cargarImagen("sensor.png");
        regionPiso = cargarImagen("pisoAzul.png");
        regionPersonaje = cargarImagenMosaico("mildorosGlitch.png", 1815, 1120, 4, 8);
        regionBCamina= cargarImagen("botonAzul3.png");
        regionBRetrocede= cargarImagen("botonAzul2.png");
        regionBSalta= cargarImagen("botonAzul1.png");
        regionMeta=cargarImagen("meta3.png");
        regionPlataforma= cargarImagen("base2A.png");
        regionPlataforma2=cargarImagen("base3A.png");
        regionPlataforma3= cargarImagen("base4A.png");
        regionEnemigo3= cargarImagen("engrane3azul.png");
        regionEnemigo4= cargarImagen("engrane4azul.png");
        regionEnemigo5= cargarImagen("engrane5azul.png");
        //regionEnemigoVerde = cargarImagenMosaico("SmithTira.png", 2041,279,1,9);//
        regionVida1 = cargarImagen("vida1.png");
        regionVida2 = cargarImagen("vida2.png");
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
        meta= new Sprite(ControlJuego.ANCHO_CAMARA+1800,ControlJuego.ALTO_CAMARA+1400,regionMeta,actividadJuego.getVertexBufferObjectManager());
        attachChild(meta);
        personaje= new AnimatedSprite(ControlJuego.ANCHO_CAMARA-1000, ControlJuego.ALTO_CAMARA/3,	regionPersonaje, actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed){
                if(this.getY() < ControlJuego.ALTO_CAMARA/3 -800 || this.getX()>ControlJuego.ANCHO_CAMARA+300){
                    //System.out.println("MORIÍ");

                    if(vidas==2){
                        vidas--;
                        long tiempos[] = new long[32];
                        for(int i=9; i<13; i++) {
                            tiempos[i] = 100;
                        }
                        personaje.animate(tiempos,0,tiempos.length-1,true);
                        detachChild(bVida1);
                        personaje.setPosition(ControlJuego.ANCHO_CAMARA/4, ControlJuego.ALTO_CAMARA/3);

                    }
                    else if(vidas==1){
                      //  personaje.setPosition(ControlJuego.ANCHO_CAMARA/4, ControlJuego.ALTO_CAMARA/3);
                        System.out.println("MORISTE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        // admEscenas.liberarEscenaJuego1();
                        ControlJuego.camara.setChaseEntity(null);
                        ControlJuego.camara.setCenter(ControlJuego.ANCHO_CAMARA/2,ControlJuego.ALTO_CAMARA/2);
                        admEscenas.crearEscenaPerder();
                        admEscenas.setEscena(TipoEscena.ESCENA_PERDER);


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
        long tiempos[] = new long[32];
        for (int i = 16; i < 21; i++) {
            tiempos[i] = 100;
        }
        personaje.animate(tiempos,0,tiempos.length-1,true);
        //personaje.animate(tiempos, 0, tiempos.length - 1, true);
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
     //piso cortado

        //piso = new Sprite(piso.getX()+2*(piso.getWidth()/2),personaje.getY()-200,regionPiso,actividadJuego.getVertexBufferObjectManager());
        //attachChild(piso);
        setBackgroundEnabled(true);

        //-450 altura minima de las plataformas

        enemigo5 = new Sprite(ControlJuego.ANCHO_CAMARA-1700, ControlJuego.ALTO_CAMARA-25,	regionEnemigo5,actividadJuego.getVertexBufferObjectManager()) {
            protected void onManagedUpdate(float pSecondsElapsed) {
                if(personaje.collidesWith(this)){
                    if(vidas==2){
                        vidas--;
                        detachChild(bVida1);
                        personaje.setPosition(ControlJuego.ANCHO_CAMARA/4, ControlJuego.ALTO_CAMARA/3);

                    }
                    else if(vidas==1){
                        //  personaje.setPosition(ControlJuego.ANCHO_CAMARA/4, ControlJuego.ALTO_CAMARA/3);
                        System.out.println("MORISTE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        // admEscenas.liberarEscenaJuego1();
                        ControlJuego.camara.setChaseEntity(null);
                        ControlJuego.camara.setCenter(ControlJuego.ANCHO_CAMARA/2,ControlJuego.ALTO_CAMARA/2);
                        admEscenas.crearEscenaPerder();
                        admEscenas.setEscena(TipoEscena.ESCENA_PERDER);


                    }
                }
            };

        };
        RotationModifier rotacion = new RotationModifier(1, 360, 0);
        LoopEntityModifier girar = new LoopEntityModifier(rotacion);
        enemigo5.registerEntityModifier(girar);
        attachChild(enemigo5);
      //  enemigo5.registerEntityModifier(girar);
        plataforma1 = new Sprite(ControlJuego.ANCHO_CAMARA-1000, ControlJuego.ALTO_CAMARA-450,	regionPlataforma,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if (sensor.collidesWith(this)&&!bSalta.isPressed()){
                    personaje.unregisterEntityModifier(paralelo);
                    personajeSaltando=false;
                    personaje.setPosition(personaje.getX(),this.getY() + (this.getHeight())+30);
                }

            };
        };;
        attachChild(plataforma1); //1da en el nivel
        plataforma2 = new Sprite(ControlJuego.ANCHO_CAMARA-500, ControlJuego.ALTO_CAMARA-100,	regionPlataforma2,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)

            {
                if (sensor.collidesWith(this)&&!bSalta.isPressed()){
                    personaje.unregisterEntityModifier(paralelo);
                    personajeSaltando=false;
                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight() + 50));

                }

            };
        };;
        attachChild(plataforma2); //2ra en el nivel
        enemigo3 = new Sprite(ControlJuego.ANCHO_CAMARA-500, ControlJuego.ALTO_CAMARA-500,	regionEnemigo3,actividadJuego.getVertexBufferObjectManager()) {
            protected void onManagedUpdate(float pSecondsElapsed) {
                if(personaje.collidesWith(this)){
                    vidas --;
                }
            };
        };
        attachChild(enemigo3);
        plataforma3 = new Sprite(ControlJuego.ANCHO_CAMARA-100, ControlJuego.ALTO_CAMARA-300,	regionPlataforma3,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if (sensor.collidesWith(this)&&!bSalta.isPressed()){
                    personaje.unregisterEntityModifier(paralelo);
                    personajeSaltando=false;
                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight()+20));
                }

            };
        };;
        attachChild(plataforma3);
        plataforma1 = new Sprite(ControlJuego.ANCHO_CAMARA-1000, ControlJuego.ALTO_CAMARA+100,	regionPlataforma,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if (sensor.collidesWith(this)&&!bSalta.isPressed()){
                    personaje.unregisterEntityModifier(paralelo);
                    personajeSaltando=false;
                    personaje.setPosition(personaje.getX(),this.getY() + (this.getHeight()+40));
                }

            };
        };;
        attachChild(plataforma1); //4ta en el nivel -1700
        plataforma3 = new Sprite(ControlJuego.ANCHO_CAMARA-1500, ControlJuego.ALTO_CAMARA+300,	regionPlataforma3,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if (sensor.collidesWith(this)&&!bSalta.isPressed()){
                    personaje.unregisterEntityModifier(paralelo);
                    personajeSaltando=false;
                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight()+20));
                }

            };
        };;
        attachChild(plataforma3);
        plataforma1 = new Sprite(ControlJuego.ANCHO_CAMARA-1500, ControlJuego.ALTO_CAMARA+700,	regionPlataforma,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if (sensor.collidesWith(this)&&!bSalta.isPressed()){
                    personaje.unregisterEntityModifier(paralelo);
                    personajeSaltando=false;
                    personaje.setPosition(personaje.getX(),this.getY() + (this.getHeight()+40));
                }

            };
        };;
        attachChild(plataforma1);
        plataforma2 = new Sprite(ControlJuego.ANCHO_CAMARA-800, ControlJuego.ALTO_CAMARA+700,	regionPlataforma2,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)

            {
                if (sensor.collidesWith(this)&&!bSalta.isPressed()){
                    personaje.unregisterEntityModifier(paralelo);
                    personajeSaltando=false;
                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight() + 50));

                }

            };
        };;
        attachChild(plataforma2);
        plataforma3 = new Sprite(ControlJuego.ANCHO_CAMARA-400, ControlJuego.ALTO_CAMARA+900,	regionPlataforma3,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if (sensor.collidesWith(this)&&!bSalta.isPressed()){
                    personaje.unregisterEntityModifier(paralelo);
                    personajeSaltando=false;
                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight()+20));
                }

            };
        };;
        attachChild(plataforma3);
        plataforma2 = new Sprite(ControlJuego.ANCHO_CAMARA, ControlJuego.ALTO_CAMARA+1000,	regionPlataforma2,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)

            {
                if (sensor.collidesWith(this)&&!bSalta.isPressed()){
                    personaje.unregisterEntityModifier(paralelo);
                    personajeSaltando=false;
                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight() + 50));

                }

            };
        };;
        attachChild(plataforma2);
        plataforma1 = new Sprite(ControlJuego.ANCHO_CAMARA+800, ControlJuego.ALTO_CAMARA+700,	regionPlataforma,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)
            {
                if (sensor.collidesWith(this)&&!bSalta.isPressed()){
                    personaje.unregisterEntityModifier(paralelo);
                    personajeSaltando=false;
                    personaje.setPosition(personaje.getX(),this.getY() + (this.getHeight()+40));
                }

            };
        };;
        enemigo3 = new Sprite(ControlJuego.ANCHO_CAMARA+800, ControlJuego.ALTO_CAMARA+1500,	regionEnemigo3,actividadJuego.getVertexBufferObjectManager()) {
            protected void onManagedUpdate(float pSecondsElapsed) {
                if(personaje.collidesWith(this)){
                    if(vidas==2){
                        vidas--;
                        detachChild(bVida1);
                        personaje.setPosition(ControlJuego.ANCHO_CAMARA/4, ControlJuego.ALTO_CAMARA/3);

                    }
                    else if(vidas==1){
                        //  personaje.setPosition(ControlJuego.ANCHO_CAMARA/4, ControlJuego.ALTO_CAMARA/3);
                        System.out.println("MORISTE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        // admEscenas.liberarEscenaJuego1();
                        ControlJuego.camara.setChaseEntity(null);
                        ControlJuego.camara.setCenter(ControlJuego.ANCHO_CAMARA/2,ControlJuego.ALTO_CAMARA/2);
                        admEscenas.crearEscenaPerder();
                        admEscenas.setEscena(TipoEscena.ESCENA_PERDER);


                    }
                }
            };
        };
        attachChild(enemigo3);
        attachChild(plataforma1);
        plataforma2 = new Sprite(ControlJuego.ANCHO_CAMARA+1300, ControlJuego.ALTO_CAMARA+1000,	regionPlataforma2,actividadJuego.getVertexBufferObjectManager()){
            @Override
            protected void onManagedUpdate(float pSecondsElapsed)

            {
                if (sensor.collidesWith(this)&&!bSalta.isPressed()){
                    personaje.unregisterEntityModifier(paralelo);
                    personajeSaltando=false;
                    personaje.setPosition(personaje.getX(), this.getY() + (this.getHeight() + 50));

                }

            };
        };;
        attachChild(plataforma2);

        //-------------------------------------------------
        bCamina = new ButtonSprite(210, 100, regionBCamina, actividadJuego.getVertexBufferObjectManager()){

            @Override
            public boolean onAreaTouched(TouchEvent event, float x, float y) {
                // Responder al touch del botón
                if (event.isActionDown())
                {
                    unregisterTouchArea(bRetrocede);
                    //El personaje mira hacia la derecha cuando se mueve a esa direccion
                    long tiempos[] = new long[32];
                    for(int i=0; i<7; i++) {
                        tiempos[i] = 100;
                    }

                    personaje.animate(tiempos,0,tiempos.length-1,true);
                    personaje.setScale(Math.abs(personaje.getScaleX()),personaje.getScaleY());
                    avanza = 10f;
                }
                else
                {
                    if(event.isActionUp()) {
                        long tiempos[] = new long[32];
                        for (int i = 16; i < 21; i++) {
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
                    long tiempos[] = new long[32];
                    for(int i=0; i<7; i++) {
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
                            long tiempos[] = new long[32];
                            for (int i = 16; i < 21; i++) {
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
                    long tiempos[] = new long[32];
                    for (int i = 26; i < 28; i++) {
                        tiempos[i] = 200;
                    }
                    personaje.animate(tiempos, 0, tiempos.length - 1, false);

                    //registerTouchArea(bCamina);
                    //registerTouchArea(bRetrocede);

                    paralelo = new ParallelEntityModifier(salto) {

                        @Override
                        protected void onModifierFinished(IEntity pItem) {
                            if(bRetrocede.isPressed()||bCamina.isPressed()){
                                long tiempos[] = new long[32];
                                for(int i=0; i<7; i++) {
                                    tiempos[i] = 100;
                                }
                                personaje.animate(tiempos,0,tiempos.length-1,true);
                            }
                            else{
                                long tiempos[] = new long[32];
                                for (int i = 16; i < 21; i++) {
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
        // distancia de personaje-meta para detectar el paso de nivel/
        double d;
        float xp = personaje.getX();
        float xm= meta.getX();
        float yp= personaje.getY();
        float ym= meta.getY();
        d= Math.sqrt((xp - xm) * (xp - xm) + (yp - ym) * (yp - ym));
        if (d<120){
            actividadJuego.getEngine().vibrate(100);
            admEscenas.crearEscenaJuego1();
            admEscenas.setEscena(TipoEscena.ESCENA_JUEGO1);
            admEscenas.liberarEscenaJuego2();
        }
    }

    @Override
    public void onBackKeyPressed() {
        admEscenas.crearEscenaMenu();
        admEscenas.setEscena(TipoEscena.ESCENA_MENU);
        admEscenas.liberarEscenaJuego2();
    }

    @Override
    public TipoEscena getTipoEscena() {
        return TipoEscena.ESCENA_JUEGO2;
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
