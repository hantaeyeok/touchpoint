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
            <input
                    type="checkbox"
                    checked={isMandatoryChecked}
                    onChange={handleMandatoryCheckboxChange}
                  />
            
            <div className="agreement-header" onClick={toggleMandatoryDropdown}>
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
            <input
                    type="checkbox"
                    checked={isAdChecked}
                    onChange={handleAdCheckboxChange}
                  />
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
      {renderInputField()}
    </div>
  );
};

export default InputDiv;
