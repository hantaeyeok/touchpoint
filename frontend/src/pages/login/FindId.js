import React, { useState } from "react";
import InputField from "@components/login/InputField";
import EmailField from "@components/login/EmailField";
import ButtonLogin from "@components/login/ButtonLogin";
import axios from "axios";
import "@styles/FindId.css";
import { useNavigate } from "react-router-dom";
import { validateField } from "@components/login/ValidateField"; // 검증 로직 가져오기

const FindId = () => {
  const [formData, setFormData] = useState({
    emailForm: { userName: "", email: "" },
    phoneForm: { userName: "", phone: "" },
  });

  const [emailErrors, setEmailErrors] = useState({}); // 이메일 폼의 오류 상태
  const [phoneErrors, setPhoneErrors] = useState({}); // 전화번호 폼의 오류 상태
  const [isSubmittingEmail, setIsSubmittingEmail] = useState(false);
  const [isSubmittingPhone, setIsSubmittingPhone] = useState(false);
  const navigate = useNavigate();

  // 입력 핸들러 (공통) 및 실시간 검증
  const handleChange = (e, formType, setErrors) => {
    const { name, value } = e.target;

    // 값 업데이트
    setFormData((prev) => ({
      ...prev,
      [formType]: { ...prev[formType], [name]: value },
    }));

    // 유효성 검사
    const { isValid, message } = validateField(name, value);
    setErrors((prev) => ({
      ...prev,
      [name]: isValid ? "" : message,
    }));
  };

  // 아이디 마스킹 처리 함수
  const maskUserId = (userId) => {
    if (userId.length <= 2) return "*".repeat(userId.length);
    const visibleStart = userId.slice(0, 2);
    const visibleEnd = userId.slice(-2);
    return `${visibleStart}${"*".repeat(userId.length - 4)}${visibleEnd}`;
  };

  // 이름 + 이메일로 조회
  const handleSubmitEmail = async (e) => {
    e.preventDefault();
    setEmailErrors({});
    setIsSubmittingEmail(true);

    try {
      const response = await axios.post("http://localhost:8989/login/find-id", formData.emailForm);

      if (response.data.code === "SU") {
        const maskedUserId = maskUserId(response.data.userId);
        alert(`찾은 아이디: ${maskedUserId}`);
        navigate("/login");
      } else {
        setEmailErrors({ general: response.data.message || "아이디를 찾을 수 없습니다." });
      }
    } catch (error) {
      console.error("아이디 찾기 요청 중 오류 발생:", error);
      setEmailErrors({ general: "서버 오류가 발생했습니다. 다시 시도해주세요." });
    } finally {
      setIsSubmittingEmail(false);
    }
  };

  // 이름 + 전화번호로 조회
  const handleSubmitPhone = async (e) => {
    e.preventDefault();
    setPhoneErrors({});
    setIsSubmittingPhone(true);

    try {
      const response = await axios.post("http://localhost:8989/login/find-id", formData.phoneForm);
      if (response.data.code === "SU") {
        const maskedUserId = maskUserId(response.data.userId);
        alert(`찾은 아이디: ${maskedUserId}`);
        navigate("/login");
      } else {
        setPhoneErrors({ general: response.data.message || "아이디를 찾을 수 없습니다." });
      }
    } catch (error) {
      console.error("아이디 찾기 요청 중 오류 발생:", error);
      setPhoneErrors({ general: "서버 오류가 발생했습니다. 다시 시도해주세요." });
    } finally {
      setIsSubmittingPhone(false);
    }
  };

  return (
    <div className="signup-container">
      <h2 className="h2Title">아이디 찾기</h2>

      {/* 이름 + 이메일로 조회 */}
      <form className="login-form-container" onSubmit={handleSubmitEmail}>
        <InputField
          name="userName"
          placeholder="이름을 입력하세요"
          value={formData.emailForm.userName}
          iconName="person"
          errorMessage={emailErrors.userName}
          onChange={(e) => handleChange(e, "emailForm", setEmailErrors)}
        />
        <EmailField
          name="email"
          placeholder="이메일을 입력하세요"
          value={formData.emailForm.email}
          iconName="mail"
          errorMessage={emailErrors.email}
          onChange={(e) => handleChange(e, "emailForm", setEmailErrors)}
        />
        {emailErrors.general && <p className="error-message">{emailErrors.general}</p>}
        <ButtonLogin
          text={isSubmittingEmail ? "처리 중..." : "이메일로 아이디 찾기"}
          type="submit"
          disabled={isSubmittingEmail}
        />
      </form>

      {/* 이름 + 전화번호로 조회 */}
      <form className="login-form-container" onSubmit={handleSubmitPhone}>
        <InputField
          name="userName"
          placeholder="이름을 입력하세요"
          value={formData.phoneForm.userName}
          iconName="person"
          errorMessage={phoneErrors.userName}
          onChange={(e) => handleChange(e, "phoneForm", setPhoneErrors)}
        />
        <InputField
          name="phone"
          placeholder="전화번호를 입력하세요"
          value={formData.phoneForm.phone}
          iconName="phone"
          errorMessage={phoneErrors.phone}
          onChange={(e) => handleChange(e, "phoneForm", setPhoneErrors)}
        />
        {phoneErrors.general && <p className="error-message">{phoneErrors.general}</p>}
        <ButtonLogin
          text={isSubmittingPhone ? "처리 중..." : "전화번호로 아이디 찾기"}
          type="submit"
          disabled={isSubmittingPhone}
        />
      </form>
    </div>
  );
};

export default FindId;
