package mx.itesm.nuevoproyecto;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * Created by A. iram on 01/10/2015.
 */
public class EscenaJuego extends EscenaBase {
    private ITextureRegion regionFondo;
    // private ITextureRegion regionBtnJugar; //boton de comenzar
    private Sprite spriteFondo;



    @Override
    public void cargarRecursos() {
        regionFondo = cargarImagen("escenasiguiente.jpg");

    }

    @Override
    public void crearEscena() {
        spriteFondo = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, regionFondo);
        attachChild(spriteFondo);

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
        this.detachSelf();
        this.dispose();
    }

    @Override
    public void liberarRecursos() {
        regionFondo.getTexture().unload();
        regionFondo = null;
    }

    public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
        if (pSceneTouchEvent.isActionDown()) {
            //	 instrucciones en pantala, toca para continuar,El	usuario	toca	la	pantalla y va a primer nivel
            admEscenas.crearEscenaJuego1();
            admEscenas.setEscena(TipoEscena.ESCENA_JUEGO1);
            admEscenas.liberarEscenaJuego();


        }
        return super.onSceneTouchEvent(pSceneTouchEvent);
    }
}
