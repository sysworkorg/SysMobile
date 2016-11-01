package ar.com.syswork.sysmobile.pconsultactacte;

import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.util.DialogManager;

public class PantallaManagerConsultaCtaCte {

	private Activity a;
	private Dialog dialog;
	private Dialog dialogFechaDesdeHasta;
	private DialogManager utlDlg;
	
	private DatePicker dtpFechaDesde;
	private DatePicker dtpFechaHasta;
	private Button btnAceptarFechas;
	
	private ListenerConsultaCtaCte listenerConsultaCtaCte;
	
	public PantallaManagerConsultaCtaCte(Activity a, ListenerConsultaCtaCte listenerConsultaCtaCte)
	{
		this.a = a;
		this.listenerConsultaCtaCte = listenerConsultaCtaCte;
		
		creaDialogoConsultaCtaCte();
		crearDialogoConsultaFechaDesdeHasta();
		
		utlDlg = new DialogManager();
	}
	
	public void seteaNombreCliente(String nombre)
	{
		TextView tv = (TextView) a.findViewById(R.id.txtRazonSocialCliente);
		tv.setText(nombre);
	}
	
	
	public void creaDialogoConsultaCtaCte()
	{
		dialog = new Dialog(a);
		dialog.setContentView(R.layout.dialog_consulta_cta_cte);
		dialog.setTitle(a.getString(R.string.sincronizando));
		dialog.setCancelable(false);
	}
	public void muestraDialogoConsultCtaCte()
	{
		dialog.show();
	}
	public void cierraDialogoConsultCtaCte()
	{
		dialog.dismiss();
	}

	public void crearDialogoConsultaFechaDesdeHasta()
	{
		dialogFechaDesdeHasta= new Dialog(a);
		dialogFechaDesdeHasta.setContentView(R.layout.dialog_consulta_fechadesdehasta);
		dialogFechaDesdeHasta.setCancelable(true);
		
		dtpFechaDesde = (DatePicker) dialogFechaDesdeHasta.findViewById(R.id.dtpFechaDesde);
		dtpFechaHasta = (DatePicker) dialogFechaDesdeHasta.findViewById(R.id.dtpFechaHasta);
		
		btnAceptarFechas = (Button) dialogFechaDesdeHasta.findViewById(R.id.btnAceptarFechaDdeHta);
		listenerConsultaCtaCte.seteaListener(btnAceptarFechas);
		
	}	
	
	public void muestraDialogoConsultaFechaDesdeHasta()
	{
		Calendar cal = Calendar.getInstance();
		dtpFechaDesde.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null);
		dtpFechaHasta.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null);
		
		dialogFechaDesdeHasta.show();
	}
	public void cierraDialogoConsultaFechaDesdeHasta()
	{
		dialogFechaDesdeHasta.dismiss();
	}
	
	
	
	public void muestraToastErrorDeConexion()
	{
		utlDlg.muestraToastGenerico(this.a, a.getString(R.string.huboErrorDeComunicaciones), false);
	}
	
	public void muestraToastErrorDesdeMayorQueHasta()
	{
		utlDlg.muestraToastGenerico(this.a, a.getString(R.string.valorDesdeMayorQueHalorHasta), false);
	}

	
	public String obtieneFechaDesdeYYYYMMDD()
	{
		String tmpFecha = "";
		String valorLeido = "";
		
		valorLeido = dtpFechaDesde.getYear() + "";
		if (valorLeido.length()==2)
			valorLeido = "20" + valorLeido;
		
		tmpFecha = valorLeido;
		
		
		valorLeido = (dtpFechaDesde.getMonth() +1)+ "";
		if (valorLeido.length()==1)
			valorLeido = "0" + valorLeido;
		tmpFecha = tmpFecha + valorLeido;
		
		valorLeido = dtpFechaDesde.getDayOfMonth() + "";
		if (valorLeido.length()==1)
			valorLeido = "0" + valorLeido;
		tmpFecha = tmpFecha + valorLeido;

		return tmpFecha;
	}
	public String obtieneFechaHastaYYYYMMDD()
	{
		String tmpFecha = "";
		String valorLeido = "";
		
		valorLeido = dtpFechaHasta.getYear() + "";
		if (valorLeido.length()==2)
			valorLeido = "20" + valorLeido;
		
		tmpFecha = valorLeido;
		
		
		valorLeido = (dtpFechaHasta.getMonth() +1) + "";
		if (valorLeido.length()==1)
			valorLeido = "0" + valorLeido;
		tmpFecha = tmpFecha + valorLeido;
		
		valorLeido = dtpFechaHasta.getDayOfMonth() + "";
		if (valorLeido.length()==1)
			valorLeido = "0" + valorLeido;
		tmpFecha = tmpFecha + valorLeido;
		
		return tmpFecha;
	}
}
