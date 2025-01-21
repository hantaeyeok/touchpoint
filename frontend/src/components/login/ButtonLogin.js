import React from "react";
import "@styles/ButtonLogin.css";

const ButtonLogin = ({ text, url, onSubmit, type = "button", variant = "default", disabled }) => {
  const handleClick = (e) => {
    if (type === "link" && url) {
      window.location.href = url;
    }
  };

  return (
    <button
      type={type} // 버튼 타입 설정
      className={`login-button ${variant === "outline" ? "outline" : ""}`} // 스타일 클래스
      onClick={handleClick} // 클릭 핸들러
      disabled={disabled} // 비활성화 상태
    >
      {text}
    </button>
  );
};

export default ButtonLogin;