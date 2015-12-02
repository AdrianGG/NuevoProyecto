package mx.itesm.nuevoproyecto;



import android.view.KeyEvent;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import java.io.IOException;


public class ControlJuego extends SimpleBaseGameActivity {
    // Dimensiones de la cámara. Se ajustará (escalará) a cualquier pantalla física.
    public static final int ANCHO_CAMARA = 1280;
    public static final int ALTO_CAMARA = 800;
    public static int vidas = 2;
    // La cámara
    public static Camera camara;
    // El administrador de escenas (se encarga de cambiar las escenas)
    private AdministradorEscenas admEscenas;

    /*
    Se crea la configuración del Engine.
    new FillResolutionPolicy() - Escala la cámara a lo ancho y alto de la pantalla
    new FixedResolutionPolicy(ANCHO_CAMARA, ALTO_CAMARA) - Mantiene la cámara con dimensiones fijas, si la pantalla es más pequeña se recorta, si es más grande quedan zonas blancas alrededor.
    new RatioResolutionPolicy(ANCHO_CAMARA, ALTO_CAMARA) - Mantiene la relación ancho/alto
     */
    @Override
    public EngineOptions onCreateEngineOptions() {
        camara = new Camera(0,0,ANCHO_CAMARA,ALTO_CAMARA);
        EngineOptions OpcionesEngine = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), camara);
        //Activa el multitouch en todas las escenas
        OpcionesEngine.getAudioOptions().setNeedsMusic(true);
        OpcionesEngine.getTouchOptions().setNeedsMultiTouch(true);
        //-----------------------------------------
        return OpcionesEngine;


    }

    // Crea los recursos del juego.
    @Override
    protected void onCreateResources() throws IOException {
        // Pasamos la información al administrador de escenas para que los comparta con cada Escena
        AdministradorEscenas.inicializarAdministrador(this, mEngine);
        // Obtenemos la referencia al objeto administrador
        admEscenas = AdministradorEscenas.getInstance();
        //habilita vibración del hw
        mEngine.enableVibrator(this);

    }

    // Regresa la escena inicial.
    @Override
    protected Scene onCreateScene() {
        // Crea la primer escena que se quiere mostrar
        admEscenas.crearEscenaSplash();
        admEscenas.setEscena(TipoEscena.ESCENA_SPLASH);

        // Programa la carga de la segunda escena, después de cierto tiempo
        mEngine.registerUpdateHandler(new TimerHandler(2,
                new ITimerCallback() {
                    @Override
                    public void onTimePassed(TimerHandler pTimerHandler) {
                        mEngine.unregisterUpdateHandler(pTimerHandler); // Invalida el timer
                        // Cambia a la escena del MENU
                        //** 1. CREA la escena del menú (la NUEVA)
                        //** 2. PONE la escena del menú (la NUEVA)
                        //** 3. LIBERA la escena de Splash (la ANTERIOR)
                        admEscenas.crearEscenaMenu();
                        admEscenas.setEscena(TipoEscena.ESCENA_MENU);
                        admEscenas.liberarEscenaSplash();
                    }
                }));

        return admEscenas.getEscenaActual();    // Regresa la primer escena que se muestra
    }

    // Atiende la tecla de BACK.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK) {
            if(admEscenas.getTipoEscenaActual()==TipoEscena.ESCENA_MENU) {
                // Si está en el menú, termina
                finish();
            } else {
                // La escena que se está mostrando maneja el evento
                admEscenas.getEscenaActual().onBackKeyPressed();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // La aplicación sale de memoria, la eliminamos completamente.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (admEscenas!=null) {
            System.exit(0);
        }
    }
}
