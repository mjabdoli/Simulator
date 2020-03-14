package models;

/**
 * Created by abdoli on 2/4/19.
 */
public enum DeviceType {
	HTTP (1),
	PUBSUB (2),
	NOTIFICATION (3),
	NONE(0);

	private final int typeCode;

	DeviceType(int levelCode) {
		this.typeCode = levelCode;
	}

	public static DeviceType setByValue(int typeValue)
	{
		switch (typeValue)
		{
			case 1:
				return HTTP;
			case 2:
				return PUBSUB;
			case 3:
				return NOTIFICATION;
			default:
				return NONE;

		}

	}

	public int getTypeCode() {
		return this.typeCode;
	}

}
