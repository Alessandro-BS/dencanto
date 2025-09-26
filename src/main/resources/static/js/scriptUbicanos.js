
      document.addEventListener("DOMContentLoaded", function () {
        const contactForm = document.getElementById("contactForm");

        contactForm.addEventListener("submit", function (e) {
          e.preventDefault();

          // Aquí normalmente enviarías el formulario a tu servidor
          // Por ahora, solo mostraremos una alerta de éxito

          // Validación básica
          const nombre = document.getElementById("nombre").value;
          const email = document.getElementById("email").value;
          const mensaje = document.getElementById("mensaje").value;

          if (nombre && email && mensaje) {
            // Simulación de envío exitoso
            alert("¡Gracias por tu mensaje! Te contactaremos pronto.");
            contactForm.reset();
          } else {
            alert("Por favor, completa todos los campos obligatorios.");
          }
        });
      });