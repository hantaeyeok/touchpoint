import React from "react";
import "@styles/ButtonLogin.css";

const ButtonLogin = ({ text, url, onSubmit, type = "button", variant = "default", disabled }) => {
  const handleClick = (e) => {
    // 기본 동작 방지 (버튼이 submit 타입일 경우)
    e.preventDefault();

    if (type === "link" && url) {
      // URL 이동
      window.location.href = url;
    } else if (onSubmit) {
      // onSubmit 함수 실행
      e.preventDefault();

      onSubmit(e);
    }
  };

  return (
    <button
      type={type === "submit" ? "submit" : "button"} // 버튼 타입 설정
      className={`login-button ${variant === "outline" ? "outline" : ""}`} // 스타일 클래스
      onClick={handleClick} // 클릭 핸들러
      disabled={disabled} // 비활성화 상태
    >
      {text}
    </button>
  );
};

export default ButtonLogin;