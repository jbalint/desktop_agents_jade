package jade.data.index;

import jade.content.lang.Codec;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jade.content.lang.sl.SLCodec;
import jade.data.config.ConfigOntology;
import jade.data.config.ConfigParam;
import jade.data.config.ConfigQuery;

public class IndriAgent extends jade.core.Agent {
	private static final Logger log = LoggerFactory.getLogger(IndriAgent.class);

	private Codec codec = new SLCodec();

	protected void setup() {
		getContentManager().registerLanguage(codec);
		getContentManager().registerOntology(ConfigOntology.getInstance());

		ConfigParam p = new ConfigParam("index-list");
		ConfigQuery q = new ConfigQuery(IndriAgent.class.getName(), p);
		ACLMessage m = new ACLMessage(ACLMessage.QUERY_REF);
		m.setLanguage(codec.getName());
		m.setOntology(ConfigOntology.getInstance().getName());
		AID aid = new AID("config", AID.ISLOCALNAME);
		m.addReceiver(aid);
		try {
			getContentManager().fillContent(m, new Action(aid, q));
			send(m);
		} catch(Exception ex) {
			log.error("Failed to request index-list", ex);
		}
	}

	protected void takeDown() {
	}
}
