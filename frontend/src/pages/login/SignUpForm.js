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
  const [userIdAvailable, setUserIdAvailable] = useState(null);
  const [emailAvailable, setEmailAvailable] = useState(null);
  const [isSubmitting, setIsSubmitting] = useState(false);
   
  // 공통 handleChange
  const handleChange = (e) => {
    const { name, value } = e.target;

    setFormData((prev) => ({ ...prev, [name]: value }));
    validateAndSetError(name, value);
  };

  // 유효성 검사
  const validateAndSetError = (name, value) => {
    const { isValid, message } = validateField(name, value, { ...formData, [name]: value });
    setErrors((prev) => ({
      ...prev,
      [name]: isValid ? "" : message,
    }));
  };

  // 중복 체크
  const handleBlur = async (field, value, checkApi, setAvailable) => {
    console.log(formData.email);
    console.log("handleBlur 호출된 URL:", checkApi);
    if (!value) {
      setErrors((prev) => ({ ...prev, [field]: `${field}을(를) 입력해주세요.` }));
      return;
    }
    try {
      const response = await axios.get(checkApi);
      const available = response.data.data;
      setAvailable(available);
      //setErrors((prev) => ({
     //   ...prev,
      // [field]: available ? "" : `이미 사용 중인 ${field}입니다.`,
      //}));
    } catch (error) {
      console.error(`${field} 중복 체크 중 오류 발생:`, error);
      setErrors((prev) => ({ ...prev, [field]: "오류 발생. 다시 시도해주세요." }));
    }
  };

  // 회원가입 처리
  const handleSubmit = async (e) => {
    e.preventDefault();

    const newErrors = validateForm();
    setErrors(newErrors);

    if (Object.keys(newErrors).length || userIdAvailable === false || emailAvailable === false) {
      alert("입력된 정보를 확인해주세요.");
      return;
    }

    try {
      setIsSubmitting(true);
      const response = await axios.post("http://localhost:8989/api/user/signup", {
        ...formData,
        adAgreed: isAdChecked,
      });
      console.log("formData",formData);

      if (response.data.data === true) {
        alert("회원가입이 완료되었습니다!");
        resetForm(); 
      } else {
        alert("회원가입을 다시 시도해주세요.");
      }
    } catch (error) {
      console.error("회원가입 중 오류 발생:", error);
      alert("회원가입 중 오류가 발생했습니다. 다시 시도해주세요.");
    } finally {
      setIsSubmitting(false);
    }
  };

  // 전체 유효성 검사
  const validateForm = () => {
    const newErrors = {};
    Object.entries(formData).forEach(([name, value]) => {
      const { isValid, message } = validateField(name, value, formData);
      if (!isValid) {
        newErrors[name] = message;
      }
    });

    if (!isMandatoryChecked) {
      newErrors.mandatoryAgreement = "필수 약관에 동의해야 합니다.";
    }

    return newErrors;
  };

  // 폼 초기화
  const resetForm = () => {
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
    setUserIdAvailable(null);
    setEmailAvailable(null);
  };

  return (
    <div className="signup-container">
      <h2 className="h2Title">회원가입</h2>
      <form className="login-form-container" onSubmit={handleSubmit}>
        <InputField
          type="text"
          name="userId"
          value={formData.userId}
          onChange={handleChange}
          onBlur={() =>
            handleBlur("userId", formData.userId, `http://localhost:8989/api/user/check-id?userId=${formData.userId}`, setUserIdAvailable)
          }
          placeholder="아이디를 입력하세요"
          iconName="person"
          errorMessage={errors.userId}
        />
        {userIdAvailable === false && <p className="error-message">이미 사용 중인 아이디입니다.</p>}

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
  onBlur={() => {
    // 이메일이 완전한 형식인지 확인
    const [local, domain] = formData.email.split("@");
    if (!local || !domain) {
      setErrors((prev) => ({ ...prev, email: "올바른 이메일 주소를 입력해주세요." }));
      return;
    }
    handleBlur(
      "email",
      formData.email,
      `http://localhost:8989/api/user/check-email?email=${formData.email}`,
      setEmailAvailable
    );
  }}
  placeholder="이메일을 입력하세요"
  iconName="mail"
  errorMessage={errors.email}
/>
        {emailAvailable === false && <p className="error-message">이미 사용 중인 이메일입니다.</p>}

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

      </form>
      <ButtonLogin
          className="signup-button"
          text="회원가입"
          type="submit"
          disabled={isSubmitting}
          onSubmit={handleSubmit}
        />
    </div>
  );
};

export default SignUpForm;
