package mx.itesm.nuevoproyecto;

import android.util.Log;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.util.GLState;

import java.io.IOException;

/**
 * Created by A. iram on 01/10/2015.
 */
public abstract class EscenaBase extends Scene {
    // Variable protegida para que se pueda acceder desde las subclases
    // Administrador de escenas
    protected AdministradorEscenas admEscenas;
    // Actividad principal del juego
    protected ControlJuego actividadJuego;

    public EscenaBase() {
        admEscenas = AdministradorEscenas.getInstance();
        // Llama al método que crea la escena
        this.actividadJuego = admEscenas.actividadJuego;
        cargarRecursos(); // Este método debe implementarse en la subclase
        crearEscena();  // Este método debe implementarse en la subclase
    }



    // Método auxiliar para cargar las imágenes de las regiones
    protected ITextureRegion cargarImagen(String archivo) {

        ITextureRegion region = null;
        try {
            ITexture textura = new AssetBitmapTexture(
                    actividadJuego.getTextureManager(), actividadJuego.getAssets(),archivo);
            textura.load();
            region = TextureRegionFactory.
                    extractFromTexture(textura);
        } catch (IOException e) {
            Log.i("cargarImagen()", "No se puede cargar: " + archivo);
        }
        return region;
    }

    // Método auxiliar para crear un Sprite.
    protected Sprite cargarSprite(float px, float py, final ITextureRegion regionFondo) {
        // Crea y regresa el Sprite
        return new Sprite(px, py, regionFondo, actividadJuego.getVertexBufferObjectManager()) {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) { // Optimizando
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        };
    }

    // Métodos abstractos
    public abstract void cargarRecursos();  // Carga imágenes/audio/música/videos/etc.
    public abstract void crearEscena(); // Arma la escena
    public abstract void onBackKeyPressed(); // Atiende el botón de back
    public abstract TipoEscena getTipoEscena(); // Regresa el tipo de escena
    public abstract void liberarEscena();   // Libera los elementos de la escena
    public abstract void liberarRecursos(); // Libera imágenes/audio/música/videos/etc.

}
