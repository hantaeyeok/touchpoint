import React, { useState } from "react";
import axios from "axios";
import InputField from "@components/login/InputField";
import PasswordField from "@components/login/PasswordField";
import EmailField from "@components/login/EmailField";
import AgreementField from "@components/login/AgreementField";
import { validateField } from "@components/login/ValidateField";
import ButtonLogin from "@components/login/ButtonLogin";
import { useNavigate } from "react-router-dom";
import "@styles/SignUp.css";

const SignUpForm = () => {
  const [formData, setFormData] = useState({
    userId: "",
    password: "",
    confirmPassword: "",
    userName: "",
    phone: "",
    email: "",
    certificationNumber: "",
    adAgreed: "N",
  });
  const [errors, setErrors] = useState({});
  const [isMandatoryChecked, setIsMandatoryChecked] = useState(false);
  const [isAdChecked, setIsAdChecked] = useState(false);
  const [userIdAvailable, setUserIdAvailable] = useState(null);
  const [emailAvailable, setEmailAvailable] = useState(null);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [phoneAvailable, setPhoneAvailable] = useState(null);
  const [isEmailSent, setIsEmailSent] = useState(false);
  const [isCertificationValid, setIsCertificationValid] = useState(false);
  const navigate = useNavigate();

  // 공통 Axios 요청 함수
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
    const { isValid, message } = validateField(name, value, { ...formData, [name]: value });
    setErrors((prev) => ({
      ...prev,
      [name]: isValid ? "" : message,
    }));
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
      "http://localhost:8989/login/email-certificaion",
      { userId: formData.userId, email: formData.email },
      () => {
        alert("인증번호가 전송되었습니다. 이메일을 확인해주세요.");
        setIsEmailSent(true);
      },
      (error) => {
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

    console.log("Verifying certificationNumber:", formData.certificationNumber);


    sendRequest(
      "http://localhost:8989/login/check-certificaion",
      { certificationNumber: formData.certificationNumber, userId: formData.userId, email: formData.email },
      () => {
        alert("인증번호가 확인되었습니다.");
        setIsCertificationValid(true);
      },
      () => {
        setErrors((prev) => ({
          ...prev,
          certificationNumber: "유효하지 않은 인증번호입니다. 다시 확인해주세요.",
        }));
        setIsCertificationValid(false);
      }
    );
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
      setAvailable(true);
      setErrors((prev) => ({ ...prev, [field]: "" }));
    },
    () => {
      const errorMessage =
        field === "userId"
          ? "사용 중인 아이디입니다."
          : field === "phone"
          ? "사용 중인 전화번호입니다."
          : field === "email"
          ? "사용 중인 이메일입니다."
          : "이미 사용 중인 값입니다.";
      setAvailable(false);
      setErrors((prev) => ({ ...prev, [field]: errorMessage }));
      }
    );
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const newErrors = validateForm();
    setErrors(newErrors);

    if (
      Object.keys(newErrors).length ||
      userIdAvailable === false ||
      emailAvailable === false ||
      phoneAvailable === false ||
      !isCertificationValid
    ) {
      alert("입력된 정보를 확인해주세요.");
      return;
    }

    setIsSubmitting(true);

    sendRequest(
      "http://localhost:8989/login/sign-up",
      formData,
      () => {
        alert("회원가입이 완료되었습니다!");
        resetForm();
        navigate("/login");

      },
      () => {
        alert("회원가입 중 오류가 발생했습니다. 다시 시도해주세요.");
      }
    );

    setIsSubmitting(false);
  };

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

  const resetForm = () => {
    setFormData({
      userId: "",
      password: "",
      confirmPassword: "",
      userName: "",
      phone: "",
      email: "",
      certificationNumber: "",
      adAgreed: "N",
    });
    setIsMandatoryChecked(false);
    setIsAdChecked(false);
    setErrors({});
    setUserIdAvailable(null);
    setEmailAvailable(null);
    setIsEmailSent(false);
    setIsCertificationValid(false);
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
            handleBlur("userId", formData.userId, "http://localhost:8989/login/check-id", setUserIdAvailable)
          }
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
          name="userName"
          value={formData.userName}
          onChange={handleChange}
          placeholder="이름을 입력하세요"
          iconName="person"
          errorMessage={errors.userName}
        />
        <InputField
          type="text"
          name="phone"
          value={formData.phone}
          onChange={handleChange}
          placeholder="전화번호를 입력하세요"
          iconName="phone"
          errorMessage={errors.phone}
          onBlur={() =>
            handleBlur(
              "phone",
              formData.phone,
              `http://localhost:8989/login/check-phone`,
              setPhoneAvailable
            )
          }
        />

        <EmailField
          name="email"
          value={formData.email}
          onChange={handleChange}
          onBlur={() => {
            const [local, domain] = formData.email.split("@");
            if (!local || !domain) {
              setErrors((prev) => ({ ...prev, email: "올바른 이메일 주소를 입력해주세요." }));
              return;
            }
            handleBlur(
              "email",
              formData.email,
              `http://localhost:8989/login/check-email`,
              setEmailAvailable
            );
          }}
          placeholder="이메일을 입력하세요"
          iconName="mail"
          errorMessage={errors.email}
        />
        <ButtonLogin
          text="인증번호 받기"
          type="button"
          variant="outline"
          onSubmit={() => {
            console.log("인증번호 받기 버튼 클릭됨");
            handleEmailSend();
          }}          
          disabled={!formData.userId}
        />

        {isEmailSent && (
          <div className="email-button-group">
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
              disabled={!formData.certificationNumber}
            />
          </div>
        )}

        <AgreementField
          type="mandatory"
          isChecked={isMandatoryChecked}
          onToggle={() => setIsMandatoryChecked(!isMandatoryChecked)}
          errorMessage={errors.mandatoryAgreement}
        />
        <AgreementField
          type="ad"
          isChecked={isAdChecked}
          onToggle={() => {
            setIsAdChecked(!isAdChecked);
            setFormData((prev) => ({
              ...prev,
              adAgreed: !isAdChecked ? "Y" : "N",
            }));
          }}
        />

        <ButtonLogin
          className="signup-button"
          text="회원가입"
          type="submit"
          disabled={isSubmitting}
          onSubmit={handleSubmit}
          style={{ marginTop: "20px" }}
        />
      </form>
    </div>
  );
};

export default SignUpForm;
