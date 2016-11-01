package ar.com.syswork.sysmobile.pconsultactacte;

import android.view.View;
import android.view.View.OnClickListener;
import ar.com.syswork.sysmobile.R;

public class ListenerConsultaCtaCte implements OnClickListener {
	
	private LogicaConsultaCtaCte logicaConsultaCtaCte;
	
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnAceptarFechaDdeHta)
		{
			logicaConsultaCtaCte.tomarParametrosFechaDesdeHasta();
		}
	}

	public void seteaListener(View v)
	{
		v.setOnClickListener(this);
	}
	
	public void seteaLogicaConsultaCtaCte(LogicaConsultaCtaCte logicaConsultaCtaCte)
	{
		this.logicaConsultaCtaCte = logicaConsultaCtaCte;
	}
}
