package mx.itesm.nuevoproyecto;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.debug.Debug;

/**
 * Created by A. iram on 01/10/2015.
 */
public class EscenaJuego extends EscenaBase {
   
   private ITextureRegion[] regionSlides;
    private ITextureRegion regionSlideActual;
    private Sprite spriteFondo;




    
    @Override
    public void cargarRecursos() {

      //  regionFondo = cargarImagen("escenasiguiente.jpg");
        regionSlides = new ITextureRegion[16];
        regionSlides[0]=cargarImagen("creditos.jpg");
        regionSlides[1] = cargarImagen("escenasiguiente.jpg");
        regionSlides[2] = cargarImagen("fondomenu.jpg");
        regionSlideActual=regionSlides[0];


    }

    @Override
    public void crearEscena() {

      //  spriteFondo = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, regionFondo);
       // attachChild(spriteFondo);
        spriteFondo = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, regionSlideActual);
        attachChild(spriteFondo);
        setOnSceneTouchListener(this.getOnSceneTouchListener());


    }
    public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent)
    {
        if (pSceneTouchEvent.isActionDown())
        {
            if(contador<2){
                contador++;
                regionSlideActual=regionSlides[contador];
                spriteFondo = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, regionSlideActual);
                attachChild(spriteFondo);
            }
            else{
                admEscenas.crearEscenaJuego1();
                admEscenas.setEscena(TipoEscena.ESCENA_JUEGO1);
                admEscenas.liberarEscenaJuego();
            }

        }
        return false;
    }


    @Override
    public void onBackKeyPressed() {
        admEscenas.crearEscenaMenu();
        admEscenas.setEscena(TipoEscena.ESCENA_MENU);
        admEscenas.liberarEscenaJuego();
    }

    @Override
    public TipoEscena getTipoEscena() {
        return TipoEscena.ESCENA_JUEGO;
    }

    @Override
    public void liberarEscena() {
        liberarRecursos();
        this.detachSelf();
        this.dispose();
    }

    @Override
    public void liberarRecursos() {
        regionSlideActual.getTexture().unload();
        regionSlideActual = null;
        regionSlides=null;
    }
   int  contador=0;


}
