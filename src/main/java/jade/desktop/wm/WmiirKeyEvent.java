package jade.desktop.wm;

// TODO - should be generalized to UI-input key event
public class WmiirKeyEvent extends WmiirEvent {
	private String key;

	public WmiirKeyEvent(String key) {
		if (!key.startsWith("Mod4-")) {
			throw new IllegalArgumentException("Unrecognized key: " + key);
		}
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}

	public String toString() {
		return WmiirKeyEvent.class.getName() + "(" + this.timestamp + "): " + this.key;
	}
}
