package jade.data.config;

import java.sql.*;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigAgent extends jade.core.Agent {
	private static final Logger log = LoggerFactory.getLogger(ConfigAgent.class);

	private static final ConfigOntology ontology = ConfigOntology.getInstance();

	private Codec codec = new SLCodec();

	private Connection conn;

	protected void setup() {
		getContentManager().registerLanguage(codec);
		getContentManager().registerOntology(ontology);

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/jade_data_config",
											   "jade_data_config", "jdc1001");
		} catch(SQLException ex) {
			log.error("Cannot connect to data to retrieve configuration", ex);
			doDelete();
		}

		addBehaviour(new ConfigQueryHandler(this, conn));
	}

	protected void takeDown() {
		try {
			conn.close();
		} catch(SQLException ex) {
			log.error("Database close failed", ex);
		}
	}
}
