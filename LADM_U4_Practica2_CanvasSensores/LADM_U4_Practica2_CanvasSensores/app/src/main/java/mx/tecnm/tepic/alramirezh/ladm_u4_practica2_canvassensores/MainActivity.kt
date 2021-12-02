package mx.tecnm.tepic.alramirezh.ladm_u4_practica2_canvassensores

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() , SensorEventListener {

    lateinit var sensorManager: SensorManager
    var maxLuzAmbiente = 0f
    var maxProximidad = 0f
    var luzDetectada = 0f
    var proximidadDetectada = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vista = Lienzo(this)
        setContentView( vista )

        sensorManager = getSystemService( Context.SENSOR_SERVICE ) as SensorManager
        sensorManager.registerListener( this ,
            sensorManager.getDefaultSensor( Sensor.TYPE_LIGHT ) ,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        sensorManager.registerListener( this ,
            sensorManager.getDefaultSensor( Sensor.TYPE_PROXIMITY) ,
            SensorManager.SENSOR_DELAY_NORMAL
        )

        AlertDialog.Builder(this)
            .setTitle("Cuida el Girasol")
            .setMessage(
                "Coloca tu movil en un area muy iluminada o con suficiente luz ambiente  para suministrarle energia solar al girasol " +
                "despues de un cierto tiempo aleja el movil de la luz para evitar sobrecargarla.\n\n" +
                "Manten el tallo en color VERDE. Si esta en color ROJO significa que esta demasiado expuesta a la luz.\n\n" +
                "Coloca tu dedo sobre el sensor de proximidad de tu movil para suministrarle agua al girasol. " +
                "Evita llenar el recipiente sobre el cual esta el girasol.\n\n" +
                "NOTA:\n" +
                "Si el recipiente esta VACIO o el tallo en color CAFE, al suministrar agua o energia puede tardar un " +
                "poco mas en surtir efecto en el girasol.")
            .setPositiveButton("Comenzar"){ view , id ->
                view.dismiss()
                vista.hiloDecremento.start()
                vista.hiloGenerar.start()
                vista.hiloRepintar.start()
            }
            .show()
    }

    override fun onSensorChanged(evento: SensorEvent) {
        //comprobar el tipo de sensor que genero el evento
        if( evento.sensor!!.type == Sensor.TYPE_LIGHT ){
            luzDetectada = evento.values[0]
            if( maxLuzAmbiente == 0f ) maxLuzAmbiente = evento.sensor!!.maximumRange
        }
        else{
            proximidadDetectada = evento.values[0]
            if( maxProximidad == 0f )  maxProximidad = evento.sensor!!.maximumRange
        }
       /* txtPrueba.setText( "Max valor del sensor de luz: ${maxLuzAmbiente}\nluz: ${luzDetectada}\n\n" +
                "Max valor del sensor de proximidad: ${maxProximidad}\nproximidad: ${proximidadDetectada}" )*/
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
}
