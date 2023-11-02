class Product:

	def __init__(self, ID, name, description, unit_price, quantity_stock):
		self.ID = ID
		self.name = name
		self.description = description
		self.unit_price = unit_price
		self.quantity_stock = quantity_stock
		self.inventory_value = unit_price * quantity_stock

	def __str__(self):
		return f"[ID:{self.ID} Name:{self.name} Description:{self.description} Unit Price:${self.unit_price} Quantity in Stock:{self.quantity_stock} Inventory Value:${self.inventory_value}]"

class InventoryManager:

	def __init__(self):
		self.inventory_list = []

	def generar_id(self):
		next_id = 1
		# Encuentra el próximo número disponible
		while any(producto.ID == f'IN{str(next_id).zfill(3)}' for producto in self.inventory_list):
			next_id += 1

		# Formatea el número con ceros a la izquierda y agrega el prefijo
		nuevo_id = f'IN{str(next_id).zfill(3)}'
		return nuevo_id

	def agregar_producto(self, producto):
		if not isinstance(producto.unit_price, (int, float)) or not isinstance(producto.quantity_stock, (int, float)):
			print("Input incorrectos")
			return

		# Genera un nuevo ID para el producto
		producto.ID = self.generar_id()
		# Agrega el producto a la lista
		print("Producto agregado con exito")
		self.inventory_list.append(producto)

	def obtener_lista_productos(self):
		return self.inventory_list

	def agregar_stock(self, producto_id, cantidad):
		if cantidad < 0:
			print("Cantidad debe ser positiva")
			return

		producto = next((producto for producto in self.inventory_list if producto.ID == producto_id), None)
		if producto == None:
			print("No existe producto con ID " + producto_id)
			return

		
		producto.quantity_stock += cantidad
		print("Cantidad añadida con exito. Stock actual: " + str(producto.quantity_stock))	


	def remover_stock(self, producto_id, cantidad):
		if cantidad < 0:
			print("Cantidad debe ser positiva")
			return

		producto = next((producto for producto in self.inventory_list if producto.ID == producto_id), None)
		if producto == None:
			print("No existe producto con ID " + producto_id)
			return

		if producto.quantity_stock < cantidad:
			print("Cantidad debe ser igual o menor a la cantidad actual: " + str(producto.quantity_stock))
			return
		
		producto.quantity_stock -= cantidad
		print("Cantidad removida con exito. Stock actual: " + str(producto.quantity_stock))

	def eliminar_producto(self, producto_id):
		producto = next((producto for producto in self.inventory_list if producto.ID == producto_id), None)
		if producto == None:
			print("No hay producto con ID: " + producto_id)
			return
		if producto.quantity_stock > 0:
			print("Solo se pueden eliminar productos sin stock")
			return

		self.inventory_list.remove(producto)
		print("Producto ID: " + producto.ID + " eliminado con exito.")



inventory_managment = InventoryManager()

producto1 = Product(None, "Pizza", "Con mucho queso", 5, 100)
producto2 = Product(None, "Empanada", "De carne", 3, 100)
producto3 = Product(None, "Hamburguesa", "Doble carne", 9, 100)


inventory_managment.agregar_producto(producto1)
inventory_managment.agregar_producto(producto2)
inventory_managment.agregar_producto(producto3)

inventory_managment.agregar_stock("IN001", 40)
inventory_managment.agregar_stock("IN003", 2)


print(f"\nInventario: ")
for producto in sorted(inventory_managment.obtener_lista_productos(), key=lambda p: p.ID):
	print(producto)

producto4 = Product("x", "PC", "intel i3", 45, 100)
producto5 = Product("x", "Monitor", "24 pulgadas", 33, 100)
producto6 = Product("x", "Teclado", "gamer", 19, 100)

inventory_managment.agregar_producto(producto4)

inventory_managment.eliminar_producto("IN002");
inventory_managment.eliminar_producto("IN001");

inventory_managment.remover_stock("IN002", 100);
inventory_managment.remover_stock("IN001", 100);

inventory_managment.eliminar_producto("IN001");
inventory_managment.eliminar_producto("IN002");

inventory_managment.agregar_producto(producto5)
inventory_managment.agregar_producto(producto6)

print("\nInventario: ")
for producto in sorted(inventory_managment.obtener_lista_productos(), key=lambda p: p.ID):
	print(producto)

input("\nPresione enter para cerrar")
