import React, { useState, useEffect } from "react";
import axios from "axios";
import ButtonLogin from "@components/login/ButtonLogin";
import naverIcon from "@pages/login/icon/navericon.png";
import "@styles/Login.css";

const Login = () => {
  // formData
  const [formData, setFormData] = useState({
    usernameOrPhone: "",
    userType: "",
    password: "",
    remember: false,
    captchaToken: "",
  });

  const [failedLoginCnt, setFailedLoginCnt] = useState(0); // 로그인 실패 횟수
  const [captchaRequired, setCaptchaRequired] = useState(false); // 캡차 필요 여부


  // Load Google reCAPTCHA script
  useEffect(() => {
    const captchaScript = () => {
      const script = document.createElement("script");
      script.src = "https://www.google.com/recaptcha/api.js?render=6Lek_LYqAAAAAM8fC9lWQNrJlR66_vgZINVe3cc1"; // ✅ 캡차 키 확인 필요 (개발용 키인지, 프로덕션용인지)
      script.async = true;
      document.body.appendChild(script);
    };

    captchaScript();
  }, []);

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    let userType = formData.userType;

    if (name === "usernameOrPhone") {
      // ✅ 입력값에 따라 전화번호인지 아이디인지 구분
      const isPhone = /^[0-9]{10,11}$/.test(value.replace(/-/g, ""));
      userType = isPhone ? "phone" : "userId";
    }

    setFormData({
      ...formData,
      [name]: type === "checkbox" ? checked : value.replace(/-/g, ""), // ✅ 전화번호 입력 시 '-' 자동 제거
      userType,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post("http://localhost:8989/login/login", {
        userIdOrPhone: formData.usernameOrPhone,
        userType: formData.userType,
        password: formData.password,
        captchaToken: captchaRequired ? formData.captchaToken : null,
      });

      // 캡차가 필요한 경우 처리
      if (response.data.data?.captchaRequired) {
        setCaptchaRequired(true); // 캡차 활성화
        setFailedLoginCnt(response.data.data.failedLoginCnt);

        // 캡차 실행
        const token = await window.grecaptcha.execute("6Lek_LYqAAAAAM8fC9lWQNrJlR66_vgZINVe3cc1", { action: "login" });
        setFormData({ ...formData, captchaToken: token });
      } else {
        handleLoginResponse(response);
      }
    } catch (error) {
      console.error("로그인 실패:", error);
      alert("로그인 실패. 다시 시도해주세요.");
    }
  };

  const handleLoginResponse = (response) => {
    if (response.data.message === "로그인 성공") {
      alert("로그인 성공!");
       // ✅ Context나 LocalStorage에 로그인 정보 저장 가능
    } else {
      alert(response.data.message);
      setFailedLoginCnt(response.data.data?.failedLoginCnt || 0);
      setCaptchaRequired(response.data.data?.captchaRequired || false);
    }
  };

  const handleKeyDown = (e) => {
    if (e.key === "Enter") {
      handleSubmit(e);
    }
  };

  // Social Login (Naver)
  const handleNaverLogin = () => {
    window.location.href = "http://localhost:8989/oauth2/authorization/naver"; // ✅ 네이버 OAuth 경로 확인
  };

  return (
    <div className="login-container">
      <form className="login-form" onSubmit={handleSubmit}>
        {/* 아이디 또는 전화번호 입력 필드 */}
        <div className="login-form-group">
          <input
            type="text"
            id="usernameOrPhone"
            name="usernameOrPhone"
            placeholder="아이디 또는 전화번호"
            required
            className="login-input-field"
            value={formData.usernameOrPhone}
            onChange={handleChange}
            onKeyDown={handleKeyDown}
            onInput={(e) => {
              e.target.value = e.target.value.replace(/-/g, ""); // ✅ '-' 문자 제거
            }}
          />
        </div>

        {/* 비밀번호 입력 필드 */}
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
            onKeyDown={handleKeyDown}
          />
        </div>

        {/* 로그인 상태 유지 체크박스 */}
        <div className="login-form-group checkbox-group">
          <label>
            <input
              type="checkbox"
              name="remember"
              checked={formData.remember}
              onChange={handleChange}
            />
            아이디 저장하기
          </label>
        </div>

         {/* 실패 횟수 및 캡차 활성화 정보 */}
         {captchaRequired && (
          <div className="captcha-info">
            <p>로그인 실패 횟수: {failedLoginCnt}</p>
            <p>캡차를 완료하고 다시 시도해주세요.</p>
          </div>
        )}

        {/* 로그인 및 회원가입 버튼 */}
        <div className="button-group">
          <ButtonLogin text="로그인" type="submit" />
          <ButtonLogin text="회원가입" url="/signup" type="link" variant="outline" />
          <ButtonLogin text="소셜임시회원가입" url="/socalsignup" type="link" variant="outline" />
          <ButtonLogin text="일반회원가입 테스트" url="/signupform" type="link" variant="outline" />
        </div>

        {/* 소셜 로그인 버튼 */}
        <div className="login-or">or</div>
        <div className="social-login">
          <button type="button" className="social-button naver" onClick={handleNaverLogin}>
            <img src={naverIcon} alt="Naver" className="icon" />
          </button>
          <button type="button" className="social-button kakao">
            <img src="/icons/kakao.png" alt="Kakao" className="icon" />
          </button>
          <button type="button" className="social-button google">
            <img src="/icons/google.png" alt="Google" className="icon" />
          </button>
        </div>

        {/* 하단 링크 */}
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
