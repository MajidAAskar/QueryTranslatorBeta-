package querytranslatorbeta;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.ScrollPaneConstants;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JList;

public class Client extends JFrame {

	private JPanel MatchedItemsPanle;
	private JTextField txtNlQuery;
	private JTextField txtSparqlQuery;
	private JScrollPane scrollPane;
	private JCheckBox[] matchCheck;
	private JList<String> FromOntology;
	private DefaultListModel<String> model = new DefaultListModel<String>();
	private JScrollPane scrollPane_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client frame = new Client();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Client() {
		getContentPane().setBackground(new Color(240, 240, 240));
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//contentPane = getContentPane().get;
		getContentPane().setLayout(null);
		setTitle("User Interface");

		txtNlQuery = new JTextField();
		txtNlQuery.setText("NL Query");
		txtNlQuery.setBounds(28, 11, 300, 20);
		getContentPane().add(txtNlQuery);
		txtNlQuery.setColumns(10);

		JButton btnTranslate = new JButton("Translate");
		btnTranslate.setBounds(338, 10, 89, 23);
		btnTranslate.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try
				{
					Socket s = new Socket("Localhost", 6000);
					//send the text to the client
					sendText(txtNlQuery.getText(), s);

					//get the matched items to have the user feedback on them
					//readText(s)
					//fill the FromOntology List
					UpdateFromOntologyList(readText(s));
					UpdateFromOntologyList(readText(s));
					UpdateFromOntologyList(readText(s));
					
					updateUI(readText(s));

					//finally present the SPARQL query
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
		getContentPane().add(btnTranslate);

		txtSparqlQuery = new JTextField();
		txtSparqlQuery.setText("SPARQL Query");
		txtSparqlQuery.setBounds(28, 214, 300, 20);
		getContentPane().add(txtSparqlQuery);
		txtSparqlQuery.setColumns(10);

		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(38, 52, 209, 151);
		getContentPane().add(scrollPane);

		MatchedItemsPanle = new JPanel();
		MatchedItemsPanle.setBorder(null);
		MatchedItemsPanle.setBackground(Color.LIGHT_GRAY);
		scrollPane.setViewportView(MatchedItemsPanle);
		MatchedItemsPanle.setLayout(new GridLayout(0, 1, 0, 0));
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(283, 52, 129, 117);
		getContentPane().add(scrollPane_1);

		FromOntology = new JList<String>(model);
		scrollPane_1.setViewportView(FromOntology);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnAdd.setBounds(335, 180, 89, 23);
		getContentPane().add(btnAdd);
		//scrollPane.add(MatchedItemsPanle);
	}
	///////**********************************************************************************
	private static String readText(Socket s) throws IOException, ClassNotFoundException 
	{
		ObjectInputStream ois;		
		ois = new ObjectInputStream(s.getInputStream());
		String inputMessage = (String) ois.readObject();
		return inputMessage;
	}
	///////**********************************************************************************
	private static void sendText(String text, Socket socket) throws IOException 
	{
		JOptionPane.showMessageDialog(null,"sending text to server..");
		OutputStream output = socket.getOutputStream();

		ObjectOutputStream oos = new ObjectOutputStream(output);
		oos.writeObject(text);
		oos.flush();
	}
	///////**********************************************************************************
	private static ArrayList<String> readArrayList(Socket s) throws IOException, ClassNotFoundException 
	{
		ObjectInputStream ois;		
		ois = new ObjectInputStream(s.getInputStream());
		ArrayList<String> inputMessage = (ArrayList) ois.readObject();
		return inputMessage;
	}
	///////**********************************************************************************
	private void updateUI(ArrayList<String> items)  
	{
		JCheckBox[] matchCheck = new JCheckBox[items.size()];
		for(int i=0;i<items.size();i++)
		{
			matchCheck[i] = new JCheckBox(items.get(i));
			this.MatchedItemsPanle.add(matchCheck[i]);
		}
		this.MatchedItemsPanle.revalidate();
		this.MatchedItemsPanle.repaint();
		this.scrollPane.revalidate();
		this.scrollPane.repaint();
		//scrollPane
		validate();
	}
	///////**********************************************************************************
	private void updateUI(String items)  
	{
		String[] itemsArr = items.split("\n");
		JCheckBox[] matchCheck = new JCheckBox[itemsArr.length-3];
		for(int i=0;i<itemsArr.length;i++)
		{
			if(!itemsArr[i].startsWith("*"))
			{
				matchCheck[i] = new JCheckBox(itemsArr[i]);
				this.MatchedItemsPanle.add(matchCheck[i]);
			}
		}
		this.MatchedItemsPanle.revalidate();
		this.MatchedItemsPanle.repaint();
		this.scrollPane.revalidate();
		this.scrollPane.repaint();
		//scrollPane
		validate();
	}
	///////**********************************************************************************
	private void UpdateFromOntologyList(String items)  
	{
		String[] itemsArr = items.split("\n");
		for(int i=0;i<itemsArr.length;i++)
			model.addElement(itemsArr[i]);
	}
}