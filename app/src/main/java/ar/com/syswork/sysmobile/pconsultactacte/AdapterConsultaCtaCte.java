package ar.com.syswork.sysmobile.pconsultactacte;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ar.com.syswork.sysmobile.R;
import ar.com.syswork.sysmobile.entities.ItemCtaCte;

public class AdapterConsultaCtaCte  extends ArrayAdapter<ItemCtaCte> {
	
	private LayoutInflater lInflater;
	
	public AdapterConsultaCtaCte(Context context, ArrayList<ItemCtaCte> listaCtaCte) {
		super(context,0, listaCtaCte);
		lInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getItemViewType(int position) {
		ItemCtaCte i = getItem(position);
		
		if (!i.getFecha().equals("")) 
			return 0;
		
		return 1;
	}
	
	@Override
	public int getViewTypeCount() {
		return 2;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = null;
		EnvoltorioAux envoltorio = null;
		ItemCtaCte itemCtaCte = getItem(position);
		
		/* SI LA FECHA VIENE EN "", QUIERE DECIR QUE ES EL SALDO ANTERIOR O EL 
		   SALDO ACTUAL, POR TANTO TENGO QUE CARGAR OTRO LAYOUT PARA EL ITEM
		*/
		
		if (convertView == null){
			if (itemCtaCte.getFecha().equals(""))
			{
				view = lInflater.inflate(R.layout.item_consulta_cta_cte_saldos,null);
			}
			else{
				view = lInflater.inflate(R.layout.item_consulta_cta_cte,null);
			}
			
			envoltorio = new EnvoltorioAux();
			envoltorio.txtDetalle = (TextView) view.findViewById(R.id.txtDescripcionCpte);
			envoltorio.txtFecha = (TextView) view.findViewById(R.id.txtFecha);
			envoltorio.txtTcSucNroLetra = (TextView) view.findViewById(R.id.txtNroCpte);
			envoltorio.txtImporte = (TextView) view.findViewById(R.id.txtImporte);
			envoltorio.imgCpte = (ImageView) view.findViewById(R.id.imagen_cpte_item);
			view.setTag(envoltorio);
			
		}
		else{
			
			view = convertView;
			envoltorio = (EnvoltorioAux) view.getTag();
			
		}
		
		
		envoltorio.txtDetalle.setText(itemCtaCte.getConcepto());
		envoltorio.txtFecha.setText(itemCtaCte.getFecha());
		envoltorio.txtTcSucNroLetra.setText(itemCtaCte.getTc() + itemCtaCte.getSucNroLetra());
		envoltorio.txtImporte.setText("$ " + itemCtaCte.getImporte());
		
		if (!itemCtaCte.getFecha().equals(""))
		{
			if (itemCtaCte.getImporte() >= 0)
				envoltorio.imgCpte.setImageResource(R.drawable.factura);
			else
				envoltorio.imgCpte.setImageResource(R.drawable.cobranza);
		}
		
		return view;
	}

	private class EnvoltorioAux {
		TextView txtDetalle;
		TextView txtFecha;
		TextView txtTcSucNroLetra;
		TextView txtImporte;
		ImageView imgCpte;
	}
}
