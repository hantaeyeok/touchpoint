import React, { useState } from "react";
import axios from "axios";
import InputField from "@components/login/InputField";
import PasswordField from "@components/login/PasswordField";
import EmailField from "@components/login/EmailField";
import AgreementField from "@components/login/AgreementField";
import { validateField } from "@components/login/ValidateField";
import ButtonLogin from "@components/login/ButtonLogin";
import "@styles/SignUp.css";

const SignUpForm = () => {
  const [formData, setFormData] = useState({
    userId: "",
    password: "",
    confirmPassword: "",
    username: "",
    phone: "",
    email: "",
  });

  const [errors, setErrors] = useState({});
  const [isMandatoryChecked, setIsMandatoryChecked] = useState(false);
  const [isAdChecked, setIsAdChecked] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;

    // 입력 값 업데이트
    setFormData((prev) => ({ ...prev, [name]: value }));

    // 실시간 유효성 검사 실행
    const { isValid, message } = validateField(name, value, { ...formData, [name]: value });

    setErrors((prev) => ({
      ...prev,
      [name]: isValid ? "" : message, // 유효성 검사 결과에 따라 오류 메시지 업데이트
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    let valid = true;
    const newErrors = {};

    Object.entries(formData).forEach(([name, value]) => {
      const { isValid, message } = validateField(name, value, formData);
      if (!isValid) {
        valid = false;
        newErrors[name] = message;
      }
    });

    if (!isMandatoryChecked) {
      valid = false;
      newErrors.mandatoryAgreement = "필수 약관에 동의해야 합니다.";
    }

    setErrors(newErrors);

    if (valid) {
      alert("회원가입이 완료되었습니다!");
    }

    //axois
    try {
      const response = await axios.post("api/user/signup",{
        ...formData,
        adAgreed: isAdChecked,
      });

      if (response.data.data === true){
        alert("회원가입이 완료되었습니다!");
        setFormData({
          userId: "", 
          password: "",
          confirmPassword: "",
          username: "",
          phone: "",
          email: "",
        });
        setIsMandatoryChecked(false);
        setIsAdChecked(false);
        setErrors({});
      };

      if (response.data.data === false){
        alert("회원가입을 다시 시도해주세요. ㅜㅜ");
        setFormData({
          userId: "",
          password: "",
          confirmPassword: "",
          username: "",
          phone: "",
          email: "",
        });
        setIsMandatoryChecked(false);
        setIsAdChecked(false);
        setErrors({});
      };

    } catch (error) {
      console.error("회원가입 중 오류 발생:", error);      
      alert(
        error.response?.data?.message || "회원가입 중 오류가 발생했습니다. 다시 시도해주세요."
      );
    } finally{
      setIsSubmitting(false); // 제출 완료
    }




  };

  return (
    <div className="signup-container">
      <h2 className="h2Title">회원가입</h2>
      <form className="form-container" onSubmit={handleSubmit}>
        <InputField
          type="text"
          name="userId"
          value={formData.userId}
          onChange={handleChange}
          placeholder="아이디를 입력하세요"
          iconName="person"
          errorMessage={errors.userId}
        />
        <PasswordField
          name="password"
          value={formData.password}
          onChange={handleChange}
          placeholder="비밀번호를 입력하세요"
          iconName="lock"
          errorMessage={errors.password}
        />
        <PasswordField 
          name="confirmPassword"
          value={formData.confirmPassword}
          onChange={handleChange}
          placeholder="비밀번호를 확인하세요"
          iconName="lock"
          errorMessage={errors.confirmPassword}
        />
        <InputField
          type="text"
          name="username"
          value={formData.username}
          onChange={handleChange}
          placeholder="이름을 입력하세요"
          iconName="person"
          errorMessage={errors.username}
        />
        <InputField
          type="text"
          name="phone"
          value={formData.phone}
          onChange={handleChange}
          placeholder="전화번호를 입력하세요"
          iconName="phone"
          errorMessage={errors.phone}
        />
        <EmailField
          name="email"
          value={formData.email}
          onChange={handleChange}
          placeholder="이메일을 입력하세요"
          iconName="mail"
          errorMessage={errors.email}
        />
        <AgreementField
          type="mandatory"
          isChecked={isMandatoryChecked}
          onToggle={() => setIsMandatoryChecked(!isMandatoryChecked)}
          errorMessage={errors.mandatoryAgreement}
        />
        <AgreementField
          type="ad"
          isChecked={isAdChecked}
          onToggle={() => setIsAdChecked(!isAdChecked)}
        />

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