import { apiFetch } from './client.js'

export function getCustomerId() {
  const fromEnv = import.meta.env.VITE_CUSTOMER_ID
  const fromStorage = localStorage.getItem('customerId')
  const raw = fromStorage || fromEnv || '1'
  const n = Number(raw)
  return Number.isFinite(n) ? n : 1
}

export function setCustomerId(id) {
  localStorage.setItem('customerId', String(id))
}

export async function listCategories() {
  return apiFetch('/api/categories')
}

export async function listProducts({ page = 0, size = 20, categoryId, q } = {}) {
  const params = new URLSearchParams()
  params.set('page', String(page))
  params.set('size', String(size))
  if (categoryId) params.set('categoryId', String(categoryId))
  if (q) params.set('q', q)
  return apiFetch(`/api/products?${params.toString()}`)
}

export async function getProduct(id) {
  return apiFetch(`/api/products/${id}`)
}

export async function createProduct(payload) {
  return apiFetch('/api/products', {
    method: 'POST',
    body: JSON.stringify(payload),
  })
}

export async function getCart(customerId) {
  return apiFetch(`/api/customers/${customerId}/cart`)
}

export async function addToCart(customerId, productId, quantity) {
  return apiFetch(`/api/customers/${customerId}/cart/items`, {
    method: 'POST',
    body: JSON.stringify({ productId, quantity }),
  })
}

export async function updateCartItem(customerId, productId, quantity) {
  return apiFetch(`/api/customers/${customerId}/cart/items`, {
    method: 'PUT',
    body: JSON.stringify({ productId, quantity }),
  })
}

export async function removeCartItem(customerId, productId) {
  return apiFetch(`/api/customers/${customerId}/cart/items/${productId}`, {
    method: 'DELETE',
  })
}

export async function clearCart(customerId) {
  return apiFetch(`/api/customers/${customerId}/cart`, { method: 'DELETE' })
}

export async function checkout(customerId, shippingAddress) {
  return apiFetch(`/api/customers/${customerId}/checkout`, {
    method: 'POST',
    body: JSON.stringify({ shippingAddress }),
  })
}

