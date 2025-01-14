document.addEventListener("DOMContentLoaded", function () {
    const photoInput = document.getElementById("photo");
    const previewImage = document.getElementById("image-preview");

    photoInput.addEventListener("change", function () {
        const file = this.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                // Substitui o src da imagem pelo conteúdo do arquivo carregado
                previewImage.src = e.target.result;
            };
            reader.readAsDataURL(file);
        }
    });
});
