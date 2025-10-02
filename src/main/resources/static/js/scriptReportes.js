// SCRIPT PARA LA PAGINA DE REPORTES.HTML
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

  // Inicializar gráficos
  initializeCharts();
});

function initializeCharts() {
  // Gráfico de ventas mensuales
  const salesCtx = document.getElementById('salesChart').getContext('2d');
  new Chart(salesCtx, {
    type: 'line',
    data: {
      labels: ['Agos', 'Sept', 'Oct', 'Nov', 'Dic', 'Ene'],
      datasets: [{
        label: 'Ventas (S/)',
        data: [32000, 35500, 38200, 41800, 40600, 45680],
        borderColor: '#007bff',
        backgroundColor: 'rgba(0, 123, 255, 0.1)',
        borderWidth: 2,
        fill: true,
        tension: 0.4
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: {
          display: false
        }
      },
      scales: {
        y: {
          beginAtZero: false,
          grid: {
            color: 'rgba(0, 0, 0, 0.1)'
          }
        },
        x: {
          grid: {
            display: false
          }
        }
      }
    }
  });

  // Gráfico de categorías
  const categoryCtx = document.getElementById('categoryChart').getContext('2d');
  new Chart(categoryCtx, {
    type: 'doughnut',
    data: {
      labels: ['Colchones', 'Base Cama', 'Almohadas', 'Protectores'],
      datasets: [{
        data: [45, 25, 20, 10],
        backgroundColor: [
          '#007bff',
          '#28a745',
          '#ffc107',
          '#dc3545'
        ],
        borderWidth: 2,
        borderColor: '#fff'
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: {
          position: 'bottom'
        }
      }
    }
  });

  // Gráfico de estado de cotizaciones
  const quotesCtx = document.getElementById('quotesChart').getContext('2d');
  new Chart(quotesCtx, {
    type: 'bar',
    data: {
      labels: ['Pendientes', 'En Proceso', 'Contactadas', 'Cerradas', 'Rechazadas'],
      datasets: [{
        label: 'Cantidad',
        data: [12, 18, 8, 7, 3],
        backgroundColor: [
          '#ffc107',
          '#17a2b8',
          '#007bff',
          '#28a745',
          '#dc3545'
        ],
        borderWidth: 0
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: {
          display: false
        }
      },
      scales: {
        y: {
          beginAtZero: true,
          grid: {
            color: 'rgba(0, 0, 0, 0.1)'
          }
        },
        x: {
          grid: {
            display: false
          }
        }
      }
    }
  });

  // Gráfico de rendimiento por vendedor
  const performanceCtx = document.getElementById('performanceChart').getContext('2d');
  new Chart(performanceCtx, {
    type: 'radar',
    data: {
      labels: ['Ventas', 'Conversión', 'Clientes', 'Eficiencia', 'Satisfacción'],
      datasets: [
        {
          label: 'Fabrizio B.',
          data: [85, 82, 78, 90, 88],
          borderColor: '#007bff',
          backgroundColor: 'rgba(0, 123, 255, 0.2)',
          pointBackgroundColor: '#007bff'
        },
        {
          label: 'Shayuri G.',
          data: [78, 75, 82, 70, 85],
          borderColor: '#28a745',
          backgroundColor: 'rgba(40, 167, 69, 0.2)',
          pointBackgroundColor: '#28a745'
        },
        {
          label: 'Walter M.',
          data: [65, 60, 70, 65, 75],
          borderColor: '#ffc107',
          backgroundColor: 'rgba(255, 193, 7, 0.2)',
          pointBackgroundColor: '#ffc107'
        }
      ]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      scales: {
        r: {
          angleLines: {
            display: true
          },
          suggestedMin: 0,
          suggestedMax: 100
        }
      }
    }
  });
}

// Función para exportar reportes
function exportReport(format) {
  // Simular exportación
  const toast = document.createElement('div');
  toast.className = 'alert alert-success position-fixed top-0 end-0 m-3';
  toast.style.zIndex = '9999';
  toast.innerHTML = `Reporte exportado en formato ${format.toUpperCase()} exitosamente`;
  document.body.appendChild(toast);

  setTimeout(() => {
    toast.remove();
  }, 3000);
}

// Asignar eventos de exportación
document.querySelector('.btn-outline-success').addEventListener('click', () => exportReport('excel'));
document.querySelector('.btn-outline-danger').addEventListener('click', () => exportReport('pdf'));
