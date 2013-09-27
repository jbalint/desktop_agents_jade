package jade.desktop.wm;

import java.sql.Timestamp;
import java.util.Arrays;

public abstract class WmiirEvent implements java.io.Serializable {
	protected Timestamp timestamp = new Timestamp(System.currentTimeMillis());

	public static WmiirEvent parse(String fields[]) {
		String type = fields[0];
		String args[] = Arrays.copyOfRange(fields, 1, fields.length);
		if ("Key".equals(type)) {
			return new WmiirKeyEvent(args[0]);
		} else if ("ClientFocus".equals(type)) {
			if ("<nil>".equals(args[0])) {
				return null;
			} else {
				return new WmiirClientFocusEvent(args[0]);
			}
		} else {
			// not interested for now
			return null;
		}
	}

	public Timestamp getTimestamp() {
		return this.timestamp;
	}
}
