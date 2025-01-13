import React from "react";
import "@styles/InputField.css";

const InputField = ({ label, type = "text", name, value, onChange,onBlur, placeholder, iconName, errorMessage }) => {
  return (
    <div className="input-container">
      {label && <label className="input-label">{label}</label>}
      <div className="input-wrapper">
        {iconName && <span className="material-symbols-outlined input-icon">{iconName}</span>}
        <input
          type={type}
          name={name}
          value={value}
          onChange={onChange}
          onBlur={onBlur}
          placeholder={placeholder}
        />
      </div>
      {errorMessage && <p className="error-message">{errorMessage}</p>}
    </div>
  );
};
 
export default InputField;
