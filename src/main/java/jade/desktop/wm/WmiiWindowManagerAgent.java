package jade.desktop.wm;

import jade.core.Agent;

public class WmiiWindowManagerAgent extends Agent {
	private Thread wmiirReadThread = new Thread("wmiir-reader");

	protected void setup() {
	}

	protected void takeDown() {
		wmiirReadThread.interrupt();
	}
}
