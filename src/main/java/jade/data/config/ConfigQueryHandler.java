package jade.data.config;

import java.sql.*;

import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.onto.basic.Action;
import jade.content.onto.basic.Result;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.states.MsgReceiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigQueryHandler extends MsgReceiver {
	private static final Logger log = LoggerFactory.getLogger(ConfigQueryHandler.class);

	private static final MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.QUERY_REF),
																  MessageTemplate.MatchOntology(ConfigOntology.ONTOLOGY_NAME));

	private Connection conn;
	private PreparedStatement getParamStmt;

	public ConfigQueryHandler(Agent agent, Connection conn) {
		super(agent, mt, MsgReceiver.INFINITE, null, null);
		this.conn = conn;
		try {
			// param 1 = agent name
			// param 2 = param key
			getParamStmt = conn.prepareStatement("select p.param_value from config_param p inner join agent a " +
												 "on p.agent_id = a.agent_id and a.agent_name = ? and p.param_key = ?");
		} catch(SQLException ex) {
			log.error("Failed to prepare statements", ex);
		}
	}

	protected void handleMessage(ACLMessage msg) {
		ConfigQuery q;

		try {
			q = (ConfigQuery) ((Action) getAgent().getContentManager().extractContent(msg)).getAction();
		} catch(Exception ex) {
			log.error("Cannot extract content from message: " + msg, ex);
			return;
		}

		if (q.getConfigParam() != null) {
			String key = q.getConfigParam().getKey();
			try {
				this.getParamStmt.setString(1, q.getAgentName());
				this.getParamStmt.setString(2, key);
				ResultSet rs = this.getParamStmt.executeQuery();
				if (rs.next()) {
					String val = rs.getString(1);
					ACLMessage reply = msg.createReply();
					reply.setPerformative(ACLMessage.INFORM);
					q.getConfigParam().setValue(val);
					Result result = new Result(q, q.getConfigParam());
					getAgent().getContentManager().fillContent(reply, result);
					getAgent().send(reply);
				}
				rs.close();
			} catch(Exception ex) {
				log.error("Failed to process query for `" + key + "' (agent=" + q.getAgentName() + ")", ex);
			}
		}
	}

	public boolean done() {
		return false;
	}
}
