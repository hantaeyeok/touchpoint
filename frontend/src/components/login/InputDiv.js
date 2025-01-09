import React, { useState } from "react";
import "@styles/InputDiv.css";

const InputDiv = ({
  label,
  type = "text",
  value,
  onChange,
  placeholder,
  iconName,
}) => {
  const [isPasswordVisible, setIsPasswordVisible] = useState(false);
  const [customDomain, setCustomDomain] = useState("");
  const [isMandatoryDropdownVisible, setIsMandatoryDropdownVisible] = useState(false);
  const [isAdDropdownVisible, setIsAdDropdownVisible] = useState(false);

  // 비밀번호 보기/숨기기 토글
  const togglePasswordVisibility = () => {
    setIsPasswordVisible(!isPasswordVisible);
  };

  // 이메일 도메인 선택
  const handleDomainClick = (domain) => {
    const [localPart] = value.split("@");
    onChange({ target: { value: `${localPart}@${domain}` } });
  };

  // 직접 입력 도메인 변경
  const handleCustomDomainChange = (e) => {
    const [localPart] = value.split("@");
    setCustomDomain(e.target.value);
    onChange({ target: { value: `${localPart}@${e.target.value}` } });
  };

  // 필수 약관 드롭다운 토글
  const toggleMandatoryDropdown = () => {
    setIsMandatoryDropdownVisible(!isMandatoryDropdownVisible);
  };

  // 광고 정보 동의 드롭다운 토글
  const toggleAdDropdown = () => {
    setIsAdDropdownVisible(!isAdDropdownVisible);
  };

  return (
    <div className="input-container">
      {label && <label className="input-label">{label}</label>}

      <div className="input-wrapper">
        {/* 아이콘이 있는 경우 */}
        {iconName && (
          <span className="material-symbols-outlined input-icon">
            {iconName}
          </span>
        )}

        {/* 이메일 필드 */}
        {type === "email" ? (
          <div className="email-field-wrapper">
            {/* 이메일 이름 입력 */}
            <input
              type="text"
              className="input-field email-name"
              placeholder="이메일 이름"
              value={value.split("@")[0] || ""}
              onChange={(e) => {
                const domainPart = value.split("@")[1] || "";
                onChange({ target: { value: `${e.target.value}@${domainPart}` } });
              }}
            />

            {/* 고정된 '@' */}
            <span className="email-at">@</span>

            {/* 이메일 도메인 선택 */}
            <select
              className="email-domain-dropdown"
              value={customDomain || value.split("@")[1] || ""}
              onChange={(e) => handleDomainClick(e.target.value)}
            >
              <option value="">도메인 선택</option>
              <option value="gmail.com">gmail.com</option>
              <option value="naver.com">naver.com</option>
              <option value="daum.net">daum.net</option>
              <option value="custom">직접 입력</option>
            </select>

            {/* 직접 입력 필드 */}
            {customDomain === "custom" && (
              <input
                type="text"
                className="custom-domain-input"
                placeholder="직접 입력"
                value={customDomain}
                onChange={handleCustomDomainChange}
              />
            )}
          </div>
        ) : (
          // 일반 필드
          <input
            type={type === "password" && isPasswordVisible ? "text" : type}
            value={value}
            onChange={onChange}
            placeholder={placeholder}
            className="input-field"
          />
        )}

        {/* 비밀번호 표시 아이콘 */}
        {type === "password" && (
          <span
            className="material-symbols-outlined input-icon"
            onClick={togglePasswordVisibility}
          >
            {isPasswordVisible ? "visibility_off" : "visibility"}
          </span>
        )}
      </div>

      {/* 개인정보 동의 약관 드롭다운 */}
      {type === "mandatory-agreement" && (
        <div className="agreement-container">
          <div
            className="agreement-header"
            onClick={toggleMandatoryDropdown}
          >
            [필수] 개인정보 동의 약관
            <span className="material-symbols-outlined arrow-icon">
              {isMandatoryDropdownVisible ? "expand_less" : "expand_more"}
            </span>
          </div>
          {isMandatoryDropdownVisible && (
            <div className="agreement-content">
              <p>
                여기에 개인정보 동의 약관 내용을 작성하세요. Lorem ipsum dolor sit
                amet, consectetur adipiscing elit.
              </p>
            </div>
          )}
        </div>
      )}

      {/* 광고 정보 동의 드롭다운 */}
      {type === "ad-agreement" && (
        <div className="agreement-container">
          <div className="agreement-header" onClick={toggleAdDropdown}>
            [선택] 광고 정보 동의 약관
            <span className="material-symbols-outlined arrow-icon">
              {isAdDropdownVisible ? "expand_less" : "expand_more"}
            </span>
          </div>
          {isAdDropdownVisible && (
            <div className="agreement-content">
              <p>
                여기에 광고 정보 동의 약관 내용을 작성하세요. Vivamus nec urna
                commodo, efficitur nisi in, suscipit nisl.
              </p>
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default InputDiv;
