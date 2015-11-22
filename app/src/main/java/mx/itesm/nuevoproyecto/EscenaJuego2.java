package mx.itesm.nuevoproyecto;

import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.input.touch.TouchEvent;

public class EscenaJuego2 extends EscenaBase implements IOnAreaTouchListener {

    @Override
    public void cargarRecursos() {

    }

    @Override
    public void crearEscena() {

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
