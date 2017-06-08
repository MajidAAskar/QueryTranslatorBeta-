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
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class OntologyReader 
{
	private static Set<OWLClass> classes;
	private static Set<OWLObjectProperty> objectProp;
	private static Set<OWLDataProperty> dataProp;
	private static String matchedItems;


	public static void main(String []args) 
	{
		readOntology();
		//match();

	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void readOntology()
	{

		File file = new File("Ontologies/oboe-core.owl");
		//File file = new File("D:/Drive g/Germany/Germany  4-7 2017/oboe-core.owl");
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology;

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

				objectProperties[++i] = new ArrayList<String>();
				System.out.println(" \tObject Property Domain");
				for (OWLObjectPropertyDomainAxiom op : ontology.getAxioms(AxiomType.OBJECT_PROPERTY_DOMAIN)) {

					if (op.getDomain().equals(cls)) {   
						for(OWLObjectProperty oop : op.getObjectPropertiesInSignature()){

							objectProperties[i].add(oop.getIRI().getShortForm());
							System.out.println("\t\t +: " + oop.getIRI().getShortForm());
						}
						//System.out.println("\t\t +: " + op.getProperty().getNamedProperty().getIRI().getShortForm());
					}
				}

				System.out.println(" \tData Property Domain");
				dataProperties[++j] = new ArrayList<String>();
				for (OWLDataPropertyDomainAxiom dp : ontology.getAxioms(AxiomType.DATA_PROPERTY_DOMAIN)) {

					if (dp.getDomain().equals(cls)) {   
						for(OWLDataProperty odp : dp.getDataPropertiesInSignature()){

							dataProperties[j].add(odp.getIRI().getShortForm());
							System.out.println("\t\t +: " + odp.getIRI().getShortForm());
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
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void match(String [] queryWords,String [] queryStems,String [] queryEntities)
	{
		matchedItems="";
		//1-normal match
		//2-stem match
		//3-stem to stem match

		//match the query words with the classes
		matchedItems+="query words with the ontology classes\n";
		int[] qWordClass = matchClasses(queryWords);
		//match the query stems with the classes
		matchedItems+="query stems with the ontology classes\n";
		int[] qStemClass = matchClasses(queryStems);
		//match the query entities with the classes
		matchedItems+="query entities with the ontology classes\n";
		int[] qEntityClass = matchClasses(queryEntities);



		//match the query words with the object Properties
		matchedItems+="query words with the ontology object Properties\n";
		int[] qWordOProp = matchobjectProperties(queryWords);
		//match the query stems with the object Properties
		matchedItems+="query stems with the ontology object Properties\n";
		int[] qStemOProp = matchobjectProperties(queryStems);
		//match the query entities with the object Properties
		matchedItems+="query entities with the ontology object Properties\n";
		int[] qEntityOProp = matchobjectProperties(queryEntities);



		//match the query words with the data Properties
		matchedItems+="query words with the ontology data Properties\n";
		int[] qWordDProp = matchdataProperties(queryWords);
		//match the query stems with the data Properties
		matchedItems+="query stems with the ontology data Properties\n";
		int[] qStemDProp = matchdataProperties(queryStems);
		//match the query entities with the data Properties
		matchedItems+="query entities with the ontology data Properties\n";
		int[] qEntityDProp = matchdataProperties(queryEntities);
	}
	//	////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static int[] matchClasses(String [] tokens)
	{
		int[] matchIndex = new int[tokens.length];
		int j=0;
		for (OWLClass cls : classes)
		{
			for(int i=0;i<tokens.length;i++)
				if(cls.getIRI().getShortForm().equalsIgnoreCase(tokens[i]))
				{
					matchIndex[i]=j;
					matchedItems+=cls.getIRI().getShortForm()+" = "+tokens[i]+"\n";
				}
			j++;
		}

		return matchIndex;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static int[] matchobjectProperties(String [] tokens)
	{
		int[] matchIndex = new int[tokens.length];
		int j=0;
		for (OWLObjectProperty op : objectProp)
		{
			//System.out.println(objectProperties[j]);
			for(int i=0;i<tokens.length;i++)
				if(op.getIRI().getShortForm().equalsIgnoreCase((tokens[i])))
				{
					matchIndex[i]=j;
					matchedItems+=op.getIRI().getShortForm()+" = "+tokens[i]+"\n";
				}
			j++;
		}

		return matchIndex;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static int[] matchdataProperties(String [] tokens)
	{
		int[] matchIndex = new int[tokens.length];
		int j=0;
		for (OWLDataProperty dp : dataProp)
		{
			for(int i=0;i<tokens.length;i++)
				if(dp.getIRI().getShortForm().equalsIgnoreCase(tokens[i]))
				{
					matchIndex[i]=j;
					matchedItems+=dp.getIRI().getShortForm()+" = "+tokens[i]+"\n";
				}
			j++;
		}

		return matchIndex;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static String getMatchedItems()
	{
		return matchedItems;	
	}
}