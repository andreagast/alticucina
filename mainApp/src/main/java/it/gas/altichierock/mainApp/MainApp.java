package it.gas.altichierock.mainApp;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainApp implements Runnable {
	private static Logger log = LoggerFactory.getLogger(MainApp.class);

	public static void main(String[] args) {
		try {
			//String lnf = UIManager.getCrossPlatformLookAndFeelClassName();
			String lnf = UIManager.getSystemLookAndFeelClassName();
			UIManager.setLookAndFeel(lnf);
		} catch (ClassNotFoundException e) {
			//e.printStackTrace();
			log.error("L&F not available.");
		} catch (InstantiationException e) {
			//e.printStackTrace();
			log.error("Can't instantiate L&F.");
		} catch (IllegalAccessException e) {
			//e.printStackTrace();
			log.error("We don't have access to L&F.");
		} catch (UnsupportedLookAndFeelException e) {
			//e.printStackTrace();
			log.error("L&F not supported.");
		}
		//where everything start
		SwingUtilities.invokeLater(new MainApp());
	}
	
	public void run() {
		//create the main app window and start it
		new MainWindow().setVisible(true);
	}

}
