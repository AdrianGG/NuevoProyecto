package mx.itesm.nuevoproyecto;

import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
/**
 * Created by Adrian on 02/12/2015.
 */
public class GameOver extends EscenaBase {
    private ITextureRegion[] regionSlides;
    private ITextureRegion regionSlideActual;
    private Sprite spriteFondo;
    private int contador=0;
    private ITextureRegion regionBSiguente;
    private ButtonSprite bSiguente;

    @Override
    public void cargarRecursos() {
        regionSlides = new ITextureRegion[0];
        regionSlides[0] = cargarImagen("final.jpg");
        regionSlideActual=regionSlides[0];
        regionBSiguente= cargarImagen("botonrosamenu.png");

    }

    @Override
    public void crearEscena() {
        spriteFondo = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, regionSlideActual);
        attachChild(spriteFondo);

        bSiguente=  new ButtonSprite(ControlJuego.ANCHO_CAMARA-50,ControlJuego.ALTO_CAMARA/2,regionBSiguente,actividadJuego.getVertexBufferObjectManager()){
            @Override
            public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(pTouchEvent.isActionDown()) {
                    if(contador<7){
                        contador++;
                        regionSlideActual=regionSlides[contador];
                        GameOver.this.detachChild(spriteFondo);
                        GameOver.this.detachChild(this);
                        spriteFondo = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, regionSlideActual);
                        GameOver.this.attachChild(spriteFondo);
                        GameOver.this.attachChild(this);

                    }
                    else{
                        admEscenas.crearEscenaMenu();
                        admEscenas.setEscena(TipoEscena.ESCENA_MENU);
                        admEscenas.liberarGameover();
                    }

                }
                return true;
            }
        };
        registerTouchArea(bSiguente);
        setTouchAreaBindingOnActionDownEnabled(true);
        attachChild(bSiguente);


    }

    @Override
    public void onBackKeyPressed() {
        admEscenas.crearEscenaMenu();
        admEscenas.setEscena(TipoEscena.ESCENA_MENU);
        admEscenas.liberarGameover();

    }

    @Override
    public TipoEscena getTipoEscena() {
        return TipoEscena.ESCENA_GAMEOVER;
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

