import { useState } from "react";
import api from "./api";

function Login({ onLoginSuccess }) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const login = async () => {
    try {
      const response = await api.post("/user/login", { email, password });
      const token = response.data.accessToken;
      onLoginSuccess(token);
      alert("로그인 성공 !!!");
    }
    catch (error) {
      console.error(error);
      alert("로그인 실패");
    }
  };

  return (
    <div>
      <h2>로그인</h2>
      <input placeholder="이메일" value={email} onChange={(e) => setEmail(e.target.value)}/>
      <input placeholder="비밀번호" type="password" value={password} onChange={(e) => setPassword(e.target.value)}/>
      <button onClick={login}>로그인</button>
    </div>
  );
}

export default Login;
