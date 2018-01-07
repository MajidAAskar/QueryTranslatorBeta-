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
import javax.swing.JLabel;
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
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JFileChooser;

public class Client extends JFrame {

	private JPanel MatchedItemsPanle;
	private JTextField txtNlQuery;
	private JTextArea txtSparqlQuery;
	private JScrollPane scrollPane;
	private JCheckBox[] matchCheck;
	private ArrayList<JCheckBox> userfeedbackCheck;
	private JList<String> FromOntology;
	private DefaultListModel<String> model = new DefaultListModel<String>();
	private JCheckBox chckbxReadUpdate;
	//private boolean atLeastOneItemIsCkecked = false;
	private Socket s;
	private JScrollPane scrollPane_1;
	private int startOfObjectPropertiy;
	private int startOfdataPropertiy;
	private JScrollPane scrollPane_2;

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
		setBounds(100, 100, 600, 645);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//contentPane = getContentPane().get;
		getContentPane().setLayout(null);
		setTitle("User Interface");

		txtNlQuery = new JTextField();
		txtNlQuery.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtNlQuery.setText("NL Query");
		txtNlQuery.setBounds(28, 11, 406, 34);
		getContentPane().add(txtNlQuery);
		txtNlQuery.setColumns(10);
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(21, 298, 553, 298);
		getContentPane().add(scrollPane_2);

		txtSparqlQuery = new JTextArea();
		txtSparqlQuery.setForeground(Color.BLUE);
		txtSparqlQuery.setFont(new Font("Century", Font.BOLD, 16));
		scrollPane_2.setViewportView(txtSparqlQuery);
		txtSparqlQuery.setWrapStyleWord(true);
		
