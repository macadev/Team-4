import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Login extends JFrame{
	
		private JTextField fieldName;
		private JPasswordField fieldPass;
		private JLabel labelMessage;
		private JButton buttonSubmit;
	
		public Login(){
			createView();
			
			//Exit Option
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			//Display Size
			setSize(800, 400);
			//Setting it to the middle of the screen
			setLocationRelativeTo(null);
			//Disable resize
			setResizable(false);
		}
		
		
		//User Interface
		private void createView(){
			JPanel panel = new JPanel();
			getContentPane().add(panel);
			
			JLabel label = new JLabel("Username: ");
			panel.add(label);
			
			fieldName = new JTextField();
			fieldName.setPreferredSize(new Dimension(150, 30));
			panel.add(fieldName);
			
			
			JLabel label2 = new JLabel("Password: ");
			panel.add(label2);
			
			fieldPass = new JPasswordField();
			fieldPass.setPreferredSize(new Dimension(150, 30));
			panel.add(fieldPass);
			
			
			buttonSubmit = new JButton("Login");
			//Note: Create Account and Exit not put in as it already exists in the main structure of the game
			
			buttonSubmit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e){
					String name = fieldName.getText();
					String password = fieldPass.getText();
					//Authentication will be done but if-else method put in as a placeholder
					if (name.isEmpty()){
						labelMessage.setText("Username cannot be blank");
					} else if (password.isEmpty()){
						labelMessage.setText("Password cannot be blank");
					} else {
						labelMessage.setText("Logging in");
					}
				}
			});
			
			
			
			panel.add(buttonSubmit);
			
			labelMessage = new JLabel("");
			panel.add(labelMessage);
		}

		
		//Main Function
		public static void main(String[] args) {
			//Create a frame and show it throught SwingUtilities
			SwingUtilities.invokeLater(new Runnable(){
				@Override
				public void run(){
					new Login().setVisible(true);
				}
			});

		}
		
}

