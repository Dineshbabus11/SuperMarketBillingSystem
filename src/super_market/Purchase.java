package super_market;
import java.util.*;
import java.time.*;
public class Purchase {
	String purchaseId;
	LocalDate date;
	ArrayList<Cart> items;
	double total;
	
	public Purchase(String purchaseId,LocalDate date,ArrayList<Cart> items,double total) {
		this.purchaseId=purchaseId;
		this.date=date;
		this.items=items;
		this.total=total;
	}
}
