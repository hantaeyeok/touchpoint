import React, {useState} from "react";
import axios from "axios";
import ButtonLogin from "components/login/ButtonLogin"
import Style from "styles/Login.css";

const Login = () => {
    const [formData,setFormData] = useState({
        usernameOrPhone : "",
        password : "",
        remember : false,
    });

    const handleChange = (e) => {
        const {name, value, type, checked} = e.target;
        setFormData({
            ...formData,
            [name]: type === "checkbox" ? checked : value,
        })
    }

    const handleSubmit = async (e) => {
        e.prenentDefault();
        
        try {
            const response = await axios.post("/api/login", {
              username: formData.username,
              password: formData.password,
              remember: formData.remember,
            });
      
            if (response.status === 200) {
              alert("로그인 성공!");
         
            }
          } catch (error) {
            console.error("로그인 실패:", error);
            alert("로그인 실패. 다시 시도해주세요.");
          
        }
   
    }


    const handleKeyDown = (e) => {
        if (e.key === "Enter") {
          handleSubmit(e);
        }
      };

    return (
        <div className="login-container">
            <form className="login-form" onSubmit={handleSubmit}>
                <div className="form-group">
                <input
                    type="text"
                    id="username"
                    name="username"
                    placeholder="아이디 또는 전화번호"
                    required
                    className="input-field"
                    value={formData.username}
                    onChange={handleChange}
                />
                </div>

                <div className="form-group">
                <input
                    type="password"
                    id="password"
                    name="password"
                    placeholder="비밀번호"
                    required
                    className="input-field"
                    value={formData.password}
                    onChange={handleChange}
                />
                </div>

                <div className="form-group checkbox-group">
                <label>
                    <input
                    type="checkbox"
                    name="remember"
                    checked={formData.remember}
                    onChange={handleChange}
                    />
                    로그인 상태 유지
                </label>
                </div>

                <button type="submit" className="login-button">
                Log In
                </button>

                <div className="login-or">or</div>

                <div className="social-login">
                <button type="button" className="social-button naver">
                    <img src="/icons/naver.png" alt="Naver" className="icon" />
                </button>
                <button type="button" className="social-button kakao">
                    <img src="/icons/kakao.png" alt="Kakao" className="icon" />
                </button>
                <button type="button" className="social-button google">
                    <img src="/icons/google.png" alt="Google" className="icon" />
                </button>
                </div>

                <div className="login-links">
                <a href="/find-password">비밀번호 찾기</a>
                <span>|</span>
                <a href="/find-id">아이디 찾기</a>
                <span>|</span>
                <a href="/signup">회원가입</a>
                </div>
            </form>
            </div>
        );
    };

export default Login;
