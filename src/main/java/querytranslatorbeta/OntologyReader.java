package querytranslatorbeta;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

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
	private String matchedItemsString="";
	private String matchedItemsStringToSend="";


	public static void main(String []args) 
	{
		new OntologyReader().readOntology();
		//match();

	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void readOntology()
	{

		File file = new File("Ontologies/oboe-core.owl");
		//File file = new File("D:/Drive g/Germany/Germany  4-7 2017/oboe-core.owl");
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

			ArrayList<String>[] objectProperties;
			ArrayList<String>[] dataProperties;
			//Set<OWLNamedIndividual> individuals;
			
			classes = ontology.getClassesInSignature();
			objectProp = ontology.getObjectPropertiesInSignature();
			dataProp = ontology.getDataPropertiesInSignature();
			//individuals = ontology.getIndividualsInSignature();
			//configurator = new OWLAPIOntologyConfigurator(this);

			objectProperties = new ArrayList[classes.size()];
			dataProperties = new ArrayList[classes.size()];
			int i=-1,j=-1; 
			

			System.out.println("Classes"+objectProperties.length);
			System.out.println("--------------------------------");
			for (OWLClass cls : classes) {
				System.out.println("+: " + cls.getIRI().getShortForm());

				
				dbcon.openBDConnection();
				dbcon.executeStoredProc("InsertClasses",cls.getIRI().getShortForm(),cls.getIRI().toQuotedString(),"0");
				
				objectProperties[++i] = new ArrayList<String>();
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
											objectProperties[i].add(oop.getIRI().getShortForm());
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
				dataProperties[++j] = new ArrayList<String>();
				for (OWLDataPropertyDomainAxiom dp : ontology.getAxioms(AxiomType.DATA_PROPERTY_DOMAIN)) {

					if (dp.getDomain().equals(cls)) {   
						for(OWLDataProperty odp : dp.getDataPropertiesInSignature()){

							dataProperties[j].add(odp.getIRI().getShortForm());
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
		//ArrayList<String> matchedItemsWithClasses = new ArrayList<String>();
		String temp="";
		for (OWLClass cls : classes)
		{
			for(int i=0;i<tokens.length;i++)
				if(cls.getIRI().getShortForm().equalsIgnoreCase(tokens[i]))
				{
					temp = cls.getIRI().getShortForm()+" = "+tokens[i];
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
		//ArrayList<String> matchedItemsWithObjectProperties = new ArrayList<String>();
		String temp="";
		for (OWLObjectProperty op : objectProp)
		{
			//System.out.println(objectProperties[j]);
			for(int i=0;i<tokens.length;i++)
				if(op.getIRI().getShortForm().equalsIgnoreCase((tokens[i])))
				{
					temp = op.getIRI().getShortForm()+" = "+tokens[i];
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
		//ArrayList<String> matchedItemsWithDataProperties = new ArrayList<String>();
		String temp="";
		for (OWLDataProperty dp : dataProp)
		{
			for(int i=0;i<tokens.length;i++)
				if(dp.getIRI().getShortForm().equalsIgnoreCase(tokens[i]))
				{
					temp = dp.getIRI().getShortForm()+" = "+tokens[i];
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
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String getClasses()
	{
		String classesStr="";
		for(OWLClass cls :classes)
			classesStr+=cls.getIRI().getShortForm()+"\n";
		return classesStr;	
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String getDataProperties()
	{
		String dataPropStr="";
		for(OWLDataProperty dp :dataProp)
			dataPropStr+=dp.getIRI().getShortForm()+"\n";
		return dataPropStr;	
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String getObjectProperties()
	{
		String objectPropStr="";
		for(OWLObjectProperty op :objectProp)
			objectPropStr+=op.getIRI().getShortForm()+"\n";
		return objectPropStr;	
	}
}