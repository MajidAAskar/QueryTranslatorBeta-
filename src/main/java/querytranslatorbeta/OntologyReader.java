package querytranslatorbeta;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

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
	private static ArrayList<String>[] objectProperties;
	private static ArrayList<String>[] dataProperties;

	public static void main(String []args) 
	{
		readOntology();
		//match();

	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void readOntology()
	{

		File file = new File("Ontologies/oboe-core.owl");
		//File file = new File("C:/Users/Mohab/Downloads/obi.owl");
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology;

		try {
			ontology = manager.loadOntologyFromOntologyDocument(file);

			System.out.println("Ontology Loaded...");
			System.out.println("Document IRI: " + file);
			System.out.println("Ontology : " + ontology.getOntologyID());
			System.out.println("Format      : "
					+ manager.getOntologyFormat(ontology));

			/*Set<OWLObjectProperty> prop;
            Set<OWLDataProperty> dataProp;
            Set<OWLNamedIndividual> individuals;*/

			classes = ontology.getClassesInSignature();
			/*prop = ontology.getObjectPropertiesInSignature();
            dataProp = ontology.getDataPropertiesInSignature();
            individuals = ontology.getIndividualsInSignature();*/
			//configurator = new OWLAPIOntologyConfigurator(this);

			objectProperties = new ArrayList[classes.size()];
			dataProperties = new ArrayList[classes.size()];
			int i=-1,j=-1;

			System.out.println("Classes");
			System.out.println("--------------------------------");
			for (OWLClass cls : classes) {
				System.out.println("+: " + cls.getIRI().getShortForm());

				System.out.println(" \tObject Property Domain");
				for (OWLObjectPropertyDomainAxiom op : ontology.getAxioms(AxiomType.OBJECT_PROPERTY_DOMAIN)) {
					i++;
					if (op.getDomain().equals(cls)) {   
						for(OWLObjectProperty oop : op.getObjectPropertiesInSignature()){
							objectProperties[i] = new ArrayList<String>();
							objectProperties[i].add(oop.getIRI().getShortForm());
							System.out.println("\t\t +: " + oop.getIRI().getShortForm());
						}
						//System.out.println("\t\t +: " + op.getProperty().getNamedProperty().getIRI().getShortForm());
					}
				}

				System.out.println(" \tData Property Domain");
				for (OWLDataPropertyDomainAxiom dp : ontology.getAxioms(AxiomType.DATA_PROPERTY_DOMAIN)) {
					j++;
					if (dp.getDomain().equals(cls)) {   
						for(OWLDataProperty odp : dp.getDataPropertiesInSignature()){
							dataProperties[j] = new ArrayList<String>();
							dataProperties[j].add(odp.getIRI().getShortForm());
							System.out.println("\t\t +: " + odp.getIRI().getShortForm());
						}
						//System.out.println("\t\t +:" + dp.getProperty());
					}
				}

			}

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			//Logger.getLogger(OntologyAPI.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void match(String [] queryWords,String [] queryStems,String [] queryEntities)
	{
		//1-normal match
		//2-stem match
		//3-stem to stem match

		//match the query words with the classes
		int[] qWordClass = matchClasses(queryWords);
		//match the query words with the object Properties
		int[] qWordOProp = matchobjectProperties(queryWords);
		//match the query words with the data Properties
		int[] qWordDProp = matchdataProperties(queryWords);

		
		//match the query stems with the classes
		int[] qStemClass = matchClasses(queryStems);
		//match the query stems with the object Properties
		int[] qStemOProp = matchobjectProperties(queryStems);
		//match the query stems with the data Properties
		int[] qStemDProp = matchdataProperties(queryStems);

		
		//match the query entities with the classes
		int[] qEntityClass = matchClasses(queryEntities);
		//match the query entities with the object Properties
		int[] qEntityOProp = matchobjectProperties(queryEntities);
		//match the query entities with the data Properties
		int[] qEntityDProp = matchdataProperties(queryEntities);
	}
	//	////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static int[] matchClasses(String [] tokens)
	{
		int[] matchIndex = new int[tokens.length];
		
		for (OWLClass cls : classes)
		{
			for(int i=0;i<tokens.length;i++)
				if(cls.getIRI().getShortForm().equals(tokens[i]))
				{
					matchIndex[i]=i;
					System.out.println(cls.getIRI().getShortForm()+" = "+tokens[i]);
				}
		}
		
		return matchIndex;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static int[] matchobjectProperties(String [] tokens)
	{
		int[] matchIndex = new int[tokens.length];
		
		for(int j=0;j<objectProperties.length;j++)
		{
			for(int i=0;i<tokens.length;i++)
				if(objectProperties[j].equals(tokens[i]))
				{
					matchIndex[i]=j;
					System.out.println(objectProperties[j]+" = "+tokens[i]);
				}
		}
		
		return matchIndex;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static int[] matchdataProperties(String [] tokens)
	{
		int[] matchIndex = new int[tokens.length];
		
		for(int j=0;j<dataProperties.length;j++)
		{
			for(int i=0;i<tokens.length;i++)
				if(dataProperties[j].equals(tokens[i]))
				{
					matchIndex[i]=j;
					System.out.println(dataProperties[j]+" = "+tokens[i]);
				}
		}
		
		return matchIndex;
	}
}