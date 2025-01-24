import React, { useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import Cookies from "js-cookie";

const AuthHandler = () => {
  const location = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    // URL에서 토큰 추출
    const token = location.pathname.split("/auth/")[1]; // 예: "/{token}"에서 토큰 추출
    if (token) {
      try {
        // 토큰을 쿠키에 저장
        Cookies.set("authToken", token, {
          expires: 1, // 7일 동안 유효
          path: "/", // 전체 경로에서 사용 가능
          sameSite: "Strict", // 동일 사이트 요청에만 전송
        });
        navigate("/");
      } catch (error) {
      }
    } else {
      console.error("No token found in the URL");
      navigate("/login"); // 토큰이 없으면 로그인 페이지로 이동
    }
  }, [location, navigate]);

  return null; // 로딩 또는 처리 중 UI
};

export default AuthHandler;
