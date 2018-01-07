package querytranslatorbeta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class QueryBuilder 
{
	private String[] matchedItems;

	public QueryBuilder(String itemsString)
	{
		matchedItems = itemsString.split(" ");
		//JOptionPane.showMessageDialog(null, itemsString,"QueryBuilder Ctr",1);
		System.out.println(itemsString+"   "+matchedItems.length);
	}
	////////////////////////////////////////////////////////////////////////////////
	//Domain based Query
	public String[] constructSPARQLQuery()
	{
		String[] queries = new String[4];
		queries[0] = constructSPARQLQuery1();
		queries[1] = constructSPARQLQuery1();
		queries[2] = constructSPARQLQuery1();
		queries[3] = constructSPARQLQuery1();
		
		return queries;
	}
	////////////////////////////////////////////////////////////////////////////////
	//Domain based Query
	public String constructSPARQLQuery1()
	{
		String query = "prefix dbpedia-owl: <http://dbpedia.org/ontology/> \nselect ";
		String where = "\n where { ";
		ArrayList<String> Domains = new ArrayList<String>();
		ArrayList<String> Ranges = new ArrayList<String>();
		ArrayList<String> relation = new ArrayList<String>();

		ArrayList<String> distinictDomainRang = new ArrayList<String>();
		ArrayList<String> distinictDomainRangURI = new ArrayList<String>();

		String tempDomain="";
		String tempRange="";
		String tempRelation="";


		ConnectionMySql DBcon = new ConnectionMySql();
		DBcon.openBDConnection();
		//1 get all the class relatives based on the domain class
		for(int i=0;i<matchedItems.length;i++)
		{
			ResultSet RS = DBcon.executeStoredProc("GetDomainRangeByDomain", matchedItems[i]);
			try
			{
				//RS.first();
				while(RS.next())
				{
					tempDomain = RS.getString(1);
					tempRelation = RS.getString(2);
					tempRange = RS.getString(3);
					//JOptionPane.showMessageDialog(null, matchedItems[i]+"  "+tempDomain+"   "+tempRelation+"   "+tempRange,"QueryBuilder items",1);
					System.out.println("QueryBuilder items"+"   "+matchedItems[i]+"  "+tempDomain+"   "+tempRelation+"   "+tempRange);
					if(!( Domains.contains(tempDomain) &&  Ranges.contains(tempRange) && relation.contains(tempRelation)))
					{
						Domains.add(tempDomain);
						Ranges.add(tempRange);
						relation.add(tempRelation);
					}
					if(!distinictDomainRang.contains(tempDomain))
						distinictDomainRang.add(tempDomain);

					if(!distinictDomainRang.contains(tempRange))
						distinictDomainRang.add(tempRange);
				}
			}
			catch(SQLException ex)
			{
				JOptionPane.showMessageDialog(null, "Error in ResultSet"+ex.getMessage(),"QueryBuilder query",1);
				ex.printStackTrace();
			}
		}

		//2 connect to the DB to get the URI for the classes and relatives

		//3 construct the query
		for(int i=0;i<distinictDomainRang.size();i++)
		{
			ResultSet RS = DBcon.executeStoredProc("SelectNameClasses", distinictDomainRang.get(i));

			query +=" ?"+distinictDomainRang.get(i);
			try
			{
				RS.next();
				where += " ?"+distinictDomainRang.get(i)+ " a "+ RS.getString(1)+" . \n";
			}
			catch(SQLException ex)
			{
				JOptionPane.showMessageDialog(null, "Error in ResultSet"+ex.getMessage(),"QueryBuilder query",1);
				ex.printStackTrace();
			}
		}

		//constructs Classes only query
		for(int i=0;i<Domains.size();i++)
		{

			where += " ?"+Domains.get(i)+ " dbpedia-owl:"+relation.get(i)+" ?"+Ranges.get(i)+".\n";
		}

		where += " }";
		query += where;
		return query;
	}
	////////////////////////////////////////////////////////////////////////////////
	//Range based Query
	public String constructSPARQLQuery2()
	{
		String query = "select ";
		String where = "\n where { ";
		ArrayList<String> Domains = new ArrayList<String>();
		ArrayList<String> Ranges = new ArrayList<String>();
		ArrayList<String> relation = new ArrayList<String>();

		ArrayList<String> distinictDomainRang = new ArrayList<String>();
		ArrayList<String> distinictDomainRangURI = new ArrayList<String>();

		String tempDomain="";
		String tempRange="";
		String tempRelation="";


		ConnectionMySql DBcon = new ConnectionMySql();
		DBcon.openBDConnection();
		//1 get all the class relatives based on the domain class
		for(int i=0;i<matchedItems.length;i++)
		{
			ResultSet RS = DBcon.executeStoredProc("GetDomainRangeByRange", matchedItems[i]);
			try
			{
				//RS.first();
				while(RS.next())
				{
					tempDomain = RS.getString(1);
					tempRelation = RS.getString(2);
					tempRange = RS.getString(3);
					//JOptionPane.showMessageDialog(null, matchedItems[i]+"  "+tempDomain+"   "+tempRelation+"   "+tempRange,"QueryBuilder items",1);
					System.out.println("QueryBuilder items"+"   "+matchedItems[i]+"  "+tempDomain+"   "+tempRelation+"   "+tempRange);
					if(!( Domains.contains(tempDomain) &&  Ranges.contains(tempRange) && relation.contains(tempRelation)))
					{
						Domains.add(tempDomain);
						Ranges.add(tempRange);
						relation.add(tempRelation);
					}
					if(!distinictDomainRang.contains(tempDomain))
						distinictDomainRang.add(tempDomain);

					if(!distinictDomainRang.contains(tempRange))
						distinictDomainRang.add(tempRange);
				}
			}
			catch(SQLException ex)
			{
				JOptionPane.showMessageDialog(null, "Error in ResultSet"+ex.getMessage(),"QueryBuilder query",1);
				ex.printStackTrace();
			}
		}

		//2 connect to the DB to get the URI for the classes and relatives

		//3 construct the query
		for(int i=0;i<distinictDomainRang.size();i++)
		{
			ResultSet RS = DBcon.executeStoredProc("SelectNameClasses", distinictDomainRang.get(i));

			query +=" ?"+distinictDomainRang.get(i);
			try
			{
				RS.next();
				where += " ?"+distinictDomainRang.get(i)+ " a "+ RS.getString(1)+" . \n";
			}
			catch(SQLException ex)
			{
				JOptionPane.showMessageDialog(null, "Error in ResultSet"+ex.getMessage(),"QueryBuilder query",1);
				ex.printStackTrace();
			}
		}

		//constructs Classes only query
		for(int i=0;i<Domains.size();i++)
		{

			where += " ?"+Domains.get(i)+ " dbpedia-owl:"+relation.get(i)+" ?"+Ranges.get(i)+".\n";
		}

		where += " }";
		query += where;
		return query;
	}
	////////////////////////////////////////////////////////////////////////////////
	//Relation (Object property) based Query
	public String constructSPARQLQuery3()
	{
		String query = "select ";
		String where = "\n where { ";
		ArrayList<String> Domains = new ArrayList<String>();
		ArrayList<String> Ranges = new ArrayList<String>();
		ArrayList<String> relation = new ArrayList<String>();

		ArrayList<String> distinictDomainRang = new ArrayList<String>();
		ArrayList<String> distinictDomainRangURI = new ArrayList<String>();

		String tempDomain="";
		String tempRange="";
		String tempRelation="";


		ConnectionMySql DBcon = new ConnectionMySql();
		DBcon.openBDConnection();
		//1 get all the class relatives based on the domain class
		for(int i=0;i<matchedItems.length;i++)
		{
			ResultSet RS = DBcon.executeStoredProc("GetDomainRangeByRelation", matchedItems[i]);
			try
			{
				//RS.first();
				while(RS.next())
				{
					tempDomain = RS.getString(1);
					tempRelation = RS.getString(2);
					tempRange = RS.getString(3);
					//JOptionPane.showMessageDialog(null, matchedItems[i]+"  "+tempDomain+"   "+tempRelation+"   "+tempRange,"QueryBuilder items",1);
					System.out.println("QueryBuilder items"+"   "+matchedItems[i]+"  "+tempDomain+"   "+tempRelation+"   "+tempRange);
					if(!( Domains.contains(tempDomain) &&  Ranges.contains(tempRange) && relation.contains(tempRelation)))
					{
						Domains.add(tempDomain);
						Ranges.add(tempRange);
						relation.add(tempRelation);
					}
					if(!distinictDomainRang.contains(tempDomain))
						distinictDomainRang.add(tempDomain);

					if(!distinictDomainRang.contains(tempRange))
						distinictDomainRang.add(tempRange);
				}
			}
			catch(SQLException ex)
			{
				JOptionPane.showMessageDialog(null, "Error in ResultSet"+ex.getMessage(),"QueryBuilder query",1);
				ex.printStackTrace();
			}
		}

		//2 connect to the DB to get the URI for the classes and relatives

		//3 construct the query
		for(int i=0;i<distinictDomainRang.size();i++)
		{
			ResultSet RS = DBcon.executeStoredProc("SelectNameClasses", distinictDomainRang.get(i));

			query +=" ?"+distinictDomainRang.get(i);
			try
			{
				RS.next();
				where += " ?"+distinictDomainRang.get(i)+ " a "+ RS.getString(1)+" . \n";
			}
			catch(SQLException ex)
			{
				JOptionPane.showMessageDialog(null, "Error in ResultSet"+ex.getMessage(),"QueryBuilder query",1);
				ex.printStackTrace();
			}
		}

		//constructs Classes only query
		for(int i=0;i<Domains.size();i++)
		{

			where += " ?"+Domains.get(i)+ " dbpedia-owl:"+relation.get(i)+" ?"+Ranges.get(i)+".\n";
		}

		where += " }";
		query += where;
		return query;
	}
	////////////////////////////////////////////////////////////////////////////////
	//Relation (Data property) based Query
	public String constructSPARQLQuery4()
	{
		String query = "select ";
		String where = "\n where { ";
		ArrayList<String> Domains = new ArrayList<String>();
		ArrayList<String> Ranges = new ArrayList<String>();
		ArrayList<String> relation = new ArrayList<String>();

		ArrayList<String> distinictDomainRang = new ArrayList<String>();
		ArrayList<String> distinictDomainRangURI = new ArrayList<String>();

		String tempDomain="";
		String tempRange="";
		String tempRelation="";


		ConnectionMySql DBcon = new ConnectionMySql();
		DBcon.openBDConnection();
		//1 get all the class relatives based on the domain class
		for(int i=0;i<matchedItems.length;i++)
		{
			ResultSet RS = DBcon.executeStoredProc("GetDomainRangeByDataProperty", matchedItems[i]);
			try
			{
				//RS.first();
				while(RS.next())
				{
					tempDomain = RS.getString(1);
					tempRelation = RS.getString(2);
					tempRange = RS.getString(3);
					//JOptionPane.showMessageDialog(null, matchedItems[i]+"  "+tempDomain+"   "+tempRelation+"   "+tempRange,"QueryBuilder items",1);
					System.out.println("QueryBuilder items"+"   "+matchedItems[i]+"  "+tempDomain+"   "+tempRelation+"   "+tempRange);
					if(!( Domains.contains(tempDomain) &&  Ranges.contains(tempRange) && relation.contains(tempRelation)))
					{
						Domains.add(tempDomain);
						Ranges.add(tempRange);
						relation.add(tempRelation);
					}
					if(!distinictDomainRang.contains(tempDomain))
						distinictDomainRang.add(tempDomain);

					if(!distinictDomainRang.contains(tempRange))
						distinictDomainRang.add(tempRange);
				}
			}
			catch(SQLException ex)
			{
				JOptionPane.showMessageDialog(null, "Error in ResultSet"+ex.getMessage(),"QueryBuilder query",1);
				ex.printStackTrace();
			}
		}

		//2 connect to the DB to get the URI for the classes and relatives

		//3 construct the query
		for(int i=0;i<distinictDomainRang.size();i++)
		{
			ResultSet RS = DBcon.executeStoredProc("SelectNameClasses", distinictDomainRang.get(i));

			query +=" ?"+distinictDomainRang.get(i);
			try
			{
				RS.next();
				where += " ?"+distinictDomainRang.get(i)+ " a "+ RS.getString(1)+" . \n";
			}
			catch(SQLException ex)
			{
				JOptionPane.showMessageDialog(null, "Error in ResultSet"+ex.getMessage(),"QueryBuilder query",1);
				ex.printStackTrace();
			}
		}

		//constructs Classes only query
		for(int i=0;i<Domains.size();i++)
		{

			where += " ?"+Domains.get(i)+ " dbpedia-owl:"+relation.get(i)+" ?"+Ranges.get(i)+".\n";
		}

		where += " }";
		query += where;
		return query;
	}
}
