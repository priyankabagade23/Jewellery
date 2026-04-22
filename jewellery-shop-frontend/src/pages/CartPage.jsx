import { useEffect, useMemo, useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import {
  clearCart,
  getCart,
  getCustomerId,
  removeCartItem,
  updateCartItem,
} from '../api/jewelleryApi.js'

export function CartPage() {
  const navigate = useNavigate()
  const customerId = useMemo(() => getCustomerId(), [])
  const [cart, setCart] = useState(null)
  const [status, setStatus] = useState({ loading: true, error: null })

  async function load() {
    setStatus({ loading: true, error: null })
    try {
      const c = await getCart(customerId)
      setCart(c)
    } catch (e) {
      setStatus({ loading: false, error: e.message || String(e) })
      return
    }
    setStatus({ loading: false, error: null })
  }

  useEffect(() => {
    load()
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  async function onQtyChange(productId, nextQty) {
    try {
      const c = await updateCartItem(customerId, productId, nextQty)
      setCart(c)
    } catch (e) {
      alert(e.message || String(e))
    }
  }

  async function onRemove(productId) {
    const c = await removeCartItem(customerId, productId)
    setCart(c)
  }

  async function onClear() {
    await clearCart(customerId)
    await load()
  }

  if (status.loading) return <div className="card">Loading...</div>
  if (status.error) return <div className="card error">{status.error}</div>

  const items = cart?.items || []
  const isEmpty = items.length === 0

  return (
    <div className="stack">
      <div className="pageHeader">
        <h1>Cart</h1>
        <p className="muted">Customer ID: {customerId}</p>
      </div>

      {isEmpty ? (
        <div className="card">
          Your cart is empty. <Link to="/products">Browse products</Link>.
        </div>
      ) : (
        <>
          <div className="card">
            <div className="table">
              <div className="thead">
                <div>Item</div>
                <div>Qty</div>
                <div>Price</div>
                <div>Total</div>
                <div></div>
              </div>
              {items.map((it) => (
                <div className="trow" key={it.productId}>
                  <div>
                    <div className="productTitle">{it.name}</div>
                    <div className="muted small">{it.sku}</div>
                  </div>
                  <div>
                    <input
                      className="input"
                      type="number"
                      min={1}
                      value={it.quantity}
                      onChange={(e) => onQtyChange(it.productId, Number(e.target.value || 1))}
                      style={{ width: 90 }}
                    />
                  </div>
                  <div>₹{it.unitPrice}</div>
                  <div>₹{it.lineTotal}</div>
                  <div>
                    <button className="btn secondary" onClick={() => onRemove(it.productId)}>
                      Remove
                    </button>
                  </div>
                </div>
              ))}
            </div>
          </div>

          <div className="rowBetween">
            <button className="btn secondary" onClick={onClear}>
              Clear cart
            </button>
            <div className="card" style={{ minWidth: 280 }}>
              <div className="rowBetween">
                <div className="muted">Total</div>
                <div className="priceLarge">₹{cart.total}</div>
              </div>
              <button className="btn" style={{ width: '100%', marginTop: 10 }} onClick={() => navigate('/checkout')}>
                Checkout
              </button>
            </div>
          </div>
        </>
      )}
    </div>
  )
}

