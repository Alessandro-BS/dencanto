// SCRIPT PARA LA PÁGINA DE USUARIOS.HTML
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

  // Función para filtrar usuarios
  document.querySelector('.search-container input').addEventListener('input', function (e) {
    const searchTerm = e.target.value.toLowerCase();
    const rows = document.querySelectorAll('tbody tr');

    rows.forEach(row => {
      const userName = row.querySelector('td:nth-child(3)').textContent.toLowerCase();
      if (userName.includes(searchTerm)) {
        row.style.display = '';
      } else {
        row.style.display = 'none';
      }
    });
  });

  // Generar contraseña temporal automáticamente
  document.getElementById('userPassword').addEventListener('focus', function () {
    if (!this.value) {
      const tempPassword = Math.random().toString(36).slice(-8);
      this.value = tempPassword;
    }
  });
});