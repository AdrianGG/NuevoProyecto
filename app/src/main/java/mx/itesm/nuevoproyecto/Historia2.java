package mx.itesm.nuevoproyecto;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * Created by A. iram on 26/10/2015.
 */
public class Historia2 extends EscenaBase {
    private ITextureRegion[] regionSlides;
    private ITextureRegion regionSlideActual;
    private Sprite spriteFondo;
    private int contador=0;

    @Override
    public void cargarRecursos() {
        regionSlides = new ITextureRegion[2];
        regionSlides[0] = cargarImagen("juego7.jpg");
        regionSlides[1] = cargarImagen("juego8.jpg");
        regionSlideActual=regionSlides[0];

    }

    @Override
    public void crearEscena() {
        spriteFondo = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, regionSlideActual);
        attachChild(spriteFondo);
        setOnSceneTouchListener(this.getOnSceneTouchListener());
    }
    public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent)
    {
        if (pSceneTouchEvent.isActionDown())
        {
            if(contador<1){
                contador++;
                regionSlideActual=regionSlides[contador];
                spriteFondo = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, regionSlideActual);
                attachChild(spriteFondo);
            }
            else{ //pasa al nivel 2
               /* admEscenas.crearEscenaJuego1();
                admEscenas.setEscena(TipoEscena.ESCENA_JUEGO1);
                admEscenas.liberarEscenaJuego();*/
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
        return null;
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
}
