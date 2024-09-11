
import java.lang.reflect.*;

class VehicleInvocationHandler implements InvocationHandler
{
	Vehicle vehicle = null;

	private VehicleInvocationHandler(Vehicle vehicle){
		this.vehicle = vehicle;
	}

	public static Object getProxy(String name){
		Vehicle vehicle = new Vehicle(name);
		// the proxy can be provided as drivable, ridable or repairable.
		Class[] interfaces = new Class[] { Drivable.class, Ridable.class, Repairable.class};
		Object proxy = Proxy.newProxyInstance(vehicle.getClass().getClassLoader(), interfaces, new VehicleInvocationHandler(vehicle));
		return proxy;
	}
	
	public Object invoke(Object proxy, Method method, Object[] args){
		if(method.getName().equals("drive")){
			vehicle.move((String)args[0], (String)args[1], ((Integer)args[2]).intValue());
		}
		if(method.getName().equals("ride")){
			String[] passengers = (String[]) args[0];
			vehicle.carry(passengers);
		}
		if(method.getName().equals("repair")){
			String[] parts = (String[]) args[0];
			vehicle.getRepaired(parts);
		}
		return null;
	}

}
