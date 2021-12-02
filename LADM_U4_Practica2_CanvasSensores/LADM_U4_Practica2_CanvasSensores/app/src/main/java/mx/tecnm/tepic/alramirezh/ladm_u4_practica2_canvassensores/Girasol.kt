package mx.tecnm.tepic.alramirezh.ladm_u4_practica2_canvassensores

import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat

class Girasol {
    var altura = 55f // 55% del height del canvas (pantalla)
    var wCanva = 0f
    var hCanva = 0f
    var totalAgua = 1000f    // 1,000 pts
    var totalSol = 1000f     // 1,000 pts
    var contadorAgua = 0f
    var contadorSol = 500f

    val anchoOvaloRecipiente = 20f
    val anchoRecipiente = 30f   // %
    val anchoTallo = 7f   // %
    var anchoCara = 25f   // %



    fun dibujar( c: Canvas , activity: MainActivity){
        wCanva = c.width.toFloat()
        hCanva = c.height.toFloat()
        val pincel = Paint()
        val porcentajeAgua = contadorAgua / totalAgua * 100f
        val porcentajeSol = contadorSol / totalSol * 100f


        //==================================
        // NIVEL DE AGUA DESBORDADO
        //==================================
        if( contadorAgua > totalAgua ) {
            pincel.color = ContextCompat.getColor(activity, R.color.azulAgua)
            pincel.style = Paint.Style.FILL
            c.drawRect(
                0f ,
                posY(100f - 5f , 0f ),
                posX(100f , 0f ),
                posY(100f, 0f),
                pincel
            )
        }

        //==================================
        // RECIPIENTE DE AGUA (NIVEL DE AGUA)
        //==================================
        pincel.color = ContextCompat.getColor(activity, R.color.azulAgua)
        pincel.style = Paint.Style.FILL
        c.drawRect(
            posX(50f - anchoRecipiente/2f , 0f ) ,
            posY( 100f , 0f - alturaAgua(porcentajeAgua) ) ,
            posX( 50f + anchoRecipiente/2f , 0f ) ,
            posY( 100f , 0f ) ,
            pincel
        )

        //==================================
        // RECIPIENTE DE AGUA (BORDE)
        //==================================
        pincel.color = ContextCompat.getColor(activity, R.color.negroLigero)
        pincel.style = Paint.Style.STROKE
        pincel.strokeWidth = 3f
        c.drawRect(
            posX(50f - anchoRecipiente/2f , 0f ) ,
            posY( 100f , 0f - alturaRecipiente() ) ,
            posX( 50f + anchoRecipiente/2f , 0f ) ,
            posY( 100f , 0f ) ,
            pincel
        )

        //==================================
        // PARTE SUPERIOR DEL CONTENEDOR RECIPIENTE
        //==================================
        pincel.color = ContextCompat.getColor(activity, R.color.negroLigero)
        pincel.style = Paint.Style.STROKE
        pincel.strokeWidth = 3f
        c.drawOval(
            posX(50f - anchoRecipiente/2f , 0f ) ,
            baseTallo() - anchoOvaloRecipiente ,
            posX( 50f + anchoRecipiente/2f , 0f ) ,
            baseTallo() ,
            pincel
        )

        //==================================
        // TALLO
        //==================================
        pincel.color = ContextCompat.getColor(activity, R.color.cafeLigero)
        pincel.style = Paint.Style.FILL
        pincel.strokeWidth = 3f
        c.drawRect(
            posX(50f - anchoTallo/2f , 0f ) ,
            limiteTallo() ,
            posX( 50f + anchoTallo/2f , 0f ) ,
            baseTallo() ,
            pincel
        )

        //==================================
        // TALLO NIVEL DEL SOL
        //==================================
        if( contadorSol > totalSol ) pincel.color = ContextCompat.getColor(activity, R.color.rojo)
        else pincel.color = ContextCompat.getColor(activity, R.color.verde)
        pincel.style = Paint.Style.FILL_AND_STROKE
        pincel.strokeWidth = 3f
        c.drawRect(
            posX(50f - anchoTallo/2f , 0f ) ,
            baseTallo() - alturaSol(porcentajeSol) ,
            posX( 50f + anchoTallo/2f , 0f ) ,
            baseTallo() ,
            pincel
        )

        //==================================
        // TALLO (BORDE)
        //==================================
        pincel.color = ContextCompat.getColor(activity, R.color.cafe)
        pincel.style = Paint.Style.STROKE
        pincel.strokeWidth = 3f
        c.drawRect(
            posX(50f - anchoTallo/2f , 0f ) ,
            limiteTallo() ,
            posX( 50f + anchoTallo/2f , 0f ) ,
            baseTallo() ,
            pincel
        )



        var xCara = posX( 50f , 0f )
        var yCara = limiteTallo() - posX( anchoCara/4*2 , 0f) + 5f

        //==================================
        // HOJA IZQUIERDA
        //==================================
        pincel.color = ContextCompat.getColor(activity, R.color.cafe)
        pincel.style = Paint.Style.FILL_AND_STROKE
        pincel.strokeWidth = 2f
        c.drawCircle(
            xCara - posX( anchoCara/2f , 0f ) , yCara ,
            posX( anchoCara/4f , 0f ) , pincel
        )

        //==================================
        // HOJA ESQUINA SUPERIOR IZQUIERDA
        //==================================
        pincel.color = ContextCompat.getColor(activity, R.color.cafe)
        pincel.style = Paint.Style.FILL_AND_STROKE
        pincel.strokeWidth = 2f
        c.drawCircle(
            xCara - posX( anchoCara/3f , 0f ) ,
            yCara - posX( anchoCara/3f , 0f )  ,
            posX( anchoCara/4f , 0f ) , pincel
        )

        //==================================
        // HOJA ESQUINA INFERIOR IZQUIERDA
        //==================================
        pincel.color = ContextCompat.getColor(activity, R.color.cafe)
        pincel.style = Paint.Style.FILL_AND_STROKE
        pincel.strokeWidth = 2f
        c.drawCircle(
            xCara - posX( anchoCara/3f , 0f ) ,
            yCara + posX( anchoCara/3f , 0f )  ,
            posX( anchoCara/4f , 0f ) , pincel
        )

        //==================================
        // HOJA DERECHA
        //==================================
        pincel.color = ContextCompat.getColor(activity, R.color.cafe)
        pincel.style = Paint.Style.FILL_AND_STROKE
        pincel.strokeWidth = 2f
        c.drawCircle(
            xCara + posX( anchoCara/2f , 0f ) , yCara ,
            posX( anchoCara/4f , 0f ) , pincel
        )

        //==================================
        // HOJA ESQUINA SUPERIOR DERECHA
        //==================================
        pincel.color = ContextCompat.getColor(activity, R.color.cafe)
        pincel.style = Paint.Style.FILL_AND_STROKE
        pincel.strokeWidth = 2f
        c.drawCircle(
            xCara + posX( anchoCara/3f , 0f ) ,
            yCara - posX( anchoCara/3f , 0f )  ,
            posX( anchoCara/4f , 0f ) , pincel
        )

        //==================================
        // HOJA ESQUINA INFERIOR DERECHA
        //==================================
        pincel.color = ContextCompat.getColor(activity, R.color.cafe)
        pincel.style = Paint.Style.FILL_AND_STROKE
        pincel.strokeWidth = 2f
        c.drawCircle(
            xCara + posX( anchoCara/3f , 0f ) ,
            yCara + posX( anchoCara/3f , 0f )  ,
            posX( anchoCara/4f , 0f ) , pincel
        )

        //==================================
        // HOJA SUPERIOR
        //==================================
        pincel.color = ContextCompat.getColor(activity, R.color.cafe)
        pincel.style = Paint.Style.FILL_AND_STROKE
        pincel.strokeWidth = 3f
        c.drawCircle(
            xCara ,
            yCara - posX( anchoCara/2f , 0f ) ,
            posX( anchoCara/4f , 0f ) , pincel
        )

        //==================================
        // HOJA INFERIOR
        //==================================
        pincel.color = ContextCompat.getColor(activity, R.color.cafe)
        pincel.style = Paint.Style.FILL_AND_STROKE
        pincel.strokeWidth = 2f
        c.drawCircle(
            xCara ,
            yCara + posX( anchoCara/2f , 0f ) ,
            posX( anchoCara/4f , 0f ) , pincel
        )

        //==================================
        // CONTORNO DE LA CARA
        //==================================
        pincel.color = ContextCompat.getColor(activity, R.color.amarillo)
        pincel.style = Paint.Style.FILL_AND_STROKE
        pincel.strokeWidth = 2f
        c.drawCircle( xCara , yCara , posX( anchoCara/2f , 0f ) , pincel )
    }




