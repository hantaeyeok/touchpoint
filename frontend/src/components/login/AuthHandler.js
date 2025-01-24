import React, { useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import Cookies from "js-cookie";
import { useAuth } from "@components/login/AuthProvider"; // AuthContext 사용

const AuthHandler = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { login } = useAuth(); // AuthProvider의 login 함수 가져오기

  useEffect(() => {
    // URL에서 토큰 추출
    const token = location.pathname.split("/auth/")[1]; // 예: "/auth/{token}"에서 토큰 추출
    if (token) {
      try {
        // 토큰을 쿠키에 저장
        Cookies.set("authToken", token, {
          expires: 1, // 1일 동안 유효
          path: "/", // 전체 경로에서 사용 가능
          sameSite: "Strict", // 동일 사이트 요청에만 전송
        });

        // 로그인 상태 업데이트
        login(); // AuthProvider의 상태를 업데이트

        // 메인 페이지로 이동
        navigate("/");
      } catch (error) {
        console.error("Error saving token:", error);
        navigate("/login"); // 에러 발생 시 로그인 페이지로 이동
      }
    } else {
      console.error("No token found in the URL");
      navigate("/login"); // 토큰이 없으면 로그인 페이지로 이동
    }
  }, [location, navigate, login]);

  return null; // 별도의 UI가 필요 없는 경우
};

export default AuthHandler;
