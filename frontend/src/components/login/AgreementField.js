import React, { useState } from "react";
import "@styles/AgreementField.css";

const AgreementField = ({ type, isChecked, onToggle, errorMessage }) => {
  const [isDropdownVisible, setIsDropdownVisible] = useState(false);

  const toggleDropdown = () => setIsDropdownVisible((prev) => !prev);

  const agreementText =
    type === "mandatory"
      ? "[필수] 개인정보 동의 약관"
      : "[선택] 광고 정보 동의 약관";

  // 약관 내용을 type에 따라 다르게 설정
  const agreementContent =
    type === "mandatory"
      ? "본 약관에 동의하시면, 개인정보는 서비스 이용을 위해 수집, 저장 및 처리됩니다. 관련된 세부사항은 개인정보처리방침에서 확인하실 수 있습니다."
      : "광고 정보 수신에 동의하시면, 키오스크 관련 프로모션, 이벤트 및 마케팅 정보를 이메일로 받아보실 수 있습니다.";

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
          <p>{agreementContent}</p> {/* 조건부로 설정된 약관 내용 */}
        </div>
      )}
      {errorMessage && <p className="error-message">{errorMessage}</p>}
    </div>
  );
};

export default AgreementField;
