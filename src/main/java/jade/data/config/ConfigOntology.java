package jade.data.config;

import jade.content.onto.BasicOntology;
import jade.content.onto.BeanOntology;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.ConceptSchema;
import jade.content.schema.ObjectSchema;

public class ConfigOntology extends BeanOntology {
	public static final String ONTOLOGY_NAME = "Config-Ontology";

	private static final ConfigOntology instance = new ConfigOntology();

	public static String CONFIG_PARAM = "ConfigParam";
	public static String CONFIG_PARAM_KEY = "key";
	public static String CONFIG_PARAM_VALUE = "value";

	public static ConfigOntology getInstance() {
		return instance;
	}

	private ConfigOntology() {
		super(ONTOLOGY_NAME);

		try {
			add("jade.data.config");
		} catch(OntologyException ex) {
			ex.printStackTrace();
		}
	}
}
