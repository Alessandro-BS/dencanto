// SCRIPT PARA LA PÁGINA DE PRODUCTOS.HTML
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

  // Simulación de datos para la tabla
  const products = [
    {
      id: '001',
      name: 'Colchón Queen Size Memory Foam',
      category: 'Colchones',
      price: 'S/ 1,250.00',
      stock: 15,
      status: 'Disponible'
    }
  ];

  // Función para filtrar productos
  document.querySelector('.search-container input').addEventListener('input', function (e) {
    const searchTerm = e.target.value.toLowerCase();
    const rows = document.querySelectorAll('tbody tr');

    rows.forEach(row => {
      const productName = row.querySelector('td:nth-child(3)').textContent.toLowerCase();
      if (productName.includes(searchTerm)) {
        row.style.display = '';
      } else {
        row.style.display = 'none';
      }
    });
  });
});