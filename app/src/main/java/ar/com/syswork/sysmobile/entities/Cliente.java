package ar.com.syswork.sysmobile.entities;

public class Cliente {
    
	private String codigo ;
	private String codigoOpcional;
	private String razonSocial;
	private String calleNroPisoDpto ;
	private String localidad ;
	private String cuit ;
	private byte iva ;
	private byte claseDePrecio;
	private double porcDto;
	private String cpteDefault;
	private String idVendedor;
	private String telefono;
	private String mail;
	
	public String getCodigo() 
	{
		return codigo;
	}
	
	public void setCodigo(String codigo) 
	{
		this.codigo = codigo;
	}
	
	public String getCodigoOpcional() 
	{
		return codigoOpcional;
	}
	
	public void setCodigoOpcional(String codigoOpcional) 
	{
		this.codigoOpcional = codigoOpcional;
	}
	
	public String getRazonSocial() 
	{
		return razonSocial;
	}
	
	public void setRazonSocial(String razonSocial) 
	{
		this.razonSocial = razonSocial;
	}
	
	public String getCalleNroPisoDpto() 
	{
		return calleNroPisoDpto;
	}
	
	public void setCalleNroPisoDpto(String calleNroPisoDpto) 
	{
		this.calleNroPisoDpto = calleNroPisoDpto;
	}
	
	public String getLocalidad() 
	{
		return localidad;
	}
	
	public void setLocalidad(String localidad) 
	{
		this.localidad = localidad;
	}
	
	public String getCuit() 
	{
		return cuit;
	}
	
	public void setCuit(String cuit) 
	{
		this.cuit = cuit;
	}
	
	public byte getIva() 
	{
		return iva;
	}
	
	public void setIva(byte iva) 
	{
		this.iva = iva;
	}
	
	public double getPorcDto() 
	{
		return porcDto;
	}
	
	public void setPorcDto(double porcDto) 
	{
		this.porcDto = porcDto;
	}
	
	public byte getClaseDePrecio() 
	{
		return claseDePrecio;
	}
	
	public void setClaseDePrecio(byte claseDePrecio) 
	{
		this.claseDePrecio = claseDePrecio;
	}
	
	public String getCpteDefault() 
	{
		return cpteDefault;
	}
	
	public void setCpteDefault(String cpteDefault) 
	{
		this.cpteDefault = cpteDefault;
	}
	
	public String getIdVendedor() 
	{
		return idVendedor;
	}
	
	public void setIdVendedor(String idVendedor) 
	{
		this.idVendedor = idVendedor;
	}
	
	public String getTelefono() 
	{
		return telefono;
	}
	
	public void setTelefono(String telefono) 
	{
		this.telefono = telefono;
	}
	
	public String getMail() 
	{
		return mail;
	}
	
	public void setMail(String mail) 
	{
		this.mail = mail;
	}

	
}
