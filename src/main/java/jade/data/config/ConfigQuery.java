package jade.data.config;

import jade.content.AgentAction;

public class ConfigQuery implements AgentAction {
	private String agentName;
	private ConfigParam configParam;

	public ConfigQuery() {
	}

	public ConfigQuery(String agentName, ConfigParam configParam) {
		this.agentName = agentName;
		this.configParam = configParam;
	}

	public String getAgentName() {
		return this.agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public ConfigParam getConfigParam() {
		return this.configParam;
	}

	public void setConfigParam(ConfigParam configParam) {
		this.configParam = configParam;
	}
}
