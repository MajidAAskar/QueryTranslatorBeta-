package querytranslatorbeta;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import javax.swing.JFrame;



import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.OutputStream;
import javax.swing.JOptionPane;
import javax.swing.JTextField;



import javax.swing.JButton;

public class Client {

	private JFrame frame;
	private JTextField txtNlQuery;
	private JTextField txtSparqlQuery;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)  
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					Client window = new Client();
					window.frame.setVisible(true);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	
	/*	String serverAddress = JOptionPane.showInputDialog(
	            "Enter IP Address of a machine that is\n" +
	            "running the date service on port 9090:");
		*/
		
	        
	        
	        /*
	        JOptionPane.showMessageDialog(null,null, answer);
	        System.exit(0);
	        
	        */
	}

	/**
	 * Create the application.
	 */
	
	
	
	
	
	private static String readText(Socket s) throws IOException, ClassNotFoundException {
		ObjectInputStream ois;
//		ObjectInputStream input =
//		        new BufferedReader(new InputStreamReader(s.getInputStream()));
//		    String answer = input.readLine();
//		    JOptionPane.showMessageDialog(null,null, answer);
		
		ois = new ObjectInputStream(s.getInputStream());
		String inputMessage = (String) ois.readObject();
		return inputMessage;
//		JOptionPane.showMessageDialog(null,null, "I'm the client; "+inputMessage);
        //JOptionPane.showMessageDialog(null,inputMessage);
		
	}
	
	private static void sendText(String text, Socket socket) throws IOException {
		JOptionPane.showMessageDialog(null,"sending text to server..");
	OutputStream output = socket.getOutputStream();
	
		ObjectOutputStream oos = new ObjectOutputStream(output);
		

        oos.writeObject(text);
        oos.flush();

	}
	
	
	
	
	
	
	
	
	
	
	
	public Client() {
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
		frame.setTitle("User Interface");
		
		txtNlQuery = new JTextField();
		txtNlQuery.setText("NL Query");
		txtNlQuery.setBounds(97, 38, 300, 20);
		frame.getContentPane().add(txtNlQuery);
		txtNlQuery.setColumns(10);
		
		JButton btnTranslate = new JButton("Translate");
		btnTranslate.setBounds(127, 92, 89, 23);
		btnTranslate.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try
				{
				Socket s = new Socket("Localhost", 6000);
		        sendText(txtNlQuery.getText(), s);
		        
		        txtSparqlQuery.setText(readText(s));
		        
		        /*Scanner input =
		            new Scanner(new InputStreamReader(s.getInputStream()));
		        String answer = input.nextLine();
		        JOptionPane.showMessageDialog(null,answer); //in feld server*/
				}
				catch(Exception ex)
				{
				
				}
			}
		});
		frame.getContentPane().add(btnTranslate);
		
		txtSparqlQuery = new JTextField();
		txtSparqlQuery.setText("SPARQL Query");
		txtSparqlQuery.setBounds(97, 156, 300, 20);
		frame.getContentPane().add(txtSparqlQuery);
		txtSparqlQuery.setColumns(10);
	}
}
