package ar.com.syswork.sysmobile.entities;

public class ItemCtaCte {
	
	private String fecha;
	private String concepto;
	private String tc;
	private String sucNroLetra;
	private double importe;
	
	public String getFecha() 
	{
		return fecha;
	}
	
	public void setFecha(String fecha) 
	{
		this.fecha = fecha;
	}
	
	public String getConcepto() 
	{
		return concepto;
	}
	
	public void setConcepto(String concepto) 
	{
		this.concepto = concepto;
	}
	
	public String getTc() 
	{
		return tc;
	}
	
	public void setTc(String tc) 
	{
		this.tc = tc;
	}
	
	public String getSucNroLetra() 
	{
		return sucNroLetra;
	}
	
	public void setSucNroLetra(String sucNroLetra) 
	{
		this.sucNroLetra = sucNroLetra;
	}
	
	public double getImporte() 
	{
		return importe;
	}
	
	public void setImporte(double importe) 
	{
		this.importe = importe;
	}
	
}
