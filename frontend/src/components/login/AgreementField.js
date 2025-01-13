import React, { useState } from "react";
import "@styles/AgreementField.css";

const AgreementField = ({ type, isChecked, onToggle, errorMessage }) => {
  const [isDropdownVisible, setIsDropdownVisible] = useState(false);

  const toggleDropdown = () => setIsDropdownVisible((prev) => !prev);

  const agreementText = type === "mandatory" ? "[필수] 개인정보 동의 약관" : "[선택] 광고 정보 동의 약관";

  return (
    <div className="agreement-container">
      <div className="agreement-header" onClick={toggleDropdown}>
        <input type="checkbox" checked={isChecked} onChange={onToggle} />
        {agreementText}
        <span className="material-symbols-outlined arrow-icon">
          {isDropdownVisible ? "expand_less" : "expand_more"}
        </span>
      </div>
      {isDropdownVisible && (
        <div className="agreement-content">
          <p>약관 내용을 여기에 작성하세요.</p>
        </div>
      )}
      {errorMessage && <p className="error-message">{errorMessage}</p>}
    </div>
  );
};

export default AgreementField;
