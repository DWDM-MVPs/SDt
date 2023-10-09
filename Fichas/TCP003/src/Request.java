import java.io.Serializable;

public class Request implements Serializable {
	private int _objectId;
	private String _method;
	private String _type;
	private Place _place;

	public Request(int objectId, String method) {
		this._objectId = objectId;
		this._method = method;

		this._type = "invoke";
	}

	public Request(Place place) {
		this._place = place;

		this._type = "new";
	}

	public int getObjectId() {
		return _objectId;
	}

	public void setObjectId(int _objectId) {
		this._objectId = _objectId;
	}

	public String getMethod() {
		return _method;
	}

	public void setMethod(String _method) {
		this._method = _method;
	}

	public String getType() {
		return _type;
	}

	public void setType(String _type) {
		this._type = _type;
	}

	public Place getPlace() {
		return _place;
	}

	public void setPlace(Place _place) {
		this._place = _place;
	}
}
