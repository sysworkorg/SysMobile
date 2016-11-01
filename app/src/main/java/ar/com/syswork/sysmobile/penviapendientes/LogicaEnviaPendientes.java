package ar.com.syswork.sysmobile.penviapendientes;

import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Handler.Callback;
import android.os.Handler;
import android.os.Message;

import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.daos.DaoPedido;
import ar.com.syswork.sysmobile.daos.DaoPedidoItem;
import ar.com.syswork.sysmobile.daos.DataManager;
import ar.com.syswork.sysmobile.entities.Pedido;
import ar.com.syswork.sysmobile.entities.PedidoItem;
import ar.com.syswork.sysmobile.shared.AppSysMobile;

public class LogicaEnviaPendientes implements Callback{
	
	private DataManager dm;
	private AppSysMobile app;
	private DaoPedido daoPedido;
	private DaoPedidoItem daoPedidoItem;
	
	private Activity a;
	private PantallaManagerEnviaPendientes pantallaManagerEnviaPendientes;

	private boolean desdeCargaDePedidos=false;
	private long idPedidoEnviar = -1;
	
	List <Pedido> listaPedidos;
	
	public LogicaEnviaPendientes(Activity a, PantallaManagerEnviaPendientes pantallaManagerEnviaPendientes)
	{
		this.a = a;
		this.pantallaManagerEnviaPendientes = pantallaManagerEnviaPendientes;
		
		app = (AppSysMobile) this.a.getApplication(); 
		dm = app.getDataManager();
		daoPedido = dm.getDaoPedido();
		daoPedidoItem = dm.getDaoPedidoItem();

	}

	public void enviarPedidosPendientes()
	{
		
		String jSonPedidos = obtieneJsonPedidos();
		
		if (jSonPedidos.equals(""))
		{
			pantallaManagerEnviaPendientes.mostrarMensajeNoHayRegistrosPendientes();
			return;
		}
		
		pantallaManagerEnviaPendientes.muestraDialogoEnviaPendientes();
		pantallaManagerEnviaPendientes.seteaTxtResultadoEnvio(a.getString(R.string.conectando));

		Handler h = new Handler(this);
        ThreadEnvio te = new ThreadEnvio(h,jSonPedidos);
        Thread t = new Thread(te);
        t.start();
	}	

	private String obtieneJsonPedidos()
	{
		String jSonPedidos = "";
		JSONArray jsonArrayPedidos = null;
		
		JSONObject jsonPedido;
		
		Pedido pedido;	
		
		if (idPedidoEnviar==-1)
			listaPedidos = daoPedido.getAll(" transferido = 0");
		else
			listaPedidos = daoPedido.getAll(" _id = " + idPedidoEnviar);
			
		if (listaPedidos.size() > 0)
		{
			
			jsonArrayPedidos = new JSONArray();
			
			Iterator<Pedido> i = listaPedidos.iterator();
			while (i.hasNext())
			{
				pedido = i.next();
				
				jsonPedido = new JSONObject();
				try 
				{
					jsonPedido.put("idPedido", pedido.getIdPedido());
					jsonPedido.put("idCliente", pedido.getCodCliente());
					jsonPedido.put("idVendedor", pedido.getIdVendedor());
					jsonPedido.put("fecha", pedido.getFecha());
					jsonPedido.put("totalNeto", pedido.getTotalNeto());
					jsonPedido.put("totalFinal", pedido.getTotalFinal());
					jsonPedido.put("facturar", pedido.isFacturar());
					jsonPedido.put("incluirEnReparto", pedido.isIncluirEnReparto());
					jsonPedido.put("detallePedido", obtieneJsonDetalleDePedido(pedido.getIdPedido()));
					
					jsonArrayPedidos.put(jsonPedido);
				} 
				catch (JSONException e) 
				{
					e.printStackTrace();
				}
			}
			
			jSonPedidos = jsonArrayPedidos.toString();
		}
		return jSonPedidos;
	}
	
