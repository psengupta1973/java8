/* Dynamic Proxy Example 
If an implementation has multiple operations/services 
only a desired/selected set of operations can be exposed 
to a certain consumer.
*/

public class Vehicle {
	String name = null;
	
	public Vehicle(String name){
		this.name = name;
	}

    public void move(String start, String end, int speed) {
		System.out.println(name +" is going from "+start+" to "+end+" at a speed of "+speed+"mph");
    }
    
    public void carry(String[] passengers) {
		for (String str : passengers)
			System.out.println(name+" is Carrying "+str);
    }

    public void getRepaired(String[] parts) {
 	for (String str : parts){
		System.out.print(str+", ");
	}
	if(parts.length > 1){
		System.out.println("are getting Repaired on "+name);
	}
	else{
		System.out.println("is getting Repaired on "+name);
	}
   }
	
}


