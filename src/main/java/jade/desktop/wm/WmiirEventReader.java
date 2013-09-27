package jade.desktop.wm;

import java.io.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A reader of `wmii' events. Using the `wmiir' commands, this object reads events from the stream.
 */
public class WmiirEventReader {
	private static final Logger log = LoggerFactory.getLogger(WmiirEventReader.class);
	Process p;
	BufferedReader reader;

	public WmiirEventReader() throws IOException {
		p = new ProcessBuilder("wmiir", "read", "/event").redirectErrorStream(true).start();
		reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
	}

	public void close() {
		p.destroy();
	}

	public String readEvent() throws IOException {
		return reader.readLine();
	}

	public static void main(String args[]) throws Exception {
		WmiirEventReader rdr = new WmiirEventReader();
		while (true) {
			System.out.println("Wmiir event: " + rdr.readEvent());
		}
	}
}
