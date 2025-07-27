package super_market;
import java.util.*;

public class User {
	String email;
	String password;
	String role;
	double credit=1000;
	int loyaltyPoints=0;
	ArrayList<Purchase> purchaseHistory=new ArrayList<>();
	
	public User(String email,String password,String role) {
		this.email=email;
		this.password=password;
		this.role=role;
	}
	
}
