package querytranslatorbeta;


import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.ling.CoreLabel;

import org.tartarus.snowball.ext.EnglishStemmer;

import java.io.StringReader;
import java.util.List;

import javax.swing.JTextArea;



public class UserQueryProcessor 
{
	private String[] userQueryWords;
	private String[] userQueryStems;
	private String[] userQueryWordsRelations;
	//*****************************************************************************************
	public  List<CoreLabel> parseQuery( String userQuery,JTextArea jta) 
	{
		// This option shows loading and using an explicit tokenizer
		TokenizerFactory<CoreLabel> tokenizerFactory =
				PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
		Tokenizer<CoreLabel> tok =
				tokenizerFactory.getTokenizer(new StringReader(userQuery));
		List<CoreLabel> rawWords2 = tok.tokenize();

		//print the tokens befor removing stop words
		jta.setText(jta.getText()+"\n----------------------------------------");
		jta.setText(jta.getText()+"\ntokens befor removing stop words");
		for(int i=0;i<rawWords2.size();i++)
		{
			jta.setText(jta.getText()+"\ntoken   "+rawWords2.get(i).originalText());
		}

		return rawWords2;

	}
	//*****************************************************************************************
	public  List<CoreLabel> parseQuery( String userQuery) 
	{
		// This option shows loading and using an explicit tokenizer
		TokenizerFactory<CoreLabel> tokenizerFactory =
				PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
		Tokenizer<CoreLabel> tok =
				tokenizerFactory.getTokenizer(new StringReader(userQuery));
		List<CoreLabel> rawWords2 = tok.tokenize();

		//print the tokens befor removing stop words
		System.out.println("tokens befor removing stop words");
		for(int i=0;i<rawWords2.size();i++)
		{
			System.out.println("token   "+rawWords2.get(i).originalText());
		}

		return rawWords2;

	}
	//***********************************************************************************************
	//get words and relations using stanford parser
	// remove stop words
	// get stems
	public String[] processQuery(String query) 
	{
		//parse the query
		List<CoreLabel> words = parseQuery( query);

		//remove stop words 
		StopWordRemoval removal = new StopWordRemoval();
		removal.removeStopWords(words);

		//print the tokens after removing stop words
		System.out.println("tokens after removing stop words");
		for(int i=0;i<words.size();i++)
		{
			System.out.println("token   "+words.get(i).originalText());
		}

		userQueryWords = new String[words.size()];
		userQueryWordsRelations = new String[words.size()];
		userQueryStems = new String[words.size()];
		//use rawWords2

		EnglishStemmer stemmer = new EnglishStemmer();

		for(int i=0;i<words.size();i++)
		{
			//get words
			userQueryWords[i] = words.get(i).originalText();
			userQueryWordsRelations[i] = "";
			stemmer.setCurrent(userQueryWords[i]);
			stemmer.stem();
			userQueryStems[i] = stemmer.getCurrent();
			//System.out.println("stem "+stemmer.getCurrent());
		}

		//do stemming here

		EntityRecognizer eRecognizer = new EntityRecognizer();
		String[] entities = eRecognizer.getEntities(userQueryWords);

		//print the tokens after getting entities
		for(int i=0;i<entities.length;i++)
		{
			System.out.println("Entity   "+entities[i]);
		}

		return entities;
		//get stems
	}
	//***********************************************************************************************

	//get words and relations using stanford parser
	// remove stop words
	// get stems
	public String[] processQuery(String query,JTextArea jta) 
	{
		//parse the query
		List<CoreLabel> words = parseQuery( query,jta);

		//remove stop words 
		StopWordRemoval removal = new StopWordRemoval();
		removal.removeStopWords(words);

		//print the tokens after removing stop words
		jta.setText(jta.getText()+"\n----------------------------------------");
		jta.setText(jta.getText()+"\ntokens after removing stop words");
		for(int i=0;i<words.size();i++)
		{
			jta.setText(jta.getText()+"\ntoken   "+words.get(i).originalText());
		}

		userQueryWords = new String[words.size()];
		userQueryWordsRelations = new String[words.size()];
		userQueryStems = new String[words.size()];
		//use rawWords2

		EnglishStemmer stemmer = new EnglishStemmer();

		for(int i=0;i<words.size();i++)
		{
			//get words
			userQueryWords[i] = words.get(i).originalText();
			userQueryWordsRelations[i] = "";
			stemmer.setCurrent(userQueryWords[i]);
			stemmer.stem();
			userQueryStems[i] = stemmer.getCurrent();
			//System.out.println("stem "+stemmer.getCurrent());
		}

		jta.setText(jta.getText()+"\n----------------------------------------");
		jta.setText(jta.getText()+"\nName Entity Recognition");

		EntityRecognizer eRecognizer = new EntityRecognizer();
		String[] entities = eRecognizer.getEntities(userQueryWords);

		//print the tokens after getting entities
		for(int i=0;i<entities.length;i++)
		{
			jta.setText(jta.getText()+"\nEntity   "+entities[i]);
		}

		return entities;
		//get stems
	}
}
