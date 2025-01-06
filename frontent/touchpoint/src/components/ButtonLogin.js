import React from "react";
import { useNavigate } from "react-router-dom";
import "../styles/ButtonLogin.css";

const ButtonLogin = ({ children, onClick, type = "button", className = "", url }) => {
    const navigate = useNavigate();
    const handleClick = () => {
      if (url) {        // React Router를 사용한 URL 이동
        navigate(url);
      } else if (onClick) {// 사용자 정의 onClick 함수 실행
        onClick();
      }
    };
  
    return (
      <button
        className={`button ${className}`}
        type={type}
        onClick={handleClick}
      >
        {children}
      </button>
    );
  };
  
  export default ButtonLogin;