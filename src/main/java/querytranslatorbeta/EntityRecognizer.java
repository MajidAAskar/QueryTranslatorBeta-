package querytranslatorbeta;


import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.ling.CoreLabel;


public class EntityRecognizer 
{
	public String[] getEntities(String[] words) 
	{
		//String serializedClassifier = "classifiers/english.muc.7class.distsim.crf.ser.gz";
		//String serializedClassifier = "classifiers/english.muc.7class.caseless.distsim.crf.ser.gz";//new one 5.1.2017
		//String serializedClassifier = "classifiers/english.all.3class.distsim.crf.ser.gz";
		String serializedClassifier = "classifiers/ner-model.ser.gz";
		AbstractSequenceClassifier<CoreLabel> classifier=null;
		try
		{
			classifier = CRFClassifier.getClassifier(serializedClassifier);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}

		String[] entities = new String[words.length];
		int i=0;

		for (String str : words) 
		{
			entities[i++] = classifier.classifyToString(str);
		}
		return entities;
	}
	//****************************************************************************************
	public String getEntities(String statement) 
	{
		//String serializedClassifier = "classifiers/english.muc.7class.distsim.crf.ser.gz";
		String serializedClassifier = "classifiers/english.all.3class.distsim.crf.ser.gz";
		AbstractSequenceClassifier<CoreLabel> classifier=null;
		try
		{
			classifier = CRFClassifier.getClassifier(serializedClassifier);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}

		String entities = new String();
		//int i=0;

		//for (String str : words) 
		//{
			entities = classifier.classifyToString(statement);
			//entities = classifier.classifyToString(statement, "tsv", false);
		//}
		return entities;
	}
}
