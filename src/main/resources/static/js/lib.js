// Adicionamos o evento window.onload, que é disparado quando todos os recursos (HTML, CSS, imagens, scripts) estiverem carregados
window.onload = function () {
    // Lista de URLs dos scripts JavaScript
    const jsLibraries = [
        'https://unpkg.com/@material-tailwind/html@latest/scripts/dialog.js',
        'https://unpkg.com/@material-tailwind/html@latest/scripts/dialog.js',
        // Adicione mais URLs conforme necessário
    ];

    // Função para adicionar os scripts JavaScript ao documento
    function addScriptInOrder(url) {
        return new Promise((resolve, reject) => {
            const script = document.createElement('script');
            script.src = url;
            script.defer = true;
            script.onload = () => resolve(`Script carregado: ${url}`);
            script.onerror = () => reject(`Erro ao carregar o script: ${url}`);
            document.body.appendChild(script);
        });
    }

    // Reduz a lista de scripts a uma cadeia de Promises
    jsLibraries
        .reduce(
            (prevPromise, currentUrl) =>
                prevPromise.then(() => addScriptInOrder(currentUrl)),
            Promise.resolve()
        )
        .then(() => {
            console.log('Todos os scripts foram carregados com sucesso!');
        })
        .catch((error) => {
            console.error('Erro ao carregar os scripts:', error);
        });
};

console.log("script carregado");


