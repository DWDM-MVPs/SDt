public class Place {
	private String _postalCode;
	private String _locality;

	public Place(String postalCode, String locality) {
		this._postalCode = postalCode;
		this._locality = locality;
	}

	public String getPostalCode() {
		return _postalCode;
	}

	public void setPostalCode(String postalCode) {
		this._postalCode = postalCode;
	}

	public String getLocality() {
		return _locality;
	}

	public void setLocality(String locality) {
		this._locality = locality;
	}
}
