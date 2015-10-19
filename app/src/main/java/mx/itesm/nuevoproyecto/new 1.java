package mx.itesm.nuevoproyecto;

//

public void Obstaculo(String n) {
        regionObstaculo= cargarImagen(n);

        obstaculo =new Sprite(ControlJuego.ANCHO_CAMARA/2,
        ControlJuego.ALTO_CAMARA/2,	regionObstaculo,
        actividadJuego.getVertexBufferObjectManager());
        attachChild(obstaculo);
}