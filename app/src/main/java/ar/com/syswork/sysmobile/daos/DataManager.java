package ar.com.syswork.sysmobile.daos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataManager {


	private static MyOpenHelper openHelper;
	private static SQLiteDatabase db ;
	
	private static DaoRubro daoRubro;
	private static DaoDeposito daoDeposito;
	private static DaoVendedor daoVendedor;
	private static DaoCliente daoCliente;
	private static DaoArticulo daoArticulo;
	private static DaoPedido daoPedido;
	private static DaoPedidoItem daoPedidoItem;
	
	public DataManager(Context c)
	{
	
		if (openHelper == null)
		{
			openHelper = new MyOpenHelper(c, "baseDeDatos.db3");
	        db = openHelper.getWritableDatabase();
	        
	        checkDataBaseStructure();
	        
	        setDaoRubro(new DaoRubro(db));
	        setDaoVendedor(new DaoVendedor(db));
	        setDaoCliente(new DaoCliente(db));
	        setDaoArticulo(new DaoArticulo(db));
	        setDaoPedido(new DaoPedido(db));
	        setDaoPedidoItem(new DaoPedidoItem(db));
	        setDaoDeposito(new DaoDeposito(db));
		}

	}
	
	public void checkDataBaseStructure()
	{
		String sql = "";
		
		if (!checkFieldNameExists("PEDIDOS","facturar"))
		{
			sql = "ALTER TABLE PEDIDOS ADD COLUMN facturar NUMERIC";
			db.execSQL(sql);
		}
		
		if (!checkFieldNameExists("PEDIDOS","incluirEnReparto"))
		{
			sql = "ALTER TABLE PEDIDOS ADD COLUMN incluirEnReparto NUMERIC";
			db.execSQL(sql);
		}
	}

	private boolean checkFieldNameExists(String table, String fieldName)
	{
		try
		{
			Cursor c = db.rawQuery("SELECT " + fieldName + " FROM " + table + " LIMIT 1",null);
			c.close();
		}
		catch (Exception ex)
		{
			return false;
		}
		
		return true;
	}
	
	public void close()
	{
		openHelper.close();
	}

	public DaoRubro getDaoRubro() {
		return daoRubro;
	}

	private void setDaoRubro(DaoRubro daoRubro) {
		DataManager.daoRubro = daoRubro;
	}

	public DaoVendedor getDaoVendedor() {
		return daoVendedor;
	}

	private void setDaoVendedor(DaoVendedor daoVendedor) {
		DataManager.daoVendedor = daoVendedor;
	}

	public DaoCliente getDaoCliente() {
		return DataManager.daoCliente;
	}

	private void setDaoCliente(DaoCliente daoCliente) {
		DataManager.daoCliente = daoCliente;
	}

	public DaoArticulo getDaoArticulo() {
		return daoArticulo;
	}

	private void setDaoArticulo(DaoArticulo daoArticulo) {
		DataManager.daoArticulo = daoArticulo;
	}
	
	public DaoPedido getDaoPedido() {
		return daoPedido;
	}

	private void setDaoPedido(DaoPedido daoPedido) {
		DataManager.daoPedido = daoPedido;
	}

	public DaoPedidoItem getDaoPedidoItem() {
		return daoPedidoItem;
	}

	private void setDaoPedidoItem(DaoPedidoItem daoPedidoItem) {
		DataManager.daoPedidoItem = daoPedidoItem;
	}

	public DaoDeposito getDaoDeposito() {
		return daoDeposito;
	}

	public void setDaoDeposito(DaoDeposito daoDeposito) {
		DataManager.daoDeposito = daoDeposito;
	}
	
}
