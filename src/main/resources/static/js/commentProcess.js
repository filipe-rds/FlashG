$("[id^='form-']").on("submit", function(event) {
    event.preventDefault();  // Evita o envio do formulário

    // Cria um objeto FormData a partir do formulário
    const formId = $(this).attr("id");  // Obtém o ID do formulário
    const idPart = formId.split('-')[1]; 
    console.log(idPart);

    //const formData = new FormData(this);  // 'this' refere-se ao formulário
    const formData = new FormData(this);  // 'this' refere-se ao formulário

    // Preenche o campo de data com a data e hora atual
    const createdAt = new Date().toISOString();
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
    commentCountElement.text(data.quantidadeComentario); 

    // Zera o text área de comentário daquela foto específica

    const textAreaElement = $('textArea-' + id);
    textAreaElement.val('');

    const novoComentario = `
        <div class="bg-white p-4 rounded-lg shadow-md"">
            <h3 class="text-lg font-bold">${data.photographerName}</h3>
            <p class="text-gray-700 text-sm mb-2">${data.createdAt}</p>
            <p class="text-gray-700">${data.commentText}</p>
        </div>
    `;

    const listCommentElement = $('#listComment-' + id);
    listCommentElement.append(novoComentario);

    $("[id^='form-']").find("textarea").val('');


}