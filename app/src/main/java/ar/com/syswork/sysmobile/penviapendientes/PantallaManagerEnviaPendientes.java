package ar.com.syswork.sysmobile.penviapendientes;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.util.DialogManager;

public class PantallaManagerEnviaPendientes {

	private Activity a;
	private ListenerEnviaPendientes listenerEnviaPendientes;
	private Dialog dialog;
	
	Button btnCerrarEnvioPendientes;
	TextView txtResultadoEnvio;
	CheckBox chkEnviarPendientes;
	ProgressBar progressBar;
	ImageView imgSincronizarResultado;
	

	public PantallaManagerEnviaPendientes(Activity a, ListenerEnviaPendientes listenerEnviaPendientes) 
	{
		this.a = a;
		this.listenerEnviaPendientes = listenerEnviaPendientes;

		Button btnEnviarPendientes = (Button) a.findViewById(R.id.btnEnviarPendientes);
		
		if (btnEnviarPendientes!=null)
			seteaListener(btnEnviarPendientes);
		
		creaDialogoProgreso();
	}

	public void mostrarMensajeNoHayRegistrosPendientes() 
	{
		DialogManager dialogManager = new DialogManager();
		dialogManager.muestraToastGenerico(a, "error al generar el Json", false);
	}

	public void creaDialogoProgreso() {
		
		dialog = new Dialog(a);
		dialog.setContentView(R.layout.dialog_enviar_pendientes);
		dialog.setTitle(a.getString(R.string.sincronizando));
		dialog.setCancelable(false);
		
		btnCerrarEnvioPendientes = (Button) dialog.findViewById(R.id.btnCerrarEnvioPendientes);
		txtResultadoEnvio = (TextView) dialog.findViewById(R.id.txtResultadoEnvio);
		chkEnviarPendientes = (CheckBox) dialog.findViewById(R.id.chkEnviarPendientes);
		progressBar = (ProgressBar) dialog.findViewById(R.id.progressBar1);
		imgSincronizarResultado = (ImageView) dialog.findViewById(R.id.imgSincronizarResultado);
		
		seteaListener(btnCerrarEnvioPendientes);
	}
	
	public void muestraDialogoEnviaPendientes()
	{
		seteaValorchkEnviarPendientes(false) ;
		seteaTxtResultadoEnvio("");
		seteaBtnCerrarEnvioPendientesVisible(false);

		seteaimgSincronizarResultadoVisible(false);
		seteaProgressBarVisible(true);
		
		dialog.show();
	}
	
	public void cerrarDialogoSincronizacion()
	{
		dialog.dismiss();
	}
	
	public void cerrarActivity()
	{
		a.finish();
	}
	public void seteaBtnCerrarEnvioPendientesVisible(boolean visible) 
	{
		btnCerrarEnvioPendientes.setVisibility((visible)?View.VISIBLE:View.INVISIBLE);
	}

	public void seteaProgressBarVisible(boolean visible) 
	{
		progressBar.setVisibility((visible)?View.VISIBLE:View.INVISIBLE);
	}

	public void seteaimgSincronizarResultadoVisible(boolean visible) 
	{
		imgSincronizarResultado.setVisibility((visible)?View.VISIBLE:View.INVISIBLE);
	}

	public void seteaTxtResultadoEnvio(String texto) 
	{
		txtResultadoEnvio.setText(texto);
	}

	public void seteaValorchkEnviarPendientes(boolean valor) 
	{
		chkEnviarPendientes.setChecked(valor);
	}
	
	public void seteaListener (View v)
	{
		v.setOnClickListener(listenerEnviaPendientes);
	}
}
