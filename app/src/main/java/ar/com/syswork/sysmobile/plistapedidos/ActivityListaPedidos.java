package ar.com.syswork.sysmobile.plistapedidos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.shared.AppSysMobile;

public class ActivityListaPedidos extends ActionBarActivity{

	private LogicaListaPedidos logicaListaPedidos;
	private BroadcastReceiver broadcastReceiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_pedidos);
		
		//Creo el Listener
		ListenerListaPedidos listenerListaPedidos = new ListenerListaPedidos();

		// Creo al PantallaManager
		PantallaManagerListaPedidos pantallaManagerListaPedidos = new PantallaManagerListaPedidos(this,listenerListaPedidos);
		pantallaManagerListaPedidos.seteaListener();
	
		//Creo la Logica
		logicaListaPedidos = new LogicaListaPedidos(this,pantallaManagerListaPedidos);
		
		//Seteo la Logica al Listener
		listenerListaPedidos.setLogica(logicaListaPedidos);
		
		logicaListaPedidos.cargarListaPedidos();
		
		registrarBroadcastReceiver();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_lista_pedidos, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		Log.d("SW","onOptionsItemSelected");
		switch (item.getItemId())
		{
			case R.id.mnu_agregar_pedido:
				Intent i = new Intent(this,ar.com.syswork.sysmobile.pconsultaclientes.ActivityConsultaClientes.class);
				i.putExtra("origenDeLaConsulta", AppSysMobile.DESDE_CARGA_DE_PEDIDOS);
				startActivity(i);
				break;
		}
		
		return false;
	}

	private void registrarBroadcastReceiver()
	{
		broadcastReceiver = new BroadcastReceiver() 
	    {
	        @Override
	        public void onReceive(Context context, Intent intent) 
	        {
	    		Log.d("SW","Escucha modificacion pedidos");
	        	logicaListaPedidos.cargarListaPedidos();
	        }
	    };
	    
	    IntentFilter intentFilter = new IntentFilter(AppSysMobile.INTENT_FILTER_CAMBIOS_LISTA_PEDIDOS);
	    registerReceiver(broadcastReceiver,intentFilter);
	
	}
	
	@Override
	protected void onDestroy() 
	{
		desRegistrarBroadcastReceiver();
		super.onDestroy();
	}
	
	private void desRegistrarBroadcastReceiver()
	{
		Log.d("SW","desRegistrarBroadcastReceiver()<-->PEDIDOS");
		unregisterReceiver(broadcastReceiver);
	}
	
}
