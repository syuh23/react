import { useState } from "react";
import Login from "./Login";
import Signup from "./Signup";
import BoardList from "./BoardList";
import BoardDetail from "./BoardDetail";
import BoardUpdate from "./BoardUpdate";

function App() {
  const [token, setToken] = useState(localStorage.getItem("accessToken"));
  const [isSignup, setSignup] = useState(false);
  const [currentBoardId, setCurrentBoardId] = useState(null);
  const [isEdit, setEdit] = useState(false);

  const loginSuccess = (jwt) => {
    localStorage.setItem("accessToken", jwt);
    setToken(jwt);
  };

  const handleSignupClick = () => setSignup(true);
  const handleLoginClick = () => setSignup(false);

  if (!token) {
    return (
      <div>
        {isSignup ? (
          <>
            <Signup onSignupSuccess={loginSuccess} />
            <button onClick={handleLoginClick}>로그인으로 돌아가기</button>
          </>
        ) : (
          <>
            <Login onLoginSuccess={loginSuccess} />
            <button onClick={handleSignupClick}>회원가입</button>
          </>
        )}
      </div>
    );
  }

  if (isEdit) {
    return (
      <BoardUpdate boardId={currentBoardId} boardUpdateSuccess={() => setEdit(false)} boardUpdateCancel={() => setEdit(false)}/>
    );
  }

  if (currentBoardId) {
    return (
      <BoardDetail boardId={currentBoardId} goBack={() => setCurrentBoardId(null)} boardEdit={() => setEdit(true)}/>
    );
  }

  return (
    <BoardList boardList={(id) => setCurrentBoardId(id)}/>
  );
}

export default App;