    fun alturaRecipiente(): Float { return posY( 20f , 0f ) }

    fun limiteTallo(): Float { return baseTallo() - alturaTallo() }
    fun baseTallo(): Float { return posY( 100f , 0f - alturaRecipiente() + anchoOvaloRecipiente/2f ) }
    fun alturaTallo(): Float { return posY( 28f , 0f ) }

    fun alturaAgua( porcentajeAgua: Float): Float {
        if( porcentajeAgua > 100f ) return alturaRecipiente()
        else if( porcentajeAgua < 0f ) return 0f
        return porcentajeAgua * alturaRecipiente() / 100f
    }

    fun alturaSol( porcentajeSol: Float): Float {
        if( porcentajeSol > 100f ) return alturaTallo()
        else if( porcentajeSol < 0f ) return 0f
        return porcentajeSol * alturaTallo() / 100f
    }


    fun decrementarValores(){
        if( contadorAgua > -30f  ) contadorAgua--
        if( contadorSol > -30f ) contadorSol--
    }

    fun aumentarSol(){
        contadorSol+=10
        if( contadorSol > 1030f ) contadorSol = 1030f
    }

    fun aumentarAgua(){
        contadorAgua+=10
        if( contadorAgua > 1030f  ) contadorAgua = 1030f
    }



    fun posX( porcentaje: Float , desfase: Float ): Float  { return ( porcentaje * wCanva / 100f ) + desfase  }
    fun posY( porcentaje: Float , desfase: Float ): Float { return ( porcentaje * hCanva / 100f ) + desfase }
}