package super_market;

import java.util.*;
import java.time.*;

public class Main {
	static Scanner sc=new Scanner(System.in);
	static HashMap<String, User> users=new HashMap<>();
	static HashMap<String, Product> products=new HashMap<>();
	static HashMap<String, ArrayList<Cart>> cart=new HashMap<>();

	public static void main(String[] args) {
		
		while(true) {
			System.out.println("=============Welcome to SuperMarket Billing System=============");
			System.out.println("1.Register\n2.Login\n3.Exit");
			int choice=Integer.parseInt(sc.nextLine());
			if(choice==1) {
				register();
			}
			else if(choice==2) {
				login();
			}
			else {
				break;
			}
		}

	}
	
	public static void register() {
		System.out.println("Enter your email:");
		String email=sc.nextLine();
		System.out.println("Enter your password:");
		String password=sc.nextLine();
		
		User userObj=new User(email,password,"customer");
		users.put(email, userObj);
		System.out.println("Registration Successful!");
	}
	
	public static void login() {
		System.out.println("Enter your email:");
		String email=sc.nextLine();
		System.out.println("Enter your password:");
		String password=sc.nextLine();
		if(users.containsKey(email) && users.get(email).password.equals(password)) {
			User user=users.get(email);
			if(user.role.equalsIgnoreCase("admin")) {
				adminMenu(user);
			}
			else {
				customerMenu(user);
			}
		}
		else {
			System.out.println("Invalid Details!");
		}
	}
	
	public static void adminMenu(User user) {
		while(true) {
			System.out.println("Welcome to Admin Menu:");
			System.out.println("1. Add Product\n2. Modify Product\n3. Delete Product\n4. View Products\n5. Search Product\n6. Add Credit\n7. Reports\n8. Logout");
            int ch=Integer.parseInt(sc.nextLine());
            
            if(ch==1) {
            	addProduct();
            }
            else if(ch==2) {
            	modifyProduct();
            }
            else if(ch==3) {
            	deleteProduct();
            }
            else if(ch==4) {
            	viewProduct();
            }
            else if(ch==5) {
            	searchProduct();
            }
            else if(ch==6) {
            	addCredit();
            }
            else if(ch==7) {
            	report();
            }
            else {
            	break;
            }
		}
	}
	
	public static void addProduct() {
		System.out.println("Enter product ID:");
		String id=sc.nextLine();
		System.out.println("Enter the name:");
		String name=sc.nextLine();
		System.out.println("Enter the price:");
		double price=Double.parseDouble(sc.nextLine());
		System.out.println("Enter the quantity:");
		int quantity=Integer.parseInt(sc.nextLine());
		products.put(id, new Product(id,name,price,quantity));
		System.out.println("Product added Successfully!");
	}
	
	public static void modifyProduct() {
		System.out.println("Enter the product ID to modify:");
		String id=sc.nextLine();
		if(products.containsKey(id)) {
			System.out.println("Enter new name:");
			String name=sc.nextLine();
			System.out.println("Enter new price:");
			double price=Double.parseDouble(sc.nextLine());
			System.out.println("Enter new quantity:");
			int quantity=Integer.parseInt(sc.nextLine());
			products.put(id, new Product(id,name,price,quantity));
			System.out.println("Product modified Successfully!");
		}
	}
	
	public static void deleteProduct() {
		System.out.println("Enter the product ID to delete:");
		String id=sc.nextLine();
		if(products.containsKey(id)) {
			products.remove(id);
			System.out.println("Product removed Successfully!");
		}
		else {
			System.out.println("Invalid product ID!");
		}
	}

	public static void viewProduct() {
		System.out.println("Product List:");
		for(Product i:products.values()) {
			System.out.println("ID:"+i.id+" | "+i.name+" | Rs."+i.price+" | "+i.quantity);
		}
	}

	public static void searchProduct() {
		System.out.println("Enter name to be search:");
		String name=sc.nextLine();
		for(Product i:products.values()) {
			if(i.name.equalsIgnoreCase(name)) {
				System.out.println("ID:"+i.id+" | "+i.name+" | Rs."+i.price+" | "+i.quantity);
			}
		}
	}

	public static void addCredit() {
		System.out.println("Enter the email:");
		String email=sc.nextLine();
		System.out.println("Enter the credit amount:");
		double credit=Double.parseDouble(sc.nextLine());
		if(users.containsKey(email)) {
			users.get(email).credit+=credit;
			System.out.println("Credit added successfully!");
		}
		else {
			System.out.println("Invalid email:");
		}
		
	}

	public static void report() {
	
	}
	
	public static void customerMenu(User user) {
		while(true) {
			System.out.println("Welcome to Customer Menu:\n1. View Products\n2. Add to Cart\n3. View Cart\n4. Checkout\n5. Purchase History\n6. Logout");
            int ch = Integer.parseInt(sc.nextLine());
            if(ch==1) {
            	viewProduct();
            }
            else if(ch==2) {
            	addToCart(user);
            }
            else if(ch==3) {
            	viewCart(user);
            }
            else if(ch==4) {
            	checkout(user);
            }
            else if(ch==5) {
            	purchaseHistory(user);
            }
            else {
            	break;
            }
		}
	}
	
	public static void addToCart(User user) {
		System.out.println("Enter the product ID:");
		String id=sc.nextLine();
		if(!products.containsKey(id)) {
			System.out.println("No product!");
			return;
		}
		System.out.println("Enter the quantity:");
		int quantity=Integer.parseInt(sc.nextLine());
		
		Product product=products.get(id);
		
		if(product.quantity<quantity) {
			System.out.println("Not enough stock!");
			return;
		}
		
		cart.putIfAbsent(user.email, new ArrayList<>());
		cart.get(user.email).add(new Cart(product,quantity));
	}
	
	public static void viewCart(User user) {
		ArrayList<Cart> list=cart.get(user.email);
		if(list.isEmpty()) {
			System.out.println("Empty Cart!");
			return;
		}
		double total=0;
		for(Cart i:list) {
			System.out.println(i.product.name+" x "+i.quantity+" = Rs."+(i.product.price*i.quantity));
			total+=i.product.price*i.quantity;
		}
		System.out.println("Total cost: Rs."+total);
	}
	
	public static void checkout(User user) {
		ArrayList<Cart> list=cart.get(user.email);
		if(list.isEmpty()) {
			System.out.println("Cart is empty!");
			return;
		}
		double total=0;
		for(Cart i:list) {
			total+=i.product.price*i.quantity;
		}
		
		double payable = total;
        if (user.loyaltyPoints >= 50) {
            payable -= 100;
            user.loyaltyPoints -= 50;
        }

        if (payable > user.credit) {
            System.out.println("Not enough credit");
            return;
        }

        user.credit -= payable;
        if (total >= 5000) {
        	user.credit += 100;
        }
        else {
        	user.loyaltyPoints += (int)(total / 100);
        }

        for (Cart i : list) {
        	i.product.quantity -= i.quantity;
        }

        String billId = "BILL" + System.currentTimeMillis();
        Purchase p = new Purchase(billId, LocalDate.now(), new ArrayList<>(list), total);
        user.purchaseHistory.add(p);
        cart.get(user.email).clear();

        System.out.println("Checkout successful! Paid: ₹" + payable);
	}
	public static void purchaseHistory(User user) {
		for (Purchase p : user.purchaseHistory) {
            System.out.println("Bill ID: " + p.purchaseId + ", Date: " + p.date + ", Total: ₹" + p.total);
        }
	}

}
