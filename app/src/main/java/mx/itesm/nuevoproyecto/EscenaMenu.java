package mx.itesm.nuevoproyecto;



import org.andengine.audio.music.Music;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;

import java.security.PublicKey;
import java.util.Random;

/**
 * Created by A. iram on 01/10/2015.
 */
public class EscenaMenu extends EscenaBase {
    // Regiones para las imágenes de la escena
    private ITextureRegion regionFondo;
    private ITextureRegion regionFondo2;
    private ITextureRegion regionBtnAcercaDe;
    private ITextureRegion regionBtnJugar;
    private ITextureRegion regionBtnInsttrucciones;
    private ITextureRegion regionBtnAcercaDe2;
    private ITextureRegion regionBtnJugar2;
    private ITextureRegion regionBtnInsttrucciones2;



    // Sprites sobre la escena
    private Sprite spriteFondo;

    //Musica
    public static Music musicaFondo;
    // Un menú de tipo SceneMenu
    private MenuScene menu;     // Contenedor de las opciones
    // Constantes para cada opción
    private final int OPCION_ACERCA_DE = 0;
    private final int OPCION_JUGAR = 1;
    private final int OPCION_INSTRUCCIONES= 2;

    // Botones de cada opción
    private ButtonSprite btnAcercaDe;
    private ButtonSprite btnJugar;
    private ButtonSprite btnInstrucciones;
    private boolean color = false;

    public void cargarRecursos() {
        // Fondo
        regionFondo = cargarImagen("menuiniciorosa.png");
        regionFondo2= cargarImagen("menuinicioazul.png");
        // Botones del menú

        regionBtnAcercaDe = cargarImagen("botonrosamenu.png");
        regionBtnJugar = cargarImagen("botonrosamenu.png");
        regionBtnInsttrucciones= cargarImagen("botonrosamenu.png");

        regionBtnAcercaDe2 = cargarImagen("botonazulmenu.png");
        regionBtnJugar2 = cargarImagen("botonazulmenu.png");
        regionBtnInsttrucciones2= cargarImagen("botonazulmenu.png");
        musicaFondo = cargarSonidos("Music/lesliewaiparadigm.mp3");
    }


    @Override
    public void crearEscena() {
        if(EscenaJuego1.musicaFondo!=null){
            if(EscenaJuego1.musicaFondo.isPlaying()){
                EscenaJuego1.musicaFondo.stop();
                //EscenaJuego1.musicaFondo.release();
            }

        }
        // Creamos el sprite de manera óptima
        Random rand = new Random();
        int n = rand.nextInt(20);

        if(n<10){spriteFondo = cargarSprite(ControlJuego.ANCHO_CAMARA/2, ControlJuego.ALTO_CAMARA/2, regionFondo);}
        else {spriteFondo = cargarSprite(ControlJuego.ANCHO_CAMARA/2, ControlJuego.ALTO_CAMARA/2, regionFondo2); color=true;}

        // Crea el fondo de la pantalla
        SpriteBackground fondo = new SpriteBackground(1,1,1,spriteFondo);
        setBackground(fondo);
        setBackgroundEnabled(true);
        // Mostrar un recuadro atrás del menú
        agregarFondoMenu();
        // Mostrar opciones de menú
        agregarMenu();
        musicaFondo.play();
    }

    private void agregarFondoMenu() {
        Rectangle cuadro = new Rectangle(ControlJuego.ANCHO_CAMARA/2, ControlJuego.ALTO_CAMARA/2,
                0.75f*ControlJuego.ANCHO_CAMARA, 0.75f*ControlJuego.ALTO_CAMARA, actividadJuego.getVertexBufferObjectManager());
        cuadro.setColor(0.1f, 0.1f, 0.1f, 0.1f);
        attachChild(cuadro);
    }

    private void agregarMenu() {
        // Crea el objeto que representa el menú
        menu = new MenuScene(actividadJuego.camara);
        // Centrado en la pantalla
        menu.setPosition(ControlJuego.ANCHO_CAMARA/2,ControlJuego.ALTO_CAMARA/2);
        // Crea las opciones (por ahora, acerca de y jugar)+
        ITextureRegion contenedor;
        if(color==true){ contenedor= regionBtnAcercaDe2;}
        else{contenedor= regionBtnAcercaDe;}
        IMenuItem opcionAcercaDe = new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_ACERCA_DE,
                contenedor, actividadJuego.getVertexBufferObjectManager()), 1.5f, 1);
        IMenuItem opcionJugar = new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_JUGAR,
                contenedor, actividadJuego.getVertexBufferObjectManager()), 1.5f, 1);
        IMenuItem opcionInstrucciones = new ScaleMenuItemDecorator(new SpriteMenuItem(OPCION_INSTRUCCIONES,
                contenedor, actividadJuego.getVertexBufferObjectManager()), 1.5f, 1);


        // Agrega las opciones al menú
        menu.addMenuItem(opcionAcercaDe);
        menu.addMenuItem(opcionJugar);
        menu.addMenuItem(opcionInstrucciones);

        // Termina la configuración
        menu.buildAnimations();
        menu.setBackgroundEnabled(false);   // Completamente transparente

        // Ubicar las opciones DENTRO del menú. El centro del menú es (0,0)
        opcionAcercaDe.setPosition(-0, -240);
        opcionJugar.setPosition(0, 27);
        opcionInstrucciones.setPosition(0,-107);

        // Registra el Listener para atender las opciones
        menu.setOnMenuItemClickListener(new MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
                                             float pMenuItemLocalX, float pMenuItemLocalY) {
                // El parámetro pMenuItem indica la opción oprimida
                switch(pMenuItem.getID()) {
                    case OPCION_ACERCA_DE:
                        // Mostrar la escena de AcercaDe
                        admEscenas.crearEscenaAcercaDe();
                        admEscenas.setEscena(TipoEscena.ESCENA_ACERCA_DE);
                        actividadJuego.getEngine().vibrate(100);
                        admEscenas.liberarEscenaMenu();
                        break;

                    case OPCION_JUGAR:
                        // Mostrar la pantalla de juego, lleva a la animacion
                        admEscenas.crearEscenaJuego();
                        admEscenas.setEscena(TipoEscena.ESCENA_JUEGO);
                        actividadJuego.getEngine().vibrate(100);
                        admEscenas.liberarEscenaMenu();

                        break;

                    case OPCION_INSTRUCCIONES:
                        admEscenas.crearEscenaInstrucciones();
                        admEscenas.setEscena(TipoEscena.ESCENA_INSTRUCCIONES);
                        actividadJuego.getEngine().vibrate(100);
                        admEscenas.liberarEscenaMenu();
                        break;

                }
                return true;
            }
        });

        // Asigna este menú a la escena
        setChildScene(menu);
    }

    // La escena se debe actualizar en este método que se repite "varias" veces por segundo
    // Aquí es donde programan TODA la acción de la escena (movimientos, choques, disparos, etc.)
    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);

    }


    @Override
    public void onBackKeyPressed() {
        // Salir del juego, no hacemos nada
    }

    @Override
    public TipoEscena getTipoEscena() {
        return TipoEscena.ESCENA_MENU;
    }

    @Override
    public void liberarEscena() {
        this.detachSelf();      // La escena se deconecta del engine
        this.dispose();         // Libera la memoria
        liberarRecursos();
    }

    @Override
    public void liberarRecursos() {
        regionFondo.getTexture().unload();
        regionFondo = null;
    }
}