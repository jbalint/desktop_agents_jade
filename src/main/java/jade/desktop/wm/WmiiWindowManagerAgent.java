package jade.desktop.wm;

import java.io.IOException;

import jade.core.Agent;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WmiiWindowManagerAgent extends Agent {
	private static final Logger log = LoggerFactory.getLogger(WmiirEventReader.class);

	private WmiirEventReader wmiirEventReader;
	private Thread wmiirReadThread = new Thread("wmiir-reader") {
			public void run() {
				while (true) {
					try {
						String event = wmiirEventReader.readEvent();
						postWmiirEvent(event);
					} catch(/*IO*/Exception ex) {
						// handle ALL exceptions to keep thread alive
						log.error("Failed to read from WmiirEventReader", ex);
						// TODO - restarting of WmiirEventReader (with sleep)
						// TODO - separate handling of failure in postWmiirEvent()
					}
				}
			}
		};

	protected void setup() {
		try {
			wmiirEventReader = new WmiirEventReader();
			wmiirReadThread.start();
		} catch(IOException ex) {
			log.error("Failed to start WmiirEventReader", ex);
			throw new RuntimeException("Cannot start WmiiWindowManagerAgent", ex);
		}
	}

	protected void takeDown() {
		wmiirReadThread.interrupt();
	}

	private void postWmiirEvent(String event) throws IOException {
		WmiirEvent wmiirEvent = WmiirEvent.parse(event.split(" "));
		if (wmiirEvent == null)
			return;
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.setContentObject(wmiirEvent);
		msg.addReceiver(new AID("da0", AID.ISLOCALNAME));
		send(msg);
	}
}
