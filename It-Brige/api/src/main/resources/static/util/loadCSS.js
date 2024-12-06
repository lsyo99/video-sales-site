// css로드 함수

export function loadCSS(href) {
    const existingLink = document.querySelector(`link[href='${href}']`);
    if (!existingLink) {
        const link = document.createElement('link');
        link.rel = 'stylesheet';
        link.href = href;
        document.head.appendChild(link);
    }
}