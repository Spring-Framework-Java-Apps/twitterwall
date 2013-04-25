package org.woehlke.spring.eai.events;

import java.util.Date;
import java.util.UUID;

public abstract class AbstractEvent {

	private final Date timestamp;
	private final String uuid;
	
	protected AbstractEvent() {
		super();
		this.uuid = UUID.randomUUID().toString();
		this.timestamp = new Date();
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getUuid() {
		return uuid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((timestamp == null) ? 0 : timestamp.hashCode());
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractEvent other = (AbstractEvent) obj;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AbstractEvent [timestamp=" + timestamp + ", uuid=" + uuid + "]";
	}
	
	
}
