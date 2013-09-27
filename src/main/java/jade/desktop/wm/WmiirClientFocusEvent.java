package jade.desktop.wm;

public class WmiirClientFocusEvent extends WmiirEvent {
	private String clientId;

	public WmiirClientFocusEvent(String clientId) {
		if (!clientId.matches("0x[a-f\\d]{1,8}")) {
			throw new IllegalArgumentException("Unrecognized client id format: " + clientId);
		}
		this.clientId = clientId;
	}

	public String getClientId() {
		return this.clientId;
	}
}
