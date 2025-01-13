import React from "react";
import '@styles/ButtonLogin.css';

const ButtonLogin = ({ text, url, onSubmit, type = "button", variant = "default", disabled }) => {
  const handleClick = (e) => {
    if (type === "link" && url) {
      window.location.href = url;
    } else if (type === "submit" && onSubmit) {
      e.preventDefault(); // 버튼 클릭 시 새로고침 방지
      onSubmit(e); // 이벤트 객체 전달
    }
  };

  return (
    <button
      type={type === "submit" ? "submit" : "button"} // type에 따라 HTML 버튼 타입 설정
      className={`login-button ${variant === "outline" ? "outline" : ""}`}
      onClick={handleClick}
      disabled={disabled} // 비활성화 상태
    >
      {text}
    </button>
  );
};

export default ButtonLogin;
