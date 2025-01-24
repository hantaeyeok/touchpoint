import React from "react";
import "@styles/ButtonLogin.css";

const ButtonLogin = ({
  text, // 버튼에 표시할 텍스트
  url, // 버튼 클릭 시 이동할 URL (type이 "link"인 경우만 동작)
  onSubmit, // 버튼 클릭 시 실행되는 함수 (주로 폼 제출 관련 로직)
  onClick, // 버튼 클릭 시 실행되는 함수 (일반 클릭 로직)
  type = "button", // 버튼 타입. 기본값은 "button". HTML <button>의 type 속성.
  variant = "default", // 버튼 스타일 변형 옵션 (e.g., "outline"은 테두리 스타일)
  disabled, // 버튼 비활성화 상태. true일 경우 클릭 불가.   
}) => {
  const handleClick = (e) => {
    if (onClick) {
      onClick(e); // onClick이 전달된 경우 실행
    }

    if (onSubmit) {
      onSubmit(e); // onSubmit이 전달된 경우 실행
    }

    if (type === "link" && url) {
      window.location.href = url; // type이 "link"인 경우 URL로 이동
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