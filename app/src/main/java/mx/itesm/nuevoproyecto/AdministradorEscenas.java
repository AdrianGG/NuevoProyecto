package mx.itesm.nuevoproyecto;

import org.andengine.engine.Engine;

/**
 * Created by A. iram on 01/10/2015.
 */
public class AdministradorEscenas {
    // Instancia única
    private static final AdministradorEscenas INSTANCE =
            new AdministradorEscenas();
    protected ControlJuego actividadJuego;

    // Declara las distintas escenas que forman el juego
    private EscenaBase escenaSplash;
    private EscenaBase escenaMenu;
    private EscenaBase escenaAcercaDe;
    private EscenaBase escenaInstrucciones;
    private EscenaBase escenaJuego;
    private EscenaBase escenaJuego1;
    private EscenaBase escenaHistoria2;
    private EscenaBase escenaJuego2;
    private EscenaBase escenaJuego3;
    private EscenaBase GameOver;
    private EscenaBase escenaPerder;


    // El tipo de escena que se está mostrando
    private TipoEscena tipoEscenaActual = TipoEscena.ESCENA_SPLASH;
    // La escena que se está mostrando
    private EscenaBase escenaActual;
    // El engine para hacer el cambio de escenas
    public Engine engine;

    // Asigna valores iniciales del administrador
    public static void inicializarAdministrador(ControlJuego actividadJuego, Engine engine) {
        getInstance().actividadJuego = actividadJuego;
        getInstance().engine = engine;
    }

    // Regresa la instancia del administrador de escenas
    public static AdministradorEscenas getInstance() {
        return INSTANCE;
    }

    // Regresa el tipo de la escena actual
    public TipoEscena getTipoEscenaActual() {
        return tipoEscenaActual;
    }

    // Regresa la escena actual
    public EscenaBase getEscenaActual() {
        return escenaActual;
    }

    /*
     * Pone en la pantalla la escena que llega como parámetro y guarda el nuevo estado
     */
    private void setEscenaBase(EscenaBase nueva) {
        engine.setScene(nueva);

        escenaActual = nueva;
        tipoEscenaActual = nueva.getTipoEscena();
    }

    /**
     * Cambia a la escena especificada en el parámetro
     * @param nuevoTipo la nueva escena que se quiere mostrar
     */
    public void setEscena(TipoEscena nuevoTipo) {
        switch (nuevoTipo) {
            case ESCENA_SPLASH:
                setEscenaBase(escenaSplash);
                break;
            case ESCENA_MENU:
                setEscenaBase(escenaMenu);
                break;
            case ESCENA_ACERCA_DE:
                setEscenaBase(escenaAcercaDe);
                break;
            case ESCENA_INSTRUCCIONES:
                setEscenaBase(escenaInstrucciones);
                break;
            case ESCENA_JUEGO:
                setEscenaBase(escenaJuego);
                break;
            case ESCENA_JUEGO1:
                setEscenaBase(escenaJuego1);
                break;
            case ESCENA_HISTORIA2:
                setEscenaBase(escenaHistoria2);
                break;
            case ESCENA_JUEGO2:
                setEscenaBase(escenaJuego2);
                break;
            case ESCENA_JUEGO3:
                setEscenaBase(escenaJuego3);
                break;
            case ESCENA_GAMEOVER:
                setEscenaBase(GameOver);
                break;
            case ESCENA_PERDER:
                setEscenaBase(escenaPerder);
                break;
        }
    }

    //*** Crea la escena de Splash
    public void crearEscenaSplash() {
        // Carga los recursos
        escenaSplash = new EscenaSplash();
    }

    //*** Libera la escena de Splash
    public void liberarEscenaSplash() {
        escenaSplash.liberarEscena();
        escenaSplash = null;
    }

    // ** MENU
    //*** Crea la escena de MENU
    public void crearEscenaMenu() {
        // Carga los recursos
        escenaMenu = new EscenaMenu();
    }

    //*** Libera la escena de MENU
    public void liberarEscenaMenu() {
        escenaMenu.liberarEscena();
        escenaMenu = null;
    }

    //*** Crea la escena de Acerca De
    public void crearEscenaAcercaDe() {
        // Carga los recursos
        escenaAcercaDe = new EscenaAcercaDe();
    }

    //*** Libera la escena de AcercDe
    public void liberarEscenaAcercaDe() {
        escenaAcercaDe.liberarEscena();
        escenaAcercaDe = null;
    }

    public void crearEscenaInstrucciones(){
        escenaInstrucciones = new EscenaInstrucciones();
    }

    public void liberarEscenaInstrucciones(){
        escenaInstrucciones.liberarEscena();
        escenaInstrucciones = null;
    }

    //Crea la escena de JUEGO
    public void crearEscenaJuego() {
        // Carga los recursos de la animacion
        // admRecursos.cargarRecursosJuego();
        escenaJuego = new EscenaJuego();
    }

    //*** Libera la escena de JUEGO
    public void liberarEscenaJuego() {
        // admRecursos.liberarRecursosJuego();
        escenaJuego.liberarEscena();
        escenaJuego = null;
    }

    public void crearEscenaJuego1(){
        escenaJuego1= new EscenaJuego1();
    }
    public void liberarEscenaJuego1(){
        escenaJuego1.liberarEscena();
    }

    public void crearEscenaHistoria2(){escenaHistoria2= new EscenaHistoria2();}
    public void liberarEscenaHistoria2(){escenaHistoria2.liberarEscena();}


    public void crearEscenaJuego2() {
        // Carga los recursos
        // admRecursos.cargarRecursosJuego2();
        escenaJuego2 = new EscenaJuego2();
    }

    //*** Libera la escena de Juego Dos
    public void liberarEscenaJuego2() {
        // admRecursos.liberarRecursosJuego2();
        escenaJuego2.liberarEscena();
        escenaJuego2 = null;
    }
    public void crearEscenaJuego3() {
        // Carga los recursos
        // admRecursos.cargarRecursosJuego3();
        escenaJuego3 = new EscenaJuego3();
    }

    //*** Libera la escena de Juego Dos
    public void liberarEscenaJuego3() {
        // admRecursos.liberarRecursosJuego3();
        escenaJuego3.liberarEscena();
        escenaJuego3 = null;
    }
    public void crearGameover() {
        // Carga los recursos
        // admRecursos.cargarRecursosJuego3();
         GameOver= new GameOver();
    }

    public void crearPerder(){
        escenaPerder = new EscenaPerder();
    }

    //*** Libera la escena de Juego Dos
    public void liberarGameover() {
        // admRecursos.liberarRecursosJuego3();
        escenaJuego3.liberarEscena();
        escenaJuego3 = null;
    }

    public void crearEscenaPerder(){
        escenaPerder = new EscenaPerder();
    }

    public void liberarEscenaPerder(){
        escenaPerder.liberarEscena();
        escenaPerder = null;
    }

}