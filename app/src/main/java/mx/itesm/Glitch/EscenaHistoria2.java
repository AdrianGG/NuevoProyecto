package mx.itesm.Glitch;

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
        regionSlides = new ITextureRegion[8];
        regionSlides[0] = cargarImagen("juego7.jpg");
        regionSlides[1] = cargarImagen("juego8.jpg");
        regionSlides[2] = cargarImagen("nivel21.jpg");
        regionSlides[3] = cargarImagen("nivel22.jpg");
        regionSlides[4] = cargarImagen("nivel23.jpg");
        regionSlides[5] = cargarImagen("nivel24.jpg");
        regionSlides[6] = cargarImagen("nivel25.jpg");
        regionSlides[7] = cargarImagen("nivel26.jpg");//
        regionSlideActual=regionSlides[0];
        regionBSkip= cargarImagen("skipBoton.png");
        regionBSiguente= cargarImagen("botonNext.png");

    }

    @Override
    public void crearEscena() {
        spriteFondo = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, regionSlideActual);
        attachChild(spriteFondo);

        bSkip= new ButtonSprite(ControlJuego.ANCHO_CAMARA-1200,ControlJuego.ALTO_CAMARA-120,regionBSkip,actividadJuego.getVertexBufferObjectManager()){
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
        bSiguente=  new ButtonSprite(ControlJuego.ANCHO_CAMARA-80,ControlJuego.ALTO_CAMARA-120,regionBSiguente,actividadJuego.getVertexBufferObjectManager()){
            @Override
            public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(pTouchEvent.isActionDown()) {
                    if(contador<7){
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
