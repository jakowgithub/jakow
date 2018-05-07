package Depo;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.j256.ormlite.*;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.*;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.jdbc.*;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.*;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseResults;
import com.j256.ormlite.support.*;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.DatabaseTable.*;
import com.j256.ormlite.table.TableUtils;
import com.j256.ormlite.table.TableUtils.*;
import com.j256.ormlite.table.*;
import com.j256.ormlite.support.DatabaseConnection.*;
import com.j256.ormlite.table.DatabaseTableConfig.*;

import java.io.File;
import java.lang.annotation.*;
import java.lang.reflect.*;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.lang.annotation.ElementType;

public class MetroDAO {
	static ConnectionSource connectionSource;

	private static final String login = "user1";
	private static final String password = "pass1";
	
	static String databaseURL = "jdbc:mysql://localhost/metro?" + // classicmodels 
	                            "user=" + login + 
			                     "&password=" + password;
	
	static Dao <Vagon, Integer> vagonDao;
	static Dao <Mashinist, String> mashinistDao;
	static Dao <Potyag, Integer> potyagDao;
	static Dao <Depo, Integer> depoDao;
	static Dao <Pasagir, Integer> pasagirDao; 
	static Dao <?, ?> dao;
	
	static CloseableIterator<String[]> closeableIterator;
	
// method povertae HashSet<>() klasiv z annotation @DatabaseTable 
public static <T> Set<Class<?>> getClassesInPackageWithAnnot (String packageName) {
// https://github.com/ronmamo/reflections
Set<Class<?>> classes = new HashSet<>();
String packageNameSlashed = packageName.replace(".", "/");

URL directoryURL = Thread.currentThread().getContextClassLoader().getResource(packageNameSlashed);
if (directoryURL == null) {System.out.println("Could not retrieve URL resource: " + packageNameSlashed);
	return classes;
}
String directoryString = directoryURL.getFile();
if (directoryString == null) {System.out.println("Could not find directory for URL resource: " + packageNameSlashed);
	return classes;
}
File directory = new File(directoryString);
if (directory.exists()) {
// Get the list of the files contained in the package
	String[] files = directory.list();
for (String fileName : files) {
// We are only interested in .class files
	if (fileName.endsWith(".class")) {
// Remove the .class extension
	fileName = fileName.substring(0, fileName.length() - 6);
		
try {classes.add(Class.forName(packageName + "." + fileName));}
catch (ClassNotFoundException e) {System.out.println(packageName + "." + fileName + " does not appear to be a valid class.");}
}}} 
else {System.out.println(packageName + " does not appear to exist as a valid package on the file system.");}
//kinez https://github.com/ronmamo/reflections

Set<Class<?>> clsHS = new HashSet<>();
for (Class<?> clas : classes) {
	if (!clas.isInterface()  && 
		!clas.isAnnotation() && 
		!clas.isEnum()       &&
		(clas.getAnnotations().length>0)) 
		
		 clsHS.add(clas);			  
}
	return clsHS;
}
//method povertae HashSet DAO ta stvorue tablizi
//na vxid HashSet klasiv z annotation
public static <T> Set<Dao<?, ?>>  getDAOs (Set<Class<?>> classes, ConnectionSource connectionSource ){
	Set<Dao<?, ?>> daoes = new HashSet<>();
	for (Class<?> clas : classes) {		
		int t1=0;
	for(Field fld:clas.getDeclaredFields()) {
		System.out.println(t1+" t1 "+fld); 
		int t2=0;
	for(Annotation annt2: fld.getDeclaredAnnotations()) {
	    System.out.println(t2+" t2 "+clas +"|"+fld+"|"+annt2);
	    if ((annt2.toString().indexOf("generatedId=true")>=0) ||
	    	(annt2.toString().indexOf("id=true")>=0)){
	    	
	    	try {  
//switch(getDao(clas).getTableName()) {
//case "vagon"     : dao=(Dao <Vagon, Integer>) DaoManager.createDao(connectionSource, clas); break;
//case "potyag"    : dao=(Dao <Potyag, Integer>) DaoManager.createDao(connectionSource, clas); break;
//case "pasagir"   : dao=(Dao <Pasagir, Integer>) DaoManager.createDao(connectionSource, clas); break;
//case "depo"      : dao=(Dao <Depo, Integer>) DaoManager.createDao(connectionSource, clas); break;
//case "mashinist" : dao=(Dao <Mashinist, String>) DaoManager.createDao(connectionSource, clas); break;
//			    	}  		
	    		 daoes.add(getDao(clas)) ;
	    	     TableUtils.dropTable(connectionSource, clas, true);
	             TableUtils.createTable(connectionSource, clas);
}
	        catch (SQLException e) {e.printStackTrace();}
	    	//catch (ClassNotFoundException e) {e.printStackTrace();}
	    }
		t2++;
}	
		t1++;		
}
}	 
return daoes;
}
/**   https://github.com/j256/ormlite-examples/blob/master/android/
/            HelloAndroidNoHelper/src/com/example/hellonohelper/
/            DatabaseHelper.java
*/            
public static  <D extends Dao<T, ?>, T> D getDao(Class<T> cls) throws SQLException {
    // lookup the dao, possibly invoking the cached database config
	Dao<T, ?> dao = DaoManager.lookupDao(connectionSource, cls);
    if (dao == null) {
     // try to use our new reflection magic
        DatabaseTableConfig<T> tableConfig = DatabaseTableConfig.fromClass(connectionSource, cls);
        if (tableConfig == null) dao = (Dao<T, ?>) DaoManager.createDao(connectionSource, cls);
        else                     dao = (Dao<T, ?>) DaoManager.createDao(connectionSource, tableConfig);
    }
    @SuppressWarnings("unchecked")
    D castDao = (D) dao;
    
    return castDao;
}
//method pishe v tablizu polya objecta (yaki annotirovani v ORM-lite)
//na vxid HashSet DAO (vsix klasiv paketa)ta Object
public void zapisDAO (Set < Dao<?, ?>> daos, Object ob) {
	
try {
switch(ob.getClass().getSimpleName()) {

case "Vagon"      : for (Dao<?, ?> dao : daos) {     
	                if (dao.getTableName().equalsIgnoreCase("Vagon")) {
	                   Dao <Vagon, Integer> vDAO = (Dao <Vagon, Integer>) dao;
		               Vagon v = (Vagon)(ob);
		               vDAO.create(v);
		               break;}} break;
case "Mashinist" : for (Dao<?, ?> dao : daos) {     
	               if (dao.getTableName().equalsIgnoreCase("Mashinist")) {
	                  Dao <Mashinist, String> mDAO = (Dao <Mashinist, String>) dao;
                      Mashinist m = (Mashinist)(ob);
                      mDAO.create(m);
                      break;}} break;
case "Potyag" : for (Dao<?, ?> dao : daos) {     
                if (dao.getTableName().equalsIgnoreCase("Potyag")) {
                   Dao <Potyag, Integer> pDAO = (Dao <Potyag, Integer>) dao;
                   Potyag p = (Potyag)(ob);
                   pDAO.create(p);
                   break;}} break;
case "Depo" : for (Dao<?, ?> dao : daos) {     
              if (dao.getTableName().equalsIgnoreCase("Depo")) {
                  Dao <Depo, Integer> dDAO = (Dao <Depo, Integer>) dao;
                  Depo d = (Depo)(ob);
                  dDAO.create(d);
                  break;}} break;
case "Pasagir" :for (Dao<?, ?> dao : daos) {
	            if (dao.getTableName().equalsIgnoreCase("Pasagir")) {
                   Dao <Pasagir, Integer> pasDAO = (Dao <Pasagir, Integer>) dao;
                   Pasagir pas = (Pasagir)(ob);
                   pasDAO.create(pas);
                   break;}} break;		
	}
}
catch (SQLException e) {e.printStackTrace();} 
}

public static void main(String[] args) {
		
	Set<Class<?>> classes = getClassesInPackageWithAnnot("Depo");
	
try {connectionSource = new JdbcConnectionSource(databaseURL);

Set < Dao<?, ?>> daos=getDAOs (getClassesInPackageWithAnnot("Depo"), connectionSource);
  
	 
     vagonDao = DaoManager.createDao(connectionSource, Vagon.class);	
	 mashinistDao = DaoManager.createDao(connectionSource, Mashinist.class);
	 potyagDao = DaoManager.createDao(connectionSource, Potyag.class);
	 depoDao = DaoManager.createDao(connectionSource, Depo.class);
	 pasagirDao = DaoManager.createDao(connectionSource, Pasagir.class);
	 
//vidalennya tabliz 
	 TableUtils.dropTable(connectionSource, Vagon.class,true);
	 TableUtils.dropTable(connectionSource, Mashinist.class,true);
	 TableUtils.dropTable(connectionSource, Potyag.class,true);
	 TableUtils.dropTable(connectionSource, Depo.class,true);
	 TableUtils.dropTable(connectionSource, Pasagir.class,true);
//stvorennya tabliz 
	 TableUtils.createTable(connectionSource, Vagon.class);
	 TableUtils.createTable(connectionSource, Mashinist.class);
	 TableUtils.createTable(connectionSource, Potyag.class);
	 TableUtils.createTable(connectionSource, Depo.class);
	 TableUtils.createTable(connectionSource, Pasagir.class);
//2.Stvoruu Depo -stvoruu vagoni 6-VagonPas, 4-VagonMach.
	 Depo depo1=new Depo (27,18, NazvaDepo.DepoDarniza);
//Stvoruu tablizu vagoniv			
	 depo1.getDepo().forEach(ob->{if (ob instanceof Vagon){Vagon v=(Vagon)ob;
				
	 try { vagonDao.createIfNotExists(v);}
	 catch (SQLException e) {e.printStackTrace();}
				}});
//Stvoruu potygi z vagoni ne depo				
	 Potyag potyag1= new Potyag(1,NazvaDepo.DepoDarniza); 
	
	 // vagonDao.create(potyag1.getPotyg());
	 Potyag potyag2= new Potyag(2,NazvaDepo.DepoGeroivDnipra); 
	// vagonDao.create(potyag2.getPotyg());
	 Potyag potyag3= new Potyag(3,NazvaDepo.DepoDarniza);
	 //vagonDao.create(potyag3.getPotyg());
//priznachiv Mashinistiv
	 potyag1.setMashinist(new Mashinist("Ivanenko", "1111111111"));
	 mashinistDao.createIfNotExists(potyag1.getMashinist());
	 potyag2.setMashinist(new Mashinist("Petrenko", "2222222222"));
	 mashinistDao.create(potyag2.getMashinist());
	 potyag3.setMashinist(new Mashinist("Kozak", "4444444444"));
	 mashinistDao.create(potyag3.getMashinist());
	 Mashinist xXX=new Mashinist("XXX", "5555555555");
	 mashinistDao.create(xXX);
//Dodau potyagi v depo
	 depo1.dodatVdepo(potyag1);
	 depo1.dodatVdepo(potyag2);
	 depo1.dodatVdepo(potyag3);
//Stvoruu potygi z vagoni depo
	 Potyag potyag4= new Potyag(depo1);
	 potyag4.setMashinist(new Mashinist("Sidorenko", "3333333333"));
	 mashinistDao.create(potyag4.getMashinist());
	 //vagonDao.delete(potyag4.getPotyg());
	 //vagonDao.create(potyag4.getPotyg());
//Dodau potyag v depo
	 depo1.dodatVdepo(potyag4);
//stvoruu pasagiriv
/**
	 List <Pasagir> pasList = new ArrayList<Pasagir> ();
	 for (int i=0; i<1100; i++){
	 Pasagir pas = new Pasagir ("PIB "+i, "IPN "+i, RandomDosvid.randomDosvid());
	 pasList.add(pas);
}
	 
//stvoruu tablizu pasagiriv
	 pasagirDao.create(pasList);
*/
	//stvoruu tablizu potyagiv
	potyagDao.create(potyag1);
	potyagDao.create(potyag2);
	potyagDao.create(potyag3);
	potyagDao.create(potyag4);
	//stvoruu tablizu depo
	depoDao.create(depo1);
	 
/**	 for (int i=1; i<16;i++){ Vagon v = vagonDao.queryForId(i);
	 if (v!=null)
		 System.out.println(v.getNomerVagona()+" "+
	                        v.getNazvaVagona()+" "+
				            v.getNomerPotyagaKey()+" "+
	                        v.getNazvaDepoKey()+" "+
				            v.hashCode());
	 }
	 for (int j=9000; j<9050;j++){ Vagon vT = vagonDao.queryForId(j);
	 if (vT!=null)
		 System.out.println(vT.getNomerVagona()+" "+
	                        vT.getNazvaVagona()+" "+
				            vT.getNomerPotyagaKey()+" "+
	                        vT.getNazvaDepoKey()+" "+
				            vT.hashCode());
	 }
*/
	 System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
	 List<Vagon> vagonList = new ArrayList<>();
	 QueryBuilder<Vagon, Integer> vagonQb = vagonDao.queryBuilder();
	 vagonList=vagonQb.query();
	 vagonList.forEach(v-> System.out.println(v.toString()));

}	
catch (SQLException e) {e.printStackTrace();} 
			//catch (IOException e) {e.printStackTrace();}
	}
		private static void printResults(DatabaseResults resultSet) throws SQLException {
			String[] columns = resultSet.getColumnNames();
		// String[] types = getColumnTypes(resultSet);
		   String[] types = { "VARCHAR", "INT", "DATE", "VARCHAR" };
			int cnt = 0;
		for (int i = 0; i < columns.length; i++) {
			System.out.print(columns[i] + " | ");
			cnt += columns[i].length();
		}
		System.out.println("");
		for (int i = 0; i < cnt + 3 * columns.length - 1; i++) System.out.print("-");
		System.out.println("");

		while (resultSet.next()) {
			for (int i = 0; i < columns.length; i++) {System.out.print(getData(resultSet, columns[i], types[i]) + " | ");
		}
		System.out.println("");
		}
		}

		private static String getData(DatabaseResults resultSet, String columnName, String columnType) {
			String res;
		try {switch (columnType) {
			case "INT":
			case "BIGINT":res = "" + resultSet.getInt(resultSet.findColumn(columnName));break;
			case "DATE":res = "" + resultSet.getTimestamp(resultSet.findColumn(columnName));break;
			case "CHAR":
			case "VARCHAR":res = "" + resultSet.getString(resultSet.findColumn(columnName));break;
			case "FLOAT":res = "" + resultSet.getFloat(resultSet.findColumn(columnName));break;
			case "DOUBLE":res = "" + resultSet.getDouble(resultSet.findColumn(columnName));break;
			case "BOOLEAN":res = "" + resultSet.getBoolean(resultSet.findColumn(columnName));break;
			default:res = "";
					}
			return res;
			} 
		catch (SQLException e) {e.printStackTrace();return "";}	
}
}

