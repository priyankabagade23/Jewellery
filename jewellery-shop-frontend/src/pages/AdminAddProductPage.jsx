import { useEffect, useMemo, useState } from 'react'
import { Link } from 'react-router-dom'
import { createProduct, listCategories } from '../api/jewelleryApi.js'

function slugSku(prefix = 'PRD') {
  const n = Math.floor(Math.random() * 9000) + 1000
  return `${prefix}-${n}`
}

export function AdminAddProductPage() {
  const [categories, setCategories] = useState([])
  const [status, setStatus] = useState({ loading: true, error: null })
  const [submitStatus, setSubmitStatus] = useState({ loading: false, error: null })
  const [created, setCreated] = useState(null)

  const [form, setForm] = useState({
    sku: slugSku('RNG'),
    name: '',
    description: '',
    price: '9999.00',
    stockQty: '10',
    imageUrl: '',
    categoryId: '',
  })

  const canSubmit = useMemo(() => {
    if (!form.sku.trim()) return false
    if (!form.name.trim()) return false
    if (!form.categoryId) return false
    const price = Number(form.price)
    const qty = Number(form.stockQty)
    if (!Number.isFinite(price) || price <= 0) return false
    if (!Number.isFinite(qty) || qty < 0) return false
    return true
  }, [form])

  useEffect(() => {
    let cancelled = false
    async function load() {
      setStatus({ loading: true, error: null })
      try {
        const cats = await listCategories()
        if (cancelled) return
        setCategories(cats)
        setForm((f) => ({ ...f, categoryId: cats[0]?.id ? String(cats[0].id) : '' }))
        setStatus({ loading: false, error: null })
      } catch (e) {
        if (cancelled) return
        setStatus({ loading: false, error: e.message || String(e) })
      }
    }
    load()
    return () => {
      cancelled = true
    }
  }, [])

  function setField(key, value) {
    setForm((f) => ({ ...f, [key]: value }))
  }

  async function onSubmit(e) {
    e.preventDefault()
    setCreated(null)
    setSubmitStatus({ loading: true, error: null })
    try {
      const payload = {
        sku: form.sku.trim(),
        name: form.name.trim(),
        description: form.description.trim() || null,
        price: Number(form.price),
        stockQty: Number(form.stockQty),
        imageUrl: form.imageUrl.trim() || null,
        categoryId: Number(form.categoryId),
      }
      const p = await createProduct(payload)
      setCreated(p)
      setSubmitStatus({ loading: false, error: null })
    } catch (e2) {
      setSubmitStatus({ loading: false, error: e2.message || String(e2) })
    }
  }

  return (
    <div className="stack">
      <div className="pageHeader">
        <h1>Admin · Add Product</h1>
        <p className="muted">Creates a new product using `POST /api/products`.</p>
      </div>

      <div className="card">
        <div className="rowBetween">
          <Link to="/products" className="btn secondary">
            Back
          </Link>
          <button className="btn secondary" onClick={() => setField('sku', slugSku('PRD'))}>
            Generate SKU
          </button>
        </div>
      </div>

      {status.loading && <div className="card">Loading categories...</div>}
      {status.error && <div className="card error">{status.error}</div>}

      {!status.loading && !status.error && (
        <form className="card formGrid" onSubmit={onSubmit}>
          <div>
            <div className="muted small">SKU</div>
            <input className="input" value={form.sku} onChange={(e) => setField('sku', e.target.value)} />
          </div>
          <div>
            <div className="muted small">Category</div>
            <select className="input" value={form.categoryId} onChange={(e) => setField('categoryId', e.target.value)}>
              {categories.map((c) => (
                <option key={c.id} value={c.id}>
                  {c.name}
                </option>
              ))}
            </select>
          </div>

          <div style={{ gridColumn: '1 / -1' }}>
            <div className="muted small">Name</div>
            <input className="input" value={form.name} onChange={(e) => setField('name', e.target.value)} />
          </div>

          <div style={{ gridColumn: '1 / -1' }}>
            <div className="muted small">Description</div>
            <textarea
              className="input"
              rows={3}
              value={form.description}
              onChange={(e) => setField('description', e.target.value)}
              style={{ width: '100%' }}
            />
          </div>

          <div>
            <div className="muted small">Price (₹)</div>
            <input
              className="input"
              type="number"
              step="0.01"
              min="0"
              value={form.price}
              onChange={(e) => setField('price', e.target.value)}
            />
          </div>
          <div>
            <div className="muted small">Stock Qty</div>
            <input
              className="input"
              type="number"
              min="0"
              value={form.stockQty}
              onChange={(e) => setField('stockQty', e.target.value)}
            />
          </div>

          <div style={{ gridColumn: '1 / -1' }}>
            <div className="muted small">Image URL (optional)</div>
            <input className="input" value={form.imageUrl} onChange={(e) => setField('imageUrl', e.target.value)} />
          </div>

          {submitStatus.error && <div className="errorBox" style={{ gridColumn: '1 / -1' }}>{submitStatus.error}</div>}

          <div className="rowBetween" style={{ gridColumn: '1 / -1' }}>
            <div className="muted small">
              {created ? (
                <>
                  Created: <span className="mono">#{created.id}</span>
                </>
              ) : (
                'Fill the form and create product'
              )}
            </div>
            <div className="row">
              {created && (
                <Link className="btn secondary" to={`/products/${created.id}`}>
                  View
                </Link>
              )}
              <button className="btn" type="submit" disabled={!canSubmit || submitStatus.loading}>
                {submitStatus.loading ? 'Creating...' : 'Create product'}
              </button>
            </div>
          </div>
        </form>
      )}
    </div>
  )
}

