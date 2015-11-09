package mx.itesm.nuevoproyecto;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * Created by A. iram on 01/10/2015.
 */
public class EscenaJuego extends EscenaBase {
   
    private ITextureRegion[] regionSlides;
    private ITextureRegion regionSlideActual;
    private ITextureRegion regionBSkip;
    private ITextureRegion regionBSiguente;
    private Sprite spriteFondo;
    private ButtonSprite bSkip;
    private ButtonSprite bSiguente;





    
    @Override
    public void cargarRecursos() {

      //  regionFondo = cargarImagen("escenasiguiente.jpg");
        regionSlides = new ITextureRegion[6];
        regionSlides[0] = cargarImagen("juego1.jpg");
        regionSlides[1] = cargarImagen("juego2.jpg");
        regionSlides[2] = cargarImagen("juego3.jpg");
        regionSlides[3] = cargarImagen("juego4.jpg");
        regionSlides[4] = cargarImagen("juego5.jpg");
        regionSlides[5] = cargarImagen("juego6.jpg");
        regionBSkip= cargarImagen("bskip.png");
        regionBSiguente= cargarImagen("bskip.png");
        regionSlideActual=regionSlides[0];


    }

    @Override
    public void crearEscena() {

       spriteFondo = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, regionSlideActual);
       attachChild(spriteFondo);

        bSkip= new ButtonSprite(ControlJuego.ANCHO_CAMARA/4,ControlJuego.ALTO_CAMARA/2,regionBSkip,actividadJuego.getVertexBufferObjectManager()){
            @Override
            public boolean onAreaTouched(TouchEvent pTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(pTouchEvent.isActionDown()) {
                    admEscenas.crearEscenaJuego1();
                    admEscenas.setEscena(TipoEscena.ESCENA_JUEGO1);
                    admEscenas.liberarEscenaJuego();
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
                    if(contador<5){
                        contador++;
                        regionSlideActual=regionSlides[contador];
                        EscenaJuego.this.detachChild(spriteFondo);
                        EscenaJuego.this.detachChild(this);
                        spriteFondo = cargarSprite(ControlJuego.ANCHO_CAMARA / 2, ControlJuego.ALTO_CAMARA / 2, regionSlideActual);
                        EscenaJuego.this.attachChild(spriteFondo);

                        EscenaJuego.this.attachChild(this);
                    }
                    else{
                        admEscenas.crearEscenaJuego1();
                        admEscenas.setEscena(TipoEscena.ESCENA_JUEGO1);
                        admEscenas.liberarEscenaJuego();
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
