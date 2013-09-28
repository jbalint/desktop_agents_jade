package jade.data.config;

public class ConfigParam implements jade.content.Concept {
	private String key;
	private String value;

	public ConfigParam(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public ConfigParam(String key) {
		this(key, null);
	}

	public ConfigParam() {
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
