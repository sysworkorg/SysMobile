package ar.com.syswork.sysmobile.psincronizar;


import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import ar.com.syswork.sysmobile.daos.DaoArticulo;
import ar.com.syswork.sysmobile.daos.DaoCliente;
import ar.com.syswork.sysmobile.daos.DaoDeposito;
import ar.com.syswork.sysmobile.daos.DaoRubro;
import ar.com.syswork.sysmobile.daos.DaoVendedor;
import ar.com.syswork.sysmobile.daos.DataManager;
import ar.com.syswork.sysmobile.entities.Articulo;
import ar.com.syswork.sysmobile.entities.Cliente;
import ar.com.syswork.sysmobile.entities.Deposito;
import ar.com.syswork.sysmobile.entities.Rubro;
import ar.com.syswork.sysmobile.entities.Vendedor;
import ar.com.syswork.sysmobile.shared.AppSysMobile;
import ar.com.syswork.sysmobile.util.Utilidades;

public class ThreadParser implements Runnable{

	private int tipoParser;
	private Handler h;
	
	private AppSysMobile app;
	private DataManager dataManager;
	
	private DaoCliente daoCliente;
	private DaoArticulo daoArticulo;
	private DaoRubro daoRubro;
	private DaoVendedor daoVendedor;
	private DaoDeposito daoDeposito;
	
	
	private Cliente cliente;
	private Articulo articulo;
	private Rubro rubro;
	private Deposito deposito;
	private Vendedor vendedor;

	private JSONArray arrayJson;
	private JSONObject jsObject;
	
	private int pagina;
	
	private Activity a;
	private String msgJson;
	
	public ThreadParser(Handler h,int tipoParser,Activity a,String msgJson,int pagina)
	{
		this.h = h;
		this.a=a;
		this.msgJson = msgJson;
		this.pagina = pagina;
		
		this.tipoParser = tipoParser;
		
		app = (AppSysMobile) this.a.getApplication();
		
		dataManager = app.getDataManager();
		daoCliente = dataManager.getDaoCliente();
		daoArticulo = dataManager.getDaoArticulo();
		daoRubro = dataManager.getDaoRubro();
		daoDeposito = dataManager.getDaoDeposito();
		daoVendedor = dataManager.getDaoVendedor();
	}
	
	@Override
	public void run() {
		
		switch (tipoParser)
		{
			case AppSysMobile.WS_VENDEDORES:
				grabaVendedores();
				Log.d("SW","grabo Vendedores()");
				break;
			case AppSysMobile.WS_RUBROS:
				grabaRubros();
				Log.d("SW","grabo Rubros()");
				break;
			case AppSysMobile.WS_DEPOSITOS:
				grabaDepositos();
				Log.d("SW","grabo Depositos()");
				break;
			case AppSysMobile.WS_CLIENTES:
				grabaClientes();
				Log.d("SW","grabo Clientes()");
				break;
			case AppSysMobile.WS_ARTICULOS:
				grabaArticulos();
				Log.d("SW","grabo Articulos()");
				break;
		}
		
		enviaMensaje("TERMINO", tipoParser);

	}

