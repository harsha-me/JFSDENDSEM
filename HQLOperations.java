
package HQLDemo;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;

public class HQLOperations
{
 public static void main(String args[])
	{
		HQLOperations operations=new HQLOperations();
		//operations.addProduct();
		//operations.displayallproductscompleteobject();
		//operations.displayallproductspartialobject();
		//operations.aggregatefunctions();
		//operations.updatepositionalparams();
		//operations.updatenamedparams();
		//operations.deletepositionalparams();
		//operations.deletenamedparams();
		//operations.displayproductbyidpositionalparams();
		//operations.dispalyproductbyidnamedparams();
		//operations.displayproducts();
		operations.paginationdemo();
		
	}
 //adding product using persistent object
 public void  addProduct() {
	 Configuration configuration=new Configuration();
	 configuration.configure("hibernate.cfg.xml");
	 
	 SessionFactory sf=configuration.buildSessionFactory();
	 Session session=sf.openSession(); //create connection
	 
	 Transaction t=session.beginTransaction();
	 
	 Product product=new Product();
	 product.setId(6);
	 product.setCategory("GROCERIES");
	 product.setName("RICE");
	 product.setCost(65);
	 product.setStock(30);
	 //please set stock status based on stock value
	 product.setStatus(true);
	
	 
	 session.persist(product);
	 t.commit();
	 System.out.print("Product Added Successfully");
	 sf.close();
	 session.close();
 }
 public void displayallproductscompleteobject() //complete object
 {
	 Configuration configuration=new Configuration();
	 configuration.configure("hibernate.cfg.xml");
	 
	 SessionFactory sf=configuration.buildSessionFactory();
	 Session session=sf.openSession(); //create connection
	 
	 String hql="from Product"; //select * from product_table
	Query<Product> qry=session.createQuery(hql, Product.class);
	 List<Product>productlist=qry.getResultList();
	 System.out.println("Total Products="+productlist.size());
	 
	 for(Product p:productlist) {
		 System.out.println("ID:"+p.getId());
		 System.out.println("Category:"+p.getCategory());
		 System.out.println("Name:"+p.getName());
		 System.out.println("Cost:"+p.getCost());
		 System.out.println("Quantity:"+p.getStock());
		 System.out.println("Status:"+p.isStatus());
	 }
	 session.close();
	 sf.close(); 
 }
 public void displayallproductspartialobject()
 {
	 Configuration configuration=new Configuration();
	 configuration.configure("hibernate.cfg.xml");
	 
	 SessionFactory sf=configuration.buildSessionFactory();
	 Session session=sf.openSession(); //create connection
	 
	 String hql = "select p.id,p.name,p.cost from Product p";
	 //p is a reference object or we simply call it as alias
	 
	 Query<Object[]> qry=session.createQuery(hql, Object[].class);
	 List<Object[]>productlist=qry.getResultList();
	 System.out.println("Total Products="+productlist.size());
	 
	 
	 for(Object[] obj:productlist) {
		 System.out.println("Product ID:"+obj[0]);
		 System.out.println("Product Name:"+obj[1]);
		 System.out.println("Product Cost:"+obj[2]);
		  
	 }
	 session.close();
	 sf.close();
	 
 }
 public void aggregatefunctions()
 {
	 Configuration configuration=new Configuration();
	 configuration.configure("hibernate.cfg.xml");
	 
	 SessionFactory sf=configuration.buildSessionFactory();
	 Session session=sf.openSession();
	 
	 String hql1="select count(*) from Product";
	 //you can also use count(property) 
	 //count(*) counts the null value but not the same for count(property)
	 Query<Long> qry1=session.createQuery(hql1,Long.class);
	 Long count=qry1.getSingleResult();
	 System.out.println("Total Products="+count);
	 
	 String hql2="select sum(cost) from Product";
	 Query<Double> qry2=session.createQuery(hql2,Double.class);
	 Double totalcost=qry2.getSingleResult();
	 System.out.println("Total Cost="+totalcost);
	 
	 String hql3="select avg(cost) from Product";
	 Query<Double> qry3=session.createQuery(hql3,Double.class);
	 Double avgcost=qry3.getSingleResult();
	 System.out.println("Average Cost="+avgcost);
	 
	 String hql4="select min(stock) from Product";
	 Query<Integer> qry4=session.createQuery(hql4,Integer.class);
	 Integer minstock=qry4.getSingleResult();
	 System.out.println("Minimum stock="+minstock);
	 
	 String hql5="select max(stock) from Product";
	 Query<Integer> qry5=session.createQuery(hql5,Integer.class);
	 Integer maxstock=qry5.getSingleResult();
	 System.out.println("Maximum stock="+maxstock);
	 
	 session.close();
	 sf.close();
 }
 public void updatepositionalparams() 
 {
	 Configuration configuration=new Configuration();
	 configuration.configure("hibernate.cfg.xml");
	 
	 SessionFactory sf=configuration.buildSessionFactory();
	 Session session=sf.openSession(); 
	 
	 Transaction t=session.beginTransaction();
	 
	 Scanner sc =new Scanner(System.in);
	 System.out.println("Enter Product Id:");
	 int pid=sc.nextInt();
	 System.out.println("Enter Product Name:");
	 String pname=sc.next();
	 System.out.println("Enter Product Cost:");
	 double pcost=sc.nextDouble();
	 
	 String hql="update Product set name=?1,cost=?2 where id=?3";
	 MutationQuery qry=session.createMutationQuery(hql);
	 qry.setParameter(1, pname);
	 qry.setParameter(2, pcost);
	 qry.setParameter(3, pid);
	 int n=qry.executeUpdate();
	 
	 t.commit();
	 System.out.println(n+" Product(s) Updated Successfully");
	
	 session.close();
	 sf.close();
	 
 }
public void deletepositionalparams() 
{
	Configuration configuration=new Configuration();
	 configuration.configure("hibernate.cfg.xml");
	 
	 SessionFactory sf=configuration.buildSessionFactory();
	 Session session=sf.openSession(); 
	 
	 Transaction t=session.beginTransaction();
	 
	 Scanner sc =new Scanner(System.in);
	 System.out.println("Enter Product Id:");
	 int pid=sc.nextInt();
	 
	 
	 String hql="delete from Product where id=?1";
	 MutationQuery qry=session.createMutationQuery(hql);
	 qry.setParameter(1, pid);
	 int n=qry.executeUpdate();
	 
	 t.commit();
	 if(n>0)
		 System.out.println("Product Deleted Successfully");
	 else
		 System.out.println("Product ID Not Found");
	
	 session.close();
	 sf.close();
}
public void updatenamedparams()
{
	Configuration configuration=new Configuration();
	 configuration.configure("hibernate.cfg.xml");
	 
	 SessionFactory sf=configuration.buildSessionFactory();
	 Session session=sf.openSession(); 
	 
	 Transaction t=session.beginTransaction();
	 
	 Scanner sc =new Scanner(System.in);
	 System.out.println("Enter Product Id:");
	 int pid=sc.nextInt();
	 System.out.println("Enter Product Name:");
	 String pname=sc.next();
	 System.out.println("Enter Product Cost:");
	 double pcost=sc.nextDouble();
	 
	 String hql="update Product set name=:v1,cost=:v2 where id=:v3";
	 MutationQuery qry=session.createMutationQuery(hql);
	 qry.setParameter("v1", pname);
	 qry.setParameter("v2", pcost);
	 qry.setParameter("v3", pid);
	 int n=qry.executeUpdate();
	 
	 t.commit();
	 System.out.println(n+" Product(s) Updated Successfully");
	
	 session.close();
	 sf.close();
}
public void deletenamedparams() 
{
	Configuration configuration=new Configuration();
	 configuration.configure("hibernate.cfg.xml");
	 
	 SessionFactory sf=configuration.buildSessionFactory();
	 Session session=sf.openSession(); 
	 
	 Transaction t=session.beginTransaction();
	 
	 Scanner sc =new Scanner(System.in);
	 System.out.println("Enter Product Id:");
	 int pid=sc.nextInt();
	 
	 
	 String hql="delete from Product where id=:v";
	 MutationQuery qry=session.createMutationQuery(hql);
	 qry.setParameter("v", pid);
	 int n=qry.executeUpdate();
	 
	 t.commit();
	 if(n>0)
		 System.out.println("Product Deleted Successfully");
	 else
		 System.out.println("Product ID Not Found");
	
	 session.close();
	 sf.close();
}
//display product by id using positional params
public void displayproductbyidpositionalparams() 
{
	Configuration configuration=new Configuration();
	 configuration.configure("hibernate.cfg.xml");
	 
	 SessionFactory sf=configuration.buildSessionFactory();
	 Session session=sf.openSession(); 
	 
	 Scanner sc=new Scanner(System.in);
	 System.out.println("Enter Product ID:");
	 int pid=sc.nextInt();
	 String hql="from Product where id=?1";
	 
	Query<Product> qry=session.createQuery(hql, Product.class);
	qry.setParameter(1, pid);
	Product p=qry.getSingleResultOrNull();
	if(p!=null) {
		//we can use getter methods to print every property of Product object(p)
		System.out.println(p.toString());
		
	}
	else{
		System.out.println("Product ID Not Found");
	}
	session.close();
	sf.close();
}
public void dispalyproductbyidnamedparams()
{
	Configuration configuration=new Configuration();
	 configuration.configure("hibernate.cfg.xml");
	 
	 SessionFactory sf=configuration.buildSessionFactory();
	 Session session=sf.openSession(); 
	 
	 Scanner sc=new Scanner(System.in);
	 System.out.println("Enter Product ID:");
	 int pid=sc.nextInt();
	 String hql="from Product where id=:v";
	 
	Query<Product> qry=session.createQuery(hql, Product.class);
	qry.setParameter("v", pid);
	Product p=qry.getSingleResultOrNull();
	if(p!=null) {
		//we can use getter methods to print every property of Product object(p)
		System.out.println(p.toString());
		
	}
	else{
		System.out.println("Product ID Not Found");
	}
	session.close();
	sf.close();
}
//display products based on category and stock
public void displayproducts() 
{
	Configuration configuration=new Configuration();
	 configuration.configure("hibernate.cfg.xml");
	 
	 SessionFactory sf=configuration.buildSessionFactory();
	 Session session=sf.openSession(); 
	 
	 String hql="from Product where category=?1 and stock>=?2";
	 Scanner sc=new Scanner(System.in);
	 System.out.println("Enter Product Category:");
	 String pcategory=sc.next();
	 System.out.println("Enter Product Stock:");
	 int pstock=sc.nextInt();
	 
	 
	Query<Product> qry=session.createQuery(hql, Product.class);
	qry.setParameter(1,pcategory);
	qry.setParameter(2,pstock);
	 List<Product>productlist=qry.getResultList();
	 System.out.println("Total Products="+productlist.size());
	 
	 for(Product p:productlist) {
		 System.out.println("ID:"+p.getId());
		 System.out.println("Category:"+p.getCategory());
		 System.out.println("Name:"+p.getName());
		 System.out.println("Cost:"+p.getCost());
		 System.out.println("Quantity:"+p.getStock());
		 System.out.println("Status:"+p.isStatus());
	 }
	 session.close();
	 sf.close();
}
//pagination
public void paginationdemo()
{
Configuration configuration = new Configuration();
  configuration.configure("hibernate.cfg.xml");
    
    SessionFactory sf = configuration.buildSessionFactory();
    Session session = sf.openSession();
    
    String hql = "from Product"; // select * from product_table
    
    Query<Product> qry = session.createQuery(hql, Product.class);
    qry.setFirstResult(1);
    qry.setMaxResults(3);
    List<Product> productlist =  qry.getResultList();
    
    System.out.println("Total Products="+productlist.size());
    
     for( Product p : productlist) 
     {
       System.out.println(p.toString());
     }
     
     session.close();
     sf.close();
}
}
