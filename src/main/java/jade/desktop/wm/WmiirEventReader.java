package jade.desktop.wm;

import java.io.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A reader of `wmii' events. Using the `wmiir' commands, this object reads events from the stream.
 */
public class WmiirEventReader {
	private static final Logger log = LoggerFactory.getLogger(WmiirEventReader.class);

	private Process p;
	private BufferedReader reader;

	public WmiirEventReader() throws IOException {
		p = new ProcessBuilder("wmiir", "read", "/event").redirectErrorStream(true).start();
		reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		log.debug("Started `wmiir' reader");
	}

	public void close() throws IOException {
		p.destroy();
		reader.close();
	}

	public String readEvent() throws IOException {
		String event = reader.readLine();
		return event;
	}

	public static void main(String args[]) throws Exception {
		System.out.println("All tags: " + Wmiir.getTagNames());
		System.out.println("Current tag: '" + Wmiir.getCurrentTagName() + "' (" + Wmiir.getCurrentTagName().length() + ")");
		WmiirEventReader rdr = new WmiirEventReader();
		while (true) {
			WmiirEvent ev = WmiirEvent.parse(rdr.readEvent().split(" "));
			System.out.println("Wmiir event: " + ev);
			if (ev != null &&
				WmiirClientFocusEvent.class.isAssignableFrom(ev.getClass())) {
				WmiirClientFocusEvent ev2 = (WmiirClientFocusEvent) ev;
				System.out.println("\t client label: " + Wmiir.getClientLabel(ev2.getClientId()));
				System.out.println("\t client tags: " + Wmiir.getClientTagNames(ev2.getClientId()));
			}
		}
	}
}
