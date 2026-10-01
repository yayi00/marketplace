## 1. Jerarquía y diseño de clases

User (clase abstracta)
├── Buyer
├── Seller
├── LogisticsOperator
├── Administrator
└── Supervisor

Product (clase abstracta)
├── PhysicalProduct
└── DigitalProduct

## 2. Dominios y atributos principales

### Módulo de usuarios y roles

User (abstract)
- id: String
- fullName: String
- email: String
- role: UserRole
- status: UserStatus

Buyer (extiende User)
- mainAddress: String
- additionalAddresses: List<String>
- commercialStatus: String

Seller (extiende User)
- companyName: String
- taxId: String

LogisticsOperator / Administrator / Supervisor
- atributos específicos por tipo de operación y gestión

### Módulo de inventario y catálogo

Product (abstract)
- id: String
- name: String
- price: double
- status: ProductStatus
- sellerId: String

PhysicalProduct (extiende Product)
- weight: double
- dimensions: String
- variants: List<String>

DigitalProduct (extiende Product)
- downloadUrl: String
- fileSize: double

Warehouse
- id, name, address, type

Inventory
- id, productId, warehouseId, availableStock, reservedStock

### Módulo de operaciones comerciales y logística

CartItem
- productId: String
- quantity: int

ShoppingCart
- buyerId: String
- items: List<CartItem>

Order
- id: String
- buyerId: String
- items: List<OrderItem>
- totalAmount: double
- status: OrderStatus

## 3. Enumeraciones

- UserRole: BUYER, SELLER, LOGISTICS_OPERATOR, ADMINISTRATOR, SUPERVISOR
- UserStatus: ACTIVE, BLOCKED, INACTIVE
- ProductStatus: PUBLISHED, SUSPENDED, DISCONTINUED
- WarehouseType: MARKETPLACE, SELLER
- OrderStatus: CART, PENDING_PAYMENT, PAID, DISPATCHED, DELIVERED

## 4. Capa de puertos (ports) - arquitectura DDD

La capa de puertos representa los contratos que el dominio usa para comunicar con infraestructura externa, especialmente para persistencia y acceso a datos.

### 4.1 ProductRepositoryPort

Define operaciones de persistencia y consulta de productos:
- save(Product product)
- findById(String id)
- findAll()
- findByStatus(ProductStatus status)
- delete(String id)

### 4.2 InventoryRepositoryPort

Define operaciones para manejar el inventario por producto y almacén:
- save(Inventory inventory)
- findById(String id)
- findByWarehouseId(String warehouseId)
- findByProductId(String productId)
- findByProductAndWarehouse(String productId, String warehouseId)

### 4.3 OrderRepositoryPort

Define métodos para la gestión de órdenes del comprador:
- save(Order order)
- findById(String id)
- findByBuyerId(String buyerId)
- findByStatus(OrderStatus status)

### 4.4 UserRepositoryPort

Define métodos para gestionar usuarios por identidad y rol:
- save(User user)
- findById(String id)
- findByEmail(String email)
- findByRole(UserRole role)

Este enfoque mantiene el dominio independiente de la infraestructura, permitiendo luego implementar adaptadores concretos con memoria, JPA, JDBC o cualquier tecnología.

## 5. Capa de casos de uso (inbound ports)

Los inbound ports representan los casos de uso de negocio que la capa de aplicación expone.

### 5.1 ManageCatalogUseCase

- registerProduct(Product product)
- updateProductStatus(String productId, String status)
- getAllProducts()
- getProductById(String productId)

### 5.2 ManageInventoryUseCase

- registerStock(Inventory inventory)
- updateStockQuantity(String inventoryId, int quantity)
- getInventoryByWarehouse(String warehouseId)

### 5.3 ProcessOrderUseCase

- createOrder(Order order)
- updateOrderStatus(String orderId, OrderStatus newStatus)
- getOrdersByBuyer(String buyerId)
- getOrderDetails(String orderId)

## 6. Capa de servicios de aplicación

La lógica de negocio se coordina en com.nexusmarket.application.

### 6.1 CatalogService

Coordina la creación, consulta y actualización del catálogo.

Reglas actuales soportadas:
- guardar un producto nuevo
- buscar por id
- listar productos
- cambiar el estado con enum

Riesgos y mejoras:
- usar `ProductStatus` en vez de `String` para evitar errores de parseo
- validar sellerId, duplicados y transiciones de estado

### 6.2 InventoryService

Valida el stock antes de persistir cambios.

Reglas actuales soportadas:
- registrar stock
- actualizar inventario
- evitar existencias negativas
- consultar por almacén

