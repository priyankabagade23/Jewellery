import { useMemo, useState } from 'react'
import { Link } from 'react-router-dom'
import { checkout, getCustomerId } from '../api/jewelleryApi.js'

export function CheckoutPage() {
  const customerId = useMemo(() => getCustomerId(), [])
  const [shippingAddress, setShippingAddress] = useState(
    'Demo Address, Street 1, City, State, India'
  )
  const [status, setStatus] = useState({ loading: false, error: null })
  const [order, setOrder] = useState(null)

  async function placeOrder() {
    setStatus({ loading: true, error: null })
    try {
      const o = await checkout(customerId, shippingAddress)
      setOrder(o)
      setStatus({ loading: false, error: null })
    } catch (e) {
      setStatus({ loading: false, error: e.message || String(e) })
    }
  }

  return (
    <div className="stack">
      <div className="pageHeader">
        <h1>Checkout</h1>
        <p className="muted">Create an order from your cart.</p>
      </div>

      <div className="card">
        <div className="muted small">Customer ID</div>
        <div className="mono">{customerId}</div>

        <div style={{ marginTop: 12 }}>
          <div className="muted small">Shipping address</div>
          <textarea
            className="input"
            rows={3}
            value={shippingAddress}
            onChange={(e) => setShippingAddress(e.target.value)}
            style={{ width: '100%' }}
          />
        </div>

        {status.error && <div className="errorBox">{status.error}</div>}

        <div className="row" style={{ marginTop: 12 }}>
          <button className="btn" onClick={placeOrder} disabled={status.loading}>
            {status.loading ? 'Placing...' : 'Place order'}
          </button>
          <Link className="btn secondary" to="/cart">
            Back to cart
          </Link>
        </div>
      </div>

      {order && (
        <div className="card">
          <div className="rowBetween">
            <div>
              <div className="muted small">Order created</div>
              <div className="mono">#{order.id}</div>
            </div>
            <div className="priceLarge">₹{order.totalAmount}</div>
          </div>

          <div className="muted" style={{ marginTop: 10 }}>
            Status: {order.status} • Items: {order.items.length}
          </div>

          <div className="table" style={{ marginTop: 12 }}>
            <div className="thead">
              <div>Item</div>
              <div>Qty</div>
              <div>Unit</div>
              <div>Total</div>
            </div>
            {order.items.map((it) => (
              <div className="trow" key={it.productId}>
                <div>
                  <div className="productTitle">{it.name}</div>
                  <div className="muted small">{it.sku}</div>
                </div>
                <div>{it.quantity}</div>
                <div>₹{it.unitPrice}</div>
                <div>₹{it.lineTotal}</div>
              </div>
            ))}
          </div>

          <div className="muted" style={{ marginTop: 10 }}>
            Tip: your cart was cleared after checkout.
          </div>
        </div>
      )}
    </div>
  )
}

