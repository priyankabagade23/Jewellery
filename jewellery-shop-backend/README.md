# Jewellery Shop Backend

A comprehensive Spring Boot backend application for jewellery retail selling system with advanced features including inventory management, promotions, authentication, and search capabilities.

## Features

### Core Functionality
- **Product Management**: Complete jewellery catalog with categories, collections, materials, and gemstones
- **Customer Management**: Customer profiles and order history
- **Order Processing**: Shopping cart and checkout functionality
- **Inventory Management**: Stock tracking with transaction history
- **Promotion System**: Discount codes and promotional campaigns
- **Authentication & Authorization**: JWT-based security with role-based access
- **Advanced Search**: Multi-criteria product search with filters
- **API Documentation**: OpenAPI/Swagger documentation

### Jewellery-Specific Features
- **Material Tracking**: Gold, silver, platinum, and other metals with purity levels
- **Gemstone Management**: Diamonds, rubies, sapphpheres, emeralds with carat weights
- **Collections**: Seasonal and themed jewellery collections
- **Customization Options**: Support for customizable jewellery items
- **Weight & Size Tracking**: Detailed product specifications

## Technology Stack

- **Framework**: Spring Boot 3.3.12
- **Database**: H2 (in-memory) with JPA/Hibernate
- **Security**: Spring Security with JWT authentication
- **Documentation**: OpenAPI 3.0 with Swagger UI
- **Build Tool**: Maven
- **Java Version**: 17

## Project Structure

```
src/main/java/com/example/jewellery/
├── entity/                    # Domain entities
│   ├── cart/                 # Shopping cart entities
│   ├── category/             # Product categories
│   ├── collection/           # Jewellery collections
│   ├── customer/             # Customer entities
│   ├── gemstone/             # Gemstone entities
│   ├── inventory/            # Inventory management
│   ├── material/             # Material entities
│   ├── order/                # Order entities
│   ├── product/              # Product entities
│   ├── promotion/            # Discount and promotions
│   └── user/                # User authentication
├── repository/               # Data access layer
├── service/                  # Business logic layer
│   ├── auth/                # Authentication services
│   ├── cart/                # Cart services
│   ├── category/            # Category services
│   ├── customer/            # Customer services
│   ├── order/               # Order services
│   ├── product/             # Product services
│   └── search/              # Search services
├── controller/               # REST API controllers
│   ├── auth/                # Authentication endpoints
│   ├── cart/                # Cart endpoints
│   ├── category/            # Category endpoints
│   ├── customer/            # Customer endpoints
│   ├── order/               # Order endpoints
│   ├── product/             # Product endpoints
│   └── search/              # Search endpoints
├── dto/                      # Data transfer objects
│   ├── cart/                # Cart DTOs
│   ├── category/            # Category DTOs
│   ├── customer/            # Customer DTOs
│   ├── order/               # Order DTOs
│   ├── product/             # Product DTOs
│   └── search/              # Search DTOs
├── common/                   # Common utilities
├── config/                   # Configuration classes
├── security/                 # Security configuration
└── seed/                     # Data seeding
```

## Database Schema

### Core Entities
- **Products**: Jewellery items with detailed specifications
- **Categories**: Product categorization (Rings, Necklaces, Earrings, etc.)
- **Collections**: Seasonal/themed collections
- **Materials**: Metals and materials with purity tracking
- **Gemstones**: Precious and semi-precious stones
- **Customers**: Customer information and profiles
- **Orders**: Order management and tracking
- **Cart**: Shopping cart functionality
- **Inventory**: Stock management and transactions
- **Promotions**: Discount and campaign management

## API Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration
- `POST /api/auth/logout` - User logout

### Products
- `GET /api/products` - Get all products (with pagination)
- `GET /api/products/{id}` - Get product by ID
- `POST /api/products` - Create new product (Admin only)
- `PUT /api/products/{id}` - Update product (Admin only)
- `DELETE /api/products/{id}` - Delete product (Admin only)

