import React, { useState } from "react";
import "@styles/EmailField.css";

const EmailField = ({ name, value, onChange, onBlur, placeholder, iconName, errorMessage }) => {
  const [customDomain, setCustomDomain] = useState("");
  const [isCustomInputVisible, setIsCustomInputVisible] = useState(false);

  const handleDomainChange = (domain) => {
    const [localPart] = value.split("@");
    const updatedValue = `${localPart}@${domain}`;
    onChange({ target: { name, value: updatedValue } }); // 이메일 전체를 업데이트
    setIsCustomInputVisible(domain === "custom");
  };

  const handleCustomDomainChange = (e) => {
    const [localPart] = value.split("@");
    const updatedDomain = e.target.value;
    const updatedValue = `${localPart}@${updatedDomain}`;
    setCustomDomain(updatedDomain);
    onChange({ target: { name, value: updatedValue } }); // 이메일 전체를 업데이트
  };

  return (
    <div className="input-container">
      <div className="email-input-wrapper">
        {iconName && <span className="material-symbols-outlined input-icon">{iconName}</span>}
        <input
          type="text"
          placeholder="이메일 이름"
          value={value.split("@")[0] || ""}
          onBlur={onBlur}
          onChange={(e) => {
            const domainPart = value.split("@")[1] || "";
            onChange({ target: { name, value: `${e.target.value}@${domainPart}` } });
          }}
        />
        <span className="email-at">@</span>
        <select
          className="email-domain-dropdown"
          value={isCustomInputVisible ? "custom" : value.split("@")[1] || ""}
          onChange={(e) => handleDomainChange(e.target.value)}
        >
          <option value="">도메인 선택</option>
          <option value="gmail.com">gmail.com</option>
          <option value="naver.com">naver.com</option>
          <option value="daum.net">daum.net</option>
          <option value="custom">직접 입력</option>
        </select>
        {isCustomInputVisible && (
          <input
            type="text"
            className="custom-domain-input"
            placeholder="직접 입력"
            value={customDomain}
            onChange={handleCustomDomainChange}
            onBlur={onBlur} // 도메인 입력 후에도 onBlur 호출
          />
        )}
      </div>
      {errorMessage && <p className="error-message">{errorMessage}</p>}
    </div>
  );
};

export default EmailField;
