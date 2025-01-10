import React, { useState } from "react";
import InputDiv from "@components/login/InputDiv";
import ButtonLogin from "@components/login/ButtonLogin";
import "@styles/SignUp.css";
import { validateField } from '@components/login/ValidateField';

const SignUpForm = () => {
  const [formData, setFormData] = useState({
    userId: "",
    password: "",
    confirmPassword: "",
    username: "",
    phone: "",
    email: "",
  });
  console.log(validateField("username", "test123"));

  const [errors, setErrors] = useState({});
  const [isMandatoryChecked, setIsMandatoryChecked] = useState(false);
  const [isAdChecked, setIsAdChecked] = useState(false);

  // Input 변경 핸들러
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
    setErrors({ ...errors, [name]: "" }); // 오류 초기화
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    let valid = true;
    const newErrors = {};

    // 모든 필드 유효성 검사
    Object.entries(formData).forEach(([name, value]) => {
      const { isValid, message } = validateField(name, value, formData);
      if (!isValid) {
        valid = false;
        newErrors[name] = message;
      }
    });

    // 필수 약관 체크 확인
    if (!isMandatoryChecked) {
      valid = false;
      newErrors.mandatoryAgreement = "필수 약관에 동의해야 합니다.";
    }

    setErrors(newErrors);

    if (valid) {
      alert("회원가입 완료!");
    }
  };

  return (
    <div className="signup-container">
      <h2 className="h2Title">logo 회원가입</h2>
      <form className="form-container" onSubmit={handleSubmit}>
        {/* 아이디 */}
        <InputDiv
          className="signup-input"
          type="text"
          name="userId"
          value={formData.userId}
          onChange={handleChange}
          placeholder="아이디를 입력하세요"
          iconName="person"
          errorMessage={errors.userId}
        />

        {/* 비밀번호 */}
        <InputDiv
          className="signup-input"
          type="password"
          name="password"
          value={formData.password}
          onChange={handleChange}
          placeholder="비밀번호를 입력하세요"
          iconName="lock"
          errorMessage={errors.password}
        />

        {/* 비밀번호 확인 */}
        <InputDiv
          className="signup-input"
          type="password"
          name="confirmPassword"
          value={formData.confirmPassword}
          onChange={handleChange}
          placeholder="비밀번호를 다시 입력하세요"
          iconName="lock"
          errorMessage={errors.confirmPassword}
        />

        {/* 이름 */}
        <InputDiv
          className="signup-input"
          type="text"
          name="username"
          value={formData.username}
          onChange={handleChange}
          placeholder="이름을 입력해주세요"
          iconName="person"
          errorMessage={errors.username}
        />

        {/* 전화번호 */}
        <InputDiv
          className="signup-input"
          type="text"
          name="phone"
          value={formData.phone}
          onChange={handleChange}
          placeholder="전화번호를 입력하세요"
          iconName="phone"
          errorMessage={errors.phone}
        />

        {/* 이메일 */}
        <InputDiv
          className="signup-input"
          type="email"
          
          value={formData.email}
          onChange={handleChange}
          placeholder="이메일 주소를 입력하세요"
          iconName="mail"
          errorMessage={errors.email}
        />

        {/* 필수 약관 동의 */}
        <InputDiv
          className="signup-agreement"
          type="mandatory-agreement"
          value={isMandatoryChecked}
          onChange={(e) => setIsMandatoryChecked(e.target.checked)}
          errorMessage={errors.mandatoryAgreement}
        />

        {/* 선택 약관 동의 */}
        <InputDiv
          className="signup-agreement"
          type="ad-agreement"
          value={isAdChecked}
          onChange={(e) => setIsAdChecked(e.target.checked)}
        />

        {/* 회원가입 버튼 */}
        <ButtonLogin
          className="signup-button"
          text="회원가입"
          type="submit"
        />
        
      </form>
    </div>
  );
};

export default SignUpForm;
