package com.webile.graphgame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Object.*;
import javax.swing.border.*;
class Preference extends JDialog implements ActionListener,ItemListener
{
	static final long serialVersionUID = 62L;
	GraphGame G;
	JCheckBox musicCheckBox,startCheckBox;
	JTextField name1,name2;
	public int closedState=1;
	Preference(GraphGame tempG)
	{
		super(tempG,"Preferences",true);
		G=tempG;
		JPanel panel = createPanel();
		getContentPane().add(panel,BorderLayout.CENTER);
		pack();
	}
	JPanel createPanel()
	{
		JPanel panel = new JPanel(new BorderLayout());
		JPanel player1Panel = new JPanel(new GridLayout(0,1));
		JPanel player2Panel = new JPanel(new GridLayout(0,1));
		JPanel optionPanel = new JPanel(new GridLayout(0,1));
		JPanel buttonPanel = new JPanel(new GridLayout(1,0));

// creating player1 info panel
		player1Panel.setBorder(BorderFactory.createCompoundBorder(
							BorderFactory.createTitledBorder("Player 1"),
					BorderFactory.createEmptyBorder(5,5,5,5)));
		JRadioButton computer1RadioButton = new JRadioButton("Computer");
		computer1RadioButton.addActionListener(this);
		computer1RadioButton.setActionCommand("computer");
		JRadioButton human1RadioButton = new JRadioButton("human");
		if(G.defaultPlayer1Human == true)
		{
			human1RadioButton.setSelected(true);
			computer1RadioButton.setSelected(false);
			G.tempPlayer1Human=G.defaultPlayer1Human;
		}
		else
		{
			human1RadioButton.setSelected(false);
			computer1RadioButton.setSelected(true);
			G.tempPlayer1Human=G.defaultPlayer1Human;
		}

		human1RadioButton.setActionCommand("human");
		human1RadioButton.addActionListener(this);

		ButtonGroup group1 = new ButtonGroup();
		group1.add(human1RadioButton);
		group1.add(computer1RadioButton);

		final JLabel player1Label = new JLabel("player name");
		name1 = new JTextField(8);
		name1.setText(G.defaultPlayer1Name);

		player1Panel.add(human1RadioButton);
		player1Panel.add(computer1RadioButton);
		player1Panel.add(player1Label);
		player1Panel.add(name1);

// creating player2 info panel
		player2Panel.setBorder(BorderFactory.createCompoundBorder(
							BorderFactory.createTitledBorder("Player 2"),
					BorderFactory.createEmptyBorder(5,5,5,5)));
		JRadioButton human2RadioButton = new JRadioButton("human");
		human2RadioButton.setSelected(true);
		human2RadioButton.setEnabled(false);
		//human2RadioButton.setActionCommand("human");
		//human2RadioButton.addActionListener(this);

		JRadioButton computer2RadioButton = new JRadioButton("Computer");
		//computer2RadioButton.addActionListener(this);
		//computer2RadioButton.setActionCommand("computer");
		computer2RadioButton.setEnabled(false);

		final JLabel player2Label = new JLabel("player name");
		name2 = new JTextField(8);
		name2.setText(G.defaultPlayer2Name);

		player2Panel.add(human2RadioButton);
		player2Panel.add(computer2RadioButton);
		player2Panel.add(player2Label);
		player2Panel.add(name2);
		/*ButtonGroup group2 = new ButtonGroup();
		group2.add(human2RadioButton);
		group2.add(computer2RadioButton);*/
// creating options panel
		optionPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		startCheckBox = new JCheckBox("Short starts");
		if(G.defaultShortStarts ==true)
		{
			startCheckBox.setSelected(true);
		}
		else
		{
			startCheckBox.setSelected(false);
		}
		G.tempShortStarts = G.defaultShortStarts;
		startCheckBox.setToolTipText("Player1 starts game when checked");
		startCheckBox.addItemListener(this);

		musicCheckBox = new JCheckBox("Music on");
		if(G.defaultMusic ==true)
		{
			musicCheckBox.setSelected(true);
		}
		else
		{
			musicCheckBox.setSelected(false);
		}
		G.tempMusic = G.defaultMusic;
		musicCheckBox.setToolTipText("Music is enabled when checked");
		musicCheckBox.addItemListener(this);

		JCheckBox timedGameCheckBox = new JCheckBox("Timed move");
				timedGameCheckBox.setSelected(true);
		timedGameCheckBox.setToolTipText("enables Timed moves when checked");
		timedGameCheckBox.setEnabled(false);

		final JLabel difficultyLabel = new JLabel("   Difficulty");
		//difficultyLabel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));

		ButtonGroup group2 = new ButtonGroup();
		JRadioButton shortButton = new JRadioButton("Short");
		shortButton.setEnabled(false);
		shortButton.addActionListener(this);
		JRadioButton mediumButton= new JRadioButton("Medium");
		mediumButton.addActionListener(this);
		mediumButton.setEnabled(false);
		mediumButton.setSelected(true);
		JRadioButton cutButton = new JRadioButton("Cut");
		cutButton.setEnabled(false);
		cutButton.addActionListener(this);
		group2.add(shortButton);
		group2.add(mediumButton);
		group2.add(cutButton);
// !!!! add tool tip text
		optionPanel.add(startCheckBox);
		optionPanel.add(musicCheckBox);
		optionPanel.add(timedGameCheckBox);
		optionPanel.add(difficultyLabel);
		optionPanel.add(shortButton);
		optionPanel.add(mediumButton);
		optionPanel.add(cutButton);
// create button panel
		JButton okButton= new JButton("   OK   ");
		okButton.addActionListener(this);
		okButton.setToolTipText("Starts new game with current options");
		okButton.setActionCommand("ok");

		JButton restoreButton = new JButton("Defaults");
		restoreButton.addActionListener(this);
		restoreButton.setToolTipText("Restore default values,starts new game");
		restoreButton.setActionCommand("restore");

		JButton cancelButton = new JButton(" Cancel ");
		cancelButton.addActionListener(this);
		cancelButton.setActionCommand("cancel");
		cancelButton.setToolTipText("Cancel all the selections and go back to the game");

		buttonPanel.add(restoreButton);
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));