		JButton btnTranslate = new JButton("Translate");
		btnTranslate.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnTranslate.setBounds(444, 11, 130, 35);
		btnTranslate.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try
				{
					MatchedItemsPanle.removeAll();
					MatchedItemsPanle.revalidate();
					MatchedItemsPanle.repaint();
					scrollPane.revalidate();
					scrollPane.repaint();
					//scrollPane
					validate();
					userfeedbackCheck = new ArrayList<JCheckBox>();
					s = new Socket("Localhost", 6000);
					//send the text to the client
					sendText(txtNlQuery.getText(), s);
					
					if(chckbxReadUpdate.isSelected())
						sendText("true", s);
					else
						sendText("false", s);
					//get the matched items to have the user feedback on them
					//readText(s)
					//fill the FromOntology List
					//Add Classes to the list
					UpdateFromOntologyList(readText(s));
					startOfObjectPropertiy = model.size();

					//Add ObjectProperties to the list
					//UpdateFromOntologyList(readText(s));
					startOfdataPropertiy = model.size();

					//Add DataProperties to the list
					//UpdateFromOntologyList(readText(s));

					//Add Matched Items to the MatchedItemsPanle
					updateUI(readText(s));


					//finally present the SPARQL query
					//txtSparqlQuery.setText(readText(s));

					/*Scanner input =
		            new Scanner(new InputStreamReader(s.getInputStream()));
		        String answer = input.nextLine();
		        JOptionPane.showMessageDialog(null,answer); //in feld server*/
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null,ex.getMessage(),"From client to server",1);
				}
			}
		});
		getContentPane().add(btnTranslate);

		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(324, 98, 209, 151);
		getContentPane().add(scrollPane);

		MatchedItemsPanle = new JPanel();
		MatchedItemsPanle.setBorder(null);
		MatchedItemsPanle.setBackground(Color.LIGHT_GRAY);
		scrollPane.setViewportView(MatchedItemsPanle);
		MatchedItemsPanle.setLayout(new GridLayout(0, 1, 0, 0));

		JButton btnAdd = new JButton(">>");
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				int index = FromOntology.getSelectedIndex();
				if(index!=-1)
				{
					//atLeastOneItemIsCkecked = true;
					userfeedbackCheck.add(new JCheckBox(FromOntology.getSelectedValue()));
					userfeedbackCheck.get(userfeedbackCheck.size()-1).setSelected(true);
					MatchedItemsPanle.add(userfeedbackCheck.get(userfeedbackCheck.size()-1));
					FromOntology.clearSelection();
					model.remove(index);
				}
				MatchedItemsPanle.revalidate();
				/*MatchedItemsPanle.repaint();
				scrollPane.revalidate();
				scrollPane.repaint();
				//scrollPane
				validate();*/
			}
		});
		btnAdd.setBounds(233, 126, 61, 29);
		getContentPane().add(btnAdd);

		JButton btnSubmitFeedback = new JButton("Submit Feedback");
		btnSubmitFeedback.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnSubmitFeedback.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				String feedbackSelectedItems="";
				if(matchCheck!=null)
					for(int i=0;i<matchCheck.length;i++)
					{
						if(matchCheck[i].isSelected())
							feedbackSelectedItems+=matchCheck[i].getText().substring(matchCheck[i].getText().indexOf('=')+2)+" ";
					}
				
				if(feedbackSelectedItems.equals(""))
				{
					JOptionPane.showMessageDialog(null, "No items are selected, pleast select at least one","Client select items",1);
					return;
				}

				for(int i=0;i<userfeedbackCheck.size();i++)
				{
					if(userfeedbackCheck.get(i).isSelected())
						feedbackSelectedItems+=userfeedbackCheck.get(i).getText()+" ";
				}
				JOptionPane.showMessageDialog(null, feedbackSelectedItems,"Client select items",1);
				//atLeastOneItemIsCkecked = false;
				sendText(feedbackSelectedItems,s);
				//finally present the SPARQL query
				txtSparqlQuery.setText(readText(s));
			}
		});

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(28, 98, 179, 151);
		getContentPane().add(scrollPane_1);

		FromOntology = new JList<String>(model);
		scrollPane_1.setViewportView(FromOntology);
		btnSubmitFeedback.setBounds(176, 253, 165, 34);
		getContentPane().add(btnSubmitFeedback);
		
		chckbxReadUpdate = new JCheckBox("Read / Update Ontology");
		chckbxReadUpdate.setFont(new Font("Tahoma", Font.PLAIN, 16));
		chckbxReadUpdate.setBounds(28, 52, 226, 23);
		getContentPane().add(chckbxReadUpdate);
		
		JButton btnTranslateFile = new JButton("Translate File");
		btnTranslateFile.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				//open the file containing the queries to be executed
				JFileChooser  openQueryFile = new JFileChooser();
				int response = openQueryFile.showOpenDialog(null);
				if(response!=0)
				{
					//Start reading the file.
				}
			}
		});
		btnTranslateFile.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnTranslateFile.setBounds(444, 52, 130, 35);
		getContentPane().add(btnTranslateFile);
		
		//scrollPane.add(MatchedItemsPanle);
	}
	///////**********************************************************************************
	private static String readText(Socket socket)  
	{

		ObjectInputStream ois;
		String inputMessage = "";
		try
		{
			ois = new ObjectInputStream(socket.getInputStream());
			inputMessage = (String) ois.readObject();
		}
		catch(IOException | ClassNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, "Can not read from the socket","From client to server",1);
		}

		return inputMessage;
	}
	///////**********************************************************************************
	private static void sendText(String text, Socket socket)  
	{
		JOptionPane.showMessageDialog(null,"sending text to server..","Client send text",1);
		OutputStream output;
		ObjectOutputStream oos;
		try
		{
			output = socket.getOutputStream();
			oos = new ObjectOutputStream(output);
			
			oos.writeObject(text);
			oos.flush();
			output.flush();
			//output.close();
			//oos.close();
		}
		catch(IOException e)
		{
			JOptionPane.showMessageDialog(null, "Can not write to the socket"+text,"From client to server",1);
		}
	}
	///////**********************************************************************************
	private static ArrayList<String> readArrayList(Socket socket) throws IOException, ClassNotFoundException 
	{
		ObjectInputStream ois;		
		ois = new ObjectInputStream(socket.getInputStream());
		ArrayList<String> inputMessage = (ArrayList) ois.readObject();
		return inputMessage;
	}
	///////**********************************************************************************
	private void updateUI(ArrayList<String> items)  
	{
		matchCheck = new JCheckBox[items.size()];
		for(int i=0;i<items.size();i++)
		{
			matchCheck[i] = new JCheckBox(items.get(i));
			matchCheck[i].setSelected(true);
			/*matchCheck[i].addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent arg0) 
				{
					if(((JCheckBox)arg0.getSource()).isSelected())
						atLeastOneItemIsCkecked = true;
				}
			});*/
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
		if(!items.equals(""))
		{
			String[] itemsArr = items.split("\n");
			matchCheck = new JCheckBox[itemsArr.length];
			for(int i=0;i<itemsArr.length;i++)
			{
				matchCheck[i] = new JCheckBox(itemsArr[i]);
				matchCheck[i].setSelected(true);
				/*matchCheck[i].addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent arg0) 
					{
						if(((JCheckBox)arg0.getSource()).isSelected())
							atLeastOneItemIsCkecked = true;
					}
				});*/
				this.MatchedItemsPanle.add(matchCheck[i]);
			}
			this.MatchedItemsPanle.revalidate();
			this.MatchedItemsPanle.repaint();
			this.scrollPane.revalidate();
			this.scrollPane.repaint();
			//scrollPane
			validate();
		}
		
	}
	///////**********************************************************************************
	private void UpdateFromOntologyList(String items)  
	{
		String[] itemsArr = items.split("\n");
		for(int i=0;i<itemsArr.length;i++)
			model.addElement(itemsArr[i]);
	}
}