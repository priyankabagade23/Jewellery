import { useEffect, useMemo, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import { addToCart, getCustomerId, getProduct } from '../api/jewelleryApi.js'

export function ProductDetailsPage() {
  const { id } = useParams()
  const customerId = useMemo(() => getCustomerId(), [])
  const [product, setProduct] = useState(null)
  const [qty, setQty] = useState(1)
  const [status, setStatus] = useState({ loading: true, error: null })

  useEffect(() => {
    let cancelled = false
    async function load() {
      setStatus({ loading: true, error: null })
      try {
        const p = await getProduct(id)
        if (!cancelled) setProduct(p)
      } catch (e) {
        if (!cancelled) setStatus({ loading: false, error: e.message || String(e) })
        return
      }
      if (!cancelled) setStatus({ loading: false, error: null })
    }
    load()
    return () => {
      cancelled = true
    }
  }, [id])

  async function onAdd() {
    try {
      await addToCart(customerId, product.id, qty)
      alert('Added to cart')
    } catch (e) {
      alert(e.message || String(e))
    }
  }

  if (status.loading) return <div className="card">Loading...</div>
  if (status.error) return <div className="card error">{status.error}</div>
  if (!product) return <div className="card">Not found</div>

  return (
    <div className="stack">
      <div className="breadcrumbs">
        <Link to="/products">← Back to products</Link>
      </div>

      <div className="details">
        <div className="detailsImg">
          {product.imageUrl ? (
            <img src={product.imageUrl} alt={product.name} />
          ) : (
            <div className="imgPlaceholder">No image</div>
          )}
        </div>
        <div className="detailsBody">
          <h1 style={{ margin: 0 }}>{product.name}</h1>
          <div className="muted">
            {product.sku} • {product.categoryName}
          </div>
          <div className="priceLarge">₹{product.price}</div>
          <div className="muted">Stock: {product.stockQty}</div>

          <div className="row" style={{ marginTop: 14 }}>
            <label className="muted">Qty</label>
            <input
              className="input"
              type="number"
              min={1}
              max={product.stockQty}
              value={qty}
              onChange={(e) => setQty(Number(e.target.value || 1))}
              style={{ width: 100 }}
            />
            <button className="btn" onClick={onAdd} disabled={product.stockQty <= 0}>
              Add to cart
            </button>
            <Link className="btn secondary" to="/cart">
              Go to cart
            </Link>
          </div>

          {product.description && (
            <div className="card" style={{ marginTop: 16 }}>
              <div className="muted small">Description</div>
              <div style={{ marginTop: 6 }}>{product.description}</div>
            </div>
          )}
        </div>
      </div>
    </div>
  )
}