//		setDefaultButton(okButton);

// add all panels
		panel.add(player1Panel,BorderLayout.CENTER);
		panel.add(player2Panel,BorderLayout.EAST);
		panel.add(optionPanel,BorderLayout.WEST);
		panel.add(buttonPanel,BorderLayout.SOUTH);
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		return panel;
	}

	public void actionPerformed(ActionEvent e)
	{
		if ("ok".equals(e.getActionCommand()))
		{
			G.tempPlayer1Name = name1.getText();
			G.tempPlayer2Name = name2.getText();
			closedState=1;

			this.setVisible(false);
			return;
		}
		if ("restore".equals(e.getActionCommand()))
		{
			closedState=0;
			this.setVisible(false);
			return;
		}
		if ("cancel".equals(e.getActionCommand()))
		{
			closedState=2;
			this.setVisible(false);
			return;
		}
		if ("human".equals(e.getActionCommand()))
		{
			G.tempPlayer1Human = true;
			return;
		}
		if("computer".equals(e.getActionCommand()))
		{
			G.tempPlayer1Human = false;
			return;
		}
	}

	public void itemStateChanged(ItemEvent e)
	{
	      Object source = e.getItemSelectable();
	       if (source == musicCheckBox)
	       {
			   if (e.getStateChange() == ItemEvent.DESELECTED)
			   {
			      G.tempMusic = false;
        		}
        		else
        		{
				G.tempMusic = true;
				}
		   }
		   else if(source == startCheckBox)
		   {
			   if (e.getStateChange() == ItemEvent.DESELECTED)
			   	{
   			       G.tempShortStarts = false;

     		    }
           		else
           		{
   					G.tempShortStarts = true;

				}
			}
			else if(source ==musicCheckBox)
			{
				if (e.getStateChange() == ItemEvent.DESELECTED)
			   	{
   			       G.tempShortStarts = false;

     		    }
           		else
           		{
   					G.tempShortStarts = true;

				}
			}
			else System.out.println("non listed action performed!!");
	}
}//end of preference class

