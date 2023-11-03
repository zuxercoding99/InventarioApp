import java.util.*;
import java.util.stream.*;

public class InventarioApp {

	public static void main(String[] args) {
		InventoryManager IM = new InventoryManager();
		
		Product producto1 = new Product(null, "Lapiz", "de color", 1, 1000);
		Product producto2 = new Product(null, "Paquete x100 Hojas", "lisas blancas", 3, 200);
		Product producto3 = new Product(null, "Tijeras", "tamaño mediano", 2, 500);

		IM.agregarProducto(producto1);
		IM.agregarProducto(producto2);
		IM.agregarProducto(producto3);

		System.out.println();

		IM.agregarStock("IN001", 400);
		IM.agregarStock("IN003", 200);

		System.out.println();
		System.out.println("Inventario:");
		IM.obtenerListaProductos().stream().sorted(Comparator.comparing(p -> p.getID())).forEach(System.out::println);

		Product producto4 = new Product(null, "Cuaderno", "x100 hojas", 4, 300);
		Product producto5 = new Product(null, "Lapiceras", "azules", 1, 1200);
		Product producto6 = new Product(null, "Mochilas", "tamaño mediano", 10, 100);

		IM.agregarProducto(producto4);

		System.out.println();

		IM.eliminarProducto("IN001");
		IM.eliminarProducto("IN002");

		System.out.println();

		IM.removerStock("IN001", 1400);
		IM.removerStock("IN002", 180);

		System.out.println();

		IM.eliminarProducto("IN001");
		IM.eliminarProducto("IN002");

		System.out.println();

		IM.agregarProducto(producto5);
		IM.agregarProducto(producto6);

		System.out.println();

		System.out.println();
		System.out.println("Inventario:");
		IM.obtenerListaProductos().stream().sorted(Comparator.comparing(p -> p.getID())).forEach(System.out::println);

	}
}

class Product {

	private String ID;
	private String name;
	private String description;
	private double unitPrice;
	private int quantityStock;
	private double inventoryValue;

	public Product(String ID, String name, String description, double unitPrice, int quantityStock) {
		this.ID = ID;
		this.name = name;
		this.description = description;
		this.unitPrice = unitPrice;
		this.quantityStock = quantityStock;
		this.inventoryValue = unitPrice * quantityStock;
	}

	@Override
	public String toString() {
		return String.format("[ID:%s Name:%s Description:%s Unit Price:$%.2f Quantity in Stock:%d Inventory Value:$%.2f]", this.ID, this.name, this.description, this.unitPrice, this.quantityStock, this.inventoryValue);
	}

	public String getID(){
		return this.ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public int getQuantityStock(){
		return this.quantityStock;
	}

	public void setQuantityStock(int quantity){

	}
}

class InventoryManager {

	List<Product> inventoryList;

	public InventoryManager(){
		inventoryList = new ArrayList<>();
	}

	private String generarId(){
        final int[] nextID = {1};
        while (inventoryList.stream().anyMatch(product -> product.getID().equals("IN" + String.format("%03d", nextID[0])))) {
            nextID[0]++;
        }
        return "IN" + String.format("%03d", nextID[0]);
	}

	public void agregarProducto(Product product) {
		product.setID(this.generarId());

		System.out.println("Producto agregado con exito");
		this.inventoryList.add(product);
	}

	public List<Product> obtenerListaProductos() {
		return this.inventoryList;
	}

	public void agregarStock(String productId, int quantity) {

		if (quantity < 0) {
			System.out.println("Cantidad debe ser positiva");
			return;
		}

		Product product = this.inventoryList.stream().filter(p -> p.getID().equals(productId)).findFirst().orElse(null);

		if (product == null) {
			System.out.println("No hay un producto con el ID "+ productId);
			return;
		}

		product.setQuantityStock(quantity);
		System.out.println("Cantidad añadida con exito");
	}

	public void removerStock(String productId, int quantity) {

		if (quantity < 0) {
			System.out.println("Cantidad debe ser positiva");
			return;
		}

		Product product = this.inventoryList.stream().filter(p -> p.getID().equals(productId)).findFirst().orElse(null);

		if (product == null) {
			System.out.println("No hay un producto con el ID "+ productId);
			return;
		}

		if (quantity > product.getQuantityStock()) {
			System.out.println("Cantidad debe ser igual o menor a al stock actual");
			return;
		}

		product.setQuantityStock(product.getQuantityStock() - quantity);
		System.out.println("Cantidad removida con exito. Cantidad actual: " + product.getQuantityStock());			
	}

	public void eliminarProducto(String productoId) {
		Product product = this.inventoryList.stream().filter(p -> p.getID().equals(productoId)).findFirst().orElse(null);

		if (product == null) {
			System.out.println("No hay producto con ID " + productoId);
			return;
		}

		this.inventoryList.remove(product);
		System.out.println("Producto eliminado con exito.");

	}

	
}