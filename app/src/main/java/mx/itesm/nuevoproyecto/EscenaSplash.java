package mx.itesm.nuevoproyecto;

import android.content.Context;
import android.util.Log;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.music.MusicManager;
import org.andengine.engine.Engine;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseActivity;

import java.io.IOException;
import java.security.PublicKey;

/**
 * Created by A. iram on 01/10/2015.
 */
public class EscenaSplash extends EscenaBase {
    // Imágenes
    private ITextureRegion regionFondo;
    // Sprites sobre la escena

    private Sprite spriteFondo;
    public Music musicaFondo;


    // Carga todos los recursos para ESTA ESCENA.
    private void cargarSonidos() {
        try {
            musicaFondo = MusicFactory.createMusicFromAsset(admEscenas.engine.getMusicManager(), actividadJuego, "music/Trancer.mp3");
        }
        catch (IOException e) {
            Log.i("cargarSonidos", "No se puede cargar la musica");
        }
        // Reproducir
        musicaFondo.setLooping(true);
        musicaFondo.play();
    }
    @Override
    public void cargarRecursos() {
        regionFondo = cargarImagen("logotec.png");

    }

    // Arma la escena que se presentará en pantalla
    @Override
    public void crearEscena() {
        // Crea el(los) sprite(s) de la escena
        spriteFondo = cargarSprite(ControlJuego.ANCHO_CAMARA/2, ControlJuego.ALTO_CAMARA/2,
                regionFondo);

       // Crea el fondo de la pantalla rojo verde azul
        SpriteBackground fondo = new SpriteBackground(.82f, .25f, .68f,spriteFondo);

        setBackground(fondo);
        setBackgroundEnabled(true);

    }

    // La escena se debe actualizar en este método que se repite "varias" veces por segundo
    // Aquí es donde programan TODA la acción de la escena (movimientos, choques, disparos, etc.)
    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);

    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public TipoEscena getTipoEscena() {
        return TipoEscena.ESCENA_SPLASH;
    }

    // Libera la escena misma del engine
    @Override
    public void liberarEscena() {
        this.detachSelf();      // La escena se deconecta del engine
        this.dispose();         // Libera la memoria
        // Libera las imágenes
        liberarRecursos();


    }

    // Libera cada una de las regiones asignadas para esta escena
    @Override
    public void liberarRecursos() {
        // Estas dos instrucciones por cada región inicializada
        regionFondo.getTexture().unload();
        regionFondo = null;
    }
}
