import React, { useState, useEffect } from "react";
import axios from "axios";

const RecaptchaTest = () => {
  const [captchaVerified, setCaptchaVerified] = useState(false);
  const [captchaToken, setCaptchaToken] = useState("");
  const [message, setMessage] = useState("");
  const siteKey = "6LcZWL4qAAAAAIDcO35ePOizWYKQKOtoDjtUyGE4"; // Google reCAPTCHA v2 사이트 키를 여기에 입력하세요.

  useEffect(() => {
    // Google reCAPTCHA v2 스크립트 동적으로 로드
    const script = document.createElement("script");
    script.src = "https://www.google.com/recaptcha/api.js";
    script.async = true;
    script.defer = true;
    document.body.appendChild(script);

    script.onload = () => {
      console.log("reCAPTCHA v2 스크립트 로드 완료");
    };

    script.onerror = () => {
      console.error("reCAPTCHA v2 스크립트 로드 실패");
      setMessage("reCAPTCHA 스크립트를 로드할 수 없습니다. 인터넷 연결을 확인하세요.");
    };

    return () => {
      document.body.removeChild(script);
    };
  }, []);

  const handleSubmit = async () => {
    if (!captchaVerified) {
      setMessage("reCAPTCHA를 완료해주세요.");
      return;
    }

    try {
      setMessage("reCAPTCHA 검증 요청 중...");
      // 서버로 reCAPTCHA 토큰 전송
      const response = await axios.post("http://localhost:8989/login/validate", {
        token: captchaToken,
      });

      if (response.data.success) {
        setMessage("reCAPTCHA 검증 성공! 서버 요청 완료.");
      } else {
        setMessage("reCAPTCHA 검증 실패. 다시 시도해주세요.");
      }
    } catch (error) {
      console.error("서버와의 통신 오류:", error);
      setMessage("서버와의 통신 중 오류가 발생했습니다.");
    }
  };

  // reCAPTCHA 콜백 함수
  window.verifyRecaptchaCallback = (token) => {
    setCaptchaVerified(true);
    setCaptchaToken(token);
    setMessage("reCAPTCHA 검증 완료!");
    console.log("생성된 reCAPTCHA 토큰:", token);
  };

  return (
    <div style={{ padding: "20px", maxWidth: "400px", margin: "0 auto", textAlign: "center" }}>
      <h2>Google reCAPTCHA v2 테스트</h2>
      {/* reCAPTCHA 위젯 */}
      <div
        className="g-recaptcha"
        data-sitekey={siteKey}
        data-callback="verifyRecaptchaCallback"
      ></div>
      <button onClick={handleSubmit} style={{ marginTop: "20px", padding: "10px 20px" }}>
        확인
      </button>
      <p>{message}</p>
    </div>
  );
};

export default RecaptchaTest;
