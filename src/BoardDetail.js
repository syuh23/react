import { useEffect, useState } from "react";
import api from "./api";

function BoardDetail({ boardId, goBack, boardEdit }) {
  const [board, setBoard] = useState(null);

  useEffect(() => {
    const fetchBoard = async () => {
      try {
        const response = await api.post(`/board/${boardId}`);
        setBoard(response.data);
      } 
      catch (error) {
        console.error(error);
        alert("게시글 불러오기 실패");
      }
    };
    fetchBoard();
  }, [boardId]);

  if (!board) {
    return <div>로딩중........</div>;
  }

  const boardDelete = async () => {
    try {
      await api.delete(`/board/${boardId}`);
      alert("게시글 삭제 완료 !");
      goBack();
    } 
    catch (error) {
      console.error(error);
      alert("게시글 삭제 실패");
    }
  };

  return (
    <div>
      <h2>{board.title}</h2>
      <p>내용 : {board.content}</p>
      <p>작성자 : {board.writer}</p>
      <p>작성일 : {board.createDate}</p>
      <button onClick={boardEdit}>수정</button>
      <button onClick={boardDelete}>삭제</button>
      <button onClick={goBack}>목록</button>
    </div>
  );
}

export default BoardDetail;
