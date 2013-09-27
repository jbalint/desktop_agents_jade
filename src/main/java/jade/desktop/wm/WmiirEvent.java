package jade.desktop.wm;

import java.util.Arrays;
import java.util.Date;

public abstract class WmiirEvent implements java.io.Serializable {
	protected Date timestamp = new Date();

	public static WmiirEvent parse(String fields[]) {
		String type = fields[0];
		String args[] = Arrays.copyOfRange(fields, 1, fields.length);
		if ("Key".equals(type)) {
			return new WmiirKeyEvent(args[0]);
		} else if ("ClientFocus".equals(type)) {
			return new WmiirClientFocusEvent(args[0]);
		} else {
			// not interested for now
			return null;
		}
	}

	public Date getTimestamp() {
		return this.timestamp;
	}
}
