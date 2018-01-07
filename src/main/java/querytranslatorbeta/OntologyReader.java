package querytranslatorbeta;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.tartarus.snowball.ext.EnglishStemmer;

public class OntologyReader 
{
	private Set<OWLClass> classes;
	private Set<OWLObjectProperty> objectProp;
	private Set<OWLDataProperty> dataProp;
	private ArrayList<String> matchedItems;
	private Set<String> ontologyClasses;
	private Set<String> ontologyDataProperties;
	private Set<String> ontologyObjectProperties;
	private String matchedItemsString="";
	private String matchedItemsStringToSend="";
	private boolean readFromFile = false;


	public OntologyReader(boolean FileOrDB)
	{
		readFromFile = FileOrDB;
	}
	public static void main(String []args) 
	{
		new OntologyReader(false).readOntology();
		//match();

	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void readOntology()
	{
		if(readFromFile)
			readOntologyFromFile();
		else
			readOntologyFromDB();
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void readOntologyFromFile()
	{
		//File file = new File("Ontologies/oboe-core.owl");
		File file = new File("Ontologies/dbpedia_2016-10.owl");
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology;

		ConnectionMySql dbcon = new ConnectionMySql();


		try {
			ontology = manager.loadOntologyFromOntologyDocument(file);

			System.out.println("Ontology Loaded...");
			System.out.println("Document IRI: " + file);
			System.out.println("Ontology : " + ontology.getOntologyID());
			System.out.println("Format      : "
					+ manager.getOntologyFormat(ontology));

			//ArrayList<String>[] objectProperties;
			//ArrayList<String>[] dataProperties;
			//Set<OWLNamedIndividual> individuals;

			classes = ontology.getClassesInSignature();
			objectProp = ontology.getObjectPropertiesInSignature();
			dataProp = ontology.getDataPropertiesInSignature();
			//individuals = ontology.getIndividualsInSignature();
			//configurator = new OWLAPIOntologyConfigurator(this);

			//objectProperties = new ArrayList[classes.size()];
			//dataProperties = new ArrayList[classes.size()];
			int i=-1,j=-1; 

			dbcon.openBDConnection();
			System.out.println("Classes"/*+objectProperties.length*/);
			System.out.println("--------------------------------");
			for (OWLClass cls : classes) {
				System.out.println("+: " + cls.getIRI().getShortForm());


				
				dbcon.executeStoredProc("InsertClasses",cls.getIRI().getShortForm(),cls.getIRI().toQuotedString(),"0");

				//objectProperties[++i] = new ArrayList<String>();
				System.out.println(" \tObject Property Domain");
				for (OWLObjectPropertyDomainAxiom op : ontology.getAxioms(AxiomType.OBJECT_PROPERTY_DOMAIN)) {

					if (op.getDomain().equals(cls)) 
					{   
						for(OWLObjectProperty oop : op.getObjectPropertiesInSignature())
						{
							//System.out.println(" \tObject Property Range");
							for (OWLObjectPropertyRangeAxiom Rop : ontology.getAxioms(AxiomType.OBJECT_PROPERTY_RANGE)) 
							{
								for(OWLObjectProperty Roop : Rop.getObjectPropertiesInSignature())
								{
									if(Roop.equals(oop))
									{
										//objectProperties[i].add(oop.getIRI().getShortForm());
										System.out.println("\t\t +: " + oop.getIRI().getShortForm());
										dbcon.executeStoredProc("InsertObjectProperties",oop.getIRI().getShortForm(),oop.getIRI().toQuotedString(),
												cls.getIRI().getShortForm(),((OWLClass)Rop.getRange()).getIRI().getShortForm());
									}
								}

							}
							//System.out.println("\t\t +: " + op.getProperty().getNamedProperty().getIRI().getShortForm());
						}
					}

				}
				System.out.println(" \tData Property Domain");
				//dataProperties[++j] = new ArrayList<String>();
				for (OWLDataPropertyDomainAxiom dp : ontology.getAxioms(AxiomType.DATA_PROPERTY_DOMAIN)) {

					if (dp.getDomain().equals(cls)) {   
						for(OWLDataProperty odp : dp.getDataPropertiesInSignature()){

							//dataProperties[j].add(odp.getIRI().getShortForm());
							System.out.println("\t\t +: " + odp.getIRI().getShortForm());
							dbcon.executeStoredProc("InsertDataProperties",cls.getIRI().getShortForm(),odp.getIRI().getShortForm(),odp.getIRI().toQuotedString());
						}
						//System.out.println("\t\t +:" + dp.getProperty());
					}
				}

			}

		} catch (Exception ex) {
			System.out.println("Exception "+ex.getMessage());
			ex.printStackTrace();
			//Logger.getLogger(OntologyAPI.class.getName()).log(Level.SEVERE, null, ex);
		}
		dbcon.closeBDConnection();
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void readOntologyFromDB()
	{
		ontologyClasses = new HashSet<String>();
		ontologyDataProperties = new HashSet<String>();
		ontologyObjectProperties = new HashSet<String>();
		ConnectionMySql dbcon = new ConnectionMySql();
		dbcon.openBDConnection();
		ResultSet rsOntology; 
		try
		{
			rsOntology = dbcon.executeStoredProc("SelectAllclasses");
			while(rsOntology.next())
			{
				//System.out.println("class: "+rsOntology.getString(1));
				ontologyClasses.add(rsOntology.getString(1));
			}

			rsOntology = dbcon.executeStoredProc("SelectAllDataProperties");
			while(rsOntology.next())
			{
				//System.out.println("DataProperties: "+rsOntology.getString(1));
				ontologyDataProperties.add(rsOntology.getString(1));
			}

			rsOntology = dbcon.executeStoredProc("SelectAllObjectProperties");
			while(rsOntology.next())
			{
				//System.out.println("ObjectProperties: "+rsOntology.getString(1));
				ontologyObjectProperties.add(rsOntology.getString(1));
			}
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(),"readOntologyFromDB ",1);
			e.printStackTrace();
		}
		dbcon.closeBDConnection();

	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void match(String [] queryWords,String [] queryStems,String [] queryEntities)
	{
		matchedItems= new ArrayList<String>();
		matchedItemsString="";
		//1-normal match
		//2-stem match
		//3-stem to stem match

		//match the query words with the classes
		matchedItemsString+="* Query words with the ontology classes\n";
		matchClasses(queryWords);
		//match the query stems with the classes
		matchedItemsString+="* Query stems with the ontology classes\n";
		matchClasses(queryStems);
		//match the query entities with the classes
		matchedItemsString+="* Query entities with the ontology classes\n";
		matchClasses(queryEntities);



		//match the query words with the object Properties
		matchedItemsString+="* Query words with the ontology object Properties\n";
		matchObjectProperties(queryWords);
		//match the query stems with the object Properties
		matchedItemsString+="* Query stems with the ontology object Properties\n";
		matchObjectProperties(queryStems);
		//match the query entities with the object Properties
		matchedItemsString+="* Query entities with the ontology object Properties\n";
		matchObjectProperties(queryEntities);



		//match the query words with the data Properties
		matchedItemsString+="* Query words with the ontology data Properties\n";
		matchDataProperties(queryWords);
		//match the query stems with the data Properties
		matchedItemsString+="* Query stems with the ontology data Properties\n";
		matchDataProperties(queryStems);
		//match the query entities with the data Properties
		matchedItemsString+="* Query entities with the ontology data Properties\n";
		matchDataProperties(queryEntities);
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void matchClasses(String [] tokens)
	{
		if(readFromFile)
			matchClassesFromFile(tokens);
		else
			matchClassesFromDB(tokens);
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void matchClassesFromFile(String [] tokens)
	{
		//ArrayList<String> matchedItemsWithClasses = new ArrayList<String>();
		String temp="";
		for (OWLClass cls : classes)
		{
			for(int i=0;i<tokens.length;i++)
				if(cls.getIRI().getShortForm().equalsIgnoreCase(tokens[i]))
				{
					temp = cls.getIRI().getShortForm()+" = "+tokens[i];
					temp = temp.toLowerCase();
					if(!matchedItems.contains(temp))
					{
						matchedItems.add(temp);
						matchedItemsString+=temp+"\n";
						matchedItemsStringToSend+=temp+"\n";
						//matchedItemsWithClasses.add(temp);
					}
				}
		}

		//return matchedItemsWithClasses;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void matchClassesFromDB(String [] tokens)
	{
		//ArrayList<String> matchedItemsWithClasses = new ArrayList<String>();
		String temp="";
		for (String cls : ontologyClasses)
		{
			for(int i=0;i<tokens.length;i++)
				if(cls.equalsIgnoreCase(tokens[i]))
				{
					temp = cls+" = "+tokens[i];
					temp = temp.toLowerCase();
					if(!matchedItems.contains(temp))
					{
						matchedItems.add(temp);
						matchedItemsString+=temp+"\n";
						matchedItemsStringToSend+=temp+"\n";
						//matchedItemsWithClasses.add(temp);
					}
				}
		}

		//return matchedItemsWithClasses;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void matchClassesStems(String [] tokens)
	{
		if(readFromFile)
			matchClassesStemsFromFile(tokens);
		else
			matchClassesStemsFromDB(tokens);
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void matchClassesStemsFromFile(String [] tokens)
	{
		//ArrayList<String> matchedItemsWithClasses = new ArrayList<String>();
		String temp="";
		EnglishStemmer stemmer = new EnglishStemmer();
		for (OWLClass cls : classes)
		{
			stemmer.setCurrent(cls.getIRI().getShortForm());
			stemmer.stem();
			for(int i=0;i<tokens.length;i++)
				if(stemmer.getCurrent().equalsIgnoreCase(tokens[i]))
				{
					temp = cls.getIRI().getShortForm()+" = "+tokens[i];
					temp = temp.toLowerCase();
					if(!matchedItems.contains(temp))
					{
						matchedItems.add(temp);
						matchedItemsString+=temp+"\n";
						matchedItemsStringToSend+=temp+"\n";
						//matchedItemsWithClasses.add(temp);
					}
				}
		}

		//return matchedItemsWithClasses;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void matchClassesStemsFromDB(String [] tokens)
	{
		//ArrayList<String> matchedItemsWithClasses = new ArrayList<String>();
		String temp="";
		EnglishStemmer stemmer = new EnglishStemmer();
		for (String cls : ontologyClasses)
		{
			stemmer.setCurrent(cls);
			stemmer.stem();
			for(int i=0;i<tokens.length;i++)
				if(stemmer.getCurrent().equalsIgnoreCase(tokens[i]))
				{
					temp = cls+" = "+tokens[i];
					temp = temp.toLowerCase();
					if(!matchedItems.contains(temp))
					{
						matchedItems.add(temp);
						matchedItemsString+=temp+"\n";
						matchedItemsStringToSend+=temp+"\n";
						//matchedItemsWithClasses.add(temp);
					}
				}
		}

		//return matchedItemsWithClasses;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void matchObjectProperties(String [] tokens)
	{
		if(readFromFile)
			matchObjectPropertiesFromFile(tokens);
		else
			matchObjectPropertiesFromDB(tokens);
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void matchObjectPropertiesFromFile(String [] tokens)
	{
		//ArrayList<String> matchedItemsWithObjectProperties = new ArrayList<String>();
		String temp="";
		for (OWLObjectProperty op : objectProp)
		{
			//System.out.println(objectProperties[j]);
			for(int i=0;i<tokens.length;i++)
				if(op.getIRI().getShortForm().equalsIgnoreCase((tokens[i])))
				{
					temp = op.getIRI().getShortForm()+" = "+tokens[i];
					temp = temp.toLowerCase();
					if(!matchedItems.contains(temp))
					{
						matchedItems.add(temp);
						matchedItemsString+=temp+"\n";
						matchedItemsStringToSend+=temp+"\n";
						//matchedItemsWithObjectProperties.add(temp);
					}
				}
		}
		//return matchedItemsWithObjectProperties;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void matchObjectPropertiesFromDB(String [] tokens)
	{
		//ArrayList<String> matchedItemsWithObjectProperties = new ArrayList<String>();
		String temp="";
		for (String op : ontologyObjectProperties)
		{
			//System.out.println(objectProperties[j]);
			for(int i=0;i<tokens.length;i++)
				if(op.equalsIgnoreCase((tokens[i])))
				{
					temp = op+" = "+tokens[i];
					temp = temp.toLowerCase();
					if(!matchedItems.contains(temp))
					{
						matchedItems.add(temp);
						matchedItemsString+=temp+"\n";
						matchedItemsStringToSend+=temp+"\n";
						//matchedItemsWithObjectProperties.add(temp);
					}
				}
		}

		//return matchedItemsWithObjectProperties;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void matchObjectPropertiesStems(String [] tokens)
	{
		if(readFromFile)
			matchObjectPropertiesStemsFromFile(tokens);
		else
			matchObjectPropertiesStemsFromDB(tokens);
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void matchObjectPropertiesStemsFromFile(String [] tokens)
	{
		//ArrayList<String> matchedItemsWithObjectProperties = new ArrayList<String>();
		String temp="";
		EnglishStemmer stemmer = new EnglishStemmer();
		for (OWLObjectProperty op : objectProp)
		{
			stemmer.setCurrent(op.getIRI().getShortForm());
			stemmer.stem();
			for(int i=0;i<tokens.length;i++)
				if(stemmer.getCurrent().equalsIgnoreCase((tokens[i])))
				{
					temp = op.getIRI().getShortForm()+" = "+tokens[i];
					temp = temp.toLowerCase();
					if(!matchedItems.contains(temp))
					{
						matchedItems.add(temp);
						matchedItemsString+=temp+"\n";
						matchedItemsStringToSend+=temp+"\n";
						//matchedItemsWithObjectProperties.add(temp);
					}
				}
		}

		//return matchedItemsWithObjectProperties;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void matchObjectPropertiesStemsFromDB(String [] tokens)
	{
		//ArrayList<String> matchedItemsWithObjectProperties = new ArrayList<String>();
		String temp="";
		EnglishStemmer stemmer = new EnglishStemmer();
		for (String op : ontologyObjectProperties)
		{
			stemmer.setCurrent(op);
			stemmer.stem();
			for(int i=0;i<tokens.length;i++)
				if(stemmer.getCurrent().equalsIgnoreCase((tokens[i])))
				{
					temp = op+" = "+tokens[i];
					temp = temp.toLowerCase();
					if(!matchedItems.contains(temp))
					{
						matchedItems.add(temp);
						matchedItemsString+=temp+"\n";
						matchedItemsStringToSend+=temp+"\n";
						//matchedItemsWithObjectProperties.add(temp);
					}
				}
		}

		//return matchedItemsWithObjectProperties;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void matchDataProperties(String [] tokens)
	{
		if(readFromFile)
			matchDataPropertiesFromFile(tokens);
		else
			matchDataPropertiesFromDB(tokens);
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void matchDataPropertiesFromFile(String [] tokens)
	{
		//ArrayList<String> matchedItemsWithDataProperties = new ArrayList<String>();
		String temp="";
		for (OWLDataProperty dp : dataProp)
		{
			for(int i=0;i<tokens.length;i++)
				if(dp.getIRI().getShortForm().equalsIgnoreCase(tokens[i]))
				{
					temp = dp.getIRI().getShortForm()+" = "+tokens[i];
					temp = temp.toLowerCase();
					if(!matchedItems.contains(temp))
					{
						matchedItems.add(temp);
						matchedItemsString+=temp+"\n";
						matchedItemsStringToSend+=temp+"\n";
						//matchedItemsWithDataProperties.add(temp);
					}
				}
		}

		//return matchedItemsWithDataProperties;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void matchDataPropertiesFromDB(String [] tokens)
	{
		//ArrayList<String> matchedItemsWithDataProperties = new ArrayList<String>();
		String temp="";
		for (String dp : ontologyDataProperties)
		{
			for(int i=0;i<tokens.length;i++)
				if(dp.equalsIgnoreCase(tokens[i]))
				{
					temp = dp+" = "+tokens[i];
					temp = temp.toLowerCase();
					if(!matchedItems.contains(temp))
					{
						matchedItems.add(temp);
						matchedItemsString+=temp+"\n";
						matchedItemsStringToSend+=temp+"\n";
						//matchedItemsWithDataProperties.add(temp);
					}
				}
		}
		//return matchedItemsWithDataProperties;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void matchDataPropertiesStems(String [] tokens)
	{
		if(readFromFile)
			matchDataPropertiesStemsFromFile(tokens);
		else
			matchDataPropertiesStemsFromDB(tokens);
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void matchDataPropertiesStemsFromFile(String [] tokens)
	{
		//ArrayList<String> matchedItemsWithDataProperties = new ArrayList<String>();
		String temp="";
		EnglishStemmer stemmer = new EnglishStemmer();
		for (OWLDataProperty dp : dataProp)
		{
			stemmer.setCurrent(dp.getIRI().getShortForm());
			stemmer.stem();
			for(int i=0;i<tokens.length;i++)
				if(stemmer.getCurrent().equalsIgnoreCase(tokens[i]))
				{
					temp = dp.getIRI().getShortForm()+" = "+tokens[i];
					temp = temp.toLowerCase();
					if(!matchedItems.contains(temp))
					{
						matchedItems.add(temp);
						matchedItemsString+=temp+"\n";
						matchedItemsStringToSend+=temp+"\n";
						//matchedItemsWithDataProperties.add(temp);
					}
				}
		}

		//return matchedItemsWithDataProperties;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void matchDataPropertiesStemsFromDB(String [] tokens)
	{
		//ArrayList<String> matchedItemsWithDataProperties = new ArrayList<String>();
		String temp="";
		EnglishStemmer stemmer = new EnglishStemmer();
		for (String dp : ontologyDataProperties)
		{
			stemmer.setCurrent(dp);
			stemmer.stem();
			for(int i=0;i<tokens.length;i++)
				if(stemmer.getCurrent().equalsIgnoreCase(tokens[i]))
				{
					temp = dp+" = "+tokens[i];
					temp = temp.toLowerCase();
					if(!matchedItems.contains(temp))
					{
						matchedItems.add(temp);
						matchedItemsString+=temp+"\n";
						matchedItemsStringToSend+=temp+"\n";
						//matchedItemsWithDataProperties.add(temp);
					}
				}
		}

		//return matchedItemsWithDataProperties;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	public ArrayList<String> getMatchedItems()
	{
		return matchedItems;	
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String getMatchedItemsString()
	{
		return matchedItemsStringToSend;	
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String getClasses()
	{
		if(readFromFile)
			return getClassesFromFile();
		else
			return getClassesFromDB();
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String getDataProperties()
	{
		if(readFromFile)
			return getDataPropertiesFromFile();
		else
			return getDataPropertiesFromDB();
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String getObjectProperties()
	{
		if(readFromFile)
			return getObjectPropertiesFromFile();
		else
			return getObjectPropertiesFromDB();
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String getClassesFromFile()
	{
		String classesStr="";
		for(OWLClass cls :classes)
			classesStr+=cls.getIRI().getShortForm()+"\n";
		return classesStr;	
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String getDataPropertiesFromFile()
	{
		String dataPropStr="";
		for(OWLDataProperty dp :dataProp)
			dataPropStr+=dp.getIRI().getShortForm()+"\n";
		return dataPropStr;	
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String getObjectPropertiesFromFile()
	{
		String objectPropStr="";
		for(OWLObjectProperty op :objectProp)
			objectPropStr+=op.getIRI().getShortForm()+"\n";
		return objectPropStr;	
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String getClassesFromDB()
	{
		String classesStr="";
		for(String Class :ontologyClasses)
			classesStr+=Class+"\n";
		return classesStr;	
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String getDataPropertiesFromDB()
	{
		String dataPropStr="";
		for(String dp :ontologyDataProperties)
			dataPropStr+=dp+"\n";
		return dataPropStr;	
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String getObjectPropertiesFromDB()
	{
		String objectPropStr="";
		for(String op :ontologyObjectProperties)
			objectPropStr+=op+"\n";
		return objectPropStr;	
	}
}