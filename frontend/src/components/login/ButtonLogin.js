import React from "react";
import '@styles/ButtonLogin.css'

const ButtonLogin = ({ text, url, onSubmit, type = "button", variant = "default" }) => {
  const handleClick = () => {
    if (type === "link" && url) {
      window.location.href = url;
    } else if (type === "submit" && onSubmit) {
      onSubmit();
    }
  };

  return (
    <button
      type="button"
      className={`login-button ${variant === "outline" ? "outline" : ""}`}
      onClick={handleClick}
    >
      {text}
    </button>
  );
};

export default ButtonLogin;