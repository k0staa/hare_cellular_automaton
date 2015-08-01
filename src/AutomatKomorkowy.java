
import java.awt.EventQueue;

public class AutomatKomorkowy {

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {

				new MyFrame();
			}
		});
	}

}
