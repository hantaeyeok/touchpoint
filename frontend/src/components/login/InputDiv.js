import React, { useState } from "react";
import "@styles/InputDiv.css";
import { validateField } from '@components/login/ValidateField'

const InputDiv = ({
  label,
  type = "text", // input 필드 타입 (text, email, password 등)
  name,
  value,
  onChange,
  placeholder,
  iconName,
}) => {
  const [isPasswordVisible, setIsPasswordVisible] = useState(false); //비밀번호 보기 숨기기
  const [customDomain, setCustomDomain] = useState(""); // 이메일 직접 입력 도메인 상태
  const [isMandatoryDropdownVisible, setIsMandatoryDropdownVisible] = useState(false); //필수 약관  드롭다운 상태
  const [isAdDropdownVisible, setIsAdDropdownVisible] = useState(false); // 광고 동의 드롭다운 상태
  const [errorMessage, setErrorMessage] = useState(""); // 오류 메시지 상태


  const [isMandatoryChecked, setIsMandatoryChecked] = useState(false);
  const [isAdChecked, setIsAdChecked] = useState(false);


  const togglePasswordVisibility = () => {
    setIsPasswordVisible(!isPasswordVisible);
  };

  const handleDomainClick = (domain) => {
    const [localPart] = value.split("@");
    onChange({ target: { value: `${localPart}@${domain}` } });
  };

  const handleCustomDomainChange = (e) => {
    const [localPart] = value.split("@");
    setCustomDomain(e.target.value);
    onChange({ target: { value: `${localPart}@${e.target.value}` } });
  };

  const toggleMandatoryDropdown = () => {
    setIsMandatoryDropdownVisible(!isMandatoryDropdownVisible);
  };

  const toggleAdDropdown = () => {
    setIsAdDropdownVisible(!isAdDropdownVisible);
  };

 // 유효성 검사 수행 (onBlur에서 호출)
 const handleValidation = () => {
  const { isValid, message } = validateField(name, value);
  if (!isValid) setErrorMessage(message); // 오류 메시지 저장
  else setErrorMessage(""); // 오류 메시지 초기화
};

// 값 변경 시 오류 메시지 초기화
const handleChange = (e) => {
  setErrorMessage(""); // 오류 메시지 초기화
  onChange(e);
};

  const handleMandatoryCheckboxChange = (e) => {
    setIsMandatoryChecked(e.target.checked);
    console.log("[필수] 개인정보 동의 상태:", e.target.checked);
  };

  const handleAdCheckboxChange = (e) => {
    setIsAdChecked(e.target.checked);
    console.log("[선택] 광고 정보 동의 상태:", e.target.checked);
  };







  const renderInputField = () => {
    {iconName && (
      <span className="material-symbols-outlined input-icon">
        {iconName}
      </span>
    )};


    switch (type) {
      case "email":
        return (
          <div className="email-input-wrapper">
             {iconName && (
              <span className="material-symbols-outlined input-icon">
                {iconName}
              </span>
            )}
 
            <input
              type="text"
              placeholder="이메일 이름"
              value={value.split("@")[0] || ""}
              onChange={(e) => {
                const domainPart = value.split("@")[1] || "";
                onChange({ target: { value: `${e.target.value}@${domainPart}` } });
              }}
            />

            <span className="email-at">@</span>

            <select 
              className="email-domain-dropdown"
              value={customDomain || value.split("@")[1] || ""}
              onChange={(e) => handleDomainClick(e.target.value)}
            >
              <option value="">도메인 선택
                
              </option>
              <option value="gmail.com">gmail.com</option>
              <option value="naver.com">naver.com</option>
              <option value="daum.net">daum.net</option> 
              <option value="custom">직접 입력</option>
            </select>     
 
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
        );


      case "password":
        return (
          <div className="input-wrapper">
             {iconName && (
              <span className="material-symbols-outlined input-icon">
                {iconName}
              </span>
            )}
            <input
              type={isPasswordVisible ? "text" : "password"}
              name={name}
              value={value}
              onChange={onChange}
              placeholder={placeholder}
            />
            <span
              className="material-symbols-outlined toggle-icon"
              onClick={togglePasswordVisibility}
            >
              {isPasswordVisible ? "visibility_off" : "visibility"}
            </span>
          </div>
        );



      case "mandatory-agreement":
        return (
          <div className="agreement-container">
           
            
            <div className="agreement-header" onClick={toggleMandatoryDropdown}>
            
            <input
                    type="checkbox"
                    checked={isMandatoryChecked}
                    onChange={handleMandatoryCheckboxChange}
                  />
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
        );


      case "ad-agreement":
        return (
          <div className="agreement-container">
            
            <div className="agreement-header" onClick={toggleAdDropdown}>
            <input
                    type="checkbox"
                    checked={isAdChecked}
                    onChange={handleAdCheckboxChange}
                  />
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
        );


      default:
        return (
          <div className="input-wrapper">
            {iconName && (
              <span className="material-symbols-outlined input-icon">
                {iconName}
              </span>
            )}
            <input
              type={type} 
              name={name}
              value={value}
              onChange={onChange}
              placeholder={placeholder} 
            />
          </div>
        );
    }
  };

  return (
    <div className="input-container">
      <label className="input-label">{label}</label>
      {renderInputField()}
      {errorMessage && <p className="error-message">{errorMessage}</p>}
    </div>
  );
};

export default InputDiv;
