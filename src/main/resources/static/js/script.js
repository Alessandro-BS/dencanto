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




// Gráfico de barras
new Chart(document.getElementById("bar-chart-grouped"), {
  type: 'bar',
  data: {
    labels: ["2022", "2023", "2024", "2025"],
    datasets: [
      {
        label: "D' Encanto",
        backgroundColor: "#3e95cd",
        data: [133, 221, 783, 2478]
      }
    ]
  },
  options: {
    title: {
      display: true,
      text: 'Population growth (millions)'
    }
  }
});