### Search & Filtering
- `GET /api/search/products` - Advanced product search
- `GET /api/search/filters` - Get available filter options
- `GET /api/search/suggestions` - Get search suggestions

### Categories
- `GET /api/categories` - Get all categories
- `POST /api/categories` - Create category (Admin only)
- `PUT /api/categories/{id}` - Update category (Admin only)

### Collections
- `GET /api/collections` - Get active collections
- `POST /api/collections` - Create collection (Admin only)

### Shopping Cart
- `GET /api/cart` - Get user's cart
- `POST /api/cart/add` - Add item to cart
- `PUT /api/cart/update` - Update cart item
- `DELETE /api/cart/remove/{id}` - Remove item from cart

### Orders
- `GET /api/orders` - Get user's orders
- `POST /api/orders/checkout` - Process checkout
- `GET /api/orders/{id}` - Get order details

## Getting Started

### Prerequisites
- Java 17 or later
- Maven 3.6 or later

### Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd jewellery-shop-backend
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on port 8080 by default.

### Access Points

- **API Base URL**: `http://localhost:8080/api`
- **Swagger UI**: `http://localhost:8080/swagger`
- **H2 Console**: `http://localhost:8080/h2`
  - JDBC URL: `jdbc:h2:mem:jewellery`
  - Username: `sa`
  - Password: (empty)

## Configuration

### Application Properties
The application can be configured via `application.properties`:

```properties
# Server Configuration
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:h2:mem:jewellery
spring.datasource.username=sa
spring.datasource.password=

# JWT Configuration
jwt.secret=mySecretKeyForJewelleryShopBackend2024
jwt.expiration=86400

# CORS Configuration
cors.allowed-origins=http://localhost:3000,http://localhost:5173
```

## Data Seeding

The application includes a comprehensive data seeder that initializes:
- Jewellery categories (Rings, Necklaces, Earrings, Bracelets, Watches, Brooches)
- Collections (Spring Bloom, Royal Heritage, Ocean Dreams)
- Materials (Gold 24K, Gold 18K, Silver 925, Platinum, etc.)
- Gemstones (Diamond, Ruby, Sapphire, Emerald, Amethyst, Topaz)
- Sample products with materials and gemstones
- Customer accounts
- Discount promotions

## Security

### Authentication
- JWT-based authentication
- Role-based authorization (CUSTOMER, MANAGER, ADMIN)
- Password encryption with BCrypt

### Authorization
- Public endpoints: Authentication, product browsing
- Customer endpoints: Cart, orders, profile management
- Admin endpoints: Product management, category management, inventory

## Advanced Features

### Search & Filtering
The search system supports:
- Full-text search across product names, descriptions, SKUs
- Filter by category, collection, material, gemstone
- Price range filtering
- Metal type and style filtering
- Weight-based filtering
- Customization options
- Pagination and sorting

### Inventory Management
- Stock quantity tracking
- Transaction history (purchases, sales, returns, adjustments)
- Cost tracking per item
- Low stock alerts

### Promotion System
- Discount codes with usage limits
- Percentage and fixed amount discounts
- Minimum order requirements
- Time-based promotions
- Category, collection, or product-specific discounts

## Development

### Adding New Features
1. Create/update entities in the appropriate package
2. Add repository interfaces
3. Implement service layer logic
4. Create REST controllers
5. Add DTOs for data transfer
6. Update documentation

### Testing
Run the test suite:
```bash
mvn test
```

### API Documentation
Access the interactive API documentation at `http://localhost:8080/swagger`

## Production Considerations

For production deployment:
1. Replace H2 with a production database (PostgreSQL, MySQL)
2. Configure proper JWT secret
3. Set up CORS for your frontend domain
4. Configure proper logging
5. Set up monitoring and health checks
6. Use HTTPS
7. Configure proper backup strategies

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## License

This project is licensed under the MIT License.
