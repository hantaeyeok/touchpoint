import React, { useState } from "react";
import InputField from "@components/login/InputField";
import EmailField from "@components/login/EmailField";

import AgreementField from "@components/login/AgreementField";
import ButtonLogin from "@components/login/ButtonLogin";
import axios from "axios";
import { validateField } from "@components/login/ValidateField"; // 검증 로직 가져오기
import "@styles/SignUp.css";

const SocalSignUp = () => {
  const [formData, setFormData] = useState({
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

    // 모든 필드 검증
    Object.entries(formData).forEach(([name, value]) => {
      const { isValid, message } = validateField(name, value, formData);
      if (!isValid) {
        valid = false;
        newErrors[name] = message;
      }
    });

    // 약관 동의 검증
    if (!isMandatoryChecked) {
      valid = false;
      newErrors.mandatoryAgreement = "필수 약관에 동의해야 회원가입을 진행할 수 있습니다.";
    }

    setErrors(newErrors);

    if (!valid) return; // 유효성 실패 시 제출 중단

    setIsSubmitting(true);

    try {
      const response = await axios.post("/api/user/social-signup", {
        ...formData,
        adAgreed: isAdChecked, // 광고 동의 여부 추가
      });

      if (response.data.success) {
        alert("회원가입이 완료되었습니다!");
        setFormData({
          username: "",
          phone: "",
          email: "",
        });
        setIsMandatoryChecked(false);
        setIsAdChecked(false);
      } else {
        alert(response.data.message || "회원가입 실패. 다시 시도해주세요.");
      }
    } catch (error) {
      console.error("회원가입 중 오류 발생:", error);
      alert("회원가입 중 오류가 발생했습니다. 다시 시도해주세요.");
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="signup-container">
      <h2 className="h2Title">소셜 회원가입</h2>
      <form className="login-form-container" onSubmit={handleSubmit}>
        <InputField
          type="text"
          name="username"
          value={formData.username}
          onChange={handleChange}
          placeholder="이름을 입력해주세요"
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
          text={isSubmitting ? "처리 중..." : "회원가입"}
          type="submit"
          disabled={isSubmitting}
        />
      </form>
    </div>
  );
};

export default SocalSignUp;
