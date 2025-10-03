const hamburger = document.querySelector(".toggle-btn");
const toggler = document.querySelector("#icon");

// Inicializar gráficos cuando la página carga
document.addEventListener('DOMContentLoaded', function() {
    initializeCharts();
});

hamburger.addEventListener("click", function () {
    document.querySelector("#sidebar").classList.toggle("expand");
    toggler.classList.toggle("bxs-chevrons-right");
    toggler.classList.toggle("bxs-chevrons-left");
});

    


function initializeCharts() {
    // Gráfico de ventas mensuales personales
    const salesCtx = document.getElementById('salesChart').getContext('2d');
    new Chart(salesCtx, {
        type: 'bar',
        data: {
            labels: ['Oct', 'Nov', 'Dic', 'Ene'],
            datasets: [{
                label: 'Mis Ventas (S/)',
                data: [12500, 14200, 15800, 18250],
                backgroundColor: '#007bff',
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

    // Gráfico de distribución por categoría
    const categoryCtx = document.getElementById('categoryChart').getContext('2d');
    new Chart(categoryCtx, {
        type: 'doughnut',
        data: {
            labels: ['Colchones', 'Base Cama', 'Almohadas', 'Protectores'],
            datasets: [{
                data: [65, 20, 10, 5],
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
}
