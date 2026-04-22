# Jewellery Shopping System (Full Stack)

This workspace contains a small full-stack **Jewellery Shopping System**:

- **Backend (Spring Boot)**: `jewellery-shop-backend/`
- **Frontend (React + Vite)**: `jewellery-shop-frontend/`

## Backend (Spring Boot)

### Run

Open a terminal in:
`jewellery-shop-backend`

```bash
mvn spring-boot:run
```

The backend starts on a **random free port** (to avoid "port already in use").
Look for a log line like:

`Tomcat started on port 59738 (http)`

### Swagger UI

Open:
`http://localhost:<BACKEND_PORT>/swagger`

### H2 Console

Open:
`http://localhost:<BACKEND_PORT>/h2`

## Frontend (React)

### Run

Open a terminal in:
`jewellery-shop-frontend`

```bash
npm run dev
```

Open:
`http://localhost:5173/`

### Connect frontend to backend

In the top-right header:

- Set **API** to `http://localhost:<BACKEND_PORT>` (the port from backend logs)
- Set **Customer** to `1` (seeded customer)

## Seeded demo data

On backend start, it seeds:

- Categories: Rings, Necklaces, Earrings
- Products: a few items with stock
- Customer: `demo@example.com` (id usually `1`)

## Implemented APIs (controller/service/repository layers)

- `GET/POST/PUT/DELETE /api/categories`
- `GET/POST/PUT/DELETE /api/products` (+ search & filter)
- `GET/POST /api/customers`
- `GET/POST/PUT/DELETE /api/customers/{customerId}/cart`
- `POST /api/customers/{customerId}/checkout`
- `GET /api/customers/{customerId}/orders`
- `GET /api/orders/{orderId}`