Riesgos y mejoras:
- validar producto/almacén associated
- diferenciar ajuste, reserva y liberación de stock
- evitar lógica de negocio dispersa en varias capas

### 6.3 OrderService

Gestiona creación y actualización del pedido.

Reglas actuales soportadas:
- crear orden en `PENDING_PAYMENT`
- actualizar estado
- impedir modificación si ya entregado
- consultar pedidos por comprador

Riesgos y mejoras:
- validar transiciones del estado de pedido
- validar que el pedido tenga artículos
- comprobar stock antes de confirmar pago o despacho

## 7. Estructura actual del proyecto

src/main/java/com/nexusmarket/
├── adapters/
│   └── inmemory/
├── application/
│   ├── CatalogService.java
│   ├── InventoryService.java
│   └── OrderService.java
├── model/
│   ├── catalog/
│   ├── enums/
│   ├── inventory/
│   ├── orders/
│   └── users/
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

## 8. Revisión de arquitectura: ¿es hexagonal?

Sí, tiene la base de una arquitectura hexagonal y DDD:

- hay capa de dominio
- existen puertos
- la aplicación depende de interfaces, no de infraestructura
- los adapters se pueden implementar para memoria, base de datos o APIs

Pero aún no es una implementación hexagonal completamente madura porque:

- el dominio sigue siendo anémico en varios agregados
- la lógica de validación y estados está repartida en servicios
- faltan adapters reales y configuración de composición de dependencias
- no existe una capa de infraestructura clara y completa

Conclusión:

La estructura es correcta y funcional como base, pero para ser una arquitectura hexagonal robusta debe reforzarse la lógica de dominio y la separación entre infraestructura y casos de uso.

## 9. Problemas detectados de diseño

1. Dominio anémico
2. Reglas de negocio concentradas en servicios
3. Validación incompleta de estados y transiciones
4. Uso de `String` para parámetros tipados con enums
5. Adaptadores limitados a memoria
6. Falta de layer de infraestructura completa
7. Roles de usuario incompletos según el modelo documental
8. Ausencia de composición de dependencias formal
9. Faltan agregados más estrictos para inventario y pedido

## 10. Principios SOLID y DDD

### SOLID

- SRP: parcialmente bien, aunque servicios aceptan demasiada lógica.
- OCP: aceptable por interfaces.
- LSP: razonablemente bien en jerarquías de `User` y `Product`.
- ISP: buena separación en ports.
- DIP: correcto en el sentido de que la capa de aplicación depende de interfaces, no de implementaciones.

### DDD

Se cumple parcialmente:

- entidades y enums bien ubicados
- casos de uso aislados
- separación de contexto de negocio

Se debe reforzar:

- mover más reglas al dominio
- validar invariantes en entidades
- manejar agregados con dominio más expresivo

## 11. Estado actual del dominio

El dominio actual cubre composición básica del negocio, y se ha reforzado la validación de entidades y la completitud de roles de usuario. Entre los archivos clave del dominio están:

- Product.java
- Inventory.java
- Order.java
- OrderItem.java
- User.java
- Buyer.java
- Seller.java
- Administrator.java
- LogisticsOperator.java
- Supervisor.java

## 12. Plan recomendado de implementación

### Paso 1: consolidar la estructura

Mantener esta organización base:

- application/
- model/
- ports/inbound/
- ports/outbound/
- adapters/inmemory/

### Paso 2: reforzar dominio

- validaciones de ingreso
- no permitir valores nulos o inválidos
- estados regulatorios inmutables por decisión de negocio

### Paso 3: completar usuarios

- define roles de administrador, supervisor y operador logístico
- mantener jerarquía de `User`

### Paso 4: mejorar use cases

- definir validaciones más fuertes en `CatalogService`, `InventoryService` y `OrderService`
- evitar que el servicio haga demasiado trabajo de dominio

### Paso 5: crear adapters reales

- in-memory para pruebas
- JPA o JDBC para persistencia real
- repositorios externos desacoplados del dominio

### Paso 6: preparar composición de dependencias

- punto de ensamblaje para inyectar repositorios en servicios
- evitar acoplamiento directo de implementación

## 13. Verificación del proyecto

Se verificó la compilación del proyecto con `javac` y la base funcional sigue siendo válida. Eso indica que la implementación actual es estable y compilable, aunque todavía puede ser mejorada para una arquitectura más estricta y expresiva.

## 14. Conclusión

El proyecto ya tiene una base sana y clara para arquitectura hexagonal, y la documentación aquí refleja ese punto de partida. La evolución recomendada es reforzar el dominio, separar mejor reglas de negocio y ampliar la infraestructura con adaptadores reales sin romper la estructura ya establecida.

---

