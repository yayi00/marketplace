# NexusMarket Domain Model

Basado en las especificaciones del documento NexusMarket y los requerimientos del profesor, aquí tienes el análisis del modelo de dominio y la estructura de las clases para la implementación en Java.

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

---

