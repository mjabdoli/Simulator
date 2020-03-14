package simulators;

import models.DeviceType;

/**
 * Created by abdoli on 3/25/19.
 */
public class AdminUser extends Sensor {
	public AdminUser(int deviceId, int profileId, String deviceName, DeviceType deviceType) {
		super(deviceId, profileId, deviceName, deviceType);
	}

	@Override
	public void start() {

	}

	@Override
	public String send() {
		return null;
	}

	@Override
	public void receive() {

	}

	@Override
	public void heartbeat() {

	}
}
