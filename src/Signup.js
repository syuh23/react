import { useState } from "react";
import api from "./api";

function Signup() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [name, setName] = useState("");

  const signup = async () => {
    try {
      await api.post("/user/signup", { name, email, password });
      alert("회원가입 완료 !!!");
    } 
    catch (error) {
      console.error(error);
      alert("회원가입 실패");
    }
  };

  return (
    <div>
      <h2>회원가입</h2>
      <input placeholder="이름" value={name} onChange={(e) => setName(e.target.value)}/>
      <input placeholder="이메일" value={email} onChange={(e) => setEmail(e.target.value)}/>
      <input placeholder="비밀번호" type="password" value={password} onChange={(e) => setPassword(e.target.value)}/>
      <button onClick={signup}>회원가입</button>
    </div>
  );
}

export default Signup;
