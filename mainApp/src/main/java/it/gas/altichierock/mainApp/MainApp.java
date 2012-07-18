package it.gas.altichierock.mainApp;

import javax.swing.SwingUtilities;

public class MainApp implements Runnable {

	public static void main(String[] args) {
		//where everything start
		SwingUtilities.invokeLater(new MainApp());
	}
	
	public void run() {
		//create the main app window and start it
		new MainWindow().setVisible(true);
	}

}
