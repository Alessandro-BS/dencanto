const hamburger = document.querySelector(".toggle-btn");
const toggler = document.querySelector("#icon");
hamburger.addEventListener("click", function () {
    document.querySelector("#sidebar").classList.toggle("expand");
    toggler.classList.toggle("bxs-chevrons-right");
    toggler.classList.toggle("bxs-chevrons-left");
});

// Variables globales
let currentStep = 1;
let selectedProducts = [];
s

// Navegación entre pasos
function nextStep(step) {
    document.getElementById(`step${currentStep}-content`).style.display = 'none';
    document.getElementById(`step${currentStep}`).classList.remove('active');

    document.getElementById(`step${step}-content`).style.display = 'block';
    document.getElementById(`step${step}`).classList.add('active');

    currentStep = step;
}

function previousStep(step) {
    nextStep(step);
}

// Agregar producto a la venta
function addProductToSale(productId) {
    const products = {
        1: { id: 1, name: 'Colchón Queen Size Memory Foam', price: 1250.00, quantity: 1 },
        2: { id: 2, name: 'Base Cama Ajustable King Size', price: 890.00, quantity: 1 },
        4: { id: 4, name: 'Protector Impermeable Queen Size', price: 85.00, quantity: 1 }
    };

    const product = products[productId];

    // Verificar si el producto ya está en la lista
    const existingProduct = selectedProducts.find(p => p.id === productId);
    if (existingProduct) {
        existingProduct.quantity++;
    } else {
        selectedProducts.push({ ...product });
    }

    updateSelectedProductsList();
    updateTotals();

    // Mostrar confirmación
    showToast(`Producto agregado: ${product.name}`);
}

// Actualizar lista de productos seleccionados
function updateSelectedProductsList() {
    const container = document.getElementById('selected-products');

    if (selectedProducts.length === 0) {
        container.innerHTML = `
                    <div class="text-center text-white-50 py-3">
                        <i class="bx bx-cart display-4 mb-2"></i>
                        <p>No hay productos agregados</p>
                    </div>
                `;
        return;
    }

    let html = '';
    selectedProducts.forEach((product, index) => {
        html += `
                    <div class="d-flex justify-content-between align-items-center mb-2 pb-2 border-bottom border-white-50">
                        <div class="text-white">
                            <div class="fw-bold small">${product.name}</div>
                            <div class="small">S/ ${product.price.toFixed(2)} x ${product.quantity}</div>
                        </div>
                        <div class="d-flex align-items-center">
                            <span class="text-white fw-bold me-2">S/ ${(product.price * product.quantity).toFixed(2)}</span>
                            <button class="btn btn-sm btn-outline-light" onclick="removeProduct(${index})">
                                <i class="bx bx-trash"></i>
                            </button>
                        </div>
                    </div>
                `;
    });

    container.innerHTML = html;
}

// Procesar venta
function processSale() {
    if (selectedProducts.length === 0) {
        alert('Debe agregar al menos un producto a la venta');
        return;
    }

    const method = document.getElementById('paymentMethod').value;
    if (method === 'cash') {
        const amountReceived = parseFloat(document.getElementById('amountReceived').value) || 0;
        const total = parseFloat(document.getElementById('total').textContent.replace('S/ ', '')) || 0;

        if (amountReceived < total) {
            alert('El monto recibido es menor al total de la venta');
            return;
        }
    }

    // Actualizar total en el modal
    document.getElementById('modalTotal').textContent = document.getElementById('total').textContent;

    // Mostrar modal de éxito
    const successModal = new bootstrap.Modal(document.getElementById('successModal'));
    successModal.show();
}