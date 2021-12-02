package mx.tecnm.tepic.alramirezh.ladm_u4_practica2_canvassensores

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint

class Sol( activity: MainActivity ) {

    var x = 0f
    var y = 0f
    var ancho = 0f
    var altura = 0f
    var img: Bitmap? = null
    var wCanva = 0f
    var hCanva = 0f
    val pantalla = activity
    var creado = false

    fun actualizarDimensiones( c: Canvas){
        wCanva = c.width.toFloat()
        hCanva = c.height.toFloat()
    }

    fun seDebeEliminar(): Boolean {
        if( y > hCanva ) return true
        return false
    }


    fun crear() {
        // CREAR IMAGEN
        val imgOriginal = BitmapFactory.decodeResource( pantalla.resources , R.drawable.sol )
        ancho = posX( 5f , 0f )
        val factor = ancho / imgOriginal.width
        altura = (imgOriginal.height * factor)
        img = Bitmap.createScaledBitmap( imgOriginal , ancho.toInt() , altura.toInt() , false )

        // VALORES X , Y ALEATORIOS
        x = ( Math.random() * ( posX( 100f , 0f ) - ancho ) ).toFloat()
        y = -( ( Math.random() * posY( 50f , 0f ) ) + altura ).toFloat()
        creado = true
    }


    fun dibujar(c: Canvas ) {
        c.drawBitmap( img!! , x , y , Paint() )
    }



    fun posX( porcentaje: Float , desfase: Float ): Float  { return ( porcentaje * wCanva / 100f ) + desfase  }
    fun posY( porcentaje: Float , desfase: Float ): Float { return ( porcentaje * hCanva / 100f ) + desfase }
}