import { useEffect, useMemo, useState } from 'react'
import { Link } from 'react-router-dom'
import { addToCart, getCustomerId, listCategories, listProducts } from '../api/jewelleryApi.js'

export function ProductsPage() {
  const customerId = useMemo(() => getCustomerId(), [])
  const [categories, setCategories] = useState([])
  const [productsPage, setProductsPage] = useState(null)
  const [categoryId, setCategoryId] = useState('')
  const [q, setQ] = useState('')
  const [status, setStatus] = useState({ loading: true, error: null })

  async function load() {
    setStatus({ loading: true, error: null })
    try {
      const [cats, prods] = await Promise.all([
        listCategories(),
        listProducts({ page: 0, size: 24, categoryId: categoryId ? Number(categoryId) : undefined, q }),
      ])
      setCategories(cats)
      setProductsPage(prods)
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

  async function onSearch(e) {
    e.preventDefault()
    await load()
  }

  async function onAdd(product) {
    try {
      await addToCart(customerId, product.id, 1)
      alert('Added to cart')
    } catch (e) {
      alert(e.message || String(e))
    }
  }

  return (
    <div className="stack">
      <div className="pageHeader">
        <div className="rowBetween">
          <div>
            <h1>Products</h1>
            <p className="muted">
              Browse jewellery items and add to cart. Seed data is already available.
            </p>
          </div>
          <Link className="btn secondary" to="/admin/products/new">
            + Add Product
          </Link>
        </div>
      </div>

      <form className="filters" onSubmit={onSearch}>
        <select className="input" value={categoryId} onChange={(e) => setCategoryId(e.target.value)}>
          <option value="">All categories</option>
          {categories.map((c) => (
            <option key={c.id} value={c.id}>
              {c.name}
            </option>
          ))}
        </select>
        <input
          className="input"
          placeholder="Search by name or SKU..."
          value={q}
          onChange={(e) => setQ(e.target.value)}
        />
        <button className="btn" type="submit">
          Search
        </button>
      </form>

      {status.loading && <div className="card">Loading...</div>}
      {status.error && <div className="card error">{status.error}</div>}

      {productsPage && (
        <div className="grid">
          {productsPage.content.map((p) => (
            <div className="productCard" key={p.id}>
              <div className="productImg">
                {p.imageUrl ? (
                  <img src={p.imageUrl} alt={p.name} />
                ) : (
                  <div className="imgPlaceholder">No image</div>
                )}
              </div>
              <div className="productBody">
                <div className="rowBetween">
                  <div>
                    <div className="productTitle">
                      <Link to={`/products/${p.id}`}>{p.name}</Link>
                    </div>
                    <div className="muted small">{p.sku} • {p.categoryName}</div>
                  </div>
                  <div className="price">₹{p.price}</div>
                </div>
                <div className="rowBetween" style={{ marginTop: 10 }}>
                  <div className="muted small">Stock: {p.stockQty}</div>
                  <button className="btn" disabled={p.stockQty <= 0} onClick={() => onAdd(p)}>
                    Add
                  </button>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  )
}

