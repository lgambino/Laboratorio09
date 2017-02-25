package dam.isi.frsf.utn.edu.ar.laboratorio09;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView horaEnX;
    private TextView horaEnY;
    private TextView horaEnZ;
    private TextView magnitudEnX;
    private TextView magnitudEnY;
    private TextView magnitudEnZ;

    private Sensor sensorAceleracion;
    private SensorManager sensorManager;

    private List<Float> aceleraciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent intent = new Intent(MainActivity.this, Mensaje.class);
        startService(intent);

        intent = new Intent(MainActivity.this, FirebaseMensaje.class);
        startService(intent);

        horaEnX = (TextView) findViewById(R.id.valor_en_x);
        horaEnY = (TextView) findViewById(R.id.valor_en_y);
        horaEnZ = (TextView) findViewById(R.id.valor_en_z);
        magnitudEnX = (TextView) findViewById(R.id.magnitud_x);
        magnitudEnY = (TextView) findViewById(R.id.magnitud_y);
        magnitudEnZ = (TextView) findViewById(R.id.magnitud_z);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorAceleracion = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // inicialización
        aceleraciones = new ArrayList<Float>();
        aceleraciones.add((float) 0);
        aceleraciones.add((float) 0);
        aceleraciones.add((float) 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this, sensorAceleracion, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Calendar calendar = Calendar.getInstance();
        Date ahora = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ");

        if(event.values[0] > aceleraciones.get(0)){
            aceleraciones.set(0, event.values[0]);
            horaEnX.setText(simpleDateFormat.format(ahora));
            magnitudEnX.setText(aceleraciones.get(0).toString());
        }
        if(event.values[1] > aceleraciones.get(1)){
            aceleraciones.set(1, event.values[1]);
            horaEnY.setText(simpleDateFormat.format(ahora));
            magnitudEnY.setText(aceleraciones.get(1).toString());
        }
        if(event.values[2] > aceleraciones.get(2)){
            aceleraciones.set(2, event.values[2]);
            horaEnZ.setText(simpleDateFormat.format(ahora));
            magnitudEnZ.setText(aceleraciones.get(2).toString());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