	private void grabaArticulos() {
		
		if (pagina == 1) 
			daoArticulo.deleteAll();
		
		articulo = new Articulo();
		
		try {
			msgJson = Utilidades.Decompress(msgJson);
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try
		{
			arrayJson = new JSONArray(msgJson);
			for (int x = 0; x<arrayJson.length() ;x++)
			{

				jsObject = arrayJson.getJSONObject(x);
				
				articulo.setIdArticulo(jsObject.getString("idArticulo"));
				articulo.setDescripcion(jsObject.getString("descripcion"));
				articulo.setIdRubro(jsObject.getString("idRubro"));
				
				articulo.setIva(jsObject.getDouble("iva"));
				articulo.setImpuestosInternos(jsObject.getDouble("impuestosInternos"));
				articulo.setExento(jsObject.getBoolean("exento"));
				
				articulo.setPrecio1(jsObject.getDouble("precio1"));
				articulo.setPrecio2(jsObject.getDouble("precio2"));
				articulo.setPrecio3(jsObject.getDouble("precio3"));
				articulo.setPrecio4(jsObject.getDouble("precio4"));
				articulo.setPrecio5(jsObject.getDouble("precio5"));
				articulo.setPrecio6(jsObject.getDouble("precio6"));
				articulo.setPrecio7(jsObject.getDouble("precio7"));
				articulo.setPrecio8(jsObject.getDouble("precio8"));
				articulo.setPrecio9(jsObject.getDouble("precio9"));
				articulo.setPrecio10(jsObject.getDouble("precio10"));

				daoArticulo.save(articulo);
				
			}
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		}	
		
	}

	private void grabaClientes() {

		if (this.pagina == 1)
			daoCliente.deleteAll();
		
		cliente = new Cliente();
		
		// msgJson viene comprimido, lo descomprimo y continuo
		try {
			msgJson = Utilidades.Decompress(msgJson);
			
		} catch (IOException e1) {
			e1.printStackTrace();
			Log.d("SW","Error Decompress CLIENTES!!!");
		}
		
		try
		{
			arrayJson = new JSONArray(msgJson);
			for (int x = 0; x<arrayJson.length() ;x++)
			{
				jsObject = arrayJson.getJSONObject(x);
			
				
				cliente.setCodigo(jsObject.getString("codigo"));
				cliente.setCodigoOpcional(jsObject.getString("codigoOpcional"));
				cliente.setRazonSocial(jsObject.getString("razonSocial"));
				cliente.setCalleNroPisoDpto(jsObject.getString("calleNroPisoDpto"));
				cliente.setLocalidad (jsObject.getString("localidad"));
				cliente.setCuit(jsObject.getString("cuit")) ;
				cliente.setIva((byte)jsObject.getInt("iva"));
				cliente.setClaseDePrecio((byte)jsObject.getInt("claseDePrecio"));
				cliente.setPorcDto(jsObject.getInt("porcDto"));
				cliente.setCpteDefault(jsObject.getString("cpteDefault"));
				cliente.setIdVendedor(jsObject.getString("idVendedor"));
				cliente.setTelefono(jsObject.getString("telefono"));
				cliente.setMail(jsObject.getString("email"));
				
				daoCliente.save(cliente);
			}
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		}
		
		
	}

	private void grabaVendedores()
	{
		if (pagina==1)
			daoVendedor.deleteAll();

		vendedor = new Vendedor();

		try {
			msgJson = Utilidades.Decompress(msgJson);
			
		} catch (IOException e1) {
			e1.printStackTrace();
			Log.d("SW","Error Decompress VENDEDORES");
		}
		
		try
		{
			arrayJson = new JSONArray(msgJson);
			for (int x = 0; x<arrayJson.length() ;x++)
			{
				jsObject = arrayJson.getJSONObject(x);
				
				vendedor.setIdVendedor(jsObject.getString("idVendedor"));
				vendedor.setNombre(jsObject.getString("nombre"));
				vendedor.setCodigoDeValidacion(jsObject.getString("codigoValidacion"));
				
				daoVendedor.save(vendedor);
			}
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		}

	}
	
	private void grabaRubros()
	{
		if (this.pagina==1)
			daoRubro.deleteAll();
		
		rubro = new Rubro();
		
		try {
			msgJson = Utilidades.Decompress(msgJson);
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			Log.d("SW","Error Decompress RUBROS");
		}

		try
		{
			arrayJson = new JSONArray(msgJson);
			for (int x = 0; x < arrayJson.length() ;x++)
			{
				jsObject = arrayJson.getJSONObject(x);
				
				rubro.setIdRubro(jsObject.getString("idRubro"));
				rubro.setDescripcion(jsObject.getString("descripcion"));
				
				daoRubro.save(rubro);
			}
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		}		
		
	}
	
	private void grabaDepositos()
	{
		if (pagina == 1)
			daoDeposito.deleteAll();

		deposito = new Deposito();
		
		try {
			msgJson = Utilidades.Decompress(msgJson);
			
		} catch (IOException e1) {
			e1.printStackTrace();
			Log.d("SW","Error Decompress DEPOSITOS");
		}

		try
		{
			arrayJson = new JSONArray(msgJson);
			for (int x = 0; x < arrayJson.length() ;x++)
			{
				jsObject = arrayJson.getJSONObject(x);

				deposito.setIdDeposito(jsObject.getString("idDeposito"));
				deposito.setDescripcion(jsObject.getString("descripcion"));

				daoDeposito.save(deposito);
			}
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		}		
		
	}
	
	private void enviaMensaje(String mensaje, int tipoParser)
	{
		Message message = new Message();
		message.arg1 = tipoParser;
		message.arg2 = pagina;
		
		message.obj = mensaje;
		h.sendMessage(message);
	}
	
}
