import React, { useState } from "react";
import "../styles/InputDiv.css";

const InputDiv = ({ label, type = "text", value, onChange, placeholder, iconName }) => {
  const [isPasswordVisible, setIsPasswordVisible] = useState(false); // 비밀번호 보기 상태
  const [customDomain, setCustomDomain] = useState(""); // 직접 입력 도메인 상태

  // 비밀번호 보기/숨기기 토글
  const togglePasswordVisibility = () => {
    setIsPasswordVisible(!isPasswordVisible);
  };

  // 이메일 도메인 선택
  const handleDomainClick = (domain) => {
    const [localPart] = value.split("@"); // 이메일의 @ 앞부분
    onChange({ target: { value: `${localPart}@${domain}` } }); // 새로운 이메일 값 설정
  };

  // 직접 입력 도메인 변경
  const handleCustomDomainChange = (e) => {
    const [localPart] = value.split("@");
    setCustomDomain(e.target.value);
    onChange({ target: { value: `${localPart}@${e.target.value}` } });
  };

  return (
    <div className="input-container">
      {label && <label className="input-label">{label}</label>}
      <div className="input-wrapper">
        {/* 아이콘이 있는 경우 */}
        {iconName && <span className="material-symbols-outlined input-icon">{iconName}</span>}
        
        {/* 공통 input 필드 */}
        <input
          type={type === "password" && isPasswordVisible ? "text" : type}
          value={value}
          onChange={onChange}
          placeholder={placeholder}
          className="input-field"
        />

        {/* 비밀번호 표시 아이콘 */}
        {type === "password" && (
          <span className="material-symbols-outlined input-icon" onClick={togglePasswordVisibility}>
            {isPasswordVisible ? "visibility_off" : "visibility"}
          </span>
        )}
      </div>

      {/* 이메일 도메인 드롭다운 */}
      {type === "email" && (
        <div className="email-dropdown-options">
          {["gmail.com", "naver.com", "daum.net"].map((domain) => (
            <button
              key={domain}
              className="email-dropdown-option"
              onClick={() => handleDomainClick(domain)}
            >
              @{domain}
            </button>
          ))}
          {/* 직접 입력 필드 */}
          <button className="email-dropdown-option" onClick={() => setCustomDomain("")}>
            직접입력
          </button>
          {customDomain === "" && (
            <input
              type="text"
              className="custom-domain-input"
              placeholder="직접 입력"
              value={customDomain}
              onChange={handleCustomDomainChange}
            />
          )}
        </div>
      )}
    </div>
  );
};

export default InputDiv;
