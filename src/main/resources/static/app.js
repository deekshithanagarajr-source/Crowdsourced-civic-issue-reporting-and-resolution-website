// ====== Shared frontend logic ======
const API = "http://localhost:8080";

// ---- Session helpers ----
function getUser() {
  try { return JSON.parse(localStorage.getItem("civic_user")); }
  catch { return null; }
}
function setUser(u) { localStorage.setItem("civic_user", JSON.stringify(u)); }
function logout() { localStorage.removeItem("civic_user"); window.location.href = "login.html"; }

function requireAuth() {
  const u = getUser();
  if (!u) { window.location.href = "login.html"; return null; }
  return u;
}
function redirectIfAuthed() {
  if (getUser()) { window.location.href = "dashboard.html"; }
}

// ---- Navbar ----
function renderNavbar(activePage) {
  const u = getUser();
  const host = document.getElementById("navbar");
  if (!host) return;
  if (!u) {
    host.innerHTML = `
      <div class="brand">Civic<span>Reporter</span></div>
      <nav>
        <a href="login.html"    class="${activePage==='login'?'active':''}">Login</a>
        <a href="register.html" class="${activePage==='register'?'active':''}">Register</a>
      </nav>`;
    return;
  }
  host.innerHTML = `
    <div class="brand">Civic<span>Reporter</span></div>
    <nav>
      <a href="dashboard.html"    class="${activePage==='dashboard'?'active':''}">Dashboard</a>
      <a href="create-issue.html" class="${activePage==='create'?'active':''}">Create Issue</a>
      <a href="view-issues.html"  class="${activePage==='view'?'active':''}">View Issues</a>
    </nav>
    <div class="user">
      Hi, <strong>${escapeHtml(u.username)}</strong>
      <button onclick="logout()">Logout</button>
    </div>`;
}

// ---- Alerts ----
function showAlert(id, type, message) {
  const el = document.getElementById(id);
  if (!el) return;
  el.className = `alert ${type} show`;
  el.textContent = message;
}
function clearAlert(id) {
  const el = document.getElementById(id);
  if (el) { el.className = "alert"; el.textContent = ""; }
}

// ---- Fetch wrapper ----
async function api(path, options = {}) {
  const res = await fetch(API + path, {
    headers: { "Content-Type": "application/json" },
    ...options,
  });
  let body = {};
  try { body = await res.json(); } catch (_) {}
  if (!res.ok) {
    const msg = body.message
      || (body.errors && Object.values(body.errors).join(", "))
      || `Request failed (${res.status})`;
    throw new Error(msg);
  }
  return body;
}

// ---- Utils ----
function escapeHtml(s) {
  return String(s ?? "")
    .replaceAll("&","&amp;").replaceAll("<","&lt;").replaceAll(">","&gt;")
    .replaceAll('"',"&quot;").replaceAll("'","&#39;");
}
function statusClass(s) {
  if (s === "Pending") return "pending";
  if (s === "In Progress") return "in-progress";
  if (s === "Resolved") return "resolved";
  return "pending";
}
function formatDate(iso) {
  if (!iso) return "";
  const d = new Date(iso);
  if (isNaN(d)) return iso;
  return d.toLocaleString();
}
