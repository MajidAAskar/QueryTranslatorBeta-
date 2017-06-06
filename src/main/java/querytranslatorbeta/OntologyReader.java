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

	public static void main(String []args) throws Exception
	{
        readOntology();

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

            Set<OWLClass> classes;
            /*Set<OWLObjectProperty> prop;
            Set<OWLDataProperty> dataProp;
            Set<OWLNamedIndividual> individuals;*/

            classes = ontology.getClassesInSignature();
            /*prop = ontology.getObjectPropertiesInSignature();
            dataProp = ontology.getDataPropertiesInSignature();
            individuals = ontology.getIndividualsInSignature();*/
            //configurator = new OWLAPIOntologyConfigurator(this);
            
            ArrayList<String>[] objectProperties = new ArrayList[classes.size()];
            ArrayList<String>[] dataProperties = new ArrayList[classes.size()];
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

}
