package openworld.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import openworld.terrain.Mountain;

public class MountainDialog extends JDialog implements ActionListener {

    private JButton yesButton, noButton;
    private Mountain mountain;
    private Mountain choice = null;
    public boolean buttonPressed = false;

    public MountainDialog(GameWindow window, Mountain mountain) {
        super(window, "Mountain", true);
        setLocationRelativeTo(window);

        this.mountain = mountain;

        Box bottomBox = Box.createHorizontalBox();
		bottomBox.add(Box.createHorizontalGlue());

        yesButton = new JButton("Yes");
        yesButton.addActionListener(this);
        bottomBox.add(yesButton);
        bottomBox.add(Box.createHorizontalGlue());
        noButton = new JButton("No");
        noButton.addActionListener(this);
        bottomBox.add(noButton);
        bottomBox.add(Box.createHorizontalGlue());

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().add(new JLabel("Would you like to explore this mountain?"));
		getContentPane().add(Box.createVerticalStrut(10));
		getContentPane().add(bottomBox);

        /* 
        try {
            TimeUnit.SECONDS.sleep(5);
            } catch(Exception e) {}
            */
		pack();
    }

    public Mountain getChoice() {
        System.out.println("Checking");    
        
        try {
            TimeUnit.SECONDS.sleep(1);
            } catch(Exception e) {}    
        return choice;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        buttonPressed = true;
        if (e.getSource() == yesButton){
            System.out.println("Yes Button");
            choice = mountain;
        }
        //choice = (e.getSource() == yesButton) ? mountain : null;
        dispose();
    }
    
}
