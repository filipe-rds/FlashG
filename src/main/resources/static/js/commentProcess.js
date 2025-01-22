$("[id^='form-']").on("submit", function(event) {
    event.preventDefault();  // Evita o envio do formulário

    // Cria um objeto FormData a partir do formulário
    const formId = $(this).attr("id");  // Obtém o ID do formulário
    const idPart = formId.split('-')[1];
    console.log(idPart);

    //const formData = new FormData(this);  // 'this' refere-se ao formulário
    const formData = new FormData(this);  // 'this' refere-se ao formulário

    // Preenche o campo de data com a data e hora atual

    // Cria uma data atual
    const currentDate = new Date();

    // Obtém o deslocamento de fuso horário do horário de Brasília (em minutos)
    const timeZoneOffset = -180; // Brasília está a -3 horas UTC

    // Ajusta a data para o fuso horário de Brasília
    const adjustedDate = new Date(currentDate.getTime() + (timeZoneOffset * 60 * 1000));

    // Converte a data ajustada para uma string ISO
    const createdAt = adjustedDate.toISOString();

    console.log(createdAt);

    formData.set("createdAt", createdAt);  // Adiciona ou atualiza o campo createdAt

    // Obtém os dados do comentário
    const commentText = formData.get("commentText");
    const commentDate = new Date().toLocaleString();  // Data do comentário
    const photographerId = formData.get("photographer");  // ID do fotógrafo
    const photoId = formData.get("photo");  // ID da foto

    // Prepara os dados para o envio via AJAX
    const data = {
        commentText: commentText,
        createdAt: createdAt,
        photographer: photographerId,  // Envia o ID do fotógrafo
        photo: photoId,  // Envia o ID da foto
    };

    // Envia os dados via AJAX (usando jQuery)
    $.ajax({
        url: '/FlashG/addComment',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),


        success: function(response) {
            console.log('Comentário enviado com sucesso:', response);

            atualizarHTMLPosComentario(response,idPart);


        },


        error: function(xhr, status, error) {
            console.log('Resposta do servidor:', xhr.responseText);
            console.log(error);
            console.error('Erro ao enviar o comentário:', error);
        }
    });
});

function atualizarHTMLPosComentario(data, id) {

    const commentCountElement = $('#sizeComment-' + id);

    // Atualiza a quantidade total de comentários
    console.log(commentCountElement);
    commentCountElement.text(data.numberOfComments);

    // Zera o text área de comentário daquela foto específica

    const textAreaElement = $('textArea-' + id);
    textAreaElement.val('');

    const novoComentario = `
        <div class="flex items-start gap-3 mb-4 max-w-[294px]">
            <img src="${data.imageUrl}" alt="perfil" class="h-8 w-8 rounded-full object-cover">
            <div>
                <div class="flex flex-row justify-between gap-6">
                    <p class="text-sm font-medium text-slate-800">${data.photographerName}</p>
                    <p class="text-sm font-medium text-slate-800">${data.createdAt}</p>
                </div>
                <p class="text-sm text-slate-600 max-w-[248px] break-words">${data.commentText}</p>
            </div>
        </div>
    `;

    const listCommentElement = $('#listComment-' + id);
    listCommentElement.append(novoComentario);

    $("[id^='form-']").find("textarea").val('');


}