package ar.com.syswork.sysmobile.psincronizar;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.content.pm.ActivityInfo;
import ar.com.syswork.sysmobile.R;


public class ActivitySincronizar extends ActionBarActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sincronizar);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		LogicaSincronizacion logicaSincronizacion = new LogicaSincronizacion(this);
		
		ListenerSincronizacion listenerSincronizacion = new ListenerSincronizacion(logicaSincronizacion);
		
		PantallaManagerSincronizacion pantallaManagerSincronizacion = new PantallaManagerSincronizacion(this, listenerSincronizacion);
		pantallaManagerSincronizacion.seteaListeners();
		
		listenerSincronizacion.setPantallaManager(pantallaManagerSincronizacion);
		
		logicaSincronizacion.setPantallaManager(pantallaManagerSincronizacion);
	}
			

}
