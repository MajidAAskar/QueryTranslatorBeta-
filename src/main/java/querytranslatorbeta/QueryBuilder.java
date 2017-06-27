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
	}
	////////////////////////////////////////////////////////////////////////////////
	public String constructSPARQLQuery()
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
		//1 get all the class relatives
		for(int i=0;i<matchedItems.length;i++)
		{
			ResultSet RS = DBcon.executeStoredProc("getDomainRange", matchedItems[i]);
			try
			{
				//RS.first();
				while(RS.next())
				{
					tempDomain = RS.getString(1);
					tempRelation = RS.getString(2);
					tempRange = RS.getString(3);
					if(!(Domains.contains(tempDomain) &&  Ranges.contains(tempRange) && relation.contains(tempRelation)))
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
				JOptionPane.showMessageDialog(null, "Error in ResultSet"+ex.getMessage());
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
				JOptionPane.showMessageDialog(null, "Error in ResultSet"+ex.getMessage());
				ex.printStackTrace();
			}
		}

		//constructs Classes only query
		for(int i=0;i<Domains.size();i++)
		{
			//query +=" ?"+Domains.get(i) +" ?"+Ranges.get(i);
			//where += " ?"+Domains.get(i)+ " a "+"URI . ";
			where += " ?"+Domains.get(i)+ " owl:"+relation.get(i)+" ?"+Ranges.get(i)+".\n";
		}

		where += " }";
		query += where;
		return query;
	}
}
