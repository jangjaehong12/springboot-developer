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
    const createButton = document.getElementById('create-btn');
    if(createButton){
        createButton.addEventListener('click',event=>{
            //서버에 블로그 등록 요청 보내기
            //등록할 글 제목, 내용을 요청 바디에 포함 시키기
            // 등록이 완료되면 '등록 완료 되었습니다'라는 팝업창 띄우고, 글 목록보기 요청을 서버에 보내기
            fetch('/api/articles',{
                method:'POST',
                headers:{
                    'Content-Type' : "application/json"
                },
                body:JSON.stringify({
                    title:document.getElementById('title').value,
                    content:document.getElementById('content').value
                })
            }).then(()=>{
                alert('등록이 완료 되었습니다.');
                location.replace('/articles')
            });
        })
    }
    const deleteButton = document.getElementById('delete-btn');

    if (deleteButton) {
        deleteButton.addEventListener('click', () => {

            const id = location.pathname.split('/').pop();

            fetch(`/api/articles/${id}`, {
                method: 'DELETE'
            })
                .then(() => {
                    alert('삭제가 완료되었습니다.');
                    location.replace('/articles');
                });
        });
    }