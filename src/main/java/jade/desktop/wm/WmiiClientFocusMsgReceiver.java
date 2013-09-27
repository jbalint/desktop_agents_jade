package jade.desktop.wm;

import java.util.Set;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.states.MsgReceiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WmiiClientFocusMsgReceiver extends MsgReceiver {
	private static final Logger log = LoggerFactory.getLogger(WmiiClientFocusMsgReceiver.class);

	private static final MessageTemplate mt =
		MessageTemplate.MatchPerformative(ACLMessage.REQUEST);

	public WmiiClientFocusMsgReceiver(Agent agent) {
		super(agent, mt, MsgReceiver.INFINITE, null, null);
	}

	protected void handleMessage(ACLMessage msg) {
		try {
			String clientId = msg.getContent();
			String currentTagName = Wmiir.getCurrentTagName();
			Set<String> clientTags = Wmiir.getClientTagNames(clientId);
			if (!clientTags.contains(currentTagName)) {
				currentTagName = clientTags.iterator().next();
				// TODO this is an arbitrary choice to show the client
				Process p = new ProcessBuilder("wmiir", "xwrite", "/ctl", "view", currentTagName).start();
				p.waitFor();
			}
			Process p = new ProcessBuilder("wmiir", "xwrite", "/tag/" + currentTagName + "/ctl", "select", "client", clientId).start();
			p.waitFor();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
