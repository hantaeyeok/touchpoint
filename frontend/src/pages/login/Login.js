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
  const [errorMessage, setErrorMessage] = useState("");

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
      [name]: value.replace(/-/g, ""),
    });
  };

  const handleSubmit = async (e) => {
    console.log("버튼 눌림");
    console.log("")
    if (isSubmitting) return;
    setIsSubmitting(true);
    setErrorMessage("");

    try {
      const response = await axios.post("http://localhost:8989/login/sign-in", {
        userIdOrPhone: formData.userIdOrPhone,
        password: formData.password,
        captchaToken: captchaRequired ? formData.captchaToken : null,
      });
      console.log("response{}",response);

      if (response.data.code === "SU") {
        alert("로그인 성공");
        window.location.href = "/";
      }
    } catch (error) {
      if (error.response) {
        console.log("responsedd{}", error.response);
        const { data } = error.response;

        if (data.code === "AL") {
          alert("아이디 잠금: 관리자에게 문의하세요");
        } else if (data.captchaActive === "Y") {
          setCaptchaRequired(true);
          alert("캡차가 활성화되었습니다. 캡차를 완료해주세요.");
          executeCaptcha();
        } else if (data.code === "SF") {
          alert("아이디와 비밀번호가 일치하지 않습니다.");
        } else if (data.code === "CF") {
          alert("캡차를 다시 진행해주세요.");
        }

        setFailedLoginCnt(data.loginFailCount || 0);
      } else {
        console.error("로그인 요청 실패:", error);
        setErrorMessage("로그인 요청 중 오류가 발생했습니다. 다시 시도해주세요.");
      }
    } finally {
      setTimeout(() => {
        setIsSubmitting(false);
      }, 3000);
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
      setErrorMessage("캡차를 실행하는 중 오류가 발생했습니다. 다시 시도해주세요.");
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

        {errorMessage && <p className="error-message">{errorMessage}</p>}

        <div className="button-group">
          <ButtonLogin
            className="signup-button"
            text="로그인"
            type="submit"
            disabled={isSubmitting}
            onSubmit={handleSubmit}
          />
          <ButtonLogin
            text="소셜임시회원가입"
            url="/socalsignup"
            type="link"
            variant="outline"
          />
          <ButtonLogin
            text="일반회원가입 테스트"
            url="/signupform"
            type="link"
            variant="outline"
          />
        </div>

        <div className="login-or">or</div>
        <div className="social-login">
          <button
            type="button"
            className="social-button naver"
            onClick={handleNaverLogin}
          >
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
