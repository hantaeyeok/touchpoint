import React, { useState } from "react";
import "@styles/PasswordField.css";

const PasswordField = ({ name, value, onChange, placeholder, iconName, errorMessage }) => {
  const [isPasswordVisible, setIsPasswordVisible] = useState(false);

  const togglePasswordVisibility = () => {
    setIsPasswordVisible((prev) => !prev);
  };

  return (
    <div className="input-container">
      <div className="input-wrapper">
        {iconName && <span className="material-symbols-outlined input-icon">{iconName}</span>}
        <input
          type={isPasswordVisible ? "text" : "password"}
          name={name}
          value={value}
          onChange={onChange}
          placeholder={placeholder}
        />
        <span className="material-symbols-outlined toggle-icon" onClick={togglePasswordVisibility}>
          {isPasswordVisible ? "visibility_off" : "visibility"}
        </span>
      </div>
      {errorMessage && <p className="error-message">{errorMessage}</p>}
    </div>
  );
};

export default PasswordField;