	private JSONArray obtieneJsonDetalleDePedido(long idPedido)
	{
		
		PedidoItem pedidoItem;
		JSONObject jsoPedidoItem;
		JSONArray jsa = null ;
		
		List <PedidoItem> listaItemsPedidos = daoPedidoItem.getAll(" idPedido = " + idPedido);
		if (listaItemsPedidos.size() > 0)
		{
			jsa = new JSONArray();
			Iterator<PedidoItem> i = listaItemsPedidos.iterator();
			while (i.hasNext())
			{
				pedidoItem = i.next();
				jsoPedidoItem = new JSONObject();
				
				try {
				
					jsoPedidoItem.put("idPedido", pedidoItem.getIdPedido());
					jsoPedidoItem.put("idArticulo", pedidoItem.getIdArticulo());
					jsoPedidoItem.put("cantidad", pedidoItem.getCantidad());
					jsoPedidoItem.put("importeUnitario", pedidoItem.getImporteUnitario());
					jsoPedidoItem.put("porcDto", pedidoItem.getPorcDto());
					jsoPedidoItem.put("total", pedidoItem.getTotal());
					
					jsa.put(jsoPedidoItem);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		}
		return jsa;
	}
	
	@Override
	public boolean handleMessage(Message msg) {
		
		// RECIBO ERRORES
		String resultado = (String)  msg.obj;

		switch (msg.arg1)
		{
			// recibo 0/1
			case 1:
				
				// Si esta Ok, muestro aviso
				if (resultado.equals("1"))
				{ 
					// Recorre la lista de pedidos y elimina el pedido y los items
					pantallaManagerEnviaPendientes.seteaValorchkEnviarPendientes(true);
					pantallaManagerEnviaPendientes.seteaTxtResultadoEnvio(a.getString(R.string.seCrearonLosPedidosExitosamente));
					pantallaManagerEnviaPendientes.seteaimgSincronizarResultadoVisible(true);
					pantallaManagerEnviaPendientes.seteaProgressBarVisible(false);
					
					if (desdeCargaDePedidos)
					{
						pantallaManagerEnviaPendientes.cerrarDialogoSincronizacion();
						pantallaManagerEnviaPendientes.cerrarActivity();
					}
					else
					{
						pantallaManagerEnviaPendientes.seteaBtnCerrarEnvioPendientesVisible(true);
					}
					
					eliminaPedidos();
				}
				else
				{
					// Muestro toast
					pantallaManagerEnviaPendientes.seteaTxtResultadoEnvio(a.getString(R.string.seProdujoUnErrorAlSubirLosPedidos));
					pantallaManagerEnviaPendientes.seteaimgSincronizarResultadoVisible(true);
					pantallaManagerEnviaPendientes.seteaProgressBarVisible(false);
					pantallaManagerEnviaPendientes.seteaBtnCerrarEnvioPendientesVisible(true);				
				}
				break;
			
			// hubo error de comunicaciones
			case 2:
				
				pantallaManagerEnviaPendientes.seteaTxtResultadoEnvio(a.getString(R.string.seProdujoUnErrorDeComunicacion) + "( " + resultado + ")" );
				pantallaManagerEnviaPendientes.seteaimgSincronizarResultadoVisible(true);
				pantallaManagerEnviaPendientes.seteaProgressBarVisible(false);
				pantallaManagerEnviaPendientes.seteaBtnCerrarEnvioPendientesVisible(true);
				break;
		}
		
		return false;
	}
	
	
	private void eliminaPedidos()
	{
		if (idPedidoEnviar != -1)
		{
			daoPedido.deleteAll("_id = " + idPedidoEnviar);
			daoPedidoItem.deleteByIdPedido(idPedidoEnviar);
		}
		else
		{
			daoPedido.deleteAll("");
			daoPedidoItem.deleteAll("");
		}
	}
	
	public void setDesdeCargaDePedidos(boolean desdeCargaDePedidos) {
		this.desdeCargaDePedidos = desdeCargaDePedidos;
	}

	
	public void setIdPedidoEnviar(long idPedidoEnviar) 
	{
		this.idPedidoEnviar = idPedidoEnviar;
	}
}
