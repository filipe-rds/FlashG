document.addEventListener("DOMContentLoaded", function () {
    const jsLibraries = [
        'https://unpkg.com/@material-tailwind/html@2.3.2/scripts/dialog.js',
        // Adicione mais URLs conforme necessário
    ];

    // Função para carregar um script de forma dinâmica
    function addScriptInOrder(url) {
        return new Promise((resolve, reject) => {
            const script = document.createElement('script');
            script.src = url;
            script.onload = () => resolve(`Script carregado: ${url}`);
            script.onerror = () => reject(`Erro ao carregar o script: ${url}`);
            document.body.appendChild(script);
        });
    }

    // Carregar scripts dinamicamente na ordem correta
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
});



