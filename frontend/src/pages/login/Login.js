import React, { useState, useEffect } from "react";
import axios from "axios";
import "@styles/Login.css";
import naverIcon from "@pages/login/icon/navericon.png";
import kakaoIcon from "@pages/login/icon/kakoicon.png";

import ButtonLogin from "@components/login/ButtonLogin";
import { Link } from "react-router-dom";

const Login = () => {
  const [formData, setFormData] = useState({
    userIdOrPhone: "",
    password: "",
    captchaToken: "",
  });

  const [captchaRequired, setCaptchaRequired] = useState(false); // 캡차 활성화 여부
  const [failedLoginCnt, setFailedLoginCnt] = useState(0); // 로그인 실패 횟수
  const [isSubmitting, setIsSubmitting] = useState(false); // 요청 중인지 여부
  const [errorMessage, setErrorMessage] = useState(""); // 오류 메시지

  const siteKey = "6LcZWL4qAAAAAIDcO35ePOizWYKQKOtoDjtUyGE4"; // reCAPTCHA v2 Site Key

  useEffect(() => {
    if (captchaRequired) {
      const script = document.createElement("script");
      script.src = "https://www.google.com/recaptcha/api.js";
      script.async = true;
      script.defer = true;
      document.body.appendChild(script);

      console.log("reCAPTCHA v2 스크립트 로드 완료");

      return () => {
        document.body.removeChild(script);
      };
    }
  }, [captchaRequired]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleRecaptchaVerify = (token) => {
    if (token) {
      setFormData((prev) => ({ ...prev, captchaToken: token }));
      console.log("캡차 토큰 생성:", token);
      setErrorMessage(""); // 오류 메시지 초기화
    } else {
      setErrorMessage("캡차 검증이 필요합니다.");
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (isSubmitting) return;
    setIsSubmitting(true);
    setErrorMessage("");

    try {
      let token = formData.captchaToken;

      if (captchaRequired) {
        const recaptchaResponse = window.grecaptcha.getResponse();
        if (!recaptchaResponse) {
          setErrorMessage("캡차를 완료해주세요.");
          setIsSubmitting(false);
          resetRecaptcha();
          return;
        }
        handleRecaptchaVerify(recaptchaResponse);
        token = recaptchaResponse;
      }

      const response = await axios.post("http://localhost:8989/login/sign-in", {
        userIdOrPhone: formData.userIdOrPhone,
        password: formData.password,
        captchaToken: token,
      });

      if (response.data.code === "SU") {
        const authToken = response.data.token;
        console.log("authToken", authToken);
        alert("로그인 성공");
        window.location.href = `/auth/${authToken}`;

      } else {
        handleServerError(response.data);
      }
    } catch (error) {
      if (error.response) {
        console.error("서버 응답 에러:", error.response);
        handleServerError(error.response.data);
      } else {
        console.error("네트워크 오류:", error);
        setErrorMessage("네트워크 오류가 발생했습니다.");
      }
    } finally {
      setIsSubmitting(false);
    }
  };

  const handleServerError = (data) => {
    console.log("서버에서 반환된 오류:", data);

    if (data.captchaActive === "Y") {
      resetRecaptcha();
      setCaptchaRequired(true);
      setFailedLoginCnt(data.loginFailCount || 0);
      alert("캡차가 활성화되었습니다. 캡차를 완료해주세요.");
    } else if (data.code === "SF") {
      const alertMessage = captchaRequired
        ? "캡차는 성공했지만 아이디와 비밀번호가 일치하지 않습니다."
        : "아이디와 비밀번호가 일치하지 않습니다.";
      alert(alertMessage);
    } else if (data.code === "AL") {
      alert("아이디 잠금: 관리자에게 문의하세요.");
    } else if (data.code === "CF") {
      alert("캡차를 다시 진행해주세요.");
      resetRecaptcha();
    }
  };

  const resetRecaptcha = () => {
    if (window.grecaptcha) {
      grecaptcha.reset(); // reCAPTCHA 초기화
      setFormData((prev) => ({ ...prev, captchaToken: "" })); // 토큰 초기화
      console.log("reCAPTCHA 초기화 완료");
    }
  };
  const handleNaverLogin = () => {
    window.location.href = "http://localhost:8989/oauth2/authorization/naver";
  };
  const handleKakaoLogin = () => {
    window.location.href = "http://localhost:8989/oauth2/authorization/kakao";
  };
  

  return (
    <div className="login-container">
      <form className="login-form" onSubmit={handleSubmit}>
        <div className="login-form-group">
          <input
            type="text"
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
            <div
              className="g-recaptcha"
              data-sitekey={siteKey}
              data-callback="handleRecaptchaVerify"
            ></div>
          </div>
        )}

        {errorMessage && <p className="error-message">{errorMessage}</p>}

      <ButtonLogin text="로그인" type="submit"></ButtonLogin>
      </form>
      <ButtonLogin text="회원가입" url="/signupform" type="link" variant="outline" />
      {/* <ButtonLogin text="소셜임시회원가입" url="/socalsignup" type="link" variant="outline" /> */}

      <div className="login-or">or</div>
      <div className="social-login">
        <button type="button" className="social-button naver" onClick={handleNaverLogin}>
          <img src={naverIcon} alt="Naver" className="icon" />
        </button>
        <button type="button" className="social-button kakao" onClick={handleKakaoLogin}>
          <img src={kakaoIcon} alt="Naver" className="icon" />
        </button>
      </div>

      <div className="login-links">
        <Link to={"/findID"}>아이디 찾기</Link>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <span>|</span>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <Link to={"/findPassword"}>비밀번호 찾기</Link>
      </div>
    </div>
  );
};

export default Login;
