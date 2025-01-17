document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("photoForm");
    const submitBtn = document.getElementById("submitBtn");

    form.addEventListener("submit", function (event) {
        // Desabilitar o botão para evitar múltiplos cliques
        submitBtn.disabled = true;
        submitBtn.textContent = "Enviando...";

        // Reabilitar o botão caso o envio falhe
        form.addEventListener("submiterror", function () {
            submitBtn.disabled = false;
            submitBtn.textContent = "Postar Foto";
        });
    });
});

