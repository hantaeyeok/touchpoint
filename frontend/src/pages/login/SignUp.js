import React, { useState } from "react";
import InputDiv from "@components/login/InputDiv";
import ButtonLogin from "@components/login/ButtonLogin";

const SignUpForm = () => {
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
    <div>
    <form onSubmit={handleSubmit}>
      <InputDiv
        label="아이디"
        type="text"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
        placeholder="아이디를 입력하세요"
        iconName="person"
      />

      <InputDiv
        label="비밀번호"
        type="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        placeholder="비밀번호를 입력하세요"
        iconName="lock"
      />

      <InputDiv
        label="이메일"
        type="email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        placeholder="이메일 주소를 입력하세요"
        iconName="mail"
      />

      <InputDiv
        label="[필수] 개인정보 동의 약관"
        type="agreement"
        value={isMandatoryChecked}
        onChange={(e) => setIsMandatoryChecked(e.target.checked)}
      />

      <InputDiv
        label="[선택] 광고 정보 동의 약관"
        type="agreement"
        value={isAdChecked}
        onChange={(e) => setIsAdChecked(e.target.checked)}
      />    
    </form>
    <ButtonLogin>회원가입</ButtonLogin>

    </div>
  );
};

export default SignUpForm;
