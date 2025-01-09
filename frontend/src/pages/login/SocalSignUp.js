import React, { useState } from "react";
import InputDiv from "@components/login/InputDiv";
import ButtonLogin from "@components/login/ButtonLogin";
import "@styles/SocalSignUp.css"; 

const SocalSignUp = () => {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [email, setEmail] = useState("");
    const [isMandatoryChecked, setIsMandatoryChecked] = useState(false);
    const [isAdChecked, setIsAdChecked] = useState(false);
    const handleSubmit = (e) => {
        e.preventDefault();
        if (!isMandatoryChecked) {
            alert("필수 약관에 동의해야 회원가입을 진행할 수 있습니다.");
            return;
        }
        alert("회원가입 완료!");
    };

    return (
      <div className="signup-container">
    <h2 className="h2Title"> logo 회원가입</h2>
    <form className="form-container" onSubmit={handleSubmit}>

      <InputDiv
        className="signup-input"
        type="text"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
        placeholder="이름을 입력해주세요"
        iconName="person"
      />
      
      <InputDiv
        className="signup-input"
        type="text"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
        placeholder="전화번호를 입력하세요"
        iconName="phone"
      />

      <InputDiv
        className="signup-input"
        type="email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        placeholder="이메일 주소를 입력하세요"
        iconName="mail"
      />

      <InputDiv
        className="signup-agreement"
        type="mandatory-agreement"
        value={isMandatoryChecked}
        onChange={(e) => setIsMandatoryChecked(e.target.checked)}
      />

      <InputDiv
        className="signup-agreement"
        type="ad-agreement"
        value={isAdChecked}
        onChange={(e) => setIsAdChecked(e.target.checked)}
      />
    </form>

    <ButtonLogin
    className="signup-button"
    text="회원가입"
    type="submit"
  />
 
  </div>
);
}

export default SocalSignUp;