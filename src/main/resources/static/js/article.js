const modifyBtn = document.getElementById('modify-btn');

if (modifyBtn) {
    const params = new URLSearchParams(location.search);
    const id = params.get("id");

    modifyBtn.addEventListener('click', () => {
        fetch(`/api/articles/${id}`, {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                title: document.getElementById("title").value,
                content: document.getElementById("content").value
            })
        }).then(() => {
            alert('수정이 완료되었습니다.');
            location.replace(`/articles/${id}`);
        });
    });
}