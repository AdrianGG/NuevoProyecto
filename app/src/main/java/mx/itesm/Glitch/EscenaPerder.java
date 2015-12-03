//package mx.itesm.nuevoproyecto;
package mx.itesm.Glitch;
import org.andengine.entity.sprite.Sprite;

/**
 * Created by A. iram on 02/12/2015.
 */
public class EscenaPerder extends EscenaBase {
    Sprite spritePerdiste;

    @Override

    public void cargarRecursos() {
        spritePerdiste = cargarSprite(ControlJuego.ANCHO_CAMARA/2, ControlJuego.ALTO_CAMARA/2,cargarImagen("final.png"));



    }

    @Override
    public void crearEscena() {
        attachChild(spritePerdiste);
        ControlJuego.camara.setChaseEntity(null);
        ControlJuego.camara.setCenter(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2);

    }

    @Override
    public void onBackKeyPressed() {
        admEscenas.crearEscenaMenu();
        admEscenas.setEscena(TipoEscena.ESCENA_MENU);
        admEscenas.liberarEscenaPerder();
    }

    @Override
    public TipoEscena getTipoEscena() {
        return TipoEscena.ESCENA_PERDER;
    }

    @Override
    public void liberarEscena() {
        ControlJuego.camara.setChaseEntity(null);
        ControlJuego.camara.setCenter(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2);
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
}
