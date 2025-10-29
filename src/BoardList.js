import { useEffect, useState } from "react";
import api from "./api";
import BoardCreate from "./BoardCreate";

function BoardList({ boardList }) {
  const [boards, setBoards] = useState([]);
  const [isCreate, setBoardCreate] = useState(false);

  const fetchBoards = async () => {
    try {
      const response = await api.post("/board/");
      setBoards(response.data);
    } 
    catch (error) {
      console.error(error);
      alert("게시판 불러오기 실패");
    }
  };

  useEffect(() => {
    fetchBoards();
  }, []);

  if (isCreate) {
    return (
      <div>
        <BoardCreate onCreateSuccess={() => {
            setBoardCreate(false);
            fetchBoards();
          }}
        />
      </div>
    );
  }

  return (
    <div>
      <h2>게시판 리스트</h2>
      <button onClick={() => setBoardCreate(true)}>게시글 추가</button>
      <ul>
        {boards.map((board) => (
          <li key={board.boardId} onClick={() => boardList(board.boardId)}>
            {board.title} --- {board.writer}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default BoardList;
