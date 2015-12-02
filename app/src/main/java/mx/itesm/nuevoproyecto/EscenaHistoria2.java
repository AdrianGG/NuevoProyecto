package mx.itesm.nuevoproyecto;

import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * Created by A. iram on 26/10/2015.
 */
public class EscenaHistoria2 extends EscenaBase {
    private ITextureRegion[] regionSlides;
    private ITextureRegion regionSlideActual;
    private Sprite spriteFondo;
    private int contador=0;
    private ITextureRegion regionBSkip;
    private ITextureRegion regionBSiguente;
    private ButtonSprite bSkip;
    private ButtonSprite bSiguente;

    @Override
    public void cargarRecursos() {
        regionSlides = new ITextureRegion[2];
        regionSlides[0] = cargarImagen("juego7.jpg");
        regionSlides[1] = cargarImagen("juego8.jpg");
        regionSlideActual=regionSlides[0];
        regionBSkip= cargarImagen("botonazulmenu.png");
        regionBSiguente= cargarImagen("botonrosamenu.png");

    }

    @Override
    public void crearEscena() {
        spriteFondo = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, regionSlideActual);
        attachChild(spriteFondo);

        bSkip= new ButtonSprite(ControlJuego.ANCHO_CAMARA/4,ControlJuego.ALTO_CAMARA/2,regionBSkip,actividadJuego.getVertexBufferObjectManager()){
            @Override
            public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(pTouchEvent.isActionDown()) {
                    admEscenas.crearEscenaJuego2();
                    admEscenas.setEscena(TipoEscena.ESCENA_JUEGO2);
                    admEscenas.liberarEscenaHistoria2();
                }
                return true;
            }
        };
        registerTouchArea(bSkip);
        setTouchAreaBindingOnActionDownEnabled(true);
        attachChild(bSkip);
        bSiguente=  new ButtonSprite(ControlJuego.ANCHO_CAMARA-50,ControlJuego.ALTO_CAMARA/2,regionBSkip,actividadJuego.getVertexBufferObjectManager()){
            @Override
            public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(pTouchEvent.isActionDown()) {
                    if(contador<1){
                        contador++;
                        regionSlideActual=regionSlides[contador];
                        EscenaHistoria2.this.detachChild(spriteFondo);
                        EscenaHistoria2.this.detachChild(this);
                        spriteFondo = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, regionSlideActual);
                        EscenaHistoria2.this.attachChild(spriteFondo);
                        EscenaHistoria2.this.attachChild(this);

                    }
                    else{
                        admEscenas.crearEscenaJuego2();
                        admEscenas.setEscena(TipoEscena.ESCENA_JUEGO2);
                        admEscenas.liberarEscenaHistoria2();
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
        admEscenas.liberarEscenaHistoria2();

    }

    @Override
    public TipoEscena getTipoEscena() {
        return TipoEscena.ESCENA_HISTORIA2;
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
