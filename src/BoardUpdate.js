import { useState, useEffect } from "react";
import api from "./api";

function BoardUpdate({ boardId, boardUpdateSuccess, boardUpdateCancel }) {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");

  useEffect(() => {
    const fetchBoard = async () => {
      try {
        const response = await api.post(`/board/${boardId}`);
        setTitle(response.data.title);
        setContent(response.data.content);
      } 
      catch (error) {
        console.error(error);
        alert("게시글 불러오기 실패");
      }
    };
    fetchBoard();
  }, [boardId]);

  const boardUpdate = async () => {
    try {
      await api.put(`/board/${boardId}`, { title, content });
      alert("게시글 수정 완료 !");
      boardUpdateSuccess();
    } 
    catch (error) {
      console.error(error);
      alert("게시글 수정 실패");
    }
  };

  return (
    <div>
      <h2>게시글 수정</h2>
      <input type="text" value={title} onChange={(e) => setTitle(e.target.value)}/>
      <textarea value={content} onChange={(e) => setContent(e.target.value)}/>
      <button onClick={boardUpdate}>저장</button>
      <button onClick={boardUpdateCancel}>취소</button>
    </div>
  );
}

export default BoardUpdate;
