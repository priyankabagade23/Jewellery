import { Navigate, Route, Routes } from 'react-router-dom'
import { Layout } from './components/Layout.jsx'
import { ProductsPage } from './pages/ProductsPage.jsx'
import { ProductDetailsPage } from './pages/ProductDetailsPage.jsx'
import { CartPage } from './pages/CartPage.jsx'
import { CheckoutPage } from './pages/CheckoutPage.jsx'
import { AdminAddProductPage } from './pages/AdminAddProductPage.jsx'

function App() {
  return (
    <Routes>
      <Route element={<Layout />}>
        <Route path="/" element={<Navigate to="/products" replace />} />
        <Route path="/products" element={<ProductsPage />} />
        <Route path="/products/:id" element={<ProductDetailsPage />} />
        <Route path="/admin/products/new" element={<AdminAddProductPage />} />
        <Route path="/cart" element={<CartPage />} />
        <Route path="/checkout" element={<CheckoutPage />} />
      </Route>
    </Routes>
  )
}

export default App
