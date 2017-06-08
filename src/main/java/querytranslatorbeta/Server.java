package querytranslatorbeta;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import org.semanticweb.owlapi.model.OWLClass;

import javax.swing.JScrollPane;;

public class Server {

	private JFrame frame;
	private JTextArea txtAlors;
	static String clientMessage;

	/**
	 * Launch the application.
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException  {
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server window = new Server();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*/
		
		Server window = new Server();
		window.frame.setVisible(true);
		
		  ServerSocket listener = new ServerSocket(6000);
		  //JOptionPane.showMessageDialog(null,"New client");
	        try {
	            while (true) {
	                Socket socket = listener.accept();
	                //txtAlors.setText(txtAlors.getText()+"\nNew client");
//	               JOptionPane.showMessageDialog(null,"New client");
	                String toCient = window.readText(socket);
	                
	                try {
	                    /*PrintWriter out =
	                        new PrintWriter(socket.getOutputStream(), true);
	                    out.println(str);*/
	                	window.sendText(toCient,socket);
	                } finally {
	                    socket.close();
	                }
	            }
	        }
	        finally {
	            listener.close();
	        }
	       
	        
	}

	
	
	
	
	private String readText(Socket s) throws IOException, ClassNotFoundException {
		txtAlors.setText("Waiting for Clients");
		ObjectInputStream ois;
//		ObjectInputStream input =
//		        new BufferedReader(new InputStreamReader(s.getInputStream()));
//		    String answer = input.readLine();
//		    JOptionPane.showMessageDialog(null,null, answer);
		
		ois = new ObjectInputStream(s.getInputStream());
		clientMessage = (String) ois.readObject();
//		JOptionPane.showMessageDialog(null,null, "I'm the server; "+inputMessage);
		txtAlors.setText(txtAlors.getText()+"\n----------------------------------------");
		txtAlors.setText(txtAlors.getText()+"\nFrom Client" +clientMessage);
		
		//start Query Handling
		//1 get the user query
		String userQuery = clientMessage.trim();
		
		//get words and relations using stanford parser
		// remove stop words
		// get stems
		UserQueryProcessor pocessor = new UserQueryProcessor();
		pocessor.processQuery(userQuery,txtAlors);
		
		String [] Qwords = pocessor.getUserQueryWords();
		String [] Qstems = pocessor.getUserQueryStems();
		String [] Qentities = pocessor.getEntities();
		
		
		//for(int i=0;i<words.length;i++)
			//txtSPARQLQuery.setText(txtSPARQLQuery.getText()+" "+words[i]);
		
		//get classes and properties from the ontology
		OntologyReader ontReader = new OntologyReader();
		
		ontReader.readOntology();
		//matching process
		ontReader.match(Qwords,Qstems,Qentities);
		
		txtAlors.setText(txtAlors.getText()+"\n----------------------------------------");
		txtAlors.setText(txtAlors.getText()+"\nMatched Items");
		txtAlors.setText(txtAlors.getText()+ontReader.getMatchedItems());
		//User Feedback
		
		
		//build SPARQL query
		
		
		return java.util.Arrays.toString(Qentities);
		
	}

	private void sendText(String text, Socket socket) throws IOException {
		//JOptionPane.showMessageDialog(null,"sending text to client..");
		OutputStream output = socket.getOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(output);
		

        oos.writeObject(text);
        oos.flush();

	}
	
	
	
	
	
	
	/**
	 * Create the application.
	 */
	public Server() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Processing Interface");
		
		txtAlors = new JTextArea();
		txtAlors.setText("Waiting for Clients");
		txtAlors.setBounds(69, 29, 308, 176);
		
		JScrollPane pane = new JScrollPane(txtAlors);
		pane.setBounds(69, 29, 308, 176);
		frame.getContentPane().add(pane);
		txtAlors.setColumns(10);
	}
}
