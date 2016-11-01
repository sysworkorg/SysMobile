package ar.com.syswork.sysmobile.psincronizar;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import ar.com.syswork.sysmobile.R;

public class PantallaManagerSincronizacion {
	
	private ActivitySincronizar a;
	private ListenerSincronizacion listenerSincronizacion;
	private Dialog dialog;

	private CheckBox chkVendedores;
	private CheckBox chkRubros;
	private CheckBox chkArticulos;
	private CheckBox chkClientes;

	private TextView txtResultadoVendedores;
	private TextView txtResultadoRubros;
	private TextView txtResultadoArticulos;
	private TextView txtResultadoClientes;

	private ProgressBar prgEstadoConexion;
	private TextView txtEstadoConexion;
	private Button btnSincronizar;
	
	private ImageView imgSincronizar;
	private ProgressBar progresBar1;
	private Button btnCerrarSincronizacion;
	
	public PantallaManagerSincronizacion(ActivitySincronizar a, ListenerSincronizacion listenerSincronizacion)
	{
		this.a = a;
		
		this.listenerSincronizacion= listenerSincronizacion;

		prgEstadoConexion= (ProgressBar) this.a.findViewById(R.id.prgbEstadoConexion);
		txtEstadoConexion = (TextView ) this.a.findViewById(R.id.txtEstadoConexion);
		btnSincronizar = (Button) a.findViewById(R.id.btnSincronizar);
		
		
		
		creaDialogoSincronizacion();
		
	}
	
	public void seteaListeners()
	{
		listenerSincronizacion.seteaListener(btnSincronizar);		
		
	}
	
	public void creaDialogoSincronizacion()
	{
		dialog = new Dialog(a);
		dialog.setContentView(R.layout.dialog_sincronizar);
		dialog.setTitle(a.getString(R.string.sincronizando));
		dialog.setCancelable(false);
		
		chkVendedores = (CheckBox) dialog.findViewById(R.id.chkVendedores);
		chkRubros = (CheckBox) dialog.findViewById(R.id.chkRubros);
		chkArticulos = (CheckBox) dialog.findViewById(R.id.chkArticulos);
		chkClientes = (CheckBox) dialog.findViewById(R.id.chkClientes);

		txtResultadoVendedores = (TextView ) dialog.findViewById(R.id.txtResultadoVendedores);
		txtResultadoRubros = (TextView ) dialog.findViewById(R.id.txtResultadoRubros);
		txtResultadoArticulos = (TextView) dialog.findViewById(R.id.txtResultadoArticulos);
		txtResultadoClientes = (TextView) dialog.findViewById(R.id.txtResultadoClientes);
		
		imgSincronizar = (ImageView ) dialog.findViewById(R.id.imgSincronizar);
		progresBar1 = (ProgressBar ) dialog.findViewById(R.id.progressBar1);
		
		btnCerrarSincronizacion = (Button) dialog.findViewById(R.id.btnCerrarSincronizacion);
		
		listenerSincronizacion.seteaListener(btnCerrarSincronizacion);
		
	}
	
	public void muestraDialogoSincronizacion()
	{
		seteaValorChkVendedores(false) ;
		seteaValorChkRubros(false);
		seteaValorChkArticulos(false) ;
		seteaValorChkClientes(false) ;

		seteatxtResultadoVendedores("");
		seteatxtResultadoRubros("");
		seteatxtResultadoClientes("");
		seteaTxtResultadoArticulos("");
		seteaImgSincronizarVisible(false);
		seteaProgressBarVisible(true);
		setVisibleBtnCerrarSincronizacion(false);
		dialog.show();
	}
	
	public void setVisibleBtnCerrarSincronizacion(boolean visible)
	{
		btnCerrarSincronizacion.setVisibility((visible)?View.VISIBLE:View.INVISIBLE);
	}

	public void finalizarActivitySinErrores()
	{
		a.setResult(Activity.RESULT_OK);
		a.finish();
	}
	
	public void cierraDialogoSincronizacion()
	{
		dialog.dismiss();
	}	
	public void seteaValorChkVendedores(boolean valor)
	{
		chkVendedores.setChecked(valor);
	}
	public void seteaValorChkRubros(boolean valor)
	{
		chkRubros.setChecked(valor);
	}
	public void seteaValorChkArticulos(boolean valor)
	{
		chkArticulos.setChecked(valor);
	}
	public void seteaValorChkClientes(boolean valor)
	{
		chkClientes.setChecked(valor);
	}
	public void seteaTxtResultadoArticulos(String valor)
	{
		txtResultadoArticulos.setText(valor);
	}
	public void seteatxtResultadoVendedores(String valor)
	{
		txtResultadoVendedores.setText(valor);
	}
	public void seteatxtResultadoRubros(String valor)
	{
		txtResultadoRubros.setText(valor);
	}
	public void seteatxtResultadoClientes(String valor)
	{
		txtResultadoClientes.setText(valor);
	}
	
	public void seteaImgSincronizarVisible(boolean visible)
	{
		imgSincronizar.setVisibility((visible)?View.VISIBLE:View.INVISIBLE);
	}
	public void seteaProgressBarVisible(boolean visible)
	{
		progresBar1.setVisibility((visible)?View.VISIBLE:View.INVISIBLE);
	}

	public void finalizarActivityConErrores() {
		a.setResult(Activity.RESULT_CANCELED);
		a.finish();
	}

	public void seteaBotonSincronizarVisible(boolean visible) {
		btnSincronizar.setVisibility((visible)?View.VISIBLE:View.INVISIBLE);
		
	}

	public void seteaPrgEstadoConexionVisible(boolean visible) {
		prgEstadoConexion.setVisibility((visible)?View.VISIBLE:View.INVISIBLE);
		
	}

	public void seteaTxtEstadoConexionVisible(boolean visible) {
		txtEstadoConexion.setVisibility((visible)?View.VISIBLE:View.INVISIBLE);
		
	}

	public void seteaTxtEstadoConexion(String strEstadoConexion) {
		txtEstadoConexion.setText(strEstadoConexion);
		
	}
	
}
