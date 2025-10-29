import { useState } from "react";
import api from "./api";

function BoardCreate({ onCreateSuccess }) {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");

  const boardCreate = async () => {
    try {
      await api.post("/board/create", { title, content });
      alert("게시글 생성 성공 !!!");
      onCreateSuccess();
    } 
    catch (error) {
      console.error(error);
      alert("게시글 생성 실패");
    }
  };

  return (
    <div>
      <input type="text" placeholder="제목" value={title} onChange={(e) => setTitle(e.target.value)}/>
      <textarea placeholder="내용" value={content} onChange={(e) => setContent(e.target.value)}/>
      <button onClick={boardCreate}>생성</button>
    </div>
  );
}

export default BoardCreate;
