// SCRIPT PARA LA PAGINA DE COTIZACIONES.HTML
const hamburger = document.querySelector(".toggle-btn");
const toggler = document.querySelector("#icon");
hamburger.addEventListener("click", function () {
  document.querySelector("#sidebar").classList.toggle("expand");
  toggler.classList.toggle("bxs-chevrons-right");
  toggler.classList.toggle("bxs-chevrons-left");
});

// CERRAR SESION EN LA INTRANET
function confirmLogout() {
  if (confirm('¿Está seguro de que desea cerrar sesión?')) {
    window.location.href = '/login';
  }
}
// Script para el sidebar toggle
document.addEventListener('DOMContentLoaded', function () {
  const toggleBtn = document.querySelector('.toggle-btn');
  const sidebar = document.getElementById('sidebar');
  const icon = document.getElementById('icon');

  toggleBtn.addEventListener('click', function () {
    sidebar.classList.toggle('collapsed');
    if (sidebar.classList.contains('collapsed')) {
      icon.classList.remove('bx-chevrons-right');
      icon.classList.add('bx-chevrons-left');
    } else {
      icon.classList.remove('bx-chevrons-left');
      icon.classList.add('bx-chevrons-right');
    }
  });

  // Función para filtrar cotizaciones 
  const searchInput = document.querySelector('.search-container input');
  if (searchInput) {
    searchInput.addEventListener('input', function (e) {
      const searchTerm = e.target.value.toLowerCase();
      const rows = document.querySelectorAll('tbody tr.quote-item');

      rows.forEach(row => {
        const clientNameElement = row.querySelector('td:nth-child(2)');
        if (clientNameElement) {
          const clientName = clientNameElement.textContent.toLowerCase();
          row.style.display = clientName.includes(searchTerm) ? '' : 'none';
        }
      });
    });
  }

  // Simular asignación automática 
  function simulateAutoAssignment() {
    const pendingQuotes = document.querySelectorAll('tr:has(.badge-pendiente)');

    pendingQuotes.forEach((quote, index) => {
      // Simular asignación automática
      setTimeout(() => {
        const assignBtn = quote.querySelector('.btn-outline-primary');
        if (assignBtn && assignBtn.innerHTML.includes('user-plus')) {
          assignBtn.classList.remove('btn-outline-primary');
          assignBtn.classList.add('btn-outline-success');
          assignBtn.innerHTML = '<i class="bx bx-check"></i>';

          const sellerCell = quote.querySelector('td:nth-child(5)');
          if (sellerCell) {
            sellerCell.innerHTML = `
              <div class="d-flex align-items-center">
                <img src="https://via.placeholder.com/30" class="customer-avatar me-2" alt="Vendedor">
                <span>Auto-Asignado</span>
              </div>
            `;
          }
        }
      }, 2000 * (index + 1));
    });
  }

  // Ejecutar simulación al cargar
  simulateAutoAssignment();
});