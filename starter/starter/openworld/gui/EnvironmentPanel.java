package openworld.gui;
import openworld.characters.NPCMove;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class EnvironmentPanel extends JPanel implements ActionListener {
	
	private GameWorld gameWorld;
	
	private JButton respawnButton, startButton, stopButton;
	
	private boolean startButtonClicked = false, stopButtonClicked = false;

	public EnvironmentPanel(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBorder(new TitledBorder("Monster and NPC controls"));
		
		respawnButton = new JButton("Respawn all");
		respawnButton.addActionListener(this);
		startButton = new JButton("Start moving");
		startButton.addActionListener(this);
		stopButton = new JButton("Stop moving");
		stopButton.addActionListener(this);
		
		add(respawnButton);
		add(startButton);
		add(stopButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Thread thread = new Thread(new NPCMove(gameWorld.getWorld()));

		if (e.getSource() == respawnButton){
			gameWorld.respawnWorld();
		}
		if (e.getSource() == startButton){
			if (!startButtonClicked){
				thread.start();
				startButtonClicked = true;
				stopButtonClicked = false;
			}
		}
		if (e.getSource() == stopButton){
			if (!stopButtonClicked){
				thread.interrupt();
				try {
					thread.join();
					NPCMove.move = false;
					startButtonClicked = false;
					stopButtonClicked = true;
				} catch(InterruptedException i){

				}
			}
			}
		
	}

	public void disableAll() {
		startButton.setEnabled(false);
		stopButton.setEnabled(false);
		respawnButton.setEnabled(false);
	}

}
