# Marketplace

Marketplace es un proyecto Java orientado a modelar un dominio de e-commerce con catálogo, inventario, pedidos y usuarios, siguiendo una estructura de arquitectura hexagonal con casos de uso y puertos.

## Estado del proyecto

El proyecto ya presenta una base correcta de separación de capas:

- Dominio: com.nexusmarket.model
- Casos de uso / puertos inbound: com.nexusmarket.ports.inbound
- Puertos outbound: com.nexusmarket.ports.outbound
- Servicios de aplicación: com.nexusmarket.application
- Adaptadores in-memory: com.nexusmarket.adapters.inmemory

Sin embargo, no es una arquitectura hexagonal completamente madura todavía: el dominio aún necesita reforzar invariantes y el sistema no está completamente alineado con un DDD estricto, aunque sí está bien encaminado.

## Revisión arquitectónica

### Qué está bien

- La separación conceptual entre dominio y aplicación existe.
- Los ports describen contratos claros para persistencia y casos de uso.
- Los servicios de aplicación son el punto de coordinación entre puertos inbound y outbound.
- El modelo de dominio refleja las principales entidades del negocio: productos, stock, pedidos y usuarios.

### Qué está incompleto o problemático

- El dominio es todavía relativamente anémico; muchas reglas quedan en `Service` y no en el agregado.
- Los estados de pedido y producto no tienen validaciones de transición robustas.
- Faltan adaptadores de infraestructura reales más allá de in-memory.
- Hay roles de usuario incompletos en el dominio si se compara con la especificación documental.
- No existe una capa de composición de dependencias formal ni un punto de entrada de aplicación.

### Conclusión

La arquitectura actual es una base viable y legible, pero se encuentra en una etapa intermedia entre un diseño orientado a capas y una arquitectura hexagonal completamente madura.

## Dominio identificado

### Catalog

- Product (abstracta)
  - PhysicalProduct
  - DigitalProduct
- Atributos principales: id, name, price, status, sellerId

### Inventory

- Inventory
- Warehouse

### Orders

- Order
- OrderItem
- ShoppingCart
- CartItem

### Users

- User (abstracta)
  - Buyer
  - Seller
  - Administrator
  - LogisticsOperator
  - Supervisor

## Puertos y casos de uso

### Inbound

- ManageCatalogUseCase
- ManageInventoryUseCase
- ProcessOrderUseCase

### Outbound

- ProductRepositoryPort
- InventoryRepositoryPort
- OrderRepositoryPort
- UserRepositoryPort

## Adaptadores

Actualmente se han incorporado adaptadores in-memory para permitir una implementación funcional sin infraestructura externa:

- com.nexusmarket.adapters.inmemory.InMemoryProductRepository
- com.nexusmarket.adapters.inmemory.InMemoryInventoryRepository
- com.nexusmarket.adapters.inmemory.InMemoryOrderRepository
- com.nexusmarket.adapters.inmemory.InMemoryUserRepository

Estos adaptadores cumplen el papel de infraestructura de prueba/persistencia temporal.

## Servicios de aplicación

### CatalogService

Función principal: registrar, consultar y actualizar productos.

Revisión:

- Hace la coordinación básica entre catálogo y repositorio.
- Debe validar duplicados y transiciones de estado más estrictamente.
- Se recomienda usar `ProductStatus` como tipo de entrada en vez de `String` para evitar errores.

### InventoryService

Función principal: validación de stock y consultas por almacén.

Revisión:

- Valida stock negativo y operación de actualización.
- Debe reforzar la validación de producto y almacén asociado.
- Debe separar claramente movimientos de stock, reservas y ajustes.

### OrderService

Función principal: creación y actualización de pedidos.

Revisión:

- Asigna `PENDING_PAYMENT` durante la creación.
- Tiene control básico de pedidos entregados.
- Debe mejorar la validación de estado de transición y la consistencia con stock.
- Debe evitar que un pedido sin artículos o con estados inválidos pase por la capa de aplicación.

## Problemas detectados de diseño

1. Dominio demasiado anémico.
2. Reglas de negocio dispersas en servicios.
3. Falta de validación fuerte de transiciones de estado.
4. Uso de `String` en entradas de negocio cuando existen enums.
5. Falta de adaptadores de infraestructura no in-memory.
6. No existe un punto de ensamblaje claro para la composición de dependencias.
7. El modelo de usuarios tiene roles incompletos si se compara con la documentación.

## Principios y arquitectura

### DDD

Se cumple parcialmente:

- Entidades y aggregates del dominio presentes.
- Casos de uso definidos.
- Relación lógica entre capas existe.

Se debe reforzar:

- más lógica dentro de las entidades/agregados
- más validación de invariantes en el dominio
- menos responsabilidad en los servicios

### SOLID

- Single Responsibility: aceptable, pero los services aún hacen demasiadas cosas.
- Open/Closed: aceptable para extensiones por interfaces.
- Liskov: razonablemente bien cuando se usan jerarquías de `User` y `Product`.
- Interface Segregation: los ports son simples y específicos.
- Dependency Inversion: se cumple en la capa de aplicación, pero falta más separación si se quiere infraestructura real.

## Estructura del proyecto

src/main/java/com/nexusmarket/
├── adapters/
│   └── inmemory/
├── application/
├── model/
│   ├── catalog/
│   ├── enums/
│   ├── inventory/
│   ├── orders/
│   └── users/
├── ports/
│   ├── inbound/
│   └── outbound/
└── ...

## Plan recomendado de implementación

### Paso 1

Consolidar la estructura final de paquetes.

### Paso 2

Reforzar validaciones del dominio: Producto, Inventory, Order y User.

### Paso 3

Completar roles de usuario para cumplir con el modelo documental.

### Paso 4

Definir mejor los ports y su separación real por contexto del negocio.

### Paso 5

Crear una capa de infra real (JPA, JDBC, repository, persistence) fuera del dominio.

### Paso 6

Introducir un punto de composición de dependencias para construir la aplicación.

### Paso 7

Revisar la transición de estados para productos y pedidos.

## Verificación actual

Se compiló el proyecto con `javac` y la base del sistema sigue funcionando sin errores de compilación.

Esto confirma que la estructura actual es funcional y estable, aunque todavía puede mejorar en rigor de arquitectura y dominio.

## Conclusión

El proyecto se encuentra en una buena fase de organización, con una base correcta para seguir hacia una arquitectura hexagonal más fuerte. El punto de mejora principal es reforzar las reglas del dominio y dejar más lógica dentro de las entidades/agregados para que la capa de aplicación quede más enfocado en orquestación y casos de uso.
