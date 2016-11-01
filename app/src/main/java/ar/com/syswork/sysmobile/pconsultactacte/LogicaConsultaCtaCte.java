package ar.com.syswork.sysmobile.pconsultactacte;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Handler.Callback;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.daos.DaoCliente;
import ar.com.syswork.sysmobile.daos.DataManager;
import ar.com.syswork.sysmobile.entities.Cliente;
import ar.com.syswork.sysmobile.entities.ItemCtaCte;
import ar.com.syswork.sysmobile.shared.AppSysMobile;

public class LogicaConsultaCtaCte implements Callback{
	
	private PantallaManagerConsultaCtaCte pantallaManagerConsultaCtaCte;
	private Activity a;
	private ArrayList<ItemCtaCte> listaCtaCte;
	private AdapterConsultaCtaCte adapter ;
	private AppSysMobile app;
	private DataManager dm;
	private DaoCliente daoCliente;
	private Cliente cliente;
	
	public LogicaConsultaCtaCte(Activity a, PantallaManagerConsultaCtaCte pantallaManagerConsultaCtaCte)
	{
		this.pantallaManagerConsultaCtaCte = pantallaManagerConsultaCtaCte;
		this.a = a;
		
		// Creo la Lista
		listaCtaCte = new ArrayList<ItemCtaCte>();
		
		// Creo el Adapter
		adapter = new AdapterConsultaCtaCte(this.a, listaCtaCte);
		
		app = (AppSysMobile) a.getApplication();
		dm = app.getDataManager();
		daoCliente = dm.getDaoCliente();
		
		ListView lv = (ListView) a.findViewById(R.id.lvConsultaCtaCte);
		lv.setAdapter(this.adapter);
	} 

	public void seteaNombreCliente(String codCliente) {
		cliente = daoCliente.getByKey(codCliente);
		pantallaManagerConsultaCtaCte.seteaNombreCliente(cliente.getRazonSocial());
	}

	public void refreshAdapter()
	{
		adapter.notifyDataSetChanged();
	}
	
	public void consultarMovimientos(String cliente,String fechaDesde,String fechaHasta)
	{
		pantallaManagerConsultaCtaCte.muestraDialogoConsultCtaCte();
		
		Handler h = new Handler(this);
		ThreadConsultaCtaCte tc = new ThreadConsultaCtaCte (h,cliente,fechaDesde,fechaHasta);
        Thread t = new Thread(tc);
        t.start();

	}

	
	public void procesaResultado(String jsonCtaCte)
	{

		// Inicializo la lista
		listaCtaCte.clear();
		
		// Obtengo una lista Auxiliar en base al JsonRecibido
		ArrayList<ItemCtaCte> tmpListaCtaCte = new ArrayList<ItemCtaCte>();
		tmpListaCtaCte = parseaCtaCte(jsonCtaCte);
		if (tmpListaCtaCte.size()>0)
		{
			Iterator<ItemCtaCte> i = tmpListaCtaCte.iterator();
			while (i.hasNext())
			{
				listaCtaCte.add(i.next());
			}
		}
		
		refreshAdapter();
		
	}
	
	@Override
	public boolean handleMessage(Message msg) {
		
		pantallaManagerConsultaCtaCte.cierraDialogoConsultCtaCte();
		String resultado = (String)  msg.obj;

		switch(msg.arg1)
		{
		case 1:
			procesaResultado(resultado);
			break;
		case 2:
			
			// Inicializo la lista
			listaCtaCte.clear();
			refreshAdapter();
			pantallaManagerConsultaCtaCte.muestraToastErrorDeConexion();
			break;
		}
		return false;
	}
	
	public  ArrayList<ItemCtaCte> parseaCtaCte(String jsonCtaCte)
	{
		JSONArray arrayJson;
		JSONObject jsObject;

		ItemCtaCte itemCtaCte = null;
		ArrayList<ItemCtaCte> listaCtaCte = new ArrayList<ItemCtaCte>();

		try
		{
			arrayJson = new JSONArray(jsonCtaCte);
			for (int x = 0; x < arrayJson.length() ;x++)
			{
				jsObject = arrayJson.getJSONObject(x);
				
				itemCtaCte = new ItemCtaCte();
				itemCtaCte.setConcepto(jsObject.getString("detalle"));
				itemCtaCte.setFecha(jsObject.getString("fecha"));
				itemCtaCte.setTc(jsObject.getString("tc"));
				itemCtaCte.setSucNroLetra(jsObject.getString("sucNroLetra"));
				itemCtaCte.setImporte(jsObject.getDouble("importe"));
				
				listaCtaCte.add(itemCtaCte);
			}
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		}		
		
		return listaCtaCte;
		
	}

	public void tomarParametrosFechaDesdeHasta() {
		
		
		String fechaDesde = pantallaManagerConsultaCtaCte.obtieneFechaDesdeYYYYMMDD();
		String fechaHasta = pantallaManagerConsultaCtaCte.obtieneFechaHastaYYYYMMDD();
		
		if (fechaDesde.compareTo(fechaHasta)>0)
		{
			pantallaManagerConsultaCtaCte.muestraToastErrorDesdeMayorQueHasta();
			return;
		}
		
		consultarMovimientos(cliente.getCodigo(),fechaDesde,fechaHasta);
		
		pantallaManagerConsultaCtaCte.cierraDialogoConsultaFechaDesdeHasta();
	
	}
	
	
}