package mx.tecnm.tepic.alramirezh.ladm_u4_practica2_canvassensores

import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import android.widget.Toast
import java.util.ArrayList

class Lienzo(p: MainActivity): View(p) {

    val pantalla = p
    var wCanva = 0f
    var hCanva = 0f
    var girasol = Girasol()
    val hiloDecremento = HiloDecremento(this)
    val hiloGenerar = HiloGenerar(this)
    val hiloRepintar = HiloRepintar(this)
    var listaSoles = ArrayList<Sol>()
    var listaGotas = ArrayList<Gota>()



    override fun onDraw(c: Canvas) {
        super.onDraw(c)
        wCanva = c.width.toFloat()
        hCanva = c.height.toFloat()
        val pincel = Paint()

        //===================================
        //  ACTUALIZACION DE GOTAS
        //===================================
        var index2 = 0
        while (index2 < listaGotas.size) {
            val gota = listaGotas.get(index2)
            gota.actualizarDimensiones(c)
            if( gota.creado ){
                if( gota.seDebeEliminar() ) {
                    listaGotas.remove(gota)
                    girasol.aumentarAgua()
                }
                else {
                    gota.y += 4
                    gota.dibujar(c)
                    index2++
                }
            }
            else{
                gota.crear()
                gota.dibujar(c)
                index2++
            }
        }

        //===================================
        //  ACTUALIZACION DE SOLES
        //===================================
        var index = 0
        while (index < listaSoles.size) {
            val sol = listaSoles.get(index)
            sol.actualizarDimensiones(c)
            if( sol.creado ){
                if( sol.seDebeEliminar() ) {
                    listaSoles.remove(sol)
                    girasol.aumentarSol()
                }
                else {
                    sol.y += 4
                    sol.dibujar(c)
                    index++
                }
            }
            else{
                sol.crear()
                sol.dibujar(c)

                index++
            }
        }


        girasol.dibujar( c , pantalla )

    }



    fun generarSoles( luzDetectada: Float , luzMaxima: Float ){

        // VERIFICAR SI HAY SOLES QUE TODAVIA NO SE VISUALICEN
        var todosVisibles = true
        listaSoles.forEach {
            if( it.y < 0 ) todosVisibles = false
        }
        if( todosVisibles ) {
            if( luzMaxima == 0f ) return
            val porcentaje = luzDetectada / luzMaxima * 100f
            var cantidad = 0
            if (porcentaje < 3f) return
            else if (porcentaje < 10f) {
                cantidad = ((Math.random() * 3) + 1 ).toInt()   // 1 - 3 soles
                for (i in 1..cantidad) listaSoles.add(Sol(pantalla))
            } else if (porcentaje < 20f) {
                cantidad = ((Math.random() * 4) + 4).toInt()   // 4 - 7 soles
                for (i in 1..cantidad) listaSoles.add(Sol(pantalla))
            } else if (porcentaje < 30f) {
                cantidad = ((Math.random() * 4) + 8).toInt()   // 8 - 11 soles
                for (i in 1..cantidad) listaSoles.add(Sol(pantalla))
            } else if (porcentaje < 40) {
                cantidad = ((Math.random() * 4) + 12).toInt()   // 12 - 15 soles
                for (i in 1..cantidad) listaSoles.add(Sol(pantalla))
            } else {
                cantidad = ((Math.random() * 5) + 16).toInt()   // 16 - 20 soles
                for (i in 1..cantidad) listaSoles.add(Sol(pantalla))
            }
            val datos = "+ ${cantidad} soles\n ${porcentaje.toInt()}% de luz\n ${luzDetectada.toInt()} lux"
            pantalla.runOnUiThread {
                Toast.makeText(pantalla , datos , Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun generarGotas( proximidadDetectada: Float , proximidadMaxima: Float ){

        // VERIFICAR SI HAY GOTAS QUE TODAVIA NO SE VISUALICEN
        var todosVisibles = true
        listaGotas.forEach {
            if( it.y < 0 ) todosVisibles = false
        }
        if( todosVisibles ) {
            if( proximidadMaxima == 0f ) return
            val porcentaje = proximidadDetectada / proximidadMaxima * 100f
            var cantidad = 0
            if (porcentaje > 3f) return
            /*
            else if (porcentaje < 20f) {
                cantidad = ((Math.random() * 3) + 1 ).toInt()   // 1 - 3 gotas
                for (i in 1..cantidad) listaGotas.add(Gota(pantalla))
            } else if (porcentaje < 40f) {
                cantidad = ((Math.random() * 4) + 4).toInt()   // 4 - 7 gotas
                for (i in 1..cantidad) listaGotas.add(Gota(pantalla))
            } else if (porcentaje < 60f) {
                cantidad = ((Math.random() * 4) + 8).toInt()   // 8 - 11 gotas
                for (i in 1..cantidad) listaGotas.add(Gota(pantalla))
            } else if (porcentaje < 80) {
                cantidad = ((Math.random() * 4) + 12).toInt()   // 12 - 15 gotas
                for (i in 1..cantidad) listaGotas.add(Gota(pantalla))
            } */
            else {
                cantidad = ((Math.random() * 7) + 12).toInt()   // 12 - 18 gotas
                for (i in 1..cantidad)  listaGotas.add(Gota(pantalla))
            }
            pantalla.runOnUiThread {
                Toast.makeText(pantalla , "+ ${cantidad} gotas\nRegadera ACTIVADA", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun posX( porcentaje: Float , desfase: Float ): Float  { return ( porcentaje * wCanva / 100f ) + desfase  }
    fun posY( porcentaje: Float , desfase: Float ): Float { return ( porcentaje * hCanva / 100f ) + desfase }

}





class HiloDecremento( l: Lienzo ): Thread()
{
    val lienzo = l
    override fun run() {
        super.run()
        while( true )
        {
            sleep(300)
            lienzo.girasol.decrementarValores()
        }
    }
}



class HiloGenerar( l: Lienzo ): Thread() {
    val lienzo = l

    override fun run() {
        super.run()
        while( true )
        {
            sleep(800)
            // CREAR MAS GOTAS EN BASE AL VALOR DEL SENSOR
            lienzo.generarGotas( lienzo.pantalla.proximidadDetectada , lienzo.pantalla.maxProximidad )

            // CREAR MAS SOLES EN BASE AL VALOR DEL SENSOR
            lienzo.generarSoles( lienzo.pantalla.luzDetectada , lienzo.pantalla.maxLuzAmbiente )
        }
    }
}




class HiloRepintar( l: Lienzo ): Thread() {
    val lienzo = l

    override fun run() {
        super.run()
        while( true )
        {
            sleep(70)
            lienzo.pantalla.runOnUiThread {
                lienzo.invalidate()
            }
        }
    }
}