

## 1. Jerarquía y Diseño de Clases

User (clase abstracta)
├── Buyer
├── Seller
├── LogisticsOperator
├── Administrator
└── Supervisor

Product (clase abstracta)
├── PhysicalProduct
└── DigitalProduct

## 2. Dominios y Atributos Principales

### Módulo de Usuarios y Roles
User (Abstract)
- id (String / Long): Identificador único.
- fullName (String): Nombre oficial del usuario.
- email (String): Correo electrónico.
- role (UserRole - Enum): Rol asignado.
- status (UserStatus - Enum): Estado operativo (ACTIVE, BLOCKED, etc.).

Buyer (Extiende User)
- mainAddress (String)
- additionalAddresses (List<String>)
- commercialStatus (String)

Seller (Extiende User)
- companyName (String)
- taxId (String)

LogisticsOperator / Administrator / Supervisor: Atributos específicos según su área.

### Módulo de Inventario y Catálogo
Product (Abstract)
- id (String)
- name (String)
- price (double)
- status (ProductStatus - Enum)
- sellerId (String)

PhysicalProduct (Extiende Product)
- weight (double)
- dimensions (String)
- variants (List<String>)

DigitalProduct (Extiende Product)
- downloadUrl (String)
- fileSize (double)

Warehouse
- id, name, address, type (WarehouseType)

Inventory
- id, productId, warehouseId, availableStock (int >= 0), reservedStock (int)

### Módulo de Operaciones Comerciales y Logística
CartItem
- productId (String), quantity (int)

ShoppingCart
- buyerId (String), items (List<CartItem>)

Order
- id, buyerId, items (List<OrderItem>), totalAmount, status (OrderStatus)

## 3. Enumeraciones
- UserRole: BUYER, SELLER, LOGISTICS_OPERATOR, ADMINISTRATOR, SUPERVISOR.
- UserStatus: ACTIVE, BLOCKED, INACTIVE.
- ProductStatus: PUBLISHED, SUSPENDED, DISCONTINUED.
- WarehouseType: MARKETPLACE, SELLER.
- OrderStatus: CART, PENDING_PAYMENT, PAID, DISPATCHED, DELIVERED.

## 4. Capa de Puertos (Ports) - Arquitectura DDD

La capa de puertos representa los contratos que el dominio usa para comunicar con infraestructura externa, especialmente para persistencia y acceso a datos. Esta capa se ubica en `src/main/java/com/nexusmarket/ports/outbound/`.

### 4.1 ProductRepositoryPort
Define las operaciones para persistir y consultar productos:
- `save(Product product)`
- `findById(String id)`
- `findAll()`
- `findByStatus(ProductStatus status)`
- `delete(String id)`

### 4.2 InventoryRepositoryPort
Define las operaciones para manejar el inventario por producto y almacén:
- `save(Inventory inventory)`
- `findById(String id)`
- `findByWarehouseId(String warehouseId)`
- `findByProductId(String productId)`
- `findByProductAndWarehouse(String productId, String warehouseId)`

### 4.3 OrderRepositoryPort
Define los métodos para la gestión de órdenes del comprador:
- `save(Order order)`
- `findById(String id)`
- `findByBuyerId(String buyerId)`
- `findByStatus(OrderStatus status)`

### 4.4 UserRepositoryPort
Define los métodos para gestionar usuarios por identidad y rol:
- `save(User user)`
- `findById(String id)`
- `findByEmail(String email)`
- `findByRole(UserRole role)`

Este enfoque mantiene el dominio independiente de la infraestructura, permitiendo implementar adaptadores concretos luego con JPA, memoria, o cualquier otra tecnología de persistencia.

## 5. Capa de Casos de Uso (Inbound Ports)

Los puertos inbound representan los casos de uso de negocio que la capa de aplicación expone al resto del sistema. Se encuentran en `src/main/java/com/nexusmarket/ports/inbound/`.

### 5.1 ManageCatalogUseCase
Define las operaciones de administración del catálogo:
- `registerProduct(Product product)`
- `updateProductStatus(String productId, String status)`
- `getAllProducts()`
- `getProductById(String productId)`

### 5.2 ManageInventoryUseCase
Define las operaciones para manejar el stock y el inventario por almacén:
- `registerStock(Inventory inventory)`
- `updateStockQuantity(String inventoryId, int quantity)`
- `getInventoryByWarehouse(String warehouseId)`

### 5.3 ProcessOrderUseCase
Define las acciones de creación y consulta de órdenes:
- `createOrder(Order order)`
- `updateOrderStatus(String orderId, OrderStatus newStatus)`
- `getOrdersByBuyer(String buyerId)`
- `getOrderDetails(String orderId)`

## 6. Capa de Servicios de Aplicación

La implementación de la lógica de negocio se ubica en `src/main/java/com/nexusmarket/application/` y conecta los puertos inbound con los puertos outbound.

### 6.1 CatalogService
Implementa `ManageCatalogUseCase` y coordina la creación, consulta y actualización de estado de productos usando `ProductRepositoryPort`.

Reglas de negocio incluidas:
- guardar un producto nuevo
- buscar producto por id
- listar todos los productos
- actualizar su estado a partir de un valor de enum `ProductStatus`

### 6.2 InventoryService
Implementa `ManageInventoryUseCase` y valida que el stock no sea negativo antes de persistir cambios.

Reglas de negocio incluidas:
- registrar stock del inventario
- actualizar cantidad de stock
- validar que la operación no deje existencias negativas
- consultar inventario por almacén

### 6.3 OrderService
Implementa `ProcessOrderUseCase` y gestiona la creación y actualización de pedidos.

Reglas de negocio incluidas:
- crear una orden con estado `PENDING_PAYMENT`
- actualizar el estado del pedido según `OrderStatus`
- impedir modificar un pedido ya entregado
- consultar pedidos por comprador y detalle por id

## 7. Estructura Actual del Proyecto

src/main/java/com/nexusmarket/
├── application/
│   ├── CatalogService.java
│   ├── InventoryService.java
│   └── OrderService.java
├── model/
│   ├── catalog/
│   ├── inventory/
│   ├── orders/
│   ├── users/
│   └── enums/
├── ports/
│   ├── inbound/
│   │   ├── ManageCatalogUseCase.java
│   │   ├── ManageInventoryUseCase.java
│   │   └── ProcessOrderUseCase.java
│   └── outbound/
│       ├── ProductRepositoryPort.java
│       ├── InventoryRepositoryPort.java
│       ├── OrderRepositoryPort.java
│       └── UserRepositoryPort.java

Esta estructura sigue el patrón DDD: dominio, puertos y servicios de aplicación desacoplados de la infraestructura externa.

---

