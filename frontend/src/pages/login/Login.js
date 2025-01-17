import React, { useState, useEffect } from "react";
import axios from "axios";
import ButtonLogin from "@components/login/ButtonLogin";
import naverIcon from "@pages/login/icon/navericon.png";
import "@styles/Login.css";

const Login = () => {
  const [formData, setFormData] = useState({
    userIdOrPhone: "",
    password: "",
    captchaToken: "",
  });

  const [failedLoginCnt, setFailedLoginCnt] = useState(0);
  const [captchaRequired, setCaptchaRequired] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);

  useEffect(() => {
    const loadCaptchaScript = () => {
      const script = document.createElement("script");
      script.src = "https://www.google.com/recaptcha/api.js?render=6Lek_LYqAAAAAM8fC9lWQNrJlR66_vgZINVe3cc1";
      script.async = true;
      document.body.appendChild(script);
    };
    loadCaptchaScript();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value.replace(/-/g, ""), // 전화번호 입력 시 '-' 제거
    });
  };

  const handleSubmit = async (e) => {
    console.log("Form submitted: ", formData);
    e.preventDefault();

    if (isSubmitting) return; // Prevent multiple submissions

    setIsSubmitting(true);
    try {
      const response = await axios.post("http://localhost:8989/login/sign-in", {
        userIdOrPhone: formData.userIdOrPhone,
        password: formData.password,
        captchaToken: captchaRequired ? formData.captchaToken : null,
      });

      handleLoginResponse(response);
    } catch (error) {
      console.error("로그인 요청 실패:", error);
      alert("로그인 요청 중 오류가 발생했습니다. 다시 시도해주세요.");
    } finally {
      setIsSubmitting(false);
    }
  };

  const handleLoginResponse = async (response) => {
    const { status, data } = response;

    if (status === 200) {
      alert("로그인 성공!");
      localStorage.setItem("userToken", data?.data?.token || "");
      window.location.href = "/";
    } else if (status === 401 || status === 403) {
      setFailedLoginCnt(data?.data?.loginFailCount || 0);
      setCaptchaRequired(data?.data?.captchaRequired === "Y");

      if (data?.data?.captchaRequired === "Y") {
        alert("캡차가 활성화되었습니다. 캡차를 완료해주세요.");
        executeCaptcha();
      } else {
        alert(data?.message || "로그인 실패. 다시 시도해주세요.");
      }
    }
  };

  const executeCaptcha = async () => {
    try {
      const token = await window.grecaptcha.execute(
        "6Lek_LYqAAAAAM8fC9lWQNrJlR66_vgZINVe3cc1",
        { action: "login" }
      );
      setFormData((prev) => ({ ...prev, captchaToken: token }));
    } catch (error) {
      console.error("캡차 실행 실패:", error);
    }
  };

  const handleNaverLogin = () => {
    window.location.href = "http://localhost:8989/oauth2/authorization/naver";
  };

  return (
    <div className="login-container">
      <form className="login-form" onSubmit={handleSubmit}>
        <div className="login-form-group">
          <input
            type="text"
            id="userIdOrPhone"
            name="userIdOrPhone"
            placeholder="아이디 또는 전화번호"
            required
            className="login-input-field"
            value={formData.userIdOrPhone}
            onChange={handleChange}
          />
        </div>

        <div className="login-form-group">
          <input
            type="password"
            id="password"
            name="password"
            placeholder="비밀번호"
            required
            className="login-input-field"
            value={formData.password}
            onChange={handleChange}
          />
        </div>

        {captchaRequired && (
          <div className="captcha-info">
            <p>로그인 실패 횟수: {failedLoginCnt}</p>
            <p>캡차를 완료하고 다시 시도해주세요.</p>
          </div>
        )}

        <div className="button-group">
          <ButtonLogin 
          text="로그인" 
          type="submit" 
          onClick={handleSubmit}
          disabled={isSubmitting}

          />
          <ButtonLogin text="소셜임시회원가입" url="/socalsignup" type="link" variant="outline" />
          <ButtonLogin text="일반회원가입 테스트" url="/signupform" type="link" variant="outline" />
        </div>

        <div className="login-or">or</div>
        <div className="social-login">
          <button type="button" className="social-button naver" onClick={handleNaverLogin}>
            <img src={naverIcon} alt="Naver" className="icon" />
          </button>
          <button type="button" className="social-button naver" onClick={handleNaverLogin}>
            <img src={naverIcon} alt="Naver" className="icon" />
          </button>
          <button type="button" className="social-button naver" onClick={handleNaverLogin}>
            <img src={naverIcon} alt="Naver" className="icon" />
          </button>
        </div>

        <div className="login-links">
          <a href="/find-password">비밀번호 찾기</a>
          &nbsp;&nbsp;&nbsp;&nbsp;
          <span>|</span>
          &nbsp;&nbsp;&nbsp;&nbsp;
          <a href="/find-id">아이디 찾기</a>
        </div>
      </form>
    </div>
  );
};

export default Login;
