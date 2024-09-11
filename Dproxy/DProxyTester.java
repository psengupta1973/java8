
public class DProxyTester
{
	public static void main(String[] args){
		Object proxy = VehicleInvocationHandler.getProxy("Bus");

		Drivable d = (Drivable) proxy;
		Ridable r = (Ridable) proxy;
		Repairable p = (Repairable) proxy;

		System.out.println("Dynamic Proxy created... ");
		d.drive("Lombard", "Chicago", 60);
		r.ride(new String[]{"Tom", "John", "Harry"});
		p.repair(new String[]{"Wipper", "Head lights", "Gear Box"});
	}

}
