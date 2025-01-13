import React, {useState} from "react";
import axios from "axios";
import ButtonLogin from '@components/login/ButtonLogin'
import naverIcon from '@pages/login/icon/navericon.png';
import "@styles/Login.css";


const Login = () => {
  //formData
    const [formData,setFormData] = useState({
        usernameOrPhone : "",
        password : "",
        remember : false,
        captchaToken: "",
    });


    //google capcha
    useEffect(() => {
      const loadRecaptchaScript = () => {
          const script = document.createElement("script");
          script.src = "https://www.google.com/recaptcha/api.js?render=6Lc-m7QqAAAAAGnwC5OIFaJxiHlknAVpNvuS9Xnp";
          script.async = true;
          document.body.appendChild(script);
      };

      loadRecaptchaScript();
  }, []);


  //
    const handleChange = (e) => {
        const {name, value, type, checked} = e.target;
        setFormData({
            ...formData,
            [name]: type === "checkbox" ? checked : value,
        });
    };


    //sumit
    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
          const isPhone = /^[0-9]{10,11}$/.test(formData.usernameOrPhone);
          const response = await axios.post("/api/login", {
            loginType: isPhone ? "phone" : "username",
            identifier: formData.usernameOrPhone,
            password: formData.password,
            remember: formData.remember,
        });

        if(response.data.data.captcha){ //여기 조건 captcha 어떻게할지 생각해야함.
          const token = await window.grecaptcha.execute("6Lc-m7QqAAAAAGnwC5OIFaJxiHlknAVpNvuS9Xnp", { action: "login" });
        }

          const token = await window.grecaptcha.execute("6Lc-m7QqAAAAAGnwC5OIFaJxiHlknAVpNvuS9Xnp", { action: "login" });

          const response1 = await axios.post("/api/login", {
            username: formData.usernameOrPhone,
            password: formData.password,
            remember: formData.remember,
            captchaToken: token,
          });
          
          if (response.data.message === "로그인 성공") {
            alert("로그인 성공!");

          //if (formData.remember) {
           //   localStorage.setItem("savedUsername", formData.usernameOrPhone);
          //} else {
          //    localStorage.removeItem("savedUsername");
         // }
          } else {
            alert(response.data.message);
          }
        } catch (error) {
          console.error("로그인 실패:", error);
          alert("로그인 실패. 다시 시도해주세요.");
        }
      };


    const handleKeyDown = (e) => {
        if (e.key === "Enter") {
          handleSubmit(e);
        }
      };

      //소셜 로그인 
      const handleNaverLogin = () => {
        window.location.href = "http://localhost:8989/oauth2/authorization/naver";
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
                  e.target.value = e.target.value.replace(/-/g, ""); // '-' 문자 제거
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
    
            {/* 로그인 및 회원가입 버튼 */}
            <div className="button-group">
              <ButtonLogin text="로그인" type="submit" />
              <ButtonLogin text="회원가입" url="/signup" type="link" variant="outline"/>
              <ButtonLogin text="소셜임시회원가입" url="/socalsignup" type="link" variant="outline"/>
              <ButtonLogin text="일반회원가입 테스트" url="/signupform" type="link" variant="outline"/>
              
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
