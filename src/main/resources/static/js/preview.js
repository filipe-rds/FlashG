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

// Função para atualizar o preview da imagem
function previewImage(event) {
    const reader = new FileReader();
    reader.onload = function () {
        const output = document.getElementById('avatar-preview');
        output.src = reader.result;
    };
    reader.readAsDataURL(event.target.files[0]);
}

// // Função para confirmar o upload (simples placeholder para implementação futura)
// function confirmUpload() {
//     alert('Alteração confirmada! A foto será atualizada.');
//     // Lógica para envio do formulário ou requisição aqui
// }