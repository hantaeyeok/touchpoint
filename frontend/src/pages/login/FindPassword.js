import React, { useState } from "react";
import axios from "axios";
import InputField from "@components/login/InputField";
import EmailField from "@components/login/EmailField";
import ButtonLogin from "@components/login/ButtonLogin";
import "@styles/SignUp.css";

const FindPassword = () => {
  const [formData, setFormData] = useState({
    userId: "",
    email: "",
    certificationNumber: "",
  });

  const [errors, setErrors] = useState({});
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [isEmailSent, setIsEmailSent] = useState(false);
  const [isCertificationValid, setIsCertificationValid] = useState(false);

  const sendRequest = async (url, data, successCallback, errorCallback) => {
    try {
      const response = await axios.post(url, data);
      successCallback(response);
    } catch (error) {
      console.error("요청 중 오류 발생:", error);
      errorCallback(error);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
    validateAndSetError(name, value);
  };

  const validateAndSetError = (name, value) => {
    let error = "";
    if (name === "userId" && !value.trim()) {
      error = "아이디를 입력해주세요.";
    } else if (name === "email") {
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!emailRegex.test(value)) {
        error = "올바른 이메일 주소를 입력해주세요.";
      }
    }
    setErrors((prev) => ({ ...prev, [name]: error }));
  };

  const handleBlur = (field, value, checkApi, setAvailable) => {
    if (!value) {
      setErrors((prev) => ({ ...prev, [field]: `${field}을(를) 입력해주세요.` }));
      return;
    }

    sendRequest(
      checkApi,
      { [field]: value },
      () => {
        setErrors((prev) => ({ ...prev, [field]: "" }));
      },
      () => {
        setErrors((prev) => ({ ...prev, [field]: "등록된 정보가 없습니다." }));
      }
    );
  };

  const handleEmailSend = () => {
    if (!formData.userId || !formData.email) {
      setErrors((prev) => ({
        ...prev,
        userId: !formData.userId ? "아이디를 먼저 입력해주세요." : prev.userId,
        email: !formData.email ? "이메일 주소를 입력해주세요." : prev.email,
      }));
      return;
    }

    sendRequest(
      "http://localhost:8989/login/email-certification",
      { userId: formData.userId, email: formData.email },
      () => {
        alert("인증번호가 전송되었습니다. 이메일을 확인해주세요.");
        setIsEmailSent(true);
      },
      () => {
        setErrors((prev) => ({ ...prev, email: "인증번호 전송 중 오류가 발생했습니다." }));
      }
    );
  };

  const handleCertificationVerify = () => {
    if (!formData.certificationNumber) {
      setErrors((prev) => ({
        ...prev,
        certificationNumber: "인증번호를 입력해주세요.",
      }));
      return;
    }

    sendRequest(
      "http://localhost:8989/login/check-certification",
      {
        userId: formData.userId,
        email: formData.email,
        certificationNumber: formData.certificationNumber,
      },
      () => {
        alert("인증번호가 확인되었습니다.");
        setIsCertificationValid(true);
      },
      () => {
        setErrors((prev) => ({
          ...prev,
          certificationNumber: "유효하지 않은 인증번호입니다.",
        }));
        setIsCertificationValid(false);
      }
    );
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!isCertificationValid) {
      alert("인증번호를 확인해주세요.");
      return;
    }

    alert("비밀번호 변경 페이지로 이동합니다.");
    // 비밀번호 변경 페이지로 리다이렉트 로직 추가 가능
  };

  return (
    <div className="signup-container">
      <h2>비밀번호 찾기</h2>
      <form className={"login-form-container"} onSubmit={handleSubmit}>
        <InputField
          type="text"
          name="userId"
          value={formData.userId}
          onChange={handleChange}
          onBlur={() =>
            handleBlur("userId", formData.userId, "http://localhost:8989/login/check-id", () => {})
          }
          placeholder="아이디를 입력하세요"
          iconName="person"
          errorMessage={errors.userId}
        />
        <EmailField
          name="email"
          value={formData.email}
          onChange={handleChange}
          onBlur={() =>
            handleBlur(
              "email",
              formData.email,
              "http://localhost:8989/login/check-email",
              () => {}
            )
          }
          placeholder="이메일을 입력하세요"
          iconName="mail"
          errorMessage={errors.email}
        />
        <ButtonLogin
          text="인증번호 받기"
          type="button"
          variant="outline"
          onSubmit={handleEmailSend}
          disabled={!formData.userId || !formData.email || errors.userId || errors.email}
        />
        {isEmailSent && (
          <>
            <InputField
              type="text"
              name="certificationNumber"
              value={formData.certificationNumber}
              onChange={handleChange}
              placeholder="인증번호를 입력하세요"
              iconName="key"
              errorMessage={errors.certificationNumber}
            />
            <ButtonLogin
              text="인증번호 확인"
              type="button"
              variant="default"
              onSubmit={handleCertificationVerify}
              disabled={!formData.certificationNumber || errors.certificationNumber}
            />
          </>
        )}
        <ButtonLogin
          text="다음"
          type="submit"
          disabled={!isCertificationValid}
        />
      </form>
    </div>
  );
};

export default FindPassword;
