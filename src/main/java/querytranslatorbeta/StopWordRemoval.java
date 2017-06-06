package querytranslatorbeta;


import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.CoreLabel;


public class StopWordRemoval 
{
	public void removeStopWords(List<CoreLabel> querywords )
	{
		ArrayList<String> stopList = new ArrayList<String>();
		stopList.add(".");
		stopList.add("a");
		stopList.add("able");
		stopList.add("about");
		stopList.add("above");
		stopList.add("according");
		stopList.add("accordingly");
		stopList.add("across");
		stopList.add("actually");
		stopList.add("after");
		stopList.add("afterwards");
		stopList.add("again");
		stopList.add("against");
		stopList.add("all");
		stopList.add("allow");
		stopList.add("allows");
		stopList.add("almost");
		stopList.add("alone");
		stopList.add("along");
		stopList.add("already");
		stopList.add("also");
		stopList.add("although");
		stopList.add("always");
		stopList.add("am");
		stopList.add("among");
		stopList.add("amongst");
		stopList.add("an");
		stopList.add("and");
		stopList.add("another");
		stopList.add("any");
		stopList.add("anybody");
		stopList.add("anyhow");
		stopList.add("anyone");
		stopList.add("anything");
		stopList.add("anyway");
		stopList.add("anyways");
		stopList.add("anywhere");
		stopList.add("apart");
		stopList.add("appear");
		stopList.add("appreciate");
		stopList.add("appropriate");
		stopList.add("are");
		stopList.add("around");
		stopList.add("as");
		stopList.add("aside");
		stopList.add("ask");
		stopList.add("asking");
		stopList.add("associated");
		stopList.add("at");
		stopList.add("available");
		stopList.add("away");
		stopList.add("awfully");
		stopList.add("b");
		stopList.add("be");
		stopList.add("became");
		stopList.add("because");
		stopList.add("become");
		stopList.add("becomes");
		stopList.add("becoming");
		stopList.add("been");
		stopList.add("before");
		stopList.add("beforehand");
		stopList.add("behind");
		stopList.add("being");
		stopList.add("believe");
		stopList.add("below");
		stopList.add("beside");
		stopList.add("besides");
		stopList.add("best");
		stopList.add("better");
		stopList.add("between");
		stopList.add("beyond");
		stopList.add("both");
		stopList.add("brief");
		stopList.add("but");
		stopList.add("by");
		stopList.add("c");
		stopList.add("came");
		stopList.add("can");
		stopList.add("cannot");
		stopList.add("cant");
		stopList.add("cause");
		stopList.add("causes");
		stopList.add("certain");
		stopList.add("certainly");
		stopList.add("changes");
		stopList.add("clearly");
		stopList.add("co");
		stopList.add("com");
		stopList.add("come");
		stopList.add("comes");
		stopList.add("concerning");
		stopList.add("consequently");
		stopList.add("consider");
		stopList.add("considering");
		stopList.add("contain");
		stopList.add("containing");
		stopList.add("contains");
		stopList.add("corresponding");
		stopList.add("could");
		stopList.add("course");
		stopList.add("currently");
		stopList.add("d");
		stopList.add("definitely");
		stopList.add("described");
		stopList.add("despite");
		stopList.add("did");
		stopList.add("different");
		stopList.add("do");
		stopList.add("does");
		stopList.add("doing");
		stopList.add("done");
		stopList.add("down");
		stopList.add("downwards");
		stopList.add("during");
		stopList.add("e");
		stopList.add("each");
		stopList.add("edu");
		stopList.add("eg");
		stopList.add("eight");
		stopList.add("either");
		stopList.add("else");
		stopList.add("elsewhere");
		stopList.add("enough");
		stopList.add("entirely");
		stopList.add("especially");
		stopList.add("et");
		stopList.add("etc");
		stopList.add("even");
		stopList.add("ever");
		stopList.add("every");
		stopList.add("everybody");
		stopList.add("everyone");
		stopList.add("everything");
		stopList.add("everywhere");
		stopList.add("ex");
		stopList.add("exactly");
		stopList.add("example");
		stopList.add("except");
		stopList.add("f");
		stopList.add("far");
		stopList.add("few");
		stopList.add("fifth");
		stopList.add("first");
		stopList.add("five");
		stopList.add("followed");
		stopList.add("following");
		stopList.add("follows");
		stopList.add("for");
		stopList.add("former");
		stopList.add("formerly");
		stopList.add("forth");
		stopList.add("four");
		stopList.add("from");
		stopList.add("further");
		stopList.add("furthermore");
		stopList.add("g");
		stopList.add("get");
		stopList.add("gets");
		stopList.add("getting");
		stopList.add("given");
		stopList.add("gives");
		stopList.add("go");
		stopList.add("goes");
		stopList.add("going");
		stopList.add("gone");
		stopList.add("got");
		stopList.add("gotten");
		stopList.add("greetings");
		stopList.add("h");
		stopList.add("had");
		stopList.add("happens");
		stopList.add("hardly");
		stopList.add("has");
		stopList.add("have");
		stopList.add("having");
		stopList.add("he");
		stopList.add("hello");
		stopList.add("help");
		stopList.add("hence");
		stopList.add("her");
		stopList.add("here");
		stopList.add("hereafter");
		stopList.add("hereby");
		stopList.add("herein");
		stopList.add("hereupon");
		stopList.add("hers");
		stopList.add("herself");
		stopList.add("hi");
		stopList.add("him");
		stopList.add("himself");
		stopList.add("his");
		stopList.add("hither");
		stopList.add("hopefully");
		stopList.add("how");
		stopList.add("howbeit");
		stopList.add("however");
		stopList.add("i");
		stopList.add("ie");
		stopList.add("if");
		stopList.add("ignored");
		stopList.add("immediate");
		stopList.add("in");
		stopList.add("inasmuch");
		stopList.add("inc");
		stopList.add("indeed");
		stopList.add("indicate");
		stopList.add("indicated");
		stopList.add("indicates");
		stopList.add("inner");
		stopList.add("insofar");
		stopList.add("instead");
		stopList.add("into");
		stopList.add("inward");
		stopList.add("is");
		stopList.add("it");
		stopList.add("its");
		stopList.add("itself");
		stopList.add("j");
		stopList.add("just");
		stopList.add("k");
		stopList.add("keep");
		stopList.add("keeps");
		stopList.add("kept");
		stopList.add("know");
		stopList.add("knows");
		stopList.add("known");
		stopList.add("l");
		stopList.add("last");
		stopList.add("lately");
		stopList.add("later");
		stopList.add("latter");
		stopList.add("latterly");
		stopList.add("least");
		stopList.add("less");
		stopList.add("lest");
		stopList.add("let");
		stopList.add("like");
		stopList.add("liked");
		stopList.add("likely");
		stopList.add("little");
		stopList.add("ll");//stopList.Addedtoavoidwordslikeyou'll,I'lletc.
		stopList.add("look");
		stopList.add("looking");
		stopList.add("looks");
		stopList.add("ltd");
		stopList.add("m");
		stopList.add("mainly");
		stopList.add("many");
		stopList.add("may");
		stopList.add("maybe");
		stopList.add("me");
		stopList.add("mean");
		stopList.add("meanwhile");
		stopList.add("merely");
		stopList.add("might");
		stopList.add("more");
		stopList.add("moreover");
		stopList.add("most");
		stopList.add("mostly");
		stopList.add("much");
		stopList.add("must");
		stopList.add("my");
		stopList.add("myself");
		stopList.add("n");
		stopList.add("name");
		stopList.add("namely");
		stopList.add("nd");
		stopList.add("near");
		stopList.add("nearly");
		stopList.add("necessary");
		stopList.add("need");
		stopList.add("needs");
		stopList.add("neither");
		stopList.add("never");
		stopList.add("nevertheless");
		stopList.add("new");
		stopList.add("next");
		stopList.add("nine");
		stopList.add("no");
		stopList.add("nobody");
		stopList.add("non");
		stopList.add("none");
		stopList.add("noone");
		stopList.add("nor");
		stopList.add("normally");
		stopList.add("not");
		stopList.add("nothing");
		stopList.add("novel");
		stopList.add("now");
		stopList.add("nowhere");
		stopList.add("o");
		stopList.add("obviously");
		stopList.add("of");
		stopList.add("off");
		stopList.add("often");
		stopList.add("oh");
		stopList.add("ok");
		stopList.add("okay");
		stopList.add("old");
		stopList.add("on");
		stopList.add("once");
		stopList.add("one");
		stopList.add("ones");
		stopList.add("only");
		stopList.add("onto");
		stopList.add("or");
		stopList.add("other");
		stopList.add("others");
		stopList.add("otherwise");
		stopList.add("ought");
		stopList.add("our");
		stopList.add("ours");
		stopList.add("ourselves");
		stopList.add("out");
		stopList.add("outside");
		stopList.add("over");
		stopList.add("overall");
		stopList.add("own");
		stopList.add("p");
		stopList.add("particular");
		stopList.add("particularly");
		stopList.add("per");
		stopList.add("perhaps");
		stopList.add("placed");
		stopList.add("please");
		stopList.add("plus");
		stopList.add("possible");
		stopList.add("presumably");
		stopList.add("probably");
		stopList.add("provides");
		stopList.add("q");
		stopList.add("que");
		stopList.add("quite");
		stopList.add("qv");
		stopList.add("r");
		stopList.add("rather");
		stopList.add("rd");
		stopList.add("re");
		stopList.add("really");
		stopList.add("reasonably");
		stopList.add("regarding");
		stopList.add("regardless");
		stopList.add("regards");
		stopList.add("relatively");
		stopList.add("respectively");
		stopList.add("right");
		stopList.add("s");
		stopList.add("said");
		stopList.add("same");
		stopList.add("saw");
		stopList.add("say");
		stopList.add("saying");
		stopList.add("says");
		stopList.add("second");
		stopList.add("secondly");
		stopList.add("see");
		stopList.add("seeing");
		stopList.add("seem");
		stopList.add("seemed");
		stopList.add("seeming");
		stopList.add("seems");
		stopList.add("seen");
		stopList.add("self");
		stopList.add("selves");
		stopList.add("sensible");
		stopList.add("sent");
		stopList.add("serious");
		stopList.add("seriously");
		stopList.add("seven");
		stopList.add("several");
		stopList.add("shall");
		stopList.add("she");
		stopList.add("should");
		stopList.add("since");
		stopList.add("six");
		stopList.add("so");
		stopList.add("some");
		stopList.add("somebody");
		stopList.add("somehow");
		stopList.add("someone");
		stopList.add("something");
		stopList.add("sometime");
		stopList.add("sometimes");
		stopList.add("somewhat");
		stopList.add("somewhere");
		stopList.add("soon");
		stopList.add("sorry");
		stopList.add("specified");
		stopList.add("specify");
		stopList.add("specifying");
		stopList.add("still");
		stopList.add("sub");
		stopList.add("such");
		stopList.add("sup");
		stopList.add("sure");
		stopList.add("t");
		stopList.add("take");
		stopList.add("taken");
		stopList.add("tell");
		stopList.add("tends");
		stopList.add("th");
		stopList.add("than");
		stopList.add("thank");
		stopList.add("thanks");
		stopList.add("thanx");
		stopList.add("that");
		stopList.add("thats");
		stopList.add("the");
		stopList.add("their");
		stopList.add("theirs");
		stopList.add("them");
		stopList.add("themselves");
		stopList.add("then");
		stopList.add("thence");
		stopList.add("there");
		stopList.add("thereafter");
		stopList.add("thereby");
		stopList.add("therefore");
		stopList.add("therein");
		stopList.add("theres");
		stopList.add("thereupon");
		stopList.add("these");
		stopList.add("they");
		stopList.add("think");
		stopList.add("third");
		stopList.add("this");
		stopList.add("thorough");
		stopList.add("thoroughly");
		stopList.add("those");
		stopList.add("though");
		stopList.add("three");
		stopList.add("through");
		stopList.add("throughout");
		stopList.add("thru");
		stopList.add("thus");
		stopList.add("to");
		stopList.add("together");
		stopList.add("too");
		stopList.add("took");
		stopList.add("toward");
		stopList.add("towards");
		stopList.add("tried");
		stopList.add("tries");
		stopList.add("truly");
		stopList.add("try");
		stopList.add("trying");
		stopList.add("twice");
		stopList.add("two");
		stopList.add("u");
		stopList.add("un");
		stopList.add("under");
		stopList.add("unfortunately");
		stopList.add("unless");
		stopList.add("unlikely");
		stopList.add("until");
		stopList.add("unto");
		stopList.add("up");
		stopList.add("upon");
		stopList.add("us");
		stopList.add("use");
		stopList.add("used");
		stopList.add("useful");
		stopList.add("uses");
		stopList.add("using");
		stopList.add("usually");
		stopList.add("uucp");
		stopList.add("v");
		stopList.add("value");
		stopList.add("various");
		stopList.add("ve");//stopList.Added to avoid words like I've, you've etc.
		stopList.add("very");
		stopList.add("via");
		stopList.add("viz");
		stopList.add("vs");
		stopList.add("w");
		stopList.add("want");
		stopList.add("wants");
		stopList.add("was");
		stopList.add("way");
		stopList.add("we");
		stopList.add("welcome");
		stopList.add("well");
		stopList.add("went");
		stopList.add("were");
		stopList.add("what");
		stopList.add("whatever");
		stopList.add("when");
		stopList.add("whence");
		stopList.add("whenever");
		stopList.add("where");
		stopList.add("whereafter");
		stopList.add("whereas");
		stopList.add("whereby");
		stopList.add("wherein");
		stopList.add("whereupon");
		stopList.add("wherever");
		stopList.add("whether");
		stopList.add("which");
		stopList.add("while");
		stopList.add("whither");
		stopList.add("who");
		stopList.add("whoever");
		stopList.add("whole");
		stopList.add("whom");
		stopList.add("whose");
		stopList.add("why");
		stopList.add("will");
		stopList.add("willing");
		stopList.add("wish");
		stopList.add("with");
		stopList.add("within");
		stopList.add("without");
		stopList.add("wonder");
		stopList.add("would");
		stopList.add("would");
		stopList.add("x");
		stopList.add("y");
		stopList.add("yes");
		stopList.add("yet");
		stopList.add("you");
		stopList.add("your");
		stopList.add("yours");
		stopList.add("yourself");
		stopList.add("yourselves");
		stopList.add("z");
		stopList.add("zero");
		stopList.add(" ");
		
		String stopWord = "";
		for(int i=0;i<querywords.size();i++)
		{
			stopWord = querywords.get(i).originalText();
			if (stopList.contains(stopWord.toLowerCase()))
				querywords.remove(i--);
		}

	}
	//*******************************************************************
	public String[] removeStopWords(String[] querywords )
	{
		ArrayList<String> stopList = new ArrayList<String>();
		ArrayList<String> words = new ArrayList<String>();
		stopList.add("a");
		stopList.add("able");
		stopList.add("about");
		stopList.add("above");
		stopList.add("according");
		stopList.add("accordingly");
		stopList.add("across");
		stopList.add("actually");
		stopList.add("after");
		stopList.add("afterwards");
		stopList.add("again");
		stopList.add("against");
		stopList.add("all");
		stopList.add("allow");
		stopList.add("allows");
		stopList.add("almost");
		stopList.add("alone");
		stopList.add("along");
		stopList.add("already");
		stopList.add("also");
		stopList.add("although");
		stopList.add("always");
		stopList.add("am");
		stopList.add("among");
		stopList.add("amongst");
		stopList.add("an");
		stopList.add("and");
		stopList.add("another");
		stopList.add("any");
		stopList.add("anybody");
		stopList.add("anyhow");
		stopList.add("anyone");
		stopList.add("anything");
		stopList.add("anyway");
		stopList.add("anyways");
		stopList.add("anywhere");
		stopList.add("apart");
		stopList.add("appear");
		stopList.add("appreciate");
		stopList.add("appropriate");
		stopList.add("are");
		stopList.add("around");
		stopList.add("as");
		stopList.add("aside");
		stopList.add("ask");
		stopList.add("asking");
		stopList.add("associated");
		stopList.add("at");
		stopList.add("available");
		stopList.add("away");
		stopList.add("awfully");
		stopList.add("b");
		stopList.add("be");
		stopList.add("became");
		stopList.add("because");
		stopList.add("become");
		stopList.add("becomes");
		stopList.add("becoming");
		stopList.add("been");
		stopList.add("before");
		stopList.add("beforehand");
		stopList.add("behind");
		stopList.add("being");
		stopList.add("believe");
		stopList.add("below");
		stopList.add("beside");
		stopList.add("besides");
		stopList.add("best");
		stopList.add("better");
		stopList.add("between");
		stopList.add("beyond");
		stopList.add("both");
		stopList.add("brief");
		stopList.add("but");
		stopList.add("by");
		stopList.add("c");
		stopList.add("came");
		stopList.add("can");
		stopList.add("cannot");
		stopList.add("cant");
		stopList.add("cause");
		stopList.add("causes");
		stopList.add("certain");
		stopList.add("certainly");
		stopList.add("changes");
		stopList.add("clearly");
		stopList.add("co");
		stopList.add("com");
		stopList.add("come");
		stopList.add("comes");
		stopList.add("concerning");
		stopList.add("consequently");
		stopList.add("consider");
		stopList.add("considering");
		stopList.add("contain");
		stopList.add("containing");
		stopList.add("contains");
		stopList.add("corresponding");
		stopList.add("could");
		stopList.add("course");
		stopList.add("currently");
		stopList.add("d");
		stopList.add("definitely");
		stopList.add("described");
		stopList.add("despite");
		stopList.add("did");
		stopList.add("different");
		stopList.add("do");
		stopList.add("does");
		stopList.add("doing");
		stopList.add("done");
		stopList.add("down");
		stopList.add("downwards");
		stopList.add("during");
		stopList.add("e");
		stopList.add("each");
		stopList.add("edu");
		stopList.add("eg");
		stopList.add("eight");
		stopList.add("either");
		stopList.add("else");
		stopList.add("elsewhere");
		stopList.add("enough");
		stopList.add("entirely");
		stopList.add("especially");
		stopList.add("et");
		stopList.add("etc");
		stopList.add("even");
		stopList.add("ever");
		stopList.add("every");
		stopList.add("everybody");
		stopList.add("everyone");
		stopList.add("everything");
		stopList.add("everywhere");
		stopList.add("ex");
		stopList.add("exactly");
		stopList.add("example");
		stopList.add("except");
		stopList.add("f");
		stopList.add("far");
		stopList.add("few");
		stopList.add("fifth");
		stopList.add("first");
		stopList.add("five");
		stopList.add("followed");
		stopList.add("following");
		stopList.add("follows");
		stopList.add("for");
		stopList.add("former");
		stopList.add("formerly");
		stopList.add("forth");
		stopList.add("four");
		stopList.add("from");
		stopList.add("further");
		stopList.add("furthermore");
		stopList.add("g");
		stopList.add("get");
		stopList.add("gets");
		stopList.add("getting");
		stopList.add("given");
		stopList.add("gives");
		stopList.add("go");
		stopList.add("goes");
		stopList.add("going");
		stopList.add("gone");
		stopList.add("got");
		stopList.add("gotten");
		stopList.add("greetings");
		stopList.add("h");
		stopList.add("had");
		stopList.add("happens");
		stopList.add("hardly");
		stopList.add("has");
		stopList.add("have");
		stopList.add("having");
		stopList.add("he");
		stopList.add("hello");
		stopList.add("help");
		stopList.add("hence");
		stopList.add("her");
		stopList.add("here");
		stopList.add("hereafter");
		stopList.add("hereby");
		stopList.add("herein");
		stopList.add("hereupon");
		stopList.add("hers");
		stopList.add("herself");
		stopList.add("hi");
		stopList.add("him");
		stopList.add("himself");
		stopList.add("his");
		stopList.add("hither");
		stopList.add("hopefully");
		stopList.add("how");
		stopList.add("howbeit");
		stopList.add("however");
		stopList.add("i");
		stopList.add("ie");
		stopList.add("if");
		stopList.add("ignored");
		stopList.add("immediate");
		stopList.add("in");
		stopList.add("inasmuch");
		stopList.add("inc");
		stopList.add("indeed");
		stopList.add("indicate");
		stopList.add("indicated");
		stopList.add("indicates");
		stopList.add("inner");
		stopList.add("insofar");
		stopList.add("instead");
		stopList.add("into");
		stopList.add("inward");
		stopList.add("is");
		stopList.add("it");
		stopList.add("its");
		stopList.add("itself");
		stopList.add("j");
		stopList.add("just");
		stopList.add("k");
		stopList.add("keep");
		stopList.add("keeps");
		stopList.add("kept");
		stopList.add("know");
		stopList.add("knows");
		stopList.add("known");
		stopList.add("l");
		stopList.add("last");
		stopList.add("lately");
		stopList.add("later");
		stopList.add("latter");
		stopList.add("latterly");
		stopList.add("least");
		stopList.add("less");
		stopList.add("lest");
		stopList.add("let");
		stopList.add("like");
		stopList.add("liked");
		stopList.add("likely");
		stopList.add("little");
		stopList.add("ll");//stopList.Addedtoavoidwordslikeyou'll,I'lletc.
		stopList.add("look");
		stopList.add("looking");
		stopList.add("looks");
		stopList.add("ltd");
		stopList.add("m");
		stopList.add("mainly");
		stopList.add("many");
		stopList.add("may");
		stopList.add("maybe");
		stopList.add("me");
		stopList.add("mean");
		stopList.add("meanwhile");
		stopList.add("merely");
		stopList.add("might");
		stopList.add("more");
		stopList.add("moreover");
		stopList.add("most");
		stopList.add("mostly");
		stopList.add("much");
		stopList.add("must");
		stopList.add("my");
		stopList.add("myself");
		stopList.add("n");
		stopList.add("name");
		stopList.add("namely");
		stopList.add("nd");
		stopList.add("near");
		stopList.add("nearly");
		stopList.add("necessary");
		stopList.add("need");
		stopList.add("needs");
		stopList.add("neither");
		stopList.add("never");
		stopList.add("nevertheless");
		stopList.add("new");
		stopList.add("next");
		stopList.add("nine");
		stopList.add("no");
		stopList.add("nobody");
		stopList.add("non");
		stopList.add("none");
		stopList.add("noone");
		stopList.add("nor");
		stopList.add("normally");
		stopList.add("not");
		stopList.add("nothing");
		stopList.add("novel");
		stopList.add("now");
		stopList.add("nowhere");
		stopList.add("o");
		stopList.add("obviously");
		stopList.add("of");
		stopList.add("off");
		stopList.add("often");
		stopList.add("oh");
		stopList.add("ok");
		stopList.add("okay");
		stopList.add("old");
		stopList.add("on");
		stopList.add("once");
		stopList.add("one");
		stopList.add("ones");
		stopList.add("only");
		stopList.add("onto");
		stopList.add("or");
		stopList.add("other");
		stopList.add("others");
		stopList.add("otherwise");
		stopList.add("ought");
		stopList.add("our");
		stopList.add("ours");
		stopList.add("ourselves");
		stopList.add("out");
		stopList.add("outside");
		stopList.add("over");
		stopList.add("overall");
		stopList.add("own");
		stopList.add("p");
		stopList.add("particular");
		stopList.add("particularly");
		stopList.add("per");
		stopList.add("perhaps");
		stopList.add("placed");
		stopList.add("please");
		stopList.add("plus");
		stopList.add("possible");
		stopList.add("presumably");
		stopList.add("probably");
		stopList.add("provides");
		stopList.add("q");
		stopList.add("que");
		stopList.add("quite");
		stopList.add("qv");
		stopList.add("r");
		stopList.add("rather");
		stopList.add("rd");
		stopList.add("re");
		stopList.add("really");
		stopList.add("reasonably");
		stopList.add("regarding");
		stopList.add("regardless");
		stopList.add("regards");
		stopList.add("relatively");
		stopList.add("respectively");
		stopList.add("right");
		stopList.add("s");
		stopList.add("said");
		stopList.add("same");
		stopList.add("saw");
		stopList.add("say");
		stopList.add("saying");
		stopList.add("says");
		stopList.add("second");
		stopList.add("secondly");
		stopList.add("see");
		stopList.add("seeing");
		stopList.add("seem");
		stopList.add("seemed");
		stopList.add("seeming");
		stopList.add("seems");
		stopList.add("seen");
		stopList.add("self");
		stopList.add("selves");
		stopList.add("sensible");
		stopList.add("sent");
		stopList.add("serious");
		stopList.add("seriously");
		stopList.add("seven");
		stopList.add("several");
		stopList.add("shall");
		stopList.add("she");
		stopList.add("should");
		stopList.add("since");
		stopList.add("six");
		stopList.add("so");
		stopList.add("some");
		stopList.add("somebody");
		stopList.add("somehow");
		stopList.add("someone");
		stopList.add("something");
		stopList.add("sometime");
		stopList.add("sometimes");
		stopList.add("somewhat");
		stopList.add("somewhere");
		stopList.add("soon");
		stopList.add("sorry");
		stopList.add("specified");
		stopList.add("specify");
		stopList.add("specifying");
		stopList.add("still");
		stopList.add("sub");
		stopList.add("such");
		stopList.add("sup");
		stopList.add("sure");
		stopList.add("t");
		stopList.add("take");
		stopList.add("taken");
		stopList.add("tell");
		stopList.add("tends");
		stopList.add("th");
		stopList.add("than");
		stopList.add("thank");
		stopList.add("thanks");
		stopList.add("thanx");
		stopList.add("that");
		stopList.add("thats");
		stopList.add("the");
		stopList.add("their");
		stopList.add("theirs");
		stopList.add("them");
		stopList.add("themselves");
		stopList.add("then");
		stopList.add("thence");
		stopList.add("there");
		stopList.add("thereafter");
		stopList.add("thereby");
		stopList.add("therefore");
		stopList.add("therein");
		stopList.add("theres");
		stopList.add("thereupon");
		stopList.add("these");
		stopList.add("they");
		stopList.add("think");
		stopList.add("third");
		stopList.add("this");
		stopList.add("thorough");
		stopList.add("thoroughly");
		stopList.add("those");
		stopList.add("though");
		stopList.add("three");
		stopList.add("through");
		stopList.add("throughout");
		stopList.add("thru");
		stopList.add("thus");
		stopList.add("to");
		stopList.add("together");
		stopList.add("too");
		stopList.add("took");
		stopList.add("toward");
		stopList.add("towards");
		stopList.add("tried");
		stopList.add("tries");
		stopList.add("truly");
		stopList.add("try");
		stopList.add("trying");
		stopList.add("twice");
		stopList.add("two");
		stopList.add("u");
		stopList.add("un");
		stopList.add("under");
		stopList.add("unfortunately");
		stopList.add("unless");
		stopList.add("unlikely");
		stopList.add("until");
		stopList.add("unto");
		stopList.add("up");
		stopList.add("upon");
		stopList.add("us");
		stopList.add("use");
		stopList.add("used");
		stopList.add("useful");
		stopList.add("uses");
		stopList.add("using");
		stopList.add("usually");
		stopList.add("uucp");
		stopList.add("v");
		stopList.add("value");
		stopList.add("various");
		stopList.add("ve");//stopList.Added to avoid words like I've, you've etc.
		stopList.add("very");
		stopList.add("via");
		stopList.add("viz");
		stopList.add("vs");
		stopList.add("w");
		stopList.add("want");
		stopList.add("wants");
		stopList.add("was");
		stopList.add("way");
		stopList.add("we");
		stopList.add("welcome");
		stopList.add("well");
		stopList.add("went");
		stopList.add("were");
		stopList.add("what");
		stopList.add("whatever");
		stopList.add("when");
		stopList.add("whence");
		stopList.add("whenever");
		stopList.add("where");
		stopList.add("whereafter");
		stopList.add("whereas");
		stopList.add("whereby");
		stopList.add("wherein");
		stopList.add("whereupon");
		stopList.add("wherever");
		stopList.add("whether");
		stopList.add("which");
		stopList.add("while");
		stopList.add("whither");
		stopList.add("who");
		stopList.add("whoever");
		stopList.add("whole");
		stopList.add("whom");
		stopList.add("whose");
		stopList.add("why");
		stopList.add("will");
		stopList.add("willing");
		stopList.add("wish");
		stopList.add("with");
		stopList.add("within");
		stopList.add("without");
		stopList.add("wonder");
		stopList.add("would");
		stopList.add("would");
		stopList.add("x");
		stopList.add("y");
		stopList.add("yes");
		stopList.add("yet");
		stopList.add("you");
		stopList.add("your");
		stopList.add("yours");
		stopList.add("yourself");
		stopList.add("yourselves");
		stopList.add("z");
		stopList.add("zero");
		//stopList.add(" ");
		
		String stopWord = "";
		for(int i=0;i<querywords.length;i++)
		{
			stopWord = querywords[i];
			if (!stopList.contains(stopWord.toLowerCase()))
				words.add(querywords[i]);
		}
		String [] wds = (String[])words.toArray();
		
		return wds;

	}
	//******************************************************************
	public String[] removeStopWords(String str)
      {
		str = str.replace("?"," ");
		String[] tk = str.split(" ");
		return removeStopWords(tk);
      }
}
