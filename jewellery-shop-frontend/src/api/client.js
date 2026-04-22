export function getApiBaseUrl() {
  const fromStorage = localStorage.getItem('apiBaseUrl')
  return fromStorage || import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'
}

export function setApiBaseUrl(url) {
  localStorage.setItem('apiBaseUrl', url)
}

export async function apiFetch(path, options = {}) {
  const baseUrl = getApiBaseUrl().replace(/\/+$/, '')
  const url = `${baseUrl}${path.startsWith('/') ? '' : '/'}${path}`

  const res = await fetch(url, {
    ...options,
    headers: {
      'Content-Type': 'application/json',
      ...(options.headers || {}),
    },
  })

  if (res.status === 204) return null

  const text = await res.text()
  const data = text ? safeJson(text) : null

  if (!res.ok) {
    const message =
      data?.message ||
      data?.error ||
      `Request failed (${res.status} ${res.statusText})`
    throw new Error(message)
  }

  return data
}

function safeJson(text) {
  try {
    return JSON.parse(text)
  } catch {
    return { message: text }
  }
}

