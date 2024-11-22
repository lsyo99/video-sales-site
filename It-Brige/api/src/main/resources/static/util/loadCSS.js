// css로드 함수

export function loadCSS(href){

    const existingLink = document.querySelector("link[href='css/home.css']");
    if(!existingLink) {
        const link = document.createElement('link');
        link.rel = 'stylesheet';
        link.href= href;
        document.head.appendChild(link);
    }
}