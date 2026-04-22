import { NavLink, Outlet } from 'react-router-dom'
import { getApiBaseUrl, setApiBaseUrl } from '../api/client.js'
import { getCustomerId, setCustomerId } from '../api/jewelleryApi.js'
import { useMemo, useState } from 'react'

export function Layout() {
  const [customerIdInput, setCustomerIdInput] = useState(String(getCustomerId()))
  const [apiBaseInput, setApiBaseInput] = useState(() => getApiBaseUrl())
  const apiBase = useMemo(() => getApiBaseUrl(), [])

  function saveCustomerId() {
    const n = Number(customerIdInput)
    if (!Number.isFinite(n) || n <= 0) return
    setCustomerId(n)
    window.location.reload()
  }

  function saveApiBase() {
    const url = apiBaseInput.trim().replace(/\/+$/, '')
    if (!url.startsWith('http://') && !url.startsWith('https://')) return
    setApiBaseUrl(url)
    window.location.reload()
  }

  return (
    <div className="app">
      <header className="topbar">
        <div className="brand">
          <div className="logo">JS</div>
          <div>
            <div className="brandTitle">Jewellery Shop</div>
            <div className="brandSub">Demo full-stack system</div>
          </div>
        </div>

        <nav className="nav">
          <NavLink to="/products">Products</NavLink>
          <NavLink to="/cart">Cart</NavLink>
          <NavLink to="/checkout">Checkout</NavLink>
        </nav>

        <div className="envBox">
          <div className="envRow">
            <span className="muted">API</span>
            <input
              className="input mono"
              value={apiBaseInput}
              onChange={(e) => setApiBaseInput(e.target.value)}
              style={{ width: 220 }}
            />
            <button className="btn" onClick={saveApiBase}>
              Set
            </button>
          </div>
          <div className="envRow">
            <span className="muted">Customer</span>
            <input
              className="input"
              value={customerIdInput}
              onChange={(e) => setCustomerIdInput(e.target.value)}
              style={{ width: 70 }}
            />
            <button className="btn" onClick={saveCustomerId}>
              Set
            </button>
          </div>
        </div>
      </header>

      <main className="container">
        <Outlet />
      </main>
    </div>
  )
}

