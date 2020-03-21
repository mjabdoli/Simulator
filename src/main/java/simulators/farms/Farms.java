package simulators.farms;

import models.DeviceType;
import simulators.AdminUser;
import simulators.HumiditySensor;
import simulators.TempratureSensor;
import simulators.WaterDevice;

/**
 * Created by abdoli on 3/25/19.
 */
public class Farms {
	HumiditySensor moisture1=new HumiditySensor(1,1,"humiditysensor1", DeviceType.HTTP);
	TempratureSensor temp1
			=new TempratureSensor(2,1,"TempSensor1", DeviceType.HTTP);
	WaterDevice water1 =new WaterDevice(3,1,"water1",DeviceType.HTTP);
	AdminUser admin1 =new AdminUser(4,1,"admin1",DeviceType.NOTIFICATION);


	HumiditySensor moisture3 =new HumiditySensor(5,2,"humiditysensor1", DeviceType.HTTP);
	HumiditySensor water2 =new HumiditySensor(6,2,"water",DeviceType.HTTP);
	AdminUser admin2 =new AdminUser(7,2,"admin1",DeviceType.NOTIFICATION);
	public Farms()
	{
		moisture1.addField("m");
		temp1.addField("t");
		water1.addField("s").addField("c");
		admin1.addField("r");
		water1.start();
		water2.addField("s").addField("c");
	}
}
