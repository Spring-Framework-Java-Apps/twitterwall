package org.woehlke.twitterwall.eai;

public class RequestTwitterwallEvent {
	
	private final String searchterm;

	private RequestTwitterwallEvent(){
		searchterm="";
	}
	
	public RequestTwitterwallEvent(String searchterm) {
		super();
		this.searchterm = searchterm;
	}

	public String getSearchterm() {
		return searchterm;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((searchterm == null) ? 0 : searchterm.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequestTwitterwallEvent other = (RequestTwitterwallEvent) obj;
		if (searchterm == null) {
			if (other.searchterm != null)
				return false;
		} else if (!searchterm.equals(other.searchterm))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RequestTwitterwallEvent [searchterm=" + searchterm + "]";
	}
	
	
}
