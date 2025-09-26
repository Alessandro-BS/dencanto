document.addEventListener("DOMContentLoaded", function () {
    // Preguntas frecuentes sobre la funcionalidad del acordeón
    const faqQuestions = document.querySelectorAll(".faq-question");

    faqQuestions.forEach((question) => {
        question.addEventListener("click", function () {
            // Toggle active class on question
            this.classList.toggle("active");

            // Toggle answer visibility
            const answer = this.nextElementSibling;
            if (answer && answer.classList.contains("faq-answer")) {
                answer.classList.toggle("show");
            }

            // Close other open answers
            faqQuestions.forEach((otherQuestion) => {
                if (otherQuestion !== question) {
                    otherQuestion.classList.remove("active");
                    const otherAnswer = otherQuestion.nextElementSibling;
                    if (otherAnswer && otherAnswer.classList.contains("faq-answer")) {
                        otherAnswer.classList.remove("show");
                    }
                }
            });
        });
    });

    // Desplazamiento suave para la navegación por categorías
    const categoryLinks = document.querySelectorAll(".category-nav .nav-link");

    categoryLinks.forEach((link) => {
        link.addEventListener("click", function (e) {
            e.preventDefault();

            // Eliminar la clase activa de todos los enlaces
            categoryLinks.forEach((l) => l.classList.remove("active"));

            // Agregar clase activa al enlace en el que se hizo clic
            this.classList.add("active");

            // Scroll to target category
            const targetId = this.getAttribute("href");
            const targetElement = document.querySelector(targetId);

            if (targetElement) {
                window.scrollTo({
                    top: targetElement.offsetTop - 100,
                    behavior: "smooth",
                });
            }
        });
    });

    // Funcionalidad de búsqueda
    const searchInput = document.getElementById("faqSearch");

    if (searchInput) {
        searchInput.addEventListener("input", function () {
            const searchTerm = this.value.toLowerCase().trim();
            const faqItems = document.querySelectorAll(".faq-item");

            faqItems.forEach((item) => {
                const questionElement = item.querySelector(".faq-question");
                const answerElement = item.querySelector(".faq-answer");
                
                if (!questionElement || !answerElement) return;

                const question = questionElement.textContent.toLowerCase();
                const answer = answerElement.textContent.toLowerCase();

                if (question.includes(searchTerm) || answer.includes(searchTerm)) {
                    item.style.display = "block";

                    // Si está en respuesta, expandir para mostrar
                    if (answer.includes(searchTerm)) {
                        questionElement.classList.add("active");
                        answerElement.classList.add("show");
                    }
                } else {
                    item.style.display = "none";
                    // Ocultar también los elementos abiertos por la búsqueda
                    questionElement.classList.remove("active");
                    answerElement.classList.remove("show");
                }
            });

            // Mostrar/ocultar títulos de categorías según los elementos visibles
            const categories = document.querySelectorAll(".faq-category");

            categories.forEach((category) => {
                const visibleItems = category.querySelectorAll(".faq-item");
                let hasVisibleItems = false;

                visibleItems.forEach((item) => {
                    if (item.style.display !== "none") {
                        hasVisibleItems = true;
                    }
                });

                category.style.display = hasVisibleItems ? "block" : "none";
            });

            // Si la búsqueda está vacía, mostrar todas las categorías
            if (searchTerm === "") {
                categories.forEach((category) => {
                    category.style.display = "block";
                });
                
                // Cerrar todas las preguntas abiertas por búsqueda
                faqItems.forEach((item) => {
                    const questionElement = item.querySelector(".faq-question");
                    const answerElement = item.querySelector(".faq-answer");
                    
                    if (questionElement && answerElement) {
                        questionElement.classList.remove("active");
                        answerElement.classList.remove("show");
                    }
                });
            }
        });
    }

    // Funcionalidad de botones útiles
    const helpfulButtons = document.querySelectorAll(".helpful-btn");

    helpfulButtons.forEach((button) => {
        button.addEventListener("click", function () {
            // Guardar el texto original para posible reset
            const originalText = this.textContent;
            const originalHTML = this.innerHTML;
            
            this.textContent = "¡Gracias por tu feedback!";
            this.style.background = "#28a745";
            this.style.color = "white";
            this.style.borderColor = "#28a745";
            this.disabled = true;
        });
    });
});