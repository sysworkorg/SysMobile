package ar.com.syswork.sysmobile.psincronizar;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import android.app.Activity;
import android.os.Handler.Callback;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.shared.AppSysMobile;
import ar.com.syswork.sysmobile.util.Utilidades;

public class ProcesaJson implements Callback{

	private String jSonVendedores;
	private String jSonRubros;
	private String jSonDepositos;
	
	//private String jSonClientes;
	//private String jSonArticulos;
	private ArrayList<String> alJsonClientes;
	private ArrayList<String> alJsonArticulos;
	 
	private PantallaManagerSincronizacion pantallaManagerSincronizacion;
	private int completadas = 0;
	
	private ThreadPoolExecutor executor;
	private ThreadParser tp;
	
	private String strProcesando;
	private String strProcesadoCorrectamente;
	
	
	private Activity a;
	
	public ProcesaJson(Activity a)
	{
		this.a =a;
		strProcesando = a.getString(R.string.procesando);
		strProcesadoCorrectamente = a.getString(R.string.procesado_correctamente);
		
		executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1) ;
	}
	
	public void procesarJson()
	{
		Handler h = new Handler(this);
		
		//pantallaManagerSincronizacion.seteatxtResultadoRubros(a.getString(R.string.procesando));
		tp = new ThreadParser(h, AppSysMobile.WS_DEPOSITOS,a,jSonDepositos,1);
		executor.execute(tp);
		
		pantallaManagerSincronizacion.seteatxtResultadoRubros(strProcesando);
		tp = new ThreadParser(h, AppSysMobile.WS_RUBROS,a,jSonRubros,1);
		executor.execute(tp);
		
		pantallaManagerSincronizacion.seteatxtResultadoVendedores(strProcesando);
		tp = new ThreadParser(h, AppSysMobile.WS_VENDEDORES,a,jSonVendedores,1);
		executor.execute(tp);
		
		pantallaManagerSincronizacion.seteaTxtResultadoArticulos(strProcesando);
		for (int pagina = 0; pagina < alJsonArticulos.size();pagina++){
			tp = new ThreadParser(h, AppSysMobile.WS_ARTICULOS,a, alJsonArticulos.get(pagina),pagina + 1);
			executor.execute(tp);
		}
		
		pantallaManagerSincronizacion.seteatxtResultadoClientes(strProcesando);
		for (int pagina = 0; pagina < alJsonClientes.size();pagina++){
			tp = new ThreadParser(h, AppSysMobile.WS_CLIENTES,a,alJsonClientes.get(pagina),pagina + 1);
			executor.execute(tp);
		}
		
	}
	
	@Override
	public boolean handleMessage(Message msg) {
		
		switch (msg.arg1)
		{
			case AppSysMobile.WS_ARTICULOS:
				
				if (msg.arg2 == alJsonArticulos.size()) 
					pantallaManagerSincronizacion.seteaTxtResultadoArticulos(strProcesadoCorrectamente);
				else
					pantallaManagerSincronizacion.seteaTxtResultadoArticulos(strProcesando + Utilidades.obtienePorcentaje(alJsonArticulos.size(), msg.arg2) + " %");
				break;
				
			case AppSysMobile.WS_CLIENTES:
				if (msg.arg2 == alJsonClientes.size()) 
					pantallaManagerSincronizacion.seteatxtResultadoClientes(strProcesadoCorrectamente);
				else
					pantallaManagerSincronizacion.seteatxtResultadoClientes(strProcesando + Utilidades.obtienePorcentaje(alJsonClientes.size(), msg.arg2) + " %");
				break;
			case AppSysMobile.WS_RUBROS:
				pantallaManagerSincronizacion.seteatxtResultadoRubros(strProcesadoCorrectamente);
				break;
			case AppSysMobile.WS_DEPOSITOS:
				//pantallaManagerSincronizacion.seteatxtResultadoRubros(a.getString(R.string.procesado_correctamente));
				break;
			case AppSysMobile.WS_VENDEDORES:
				pantallaManagerSincronizacion.seteatxtResultadoVendedores(strProcesadoCorrectamente);
				break;
		}
		
		
		completadas++;
		
		if (executor.getTaskCount()== completadas)
		{
			Log.d("SW","Se terminaron de ejecutar las tareas");
			pantallaManagerSincronizacion.setVisibleBtnCerrarSincronizacion(true);
			pantallaManagerSincronizacion.seteaProgressBarVisible(false);
			pantallaManagerSincronizacion.seteaImgSincronizarVisible(true);
		}
		
		return false;
	}


	public void setjSonVendedores(String jSonVendedores) {
		this.jSonVendedores = jSonVendedores;
	}

	public void setjSonRubros(String jSonRubros) {
		this.jSonRubros = jSonRubros;
	}
	
	public void setjSonDepositos(String jSonDepositos) {
		this.jSonDepositos = jSonDepositos;
	}

	public void setalJsonClientes(ArrayList<String> alJsonClientes) {
		this.alJsonClientes = alJsonClientes;
	}

	public void setalJsonArticulos(ArrayList<String> alJsonArticulos) {
		this.alJsonArticulos = alJsonArticulos;
		
	}

	public void setPantallaManager(PantallaManagerSincronizacion pantallaManagerSincronizacion) {
		this.pantallaManagerSincronizacion = pantallaManagerSincronizacion; 
	}
	
}
