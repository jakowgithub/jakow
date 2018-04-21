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
import com.j256.ormlite.table.DatabaseTable.*;
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

public class ReflectDAO2 {
	static ConnectionSource connectionSource;

	private static final String login = "user1";
	private static final String password = "pass1";
	// classicmodels
	static String databaseURL = "jdbc:mysql://localhost/metro?" +  
	                            "user=" + login + 
			                    "&password=" + password;
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
//na vxid HashSet klasiv z annotation ta ConnectionSource
public static <T>  Set<Dao<?, ?>>  getDAOs (Set<Class<?>> classes, ConnectionSource connectionSource ){
	Set<Dao<?, ?>> daoes = new HashSet<>();
	for (Class<?> clas : classes) {		
	    for(Field fld:clas.getDeclaredFields()) {
	       for(Annotation annt: fld.getDeclaredAnnotations()) {
	    
	    if ((annt.toString().indexOf("generatedId=true") >= 0) ||
	    	(annt.toString().indexOf("id=true") >=0 )){

	    	//https:/github.com/j256/ormlite-examples/blob/master/android/HelloAndroidNoHelper/src/com/example/hellonohelper/DatabaseHelper.java	    	
	   try { Class<T> clasTemp = (Class<T>) clas;
	         Dao<T, ?> dao = DaoManager.lookupDao(connectionSource, clasTemp);
	         if (dao == null) {
		     // try to use our new reflection magic
		        DatabaseTableConfig<T> tableConfig = DatabaseTableConfig.fromClass(connectionSource, clasTemp);
		        if (tableConfig == null) dao = (Dao<T, ?>) DaoManager.createDao(connectionSource, clasTemp);
		        else                     dao = (Dao<T, ?>) DaoManager.createDao(connectionSource, tableConfig);
	          }
			 daoes.add(dao);
	    	 TableUtils.dropTable(connectionSource, clas, true);
	         TableUtils.createTable(connectionSource, clas);
}
	   catch (SQLException e) {e.printStackTrace();}
}}}}	 
return daoes;
}

//method ZAPISUE v tablizu polya objecta (yaki annotirovani v ORM-lite)
//na vxid HashSet DAO (vsix klasiv paketa), Object
public static <T> void  zapisDAO (Set < Dao<?, ?>> daos, Object ob) {
	Class<T> cls =  (Class<T>) ob.getClass(); 
	T tmp = (T) (ob);
try { for (Dao<?, ?> dao : daos) {
		
		if (dao.getTableName().equalsIgnoreCase(cls.getSimpleName())) {
	    	  int prapor=0;
	    	  for(Field fld:cls.getDeclaredFields()) {
	    		  if (1==prapor) break;
	    		  for(Annotation annt: fld.getDeclaredAnnotations()) {
	    		    
	    			if ((annt.toString().indexOf("generatedId=true") >= 0) ||
	    		    	(annt.toString().indexOf("id=true") >=0 )){
	    				
	    	        if (fld.getGenericType().toString().indexOf("int") >= 0) {
	 		            Dao<T, Integer> tmpDAO = (Dao<T, Integer>) dao;
		                tmpDAO.create(tmp);   
	    	         }
	 	            else {if (fld.getGenericType().toString().indexOf("String") >= 0) {
	 		                  Dao<T, String> tmpDAO = (Dao<T, String>) dao;
		                      tmpDAO.create(tmp);
	 	              }     
	 	            else { Dao<T, ?> tmpDAO = (Dao<T, ?>)dao;
	 	                   tmpDAO.create(tmp);
	 	   }}
	    	      
	    	  prapor=1;
              break;
}}}}}} 
catch (SQLException e) {e.printStackTrace();} 
}
//method VIDALYAE z tablizu polya objecta (yaki annotirovani v ORM-lite)
//na vxid HashSet DAO (vsix klasiv paketa), Object
public static <T> void  vidalitDAO (Set < Dao<?, ?>> daos, Object ob) {
	Class<T> cls = (Class<T>) ob.getClass(); 
	T tmp = (T)(ob);
	try { for (Dao<?, ?> dao : daos) {
		if (dao.getTableName().equalsIgnoreCase(cls.getSimpleName())) {
	    	  int prapor=0;
	    	  for(Field fld:cls.getDeclaredFields()) {
	    		  if (1==prapor) break;
	    		  for(Annotation annt: fld.getDeclaredAnnotations()) {
	    		    
	    			if ((annt.toString().indexOf("generatedId=true") >= 0) ||
	    		    	(annt.toString().indexOf("id=true") >=0 )){
	    	    	  
	    	        if (fld.getGenericType().toString().indexOf("int") >= 0) {
	 		            Dao<T, Integer> tmpDAO = (Dao<T, Integer>) dao;
		                tmpDAO.delete(tmp);   
	    	        }
	 	            else {if (fld.getGenericType().toString().indexOf("String") >= 0) {
	 		                  Dao<T, String> tmpDAO = (Dao<T, String>) dao;
		                      tmpDAO.delete(tmp);
	 	                   }     
	 	            else { Dao<T, ?> tmpDAO = (Dao<T, ?>)dao;
	                       tmpDAO.delete(tmp);
	 	   }}
	          prapor=1;
            break;
}}}}}} 
catch (SQLException e) {e.printStackTrace();} 
}
//WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
	public static void main(String[] args) {
try {connectionSource = new JdbcConnectionSource(databaseURL);
	 Set < Dao<?, ?>> daos = getDAOs (getClassesInPackageWithAnnot("Depo"), connectionSource);	  
	//2.Stvoruu Depo -stvoruu vagoni 6-VagonPas, 4-VagonMach.
	 Depo depo1=new Depo (27,18, NazvaDepo.DepoDarniza);
	//Stvoruu tablizu vagoniv			
	 depo1.getDepo().forEach(ob-> { if (ob instanceof Vagon){ 
					Vagon v=(Vagon)ob;
					zapisDAO(daos, v);
							}});
	//Stvoruu potygi z vagoni ne depo				
 Potyag potyag1= new Potyag(1,NazvaDepo.DepoDarniza); 
 potyag1.getPotyg().forEach(v-> zapisDAO(daos, v));
 Potyag potyag2= new Potyag(2,NazvaDepo.DepoGeroivDnipra); 
 potyag2.getPotyg().forEach(v-> zapisDAO(daos, v));
 Potyag potyag3= new Potyag(3,NazvaDepo.DepoDarniza);
 potyag3.getPotyg().forEach(v-> zapisDAO(daos, v));
 //priznachiv Mashinistiv
	potyag1.setMashinist(new Mashinist("Ivanenko", "1111111111"));
 zapisDAO(daos, potyag1.getMashinist());
 potyag2.setMashinist(new Mashinist("Petrenko", "2222222222"));
 zapisDAO(daos, potyag2.getMashinist());
 potyag3.setMashinist(new Mashinist("Kozak", "4444444444"));
 zapisDAO(daos, potyag3.getMashinist());		 
 Mashinist xXX=new Mashinist("XXX", "5555555555");
 zapisDAO(daos, xXX);
 //Dodau potyagi v depo
 depo1.dodatVdepo(potyag1);
 depo1.dodatVdepo(potyag2);
 depo1.dodatVdepo(potyag3);
//Stvoruu potygi z vagoni depo
 Potyag potyag4= new Potyag(depo1);
 potyag4.setMashinist(new Mashinist("Sidorenko", "3333333333"));
 zapisDAO(daos, potyag4.getMashinist());
 potyag4.getPotyg().forEach(v -> vidalitDAO(daos, v));
 potyag4.getPotyg().forEach(v -> zapisDAO(daos, v));
 //Dodau potyag v depo
 depo1.dodatVdepo(potyag4);
 //stvoruu pasagiriv
 List <Pasagir> pasList = new ArrayList<Pasagir> ();
 for (int i=0; i<1100; i++){
 Pasagir pas = new Pasagir ("PIB "+i, "IPN "+i, RandomDosvid.randomDosvid());
 pasList.add(pas);
 }				 
 //stvoruu tablizu pasagiriv
 pasList.forEach(pas -> zapisDAO(daos, pas));
 //stvoruu tablizu potyagiv
 zapisDAO(daos, potyag1);
 zapisDAO(daos, potyag2);
 zapisDAO(daos, potyag3);
 zapisDAO(daos, potyag4);
				
//stvoruu tablizu depo
 zapisDAO(daos, depo1);				 
 System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
 List<Vagon> vagonList = new ArrayList<>();
 
 for (Dao<?, ?> dao : daos) { 
	 if (dao.getTableName().equalsIgnoreCase("Vagon")) {
        Dao <Vagon, Integer> tmpDAO = (Dao <Vagon, Integer>) dao;
        QueryBuilder<Vagon, Integer> vagonQb = tmpDAO.queryBuilder();
        vagonList=vagonQb.query();
     break;
 }}
 vagonList.forEach(v-> System.out.println(v.toString()));	
 System.out.println("======================================================================================");
 List<Mashinist> mashinistList = new ArrayList<>();
 
 for (Dao<?, ?> dao : daos) { 
	 if (dao.getTableName().equalsIgnoreCase("Mashinist")) {
        Dao <Mashinist, String> tmpDAO = (Dao <Mashinist, String>) dao;
        QueryBuilder<Mashinist, String> mashinistQb = tmpDAO.queryBuilder();
        mashinistList=mashinistQb.query();
      break;
 }}
 mashinistList.forEach(m-> System.out.println(m.toString()));			 
}	
catch (SQLException e) {e.printStackTrace();} 			
}}
